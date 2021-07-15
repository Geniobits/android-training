package com.geniobits.apiproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.geniobits.apiproject.ApiApp;
import com.geniobits.apiproject.Models.User;
import com.geniobits.apiproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class AddUser extends AppCompatActivity {

    boolean is_update;
    String id;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        EditText editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        EditText editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        EditText editTextGender = findViewById(R.id.editTextGender);
        Button buttonSave = findViewById(R.id.buttonSave);


            is_update = getIntent().getBooleanExtra("is_update", false);
            position = getIntent().getIntExtra("position", 0);
            if(is_update) {
                User user = MainActivity.userArrayList.get(position);
                id = user.id;
                editTextTextPersonName.setText(user.name);
                editTextTextEmailAddress.setText(user.email);
                editTextGender.setText(user.gender);
            }


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_update){
                    updateUser(editTextTextPersonName.getText().toString(), editTextTextEmailAddress.getText().toString(), editTextGender.getText().toString(), id);

                }else
                addUser(editTextTextPersonName.getText().toString(), editTextTextEmailAddress.getText().toString(), editTextGender.getText().toString());
            }
        });

    }

    private void addUser(String name, String email, String gender) {
        StringRequest stringRequestCourseData = new StringRequest(Request.Method.POST, "https://gorest.co.in/public/v1/users",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectUser = jsonObject.getJSONObject("data");
                            User user = new User();
                            user.id = jsonObjectUser.getString("id");
                            user.name = jsonObjectUser.getString("name");
                            user.email = jsonObjectUser.getString("email");
                            user.status = jsonObjectUser.getString("status");
                            user.gender = jsonObjectUser.getString("gender");
                            MainActivity.userArrayList.add(user);
                            Toast.makeText(AddUser.this, "User Created", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddUser.this, "Something went wrong!", Toast.LENGTH_SHORT).show();


                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("gender", gender);
                params.put("status", "active");
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

    private void updateUser(String name, String email, String gender, String id) {
        StringRequest stringRequestCourseData = new StringRequest(Request.Method.PATCH, "https://gorest.co.in/public/v1/users/"+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectUser = jsonObject.getJSONObject("data");
                            User user = new User();
                            user.id = jsonObjectUser.getString("id");
                            user.name = jsonObjectUser.getString("name");
                            user.email = jsonObjectUser.getString("email");
                            user.status = jsonObjectUser.getString("status");
                            user.gender = jsonObjectUser.getString("gender");
                            MainActivity.userArrayList.add(user);
                            Toast.makeText(AddUser.this, "User Created", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddUser.this, "Something went wrong!", Toast.LENGTH_SHORT).show();


                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("gender", gender);
                params.put("status", "active");
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
}