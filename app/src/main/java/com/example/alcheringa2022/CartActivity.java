package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alcheringa2022.Model.cartModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements onItemClick {
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<cartModel> list;
    TextView amount;
    String total_amount;
    Button checkout_btn;
    DBHandler dbHandler;
    ImageView cart;
    ArrayList<cartModel> cartModelArrayList;
    Button startShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list=new ArrayList<>();
        dbHandler=new DBHandler(getApplicationContext());
        cartModelArrayList =dbHandler.readCourses();

        if(cartModelArrayList.size() == 0){

            setContentView(R.layout.empty_shopping_cart);
            startShopping = findViewById(R.id.start_shopping);
            startShopping.setOnClickListener((v) -> {
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("fragment", "merch");
                startActivity(i);
            });

        }else{
            setContentView(R.layout.activity_cart);

            /*for (int i = 0; i< cartModelArrayList.size(); i++){
                Toast.makeText(getApplicationContext(), ""+ cartModelArrayList.get(i).getPrice(), Toast.LENGTH_SHORT).show();
            }*/

            amount=findViewById(R.id.order_total_value);
            checkout_btn=findViewById(R.id.checkout_button);

            recyclerView=findViewById(R.id.cart_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);


            populate_cart();
            calculate_amount();
            checkout_btn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddAddressActivity.class)));
        }

        ImageButton backBtn = findViewById(R.id.backbtn);
        backBtn.setOnClickListener(v -> finish());

    }

    private void calculate_amount() {
        long amt=0;
        for(int i = 0; i< cartModelArrayList.size(); i++){
            //Toast.makeText(getApplicationContext(), ""+ cartModelArrayList.get(i).getCount(), Toast.LENGTH_SHORT).show();
            amt=amt+Long.parseLong(cartModelArrayList.get(i).getPrice())*Long.parseLong(cartModelArrayList.get(i).getCount());
        }
        total_amount=amt+".";
        amount.setText(total_amount);
    }

    private void populate_cart() {
            cartAdapter=new CartAdapter(cartModelArrayList,this,getApplicationContext());
            recyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void Onclick(int position) {
        dbHandler.DeleteItem(cartModelArrayList.get(position).getName(), cartModelArrayList.get(position).getSize());
        cartModelArrayList.remove(cartModelArrayList.get(position));
        cartAdapter=new CartAdapter(cartModelArrayList,this,getApplicationContext());
        recyclerView.setAdapter(cartAdapter);
        calculate_amount();
    }

    @Override
    public void OnIncrementClick(int position) {
        int count=Integer.parseInt(cartModelArrayList.get(position).getCount());
        count++;
        cartModelArrayList.get(position).setCount(count+"");
        dbHandler
                .addNewitemIncart(cartModelArrayList.get(position).getName(),
                cartModelArrayList.get(position).getPrice(),
                cartModelArrayList.get(position).getSize(),
                cartModelArrayList.get(position).getCount(),
                cartModelArrayList.get(position).getImage(),
                cartModelArrayList.get(position).getType(),getApplicationContext());
        cartAdapter.notifyDataSetChanged();
        calculate_amount();

    }

    @Override
    public void OnDecrementClick(int position) {
        int count=Integer.parseInt(cartModelArrayList.get(position).getCount());
        if(count>0){
            count--;
            cartModelArrayList.get(position).setCount(count+"");
            dbHandler.RemoveFromCart(cartModelArrayList.get(position).getName(), cartModelArrayList.get(position).getPrice(),
                    cartModelArrayList.get(position).getSize(), cartModelArrayList.get(position).getCount());
            cartAdapter.notifyDataSetChanged();
            calculate_amount();
        }


    }
}