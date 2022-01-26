package com.example.alcheringa2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alcheringa2022.Model.cartModel;

import java.util.List;
import java.util.Objects;

public class CartItemsAdapter extends RecyclerView.Adapter<CartViewHolder> {
    List<cartModel> cartModelList ;
    com.example.alcheringa2022.onItemClick onItemClick;
    Context context;

    public CartItemsAdapter(List<cartModel> cartModelList, com.example.alcheringa2022.onItemClick onItemClick, Context context) {
        this.cartModelList = cartModelList;
        this.onItemClick = onItemClick;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder(view,onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.count.setText(cartModelList.get(position).getCount());
        holder.name.setText(cartModelList.get(position).getName());
        holder.price.setText("₹ "+cartModelList.get(position).getPrice()+".");
        holder.type.setText(cartModelList.get(position).getType());
        String size=cartModelList.get(position).getSize();
        if(Objects.equals(size, "S")){
            size="Small";
        }
        else  if(Objects.equals(size, "M")){
            size="Medium";
        }
        else  if(Objects.equals(size, "L")){
            size="Large";
        }
        else  if(Objects.equals(size, "XL")){
            size="Extra Large";
        }
        holder.size.setText(size);
        Glide.with(context).load(cartModelList.get(position).getImage()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }
}
