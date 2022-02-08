package com.alcheringa.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class InterestsActivity extends AppCompatActivity {

    ArrayList<String> interests;
    SharedPreferences sharedPreferences;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        interests = new ArrayList<>();
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
    }

    public void interestItemClick(View v){
        TextView t = (TextView) v;

        int color=t.getCurrentTextColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & color));

        if(hexColor.equals("#FFFFFF")){
            t.setBackgroundResource(R.drawable.interests_highlighted);
            t.setTextColor(Color.parseColor("#EE6337"));
            interests.add(t.getText().toString());
        }else{
            t.setBackgroundResource(R.drawable.interests);
            t.setTextColor(Color.parseColor("#FFFFFF"));
            interests.remove(t.getText().toString());
        }

    }

    public void submit(View v){
        uploadToFirebase();
        Set<String> set = new HashSet<>(interests);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("interests", set);
        editor.apply();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    public void uploadToFirebase(){
        String email= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

        Map<String,Object> data=new HashMap<>();

        data.put("interests", interests);

        assert email != null;
        firebaseFirestore.collection("USERS").document(email).collection("interests").document("interests").set(data).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.d("TAG","Interests added to firebase");
                //Toast.makeText(getApplicationContext(), "Interests added to firebase", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Some Error Occurred while adding interests", Toast.LENGTH_SHORT).show();

            }
        });
    }
}