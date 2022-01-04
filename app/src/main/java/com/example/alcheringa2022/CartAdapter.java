package com.example.alcheringa2022;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alcheringa2022.Model.Cart_model;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    List<Cart_model> cartModelList ;
    onItemClick onItemClick;

    public CartAdapter(List<Cart_model> cartModelList, com.example.alcheringa2022.onItemClick onItemClick) {
        this.cartModelList = cartModelList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.merch_cart,parent,false);
        return new CartViewHolder(view,onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.count.setText(cartModelList.get(position).getCount());



    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }
}
