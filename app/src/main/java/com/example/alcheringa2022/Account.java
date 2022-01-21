package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Account extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    View view, your_orders, contactus_view, profile_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        firebaseAuth=FirebaseAuth.getInstance();
        view=findViewById(R.id.signout_button);
        contactus_view=findViewById(R.id.contact_us_button);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        contactus_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AlcherActivity.class));
            }});

        your_orders=findViewById(R.id.ur_orders_button);
        your_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),YourOrders.class));
            }
        });

        profile_page=findViewById(R.id.user_name_button);
        profile_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfilePage.class));
            }
        });


    }
}