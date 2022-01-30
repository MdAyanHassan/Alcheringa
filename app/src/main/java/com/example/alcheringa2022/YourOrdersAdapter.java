package com.example.alcheringa2022;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alcheringa2022.Model.YourOrders_model;

import java.util.List;
import java.util.Objects;

public class YourOrdersAdapter extends RecyclerView.Adapter<YourOrdersViewHolder> {
    List<YourOrders_model> yourOrders_ModelList ;
    Context context;

    public YourOrdersAdapter(List<YourOrders_model> yourOrdersModelList, Context context) {

        this.yourOrders_ModelList = yourOrdersModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public YourOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_orders_item,parent,false);
        return new YourOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourOrdersViewHolder holder, int position) {

        String status = yourOrders_ModelList.get(position).getStatus();

        String status_text;
        if(Objects.equals(status, "true"))
        {
            status_text = "Delivered";
            holder.status.setTextColor(Color.parseColor("#11D3D3"));
        }
        else
        {
            status_text = "Ordered";
            holder.status.setTextColor(Color.parseColor("#EE6337"));
        }

        holder.status.setText(status_text);
        holder.merch_name.setText(yourOrders_ModelList.get(position).getMerch_name());

        String size= yourOrders_ModelList.get(position).getMerch_size();
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

        Log.d("YourAdapter", size);

        String merch_details = yourOrders_ModelList.get(position).getMerch_type() + ", " + size + ", " + yourOrders_ModelList.get(position).getMerch_quantity() + " Qty";
        holder.merch_details.setText(merch_details);

        holder.price.setText("₹ "+yourOrders_ModelList.get(position).getPrice()+".");

        holder.delivery_date.setText("Delivery by " + yourOrders_ModelList.get(position).getDelivery_date());

        Glide.with(context).load(yourOrders_ModelList.get(position).getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return yourOrders_ModelList.size();
    }
}

