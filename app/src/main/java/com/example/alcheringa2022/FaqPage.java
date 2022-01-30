package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ContactUs.class));
            }
        });

        detailsText1 = findViewById(R.id.details1);
        detailsText2 = findViewById(R.id.details2);
        detailsText3 = findViewById(R.id.details3);

        faq1 = findViewById(R.id.faq1);
        faq1.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        faq2 = findViewById(R.id.faq2);
        faq2.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        faq3 = findViewById(R.id.faq3);
        faq3.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        back_btn=findViewById(R.id.backbtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void expand1(View view) {
        int v1 = (detailsText1.getVisibility() ==View.GONE)? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(faq1, new AutoTransition());
        detailsText1.setVisibility(v1);
    }

    public void expand2(View view) {
        int v2 = (detailsText2.getVisibility() ==View.GONE)? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(faq2, new AutoTransition());
        detailsText2.setVisibility(v2);
    }

    public void expand3(View view) {
        int v3 = (detailsText3.getVisibility() ==View.GONE)? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(faq3, new AutoTransition());
        detailsText3.setVisibility(v3);
    }
}