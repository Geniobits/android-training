package com.geniobits.recyclerviewproject.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.geniobits.recyclerviewproject.Main.Adapter.FoodAdapter;
import com.geniobits.recyclerviewproject.Main.Model.FoodModel;
import com.geniobits.recyclerviewproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static ArrayList<FoodModel> foodModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodModelArrayList.add(new FoodModel(1,"Bakery and Bread",
                "Bakery and Bread description",300,"https://images.unsplash.com/photo-1509440159596-0249088772ff?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=752&q=80"));
        foodModelArrayList.add(new FoodModel(2,"Meat and Seafood.",
                        "Meat and Seafood. description",300,"https://images.unsplash.com/photo-1613660635034-b7a09ae11463?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=617&q=80"));
        foodModelArrayList.add(new FoodModel(3,"Pasta ",
                        "Pasta  description",300,"https://images.unsplash.com/photo-1563379926898-05f4575a45d8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        foodModelArrayList.add(new FoodModel(4,"Bakery and Bread",
                "Bakery and Bread description",300,"https://images.unsplash.com/photo-1509440159596-0249088772ff?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=752&q=80"));
        foodModelArrayList.add(new FoodModel(5,"Meat and Seafood.",
                        "Meat and Seafood. description",300,"https://images.unsplash.com/photo-1613660635034-b7a09ae11463?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=617&q=80"));
        foodModelArrayList.add(new FoodModel(6,"Pasta ",
                        "Pasta  description",300,"https://images.unsplash.com/photo-1563379926898-05f4575a45d8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));



        RecyclerView recyclerviewFood = findViewById(R.id.recyclerview);
        FoodAdapter mAdapter = new FoodAdapter();
        final RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerviewFood.setLayoutManager(mLayoutManager);
        recyclerviewFood.setAdapter(mAdapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodModelArrayList.add(new FoodModel(6,"Pasta ",
                        "Pasta  description",300,"https://images.unsplash.com/photo-1563379926898-05f4575a45d8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}