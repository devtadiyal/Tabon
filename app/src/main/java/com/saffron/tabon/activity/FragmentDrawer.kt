package com.saffron.tabon.activity

/**
 * Created by Rahul on 07-03-2018.
 */

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import com.saffron.tabon.R
import com.saffron.tabon.adapter.NavigationDrawerAdapter
import com.saffron.tabon.model.NavDrawerItem
import java.util.*

class FragmentDrawer : Fragment() {

    private var titles: Array<String>? = null
    private var recyclerView: RecyclerView? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var adapter: NavigationDrawerAdapter? = null
    private var containerView: View? = null
    private var drawerListener: FragmentDrawerListener? = null

    // preparing navigation drawer items
    val data: MutableList<NavDrawerItem>
        get() {
            val data = ArrayList<NavDrawerItem>()
            for (title in titles!!) {
                val navItem = NavDrawerItem()
                navItem.title = title
                data.add(navItem)
            }
            return data
        }

    fun setDrawerListener(listener: FragmentDrawerListener) {
        this.drawerListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // drawer labels
        titles = activity!!.resources.getStringArray(R.array.nav_drawer_labels)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false)
        recyclerView = layout.findViewById(R.id.drawerList)

        adapter = NavigationDrawerAdapter(activity!!.applicationContext, data)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.addOnItemTouchListener(RecyclerTouchListener(activity!!.applicationContext, recyclerView!!, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                drawerListener!!.onDrawerItemSelected(view, position)
                mDrawerLayout!!.closeDrawer(containerView!!)
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))

        return layout
    }


    fun setUp(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar) {
        containerView = activity!!.findViewById(fragmentId)
        mDrawerLayout = drawerLayout
        mDrawerToggle = object : ActionBarDrawerToggle(activity!!, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                activity!!.invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                activity!!.invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toolbar.alpha = 1 - slideOffset / 2
            }
        }

        mDrawerLayout!!.addDrawerListener(mDrawerToggle!!)
        mDrawerLayout!!.post { mDrawerToggle!!.syncState() }

    }

    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    interface FragmentDrawerListener {
        fun onDrawerItemSelected(view: View, position: Int)
    }

    internal class RecyclerTouchListener(context: Context, recyclerView: RecyclerView, private val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }


    }
}
