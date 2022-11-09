package com.example.messmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home_sp.*
import kotlinx.android.synthetic.main.activity_signup.*

class Home_SP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_sp)

        btn_scan.setOnClickListener {
            val intent = Intent(this, MainQRScanner::class.java)
            startActivity(intent)
        }
    }
}