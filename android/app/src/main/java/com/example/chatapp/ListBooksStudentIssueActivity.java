package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chatapp.adapter.ListViewAdapterAdmin;
import com.example.chatapp.adapter.ListViewAdapterStudentIssue;
import com.example.chatapp.dao.BookDao;
import com.example.chatapp.models.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ListBooksStudentIssueActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapterStudentIssue listViewAdapterStudentIssue;
    ArrayList<Book> book_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_student_issue);
        listView = findViewById(R.id.list);

        book_names = new ArrayList<>();

        listViewAdapterStudentIssue = new ListViewAdapterStudentIssue(ListBooksStudentIssueActivity.this, book_names);
        listView.setAdapter(listViewAdapterStudentIssue);

        BookDao dao = new BookDao();
        Query query = dao.getList();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    HashMap<String, Object> book = (HashMap<String, Object>) dataSnapshot.getValue();
                    book.put("key", dataSnapshot.getKey());
                    listViewAdapterStudentIssue.add(getBook(book));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do Something
            }
        });
    }

    private Book getBook(HashMap<String, Object> map) {
        Book book = new Book(map.get("title").toString(),
                map.get("author").toString(),
                Integer.parseInt(map.get("year").toString()),
                Integer.parseInt(map.get("noofcopies").toString()),
                map.get("publisher").toString(),
                Double.parseDouble(map.get("cost").toString()));
        book.setKey(map.get("key").toString());

        return book;
    }
}