package com.geniobits.sqlitedemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final Context context;

    public ContactAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contact contact = MainActivity.prefManager.contactArrayList.get(position);
        holder.textViewName.setText(contact.name);
        holder.textViewAddress.setText(contact.address);
        holder.textViewEmail.setText(contact.email);
        holder.textViewPhone.setText(contact.phone_number);
        Picasso.get().load("https://images.unsplash.com/photo-1530908158103-e2d2bf40c235?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80").placeholder(R.drawable.ic_launcher_background).into(holder.imageView);
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MainActivity.prefManager.contactArrayList.remove(position);
                MainActivity.prefManager.updateSharedPref();
                notifyDataSetChanged();
                return false;
            }
        });
        holder.update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddContact.class);
                i.putExtra("id", position);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return MainActivity.prefManager.contactArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewPhone;
        TextView textViewEmail;
        TextView textViewAddress;
        Button update_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            update_button = itemView.findViewById(R.id.update_button);
        }
    }
}
