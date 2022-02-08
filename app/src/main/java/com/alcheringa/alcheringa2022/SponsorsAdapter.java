package com.alcheringa.alcheringa2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.alcheringa.alcheringa2022.Model.Sponsor_model;

import java.util.List;

public class SponsorsAdapter extends RecyclerView.Adapter<SponsorsViewHolder> {

    List<Sponsor_model> sponsor_modelList;
    Context context;
    com.alcheringa.alcheringa2022.onItemClick onItemClick;

    public SponsorsAdapter(List<Sponsor_model> sponsor_modelList, com.alcheringa.alcheringa2022.onItemClick onItemClick, Context context) {

        this.sponsor_modelList = sponsor_modelList;
        this.onItemClick = onItemClick;
        this.context = context;
    }

    @NonNull
    @Override
    public SponsorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sponsor_item,parent,false);
        return new SponsorsViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorsViewHolder holder, int position) {
        Glide.with(context).load(sponsor_modelList.get(position).getLogo()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return sponsor_modelList.size();
    }
}

