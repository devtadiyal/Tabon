package com.saffron.tabon.contacts.tabPager

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.saffron.tabon.R


/**
 * Created by day on 22/2/18.
 */


class TabLayoutActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab_activity)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val adapter = SampleAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        /* tabLayout.addTab(tabLayout.newTab().setText("Songs"))
         tabLayout.addTab(tabLayout.newTab().setText("Albums"))
         tabLayout.addTab(tabLayout.newTab().setText("Artists"))*/

        /*tabLayout.getTabAt(0).setIcon(android.R.drawable.ic_dialog_email)
        tabLayout.getTabAt(1).setIcon(android.R.drawable.ic_dialog_info)
        tabLayout.getTabAt(2).setIcon(android.R.drawable.ic_dialog_alert)*/

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun initToolbar() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.app_name)
    }
}

