package com.geniobits.firebaseauthdemo2.Login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.geniobits.firebaseauthdemo2.Login.Model.User;
import com.geniobits.firebaseauthdemo2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        EditText editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        EditText editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        EditText editTextTextPostalAddress = findViewById(R.id.editTextTextPostalAddress);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if(user.getEmail()!=null || !user.getEmail().isEmpty()){
            editTextTextEmailAddress.setText(user.getEmail());
        }

        if(user.getPhoneNumber()!=null || !user.getPhoneNumber().isEmpty()){
            editTextPhone.setText(user.getPhoneNumber());
        }

        mDatabase.child("users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    try {
                        User user1 = task.getResult().getValue(User.class);
                        editTextPhone.setText(user1.phone_number);
                        editTextTextEmailAddress.setText(user1.email);
                        editTextTextPersonName.setText(user1.username);
                        editTextTextPostalAddress.setText(user1.address);
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    }catch (Exception e){}
                }
            }
        });

        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user1 = new User();
                user1.id = user.getUid();
                user1.username = editTextTextPersonName.getText().toString();
                user1.email = editTextTextEmailAddress.getText().toString();
                user1.phone_number = editTextPhone.getText().toString();
                user1.address = editTextTextPostalAddress.getText().toString();
                mDatabase.child("users").child(user.getUid()).setValue(user1);
                onBackPressed();

            }
        });

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}