package com.alcheringa.alcheringa2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.alcheringa.alcheringa2022.Model.cartModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderSummaryAdapter extends ArrayAdapter<cartModel> {

    // invoke the suitable constructor of the ArrayAdapter class
    public OrderSummaryAdapter(@NonNull Context context, ArrayList<cartModel> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.order_summary_item, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        cartModel currentItem = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView image = currentItemView.findViewById(R.id.merch_image);
        assert currentItem != null;
        //image.setImageResource(currentItem.getImage_url());
        try {
            Glide.with(getContext()).load(currentItem.getImage()).into(image);
            //Toast.makeText(getContext(), "Image set successfully",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getContext(), "could not load image"+ e.getMessage(),Toast.LENGTH_LONG).show();
        }

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView name = currentItemView.findViewById(R.id.name);
        name.setText(currentItem.getName());

        TextView price = currentItemView.findViewById(R.id.price);
        price.setText(String.format("₹%s.", currentItem.getPrice()));

        TextView details = currentItemView.findViewById(R.id.details);
        String size = "";
        switch (currentItem.getSize()){
            case "S": size = "Small"; break;
            case "M": size = "Medium"; break;
            case "L": size = "Large"; break;
            case "XL": size = "Extra Large"; break;
        }
        details.setText(String.format("%s, %s, %s Qty", currentItem.getType(), size, currentItem.getCount()));

        TextView delivery = currentItemView.findViewById(R.id.delivery);
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 15); // Delivered within 15 days
        delivery.setText(String.format("Delivery by %s", sdf.format(c.getTime())));



        // then return the recyclable view
        return currentItemView;
    }
}

