package com.saffron.tabon.contacts;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.saffron.tabon.R;
import com.saffron.tabon.activity.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PeopleOrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<Integer> l6 = new ArrayList<>();
    String aa, bb, cc, dd, ee, ff,no,itemlist,quanity,offer,price;
    int gg;
    Dialog dialog;
    private RecyclerView.Adapter adapter;
    private List<PeopleOrderGetter> list = new ArrayList<>();
    private List<ListGetter> list1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_order);

        Button b2 = findViewById(R.id.offer);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PeopleOrderActivity.this,AddOfferActivity.class));
            }
        });


        Button b1 = findViewById(R.id.bill);

           b1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(PeopleOrderActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.billrequestalert, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(PeopleOrderActivity.this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                       startActivity(new Intent(PeopleOrderActivity.this,PayBillActivity.class));
                        deleteDialog.dismiss();
                    }
                });
                deleteDialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();
            }
        });





ImageButton ib = (ImageButton) findViewById(R.id.imagebutton);
ib.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(PeopleOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_contact);
        TextView text = dialog.findViewById(R.id.cancel);
        text.setOnClickListener(v1 -> dialog.dismiss());


        dialog.show();

    }
});
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Punjabi by nature");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        //  FOR VERTICAl SCROLLING
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //  FOR HORIZONTAL SCROLLING
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.getLayoutManager().scrollToPosition(2);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
              //  int image_name = l6.get(position);
               // Toast.makeText(PeopleOrderActivity.this,"hi",Toast.LENGTH_LONG).show();

                LayoutInflater factory = LayoutInflater.from(PeopleOrderActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.show_profile, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(PeopleOrderActivity.this).create();
                deleteDialog.setView(deleteDialogView);
              /*  deleteDialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        startActivity(new Intent(PeopleOrderActivity.this,PayBillActivity.class));
                        deleteDialog.dismiss();
                    }
                });*/
                deleteDialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));


        List<String> l = new ArrayList<>();
        l.add("SUPERDRY");
        l.add("NIKE");
        l.add("H & M");
        l.add("ZARA");
        l.add("NIKE");
        l.add("H & M");

        l6.add(R.drawable.restaurant);
        l6.add(R.drawable.restaurant);
        l6.add(R.drawable.restaurant);
        l6.add(R.drawable.restaurant);
        l6.add(R.drawable.restaurant);
        l6.add(R.drawable.restaurant);



        for (int k = 0; k < l6.size(); k++) {
            bb = l.get(k);
            gg = l6.get(k);

            PeopleOrderGetter listItem = new PeopleOrderGetter(gg, aa);
            list.add(listItem);

        }

        adapter = new PlaceOrderAdapter(list, this);
        recyclerView.setAdapter(adapter);
        //initSpinner();











        RecyclerView recyclerView1 = findViewById(R.id.list);
        recyclerView1.setHasFixedSize(true);

        //  FOR VERTICAl SCROLLING
         recyclerView1.setLayoutManager(new LinearLayoutManager(this));


        recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView1, new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                int image_name = l6.get(position);
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
        l1.add("H & M");
        List<Integer> l2 = new ArrayList<>();
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);
        l2.add(R.drawable.restaurant);

        List<String> n = new ArrayList<>();
        n.add("1");
        n.add("2");
        n.add("3");
        n.add("4");
        n.add("5");
        n.add("6");

        List<String> il = new ArrayList<>();
        il.add("Chicken masala tikka");
        il.add("Shahi paneer");
        il.add("Chicken biryani");
        il.add("Matar paneer");
        il.add("Chilli chicken");
        il.add("Garlic fish");

        List<String> q = new ArrayList<>();
        q.add("1");
        q.add("2");
        q.add("1");
        q.add("6");
        q.add("3");
        q.add("1");

        List<String> p = new ArrayList<>();
        p.add("425");
        p.add("369");
        p.add("846");
        p.add("451");
        p.add("326");
        p.add("259");

        List<String> py = new ArrayList<>();
        py.add("");
        py.add("");
        py.add("");
        py.add("");
        py.add("Offer Applied 30% off");
        py.add("Offer Applied 26% off");

        for (int k = 0; k < l6.size(); k++) {
            aa = l1.get(k);
            gg = l2.get(k);
            no = n.get(k);
            itemlist = il.get(k);
            quanity = q.get(k);
            price = p.get(k);
            offer = py.get(k);
            ListGetter listItem = new ListGetter(gg, aa,no,itemlist,quanity,price,offer);
            list1.add(listItem);

        }

        adapter = new ListAdapter(list1, this);
        recyclerView1.setAdapter(adapter);
        //initSpinner();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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