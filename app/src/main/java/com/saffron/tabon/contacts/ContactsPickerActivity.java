package com.saffron.tabon.contacts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.saffron.tabon.R;

import java.util.ArrayList;

public class ContactsPickerActivity extends AppCompatActivity implements OnClickItem {

    ListView contactsChooser;
    Button btnDone;
    EditText txtFilter;
    ArrayList<String> checkedValue;
    TextView txtLoadInfo, t11, t22, t33, t44, skipp;
    ContactsListAdapter contactsListAdapter;
    ContactsLoader contactsLoader;
    private LinearLayout llView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_picker);

        contactsChooser = (ListView) findViewById(R.id.lst_contacts_chooser);
        btnDone = (Button) findViewById(R.id.btn_done);
        skipp = findViewById(R.id.skip);
        txtFilter = (EditText) findViewById(R.id.txt_filter);
        txtLoadInfo = (TextView) findViewById(R.id.txt_load_progress);
        llView = findViewById(R.id.llView);
        t11 = findViewById(R.id.t1);
        t22 = findViewById(R.id.t2);
        t33 = findViewById(R.id.t3);
        t44 = findViewById(R.id.t4);



        contactsListAdapter = new ContactsListAdapter(this, new ContactsList());

        contactsChooser.setAdapter(contactsListAdapter);

        loadContacts("");


        txtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsListAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           /*     if (contactsListAdapter.selectedContactsList.contactArrayList.isEmpty()) {
                    setResult(RESULT_CANCELED);
                } else {

                    Intent resultIntent = new Intent();
                    resultIntent.putParcelableArrayListExtra("SelectedContacts", contactsListAdapter.selectedContactsList.contactArrayList);
                    setResult(RESULT_OK, resultIntent);

                }
                finish();*/

           startActivity(new Intent(ContactsPickerActivity.this,PeopleOrderActivity.class));

            }
        });

        t11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t11.setVisibility(View.GONE);
            }
        });
        t22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t22.setVisibility(View.GONE);
                t44.setVisibility(View.GONE);
            }
        });
        t33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t33.setVisibility(View.GONE);
                t44.setVisibility(View.GONE);
            }
        });
    }


    private void loadContacts(String filter) {

        if (contactsLoader != null && contactsLoader.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                contactsLoader.cancel(true);
            } catch (Exception e) {

            }
        }
        if (filter == null) filter = "";

        try {
            //Running AsyncLoader with adapter and  filter
            contactsLoader = new ContactsLoader(this, contactsListAdapter);
            contactsLoader.txtProgress = txtLoadInfo;
            contactsLoader.execute(filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void clickItem(int position, boolean isChecked) {
        try {
            if (isChecked) {
                llView.setVisibility(View.VISIBLE);
                skipp.setVisibility(View.INVISIBLE);
                t44.setText(String.valueOf(contactsListAdapter.checkbox_clicked));
                t11.setText(contactsListAdapter.al.get(0).toString());
                t22.setText(contactsListAdapter.al.get(1).toString());
                t33.setText(contactsListAdapter.al.get(2).toString());


            } else {
                skipp.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            System.out.println("E    " + e);
        }

    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                0); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    @Override
    public void favourite(int position, String text, boolean isChecked) {

    }
}
