package com.example.alcheringa2022;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alcheringa2022.Model.Merch_model;

import java.util.ArrayList;

public class OrderSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);


        // create a arraylist of the type Merch_model
        final ArrayList<Merch_model> arrayList = new ArrayList<Merch_model>();

        // the items are of the type Merch_model
        String image_url = "https://i.picsum.photos/id/355/200/300.jpg?hmac=CjmRk_yPeMJV6teNYBA4ceaviVpxIl8XM9NL7GQzLMU";
        arrayList.add(new Merch_model("Lightning", "4300" ,image_url, false, false, true));
        arrayList.add(new Merch_model("Lightning", "4300" ,image_url, false, false, true));
        arrayList.add(new Merch_model("Lightning", "4300" ,image_url, false, false, true));


        OrderSummaryAdapter adapter = new OrderSummaryAdapter(this, arrayList);
        ListView listView = findViewById(R.id.items_list_view);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnItems(listView);

        /*LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout list = (LinearLayout)findViewById(R.id.list_linear_layout);
        for (int i=0; i<arrayList.size(); i++) {
            Merch_model merch_model = arrayList.get(i);
            View vi = inflater.inflate(R.layout.order_summary_item, null);
            list.addView(vi);
        }*/
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }


}



