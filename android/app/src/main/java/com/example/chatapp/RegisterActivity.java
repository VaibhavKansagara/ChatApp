package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText et_username, et_password, et_email;
    String username, password, email;
    Button registerBtn;
    Toolbar toolbar;
    Boolean isAdmin;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbarregis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_username = findViewById(R.id.reg_username);
        et_password = findViewById(R.id.reg_password);
        et_email = findViewById(R.id.reg_email);
        registerBtn = findViewById(R.id.register_Account_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        // extract info regarding whether user is admin or not.
        Bundle extras = getIntent().getExtras();
        isAdmin = extras.getBoolean("isAdmin");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = et_username.getText().toString();
                email = et_email.getText().toString();
                password = et_password.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    et_username.setError("Username can't be empty");
                } else if (TextUtils.isEmpty(email)) {
                    et_email.setError("Email can't be empty");
                } else if (TextUtils.isEmpty(password)) {
                    et_password.setError("Password can't be empty");
                } else if (password.length() < 6) {
                    et_password.setError("Password length must be greater than 5");
                } else {
                    registerUser(username, email, password);
                }
            }
        });
    }

    private void registerUser(String username, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                            .child(user.getUid());

                    if (user != null) {
                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        HashMap<String, Object> issuedBooks = new HashMap<>();
                        hashMap.put("username", username);
                        hashMap.put("email", email);
                        hashMap.put("password", password);
                        hashMap.put("isAdmin", isAdmin);
                        issuedBooks.put("dummy", "dummy");
                        hashMap.put("IssuedBooks", issuedBooks);
                        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this,
                                            "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this,
                                            StartActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("isAdmin", isAdmin);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}