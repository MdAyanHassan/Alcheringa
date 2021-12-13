package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.Objects;

public class Splash_Screen extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        sharedPreferences=getSharedPreferences("USER",MODE_PRIVATE);
        boolean isLoggedIn=sharedPreferences.getBoolean("isLoggedIn",false);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLoggedIn){
                    startActivity(new Intent(getApplicationContext(),Profile.class));
                    finish();
                }
                else{
                    startActivity(new Intent(getApplicationContext(),SignUp.class));
                    finish();
                }
            }
        },3000);
    }
}