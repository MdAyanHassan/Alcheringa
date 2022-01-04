package com.example.alcheringa2022;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Merch_Description extends AppCompatActivity implements View.OnClickListener {
    Button small_btn,medium_btn,large_btn,xlarge_btn;
    String merch_size="small";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch_description);
        small_btn=findViewById(R.id.small_size);
        medium_btn=findViewById(R.id.media_size);
        large_btn=findViewById(R.id.large_size);
        xlarge_btn=findViewById(R.id.xlarge_size);

        small_btn.setOnClickListener(this);
        medium_btn.setOnClickListener(this);
        large_btn.setOnClickListener(this);
        xlarge_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.small_size:
                deselect_size();
                select_size(small_btn);
                merch_size="S";
                break;
            case R.id.media_size:
                deselect_size();
                select_size(medium_btn);
                merch_size="M";
                break;
            case R.id.large_size:
                deselect_size();
                select_size(large_btn);
                merch_size="L";
                break;
            case R.id.xlarge_size:
                deselect_size();
                select_size(xlarge_btn);
                merch_size="XL";
                break;
        }

    }

    private void select_size(Button btn) {
        btn.setBackground(getDrawable(R.drawable.merch_size_btn_selected));
        btn.setTextColor(Color.parseColor("#000000"));
    }

    private void deselect_size() {
        small_btn.setBackground(getDrawable(R.drawable.merch_size_btn_deselect));
        medium_btn.setBackground(getDrawable(R.drawable.merch_size_btn_deselect));
        large_btn.setBackground(getDrawable(R.drawable.merch_size_btn_deselect));
        xlarge_btn.setBackground(getDrawable(R.drawable.merch_size_btn_deselect));
        small_btn.setTextColor(Color.parseColor("#ffffff"));
        medium_btn.setTextColor(Color.parseColor("#ffffff"));
        large_btn.setTextColor(Color.parseColor("#ffffff"));
        xlarge_btn.setTextColor(Color.parseColor("#ffffff"));
    }
}