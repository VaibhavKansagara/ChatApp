package com.example.chatapp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.chatapp.dao.BookDao;
import com.example.chatapp.models.Book;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Expose Java to JavaScript. Methods annotated with {@link ReactMethod} are exposed.
 */
final class ActivityStarterModule extends ReactContextBaseJavaModule {

    ActivityStarterModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    /**
     * @return the name of this module. This will be the name used to {@code require()} this module
     * from JavaScript.
     */
    @Override
    public String getName() {
        return "ActivityStarter";
    }

    @ReactMethod
    void navigateToExample() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, RootActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }

    @ReactMethod
    void navigateToViewBooks() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, ListBooksAdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }

    @ReactMethod
    void logout() {
        Activity activity = getCurrentActivity();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (activity != null) {
            firebaseAuth.signOut();
            Intent intent = new Intent(activity, RootActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    @ReactMethod
    void createBook(String title, String publisher, String author, String cost,
                    String year, String noofcopies) {
        Activity activity = getCurrentActivity();
        BookDao bookDao = new BookDao();

        Double doubleCost = Double.parseDouble(cost);
        int intYear = Integer.parseInt(year);
        int intNoofcopies = Integer.parseInt(noofcopies);

        bookDao.add(new Book(title,author,intYear, intNoofcopies, publisher,
                doubleCost)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, "Book Successfully added",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @ReactMethod
    private void loginUser(String email, String password) {
        Activity activity = getCurrentActivity();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, "Logged in Successfully",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
