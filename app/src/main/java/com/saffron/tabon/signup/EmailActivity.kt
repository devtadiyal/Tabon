package com.saffron.tabon.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

import com.saffron.tabon.R


class EmailActivity : Activity() {

    internal lateinit var next_two: Button
    internal lateinit var tb: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        tb = findViewById(R.id.back2)
        next_two = findViewById(R.id.next2)
        next_two.setOnClickListener { v -> startActivity(Intent(this@EmailActivity, NumberActivity::class.java)) }
        tb.setOnClickListener { v -> finish() }
    }
}
