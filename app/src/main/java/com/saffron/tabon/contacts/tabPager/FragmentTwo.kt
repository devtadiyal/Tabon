package com.saffron.tabon.contacts.tabPager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.saffron.tabon.R


/**
 * Created by day on 22/2/18.
 */

class FragmentTwo : Fragment() {

    internal lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_two, container, false)
        searchView = view.findViewById(R.id.search_place)
        return view
    }

    companion object {

        fun newInstance(): FragmentTwo {

            val args = Bundle()
            val fragment = FragmentTwo()
            fragment.arguments = args
            return fragment
        }
    }
}
