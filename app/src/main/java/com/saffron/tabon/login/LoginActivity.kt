package com.saffron.tabon.login

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.*
import com.saffron.tabon.R
import com.saffron.tabon.activity.HomeScreenActivity

class LoginActivity : Activity() {

    internal lateinit var login: Button
    internal lateinit var facebook: Button
    internal lateinit var google: Button
    internal lateinit var forgot: TextView
    internal lateinit var signup: TextView
    private var username: EditText? = null
    private var password: EditText? = null
    private var remember: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.user_name)
        password = findViewById(R.id.pass_word)
        remember = findViewById(R.id.check)
        login = findViewById(R.id.log_in)
        facebook = findViewById(R.id.facebook_login)
        google = findViewById(R.id.google_login)
        forgot = findViewById(R.id.for_got)
        signup = findViewById(R.id.sign_up)

        password!!.typeface = Typeface.DEFAULT
        password!!.transformationMethod = PasswordTransformationMethod()

        signup.setOnClickListener { v ->
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }


        forgot.setOnClickListener { v ->
            val intent = Intent(this@LoginActivity, ForgotPassword::class.java)
            startActivity(intent)
        }


        login.setOnClickListener { v ->
            val user = username!!.text.toString()
            val pass = password!!.text.toString()

            if (user.isEmpty()) {
                if (pass.isEmpty()) {

                    if (!remember!!.isChecked) {
                        username!!.setText("")
                        password!!.setText("")
                    }

                    val intent = Intent(this@LoginActivity, HomeScreenActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@LoginActivity, "Enter Password", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@LoginActivity, "Enter User name", Toast.LENGTH_LONG).show()
            }

        }

    }
}
