package com.alcheringa.alcheringa2022;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alcheringa.alcheringa2022.Model.Sponsor_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Sponsors extends AppCompatActivity implements onItemClick{

    RecyclerView recyclerView;
    List<Sponsor_model> sponsor_modelList;
    SponsorsAdapter sponsors_adapter;
    FirebaseFirestore firestore;
    ImageButton imageView;
    LoaderView loaderView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        recyclerView=findViewById(R.id.sponsor_recyclerview);
        imageView=findViewById(R.id.backbtn);
        imageView.setOnClickListener(v -> finish());

        firestore= FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        sponsor_modelList=new ArrayList<>();
        sponsors_adapter=new SponsorsAdapter(sponsor_modelList, this, this);

        populate_sponsors();

        loaderView = findViewById(R.id.dots_progress);
        loaderView.setVisibility(View.VISIBLE);
    }

    private void populate_sponsors() {

        firestore.collection("Sponsors").orderBy("Rank").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot documentSnapshot : task.getResult()){
                    Log.d("sponsor", documentSnapshot.getString("Logo"));
                    sponsor_modelList.add(new Sponsor_model(documentSnapshot.getString("Logo"), documentSnapshot.getString("WebURL")));
                    recyclerView.setAdapter(sponsors_adapter);
                }
                loaderView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void Onclick(int position) {
        Log.d("delete sponsor", sponsor_modelList.get(position).getWebURL());
        Uri uri = Uri.parse(sponsor_modelList.get(position).getWebURL());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void OnAnyClick(int position) {
    }

    @Override
    public void OnIncrementClick(int position) {
    }

    @Override
    public void OnDecrementClick(int position) {
    }
}