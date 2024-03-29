package com.example.bookfinder;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        ArrayList<Book> books = new ArrayList<>();
        Uri imageUri;
        try {
            JSONObject rootObject = new JSONObject(jsonFile);
            JSONArray items = rootObject.optJSONArray("items");
            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    JSONObject eachItem = items.getJSONObject(i);
                    JSONObject volumeInfo = eachItem.getJSONObject("volumeInfo");

                    /*
                     * var: bookTitle
                     * get the Book title
                     */
                    String bookTitle = volumeInfo.getString("title");
                    Log.i("Loop","string"+i);

                    //get the book Authors
                    String Author;
                    try {
                        JSONArray listOfAuthors = volumeInfo.getJSONArray("authors");
                        Author = listOfAuthors.getString(0);
                    }catch(JSONException e){
                        Author = "(No Author Specified!)";
                    }
                    Log.i("Loop","Author"+i);

                    //get the Book ratings
                    double averageRating;
                    try {
                        averageRating = volumeInfo.getDouble("averageRating");
                    }catch(JSONException e){
                        averageRating = 0.0;
                    }
                    Log.i("Loop","Rating"+i);

                    //get the Book URi Link
                    String infoLink = volumeInfo.getString("infoLink");
                    Log.i("Loop","infoLink"+i);

                    //get the Book Pages
                    int bookPages;
                    try {
                        bookPages = volumeInfo.getInt("pageCount");
                    }catch(JSONException e){
                        bookPages = 0;
                    }
                    Log.i("Loop","pages"+i);

                    //get the Book ratingsCount
                    int ratingsCount;
                    try {
                        ratingsCount = volumeInfo.getInt("ratingsCount");
                    } catch(JSONException e){
                        ratingsCount = 0;
                    }
                    //Get the User Image URI
                    try {
                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                        String imageUriString = imageLinks.getString("thumbnail");
                        imageUri = Uri.parse(imageUriString);
                        Log.i("Loop","Image"+i);
                    }catch(JSONException e){
                        Log.i("ImageIssue","Default Image Added");
                        imageUri = Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRO16IDz68_ChB0bL0KVMaqHOtB_ts835Io5cAWd40ZKS2QRL_w");
                    }

                    books.add(i, new Book(bookTitle, Author,imageUri,infoLink,averageRating,bookPages,ratingsCount));
                }

                Log.i(LOG_TAG,"Books have been added..");
                return books;
            }else{
                Log.i(LOG_TAG,"No Books found!..");
                return null;
            }

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
