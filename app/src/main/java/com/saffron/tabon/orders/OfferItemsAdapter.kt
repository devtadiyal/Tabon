package com.saffron.tabon.orders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.saffron.tabon.R

/**
 * Created by saffron on 2/24/2018.
 */

class OfferItemsAdapter internal constructor(private val list: List<OfferitemsGetter>, private val context: Context) : RecyclerView.Adapter<OfferItemsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.popularbrandlist,
                parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val g = list[position]
        holder.t66.text = g.banner
        holder.v11.setImageResource(g.img)

        val metrics = context.resources.displayMetrics
        val width = metrics.widthPixels / 3 - (16 * metrics.density).toInt()
        holder.v11.layoutParams.width = width
        holder.v11.layoutParams.height = width
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val t66: TextView
        val v11: ImageView

        init {

            t66 = itemView.findViewById(R.id.banner)
            v11 = itemView.findViewById(R.id.icon)
        }

    }
}

