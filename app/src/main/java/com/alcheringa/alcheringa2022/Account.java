package com.alcheringa.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.microsoft.graph.models.extensions.Team;

public class Account extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    FirebaseAuth firebaseAuth;
    View view, your_orders, contactus_view, profile_page, faq_page, tnc_page, privacy_page, about_page, sponsor_page, team_page;
    TextView user_name,version,website;
    ImageView user_photo;
    ImageView backbtn;
    String shared_name, shared_photoUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        firebaseAuth=FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        version=findViewById(R.id.version);
        view=findViewById(R.id.signout_button);
        contactus_view=findViewById(R.id.contact_us_button);
        website=findViewById(R.id.website_link);

        user_name = findViewById(R.id.user_name_text);
        user_photo = findViewById(R.id.user_photo);

        shared_name = sharedPreferences.getString("name", "");
        shared_photoUrl = sharedPreferences.getString("photourl", "");
        backbtn=findViewById(R.id.backbtn);
        backbtn.setOnClickListener(view -> finish());

        website.setOnClickListener(v->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.alcheringa.in"))));

        if(!shared_name.equals(""))
        {
            user_name.setText(shared_name);
        }

        if(!shared_photoUrl.equals(""))
        {
            Glide.with(this).load(shared_photoUrl).into(user_photo);
        }

        view.setOnClickListener(v -> {
            firebaseAuth.signOut();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("name");
            editor.remove("email");
            editor.remove("photourl");
            editor.remove("interests");
            editor.apply();
            Intent intent = new Intent(getApplicationContext(),Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        version.setText("V "+BuildConfig.VERSION_NAME);
        contactus_view.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ContactUs.class)));

        contactus_view.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ContactUs.class)));

        your_orders=findViewById(R.id.ur_orders_button);
        your_orders.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),YourOrders.class)));

        profile_page=findViewById(R.id.profile_button);
        profile_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),ProfilePage.class)));

        faq_page=findViewById(R.id.faq_button);
        faq_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),FaqPage.class)));

        tnc_page=findViewById(R.id.tnc);
        tnc_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),TermsAndConditions.class)));

        privacy_page=findViewById(R.id.privacy_policy);
        privacy_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),PrivacyPolicy.class)));

        about_page=findViewById(R.id.about);
        about_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),AboutPage.class)));

        sponsor_page=findViewById(R.id.sponsor_button);
        sponsor_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Sponsors.class)));

        team_page=findViewById(R.id.team_button);
        team_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), team.class)));
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