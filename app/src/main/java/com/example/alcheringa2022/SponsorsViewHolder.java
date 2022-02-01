package com.example.alcheringa2022;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SponsorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView imageView;
    com.example.alcheringa2022.onItemClick onItemClick;

    public SponsorsViewHolder(@NonNull View itemView, com.example.alcheringa2022.onItemClick onItemClick) {
        super(itemView);
        imageView=itemView.findViewById(R.id.sponsor_logo);
        this.onItemClick= onItemClick;
        imageView.setOnClickListener(this);
    }

    public void onClick(View v) {
        onItemClick.Onclick(getAdapterPosition());
    }
}
