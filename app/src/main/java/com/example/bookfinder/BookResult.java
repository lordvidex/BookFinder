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
    private String bookUrlPrefix = "https://www.googleapis.com/books/v1/volumes";
    private Button researchBtn;
    private String searchQuery;
    TextView headerSubView;
    EditText searchField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_result);
        headerSubView = findViewById(R.id.headerSubVie);
        searchField = findViewById(R.id.searchFiel);
        Intent intent = getIntent();
        searchQuery = intent.getStringExtra("query");
        headerSubView.setText(searchQuery);
        searchField.setText(searchQuery);
        researchBtn = findViewById(R.id.btn_searchBoo);
        researchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestart();
            }
        });
        performTask();
        }

    private void performTask() {
        String splittedUrl = performQuerySplit(searchQuery);
        String searchUrl = bookUrlPrefix+"?q="+splittedUrl;
        Log.i("Evans:",searchUrl);
        BookAsyncTask bookAsyncTask = new BookAsyncTask();
        bookAsyncTask.execute(searchUrl);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        headerSubView = findViewById(R.id.headerSubVie);
        searchField = findViewById(R.id.searchFiel);
        searchQuery = searchField.getText().toString();
        headerSubView.setText(searchQuery);
        searchField.setText(searchQuery);
        performTask();
    }

    private String performQuerySplit(String searchQuery) {
        String splitted = searchQuery.replace(" ", "+");
        return splitted;
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
