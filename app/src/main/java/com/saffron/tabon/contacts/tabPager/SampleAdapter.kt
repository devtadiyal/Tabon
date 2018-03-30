package com.saffron.tabon.contacts.tabPager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by day on 22/2/18.
 */

class SampleAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        val fragment: Fragment?
        when (position) {
            0 -> fragment = FragmentOne.newInstance()
            1 -> fragment = FragmentTwo.newInstance()
            else -> fragment = null
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {

        val title: CharSequence
        when (position) {
            0 -> title = "People"
            1 -> title = "Brand"
            else -> title = ""
        }

        return title
    }

    override fun getCount(): Int {
        return 2
    }
}
