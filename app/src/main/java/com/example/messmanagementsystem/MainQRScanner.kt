package com.example.messmanagementsystem

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.gson.Gson
import org.json.JSONObject


class MainQRScanner : AppCompatActivity() {

    private lateinit var codescanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_qrscanner)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
             PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
        }
        else{
            startScanning()
        }
    }

    var volleyRequestQueue: RequestQueue? = null
    var dialog: ProgressDialog? = null
    val serverAPIURL: String = "http://192.168.1.74/qrcode"
    val TAG = "Sign in"

    fun updateBill(
        email: String
    ) {
        volleyRequestQueue = Volley.newRequestQueue(this)
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
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
                    val isSuccess: Boolean= responseObj.getBoolean("isSuccess")
                    val code = responseObj.getInt("code")

                    if(isSuccess.toString() == "true"){
                        val intent = Intent(this, Home_SP::class.java).also{
                            it.putExtra("email", email)
                            startActivity(it)
                        }
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

    private fun startScanning() {
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)
        codescanner = CodeScanner(this, scannerView)
        codescanner.camera = CodeScanner.CAMERA_BACK
        codescanner.formats = CodeScanner.ALL_FORMATS

        codescanner.autoFocusMode = AutoFocusMode.SAFE
        codescanner.scanMode = ScanMode.SINGLE
        codescanner.isAutoFocusEnabled = true
        codescanner.isFlashEnabled = false

        codescanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                if(it.text == "sys"){
                    val email = intent.getStringExtra("email")
                    updateBill(email.toString())
                    Toast.makeText(this, "Matched", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Not Matched", Toast.LENGTH_SHORT).show()
                }
            }
        }

        codescanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        scannerView.setOnClickListener {
            codescanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera persission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            }
            else{
                Toast.makeText(this, "Camera persission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(::codescanner.isInitialized){
            codescanner?.startPreview()
        }
    }

    override fun onPause() {
        if(::codescanner.isInitialized){
            codescanner?.releaseResources()
        }

        super.onPause()
    }
}