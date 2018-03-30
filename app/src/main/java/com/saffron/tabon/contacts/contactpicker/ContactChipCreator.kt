package com.saffron.tabon.contacts.contactpicker

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat

import com.hootsuite.nachos.chip.ChipSpan
import com.hootsuite.nachos.chip.ChipSpanChipCreator
import com.saffron.tabon.R

/**
 * Created by Carlos Reyna on 21/01/17.
 */

class ContactChipCreator : ChipSpanChipCreator() {


    override fun createChip(context: Context, text: CharSequence, data: Any?): ChipSpan {
        val chipIcon: Drawable?
        if (PickerUtils.isEmail(text.toString()))
            chipIcon = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_email_white, null)
        else
            chipIcon = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_message_white, null)
        return ChipSpan(context, text, chipIcon, data)
    }
}
