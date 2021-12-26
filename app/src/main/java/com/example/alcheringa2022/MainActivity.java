package com.example.alcheringa2022;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    Events_Fragment events_fragment;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        events_fragment = new Events_Fragment();
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);
//        if(!isLoggedIn){
//            Intent intent = new Intent(this, SignUp.class);
//            startActivity(intent);
//        }

        bottomNavigationView.setSelectedItemId(R.id.home);



    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, events_fragment).commit();
                break;
            case R.id.home_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Home()).commit();

                break ;
            case R.id.merch:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Merch()).commit();

                break;
            case R.id.schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Schedule()).commit();
                break;

        }
        return false;
    }
}