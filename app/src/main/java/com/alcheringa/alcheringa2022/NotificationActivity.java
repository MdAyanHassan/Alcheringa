package com.alcheringa.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = "NotificationActivity";
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    NotificationAdapter notificationAdapter;
    ArrayList<NotificationData> list;
//    LoaderView loaderView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView=findViewById(R.id.notificationlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        notificationAdapter=new NotificationAdapter(this,list);
        recyclerView.setAdapter(notificationAdapter);

        ImageButton back_btn = findViewById(R.id.backbtn);
        back_btn.setOnClickListener(v -> finish());

/*        loaderView = findViewById(R.id.dots_progress);
        loaderView.setVisibility(View.VISIBLE);*/

        firebaseFirestore= FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);


        firebaseFirestore.collection("Notification").orderBy("Timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.d(TAG, "No of notifications: "+task.getResult().size());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("seen_notifs_count", task.getResult().size());
                editor.apply();

                for(DocumentSnapshot documentSnapshot : task.getResult()){
                    Log.d(TAG, "notification found: " + documentSnapshot.getString("Heading"));
                    Log.d(TAG, ""+documentSnapshot.getDate("Timestamp").getHours());

                    list.add(new NotificationData(
                            documentSnapshot.getString("Heading"),
                            documentSnapshot.getString("Subheading"),
                            documentSnapshot.getDate("Timestamp")
                    ));
                }
                if(!list.isEmpty()){findViewById(R.id.emptynotificationview).setVisibility(View.GONE);}
                notificationAdapter.notifyDataSetChanged();
            }else{
                Log.d(TAG, "Error getting documents", task.getException());
            }
//            loaderView.setVisibility(View.GONE);
        });
    }
}