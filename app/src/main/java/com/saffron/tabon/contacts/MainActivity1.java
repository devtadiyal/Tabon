package com.saffron.tabon.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saffron.tabon.R;

import java.util.ArrayList;

public class MainActivity1 extends AppCompatActivity {

    TextView contactsDisplay;
    Button pickContacts;
    final int CONTACT_PICK_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        contactsDisplay = (TextView) findViewById(R.id.txt_selected_contacts);
        pickContacts = (Button) findViewById(R.id.btn_pick_contacts);

        pickContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentContactPick = new Intent(MainActivity1.this,ContactsPickerActivity.class);
                MainActivity1.this.startActivityForResult(intentContactPick,CONTACT_PICK_REQUEST);
            }
        });
    }

    //<editor-fold desc="Description">
    //region Description
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            int i;
            if (requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK) {

                ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");

                String display = "";
                for (i = 0; i < selectedContacts.size(); i++) {

                   // display += (i + 1) + ". " + selectedContacts.get(i).toString() + "\n";
                    display += selectedContacts.get(i).toString() + "\n";

                }
                contactsDisplay.setText("Selected Contacts : \n\n" + display);
                String b [] = display.split("\\n");
                System.out.println(b[0]);
                System.out.println(b[1]);
                System.out.println(b[2]);
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION "+e);
        }
    }


}
