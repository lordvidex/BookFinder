package com.example.bookfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchText = findViewById(R.id.searchField);
        button = findViewById(R.id.btn_searchBook);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchText.getText().toString();
                Intent searchIntent = new Intent(MainActivity.this, BookResult.class);
                searchIntent.putExtra("query", query);
                startActivity(searchIntent);
            }
        });
    }
}
