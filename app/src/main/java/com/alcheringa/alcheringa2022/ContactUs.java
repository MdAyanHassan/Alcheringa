package com.alcheringa.alcheringa2022;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {

    ConstraintLayout buttonEmail;
    ConstraintLayout buttonCall;
    ConstraintLayout buttonCall2;
    ConstraintLayout buttonEmail2;
    ConstraintLayout buttonCall3;
    ConstraintLayout buttonEmail3;
    ImageView back_btn;
    TextView textView1,textView2,textView3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        back_btn=findViewById(R.id.backbtn);
        back_btn.setOnClickListener(v -> finish());
        textView1=findViewById(R.id.name1);
        textView2=findViewById(R.id.name2);
        textView3=findViewById(R.id.name3);
        textView3.setText("Siddhant singh");
        textView2.setText("Akshara reddy");
        textView1.setText("Quasim Khan");

        buttonCall=findViewById(R.id.call_btn1);
        buttonEmail=findViewById(R.id.email_btn1);
        buttonCall2=findViewById(R.id.call_btn2);
        buttonEmail2=findViewById(R.id.email_btn2);
        buttonCall3=findViewById(R.id.call_btn3);
        buttonEmail3=findViewById(R.id.email_btn3);


        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+917763959688"));
                startActivity(intent);

            }
        });

        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:quasim@alcheringa.in"));
                startActivity(intent);
            }
        });
        buttonCall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+919908991939"));
                startActivity(intent);
            }
        });

        buttonEmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:akshara@alcheringa.in"));
                startActivity(intent);
            }
        });
        buttonCall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+919310859978"));
                startActivity(intent);
            }
        });


        buttonEmail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:siddhant.s@alcheringa.in"));
                startActivity(intent);
            }
        });
    }
}
