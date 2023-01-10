package com.alcheringa.alcheringa2022;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name,type,price,size;
    ImageView imageView;
    TextView remove_btn;
    TextView count;
    ConstraintLayout card;
    ImageView increment,decrement;
    com.alcheringa.alcheringa2022.onItemClick onItemClick;
    public CartViewHolder(@NonNull View itemView, com.alcheringa.alcheringa2022.onItemClick onItemClick) {
        super(itemView);
        name=itemView.findViewById(R.id.hoodie_name);
        type=itemView.findViewById(R.id.type);
        price=itemView.findViewById(R.id.price);
        size=itemView.findViewById(R.id.size_2);
        imageView=itemView.findViewById(R.id.merch_image);
//        remove_btn=itemView.findViewById(R.id.remove_btn);
        count=itemView.findViewById(R.id.quantity);
        card = itemView.findViewById(R.id.bg_card);
        increment=itemView.findViewById(R.id.add);
        decrement=itemView.findViewById(R.id.subtract);
        this.onItemClick= onItemClick;
//        remove_btn.setOnClickListener(this);
        increment.setOnClickListener(this);
        decrement.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        onItemClick.OnAnyClick(getAdapterPosition());
        switch (v.getId()){
            case R.id.add:
                onItemClick.OnIncrementClick(getAdapterPosition());
                break;
            case R.id.subtract:
                onItemClick.OnDecrementClick(getAdapterPosition());
                break;
            /*case R.id.remove_btn:
                onItemClick.Onclick(getAdapterPosition());
                break;*/


        }
    }
}