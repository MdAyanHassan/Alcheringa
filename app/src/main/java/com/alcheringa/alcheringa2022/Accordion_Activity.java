package com.alcheringa.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Accordion_Activity extends AppCompatActivity {

    TextView detailsText;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_page);

        detailsText = findViewById(R.id.details);
        layout = findViewById(R.id.layout);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
    }

    public void expand(View view) {
        int v = (detailsText.getVisibility() ==View.GONE)? View.VISIBLE : View.GONE;

        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        detailsText.setVisibility(v);
    }
}