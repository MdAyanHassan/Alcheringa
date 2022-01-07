package com.example.alcheringa2022;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alcheringa2022.Model.Merch_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Merch extends Fragment implements onItemClick{
    RecyclerView recyclerView;
    List<Merch_model> merch_modelList;
    Merch_Adapter merch_adapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_merch,container,false);
        recyclerView=view.findViewById(R.id.merch_recyclerview);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        merch_modelList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        merch_adapter=new Merch_Adapter(merch_modelList,getContext(),this);

        populate_merch();
        return  view;

    }

    private void populate_merch() {
        firestore.collection("Merch").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot documentSnapshot : task.getResult()){
                    merch_modelList.add(new Merch_model(documentSnapshot.getString("Name"),documentSnapshot.getString("Material")
                            ,documentSnapshot.getString("Price"),documentSnapshot.getString("Description"),"1",
                            documentSnapshot.getBoolean("Available"),documentSnapshot.getBoolean("Small"),
                            documentSnapshot.getBoolean("Medium"),documentSnapshot.getBoolean("Large"),
                            documentSnapshot.getBoolean("Large")));
                    recyclerView.setAdapter(merch_adapter);

                }
            }
        });


    }

    @Override
    public void Onclick(int position) {
        Toast.makeText(getContext(), "Ritesh", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(),Merch_Description.class);
        intent.putExtra("item",merch_modelList.get(position));
        startActivity(intent);
    }

    @Override
    public void OnIncrementClick(int position) {

    }

    @Override
    public void OnDecrementClick(int position) {

    }
}
