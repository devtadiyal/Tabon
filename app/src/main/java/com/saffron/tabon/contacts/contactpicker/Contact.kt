package com.saffron.tabon.contacts.contactpicker

import com.bignerdranch.expandablerecyclerview.model.Parent
import java.io.Serializable
import java.util.*

/**
 * Created by Carlos Reyna on 20/01/17.
 */

class Contact : Parent<String>, Serializable {

    /**** Getters  */
    /**** Setters */
    var displayName: String? = null
    var isSelected: Boolean = false
    var communications: ArrayList<String>? = null
    var selectedCommunication: String? = null

    val initial: String
        get() = this.displayName!![0].toString()


    val defaultCommunication: String
        get() = if (communications!!.size > 0) communications!![0] else "Not found"


    val totalCommunications: Int
        get() = communications!!.size


    /**** Constructors  */
    constructor(displayName: String) {
        this.displayName = displayName
        setCommunications(ArrayList())


    }
    constructor(displayName: String, communications: ArrayList<String>) {
        this.displayName = displayName
        setCommunications(communications)
    }

    fun getCommunications(): List<String>? {
        return communications
    }

    fun setCommunications(communications: List<String>?) {
        var communications = communications
        if (communications == null)
            communications = ArrayList()
        this.communications = communications as ArrayList<String>?
    }

    fun addCommunication(communication: String) {
        var communication = communication
        communication = communication.replace(" ".toRegex(), "")
        if (!communications!!.contains(communication)) {
            communications!!.add(communication)
        }
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is Contact) {
            val contact = obj as Contact?
            return this.displayName == contact!!.displayName
        }
        return false
    }

    override fun toString(): String {
        return "Contact{" +
                "displayName='" + displayName + '\''.toString() +
                ", isSelected=" + isSelected +
                ", communications=" + communications +
                ", selectedCommunication='" + selectedCommunication + '\''.toString() +
                '}'.toString()
    }

    override fun getChildList(): List<String> {
        return if (communications!!.size > 1) communications!! else ArrayList()
    }

    override fun isInitiallyExpanded(): Boolean {
        return false
    }

    fun simplify(): SimpleContact {
        return SimpleContact(displayName, selectedCommunication)
    }
}
