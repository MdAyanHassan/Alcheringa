package com.alcheringa.alcheringa2022;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class HeaderMenu extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    FirebaseAuth firebaseAuth;
    TextView user_name;
    ImageView user_photo;
    String shared_name, shared_photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        firebaseAuth=FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);

        user_name = findViewById(R.id.user_name_text);
        user_photo = findViewById(R.id.user_photo);

        shared_name = sharedPreferences.getString("name", "");
        shared_photoUrl = sharedPreferences.getString("photourl", "");

        if(!shared_name.equals(""))
        {
            user_name.setText(shared_name);
        }

        if(!shared_photoUrl.equals(""))
        {
            Glide.with(this).load(shared_photoUrl).into(user_photo);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        shared_photoUrl = sharedPreferences.getString("photourl", "");
        if(!shared_photoUrl.equals(""))
        {
            Glide.with(this).load(shared_photoUrl).into(user_photo);
        }
    }
}
