package com.saffron.tabon.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

import com.saffron.tabon.R


class OTPActivity : Activity() {
    private var next: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)

        next = findViewById(R.id.continu)
        next!!.setOnClickListener { v ->
            val intent = Intent(this@OTPActivity, VerifyActivity::class.java)
            startActivity(intent)
        }
    }
}
