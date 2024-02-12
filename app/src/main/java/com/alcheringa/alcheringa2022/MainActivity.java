package com.alcheringa.alcheringa2022;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alcheringa.alcheringa2022.Database.DBHandler;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
   ShadowIndicatorBottomNavigationView bottomNavigationView;
    Events events_fragment;
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    FirebaseFirestore firebaseFirestore;
    ImageButton epass;
    FirebaseAuth firebaseAuth;
    NavController NavController;
    DrawerLayout drawer;
    NavigationView navigationView;
    public static int index;
    String shared_name, shared_photoUrl;
    View tnc_page, privacy_page, about_page;
    TextView user_name, version, website;
    ImageView user_photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        events_fragment = new Events();
        bottomNavigationView=findViewById(R.id.bottomNavigationView);


       // epass=findViewById(R.id.epass);
        //bottomNavigationView.setBackground(null);
        //bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        //bottomNavigationView.setOnNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        shared_name = sharedPreferences.getString("name", "");
        shared_photoUrl = sharedPreferences.getString("photourl", "");


        dbHandler=new DBHandler(getApplicationContext());
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        String email=firebaseAuth.getCurrentUser().getEmail();
        NavHostFragment navHostFragment =
                 (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController = navHostFragment.getNavController();

         NavController.setGraph(R.navigation.my_nav);
        NavigationUI.setupWithNavController(bottomNavigationView,NavController);
         NavController.addOnDestinationChangedListener((controller, destination, arguments) -> bottomNavigationView.onItemSelected(destination.getId(),true));


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//
//        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);



        version = findViewById(R.id.version);
        version.setText("V "+ com.google.firebase.BuildConfig.VERSION_NAME);

        View header = navigationView.getHeaderView(0);
        user_name = header.findViewById(R.id.user_name_text);
        user_photo = header.findViewById(R.id.user_photo);
        if(!shared_name.equals(""))
        {
            user_name.setText(shared_name);
        }
        user_name.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProfileActivity.class)));

        if(!shared_photoUrl.equals(""))
        {
            Glide.with(this).load(shared_photoUrl).into(user_photo);
        }
        user_photo.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProfileActivity.class)));

        website = findViewById(R.id.website_link);
        website.setOnClickListener(v->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.alcheringa.in"))));

        tnc_page = findViewById(R.id.tnc);
        tnc_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),TermsAndConditions.class)));

        privacy_page=findViewById(R.id.privacy_policy);
        privacy_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),PrivacyPolicy.class)));

        about_page=findViewById(R.id.about);
        about_page.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),AboutPage.class)));


        /*boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);
        if(!isLoggedIn){
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }*/
//        epass.setOnClickListener(v->{
//            if(checkIsPassExistForUser(email)){
//                startActivity(new Intent(getApplicationContext(),E_pass.class));
//            }
//            else{
//                startActivity(new Intent(getApplicationContext(), ScannerActivity.class));
//            }
//        });

        //getVersionInfo();
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


//        index=R.id.home_nav;
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Home()).commit();
//        bottomNavigationView.getMenu().findItem(R.id.events).setChecked(true);
//        getVersionInfo();


       /* try{
            Intent intent = getIntent();
            String fragment_name = intent.getExtras().getString("fragment");
            if(fragment_name.equals("merch")){
                bottomNavigationView.setSelectedItemId(R.id.merch);
                index=R.id.merch;
            }
        }catch(Exception ignored){}*/


    }

    private boolean checkIsPassExistForUser(String email) {
        return false;
    }

    private void getVersionInfo() {
        int versionCode = BuildConfig.VERSION_CODE;
        firebaseFirestore.collection("Version").document("version").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    int version_info=Integer.parseInt(documentSnapshot.getString("version"));
                    //Toast.makeText(MainActivity.this, "equals"+versionCode, Toast.LENGTH_SHORT).show();
                    if(version_info>versionCode){
                        ShowDialog();
                    }
                }
                else{
                    //Toast.makeText(MainActivity.this, "Version code not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ShowDialog() {

        new AlertDialog.Builder(MainActivity.this).setView(R.layout.animationforupdatedialogue)
                .setTitle("New Version is Available")
                .setCancelable(false)
                .setPositiveButton("Download Update", (dialog, which) -> { try {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("https://play.google.com/store/apps/details?id=com.alcheringa.alcheringa2022"));
                        startActivity(viewIntent);
                    }catch(Exception e) {
                        Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                })



                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setIcon(android.R.drawable.ic_dialog_alert)

                .show();
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()){
//            case R.id.events:
//                if(index!=R.id.events){
////                    index=R.id.events;
////                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, events_fragment).commit();
////                    bottomNavigationView.getMenu().findItem(R.id.events).setChecked(true);
//                      //  navController.navigate(R.id);
//
//                }
//
//                break;
//            case R.id.home_nav:
//                if(index!=R.id.home_nav) {
//                    index=R.id.home_nav;
//
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Home()).commit();
//                    bottomNavigationView.getMenu().findItem(R.id.home_nav).setChecked(true);
//                }
//                break ;
//            case R.id.merch:
//                if(index!=R.id.merch) {
//                    index=R.id.merch;
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new MerchFragment()).commit();
//                    bottomNavigationView.getMenu().findItem(R.id.merch).setChecked(true);
//                }
//                break;
//            case R.id.schedule:
//                if(index!=R.id.schedule) {
//                    index=R.id.schedule;
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Schedule()).commit();
//                    bottomNavigationView.getMenu().findItem(R.id.schedule).setChecked(true);
//                }
//                break;
//        }
//        return false;
//    }

    @Override
    protected void onResume() {
        getVersionInfo();
        super.onResume();
        String newshared_photoUrl = sharedPreferences.getString("photourl", "");
        if(!newshared_photoUrl.equals("") && newshared_photoUrl != shared_photoUrl)
        {
            Glide.with(this).load(newshared_photoUrl).into(user_photo);
        }
        shared_name = sharedPreferences.getString("name", "");
        user_name.setText(shared_name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if(bottomNavigationView.getSelectedItemId()==R.id.home_nav){
//            super.onBackPressed();
//        }
//        else/* if()*/{
//            index=R.id.home_nav;
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new Home()).commit();
//            bottomNavigationView.getMenu().findItem(R.id.home_nav).setChecked(true);

        //}

    }

    private void signOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("name");
        editor.remove("email");
        editor.remove("photourl");
        editor.remove("interests");
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        firebaseAuth.signOut();
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setCheckable(false);
        }


      switch (id){
          case R.id.miOrders: startActivity(new Intent(getApplicationContext(),YourOrdersActivity.class));break;
          case R.id.miContactUs: startActivity(new Intent(getApplicationContext(),ContactUsActivity.class));break;
          case R.id.miFAQs: startActivity(new Intent(getApplicationContext(),FaqActivity.class));break;
          case R.id.miProfile: startActivity(new Intent(getApplicationContext(),ProfileActivity.class));break;
          case R.id.miSponsors: startActivity(new Intent(getApplicationContext(),SponsorsActivity.class));break;
          case R.id.miTeam: startActivity(new Intent(getApplicationContext(),TeamActivity.class));break;
          case R.id.miSignOut: signOut();
        }

        drawer.closeDrawer(Gravity.RIGHT);


        return true;
    }
}