package com.saffron.tabon.orders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.saffron.tabon.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saffron on 3/23/2018.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    List<OrderHistoryGetter> list;
    Context context;
    Toolbar tb;

    public OrderHistoryAdapter(List<OrderHistoryGetter> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderhistorylist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        OrderHistoryGetter g = list.get(position);
        holder.t11.setText(g.getHeading());
        holder.t22.setText(g.getBanner());
        holder.t33.setText(g.getTime());
        holder.t55.setText(g.getAmount());
        holder.t44.setImageResource(g.getPic());
        holder.civ.setImageResource(g.getProfilePic());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder

    {
        private TextView t11, t22, t33, t55;

        private ImageButton t44;
        ImageButton civ;
        public ViewHolder(View itemView) {
            super(itemView);

            t11 = itemView.findViewById(R.id.t1);
            t22 = itemView.findViewById(R.id.t2);
            t33 = itemView.findViewById(R.id.t3);
            t55 = itemView.findViewById(R.id.t4);
            t44 = itemView.findViewById(R.id.d);
            civ = itemView.findViewById(R.id.icon);
        }
    }
}