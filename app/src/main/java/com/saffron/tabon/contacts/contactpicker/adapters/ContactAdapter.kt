package com.saffron.tabon.contacts.contactpicker.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.saffron.tabon.R
import com.saffron.tabon.contacts.contactpicker.Contact
import com.saffron.tabon.contacts.contactpicker.PickerUtils
import com.saffron.tabon.contacts.contactpicker.SimpleContact
import com.saffron.tabon.contacts.contactpicker.interfaces.ContactSelectionListener
import com.saffron.tabon.contacts.contactpicker.views.CommunicationViewHolder
import com.saffron.tabon.contacts.contactpicker.views.ContactViewHolder
import java.util.*


/**
 * Created by Carlos Reyna on 20/01/17.
 */

class ContactAdapter(context: Context?, private val mContacts: List<Contact>, private val mListener: ContactSelectionListener, selectedIconHex: String?, selectedDrawable: ByteArray?) : ExpandableRecyclerAdapter<Contact, String, ContactViewHolder, CommunicationViewHolder>(mContacts) {
    private val selectedDrawable: Bitmap
    private val selectedColor: Int
    private val subtitleColor: Int
    private val selectedIconColor: Int
    private val mMaterialColors: IntArray
    private val mSelection: MutableList<Contact>

    val selection: ArrayList<SimpleContact>
        get() {
            val selected = ArrayList<SimpleContact>()
            for (contact in mSelection) {
                selected.add(contact.simplify())
            }
            return selected
        }

    init {
        this.mMaterialColors = context!!.resources.getIntArray(R.array.colors)
        this.mSelection = ArrayList()
        this.selectedColor = ResourcesCompat.getColor(context.resources, R.color.color_7, null)
        this.subtitleColor = ResourcesCompat.getColor(context.resources, R.color.subtitle, null)

        if (selectedIconHex == null)
            this.selectedIconColor = ResourcesCompat.getColor(context.resources, R.color.materialGreen, null)
        else
            this.selectedIconColor = Color.parseColor(selectedIconHex)

        if (selectedDrawable != null)
            this.selectedDrawable = PickerUtils.extractDrawable(selectedDrawable)
        else
            this.selectedDrawable = PickerUtils.extractDrawable(PickerUtils.sendDrawable(context.resources, R.drawable.ic_done))
    }


