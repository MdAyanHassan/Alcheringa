package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class InterestsActivity extends AppCompatActivity {

    ArrayList<String> interests;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        interests = new ArrayList<>();
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
    }

    public void interestItemClick(View v){
        TextView t = (TextView) v;

        int color=t.getCurrentTextColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & color));

        if(hexColor.equals("#FFFFFF")){
            t.setBackgroundResource(R.drawable.interests_highlighted);
            t.setTextColor(Color.parseColor("#EE6337"));
            interests.add(t.getText().toString());
        }else{
            t.setBackgroundResource(R.drawable.interests);
            t.setTextColor(Color.parseColor("#FFFFFF"));
            interests.remove(t.getText().toString());
        }

    }

    public void submit(View v){
        Set<String> set = new HashSet<>(interests);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("interests", set);
        editor.apply();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}