package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Category_Activity extends AppCompatActivity implements View.OnClickListener{
    Button student,Staff,outsider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        student=findViewById(R.id.IITG_Student);
        Staff=findViewById(R.id.IITG_Staff);
        outsider=findViewById(R.id.OutSider);
        student.setOnClickListener(this);
        Staff.setOnClickListener(this);
        outsider.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),Profile.class);
        switch (v.getId()){
            case  R.id.IITG_Student:
                intent.putExtra("Category","IITG_Student");
                startActivity(intent);
                break;
            case  R.id.IITG_Staff :
                intent.putExtra("Category","IITG_Staff");
                startActivity(intent);
                break;
            case  R.id.OutSider:
                intent.putExtra("Category","OutSider");
                startActivity(intent);
                break;
        }

    }
}