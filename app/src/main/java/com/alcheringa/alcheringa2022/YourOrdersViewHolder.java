package com.alcheringa.alcheringa2022;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class YourOrdersViewHolder extends RecyclerView.ViewHolder {
    TextView status,merch_name, merch_details, price, decimalprice, delivery_date;
    ImageView imageView;

    public YourOrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        status=itemView.findViewById(R.id.order_status);
        merch_name=itemView.findViewById(R.id.merch_name);
        merch_details=itemView.findViewById(R.id.merch_details);
        price=itemView.findViewById(R.id.price);
        decimalprice = itemView.findViewById(R.id.decimal_price);
        delivery_date= itemView.findViewById(R.id.deliver_date);
        imageView=itemView.findViewById(R.id.merch_image);
    }
}
