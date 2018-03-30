package com.saffron.tabon.contacts.contactpicker.views

import android.view.View
import android.widget.TextView

import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import com.saffron.tabon.R


/**
 * Created by Carlos Reyna on 22/01/17.
 */

class CommunicationViewHolder(view: View) : ChildViewHolder<View>(view) {

    var ivCommunicationIcon: RoundedImageView
    var tvCommunication: TextView

    init {
        ivCommunicationIcon = view.findViewById(R.id.ivCommunicationIcon)
        tvCommunication = view.findViewById(R.id.tvCommunication)
    }
}
