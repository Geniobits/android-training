package com.geniobits.apiproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.geniobits.apiproject.Adapter.UsersAdapter;
import com.geniobits.apiproject.ApiApp;
import com.geniobits.apiproject.Models.User;
import com.geniobits.apiproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<User> userArrayList = new ArrayList<>();
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerUsers = findViewById(R.id.recyclerUsers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        recyclerUsers.setLayoutManager(linearLayoutManager);
        adapter = new UsersAdapter();
        recyclerUsers.setAdapter(adapter);
        getDataFromServer();
        FloatingActionButton buttonAddUser = findViewById(R.id.buttonAddUser);

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddUser.class);
                startActivity(intent);
            }
        });
    }

    private void getDataFromServer() {
        StringRequest stringRequestCourseData = new StringRequest(Request.Method.GET, "https://gorest.co.in/public/v1/users",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray usersJSONArray = jsonObject.getJSONArray("data");
                            for(int i=0; i<usersJSONArray.length(); i++){
                                JSONObject jsonObjectUser = usersJSONArray.getJSONObject(i);
                                User user = new User();
                                user.id = jsonObjectUser.getString("id");
                                user.name = jsonObjectUser.getString("name");
                                user.email = jsonObjectUser.getString("email");
                                user.status = jsonObjectUser.getString("status");
                                user.gender = jsonObjectUser.getString("gender");
                                userArrayList.add(user);
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                try {
                    headers.put("Authorization", "Bearer a0f7094324786277b6b31f22904f8f4df44b61b776002194fecd01d843b927a3");
                } catch (Exception e) {
                }
                return headers;
            }
        };

        ApiApp.getInstance().addToRequestQueue(stringRequestCourseData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}