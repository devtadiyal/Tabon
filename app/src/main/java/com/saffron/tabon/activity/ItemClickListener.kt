package com.saffron.tabon.activity

import android.view.View

/**
 * Created by saffron on 2/15/2018.
 */

interface ItemClickListener {

    fun onClick(view: View, position: Int)

    fun onLongClick(view: View, position: Int)
}

