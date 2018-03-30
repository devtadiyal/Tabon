package com.saffron.tabon.contacts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.saffron.tabon.R;
import com.saffron.tabon.activity.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class TotalBillActivity extends AppCompatActivity {
    String aa, no, itemlist, quanity, price,offer;
    int gg;
    List<Integer> l6 = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    private List<TotalBillGetter> list1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_bill);

        ImageButton ib = findViewById(R.id.back4);
        ib.setOnClickListener(new View.OnClickListener() {
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
        l1.add("SUPERDRY");
        l1.add("NIKE");
        l1.add("H & M");
        l1.add("ZARA");
        l1.add("NIKE");

        List<Integer> l2 = new ArrayList<>();
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);


        List<String> n = new ArrayList<>();
        n.add("Govind Kumar");
        n.add("Amit Verma");
        n.add("Devendra Singh");
        n.add("Rahul Gupta");
        n.add("Kunal Kashyap");


        List<String> il = new ArrayList<>();
        il.add("Chicken masala tikka");
        il.add("Shahi paneer");
        il.add("Chicken biryani");
        il.add("Matar paneer");
        il.add("Chilli chicken");


        List<String> q = new ArrayList<>();
        q.add("Govind Kumar");
        q.add("Amit Verma");
        q.add("Devendra Singh");
        q.add("Rahul Gupta");
        q.add("Kunal Kashyap");


        List<String> p = new ArrayList<>();
        p.add("425");
        p.add("369");
        p.add("846");
        p.add("451");
        p.add("326");



        List<String> py = new ArrayList<>();
        py.add("");
        py.add("");
        py.add("");
        py.add("Offer Applied 30% off");
        py.add("Offer Applied 26% off");

        for (int k = 0; k < p.size(); k++) {
            aa = l1.get(k);
            gg = l2.get(k);
            no = n.get(k);


            TotalBillGetter listItem = new TotalBillGetter(gg, aa, no);
            list1.add(listItem);

        }

        adapter = new TotalBillAdapter(list1, this);
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
