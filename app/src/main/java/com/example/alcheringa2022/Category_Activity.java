package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.alcheringa2022.Database.DBHandler;
import com.example.alcheringa2022.Model.cartModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Category_Activity extends AppCompatActivity  {
    DBHandler dbHandler;
    Button student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        String transcId = df.format(c);
        student=findViewById(R.id.IITG_Student);
        dbHandler=new DBHandler(getApplicationContext());


        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dbHandler.addNewitemIncart("vipin","500","XL","2");
//                dbHandler.addNewitemIncart("vipin","500","L","2");
                Toast.makeText(getApplicationContext(), "submitted", Toast.LENGTH_SHORT).show();
                ArrayList<cartModel> list= dbHandler.readCourses();
                Toast.makeText(getApplicationContext(), ""+list.toString(), Toast.LENGTH_SHORT).show();
               /// dbHandler.Delete_all();
            }
        });

    }

}