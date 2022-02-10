package com.alcheringa.alcheringa2022;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.commons.text.WordUtils;

public class SignUp extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;
    LinearLayout signInButton;
    FirebaseAuth firebaseAuth;

    VideoView videoView;

    private static final String TAG = MainActivity.class.getSimpleName();

    LinearLayout signInButtonO;
    Button signupButton;

    TextView loginTextView;
    ImageView backButton;
    LoaderView loaderView;
    private EditText Name,Email,Password;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loaderView = findViewById(R.id.dots_progress);
        loaderView.setVisibility(View.GONE);


        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        signInButton = findViewById(R.id.signInButton);
        signupButton=findViewById(R.id.signupbutton);
        signInButtonO = findViewById(R.id.sign_in_outlook);
        backButton =findViewById(R.id.back_button);
        loginTextView =findViewById(R.id.login_here);

        firebaseAuth =FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);

        Password.setTransformationMethod(new HiddenPassTransformationMethod());
        Password.setSelection(Password.getText().length());

        loginTextView.setOnClickListener(v -> {
            Intent i = new Intent(this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        });

        signupButton.setOnClickListener(v -> CustomSignup());
        signInButton.setOnClickListener(v -> {
            loaderView.setVisibility(View.VISIBLE);
            GoogleSignIn();
        });
        signInButtonO.setOnClickListener(v -> {
            loaderView.setVisibility(View.VISIBLE);
            MicrosoftLogin();
        });

        backButton.setOnClickListener(v -> goBack());
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {@Override public void handleOnBackPressed() { goBack(); }};
        this.getOnBackPressedDispatcher().addCallback(this, callback);

        video_load();
    }

    private void goBack() {
        Intent intent = new Intent(getApplicationContext(),Greeting_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void MicrosoftLogin() {
        Log.d(TAG, "MicrosoftLogin");
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
        provider.addCustomParameter("tenant", "850aa78d-94e1-4bc6-9cf3-8c11b530701c");

        firebaseAuth.startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener(
                        authResult -> {
                            Log.d(TAG, "Authenticated with Outlook"+ authResult.getAdditionalUserInfo().getProfile().get("displayName").toString());

                            // Update UI
                            String email;
                            String name = "No name found";
                            String rollno = "No Roll No. found";
                            try{
                                email = authResult.getAdditionalUserInfo().getProfile().get("mail").toString();
                                try{
                                    name = WordUtils.capitalizeFully(authResult.getAdditionalUserInfo().getProfile().get("displayName").toString());
                                }catch(Exception ignored){}
                                try{
                                    rollno = authResult.getAdditionalUserInfo().getProfile().get("surname").toString();
                                }catch(Exception ignored){}


                                Log.d(TAG, "Name: " + name);
                                Log.d(TAG, "Email: " + email);
                                Log.d(TAG, "Roll No: " + rollno);

                                String finalName = name;
                                String finalEmail = email;
                                saveDetails(finalName, finalEmail);
                                firebaseFirestore.collection("USERS").document(email).get().addOnCompleteListener(task2 -> {
                                    loaderView.setVisibility(View.GONE);
                                    if (task2.isSuccessful() && !task2.getResult().exists()) {
                                        RegisterUserInDatabase();
                                        toast("Welcome " + finalName);
                                        startMainActivity();
                                    }else{
                                        toast("Welcome back " + finalName);
                                        Intent intent = new Intent(this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }catch(Exception e){
                                loaderView.setVisibility(View.GONE);
                                toast("Could not get your email from Outlook");
                            }
                        })
                .addOnFailureListener(
                        e -> {
                            Log.d(TAG, "onFailure"+ e.getMessage());
                            Toast.makeText(this,"Signup with Microsoft failed", Toast.LENGTH_LONG ).show();
                            loaderView.setVisibility(View.GONE);
                        });
    }

    private void toast(String text) {
        Toast.makeText(getApplicationContext(), ""+text, Toast.LENGTH_LONG).show();
    }

    private void CustomSignup() {

        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean isEmailValid = Pattern.compile(emailRegex)
                .matcher(Email.getText().toString().trim())
                .matches();

        if(Name.length()>0 && isEmailValid && Password.length()>7){
            String name= Name.getText().toString();
            String email= Email.getText().toString().trim();
            String password= Password.getText().toString().trim();
            loaderView.setVisibility(View.VISIBLE);
            StartCustomSignup(name,email,password);
        } else if(Name.length()==0){
               Name.setError("Please fill your name");
               Name.requestFocus();
        } else if(Email.length()==0){
            Email.setError("Please fill your email address");
            Email.requestFocus();
        } else if(!isEmailValid){
            Email.setError("Please enter a valid email address");
            Email.requestFocus();
        } else if(Password.length()==0){
            Password.setError("Please enter a password");
            Password.requestFocus();
        } else if(Password.length()<=7){
            Password.setError("Password length must be greater than 7");
            Password.requestFocus();
        }
    }

    private void StartCustomSignup(String name, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "The mail has been sent to your email address please verify", Toast.LENGTH_SHORT).show();
                            /*FirebaseUser firebaseUser=mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            firebaseUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Log.d(TAG, "User profile updated.");

                                        }
                                    });*/
                            emailVerification();
                            RegisterUserInDatabaseCustom(name, email);

                        } else {
                            loaderView.setVisibility(View.GONE);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Signup failed: "+task.getException(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    private void emailVerification() {
                        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
                        assert firebaseUser != null;
                        firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                            FirebaseAuth.getInstance().signOut();
                            loaderView.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        });
                    }
                });
    }

    private void RegisterUserInDatabaseCustom(String name, String email){
        firebaseFirestore.collection("USERS").document(email).addSnapshotListener((value, error) -> {
            assert value != null;
            if (!value.exists()) {
                Map<String, Object> data = new HashMap<>();
                data.put("Name", name);
                data.put("Email", email);
                firebaseFirestore.collection("USERS").document(email).set(data).
                        addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Added user in database");
                            } else {
                                Toast.makeText(getApplicationContext(), "Error Occurred while adding user to the database", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void RegisterUserInDatabase() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        assert email != null;
        firebaseFirestore.collection("USERS").document(email).addSnapshotListener((value, error) -> {
            assert value != null;
            if (!value.exists()) {
                Map<String, Object> data = new HashMap<>();
                data.put("Name", user.getDisplayName());
                data.put("Email", email);
                firebaseFirestore.collection("USERS").document(email).set(data).
                        addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Added user in database");
                            } else {
                                Toast.makeText(getApplicationContext(), "Error Occurred while adding user to the database", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void GoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                loaderView.setVisibility(View.GONE);
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }else{
            loaderView.setVisibility(View.GONE);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        assert (user != null ? user.getEmail() : null) != null;

                        saveDetails(user.getDisplayName(), user.getEmail());
                        Log.d(TAG, "Signup with Google Successful");

                        firebaseFirestore.collection("USERS").document(user.getEmail()).get().addOnCompleteListener(task2 -> {
                            loaderView.setVisibility(View.GONE);
                            if (task2.isSuccessful() && !task2.getResult().exists()) {
                                RegisterUserInDatabase();
                                toast("Welcome " + user.getDisplayName());
                                startMainActivity();
                            }else{
                                toast("Welcome back " + user.getDisplayName());
                                Intent intent = new Intent(this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        loaderView.setVisibility(View.GONE);
                        Log.d(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /* ################################ Custom Signup Functions ############################### */


    private void startMainActivity(){
        Intent intent = new Intent(this, InterestsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void saveDetails(String name, String email){
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email",email);
        editor.apply();
    }

    private class HiddenPassTransformationMethod implements TransformationMethod {

        private char DOT = '\u2022';

        @Override
        public CharSequence getTransformation(final CharSequence charSequence, final View view) {
            return new SignUp.HiddenPassTransformationMethod.PassCharSequence(charSequence);
        }

        @Override
        public void onFocusChanged(final View view, final CharSequence charSequence, final boolean b, final int i,
                                   final Rect rect) { }

        private class PassCharSequence implements CharSequence {

            private final CharSequence charSequence;

            public PassCharSequence(final CharSequence charSequence) {
                this.charSequence = charSequence;
            }

            @Override
            public char charAt(final int index) {
                return DOT;
            }

            @Override
            public int length() {
                return charSequence.length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return new SignUp.HiddenPassTransformationMethod.PassCharSequence(charSequence.subSequence(start, end));
            }
        }
    }

    public void ShowHidePass(View view){

        if(view.getId()==R.id.show_pass_btn){

            if(!Password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.show);

                //Show Password
                Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                Password.setSelection(Password.getText().length());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.hide);

                //Hide Password
                //Password.setTransformationMethod(HiddenPassTransformationMethod.getInstance());
                Password.setTransformationMethod(new HiddenPassTransformationMethod());
                Password.setSelection(Password.getText().length());

            }
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
    private void video_load() {
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

}