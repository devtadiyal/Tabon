package com.saffron.tabon.adapter

/**
 * Created by Rahul on 07-03-2018.
 */

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.saffron.tabon.R
import com.saffron.tabon.model.NavDrawerItem

internal class NavigationDrawerAdapter(private val context: Context, data: MutableList<NavDrawerItem>) : RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder>() {
    private var data = emptyList<NavDrawerItem>()
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.nav_drawer_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = data.get(position)
        holder.title.setText(current.title)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView

        init {
            title = itemView.findViewById(R.id.title)
        }
    }
}
