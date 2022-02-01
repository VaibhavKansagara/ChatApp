package com.example.chatapp.dao;

import com.example.chatapp.models.Book;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class BookDao {
    private final DatabaseReference databaseReference;

    public BookDao() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Book.class.getSimpleName());
    }

    public Task<Void> add(Book book) {
        return databaseReference.push().setValue(book);
    }

    public Task<Void> update(String key, HashMap<String, Object> map) {
        return databaseReference.child(key).updateChildren(map);
    }

    public Task<Void> subtractNoofCopies(String key, int noofcopies) {
        return databaseReference.child(key).child("noofcopies")
                .setValue(noofcopies - 1);
    }

    public Task<Void> addNoofCopies(String key, int noofcopies) {
        return databaseReference.child(key).child("noofcopies")
                .setValue(noofcopies + 1);
    }

    public Task<Void> remove(String key) {
        return databaseReference.child(key).removeValue();
    }

    public DatabaseReference get() { return databaseReference; }

    public Query getList() {
        return databaseReference.orderByKey();
    }
}
