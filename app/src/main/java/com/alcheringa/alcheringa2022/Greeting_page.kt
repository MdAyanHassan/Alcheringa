package com.alcheringa.alcheringa2022

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth

// This activity simply presents the GreetingPage with login and signup button. Clicking them lands
// us on Login and Signup pages respectively

class Greeting_page : ComponentActivity() {
    var signupButton: Button? = null
    var Login_button: Button? = null
    var firebaseAuth: FirebaseAuth? = null
    var videoView: VideoView? = null

    //    private final int REQUEST_CODE=11;
    //    AppUpdateManager appUpdateManager;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingPage(
                onLoginClick = {
                    startActivity(
                        Intent(
                            applicationContext, Login::class.java
                        )
                    )
                },
                onSignupClick = {
                    startActivity(
                        Intent(
                            applicationContext, SignUp::class.java
                        )
                    )
                }
            )
        }
        //        setContentView(R.layout.activity_greeting_page);
//        signupButton = findViewById(R.id.SignUp_button)
//        Login_button = findViewById(R.id.Login_button)
        firebaseAuth = FirebaseAuth.getInstance()
//        Login_button.setOnClickListener(View.OnClickListener { v: View? ->
//            startActivity(
//                Intent(
//                    applicationContext, Login::class.java
//                )
//            )
//        })
//        signupButton.setOnClickListener(View.OnClickListener { v: View? ->
//            startActivity(
//                Intent(
//                    applicationContext, SignUp::class.java
//                )
//            )
//        })
//        videoView = findViewById(R.id.videoview)
//        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.greeting_video)
//        videoView.setVideoURI(uri)
//        videoView.start()
//
//        videoView.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })
        // check_update_available() ;
    }

    //    private void check_update_available() {
    //        appUpdateManager= AppUpdateManagerFactory.create(Greeting_page.this);
    //        Task<AppUpdateInfo> appUpdateInfoTask=appUpdateManager.getAppUpdateInfo();
    //        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
    //            @Override
    //            public void onSuccess(AppUpdateInfo appUpdateInfo) {
    //                if(appUpdateInfo.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE
    //                            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
    //                    try {
    //                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,Greeting_page.this,REQUEST_CODE);
    //                    } catch (IntentSender.SendIntentException e) {
    //                        e.printStackTrace();
    //                    }
    //
    //                }
    //            }
    //        });
    //    }
    //
    //    @Override
    //    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //        if(requestCode==REQUEST_CODE ){
    //            Toast.makeText(this, "start Download", Toast.LENGTH_SHORT).show();
    //        }
    //        if(resultCode!=RESULT_OK){
    //            Log.d("UPDATE","Update flow failed"+resultCode);
    //        }
    //    }
    override fun onStart() {
        super.onStart()
        if (firebaseAuth!!.currentUser != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    override fun onPause() {
//        videoView!!.suspend()
        super.onPause()
    }

    override fun onDestroy() {
//        videoView!!.stopPlayback()
        super.onDestroy()
    }
}