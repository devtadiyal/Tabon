package com.saffron.tabon.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

import com.saffron.tabon.R
import com.saffron.tabon.activity.HomeScreenActivity


class SubmitActivity : Activity() {
    internal lateinit var next: Button
    internal lateinit var tb: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.submit_layout)

        next = findViewById(R.id.submit)
        tb = findViewById(R.id.back5)

        tb.setOnClickListener { v -> finish() }



        next.setOnClickListener { v -> startActivity(Intent(this@SubmitActivity, HomeScreenActivity::class.java)) }
    }
}
