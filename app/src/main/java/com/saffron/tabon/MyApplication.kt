package com.saffron.tabon

import android.app.Application

import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by day on 24/2/18.
 */

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder().setFontAttrId(R.attr.fontPath).build())
    }
}
