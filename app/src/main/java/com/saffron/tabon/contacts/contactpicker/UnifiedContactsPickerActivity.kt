package com.saffron.tabon.contacts.contactpicker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.saffron.tabon.R
import com.saffron.tabon.contacts.contactpicker.adapters.ContactAdapter
import com.saffron.tabon.contacts.contactpicker.interfaces.ContactSelectionListener
import com.saffron.tabon.contacts.contactpicker.ui.ContactPickerActivity
import java.util.*


class UnifiedContactsPickerActivity : AppCompatActivity(), ContactSelectionListener {

    private val mContacts = ArrayList<Contact>()
    private var mRecyclerView: RecyclerView? = null
    private var mTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unified_contacts)
        mRecyclerView = findViewById(R.id.recyclerView)
        mTextView = findViewById(R.id.textView)
    }

    fun launchContactPicker(view: View?) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CONTACT_PICKER_REQUEST ->
                //contacts were selected
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        val selectedContacts = data.getSerializableExtra(ContactPickerActivity.CP_SELECTED_CONTACTS) as TreeSet<SimpleContact>
                        if (selectedContacts != null) {
                            for (selectedContact in selectedContacts) {
                                val list = ArrayList<String>()
                                list.add(selectedContact.communication!!)
                                mContacts.add(Contact(selectedContact.displayName!!, list))
                            }
                        }
                    }
                    setRecyclerView()
                }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setRecyclerView() {
        val adapter = ContactAdapter(this, mContacts, this, null, null)
        mRecyclerView!!.adapter = adapter
        mRecyclerView!!.itemAnimator = DefaultItemAnimator()
        mRecyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        toggleViews()
    }

    private fun toggleViews() {
        mTextView!!.text = "Contacts selected: "
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_CONTACT_REQUEST -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchContactPicker(null)
            }
        }
    }

    override fun onContactSelected(contact: Contact, communication: String) {

    }

    override fun onContactDeselected(contact: Contact, communication: String) {

    }

    companion object {

        private val READ_CONTACT_REQUEST = 1
        private val CONTACT_PICKER_REQUEST = 2
    }
}
