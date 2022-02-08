package com.alcheringa.alcheringa2022;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ContactUs extends AppCompatActivity {

    Button buttonEmail;
    Button buttonCall;
    ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        back_btn=findViewById(R.id.backbtn);
        back_btn.setOnClickListener(v -> finish());

        buttonCall=findViewById(R.id.call_btn);
        buttonEmail=findViewById(R.id.email_btn);

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:7011879379"));
                startActivity(intent);

            }
        });

        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:abc@gmail.com"));
                startActivity(intent);
            }
        });
    }
}