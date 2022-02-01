package com.example.chatapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chatapp.BookDetailsActivity;
import com.example.chatapp.R;
import com.example.chatapp.models.Book;

import java.util.ArrayList;

public class ListViewAdapterStudentIssue extends ArrayAdapter<Book> {
    ArrayList<Book> list;
    Context context;
    Book currBook;

    public ListViewAdapterStudentIssue(Context context, ArrayList<Book> items) {
        super(context, R.layout.list_rows_student_issue, items);
        this.context = context;
        list = items;
    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_rows_student_issue, null);
            TextView name = convertView.findViewById(R.id.name);
            Button issue = convertView.findViewById(R.id.issueBtn);
            TextView number = convertView.findViewById(R.id.number);

            number.setText(position + 1 + ".");

            currBook = list.get(position);
            name.setText(currBook.getTitle());

            // Listeners for duplicating and removing an item.
            // They use the static removeItem and addItem methods created in MainActivity.
            issue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BookDetailsActivity.class);
                    putExtras(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    private void putExtras(Intent intent) {
        intent.putExtra("title", currBook.getTitle());
        intent.putExtra("author", currBook.getAuthor());
        intent.putExtra("publisher", currBook.getPublisher());
        intent.putExtra("year", currBook.getYear());
        intent.putExtra("noofcopies", currBook.getNoofcopies());
        intent.putExtra("cost", currBook.getCost());
        intent.putExtra("key", currBook.getKey());
    }
}
