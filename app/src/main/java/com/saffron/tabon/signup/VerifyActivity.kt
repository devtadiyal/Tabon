package com.saffron.tabon.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

import com.saffron.tabon.R


class VerifyActivity : Activity() {
    internal lateinit var next: Button
    internal lateinit var tb: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup2_layout)

        next = findViewById(R.id.tnc)
        tb = findViewById(R.id.back5)

        tb.setOnClickListener { v -> finish() }

        next.setOnClickListener { v -> startActivity(Intent(this@VerifyActivity, PasswordActivity::class.java)) }
    }
}
