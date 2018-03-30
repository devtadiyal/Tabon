package com.saffron.tabon.orders

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.saffron.tabon.R

class OrdersPagerAdapter internal constructor(private val context: Context, private val mSize: Int) : PagerAdapter() {

    override fun getCount(): Int {
        return mSize
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(view: ViewGroup, position: Int, `object`: Any) {
        view.removeView(`object` as View)
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {

        val mView = LayoutInflater.from(context).inflate(R.layout.shirt_pager_adapter_layout, view, false)

        when (position) {
            0 -> setImageResource(context, mView.findViewById(R.id.image_screen), R.drawable.restaurant)
            1 -> setImageResource(context, mView.findViewById(R.id.image_screen), R.drawable.restaurant)
            2 -> setImageResource(context, mView.findViewById(R.id.image_screen), R.drawable.restaurant)
            3 -> setImageResource(context, mView.findViewById(R.id.image_screen), R.drawable.restaurant)
        }

        view.addView(mView)

        return mView
    }

    private fun setImageResource(mContext: Context, imageView: ImageView, id: Int) {

        Glide.with(mContext).load(id)
                .thumbnail(0.5f)
                .into(imageView)
    }
}