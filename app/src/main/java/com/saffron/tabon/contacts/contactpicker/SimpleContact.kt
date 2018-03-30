package com.saffron.tabon.contacts.contactpicker

import java.io.Serializable

/**
 * Created by Carlos Reyna on 22/01/17.
 */

class SimpleContact(var displayName: String?, var communication: String?) : Comparable<SimpleContact>, Serializable {

    override fun equals(obj: Any?): Boolean {
        if (obj is SimpleContact) {
            val contact = obj as SimpleContact?
            return this.communication == contact!!.communication
        }
        return false
    }

    override fun toString(): String {
        return "SimpleContact{" +
                "displayName='" + displayName + '\''.toString() +
                ", communication='" + communication + '\''.toString() +
                '}'.toString()
    }


    override fun compareTo(other: SimpleContact): Int {
        return this.displayName!!.compareTo(other.displayName!!)
    }
}
