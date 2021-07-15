package com.geniobits.recyclerviewproject.Main.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geniobits.recyclerviewproject.Main.Activities.MainActivity;
import com.geniobits.recyclerviewproject.Main.Model.FoodModel;
import com.geniobits.recyclerviewproject.R;
import com.squareup.picasso.Picasso;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    public FoodAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);
        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodModel foodModel = MainActivity.foodModelArrayList.get(position);
        holder.name_textview.setText(foodModel.name);
        holder.decription_textview.setText(foodModel.description);
        holder.price_textview.setText(foodModel.price+"INR");
        Picasso.get().load(foodModel.image_url).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getRootView().getContext(), "Clicked item no:"+position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                MainActivity.foodModelArrayList.remove(position);
                notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("Size", MainActivity.foodModelArrayList.size()+"");
        return MainActivity.foodModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name_textview;
        public TextView decription_textview;
        public TextView price_textview;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_textview = itemView.findViewById(R.id.name_textview);
            decription_textview = itemView.findViewById(R.id.decription_textview);
            price_textview = itemView.findViewById(R.id.price_textview);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
