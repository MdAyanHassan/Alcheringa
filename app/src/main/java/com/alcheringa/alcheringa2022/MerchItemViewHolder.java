package com.alcheringa.alcheringa2022;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MerchItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name,material,description,price,decimal_price;
    ImageView imageView;
    View view;
    onItemClick click;
    public MerchItemViewHolder(@NonNull View itemView, onItemClick onItemClick) {
        super(itemView);
        name=itemView.findViewById(R.id.hoodie_name);
        material=itemView.findViewById(R.id.material);
        description=itemView.findViewById(R.id.description);
        price=itemView.findViewById(R.id.price);
        imageView=itemView.findViewById(R.id.merch_image);
        view=itemView.findViewById(R.id.bg_card);
        decimal_price=itemView.findViewById(R.id.decimal_price);
        click= onItemClick;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        click.Onclick(getAdapterPosition());

    }
}
