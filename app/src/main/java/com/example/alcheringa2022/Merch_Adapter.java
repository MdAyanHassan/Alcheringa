package com.example.alcheringa2022;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alcheringa2022.Model.Merch_model;

import java.util.List;

public class Merch_Adapter extends RecyclerView.Adapter<Merch_viewholder> {
    List<Merch_model> list;
    Context context;
    onItemClick onItemClick;

    public Merch_Adapter(List<Merch_model> list, Context context,onItemClick onItemClick) {
        this.list = list;
        this.context = context;
        this.onItemClick=onItemClick;
    }

    @NonNull
    @Override
    public Merch_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.merch_item,parent,false);
        return new Merch_viewholder(view,onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull Merch_viewholder holder, int position) {
        if (position == 0) {
            holder.price.setTextColor(Color.parseColor("#00010D"));
            holder.name.setTextColor(Color.parseColor("#00010D"));
            holder.material.setTextColor(Color.parseColor("#00010D"));
            holder.description.setTextColor(Color.parseColor("#00010D"));
            holder.view.setBackgroundColor(Color.parseColor("#11D3D3"));
            holder.decimal_price.setTextColor(Color.parseColor("#00010D"));
        }
        holder.price.setText("₹ "+list.get(position).getPrice()+".");
        holder.name.setText(list.get(position).getName_hoddie());
        holder.material.setText(list.get(position).getMaterial());
        holder.description.setText(list.get(position).getDescription());
        Glide.with(context).load(list.get(position).getImage_url()).into(holder.imageView);
        //holder.imageView
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
