package com.example.alcheringa2022;

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
    TextView detailsText1, detailsText2, detailsText3;
    LinearLayout faq1, faq2, faq3;
    ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_page);

        contact_us = findViewById(R.id.contact_us_button);
        contact_us.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),ContactUs.class)));

        detailsText1 = findViewById(R.id.details1);
        detailsText2 = findViewById(R.id.details2);
        detailsText3 = findViewById(R.id.details3);

        faq1 = findViewById(R.id.faq1);
        faq1.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq1.setOnClickListener(v -> expand(faq1, detailsText1));

        faq2 = findViewById(R.id.faq2);
        faq2.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq2.setOnClickListener(v -> expand(faq2, detailsText2));

        faq3 = findViewById(R.id.faq3);
        faq3.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        faq3.setOnClickListener(v -> expand(faq3, detailsText3));

        back_btn=findViewById(R.id.backbtn);
        back_btn.setOnClickListener(v -> finish());
    }

    public void expand(ViewGroup view, View detailsText) {
        int v1 = (detailsText.getVisibility() ==View.GONE)? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(view, new AutoTransition());
        detailsText.setVisibility(v1);
    }
}