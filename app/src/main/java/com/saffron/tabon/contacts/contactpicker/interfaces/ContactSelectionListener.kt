package com.saffron.tabon.contacts.contactpicker.interfaces


import com.saffron.tabon.contacts.contactpicker.Contact

/**
 * Created by Carlos Reyna on 21/01/17.
 */

interface ContactSelectionListener {
    fun onContactSelected(contact: Contact, communication: String)

    fun onContactDeselected(contact: Contact, communication: String)
}
