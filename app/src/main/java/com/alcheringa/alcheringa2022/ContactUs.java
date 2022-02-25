package com.alcheringa.alcheringa2022;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {

    Button buttonEmail;
    Button buttonCall;
    Button buttonCall2;
    Button buttonEmail2;
    Button buttonCall3;
    Button buttonEmail3;
    ImageView back_btn;
    TextView textView1,textView2,textView3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        back_btn=findViewById(R.id.backbtn);
        back_btn.setOnClickListener(v -> finish());
        textView1=findViewById(R.id.name);
        textView2=findViewById(R.id.name2);
        textView3=findViewById(R.id.name3);
        textView3.setText("Vishal Chelly");
        textView2.setText("Ch Venkat Vikas");
        textView1.setText("Ankit Agarwal");

        buttonCall=findViewById(R.id.call_btn);
        buttonEmail=findViewById(R.id.email_btn);
        buttonCall2=findViewById(R.id.call_btn2);
        buttonEmail2=findViewById(R.id.email_btn2);
        buttonCall3=findViewById(R.id.call_btn3);
        buttonEmail3=findViewById(R.id.email_btn3);


        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9983072631"));
                startActivity(intent);

            }
        });
        buttonCall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8919054239"));
                startActivity(intent);
            }
        });

        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:ankit.a@alcheringa.in"));
                startActivity(intent);
            }
        });

        buttonEmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:vikas@alcheringa.in"));
                startActivity(intent);
            }
        });
        buttonCall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:7331104679"));
                startActivity(intent);
            }
        });


        buttonEmail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:vishal@alcheringa.in"));
                startActivity(intent);
            }
        });
    }
    }
