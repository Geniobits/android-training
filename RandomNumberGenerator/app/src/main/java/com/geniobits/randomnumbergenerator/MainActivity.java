package com.geniobits.randomnumbergenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CONTACT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send_button = findViewById(R.id.send_button);
        TextView message_textview = findViewById(R.id.message_text_view);
        message_textview.setVisibility(View.GONE);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent pick = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pick, REQUEST_CONTACT);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CONTACT  && resultCode  == RESULT_OK) {


                Uri contactUri = data.getData();

                String[] queryFields = new String[] {ContactsContract.Contacts.DISPLAY_NAME };

                Cursor c = getApplicationContext().getContentResolver()
                        .query(contactUri, queryFields, null, null, null);

                try{
                    if (c.getCount() == 0) { return; }

                    c.moveToFirst();
                    String suspect = c.getString(0);
                    Log.e("Mysuspect",suspect);

                }catch (Exception e){
                    Log.e("error",e.toString());
                }


            }
        } catch (Exception ex) {

        }

    }

}