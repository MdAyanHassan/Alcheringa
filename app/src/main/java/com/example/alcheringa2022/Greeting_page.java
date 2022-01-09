package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Greeting_page extends AppCompatActivity {
    Button signupButton,Login_button;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting_page);
        signupButton=findViewById(R.id.SignUp_button);
        Login_button=findViewById(R.id.Login_button);
        firebaseAuth=FirebaseAuth.getInstance();
        Login_button.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
        signupButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),SignUp.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}