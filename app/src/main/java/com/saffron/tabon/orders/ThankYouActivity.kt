package com.saffron.tabon.orders

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.saffron.tabon.R
import com.saffron.tabon.contacts.contactpicker.ui.ContactPickerActivity

class ThankYouActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)
    }

    fun showContacts(view: View) {

        launchContactPicker()
    }

    fun launchContactPicker() {

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val contactPicker = Intent(this, ContactPickerActivity::class.java)
            startActivityForResult(contactPicker, CONTACT_PICKER_REQUEST)
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    READ_CONTACT_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_CONTACT_REQUEST -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchContactPicker()
            }
        }
    }

    companion object {


        private val READ_CONTACT_REQUEST = 1
        private val CONTACT_PICKER_REQUEST = 2
    }

}
