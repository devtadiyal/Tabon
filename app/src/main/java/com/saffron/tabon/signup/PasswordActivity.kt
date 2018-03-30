package com.saffron.tabon.signup

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

import com.saffron.tabon.R
import com.saffron.tabon.activity.HomeScreenActivity

class PasswordActivity : Activity() {

    internal lateinit var next_two: Button
    internal lateinit var tb: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        tb = findViewById(R.id.back3)

        val loginPasssword = findViewById<EditText>(R.id.editText)
        loginPasssword.typeface = Typeface.DEFAULT
        loginPasssword.transformationMethod = PasswordTransformationMethod()

        val confirmPasssword = findViewById<EditText>(R.id.editText2)
        confirmPasssword.typeface = Typeface.DEFAULT
        confirmPasssword.transformationMethod = PasswordTransformationMethod()

        next_two = findViewById(R.id.next3)
        next_two.setOnClickListener { v -> startActivity(Intent(this@PasswordActivity, HomeScreenActivity::class.java)) }

        tb.setOnClickListener { v -> finish() }
    }
}
