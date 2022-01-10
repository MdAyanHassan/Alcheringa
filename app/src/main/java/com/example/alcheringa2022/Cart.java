package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alcheringa2022.Model.Cart_model;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity implements onItemClick {
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<Cart_model> list;
    TextView amount;
    String total_amount;
    Button checkout_btn;
    DBHandler dbHandler;
    ImageView cart;
    ArrayList<Cart_model> cart_modelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        amount=findViewById(R.id.order_total_value);
        checkout_btn=findViewById(R.id.checkout_button);

        recyclerView=findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        dbHandler=new DBHandler(getApplicationContext());
        cart_modelArrayList =dbHandler.readCourses();
        for (int i=0;i<cart_modelArrayList.size();i++){
            Toast.makeText(getApplicationContext(), ""+cart_modelArrayList.get(i).getPrice(), Toast.LENGTH_SHORT).show();
        }

        populate_cart();
        calcualte_amount();
        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Add_Address_Activity.class));
            }
        });
    }

    private void calcualte_amount() {
        long amt=0;
        for(int i=0;i<cart_modelArrayList.size();i++){
            Toast.makeText(getApplicationContext(), ""+cart_modelArrayList.get(i).getCount(), Toast.LENGTH_SHORT).show();
            amt=amt+Long.parseLong(cart_modelArrayList.get(i).getPrice())*Long.parseLong(cart_modelArrayList.get(i).getCount());
        }
        total_amount=amt+".";
        amount.setText(total_amount);
    }

    private void populate_cart() {
            cartAdapter=new CartAdapter(cart_modelArrayList,this,getApplicationContext());
            recyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void Onclick(int position) {
        dbHandler.DeleteItem(cart_modelArrayList.get(position).getName(),cart_modelArrayList.get(position).getSize());
        cart_modelArrayList.remove(cart_modelArrayList.get(position));
        cartAdapter=new CartAdapter(cart_modelArrayList,this,getApplicationContext());
        recyclerView.setAdapter(cartAdapter);
        calcualte_amount();
    }

    @Override
    public void OnIncrementClick(int position) {
        int count=Integer.parseInt(cart_modelArrayList.get(position).getCount());
        count++;
        cart_modelArrayList.get(position).setCount(count+"");
        dbHandler
                .addNewitemIncart(cart_modelArrayList.get(position).getName(),
                cart_modelArrayList.get(position).getPrice(),
                cart_modelArrayList.get(position).getSize(),
                cart_modelArrayList.get(position).getCount(),
                cart_modelArrayList.get(position).getImage(),
                cart_modelArrayList.get(position).getType(),getApplicationContext());
        cartAdapter.notifyDataSetChanged();
        calcualte_amount();

    }

    @Override
    public void OnDecrementClick(int position) {
        int count=Integer.parseInt(cart_modelArrayList.get(position).getCount());
        if(count>0){
            count--;
            cart_modelArrayList.get(position).setCount(count+"");
            dbHandler.RemoveFromCart(cart_modelArrayList.get(position).getName(),cart_modelArrayList.get(position).getPrice(),
                    cart_modelArrayList.get(position).getSize(),cart_modelArrayList.get(position).getCount());
            cartAdapter.notifyDataSetChanged();
            calcualte_amount();
        }


    }
}