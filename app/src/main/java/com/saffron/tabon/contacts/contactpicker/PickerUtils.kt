package com.saffron.tabon.contacts.contactpicker

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.DrawableRes

import java.io.ByteArrayOutputStream

/**
 * Created by Carlos Reyna on 21/01/17.
 */

object PickerUtils {

    fun isEmail(communication: String?): Boolean {
        return if (communication == null) false else communication.contains("@")
    }

    fun sendDrawable(resources: Resources, @DrawableRes drawableRes: Int): ByteArray {
        val bitmap = BitmapFactory.decodeResource(resources, drawableRes)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    fun extractDrawable(drawable: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(drawable, 0, drawable.size)
    }
}
