package com.alcheringa.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FaqPage extends AppCompatActivity {

    TextView contact_us;
    TextView detailsText1, detailsText2,detailsText4, detailsText5, detailsText6,detailsText7, detailsText8,detailsText9,detailsText10, detailsText11, detailsText12,detailsText13;
    LinearLayout faq1, faq2,faq4, faq5, faq6,faq7, faq8, faq9,faq10, faq12, faq13;
    ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_page);

        contact_us = findViewById(R.id.contact_us_button);
        contact_us.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),ContactUs.class)));

        detailsText1 = findViewById(R.id.details1);
        detailsText2 = findViewById(R.id.details2);

        detailsText4 = findViewById(R.id.details4);
        detailsText5 = findViewById(R.id.details5);
        detailsText6 = findViewById(R.id.details6);
        detailsText7 = findViewById(R.id.details7);
        detailsText8 = findViewById(R.id.details8);

        detailsText10 = findViewById(R.id.details10);
        detailsText12 = findViewById(R.id.details12);

        //detailsText13 = findViewById(R.id.details14);




        faq1 = findViewById(R.id.faq1);
        faq1.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq1.setOnClickListener(v -> expand(faq1, detailsText1));



        faq4 = findViewById(R.id.faq4);
        faq4.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq4.setOnClickListener(v -> expand(faq4, detailsText4));

        faq5 = findViewById(R.id.faq5);
        faq5.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq5.setOnClickListener(v -> expand(faq5, detailsText5));

        faq6 = findViewById(R.id.faq6);
        faq6.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq6.setOnClickListener(v -> expand(faq6, detailsText6));

        faq7 = findViewById(R.id.faq7);
        faq7.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq7.setOnClickListener(v -> expand(faq7, detailsText7));

        faq8 = findViewById(R.id.faq8);
        faq8.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq8.setOnClickListener(v -> expand(faq8, detailsText8));

        faq10 = findViewById(R.id.faq10);
        faq10.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq10.setOnClickListener(v -> expand(faq10, detailsText10));


        faq12 = findViewById(R.id.faq12);
        faq12.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq12.setOnClickListener(v -> expand(faq12, detailsText12));

//        faq14 = findViewById(R.id.faq14);
//        faq14.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
//        faq14.setOnClickListener(v -> expand(faq14, detailsText1));


        faq2 = findViewById(R.id.faq2);
        faq2.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq2.setOnClickListener(v -> expand(faq2, detailsText2));



        back_btn=findViewById(R.id.backbtn);
        back_btn.setOnClickListener(v -> finish());
    }

    public void expand(ViewGroup view, View detailsText) {
        int v1 = (detailsText.getVisibility() ==View.GONE)? View.VISIBLE : View.GONE;
        detailsText.setVisibility(v1);
        TransitionManager.beginDelayedTransition(view, new AutoTransition());
    }
}