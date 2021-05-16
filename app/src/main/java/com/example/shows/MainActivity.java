package com.example.shows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shows.model.database.DatabaseShows;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  DatabaseShows databaseShows = DatabaseShows.getInstance(this);


    }
}