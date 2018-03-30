package com.saffron.tabon.contacts;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.saffron.tabon.R;
import com.saffron.tabon.activity.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AddOfferActivity extends AppCompatActivity {
    String aa, no, itemlist, quanity, price,offer;
    int gg;
    TextView t1,t2;
    List<Integer> l6 = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    private List<AddOfferGetter> list1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        t1 = findViewById(R.id.ok);
        t2 = findViewById(R.id.cancel);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView1 = findViewById(R.id.list1);
        recyclerView1.setHasFixedSize(true);

        //  FOR VERTICAl SCROLLING
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));


        recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView1, new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));


        List<String> l1 = new ArrayList<>();
        l1.add("Blooming Onion");
        l1.add("Chilli Chicken");
        l1.add("Big Burger");
        l1.add("Grilled Fish");

        List<Integer> l2 = new ArrayList<>();
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);





        List<String> il = new ArrayList<>();
        il.add("Chicken masala tikka");
        il.add("Shahi paneer");
        il.add("Chicken biryani");
        il.add("Matar paneer");


        List<String> q = new ArrayList<>();
        q.add("Govind Kumar");
        q.add("Amit Verma");
        q.add("Devendra Singh");
        q.add("Rahul Gupta");



        List<String> p = new ArrayList<>();
        p.add("425");
        p.add("369");
        p.add("846");
        p.add("451");




        List<String> py = new ArrayList<>();
        py.add("21% OFF");
        py.add("34% OFF");
        py.add("30% OFF");
        py.add("26% OFF");

        for (int k = 0; k < py.size(); k++) {
            aa = l1.get(k);
            gg = l2.get(k);
            no = py.get(k);


            AddOfferGetter listItem = new AddOfferGetter(gg, aa, no);
            list1.add(listItem);

        }

        adapter = new AddOfferAdapter(list1, this);
        recyclerView1.setAdapter(adapter);
        //initSpinner();
    }


    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ItemClickListener clicklistener;
        private GestureDetector gestureDetector;

        RecyclerTouchListener(Context context, final RecyclerView recycleView, final ItemClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
