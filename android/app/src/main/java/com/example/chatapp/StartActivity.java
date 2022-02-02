package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // cast the views
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerbtn);

        // extract info regarding whether user is admin or not.
        Bundle extras = getIntent().getExtras();
        Boolean isAdmin = extras.getBoolean("isAdmin");

        // redirect the views
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (isAdmin) {
                    intent = new Intent(StartActivity.this, LoginAdminActivity.class);
                } else {
                    intent = new Intent(StartActivity.this, LoginActivity.class);
                }
                intent.putExtra("isAdmin", isAdmin);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
                intent.putExtra("isAdmin", isAdmin);
                startActivity(intent);
            }
        });
    }

}