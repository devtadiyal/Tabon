package com.saffron.tabon.orders

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.saffron.tabon.R

/**
 * Created by saffron on 2/24/2018.
 */

class OrdersAdapter internal constructor(private val list: List<OrdersGetter>, private val context: Context?) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.shirtlist, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val g = list[position]
        holder.t11.text = g.title
        holder.v11.setImageResource(g.img)

        val metrics = context!!.resources.displayMetrics
        val width = metrics.widthPixels / 2 - (4 * metrics.density).toInt()
        holder.v11.layoutParams.width = width
        holder.v11.layoutParams.height = width

        holder.relativeLayout.layoutParams.width = width

        holder.v11.setOnClickListener { v -> context.startActivity(Intent(context, OrdersPagerActivity::class.java)) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var relativeLayout: RelativeLayout
        val t11: TextView
        val v11: ImageView

        init {

            v11 = itemView.findViewById(R.id.icon)
            t11 = itemView.findViewById(R.id.title)
            relativeLayout = itemView.findViewById(R.id.l1)
        }
    }
}
