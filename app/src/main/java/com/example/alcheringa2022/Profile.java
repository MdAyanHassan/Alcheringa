package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    Button EpassButton;

    ArrayList<String> interests;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        EpassButton = findViewById(R.id.Epassbutton);
        Intent intent=getIntent();
        String category=intent.getStringExtra("Category");
        Toast.makeText(getApplicationContext(), ""+category, Toast.LENGTH_SHORT).show();
        EpassButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), E_pass.class)));

        ImageButton back = findViewById(R.id.backbtn);
        back.setOnClickListener(v-> finish());

        interests = new ArrayList<>();
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);

    }

    public void interestItemClick(View v){
        TextView t = (TextView) v;

        int color=t.getCurrentTextColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & color));

        if(hexColor.equals("#FFFFFF")){
            t.setBackgroundResource(R.drawable.interests_highlighted);
            t.setTextColor(Color.parseColor("#EE6337"));
            interests.add(t.getText().toString());
        }else{
            t.setBackgroundResource(R.drawable.interests);
            t.setTextColor(Color.parseColor("#FFFFFF"));
            interests.remove(t.getText().toString());
        }

    }

}