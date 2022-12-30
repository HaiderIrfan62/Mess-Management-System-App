package com.example.messmanagementsystem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bill.*

class BillActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)
        val bundle = intent.extras
        val email = bundle!!.getString("email")
        val bill_id = bundle!!.getInt("bill_id")
        val bill = bundle!!.getInt("bill")
        val meals = bundle!!.getInt("meals")
        val drinks = bundle!!.getInt("drinks")

        textView2.text = email.toString()
        textView4.text = bill_id.toString()
        textView6.text = bill.toString()
        textView8.text = meals.toString()
        textView10.text = drinks.toString()
    }
}