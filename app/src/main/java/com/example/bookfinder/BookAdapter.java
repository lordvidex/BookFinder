package com.example.bookfinder;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, List<Book> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if(listView==null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        //Set the image URI
        ImageView bookImage = listView.findViewById(R.id.bookImage);
        Picasso.with(getContext()).load(currentBook.getmBookImage()).into(bookImage);

        //Set the book Title
        TextView bookTitle = listView.findViewById(R.id.book_title);
        bookTitle.setText(currentBook.getmBookTitle());

        //Set the book Author
        TextView bookAuthor = listView.findViewById(R.id.book_author);
        bookAuthor.setText(currentBook.getmBookAuthor());

        //return completed view
        return listView;
    }
}
