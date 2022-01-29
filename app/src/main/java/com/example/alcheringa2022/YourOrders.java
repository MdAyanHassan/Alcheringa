package com.example.alcheringa2022;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alcheringa2022.Model.YourOrders_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YourOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    List<YourOrders_model> yourOrders_modelList;
    YourOrdersAdapter yourOrders_adapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    LoaderView loaderView;

    ImageButton back_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);
        recyclerView=findViewById(R.id.user_orders_recyclerview);

        firebaseAuth=FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        yourOrders_modelList=new ArrayList<>();
        yourOrders_adapter=new YourOrdersAdapter(yourOrders_modelList, this);

        populate_your_orders();

        back_btn=findViewById(R.id.backbtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loaderView = findViewById(R.id.dots_progress);
        loaderView.setVisibility(View.GONE);
    }

    private void populate_your_orders() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        assert email != null;

        firestore.collection("USERS").document(email).collection("ORDERS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot documentSnapshot : task.getResult()){
                    ArrayList<HashMap<String, Object>> obj= (ArrayList<HashMap<String, Object>>) documentSnapshot.get("orders");

                    for(HashMap<String, Object> order : obj) {
                        yourOrders_modelList.add(new YourOrders_model(order.get("isDelivered").toString(), order.get("Name").toString()
                                , order.get("Type").toString(), order.get("Count").toString(), order.get("Size").toString(),
                                order.get("Price").toString(), "12", "https://i.picsum.photos/id/355/200/300.jpg?hmac=CjmRk_yPeMJV6teNYBA4ceaviVpxIl8XM9NL7GQzLMU"));
                    }
                    recyclerView.setAdapter(yourOrders_adapter);
                }
            }
        });

    }
}