    override fun onCreateParentViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cp_contact_row, parent, false)
        return ContactViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): CommunicationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cp_communication_row, parent, false)
        val viewHolder = CommunicationViewHolder(view)
        viewHolder.itemView.setOnClickListener { v ->
            val childPosition = viewHolder.childAdapterPosition
            val parentPosition = viewHolder.parentAdapterPosition
            handleChildSelection(parentPosition, childPosition)
            collapseParent(parentPosition)
        }
        return viewHolder
    }

    override fun onBindParentViewHolder(parentViewHolder: ContactViewHolder, parentPosition: Int, parent: Contact) {
        bindParent(parentViewHolder, parentPosition)
    }

    override fun onBindChildViewHolder(childViewHolder: CommunicationViewHolder, parentPosition: Int, childPosition: Int, child: String) {
        bindCommunicationToViewHolder(childViewHolder, child)
    }

    private fun bindCommunicationToViewHolder(childViewHolder: CommunicationViewHolder, child: String) {
        childViewHolder.tvCommunication.text = child
        if (PickerUtils.isEmail(child))
            childViewHolder.ivCommunicationIcon.setImageResource(R.drawable.ic_email)
        else
            childViewHolder.ivCommunicationIcon.setImageResource(R.drawable.ic_message)
    }

    @SuppressLint("DefaultLocale")
    private fun bindParent(parentViewHolder: ContactViewHolder, position: Int) {

        val contact = mContacts[position]
        if (contact != null) {

            val communication = contact.selectedCommunication

            parentViewHolder.ivSelected.setBackgroundColor(this.selectedIconColor)
            parentViewHolder.ivSelected.setImageBitmap(selectedDrawable)
            parentViewHolder.letterIcon.letter = contact.initial
            parentViewHolder.letterIcon.shapeColor = mMaterialColors[position % mMaterialColors.size]
            parentViewHolder.tvCommunication.text = communication
            parentViewHolder.tvDisplayName.text = contact.displayName

            if (PickerUtils.isEmail(communication))
                parentViewHolder.ivSelectedCommunication.setImageResource(R.drawable.ic_email)
            else
                parentViewHolder.ivSelectedCommunication.setImageResource(R.drawable.ic_message)

            if (contact.totalCommunications > 1) {
                parentViewHolder.ivExpandArrow.visibility = View.VISIBLE
                parentViewHolder.expandableArea.setOnClickListener { v -> expand(contact, parentViewHolder) }

                parentViewHolder.itemView.setOnClickListener { v ->
                    if (contact.isSelected)
                        unSelectContact(contact)
                    else
                        expand(contact, parentViewHolder)
                }
            } else {
                parentViewHolder.ivExpandArrow.visibility = View.INVISIBLE
                parentViewHolder.expandableArea.isClickable = false
                parentViewHolder.expandableArea.isFocusable = false
                parentViewHolder.itemView.setOnClickListener { v -> handleParentSelection(position) }
            }


            //Select item if previously selected
            selectView(contact, parentViewHolder)


        } else
            Log.e(TAG, "onBindViewHolder: contact null")
    }

    private fun expand(contact: Contact?, parentViewHolder: ContactViewHolder) {
        if (parentViewHolder.isExpanded)
            collapseParent(contact!!)
        else
            expandParent(contact!!)
    }


    private fun handleParentSelection(position: Int) {
        val contact = mContacts[position]
        val communication = contact.selectedCommunication
        selectCommunication(contact, position, communication!!)
    }

    private fun handleChildSelection(parentPosition: Int, childPosition: Int) {
        val contact = mContacts[parentPosition]
        val communication = contact.communications!![childPosition]
        selectCommunication(contact, parentPosition, communication)
    }

    private fun selectCommunication(contact: Contact, parentPosition: Int, communication: String) {
        if (mSelection.contains(contact)) {
            //existing contact
            if (contact.selectedCommunication == communication) {
                contact.isSelected = false
                mSelection.remove(contact)
                mListener.onContactDeselected(contact, communication)
            } else {
                contact.isSelected = true
                val prevSelected = contact.selectedCommunication
                mListener.onContactDeselected(contact, prevSelected!!)
                mListener.onContactSelected(contact, communication)
                contact.selectedCommunication = communication
            }
        } else {
            contact.isSelected = true
            contact.selectedCommunication = communication
            mSelection.add(contact)
            mListener.onContactSelected(contact, communication)
        }
        notifyParentChanged(parentPosition)
    }

    private fun unSelectContact(contact: Contact?) {

        if (mSelection.contains(contact)) {
            contact!!.isSelected = false
            mSelection.remove(contact)
            mListener.onContactDeselected(contact, contact.selectedCommunication!!)
            notifyParentChanged(mContacts.indexOf(contact))
        }

    }

    private fun selectView(contact: Contact, view: ContactViewHolder) {
        val ivSelected = view.ivSelected
        val letterIcon = view.letterIcon
        val tvDisplayName = view.tvDisplayName
        val tvCommunication = view.tvCommunication

        if (contact.isSelected) {
            letterIcon.visibility = View.GONE
            ivSelected.visibility = View.VISIBLE
            tvDisplayName.setTextColor(selectedColor)
            tvCommunication.setTextColor(selectedColor)
        } else {
            letterIcon.visibility = View.VISIBLE
            ivSelected.visibility = View.GONE
            tvDisplayName.setTextColor(Color.BLACK)
            tvCommunication.setTextColor(subtitleColor)
        }

    }

    override fun getItemCount(): Int {
        return mContacts.size
    }

    companion object {

        private val TAG = ContactAdapter::class.java!!.getSimpleName()
    }

}
