package com.example.messmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class SignupActivity : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var name:EditText
    lateinit var pass:EditText
    lateinit var hostel:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        email = findViewById(R.id.inputEmail)
        name = findViewById(R.id.inputName)
        pass = findViewById(R.id.inputPassword)
        hostel = findViewById(R.id.inputHostel)

        buttonSignup.setOnClickListener {
            var dataBaseTest:DataBaseTest = DataBaseTest()
            dataBaseTest.insertRecord(email.text.toString(), name.text.toString(), pass.text.toString(), hostel.text.toString().toInt())

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        AlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}