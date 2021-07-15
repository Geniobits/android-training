package com.geniobits.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AddContact extends AppCompatActivity {
    int id;
    boolean is_update = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        if(getIntent().getData()!=null){
            id = getIntent().getIntExtra("id", 0);
            is_update = true;
        }
        PrefManager prefManager = new PrefManager(getApplicationContext());

        EditText editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        EditText editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        EditText editTextPhone2 = findViewById(R.id.editTextPhone2);
        EditText editTextTextPostalAddress = findViewById(R.id.editTextTextPostalAddress);
        Button button = findViewById(R.id.button);

        if(is_update){
            Contact contact = prefManager.contactArrayList.get(id);
            if(contact!=null){
                editTextTextPersonName.setText(contact.name);
                editTextPhone.setText(contact.phone_number);
                editTextTextEmailAddress.setText(contact.email);
                editTextPhone2.setText(contact.alternative_phone);
                editTextTextPostalAddress.setText(contact.address);
            }

        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = new Contact(
                        id,
                        editTextTextPersonName.getText().toString().trim(),
                        editTextPhone.getText().toString().trim(),
                        editTextPhone2.getText().toString().trim(),
                        editTextTextEmailAddress.getText().toString().trim(),
                        editTextTextPostalAddress.getText().toString().trim()
                );
                if(is_update){
                    prefManager.contactArrayList.add(id, contact);
                    prefManager.updateSharedPref();
                }else {
                    prefManager.contactArrayList.add(contact);
                    prefManager.updateSharedPref();
                }
                onBackPressed();
                finish();
            }
        });
    }
}