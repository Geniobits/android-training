package com.geniobits.randomnumbergenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        TextView message_textview = findViewById(R.id.my_message_text_view);
        String message= getIntent().getStringExtra("message");
        message_textview.setText(message);

    }
}