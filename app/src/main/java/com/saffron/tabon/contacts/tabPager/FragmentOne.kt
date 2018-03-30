package com.saffron.tabon.contacts.tabPager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.saffron.tabon.R
import com.saffron.tabon.contacts.contactpicker.ui.ContactPickerActivity


/**
 * Created by day on 22/2/18.
 */

class FragmentOne : Fragment() {

    internal lateinit var searchView: SearchView
    internal var context: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_one, container, false)
        searchView = view.findViewById(R.id.search_place)
        searchView.setOnSearchClickListener { v -> launchContactPicker() }

        return view
    }

    fun launchContactPicker() {

        val permissionCheck = ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_CONTACTS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val contactPicker = Intent(context, ContactPickerActivity::class.java)
            startActivityForResult(contactPicker, CONTACT_PICKER_REQUEST)
        } else {
            ActivityCompat.requestPermissions((context as Activity?)!!,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    READ_CONTACT_REQUEST)
        }
        searchView.isIconified = true
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

        fun newInstance(): FragmentOne {

            val args = Bundle()
            val fragment = FragmentOne()
            fragment.arguments = args
            return fragment
        }
    }

}
