package com.example.bookfinder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

        //Set the books ratings
        RatingBar ratingBar = listView.findViewById(R.id.ratingsBar);
        float ratings = (float)currentBook.getmRatings();
        ratingBar.setRating(ratings);

        //Set the number of people who rated the Book
        TextView numOfReviews = listView.findViewById(R.id.txt_ratings);
        int number_of_ratings = currentBook.getmNumberOfPeopleWhoRated();
        String edited_number_of_ratings;
        if(number_of_ratings==0){
            edited_number_of_ratings = number_of_ratings + " review";
            numOfReviews.setTypeface(null, Typeface.ITALIC);
        }else if(number_of_ratings==1){
            edited_number_of_ratings = number_of_ratings+ " review";
            numOfReviews.setTypeface(null,Typeface.NORMAL);
        }else{
            edited_number_of_ratings = number_of_ratings + " reviews";
            numOfReviews.setTypeface(null,Typeface.NORMAL);
        }
            numOfReviews.setText(edited_number_of_ratings);

        //Set the number of Pages
        TextView numPages = listView.findViewById(R.id.book_pages);
        String postfix;
        switch (currentBook.getmPages()){
            case 0:
                numPages.setTypeface(null,Typeface.ITALIC);
                numPages.setTextColor(ContextCompat.getColor(getContext(),R.color.red));
                postfix = "Number of pages not specified!";
                break;
            case 1:
                postfix = "1 page";
                numPages.setTypeface(null,Typeface.NORMAL);
                numPages.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
                break;
            default:
                numPages.setTypeface(null,Typeface.NORMAL);
                postfix = currentBook.getmPages()+" pages";
                numPages.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
                break;
        }
        numPages.setText(postfix);

        //return completed view
        return listView;
    }
}
