package com.saffron.tabon.contacts;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.saffron.tabon.R;

import java.util.List;

/**
 * Created by saffron on 3/29/2018.
 */

public class AddOfferAdapter extends RecyclerView.Adapter<AddOfferAdapter.ViewHolder> {


    List<AddOfferGetter> list;
    Context context;
    Toolbar tb;
    Button checkstatus1;
    private int flag = 0;
    public AddOfferAdapter(List<AddOfferGetter> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public AddOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.addofferlist,
                parent, false);
        return new AddOfferAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddOfferAdapter.ViewHolder holder, int position) {

        AddOfferGetter g = list.get(position);
        holder.t66.setText(g.getBanner());
        holder.t11.setText(g.getNo());
        holder.v11.setImageResource(g.getImg());

        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0) {
                    flag = 1; // 1 => Button ON



                    holder.b.setText("REMOVE");
                    holder.b.setHeight(20);
                    holder.b.setWidth(70);
                    holder.b.setTextColor(Color.parseColor("#ffffff"));
                    holder.b.setBackgroundColor(Color.parseColor("#C51162"));
                } else {
                    flag = 0; // 0 => Button OFF
                    holder.b.setText("ADD");
                    holder.b.setHeight(20);
                    holder.b.setWidth(70);
                    holder.b.setTextColor(Color.parseColor("#000000"));
                    holder.b.setBackgroundResource(R.drawable.button_border);
                }


            }
        });

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
        Button b;

        ViewHolder(View itemView) {
            super(itemView);


            t66 = itemView.findViewById(R.id.t2);
            v11 = itemView.findViewById(R.id.icon);
            t11 = itemView.findViewById(R.id.t1);
            b = itemView.findViewById(R.id.e2);

        }


    }
}