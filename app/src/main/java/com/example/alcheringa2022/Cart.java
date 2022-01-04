package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.alcheringa2022.Model.Cart_model;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity implements onItemClick {
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<Cart_model> list;
    TextView amount;
    String total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        amount=findViewById(R.id.order_total_value);
        recyclerView=findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        populate_cart();
        calcualte_amount();
    }

    private void calcualte_amount() {
        long amt=0;
        for(int i=0;i<list.size();i++){
            amt=amt+Long.parseLong(list.get(i).getPrice())*Long.parseLong(list.get(i).getCount());
        }
        total_amount=amt+".";
        amount.setText(total_amount);
    }

    private void populate_cart() {
        for(int i=0;i<3;i++){
            list.add(new Cart_model("","","","800","","1"));
            cartAdapter=new CartAdapter(list,this);
            recyclerView.setAdapter(cartAdapter);
        }
    }

    @Override
    public void Onclick(int position) {
        list.remove(list.get(position));
        cartAdapter=new CartAdapter(list,this);
        recyclerView.setAdapter(cartAdapter);
        calcualte_amount();
    }

    @Override
    public void OnIncrementClick(int position) {
        int count=Integer.parseInt(list.get(position).getCount());
        count++;
        list.get(position).setCount(count+"");

        cartAdapter.notifyDataSetChanged();
        calcualte_amount();

    }

    @Override
    public void OnDecrementClick(int position) {
        int count=Integer.parseInt(list.get(position).getCount());
        if(count>0){
            count--;
            list.get(position).setCount(count+"");
            cartAdapter.notifyDataSetChanged();
            calcualte_amount();
        }


    }
}