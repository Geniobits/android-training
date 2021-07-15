package com.geniobits.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public TextView time_textview;
    public Button start_button;
    public Button stop_button;
    public Button reset_button;
    private boolean isRunning = false;
    private int seconds = 0;
    private Handler mHandler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            isRunning = savedInstanceState.getBoolean("is_running");
            seconds = savedInstanceState.getInt("seconds");
        }
        time_textview = findViewById(R.id.time_textview);
        start_button = findViewById(R.id.start_button);
        stop_button = findViewById(R.id.stop_button);
        reset_button = findViewById(R.id.reset_button);
        mHandler = new Handler();
        startTimer();
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = true;
            }
        });

        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = false;
            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = false;
                seconds = 0;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    

    private void startTimer() {
        Log.e("In startTime","method");
        runnable = new Runnable() {
            @Override
            public void run () {

                Log.e("In startTime","Starttime");
                try {
                    int hr = seconds / 3600;
                    int min = (seconds % 3600) / 60;
                    int sec = seconds % 60;
                    String time = String.format("%02d:%02d:%02d", hr, min, sec);
                    time_textview.setText(time);
                }catch (Exception e){
                    Log.e("error", e.toString());
                }
                if(isRunning){
                    seconds = seconds+1;
                }
                mHandler.postDelayed(this, 1000);

            }
        };
        mHandler.postDelayed(runnable, 1000);     //   1 second = 1000 milliseconds;

    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;

    }
}