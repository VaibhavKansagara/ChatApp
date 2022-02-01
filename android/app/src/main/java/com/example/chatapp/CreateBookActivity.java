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

import com.example.chatapp.dao.BookDao;
import com.example.chatapp.models.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class CreateBookActivity extends AppCompatActivity {

    MaterialEditText et_title, et_author, et_publisher, et_cost, et_year, et_noofcopies;
    String title, author, publisher;
    int year, noofcopies;
    double cost;
    Toolbar toolbar;
    Button addBtn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);

        toolbar = findViewById(R.id.toolbarcreatebook);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_title = findViewById(R.id.book_title);
        et_author = findViewById(R.id.author);
        et_publisher = findViewById(R.id.publisher);
        et_cost = findViewById(R.id.cost);
        et_year = findViewById(R.id.year);
        et_noofcopies = findViewById(R.id.noofcopies);

        addBtn = findViewById(R.id.addBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getText().toString();
                author = et_author.getText().toString();
                publisher = et_publisher.getText().toString();
                cost = Double.parseDouble(et_cost.getText().toString());
                year = Integer.parseInt(et_year.getText().toString());
                noofcopies = Integer.parseInt(et_noofcopies.getText().toString());

                if (TextUtils.isEmpty(title)) {
                    et_title.setError("Email can't be empty");
                } else if (TextUtils.isEmpty(author)) {
                    et_author.setError("Password can't be empty");
                } else if (TextUtils.isEmpty(publisher)) {
                    et_author.setError("Publisher can't be empty");
                } else if (TextUtils.isEmpty(et_cost.getText().toString())){
                    et_cost.setError("Cost can't be empty");
                } else if (TextUtils.isEmpty(et_year.getText().toString())) {
                    et_year.setError("Year can't be empty");
                } else if (TextUtils.isEmpty(et_noofcopies.getText().toString())) {
                    et_noofcopies.setError("NoofCopies can't be empty");
                } else {
                    createBook(title, publisher, author, cost, year, noofcopies);
                }
            }
        });
    }

    private void createBook(String title, String publisher, String author, double cost,
                            int year, int noofcopies) {
        BookDao bookDao = new BookDao();
        bookDao.add(new Book(title,author,year, noofcopies, publisher,
                cost)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateBookActivity.this, "Book Successfully added",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateBookActivity.this, AdminStartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}