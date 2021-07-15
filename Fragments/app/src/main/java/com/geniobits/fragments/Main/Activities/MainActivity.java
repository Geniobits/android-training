package com.geniobits.fragments.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.geniobits.fragments.Main.Fragments.FirstFragment;
import com.geniobits.fragments.Main.Fragments.SecondFragment;
import com.geniobits.fragments.Main.Fragments.ThirdFragment;
import com.geniobits.fragments.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);


        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==0){
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.container, FirstFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else if(position==1){
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.container, SecondFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else{
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.container, ThirdFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        };
        listView.setOnItemClickListener(onItemClickListener);




    }
}