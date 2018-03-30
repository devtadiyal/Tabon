package com.saffron.tabon.contacts.contactpicker.ui

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.saffron.tabon.R
import com.saffron.tabon.contacts.contactpicker.PickerUtils


class ContactPickerActivity : AppCompatActivity() {
    private var mFragment: ContactPickerFragment? = null

    var isShowChips: Boolean = false
        private set
    private var hasCustomArgs: Boolean = false
    var projection: Array<String>? = null
        private set
    var selectArgs: Array<String>? = null
        private set
    var select: String? = null
        private set
    var sortBy: String? = null
        private set
    var selectedColor: String? = null
        private set
    var selectedDrawable: ByteArray? = null
        private set
    private var fabDrawable: ByteArray? = null
    private var fabColor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_picker)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState != null)
            mFragment = supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_KEY) as ContactPickerFragment
        else
            mFragment = supportFragmentManager.findFragmentById(R.id.fragment) as ContactPickerFragment

        readExtras()
        setFAB()
    }


    private fun readExtras() {
        val intent = intent
        if (intent != null) {
            this.isShowChips = intent.getBooleanExtra(CP_EXTRA_SHOW_CHIPS, true)
            this.hasCustomArgs = intent.getBooleanExtra(CP_EXTRA_HAS_CUSTOM_SELECTION_ARGS, false)
            this.projection = intent.getStringArrayExtra(CP_EXTRA_PROJECTION)
            this.select = intent.getStringExtra(CP_EXTRA_SELECTION)
            this.selectArgs = intent.getStringArrayExtra(CP_EXTRA_SELECTION_ARGS)
            this.sortBy = intent.getStringExtra(CP_EXTRA_SORT_BY)
            this.selectedColor = intent.getStringExtra(CP_EXTRA_SELECTION_COLOR)
            this.fabColor = intent.getStringExtra(CP_EXTRA_FAB_COLOR)
            this.fabDrawable = intent.getByteArrayExtra(CP_EXTRA_FAB_DRAWABLE)
            this.selectedDrawable = intent.getByteArrayExtra(CP_EXTRA_SELECTION_DRAWABLE)
            cleanIfNeeded()
        }
    }

    private fun cleanIfNeeded() {
        if (this.projection == null)
            this.projection = CP_DEFAULT_PROJECTION
        if (this.select == null)
            this.select = CP_DEFAULT_SELECTION
        if (this.selectArgs == null && !hasCustomArgs)
            this.selectArgs = CP_DEFAULT_SELECTION_ARGS
        if (this.sortBy == null)
            this.sortBy = CP_DEFAULT_SORT_BY
    }

    private fun setFAB() {
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        if (fabColor != null)
            fab.backgroundTintList = ColorStateList.valueOf(Color.parseColor(fabColor))
        if (fabDrawable != null) {
            val customFabIcon = PickerUtils.extractDrawable(fabDrawable!!)
            if (customFabIcon != null)
                fab.setImageBitmap(customFabIcon)
        }

        fab.setOnClickListener { view ->
            val selected = mFragment!!.selected
            val result = Intent()
            result.putExtra(CP_SELECTED_CONTACTS, selected)
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val fragmentManager = supportFragmentManager
        fragmentManager?.putFragment(outState, FRAGMENT_KEY, mFragment)
    }

    companion object {

        val CP_SELECTED_CONTACTS = "CP_SELECTED_CONTACTS"
        val CP_EXTRA_SHOW_CHIPS = "CP_EXTRA_SHOW_CHIPS"
        val CP_EXTRA_PROJECTION = "CP_EXTRA_PROJECTION"
        val CP_EXTRA_SELECTION = "CP_EXTRA_SELECTION"
        val CP_EXTRA_SELECTION_ARGS = "CP_EXTRA_SELECTION_ARGS"
        val CP_EXTRA_SORT_BY = "CP_EXTRA_SORT_BY"
        val CP_EXTRA_HAS_CUSTOM_SELECTION_ARGS = "CP_EXTRA_HAS_CUSTOM_SELECTION_ARGS"
        val CP_EXTRA_SELECTION_COLOR = "CP_EXTRA_SELECTION_COLOR"
        val CP_EXTRA_SELECTION_DRAWABLE = "CP_EXTRA_SELECTION_DRAWABLE"
        val CP_EXTRA_FAB_DRAWABLE = "CP_EXTRA_DAB_DRAWABLE"
        val CP_EXTRA_FAB_COLOR = "CP_EXTRA_FAB_COLOR"
        val CP_DEFAULT_PROJECTION = arrayOf(ContactsContract.Data._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Data.MIMETYPE, ContactsContract.Contacts.PHOTO_URI)
        val CP_DEFAULT_SELECTION = "(" + ContactsContract.Data.MIMETYPE + "=? OR " + ContactsContract.Data.MIMETYPE + "=?)"
        val CP_DEFAULT_SORT_BY = ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        val CP_DEFAULT_SELECTION_ARGS = arrayOf(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
        private val FRAGMENT_KEY = "CP_FRAG_KEY"
    }
}
