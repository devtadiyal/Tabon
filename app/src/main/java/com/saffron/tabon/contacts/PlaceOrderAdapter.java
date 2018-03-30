package com.saffron.tabon.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.saffron.tabon.R;

import java.util.List;

/**
 * Created by saffron on 3/27/2018.
 */

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderAdapter.ViewHolder> {


    List<PeopleOrderGetter> list;
    Context context;
    Toolbar tb;
    Button checkstatus1;

    public PlaceOrderAdapter(List<PeopleOrderGetter> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.placeorderlist,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PeopleOrderGetter g = list.get(position);
        holder.t66.setText(g.getBanner());
        holder.v11.setImageResource(g.getImg());

       /* DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        System.out.println("MERICS VALUE HAI  "+metrics);
        int width = metrics.widthPixels / 4 - ((int) (12 * metrics.density)) - 20;
        holder.v11.getLayoutParams().width = width+5;
        holder.v11.getLayoutParams().height = width+5;*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder

    {
        private TextView t11, t22, t33, t44, t55, t66;
        private ImageView v11;

        ViewHolder(View itemView) {
            super(itemView);


            t66 = itemView.findViewById(R.id.banner);
            v11 = itemView.findViewById(R.id.icon);

        }


    }
}

