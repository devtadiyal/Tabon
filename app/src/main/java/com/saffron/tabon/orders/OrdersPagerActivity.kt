package com.saffron.tabon.orders

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import com.saffron.tabon.R
import com.saffron.tabon.activity.ItemClickListener
import com.saffron.tabon.ui.CircleIndicator
import java.util.*


class OrdersPagerActivity : AppCompatActivity() {

    internal var l6: MutableList<Int> = ArrayList()
    private val list = ArrayList<OfferitemsGetter>()
    private var adapter: RecyclerView.Adapter<OfferItemsAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shirt_pager_layout)

        val ib = findViewById<ImageButton>(R.id.back4)
        ib.setOnClickListener { v -> finish() }

        val mViewpager = findViewById<ViewPager>(R.id.viewpager)
        mViewpager.layoutParams.height = resources.displayMetrics.widthPixels

        val mIndicator = findViewById<CircleIndicator>(R.id.indicator)
        mViewpager.adapter = OrdersPagerAdapter(this, 4)
        mIndicator.setViewPager(mViewpager)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        recyclerView.layoutManager.scrollToPosition(2)

        recyclerView.addOnItemTouchListener(OrdersFragment.RecyclerTouchListener(this,
                recyclerView, object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                //Values are passing to activity & to fragment as well

                val image_name = l6[position]
                /*  company_name = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.t1)).getText().toString();
                cloth_type = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.t2)).getText().toString();
                size = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.t3)).getText().toString();
                color = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.t4)).getText().toString();
                quantity = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.t5)).getText().toString();
                tag_line = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.t6)).getText().toString();

                */

            }

            override fun onLongClick(view: View, position: Int) {

            }

        }))


        val l = ArrayList<String>()
        l.add("20% off\nBloomin Onion")
        l.add("30% off\nChilli Chicken")
        l.add("21% off\nBig Burger")
        l.add("26% off\nGrilled fish")

        l6.add(R.drawable.restaurant)
        l6.add(R.drawable.restaurant)
        l6.add(R.drawable.restaurant)
        l6.add(R.drawable.restaurant)


        for (k in l.indices) {
            val listItem = OfferitemsGetter(l6[k], l[k])
            list.add(listItem)

        }
        adapter = OfferItemsAdapter(list, this)
        recyclerView.adapter = adapter
    }

    fun tabOn(view: View) {

        startActivity(Intent(this, TabOnCodeGeneratorActivity::class.java))
    }
}
