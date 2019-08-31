package com.example.bookfinder;

import android.net.Uri;

public class Book {
    private String mBookTitle;
    private String mBookAuthor;
    private Uri mBookImage;
    private String mBookLink;
    private double mRatings;
    private int mPages;
    private int mNumberOfPeopleWhoRated;

    public Book(String bookTitle, String bookAuthor, Uri bookImage, String bookLink, double ratings, int pages,int numberOfPeopleWhoRated) {
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mBookImage = bookImage;
        mBookLink = bookLink;
        mRatings = ratings;
        mPages = pages;
        mNumberOfPeopleWhoRated = numberOfPeopleWhoRated;
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

    public String getmBookLink() { return mBookLink; }

    public int getmNumberOfPeopleWhoRated() {
        return mNumberOfPeopleWhoRated;
    }

    public double getmRatings() { return mRatings; }
    public int getmPages() {
        return mPages;
    }
}
