package com.example.alcheringa2022;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    Events events_fragment;
    int index;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        events_fragment = new Events();
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);
        /*if(!isLoggedIn){
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }*/



        bottomNavigationView.setSelectedItemId(R.id.home_nav);
        index=R.id.home_nav;

        try{
            Intent intent = getIntent();
            String fragment_name = intent.getExtras().getString("fragment");
            if(fragment_name.equals("merch")){
                bottomNavigationView.setSelectedItemId(R.id.merch);
                index=R.id.merch;
            }
        }catch(Exception ignored){}



    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.events:
                if(index!=R.id.events){
                    index=R.id.events;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, events_fragment).commit();
                    bottomNavigationView.getMenu().findItem(R.id.events).setChecked(true);
                }

                break;
            case R.id.home_nav:
                if(index!=R.id.home_nav) {
                    index=R.id.home_nav;

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Home()).commit();
                    bottomNavigationView.getMenu().findItem(R.id.home_nav).setChecked(true);
                }
                break ;
            case R.id.merch:
                if(index!=R.id.merch) {
                    index=R.id.merch;

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new MerchFragment()).commit();
                    bottomNavigationView.getMenu().findItem(R.id.merch).setChecked(true);
                }
                break;
            case R.id.schedule:
                if(index!=R.id.schedule) {
                    index=R.id.schedule;

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Schedule()).commit();
                    bottomNavigationView.getMenu().findItem(R.id.schedule).setChecked(true);
                }
                break;
        }
        return false;
    }
}