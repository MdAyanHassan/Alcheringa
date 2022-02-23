package com.alcheringa.alcheringa2022;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Greeting_page extends AppCompatActivity {
    Button signupButton,Login_button;
    FirebaseAuth firebaseAuth;
    VideoView videoView;
    private final int REQUEST_CODE=11;
    AppUpdateManager appUpdateManager;

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
        check_update_available() ;
    }

    private void check_update_available() {
        appUpdateManager= AppUpdateManagerFactory.create(Greeting_page.this);
        Task<AppUpdateInfo> appUpdateInfoTask=appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if(appUpdateInfo.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE
                            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,Greeting_page.this,REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE ){
            Toast.makeText(this, "start Download", Toast.LENGTH_SHORT).show();
        }
        if(resultCode!=RESULT_OK){
            Log.d("UPDATE","Update flow failed"+resultCode);
        }
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