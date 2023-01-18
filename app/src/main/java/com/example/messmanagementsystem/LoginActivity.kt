package com.example.messmanagementsystem

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    lateinit var et_email: EditText
    lateinit var et_pass: EditText
    lateinit var et_admin_pass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_email = findViewById(R.id.inputEmail)
        et_pass = findViewById(R.id.inputPassword)
        et_admin_pass = findViewById(R.id.inputAdminPassword)

        var volleyRequestQueue: RequestQueue? = null
        var dialog: ProgressDialog? = null
        var serverAPIURL: String = "http://192.168.1.74/signin_student"
        var TAG = "Sign in"

        fun SendSignInDataToServer(
            email: String,
            password: String
        ) {
            volleyRequestQueue = Volley.newRequestQueue(this)
            dialog = ProgressDialog.show(this, "", "Please wait...", true);
            val parameters: MutableMap<String, String> = HashMap()
            // Add your parameters in HashMap
            parameters.put("email", email)
            parameters.put("password", password)

            val gson = Gson()
            val jsonBody = JSONObject(gson.toJson(parameters))

            val mQueue = Volley.newRequestQueue(applicationContext)

            val jsonObjectRequest: JsonObjectRequest =
                object : JsonObjectRequest(serverAPIURL, jsonBody,
                    Response.Listener { response ->
                        Log.e(TAG, "response: $response")
                        val responseObj = JSONObject(response.toString())
                        val isSuccess: Boolean= responseObj.getBoolean("isSuccess")
                        val code = responseObj.getInt("code")

                        if(isSuccess.toString() == "true"){
                            val intent = Intent(this, Home_SP::class.java).also{
                                it.putExtra("email", email)
                                startActivity(it)
                            }
                        }
                        else{
                            Toast.makeText(this, "Error1", Toast.LENGTH_LONG).show()
                        }
                        dialog?.dismiss()
                        try {

                            if (responseObj.has("data")) {
                                val data = responseObj.getJSONObject("data")
                                // Handle your server response data here
                            }

                        } catch (e: Exception) { // caught while parsing the response
                            Log.e(TAG, "problem occurred")
                            e.printStackTrace()

                        }

                    },
                    Response.ErrorListener { error -> Log.e("TAG", error.message, error) }) {
                    //no semicolon or coma
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["Content-Type"] = "application/json"
                        return params
                    }
                }
            mQueue.add(jsonObjectRequest)
        }

        volleyRequestQueue = null
        dialog = null
        serverAPIURL = "http://192.168.1.74/signin_admin"
        TAG = "Sign in admin"

        fun SendAdminSignInDataToServer(
            email: String,
            password: String
        ) {
            volleyRequestQueue = Volley.newRequestQueue(this)
            dialog = ProgressDialog.show(this, "", "Please wait...", true);
            val parameters: MutableMap<String, String> = HashMap()
            // Add your parameters in HashMap
            parameters.put("email", email)
            parameters.put("password", password)

            val gson = Gson()
            val jsonBody = JSONObject(gson.toJson(parameters))

            val mQueue = Volley.newRequestQueue(applicationContext)

            val jsonObjectRequest: JsonObjectRequest =
                object : JsonObjectRequest(serverAPIURL, jsonBody,
                    Response.Listener { response ->
                        Log.e(TAG, "response: $response")
                        val responseObj = JSONObject(response.toString())
                        val isSuccess: Boolean= responseObj.getBoolean("isSuccess")
                        val code = responseObj.getInt("code")

                        if(isSuccess.toString() == "true"){
                            val intent = Intent(this, Home_AP_Activity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, "Error1", Toast.LENGTH_LONG).show()
                        }
                        dialog?.dismiss()
                        try {

                            if (responseObj.has("data")) {
                                val data = responseObj.getJSONObject("data")
                                // Handle your server response data here
                            }

                        } catch (e: Exception) { // caught while parsing the response
                            Log.e(TAG, "problem occurred")
                            e.printStackTrace()

                        }

                    },
                    Response.ErrorListener { error -> Log.e("TAG", error.message, error) }) {
                    //no semicolon or coma
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["Content-Type"] = "application/json"
                        return params
                    }
                }
            mQueue.add(jsonObjectRequest)
        }


        TextViewSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            if(rb_student.isChecked == true) {
                SendSignInDataToServer(et_email.text.toString(), et_pass.text.toString())
            }
            else if(rb_admin.isChecked == true){
                if(et_admin_pass.text.toString() == "sys") {
                    SendAdminSignInDataToServer(et_email.text.toString(), et_pass.text.toString())
                }
                else{
                    Toast.makeText(this, "Invalid Admin Password", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show()
            }
        }
    }
}