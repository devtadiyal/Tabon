package com.saffron.tabon.contacts.contactpicker.ui

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MultiAutoCompleteTextView
import com.hootsuite.nachos.NachoTextView
import com.hootsuite.nachos.chip.Chip
import com.hootsuite.nachos.chip.ChipSpan
import com.hootsuite.nachos.terminator.ChipTerminatorHandler
import com.hootsuite.nachos.tokenizer.SpanChipTokenizer
import com.saffron.tabon.R
import com.saffron.tabon.contacts.contactpicker.Contact
import com.saffron.tabon.contacts.contactpicker.ContactChipCreator
import com.saffron.tabon.contacts.contactpicker.SimpleContact
import com.saffron.tabon.contacts.contactpicker.adapters.AutoCompleteAdapter
import com.saffron.tabon.contacts.contactpicker.adapters.ContactAdapter
import com.saffron.tabon.contacts.contactpicker.interfaces.ContactSelectionListener
import com.saffron.tabon.contacts.contactpicker.views.CPLinearLayoutManager
import java.util.*


/**
 * A placeholder fragment containing a simple view.
 */
class ContactPickerFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>, ContactSelectionListener {
    private val mSuggestions = ArrayList<Contact>()
    private var projection: Array<String>? = null
    private var selection: String? = null
    private var selectionArgs: Array<String>? = null
    private var sortBy: String? = null
    private var mRecyclerView: RecyclerView? = null
    private var mNachoTextView: NachoTextView? = null
    private var mContactAdapter: ContactAdapter? = null
    private var mContacts = ArrayList<Contact>()
    private var mActivity: ContactPickerActivity? = null

    /*** Public methods  */

    val selected: TreeSet<SimpleContact>
        get() {
            val toReturn = TreeSet<SimpleContact>()
            if (this.mNachoTextView != null) {
                val chips = HashSet<SimpleContact>()

                for (s in this.mNachoTextView!!.chipValues) {
                    chips.add(SimpleContact(s, s))
                }

                val selected = this.mContactAdapter!!.selection
                for (simpleContact in chips) {
                    if (selected.contains(simpleContact))
                        toReturn.add(selected[selected.indexOf(simpleContact)])
                    else
                        toReturn.add(simpleContact)
                }
            }
            return toReturn
        }

    /*** Fragment callbacks  */

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            val restored = savedInstanceState.getSerializable(SELECTION_SAVE) as ArrayList<Contact>
            if (restored != null)
                mContacts = restored
        }

        //Start contact cursor query in background
        mActivity = activity as ContactPickerActivity?
        init()
        mActivity!!.supportLoaderManager.initLoader(CONTACT_LOADER_ID, Bundle(), this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mRecyclerView = view.findViewById(R.id.cp_listView)
        this.mNachoTextView = view.findViewById(R.id.nachoTextView)

        this.mNachoTextView!!.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        this.mNachoTextView!!.threshold = 3
        this.mNachoTextView!!.maxLines = 2
        this.mNachoTextView!!.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR)
        this.mNachoTextView!!.chipTokenizer = SpanChipTokenizer(context, ContactChipCreator(), ChipSpan::class.java!!)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mContactAdapter != null)
            outState.putSerializable(SELECTION_SAVE, mContacts)
        else
            Log.e(TAG, "onSaveInstanceState: adapter is null")
    }

    /*** Loader callbacks  */

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

        return CursorLoader(context!!,
                ContactsContract.Data.CONTENT_URI, // URI
                projection, // projection fields
                selection, // the selection criteria
                selectionArgs, // the selection args
                sortBy // the sort order
        )
    }

    /**
     * When the system finishes retrieving the Cursor through the CursorLoader,
     * a call to the onLoadFinished() method takes place.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        if (cursor.moveToFirst()) {
            do {
                val displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val communication = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val newContact = Contact(displayName)

                val suggestion = Contact(displayName)
                suggestion.addCommunication(communication)
                mSuggestions.add(suggestion)

                if (!mContacts.contains(newContact)) {
                    newContact.addCommunication(communication)
                    mContacts.add(newContact)
                } else {
                    val existingContact = mContacts[mContacts.indexOf(newContact)]
                    existingContact.addCommunication(communication)
                }

            } while (cursor.moveToNext())
        }
        if (mContactAdapter != null) {
            mContactAdapter!!.notifyDataSetChanged()
        }

        setRecyclerView()
        val adapter = AutoCompleteAdapter(context, R.layout.cp_suggestion_row, mSuggestions)
        this.mNachoTextView!!.setAdapter(adapter)
    }

    /**
     * This method is triggered when the loader is being reset
     * and the loader data is no longer available. Called if the data
     * in the provider changes and the Cursor becomes stale.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {}

    /*** Contact selection callbacks  */

    /**
     * Called whenever a contact is selected from the `mRecyclerView`
     * This callback is NOT triggered when a `Chip` is added to `mNachoTextView`
     *
     * @param contact       Selected contact
     * @param communication Selected contact communication
     */
    override fun onContactSelected(contact: Contact, communication: String) {
        addChip(communication)
    }

    /**
     * Called whenever a contact is unselected from the `mRecyclerView`
     * This callback is NOT triggered when a `Chip` is added to `mNachoTextView`
     *
     * @param contact       Unselected contact
     * @param communication Unelected contact communication
     */
    override fun onContactDeselected(contact: Contact, communication: String) {
        var toRemove: Chip? = null
        for (chip in mNachoTextView!!.allChips) {
            if (chip.text == communication)
                toRemove = chip
        }

        removeChip(toRemove)
    }

    /*** Private methods  */


    private fun setRecyclerView() {
        val selectedHex = mActivity!!.selectedColor
        val selectedDrawable = mActivity!!.selectedDrawable
        this.mContactAdapter = ContactAdapter(context, mContacts, this, selectedHex, selectedDrawable)
        mRecyclerView!!.swapAdapter(mContactAdapter, true)
        mRecyclerView!!.itemAnimator = DefaultItemAnimator()
        mRecyclerView!!.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
        mRecyclerView!!.layoutManager = CPLinearLayoutManager(context!!)
        mRecyclerView!!.stopScroll()
        mContactAdapter!!.notifyDataSetChanged()
    }

    private fun addChip(communication: String) {
        if (mNachoTextView != null) {
            mNachoTextView!!.append(communication)
            val start = mNachoTextView!!.text.toString().indexOf(communication)
            val last = mNachoTextView!!.text.length
            mNachoTextView!!.chipify(start, last)
        } else
            Log.e(TAG, "mNachoTextView is null")
    }

    private fun removeChip(toRemove: Chip?) {
        if (toRemove != null && mNachoTextView!!.chipTokenizer != null) {
            mNachoTextView!!.chipTokenizer!!.deleteChip(toRemove, mNachoTextView!!.text)
        }
    }

    private fun init() {
        if (mActivity!!.isShowChips)
            this.mNachoTextView!!.visibility = View.VISIBLE
        else
            this.mNachoTextView!!.visibility = View.GONE


        this.projection = mActivity!!.projection
        this.selection = mActivity!!.select
        this.selectionArgs = mActivity!!.selectArgs
        this.sortBy = mActivity!!.sortBy
    }

    companion object {

        private val CONTACT_LOADER_ID = 666
        private val SELECTION_SAVE = "SELECTION_SAVE"
        private val TAG = ContactPickerFragment::class.java!!.getSimpleName()
    }

}
