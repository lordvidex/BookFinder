package com.example.bookfinder;

import android.net.Uri;

public class Book {
    private String mBookTitle;
    private String mBookAuthor;
    private Uri mBookImage;
    public Book(String bookTitle, String bookAuthor,Uri bookImage) {
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mBookImage = bookImage;

    }

    public Uri getmBookImage() {
        return mBookImage;
    }

    public String getmBookTitle() {
        return mBookTitle;
    }

    public String getmBookAuthor() {
        return mBookAuthor;
    }

}
