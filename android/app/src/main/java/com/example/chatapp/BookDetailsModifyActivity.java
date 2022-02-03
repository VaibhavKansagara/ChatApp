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

import java.util.HashMap;

public class BookDetailsModifyActivity extends AppCompatActivity {
    MaterialEditText et_title, et_author, et_publisher, et_cost, et_year, et_noofcopies;
    String title, author, publisher, key;
    int year, noofcopies;
    double cost;
    Toolbar toolbar;
    Button modifyBtn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details_modify);

        toolbar = findViewById(R.id.toolbarmodifybook);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Modify Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_title = findViewById(R.id.book_title);
        et_author = findViewById(R.id.author);
        et_publisher = findViewById(R.id.publisher);
        et_cost = findViewById(R.id.cost);
        et_year = findViewById(R.id.year);
        et_noofcopies = findViewById(R.id.noofcopies);

        // extract info regarding whether user is admin or not.
        Bundle extras = getIntent().getExtras();
        title = extras.getString("title");
        author = extras.getString("author");
        publisher = extras.getString("publisher");
        key = extras.getString("key");
        year = extras.getInt("year");
        noofcopies = extras.getInt("noofcopies");
        cost = extras.getDouble("cost");

        // Set the fields from intent from previous activity
        et_title.setText(title);
        et_author.setText(author);
        et_publisher.setText(publisher);
        et_year.setText(String.valueOf(year));
        et_noofcopies.setText(String.valueOf(noofcopies));
        et_cost.setText(String.valueOf(cost));

        modifyBtn = findViewById(R.id.modifyBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        modifyBtn.setOnClickListener(new View.OnClickListener() {
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
                    modifyBook(title, publisher, author, cost, year, noofcopies, key);
                }
            }
        });
    }

    private void modifyBook(String title, String publisher, String author, double cost,
                            int year, int noofcopies, String key) {
        BookDao bookDao = new BookDao();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title", title);
        hashMap.put("publisher", publisher);
        hashMap.put("author", author);
        hashMap.put("cost", cost);
        hashMap.put("year", year);
        hashMap.put("noofcopies", noofcopies);

        bookDao.update(key, hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(BookDetailsModifyActivity.this, "Book Successfully modified",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookDetailsModifyActivity.this,
                            ListBooksAdminActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}