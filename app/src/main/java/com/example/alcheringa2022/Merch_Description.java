package com.example.alcheringa2022;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alcheringa2022.Model.Merch_model;

public class Merch_Description extends AppCompatActivity implements View.OnClickListener {
    Button small_btn,medium_btn,large_btn,xlarge_btn;
    String merch_size="S";
    Button buy_now,add_to_cart;
    DBHandler dbHandler;
    Merch_model merch_model;
    TextView name,type,price,description;
    ImageView cart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch_description);
        cart=findViewById(R.id.cart);
        Intent intent=getIntent();
        merch_model=intent.getExtras().getParcelable("item");
        small_btn=findViewById(R.id.small_size);
        medium_btn=findViewById(R.id.media_size);
        large_btn=findViewById(R.id.large_size);
        xlarge_btn=findViewById(R.id.xlarge_size);
        buy_now=findViewById(R.id.buy_now);
        add_to_cart=findViewById(R.id.add_to_cart);
        name=findViewById(R.id.merch_name);
        type=findViewById(R.id.merch_type);
        price=findViewById(R.id.merch_price);
        description=findViewById(R.id.merch_description);
        small_btn.setOnClickListener(this);
        medium_btn.setOnClickListener(this);
        large_btn.setOnClickListener(this);
        xlarge_btn.setOnClickListener(this);
        buy_now.setOnClickListener(this);
        add_to_cart.setOnClickListener(this);
        cart.setOnClickListener(this);
        dbHandler=new DBHandler(this);
        small_btn.setVisibility(View.GONE);
        large_btn.setVisibility(View.GONE);
        medium_btn.setVisibility(View.GONE);
        xlarge_btn.setVisibility(View.GONE);


        /// setting buttons
        setting_buttons();

        populate_details();
        //dbHandler.Delete_all();


    }

    private void populate_details() {
        name.setText(merch_model.getName_hoddie());
        type.setText(merch_model.getMaterial());
        price.setText("₹ "+merch_model.getPrice()+".00");
        description.setText(merch_model.getDescription());
    }


    private void setting_buttons() {
        if(merch_model.getSmall()){
            small_btn.setVisibility(View.VISIBLE);
        }
        if(merch_model.getLarge()){
            large_btn.setVisibility(View.VISIBLE);
        }
        if(merch_model.getMedium()){
            medium_btn.setVisibility(View.VISIBLE);
        }
        if(merch_model.getLarge()){
            xlarge_btn.setVisibility(View.VISIBLE);
        }
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
            case R.id.buy_now:
               // dbHandler.addNewitemIncart(merch_model.getName_hoddie(),merch_model.getPrice(),merch_size,"1",merch_model.getImage_url(),merch_model.getMaterial());
                startActivity(new Intent(getApplicationContext(),Cart.class));
                break;
            case R.id.add_to_cart:
                dbHandler.addNewitemIncart(merch_model.getName_hoddie(),merch_model.getPrice(),merch_size,
                        "1",merch_model.getImage_url(),merch_model.getMaterial(),
                        getApplicationContext());
                startActivity(new Intent(getApplicationContext(),Cart.class));
                break;
            case R.id.cart:
                startActivity(new Intent(getApplicationContext(),Cart.class));
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