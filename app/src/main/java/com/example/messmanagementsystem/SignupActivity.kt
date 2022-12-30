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
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONException
import org.json.JSONObject


class SignupActivity : AppCompatActivity() {
    lateinit var et_email: EditText
    lateinit var et_name: EditText
    lateinit var et_pass: EditText
    lateinit var et_hostel: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        et_email = findViewById(R.id.inputEmail)
        et_name = findViewById(R.id.inputName)
        et_pass = findViewById(R.id.inputPassword)
        et_hostel = findViewById(R.id.inputHostel)

        var volleyRequestQueue: RequestQueue? = null
        var dialog: ProgressDialog? = null
        var serverAPIURL: String = "http://10.1.164.225/insert_student"
        var TAG = "Insert in STUDENT TABLE"

        fun SendSignUpDataToServer(
            firstName: String,
            email: String,
            password: String,
            hostel: String
        ) {
            volleyRequestQueue = Volley.newRequestQueue(this)
            dialog = ProgressDialog.show(this, "", "Please wait...", true);
            val parameters: MutableMap<String, String> = HashMap()
            // Add your parameters in HashMap
            parameters.put("firstName", firstName)
            parameters.put("email", email)
            parameters.put("password", password)
            parameters.put("hostel", hostel)

            val gson = Gson()
            val jsonBody = JSONObject(gson.toJson(parameters))

            val mQueue = Volley.newRequestQueue(applicationContext)

            val jsonObjectRequest: JsonObjectRequest =
                object : JsonObjectRequest(serverAPIURL, jsonBody,
                    Response.Listener { response ->
                        Log.e(TAG, "response: $response")
                        dialog?.dismiss()
                        try {
                            val responseObj = JSONObject(response.toString())
                            val isSuccess = responseObj.getBoolean("isSuccess")
                            val code = responseObj.getInt("code")
                            if (responseObj.has("data")) {
                                val data = responseObj.getJSONObject("data")
                                // Handle your server response data here
                            }
                            Toast.makeText(this, isSuccess.toString(), Toast.LENGTH_LONG).show()

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
        serverAPIURL = "http://10.1.164.225/insert_admin"
        TAG = "Insert in ADMIN TABLE"

        fun SendAdminSignUpDataToServer(
            firstName: String,
            email: String,
            password: String,
        ) {
            volleyRequestQueue = Volley.newRequestQueue(this)
            dialog = ProgressDialog.show(this, "", "Please wait...", true);
            val parameters: MutableMap<String, String> = HashMap()
            // Add your parameters in HashMap
            parameters.put("firstName", firstName)
            parameters.put("email", email)
            parameters.put("password", password)

            val gson = Gson()
            val jsonBody = JSONObject(gson.toJson(parameters))

            val mQueue = Volley.newRequestQueue(applicationContext)

            val jsonObjectRequest: JsonObjectRequest =
                object : JsonObjectRequest(serverAPIURL, jsonBody,
                    Response.Listener { response ->
                        Log.e(TAG, "response: $response")
                        dialog?.dismiss()
                        try {
                            val responseObj = JSONObject(response.toString())
                            val isSuccess = responseObj.getBoolean("isSuccess")
                            val code = responseObj.getInt("code")
                            if (responseObj.has("data")) {
                                val data = responseObj.getJSONObject("data")
                                // Handle your server response data here
                            }
                            Toast.makeText(this, isSuccess.toString(), Toast.LENGTH_LONG).show()

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

        buttonSignup.setOnClickListener {
            if(signupStudent.isChecked == true) {
                SendSignUpDataToServer(et_name.text.toString(), et_email.text.toString(), et_pass.text.toString(), et_hostel.text.toString())
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else if(signupAdmin.isChecked == true){
                SendAdminSignUpDataToServer(et_name.text.toString(), et_email.text.toString(), et_pass.text.toString())
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show()
            }
        }

        AlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


//            val strReq: StringRequest = object : StringRequest(
//                Method.POST,serverAPIURL,
//                Response.Listener { response ->
//                    Log.e(TAG, "response: " + response)
//                    dialog?.dismiss()
//
//                    // Handle Server response here
//                    try {
//                        val responseObj = JSONObject(response)
//                        val isSuccess = responseObj.getBoolean("isSuccess")
//                        val code = responseObj.getInt("code")
//                        val message = responseObj.getString("message")
//                        if (responseObj.has("data")) {
//                            val data = responseObj.getJSONObject("data")
//                            // Handle your server response data here
//                        }
//                        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
//
//                    } catch (e: Exception) { // caught while parsing the response
//                        Log.e(TAG, "problem occurred")
//                        e.printStackTrace()
//                    }
//                },
//                Response.ErrorListener { volleyError -> // error occurred
//                    Toast.makeText(this,"insertion error",Toast.LENGTH_LONG).show()
//                    Log.e(TAG, "problem occurred, volley error: " + volleyError.message)
//                }) {
//
//                override fun getParams(): MutableMap<String, String> {
//                    return parameters;
//                }
//
//                @Throws(AuthFailureError::class)
//                override fun getHeaders(): Map<String, String> {
//                    val headers: MutableMap<String, String> = HashMap()
//                    // Add your Header paramters here
//                    headers.put("firstName",firstName)
//                    headers.put("email",email)
//                    headers.put("password",password)
//                    headers.put("hostel", hostel)
//
//                    return headers
//                }
//            }
//            // Adding request to request queue
//            volleyRequestQueue?.add(strReq)

    /**fun postVolley() {
    val queue = Volley.newRequestQueue(this)
    val url = "http://10.1.164.225:5000/test"

    val requestBody = "id=1" + "&msg=test_msg"
    val stringReq : StringRequest =
    object : StringRequest(Method.POST, url,
    Response.Listener { response ->
    // response
    var strResp = response.toString()
    Log.d("API", strResp)
    },
    Response.ErrorListener { error ->
    Log.d("API", "error => $error")
    }
    ){
    override fun getBody(): ByteArray {
    return requestBody.toByteArray(Charset.defaultCharset())
    }
    }
    queue.add(stringReq)
    }*/
}