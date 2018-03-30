package com.saffron.tabon.orders

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import com.saffron.tabon.R

class TabOnCodeGeneratorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab_on_code_progress)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val textViewMsg = findViewById<TextView>(R.id.progress_msg)
        val viewTabOnCode = findViewById<View>(R.id.tabOnCode)

        Handler().postDelayed({

            progressBar.visibility = View.GONE
            textViewMsg.visibility = View.GONE
            viewTabOnCode.visibility = View.VISIBLE
        }, 2000)
    }

    fun thankYou(view: View) {

        startActivity(Intent(this, ThankYouActivity::class.java))
    }
}
