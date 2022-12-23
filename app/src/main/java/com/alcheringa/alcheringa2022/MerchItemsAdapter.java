package com.alcheringa.alcheringa2022;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.alcheringa.alcheringa2022.Model.merchModel;

import java.util.List;

public class MerchItemsAdapter extends RecyclerView.Adapter<MerchItemViewHolder> {
    List<merchModel> list;
    Context context;
    com.alcheringa.alcheringa2022.onItemClick onItemClick;
    int[] back = {
            R.drawable.merch_bg_1,
            R.drawable.merch_bg_2,
            R.drawable.merch_bg_3,
    };

    public MerchItemsAdapter(List<merchModel> list, Context context, com.alcheringa.alcheringa2022.onItemClick onItemClick) {
        this.list = list;
        this.context = context;
        this.onItemClick= onItemClick;
    }

    @NonNull
    @Override
    public MerchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.merch_item,parent,false);
        return new MerchItemViewHolder(view,onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchItemViewHolder holder, int position) {
//        if (position == 0) {
//            holder.price.setTextColor(Color.parseColor("#00010D"));
//            holder.name.setTextColor(Color.parseColor("#00010D"));
//            holder.material.setTextColor(Color.parseColor("#00010D"));
//            holder.description.setTextColor(Color.parseColor("#00010D"));

//            holder.decimal_price.setTextColor(Color.parseColor("#00010D"));

        holder.view.setBackground(ContextCompat.getDrawable(context, back[position]));
        holder.price.setText("₹ " + list.get(position).getPrice() + ".");
        holder.name.setText(list.get(position).getName());
        holder.material.setText(list.get(position).getMaterial());
        holder.description.setText(list.get(position).getSmall_Descripition());
        holder.decimal_price.setText(R.string.default_decimal_place);
        Glide.with(context).load(list.get(position).getImage_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
