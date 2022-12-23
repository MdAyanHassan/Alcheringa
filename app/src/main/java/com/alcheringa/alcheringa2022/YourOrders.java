package com.alcheringa.alcheringa2022;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.alcheringa.alcheringa2022.Model.YourOrders_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YourOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    List<YourOrders_model> yourOrders_modelList;
    YourOrdersAdapter yourOrders_adapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    ImageButton imageView;
    LoaderView loaderView;
    Button startShopping;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_orders);

/*        loaderView = findViewById(R.id.dots_progress);
        loaderView.setVisibility(View.VISIBLE);*/

        recyclerView = findViewById(R.id.user_orders_recyclerview);
        imageView = findViewById(R.id.backbtn);
        imageView.setOnClickListener(v -> finish());

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        yourOrders_modelList = new ArrayList<>();
        yourOrders_adapter = new YourOrdersAdapter(yourOrders_modelList, this);

        populate_your_orders();


    }

    private void populate_your_orders() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        assert email != null;

        firestore.collection("USERS").document(email).collection("ORDERS").get().addOnCompleteListener(task -> {

            if(task.isSuccessful() && !task.getResult().isEmpty())
            {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    ArrayList<HashMap<String, Object>> obj = (ArrayList<HashMap<String, Object>>) documentSnapshot.get("orders");

                    for (HashMap<String, Object> order : obj) {
                        yourOrders_modelList.add(new YourOrders_model(order.get("isDelivered").toString(),
                                order.get("Name").toString(), order.get("Type").toString(), order.get("Count").toString(),
                                order.get("Size").toString(),
                                order.get("Price").toString(), "12",
                                order.get("image").toString()));
                    }
                }
            }

            recyclerView.setAdapter(yourOrders_adapter);
//            loaderView.setVisibility(View.GONE);

            if(yourOrders_modelList.size() == 0){
                setContentView(R.layout.empty_your_orders);

//                Changing animation colour (not working):
                /*LottieAnimationView animationView = findViewById(R.id.empty_orders_anim);
                animationView.addValueCallback(
                        new KeyPath("**"),
                        LottieProperty.COLOR_FILTER,
                        new SimpleLottieValueCallback<ColorFilter>() {
                            @Override
                            public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                                return new PorterDuffColorFilter(0x19A6C2, PorterDuff.Mode.SRC_ATOP);
                            }
                        }
                );*/
            }

            startShopping = findViewById(R.id.start_shopping);
            startShopping.setOnClickListener((v) -> {
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("fragment", "merch");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            });
            imageView = findViewById(R.id.backbtn);
            imageView.setOnClickListener(v -> finish());
        });
    }
}
