package com.example.messmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        TextViewSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            if(rb_student.isChecked == true) {
                val intent = Intent(this, Home_SP::class.java)
                startActivity(intent)
            }
            else if(rb_admin.isChecked == true){
                val intent = Intent(this, Home_AP_Activity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show()
            }
        }
    }
}