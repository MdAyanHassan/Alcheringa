package com.example.alcheringa2022;





import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    about About;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        About = new about();
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, About).commit();
                break;
            case R.id.home2:
                Home home = new Home();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, home).commit();

                break ;
            case R.id.contact_us:
                contact_us contact_us = new contact_us();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, contact_us).commit();

                break;

        }
        return false;
    }
}