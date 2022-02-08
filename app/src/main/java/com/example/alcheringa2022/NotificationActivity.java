package com.example.alcheringa2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.alcheringa2022.Model.YourOrders_model;
import com.example.alcheringa2022.Model.merchModel;
import com.google.api.LogDescriptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = "NotificationActivity";
    RecyclerView recyclerView;
    //DatabaseReference database;
    FirebaseFirestore firebaseFirestore;
    NotificationAdapter notificationAdapter;
    ArrayList<NotificationData> list;
    LoaderView loaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView=findViewById(R.id.notificationlist);
        //database= FirebaseDatabase.getInstance().getReference("NotificationData");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        notificationAdapter=new NotificationAdapter(this,list);
        recyclerView.setAdapter(notificationAdapter);

        ImageButton back_btn = findViewById(R.id.backbtn);
        back_btn.setOnClickListener(v -> finish());

        loaderView = findViewById(R.id.dots_progress);
        loaderView.setVisibility(View.VISIBLE);

        firebaseFirestore= FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Notification ").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.d(TAG, ""+task.getResult().size());
                for(DocumentSnapshot documentSnapshot : task.getResult()){
                    Log.d(TAG, "notification found: " + documentSnapshot.getString("Heading"));
                    list.add(new NotificationData(
                            documentSnapshot.getString("Heading"),
                            documentSnapshot.getString("Subheading"),
                            documentSnapshot.getString("Time")
                    ));
                }
                notificationAdapter.notifyDataSetChanged();
            }else{
                Log.d(TAG, "Error getting documents", task.getException());
            }
            loaderView.setVisibility(View.GONE);
        });

        /*database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                     NotificationData notificationData=dataSnapshot.getValue(NotificationData.class);
                     list.add(notificationData);
                 }
                 notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
}