package com.geniobits.apiproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.geniobits.apiproject.Activities.AddUser;
import com.geniobits.apiproject.Activities.MainActivity;
import com.geniobits.apiproject.ApiApp;
import com.geniobits.apiproject.Models.User;
import com.geniobits.apiproject.R;

import java.util.HashMap;
import java.util.Map;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = MainActivity.userArrayList.get(position);
        holder.textViewName.setText(user.name);
        holder.textViewEmail.setText(user.email);
        holder.textViewGender.setText(user.gender);
        holder.textViewStatus.setText(user.status);

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getRootView().getContext(), AddUser.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("position", position);
                intent.putExtra("is_update", true);
                view.getRootView().getContext().startActivity(intent);
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.userArrayList.remove(position);
                notifyDataSetChanged();
                delete(user.id, view.getRootView().getContext());
            }
        });

    }

    private void delete(String id, Context context) {
        StringRequest stringRequestCourseData = new StringRequest(Request.Method.DELETE, "https://gorest.co.in/public/v1/users/"+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Deleted Success!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


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
    public int getItemCount() {
        return MainActivity.userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewEmail;
        TextView textViewGender;
        TextView textViewStatus;
        Button buttonUpdate;
        Button buttonDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewGender = itemView.findViewById(R.id.textViewGender);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
