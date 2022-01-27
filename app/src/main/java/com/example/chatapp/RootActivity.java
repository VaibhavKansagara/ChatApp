package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RootActivity extends AppCompatActivity {

    Button admin, student;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        admin = findViewById(R.id.adminBtn);
        student = findViewById(R.id.studentBtn);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RootActivity.this, StartActivity.class);
                intent.putExtra("isAdmin", true);
                startActivity(intent);
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RootActivity.this, StartActivity.class);
                intent.putExtra("isAdmin", false);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                    .child(user.getUid());
            databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    HashMap<String, Object> userDetails = (HashMap<String, Object>) task.getResult().getValue();
                    Boolean isAdmin = (Boolean) userDetails.get("isAdmin");
                    if (isAdmin) {
                        Toast.makeText(RootActivity.this,
                                "This is Admin", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RootActivity.this,
                                "This is Student", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            startActivity(new Intent(RootActivity.this, MainActivity.class));
        }
    }
}