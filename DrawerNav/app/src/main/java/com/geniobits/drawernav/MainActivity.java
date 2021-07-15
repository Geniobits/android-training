package com.geniobits.drawernav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawer_layout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.START, true);
            }
        });

        NavigationView nav_bar = findViewById(R.id.nav_bar);
        nav_bar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer_layout.closeDrawer(GravityCompat.START, true);
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.search:
                        Toast.makeText(MainActivity.this, "Search Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.setting:
                        Toast.makeText(MainActivity.this, "Setting Clicked", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });
    }
}