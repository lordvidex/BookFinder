package com.example.bookfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.List;

public class BookResult extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private static String searchUrl;
    private LinkedList<Book> loadedBooks;
    private String searchQuery;
    private ProgressBar progressBar;
    TextView headerSubView;
    TextView mEmptyStateTextView;
    EditText searchField;
    LoaderManager loaderManager = getLoaderManager();
    ConnectivityManager cm;
    NetworkInfo networkInfo;
    RecyclerView myRecyclerView;
    BookRecyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_result);
        loadedBooks = new LinkedList<>();
        //recyclerView
         myRecyclerView = findViewById(R.id.recyclerView);
         myRecyclerView.setVisibility(View.VISIBLE);
         mEmptyStateTextView = findViewById(R.id.emptyTextView);
         mEmptyStateTextView.setVisibility(View.GONE);
        mAdapter =new BookRecyclerAdapter(this,loadedBooks);
        myRecyclerView.setAdapter(mAdapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //myListView.setEmptyView(mEmptyStateTextView);

        //ProgressBar
        progressBar = findViewById(R.id.progressBar);

        //headerSubView
        headerSubView = findViewById(R.id.headerSubVie);

        //EditText
        searchField = findViewById(R.id.searchFiel);

        //get Intents values
        Intent intent = getIntent();
        searchQuery = intent.getStringExtra("query");
        //get URL
        performTask();

        headerSubView.setText(searchQuery);
        searchField.setText(searchQuery);

        //Search Button
        Button researchBtn = findViewById(R.id.btn_searchBoo);
        researchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStop();
                onRestart();
            }
        });
        Log.i("pretty",searchQuery);

        //Initialize connection Checkers
        cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
         networkInfo = cm.getActiveNetworkInfo();

        //Loaders
        if(networkInfo!=null&&networkInfo.isConnected()) {
            loaderManager.initLoader(0, null, this);
        }else{
            progressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_network);
        }
    }
    private void performTask() {
        String splittedUrl = performQuerySplit(searchQuery);
        String bookUrlPrefix = "https://www.googleapis.com/books/v1/volumes";
        searchUrl = bookUrlPrefix +"?q="+splittedUrl;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadedBooks.clear();
        myRecyclerView.getAdapter().notifyDataSetChanged();
        headerSubView = findViewById(R.id.headerSubVie);
        searchField = findViewById(R.id.searchFiel);

        searchQuery = searchField.getText().toString();
        headerSubView.setText(searchQuery);
        performTask();
        loaderManager.restartLoader(0,null,this);

    }

    private String performQuerySplit(String searchQuery) {
        String splitted = searchQuery.replace(" ", "+");
        return splitted;
    }
    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.i("pretty","onCreateLoader...");
        if(progressBar.getVisibility()==View.GONE){
            progressBar.setVisibility(View.VISIBLE);
        }
        return new BookLoader(this,searchUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        progressBar.setVisibility(View.GONE);
        //Check for Internet Connection
        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
         networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo==null){
            //No Internet
            mEmptyStateTextView.setText(R.string.no_network);
        }else if(networkInfo.isConnected()){
            mEmptyStateTextView.setText(R.string.no_book);
        }

        if(books!=null&&!books.isEmpty()){
            for(int i = 0;i<books.size();i++){
                loadedBooks.addLast(books.get(i));
            }
            myRecyclerView.getAdapter().notifyDataSetChanged();
        }else{
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            myRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        myRecyclerView.getAdapter().notifyDataSetChanged();
    }

}
