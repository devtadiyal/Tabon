package com.saffron.tabon.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView

import com.saffron.tabon.R
import com.saffron.tabon.signup.SignupActivityOld
import com.saffron.tabon.signup.SubmitActivity

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class SignUpActivity : Activity() {

    internal lateinit var facebook: Button
    internal lateinit var google: Button
    internal lateinit var login: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val relativeLayout = findViewById<RelativeLayout>(R.id.relativeLayout)
        login = findViewById(R.id.log_in)
        facebook = findViewById(R.id.facebook_login)
        google = findViewById(R.id.google_login)

        val signup = findViewById<TextView>(R.id.email_login)
        signup.setOnClickListener { v ->
            val intent = Intent(this@SignUpActivity, SignupActivityOld::class.java)
            startActivity(intent)
        }

        login.setOnClickListener { v ->

            /*Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);*/
            finish()
        }


        val buttonNext = findViewById<Button>(R.id.next)
        buttonNext.setOnClickListener { v ->

            val intent = Intent(this@SignUpActivity, SubmitActivity::class.java)
            startActivity(intent)
        }

        /*Glide.with(this).load(R.drawable.dark_bg).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout.setBackground(resource);
                }
            }
        });*/

    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
