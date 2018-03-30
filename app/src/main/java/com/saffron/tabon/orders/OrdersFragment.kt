package com.saffron.tabon.orders

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.saffron.tabon.R
import com.saffron.tabon.activity.ItemClickListener
import java.util.*


class OrdersFragment : Fragment() {


    private var recyclerView: RecyclerView? = null

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.activity_shirts, container, false)
        recyclerView = v.findViewById(R.id.rc)
        recyclerView!!.setHasFixedSize(true)


        val numberOfColumns = 2
        recyclerView!!.layoutManager = GridLayoutManager(context, numberOfColumns)


        //  FOR VERTICAl SCROLLING
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  FOR HORIZONTAL SCROLLING
        // recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true));

        recyclerView!!.addOnItemTouchListener(RecyclerTouchListener(context,
                recyclerView!!, object : ItemClickListener {
            override fun onClick(view: View, position: Int) {

                //Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            override fun onLongClick(view: View, position: Int) {

                //Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

        }))


        Handler().postDelayed({ this.showRestaurants() }, 100)
        return v
    }

    fun showRestaurants() {

        val titleList = ArrayList<String>()
        titleList.add(getString(R.string.tgi))
        titleList.add(getString(R.string.khan))
        titleList.add(getString(R.string.delhi_heights))
        titleList.add(getString(R.string.my_bar_cafe))

        val subTitleList = ArrayList<String>()
        subTitleList.add(getString(R.string.everybody_knows))
        subTitleList.add(getString(R.string.everybody_knows))
        subTitleList.add(getString(R.string.some_says))
        subTitleList.add(getString(R.string.indian_chinese))


        val restaurantList = ArrayList<Int>()
        restaurantList.add(R.drawable.restaurant)
        restaurantList.add(R.drawable.restaurant)
        restaurantList.add(R.drawable.restaurant)
        restaurantList.add(R.drawable.restaurant)


        val list = ArrayList<OrdersGetter>()
        for (i in restaurantList.indices) {
            val listItem = OrdersGetter(
                    restaurantList[i], titleList[i], subTitleList[i])
            list.add(listItem)
        }

        val adapter = OrdersAdapter(list, context)
        recyclerView!!.adapter = adapter
    }

    internal class RecyclerTouchListener(context: Context?, recycleView: RecyclerView, private val clicklistener: ItemClickListener?) : RecyclerView.OnItemTouchListener {
        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recycleView.findChildViewUnder(e.x, e.y)
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child))
            }
            return false
        }


        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }

}