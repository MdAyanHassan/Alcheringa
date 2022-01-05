package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Greeting_page extends AppCompatActivity {
    Button signupButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting_page);
        signupButton=findViewById(R.id.SignUp_button);
        firebaseAuth=FirebaseAuth.getInstance();
        signupButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),SignUp.class)));
    }
}