package com.example.bookfinder;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    //instantiates a private String to hold the URL for the background task
    private String mUrl;
    /**
     * @param context
     * @param stringUrl
     * @deprecated
     */
    public BookLoader(Context context,String stringUrl) {
        super(context);
        mUrl = stringUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if(mUrl==null){
            return null;
        }
        Log.i("pretty","loadInBack...");
        return QueryUtils.performNetworkRequest(mUrl);
    }
}
