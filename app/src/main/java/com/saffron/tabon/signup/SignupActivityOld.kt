package com.saffron.tabon.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

import com.saffron.tabon.R


class SignupActivityOld : Activity() {

    internal lateinit var tb: ImageButton
    internal lateinit var next_one: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_signup_old)

        tb = findViewById(R.id.back)
        next_one = findViewById(R.id.next)
        next_one.setOnClickListener { v -> startActivity(Intent(this@SignupActivityOld, EmailActivity::class.java)) }

        tb.setOnClickListener { v -> finish() }
    }
}
