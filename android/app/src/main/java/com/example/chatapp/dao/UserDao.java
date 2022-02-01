package com.example.chatapp.dao;

import androidx.annotation.NonNull;

import com.example.chatapp.models.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private final DatabaseReference databaseReference;

    public UserDao() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");
    }

    public Task<Void> updateUserBooksIssuedList(String userId, String childKey, String book_key,
                                                String lastDate, HashMap<String, Object> issuedBooks,
                                                String operation) {
        if (operation.equals("add")) {
            issuedBooks.put(book_key, lastDate);
        } else {
            lastDate = issuedBooks.get(book_key).toString();
            issuedBooks.remove(book_key);
        }
        return databaseReference.child(userId).child(childKey).setValue(issuedBooks);
    }

    public Task<Void> remove(String key) {
        return databaseReference.child(key).removeValue();
    }

    public DatabaseReference get() { return databaseReference; }

    public Query getList() {
        return databaseReference.orderByKey();
    }
}
