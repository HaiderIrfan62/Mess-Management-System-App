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
import kotlinx.android.synthetic.main.activity_home_ap.*
import org.json.JSONObject

class Home_AP_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_ap)

        var volleyRequestQueue: RequestQueue? = null
        var dialog: ProgressDialog? = null
        var serverAPIURL: String = "http://10.1.166.96/admin_bill"
        val TAG = "Admin Portal"

        fun ABillToServer(
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
                        val bill = responseObj.getInt("bill")
                        bill_tv.text = bill.toString() + " Rs"

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

        buttonSearch.setOnClickListener {
            ABillToServer(inputEmailAP.text.toString())
        }
    }
}