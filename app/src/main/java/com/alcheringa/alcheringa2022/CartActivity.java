package com.alcheringa.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alcheringa.alcheringa2022.Database.DBHandler;
import com.alcheringa.alcheringa2022.Model.cartModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements onItemClick {
    RecyclerView recyclerView;
    CartItemsAdapter cartItemsAdapter;
    List<cartModel> list;
    TextView amount, items;
    String total_amount;
    Button checkout_btn;
    DBHandler dbHandler;
    ImageView cart;
    ArrayList<cartModel> cartModelArrayList;
    Button startShopping;
    LoaderView loaderView;

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
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            });

        }else{
            setContentView(R.layout.activity_cart);

            amount=findViewById(R.id.order_total_value);
            items = findViewById((R.id.item_counter));
            checkout_btn=findViewById(R.id.checkout_button);

            recyclerView=findViewById(R.id.cart_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);


            populate_cart();
            calculate_amount();
            checkout_btn.setOnClickListener(v ->
                    startActivity(new Intent(getApplicationContext(), AddAddressActivity.class)));

            loaderView = findViewById(R.id.dots_progress);
            loaderView.setVisibility(View.GONE);
        }

        ImageButton backBtn = findViewById(R.id.backbtn);
        backBtn.setOnClickListener(v -> finish());

    }

    private void calculate_amount() {
        long amt=0;
        long item_count=0;
        for(int i = 0; i< cartModelArrayList.size(); i++){
            //Toast.makeText(getApplicationContext(), ""+ cartModelArrayList.get(i).getCount(), Toast.LENGTH_SHORT).show();
            amt=amt+Long.parseLong(cartModelArrayList.get(i).getPrice())*Long.parseLong(cartModelArrayList.get(i).getCount());
            item_count=item_count+Long.parseLong(cartModelArrayList.get(i).getCount());
        }
        total_amount=amt+".";
        amount.setText(total_amount);

        items.setText(item_count+" items");
    }

    private void populate_cart() {
            cartItemsAdapter =new CartItemsAdapter(cartModelArrayList,this,getApplicationContext());
            recyclerView.setAdapter(cartItemsAdapter);
    }

    @Override
    public void OnAnyClick(int position) {

    }

    @Override
    public void Onclick(int position) {
        dbHandler.DeleteItem(cartModelArrayList.get(position).getName(), cartModelArrayList.get(position).getSize());
        cartModelArrayList.remove(cartModelArrayList.get(position));
        cartItemsAdapter =new CartItemsAdapter(cartModelArrayList,this,getApplicationContext());
        recyclerView.setAdapter(cartItemsAdapter);
        calculate_amount();
        refreshView();
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
        cartItemsAdapter.notifyDataSetChanged();
        calculate_amount();
        refreshView();

    }

    @Override
    public void OnDecrementClick(int position) {
        int count=Integer.parseInt(cartModelArrayList.get(position).getCount());
        if(count>1){
            count--;
            cartModelArrayList.get(position).setCount(count+"");
            dbHandler.RemoveFromCart(cartModelArrayList.get(position).getName(), cartModelArrayList.get(position).getPrice(),
                    cartModelArrayList.get(position).getSize(), cartModelArrayList.get(position).getCount());
            cartItemsAdapter.notifyDataSetChanged();
            calculate_amount();
        }else if(count==1){
            dbHandler.DeleteItem(cartModelArrayList.get(position).getName(), cartModelArrayList.get(position).getSize());
            cartModelArrayList.remove(cartModelArrayList.get(position));
            cartItemsAdapter =new CartItemsAdapter(cartModelArrayList,this,getApplicationContext());
            recyclerView.setAdapter(cartItemsAdapter);
            calculate_amount();
        }
        refreshView();


    }

    private void refreshView() {
        if(cartModelArrayList.size() == 0){
            setContentView(R.layout.empty_shopping_cart);
            startShopping = findViewById(R.id.start_shopping);
            startShopping.setOnClickListener((v) -> {
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("fragment", "merch");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            });
            ImageButton backBtn = findViewById(R.id.backbtn);
            backBtn.setOnClickListener(v -> finish());
        }
    }
}