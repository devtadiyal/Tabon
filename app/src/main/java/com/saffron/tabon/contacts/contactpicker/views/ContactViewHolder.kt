package com.saffron.tabon.contacts.contactpicker.views


import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.bignerdranch.expandablerecyclerview.model.Parent
import com.github.ivbaranov.mli.MaterialLetterIcon
import com.saffron.tabon.R


/**
 * Created by Carlos Reyna on 22/01/17.
 */

class ContactViewHolder(view: View) : ParentViewHolder<Parent<View>, View>(view) {

    var letterIcon: MaterialLetterIcon
    var tvDisplayName: TextView
    var tvCommunication: TextView
    var ivSelected: ImageView
    var ivSelectedCommunication: ImageView
    var ivExpandArrow: ImageView
    var expandableArea: View

    init {
        letterIcon = view.findViewById(R.id.letterIcon)
        ivSelected = view.findViewById(R.id.cp_ivSelected)
        ivSelectedCommunication = view.findViewById(R.id.cp_ivSelectedComm)
        ivExpandArrow = view.findViewById(R.id.cp_arrowExpand)
        tvDisplayName = view.findViewById(R.id.tvDisplayName)
        tvCommunication = view.findViewById(R.id.tvCommunication)
        expandableArea = view.findViewById(R.id.cp_clickArea)
    }

    override fun shouldItemViewClickToggleExpansion(): Boolean {
        return false
    }
}
