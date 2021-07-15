package com.geniobits.firebaseauthdemo2.Home.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.geniobits.firebaseauthdemo2.Home.Model.image;
import com.geniobits.firebaseauthdemo2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends FirebaseRecyclerAdapter<
        image, ImageAdapter.imageViewholder> {

    private final Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ImageAdapter(@NonNull FirebaseRecyclerOptions<image> options, Context context) {
        super(options);
        this.context = context;
        Log.e("Id","Adpter");
    }

    @Override
    protected void onBindViewHolder(@NonNull imageViewholder holder, int position, @NonNull image model) {
        holder.textViewImagename.setText(model.id);
        Picasso.get().load(model.getDownload_url()).into(holder.imageView);

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(model.id);
            }
        });
    }

    @NonNull
    @Override
    public imageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new imageViewholder(view);
    }

    public static class imageViewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewImagename;
        Button buttonDelete;
        public imageViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewImagename = itemView.findViewById(R.id.textViewImagename);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public void delete(String id){
        String storageUrl = "images/"+id;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(storageUrl);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                delete_from_database(id);
                Log.d("Delete", "onSuccess: deleted file");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Log.d("deletefail", "onFailure: did not delete file");
            }
        });
    }

    public void delete_from_database(String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query imageQuery = ref.child("images").orderByChild("id").equalTo(id);

        imageQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                    imageSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("deltefail", "onCancelled", databaseError.toException());
            }
        });
    }
}
