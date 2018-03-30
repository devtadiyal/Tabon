package com.saffron.tabon.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

import com.saffron.tabon.R


class NumberActivity : Activity() {

    internal lateinit var tb: ImageButton
    internal lateinit var next_two: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)


        tb = findViewById(R.id.back4)
        next_two = findViewById(R.id.next4)
        next_two.setOnClickListener { v -> startActivity(Intent(this@NumberActivity, PasswordActivity::class.java)) }

        tb.setOnClickListener { v -> finish() }

    }
}
