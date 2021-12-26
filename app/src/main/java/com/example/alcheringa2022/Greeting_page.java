package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Greeting_page extends AppCompatActivity {
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting_page);
        signupButton=findViewById(R.id.SignUp_button);
        signupButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),SignUp.class)));
    }
}