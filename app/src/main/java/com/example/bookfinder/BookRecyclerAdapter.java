package com.example.bookfinder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder> {
    private Picasso mPicasso;
    private LayoutInflater mInflater;
    private LinkedList<Book> mBooks;
    private Context mContext;
    public BookRecyclerAdapter(Context context, LinkedList<Book>books) {
        mInflater = LayoutInflater.from(context);
        mPicasso = Picasso.with(context);
        mBooks = books;
        mContext = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.activity_list_item,parent,false);
        return new BookViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        //Book Image
        mPicasso.load(mBooks.get(position).getmBookImage()).into(holder.bookImage);

        //Book title
        holder.bookTitle.setText(mBooks.get(position).getmBookTitle());

        //Book Author
        holder.bookAuthor.setText(mBooks.get(position).getmBookAuthor());

        //Book Pages
        TextView bookPageTxtView = holder.bookPages;
        String showedText = "";
        switch(mBooks.get(position).getmPages()){
            case 0:
                bookPageTxtView.setTypeface(null,Typeface.ITALIC);
                bookPageTxtView.setTextColor(ContextCompat.getColor(mContext,R.color.red));
                showedText = "Number of pages not specified!";
                break;
            case 1:
                showedText = "1 page";
                bookPageTxtView.setTypeface(null,Typeface.NORMAL);
                bookPageTxtView.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimaryDark));
                break;
            default:
                bookPageTxtView.setTypeface(null,Typeface.NORMAL);
                showedText = mBooks.get(position).getmPages()+" pages";
                bookPageTxtView.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimaryDark));
                break;
        }
        bookPageTxtView.setText(showedText);

        //Book ratings
        holder.bookRating.setRating((float) mBooks.get(position).getmRatings());

        //Ratings Count
        String ratingsCountText = "";
        TextView ratingsTextView = holder.numberOfRating;
        switch (mBooks.get(position).getmNumberOfPeopleWhoRated()) {
            case 0:
                ratingsCountText = mBooks.get(position).getmNumberOfPeopleWhoRated() + " review";
                ratingsTextView.setTypeface(null,Typeface.ITALIC);
                break;
            case 1:
                ratingsCountText = mBooks.get(position).getmNumberOfPeopleWhoRated()+ " review";
                ratingsTextView.setTypeface(null,Typeface.NORMAL);
                break;
            default:
                ratingsCountText = mBooks.get(position).getmNumberOfPeopleWhoRated()+ " reviews";
                ratingsTextView.setTypeface(null,Typeface.NORMAL);
                break;
        }
        ratingsTextView.setText(ratingsCountText);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        final ImageView bookImage;
        final TextView bookTitle;
        final TextView bookAuthor;
        final TextView bookPages;
        final RatingBar bookRating;
        final TextView numberOfRating;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.bookImage);
            bookTitle =itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookPages = itemView.findViewById(R.id.book_pages);
            bookRating = itemView.findViewById(R.id.ratingsBar);
            numberOfRating = itemView.findViewById(R.id.txt_ratings);
        }
    }
}
