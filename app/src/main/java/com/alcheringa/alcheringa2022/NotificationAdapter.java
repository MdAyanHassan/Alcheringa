package com.alcheringa.alcheringa2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;

    ArrayList<NotificationData> list;

    public NotificationAdapter(Context context, ArrayList<NotificationData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.notificationitem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         NotificationData notificationData=list.get(position);
         holder.heading.setText(notificationData.getHeading());
         holder.subheading.setText(notificationData.getSubheading());
         holder.time.setText(notificationData.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView heading,subheading,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heading=itemView.findViewById(R.id.heading);
            subheading=itemView.findViewById(R.id.subHeading);
            time=itemView.findViewById(R.id.time);
        }
    }

}
