package com.example.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.JsonObject;

//Outlook Login imports:
import com.microsoft.graph.authentication.IAuthenticationProvider; //Imports the Graph sdk Auth interface
import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.http.IHttpRequest;
import com.microsoft.graph.models.extensions.*;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.identity.client.AuthenticationCallback; // Imports MSAL auth methods
import com.microsoft.identity.client.*;
import com.microsoft.identity.client.exception.*;

public class SignUp extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;
    //SignInButton signInButton;
    LinearLayout signInButton;
    FirebaseAuth mAuth;

    private final static String[] SCOPES = {"user.read"};
    /* Azure AD v2 Configs */
    //final static String AUTHORITY = "https://login.microsoftonline.com/common";
    private ISingleAccountPublicClientApplication mSingleAccountApp;

    private static final String TAG = MainActivity.class.getSimpleName();

    LinearLayout signInButtonO;
    Button signOutButton,signupButton;

    TextView logTextView;
    ImageView backbutton;
    private EditText Name,Email,Password;
    TextView currentUserTextView;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signInButton = findViewById(R.id.signInButton);
        mAuth=FirebaseAuth.getInstance();
        signupButton=findViewById(R.id.signupbutton);
        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        signInButtonO = findViewById(R.id.sign_in_outlook);

        firebaseFirestore= FirebaseFirestore.getInstance();

        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);

        logTextView=findViewById(R.id.login_here);
        backbutton=findViewById(R.id.back_button);
        signInButton.setOnClickListener(v -> GooogleSignIn());
        logTextView.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
        backbutton.setOnClickListener(v -> finish());
        signupButton.setOnClickListener(v -> CustomSignup());
        signInButtonO.setOnClickListener(v -> MicrosoftLogin());

        //signOutButton = findViewById(R.id.clearCache);

//        if(isLoggedIn){
//            startMainActivity();
//        }


    }

    private void MicrosoftLogin() {
        Log.d(TAG, "MicrosoftLogin");
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
        provider.addCustomParameter("tenant", "850aa78d-94e1-4bc6-9cf3-8c11b530701c");

        mAuth.startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                .addOnSuccessListener(
                        authResult -> {
                            Log.d(TAG, "onSuccess");
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            // The OAuth access token can also be retrieved:
                            // authResult.getCredential().getAccessToken().
                            // The OAuth ID token can also be retrieved:
                            // authResult.getCredential().getIdToken().

                            Log.d(TAG, "Success: "+ authResult.getAdditionalUserInfo().getProfile().get("displayName").toString());

                            Log.d(TAG, "Successfully authenticated");

                            // Update UI
                            String name = "No name found";
                            String email = "No email found";
                            String rollno = "No Roll No. found";
                            try{
                                email = authResult.getAdditionalUserInfo().getProfile().get("mail").toString();
                            }catch(Exception ignored){}
                            try{
                                name = authResult.getAdditionalUserInfo().getProfile().get("displayName").toString();
                            }catch(Exception ignored){}
                            try{
                                rollno = authResult.getAdditionalUserInfo().getProfile().get("surname").toString();
                            }catch(Exception ignored){}
                            Log.d(TAG, "Name: " + name);
                            Log.d(TAG, "Email: " + email);
                            Log.d(TAG, "Roll No: " + rollno);
                            Toast.makeText(SignUp.this, "Name: " + name + " \n" + "Email: " + email, Toast.LENGTH_LONG).show();

                            saveDetails(name, email);
                            startMainActivity();
                        })
                .addOnFailureListener(
                        e -> {
                            Log.d(TAG, "onFailure"+ e.getMessage());
                            // Handle failure.
                        });
    }

    private void CustomSignup() {

        if(Name.length()>0 && Email.length() >0 && Password.length()>0 && Password.length()>7){
            String name= Name.getText().toString();
            String email= Email.getText().toString();
            String password= Password.getText().toString();
            StartCustomSignup(name,email,password);
        }
        else if(Name.length()==0){
               Name.setError("Please fill the name");
               Name.requestFocus();
        }
        else if(Email.length()==0){
            Email.setError("Please fill the email");
            Email.requestFocus();
        }
        else if(Password.length()==0){
            Password.setError("Please fill the password");
            Password.requestFocus();
        }
        else if(Password.length()<=7){
            Password.setError("Password length must be greater than 7");
            Password.requestFocus();
        }


    }

    private void StartCustomSignup(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "The mail has been sent to your email address please verify", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser=mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            emailVerification();
                            RegisterUserInDatabase();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }
                    }

                    private void emailVerification() {
                        FirebaseUser firebaseUser=mAuth.getCurrentUser();
                        assert firebaseUser != null;
                        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                            }
                        });
                    }
                });

    }

    private void RegisterUserInDatabase() {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        assert email != null;
        firebaseFirestore.collection("USERS").document(email).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                if (!value.exists()) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("Name", "VIPIN");
                    data.put("Email", email);
                    firebaseFirestore.collection("USERS").document(email).set(data).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Added in the Database", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void GooogleSignIn() {
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
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                            RegisterUserInDatabase();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    /* ################################ Custom Signup Functions ############################### */


    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveDetails(String name, String email){
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("name", name);
        editor.putString("email",email);
        editor.apply();
    }

}