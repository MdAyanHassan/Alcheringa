package com.alcheringa.alcheringa2022;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.alcheringa.alcheringa2022.Model.Sponsor_model;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

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
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorFilter(context.getResources().getColor(R.color.RotatingLoaderColor), PorterDuff.Mode.MULTIPLY);
        circularProgressDrawable.start();

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.back)
                .fitCenter();


        Glide.with(context)
                .load(sponsor_modelList.get(position).getLogo())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return sponsor_modelList.size();
    }
}

