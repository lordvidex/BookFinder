package com.example.bookfinder;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static ArrayList<Book> performNetworkRequest(String stringUrl){
        URL url = createUrl(stringUrl);
        String jsonFile = null;
        try {
            jsonFile = performNetworkConnection(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        //Extract useful data from the JSON string
        ArrayList<Book> books = extractFromJson(jsonFile);
        return books;
    }

    private static ArrayList<Book> extractFromJson(String jsonFile) {
        try {
            ArrayList<Book> books = new ArrayList<>();
            JSONObject rootObject = new JSONObject(jsonFile);
            JSONArray items = rootObject.optJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject eachItem = items.getJSONObject(i);
                JSONObject volumeInfo = eachItem.getJSONObject("volumeInfo");

                /**
                 * @var: bookTitle
                 * get the Book title
                 */
                String bookTitle = volumeInfo.getString("title");

                //get the book Authors
                JSONArray listOfAuthors = volumeInfo.getJSONArray("authors");
                String Author = listOfAuthors.getString(0);

            //Get the User Image URI
            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
            String imageUriString = imageLinks.getString("thumbnail");
            Uri imageUri = Uri.parse(imageUriString);
            books.add(i, new Book(bookTitle, Author,imageUri));
        }
            return books;
        } catch (JSONException e) {
            Log.e("QueryUtils","Problem parsing the Book JSON file");
        }

        return null;
    }

    private static String performNetworkConnection(URL url) throws IOException {
        String jsonFile = "";
        if (url == null) {
            return jsonFile;
        }
        HttpURLConnection connection = null;
        InputStream inputStream = null;
    try{
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(10000);
        connection.connect();

        //check if the connection worked
        if (connection.getResponseCode() == 200) {
            inputStream = connection.getInputStream();
            jsonFile = readFromInputStream(inputStream);
        } else {
            Log.e("LOG_TAG", "Error connecting :" + connection.getResponseCode());
        }
    }catch(IOException e){
            Log.e(LOG_TAG,"Problem getting Book",e);
        }finally {
        if(connection!= null){
            connection.disconnect();
        }
        if(inputStream!=null){
            inputStream.close();
        }
    }
        return jsonFile;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader realReader = new BufferedReader(inputStreamReader);
            String reader = realReader.readLine();
            while(reader!=null){
                output.append(reader);
                reader=realReader.readLine();
            }
        }
        return output.toString();

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
