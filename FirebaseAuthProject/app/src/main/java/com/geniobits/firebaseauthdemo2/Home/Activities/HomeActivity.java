package com.geniobits.firebaseauthdemo2.Home.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.geniobits.firebaseauthdemo2.Home.Adapter.ImageAdapter;
import com.geniobits.firebaseauthdemo2.Home.Model.image;
import com.geniobits.firebaseauthdemo2.Login.Activities.ProfileActivity;
import com.geniobits.firebaseauthdemo2.Login.Model.User;
import com.geniobits.firebaseauthdemo2.R;
import com.geniobits.mediachooserdialog.MediaChooser.MediaChooserDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseDatabase database;

    private DatabaseReference databaseRef;
    private ImageAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseApp.initializeApp(getApplicationContext());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();


        RecyclerView recycler_image = findViewById(R.id.recycler_image);
        recycler_image.setLayoutManager(
                new LinearLayoutManager(HomeActivity.this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<image> options
                = new FirebaseRecyclerOptions.Builder<image>()
                .setQuery(databaseRef.child("images"), image.class)
                .build();
        databaseRef.child("images").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    try {

                        Log.e("firebase", String.valueOf(task.getResult().getValue()));
                    }catch (Exception e){
                        Log.e("Err", e.toString());
                    }
                }
            }
        });
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new ImageAdapter(options, getApplicationContext());
        // Connecting Adapter class with the Recycler view*/
        recycler_image.setAdapter(adapter);
        FloatingActionButton buttonAddImage = findViewById(R.id.buttonAddImage);
        FloatingActionButton buttonProfile = findViewById(R.id.buttonProfile);
        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaChooserDialog mediaChooserDialog = new MediaChooserDialog(HomeActivity.this, new MediaChooserDialog.MediaSelectionListener() {
                    @Override
                    public void onMediaSelected(Uri uri, String path) {
                        //uri is file uri
                        //path is file path
                        uploadImage(uri);
                    }
                });
                mediaChooserDialog.showPictureDialog();
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void uploadImage(Uri filePath) {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String id = UUID.randomUUID().toString();
            // Defining the child of storageReference
            StorageReference ref
                    = storageReference.child("images/"+ id);

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Uri downloadUrl = uri;
                                            //Do what you want with the url
                                            store_data_in_database(id,downloadUrl.toString());
                                            progressDialog.dismiss();
                                            Toast.makeText(HomeActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(HomeActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+ (int)progress + "%");
                                }
                            });
        }
    }

    private void store_data_in_database(String id, String download_url) {
        image image = new image();
        image.id = id;
        image.download_url = download_url;

        DatabaseReference imagesRef = databaseRef.child("images");
        imagesRef.push().setValue(image);

    }
}