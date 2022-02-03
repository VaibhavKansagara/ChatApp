package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.dao.BookDao;
import com.example.chatapp.dao.UserDao;
import com.example.chatapp.models.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BookDetailsActivity extends AppCompatActivity {

    DatePickerDialog picker;
    TextView tv_title, tv_author, tv_publisher, tv_cost;
    MaterialEditText fromDate, toDate;
    Button issueBtn, returnBtn;
    Book book;

    FirebaseUser user;
    HashMap<String, Object> issuedBooks;
    DatabaseReference databaseReference;

    UserDao userDao;
    BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        tv_title = findViewById(R.id.book_title);
        tv_author = findViewById(R.id.book_author);
        tv_publisher = findViewById(R.id.book_publisher);
        tv_cost = findViewById(R.id.book_cost);
        issueBtn = findViewById(R.id.issueBtn);
        returnBtn = findViewById(R.id.returnBtn);
        fromDate = findViewById(R.id.date_from);
        toDate = findViewById(R.id.date_to);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userDao = new UserDao();
        databaseReference = userDao.get().child(user.getUid());

        bookDao = new BookDao();

        getDate(fromDate);
        getDate(toDate);

        Bundle extras = getIntent().getExtras();
        setBook(extras);
        setViews();


        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                HashMap<String, Object> userDetails = (HashMap<String, Object>) task.getResult().getValue();
                issuedBooks = (HashMap<String, Object>) userDetails.get("IssuedBooks");
                if (issuedBooks.get(book.getKey()) == null) {
                    issueBtn.setEnabled(true);
                    returnBtn.setEnabled(false);
                    fromDate.setEnabled(true);
                    toDate.setEnabled(true);
                } else {
                    issueBtn.setEnabled(false);
                    returnBtn.setEnabled(true);
                    fromDate.setEnabled(false);
                    toDate.setEnabled(false);
                }
            }
        });

        issueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check the details of book
                if (book.getNoofcopies() > 0) {
                    // this is an independent operation
                    bookDao.subtractNoofCopies(book.getKey(), book.getNoofcopies());

                    // Update the details in User database
                    userDao.updateUserBooksIssuedList(user.getUid(), "IssuedBooks",
                            book.getKey(), toDate.getText().toString(), issuedBooks, "add")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // make a toast
                                    Toast.makeText(BookDetailsActivity.this,
                                            "Book Issued Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BookDetailsActivity.this, ListBooksStudentIssueActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                    });
                } else {
                    Toast.makeText(BookDetailsActivity.this,
                            "There are no copies available", Toast.LENGTH_SHORT).show();
                    // Redirect
                    Intent intent = new Intent(BookDetailsActivity.this, ListBooksStudentIssueActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this is an independent operation
                bookDao.addNoofCopies(book.getKey(), book.getNoofcopies());

                String lastDate = "";
                // Update the details in User database
                userDao.updateUserBooksIssuedList(user.getUid(), "IssuedBooks",
                        book.getKey(), lastDate, issuedBooks, "remove")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // make a toast
                                    Toast.makeText(BookDetailsActivity.this,
                                            "Book Returned Successfully with fine:" + getFine(lastDate), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BookDetailsActivity.this, ListBooksStudentIssueActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
    }

    private String getFine(String lastDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date returnDate, maximumDate;
        try{
            returnDate = new Date();
            maximumDate = dateFormat.parse(lastDate);
        } catch (ParseException e) {
            returnDate = new Date();
            maximumDate = new Date();
        }
        long diff = returnDate.getTime() - maximumDate.getTime();

        return String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    private void setBook(Bundle extras) {
        book = new Book();
        book.setTitle(extras.getString("title"));
        book.setAuthor(extras.getString("author"));
        book.setKey(extras.getString("key"));
        book.setPublisher(extras.getString("publisher"));
        book.setNoofcopies(extras.getInt("noofcopies"));
        book.setYear(extras.getInt("year"));
        book.setCost(extras.getDouble("cost"));
    }

    private void setViews() {
        tv_title.setText(book.getTitle());
        tv_author.setText(book.getAuthor());
        tv_publisher.setText(book.getPublisher());
        tv_cost.setText(String.valueOf(book.getCost()));
    }

    private void getDate(MaterialEditText text) {
        text.setInputType(InputType.TYPE_NULL);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(BookDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                text.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });
    }
}