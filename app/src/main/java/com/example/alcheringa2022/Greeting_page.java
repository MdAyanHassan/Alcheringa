package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;

public class Greeting_page extends AppCompatActivity {
    Button signupButton,Login_button;
    FirebaseAuth firebaseAuth;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting_page);
        signupButton=findViewById(R.id.SignUp_button);
        Login_button=findViewById(R.id.Login_button);
        firebaseAuth=FirebaseAuth.getInstance();
        Login_button.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
        signupButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),SignUp.class)));
        videoView=findViewById(R.id.videoview);
        Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.greeting_video);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
    @Override
    protected void onResume() {
        videoView.resume();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        videoView.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }
}