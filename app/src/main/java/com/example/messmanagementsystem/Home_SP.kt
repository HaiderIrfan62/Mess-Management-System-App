package com.example.messmanagementsystem

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home_sp.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject

class Home_SP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_sp)

            var volleyRequestQueue: RequestQueue? = null
            var dialog: ProgressDialog? = null
            var serverAPIURL: String = "http://10.1.164.225/bill"
            val TAG = "Home"

            fun BillToServer(
                email: String,
            ) {
                volleyRequestQueue = Volley.newRequestQueue(this)
                //dialog = ProgressDialog.show(this, "", "Please wait...", true);
                val parameters: MutableMap<String, String> = HashMap()
                // Add your parameters in HashMap
                parameters.put("email", email)

                val gson = Gson()
                val jsonBody = JSONObject(gson.toJson(parameters))

                val mQueue = Volley.newRequestQueue(applicationContext)

                val jsonObjectRequest: JsonObjectRequest =
                    object : JsonObjectRequest(serverAPIURL, jsonBody,
                        Response.Listener { response ->
                            Log.e(TAG, "response: $response")
                            val responseObj = JSONObject(response.toString())
                            val bill_id: Int= responseObj.getInt("bill_id")
                            val bill = responseObj.getInt("bill")
                            val meals = responseObj.getInt("Meals")
                            val drinks = responseObj.getInt("Drinks")
                            if(bill_id != null){
                                val intent = Intent(this, BillActivity::class.java).also{
                                    val bundle = Bundle()

                                    bundle.putString("email", email)
                                    bundle.putInt("bill_id", bill_id)
                                    bundle.putInt("bill", bill)
                                    bundle.putInt("meals", meals)
                                    bundle.putInt("drinks", drinks)
                                    it.putExtras(bundle)
                                    startActivity(it)
                                }
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

        volleyRequestQueue = null
        dialog = null
        serverAPIURL = "http://10.1.166.96/mess_status"


        fun mess_status(
            email: String,
        ) {
            volleyRequestQueue = Volley.newRequestQueue(this)
            //dialog = ProgressDialog.show(this, "", "Please wait...", true);
            val parameters: MutableMap<String, String> = HashMap()
            // Add your parameters in HashMap
            parameters.put("email", email)

            val gson = Gson()
            val jsonBody = JSONObject(gson.toJson(parameters))

            val mQueue = Volley.newRequestQueue(applicationContext)

            val jsonObjectRequest: JsonObjectRequest =
                object : JsonObjectRequest(serverAPIURL, jsonBody,
                    Response.Listener { response ->
                        Log.e(TAG, "response: $response")
                        val responseObj = JSONObject(response.toString())
                        val stat: String = responseObj.getString(("stat"))
                        val isSuccess: Boolean= responseObj.getBoolean("isSuccess")
                        val code = responseObj.getInt("code")
                        if(isSuccess == true){
                            Toast.makeText(this, stat, Toast.LENGTH_LONG).show()
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


        btn_scan.setOnClickListener {
            val email = intent.getStringExtra("email")
            val intent = Intent(this, MainQRScanner::class.java).also{
                it.putExtra("email", email)
                startActivity(it)
            }
        }

        button3.setOnClickListener {
            val email = intent.getStringExtra("email")
            BillToServer(email.toString())
        }

        button2.setOnClickListener{
            val email = intent.getStringExtra("email")
            mess_status(email.toString())
        }

        /**button4.setOnClickListener {
            val intent = Intent(this, VoteActivity::class.java)
            startActivity(intent)
        }*/

        button5.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        /**button6.setOnClickListener {
            val email = intent.getStringExtra("email")
            val intent = Intent(this, ComplainActivity::class.java).also{
                it.putExtra("email", email)
                startActivity(it)
            }
        }*/
    }
}