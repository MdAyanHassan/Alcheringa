package com.alcheringa.alcheringa2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

         Date notificationDate = notificationData.getDate();
         holder.time.setText(getTimeString(notificationDate));
    }

    private String getTimeString(Date notificationDate) {
        Date currentDate = new Date();
        if (notificationDate.before(currentDate)) {
            long different = currentDate.getTime() - notificationDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            int elapsedDays = (int)(different / daysInMilli);
            different = different % daysInMilli;

            int elapsedHours = (int)(different / hoursInMilli);
            different = different % hoursInMilli;

            int elapsedMinutes = (int)(different / minutesInMilli);
            different = different % minutesInMilli;

            int elapsedSeconds = (int)(different / secondsInMilli);

            String timeString = "";
            if(elapsedDays>0){
                SimpleDateFormat sdfDate = new SimpleDateFormat("d MMM", Locale.getDefault());
                timeString = sdfDate.format(notificationDate);
            }else{
                if(elapsedHours>0){
                    timeString = timeString + elapsedHours + "h ";
                }
                else if(elapsedMinutes>0){
                    timeString = timeString + elapsedMinutes + "m ";
                }
                else if(elapsedSeconds>0){
                    timeString = timeString + elapsedSeconds + "s";
                }
            }
            return timeString.trim();
        }
        else return "";
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
