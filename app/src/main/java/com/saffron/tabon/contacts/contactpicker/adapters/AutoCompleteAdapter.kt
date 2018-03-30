package com.saffron.tabon.contacts.contactpicker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.github.ivbaranov.mli.MaterialLetterIcon
import com.saffron.tabon.R
import com.saffron.tabon.contacts.contactpicker.Contact
import java.util.*

/**
 * Created by Carlos Reyna on 21/01/17.
 */

class AutoCompleteAdapter(context: Context?, private val mResourceId: Int, private val mContacts: List<Contact>) : ArrayAdapter<Contact>(context, mResourceId, mContacts), Filterable {
    private val mMaterialColors: IntArray
    private val tempContacts: List<Contact>
    private val suggestions: MutableList<Contact>
    private val mFilter = ContactFilter()

    init {
        this.mMaterialColors = context!!.resources.getIntArray(R.array.colors)
        this.tempContacts = ArrayList(mContacts)
        this.suggestions = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(mResourceId, null)
        }

        val contact = getItem(position)
        if (contact != null) {
            val tvDisplayName = view!!.findViewById<TextView>(R.id.tvDisplayName)
            val tvCommunication = view.findViewById<TextView>(R.id.tvCommunication)
            val letterIcon = view.findViewById<MaterialLetterIcon>(R.id.letterIcon)

            if (tvDisplayName != null)
                tvDisplayName.text = contact.displayName
            if (tvCommunication != null)
                tvCommunication.text = contact.defaultCommunication
            if (letterIcon != null) {
                letterIcon.letter = contact.initial
                letterIcon.shapeColor = mMaterialColors[position % mMaterialColors.size]

            }
        }

        return view!!
    }

    override fun getFilter(): Filter {
        return mFilter
    }

    private inner class ContactFilter : Filter() {

        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as Contact).defaultCommunication
        }

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {

            if (constraint != null) {
                suggestions.clear()
                val query = constraint.toString().toLowerCase()
                for (contact in tempContacts) {
                    if (contact.displayName!!.toLowerCase().contains(query) || contact.selectedCommunication!!.contains(query))
                        suggestions.add(contact)
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                return filterResults
            } else {
                return Filter.FilterResults()
            }
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {

            val filterList = results.values as ArrayList<Contact>
            if (results.count > 0) {
                clear()
                for (contact in filterList) {
                    add(contact)
                    notifyDataSetChanged()
                }
            }

        }
    }

}
