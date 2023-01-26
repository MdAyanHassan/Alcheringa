package com.alcheringa.alcheringa2022;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SponsorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView imageView;
    ProgressBar progressBar;
    com.alcheringa.alcheringa2022.onItemClick onItemClick;

    public SponsorsViewHolder(@NonNull View itemView, com.alcheringa.alcheringa2022.onItemClick onItemClick) {
        super(itemView);
        imageView=itemView.findViewById(R.id.sponsor_logo);
        progressBar=itemView.findViewById(R.id.progress);
        this.onItemClick= onItemClick;
        imageView.setOnClickListener(this);
    }

    public void onClick(View v) {
        onItemClick.Onclick(getAdapterPosition());
    }
}
