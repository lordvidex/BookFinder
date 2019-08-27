package com.example.bookfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BookResult extends AppCompatActivity{
    private static final String BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_result);
        TextView headerSubView = findViewById(R.id.headerSubVie);
        EditText searchField = findViewById(R.id.searchFiel);
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("query");
        headerSubView.setText(searchQuery);
        searchField.setText(searchQuery);
        //String searchUrl = "?q="+searchQuery;
        /**
        * @Info: URL is parsed here
         * */
        //BOOK_URL+=searchUrl;
        BookAsyncTask bookAsyncTask = new BookAsyncTask();
        bookAsyncTask.execute(BOOK_URL);
    }
    private class BookAsyncTask extends AsyncTask<String,Void,List<Book>>{

        @Override
        protected List<Book> doInBackground(String... strings) {
            Log.i("Task: ","Background work started......");
            List<Book> bookList = QueryUtils.performNetworkRequest(strings[0]);
            return bookList;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            final BookAdapter myBookAdapter = new BookAdapter(BookResult.this,books);
            ListView myListView = findViewById(R.id.listView);
            myListView.setAdapter(myBookAdapter);
        }
    }

}
