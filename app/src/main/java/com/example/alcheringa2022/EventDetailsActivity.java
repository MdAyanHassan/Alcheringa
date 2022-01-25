package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.alcheringa2022.R;

public class EventDetailsActivity extends AppCompatActivity {

    LoaderView loaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        loaderView = findViewById(R.id.dots_progress);


        //after loading activity:
        loaderView.setVisibility(View.GONE);
    }
}