package com.alcheringa.alcheringa2022;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.alcheringa.alcheringa2022.Database.DBHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    Events events_fragment;
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    FirebaseFirestore firebaseFirestore;
    public static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        events_fragment = new Events();
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        dbHandler=new DBHandler(getApplicationContext());
        firebaseFirestore=FirebaseFirestore.getInstance();
        /*boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);
        if(!isLoggedIn){
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }*/
        getVersionInfo();
//        Home home=new Home();
//        Schedule schedule=new Schedule();
//        if(home.getHome()){
//            schedule.setSchedule(false);
//            index=R.id.home_nav;
//        }
//        if(schedule.getSchedule()){
//            home.setHome(false);
//            index=R.id.schedule;
//        }



        index=R.id.home_nav;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Home()).commit();
        bottomNavigationView.getMenu().findItem(R.id.events).setChecked(true);


       /* try{
            Intent intent = getIntent();
            String fragment_name = intent.getExtras().getString("fragment");
            if(fragment_name.equals("merch")){
                bottomNavigationView.setSelectedItemId(R.id.merch);
                index=R.id.merch;
            }
        }catch(Exception ignored){}*/


    }

    private void getVersionInfo() {
        String versionCode = BuildConfig.VERSION_NAME+"";
        firebaseFirestore.collection("Version").document("version").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    String version_info=documentSnapshot.getString("version");
                    if(!version_info.equals(versionCode)){
                       ShowDialog();
                        Toast.makeText(MainActivity.this, "equals"+versionCode, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Version code not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ShowDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("New Version is Availble")
                .setMessage("Click ok to download new version")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { try {
                            Intent viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            Uri.parse("https://play.google.com/store/apps/details?id=com.instagram.android"));
                            startActivity(viewIntent);
                        }catch(Exception e) {
                            Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.events:
                if(index!=R.id.events){
                    index=R.id.events;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, events_fragment).addToBackStack(null).commit();
                    bottomNavigationView.getMenu().findItem(R.id.events).setChecked(true);
                }

                break;
            case R.id.home_nav:
                if(index!=R.id.home_nav) {
                    index=R.id.home_nav;

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Home()).addToBackStack(null).commit();
                    bottomNavigationView.getMenu().findItem(R.id.home_nav).setChecked(true);
                }
                break ;
            case R.id.merch:
                if(index!=R.id.merch) {
                    index=R.id.merch;

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new MerchFragment()).addToBackStack(null).commit();
                    bottomNavigationView.getMenu().findItem(R.id.merch).setChecked(true);
                }
                break;
            case R.id.schedule:
                if(index!=R.id.schedule) {
                    index=R.id.schedule;

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Schedule()).addToBackStack(null).commit();
                    bottomNavigationView.getMenu().findItem(R.id.schedule).setChecked(true);
                }
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        getVersionInfo();
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}