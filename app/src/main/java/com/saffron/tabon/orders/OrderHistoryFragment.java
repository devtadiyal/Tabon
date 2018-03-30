package com.saffron.tabon.orders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.saffron.tabon.R;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFragment extends Fragment {

    int gg, hh;
    String aa, bb, cc, dd,kk;
    private List<OrderHistoryGetter> list = new ArrayList<>();

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_history, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.rc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        List<String> l = new ArrayList<>();
        l.add("Punjabi by nature");
        l.add("Punjab Grill");
        l.add("Pind Baluchi");
        //l.add("Adidas Shoes");

        List<String> l2 = new ArrayList<>();
        l2.add("28-Feb-2018");
        l2.add("15-Jan-2018");
        l2.add("19-Dec-2017");
      //  l2.add("Adidas sale 25 to 60% off");

        List<String> l3 = new ArrayList<>();
        l3.add("Total Amount ");
        l3.add("Total Amount ");
        l3.add("Total Amount ");
       // l3.add("13m");
       /* l.add("PUMA");
        l.add("GUCCI");
        l.add("Nike");*/

        List<Integer> l6 = new ArrayList<>();
        l6.add(R.drawable.restaurant);
        l6.add(R.drawable.restaurant);
        l6.add(R.drawable.restaurant);
        l6.add(R.drawable.restaurant);

        List<Integer> l4 = new ArrayList<>();
        l4.add(R.drawable.restaurant);
        l4.add(R.drawable.restaurant);
        l4.add(R.drawable.restaurant);
        l4.add(R.drawable.restaurant);
      /*  l6.add(R.drawable.puma1);
        l6.add(R.drawable.gucchi1);
        l6.add(R.drawable.nike1);*/

        List<String> l5 = new ArrayList<>();
        l5.add("₹ 1290");
        l5.add("₹ 1452");
        l5.add("₹ 2517");

        for (int k = 0; k < l.size(); k++) {

            gg = l6.get(k);
            hh = l4.get(k);
            aa = l.get(k);
            bb = l2.get(k);
            cc = l3.get(k);
            kk = l5.get(k);

            OrderHistoryGetter listItem = new OrderHistoryGetter(gg, hh, aa, bb, cc,kk);
            list.add(listItem);
        }


        RecyclerView.Adapter adapter = new OrderHistoryAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment

        ImageButton ib = v.findViewById(R.id.back4);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return v;

    }
}
