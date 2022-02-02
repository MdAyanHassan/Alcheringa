package com.example.alcheringa2022;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    TextView SignupTextView;
    ImageView backButton;
    LinearLayout google_login_btn;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private EditText Email,Password;
    //TextInputLayout Password;
    Button loginButton;
    LinearLayout signInButtonO;
    SharedPreferences sharedPreferences;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignupTextView =findViewById(R.id.signup_here);
        backButton =findViewById(R.id.back_button);
        google_login_btn=findViewById(R.id.google_login_btn);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        signInButtonO = findViewById(R.id.sign_in_outlook);
        loginButton =findViewById(R.id.loginbtn);
        LinearLayout forgotPassword = findViewById(R.id.forgot_password);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);

        Password.setTransformationMethod(new HiddenPassTransformationMethod());

        loginButton.setOnClickListener(v -> CustomLogin());
        google_login_btn.setOnClickListener(v -> google_login_callback());
        backButton.setOnClickListener(v -> goBack());
        SignupTextView.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUp.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        signInButtonO.setOnClickListener(v -> MicrosoftLogin());
        forgotPassword.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ResetPassword.class)));

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() { goBack(); }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

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
                    Log.d(TAG, "Successfully authenticated with Outlook"+ authResult.getAdditionalUserInfo().getProfile().get("displayName").toString());

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
                        //Toast.makeText(Login.this, "Name: " + name + " \n" + "Email: " + email, Toast.LENGTH_LONG).show();

                        String finalName = name;
                        String finalEmail = email;
                        firebaseFirestore.collection("USERS").document(email).get().addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful() && task2.getResult().exists()) {
                                toast("Welcome back "+finalName);
                                saveDetails(finalName, finalEmail);
                                setInterests(email);
                                startMainActivity();
                            }else{
                                final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                currentUser.delete();
                                firebaseAuth.signOut();
                                toast("You are not registered with us. Please create a new account");
                            }
                        });
                    }catch(Exception e){
                        toast("Could not get user email");
                    }
                })
            .addOnFailureListener(
                e -> {
                    Log.d(TAG, "onFailure"+ e.getMessage());
                    Toast.makeText(this,"Login with Microsoft failed", Toast.LENGTH_LONG ).show();
                });
    }

    private void setInterests(String email) {
        firebaseFirestore.collection("USERS").document(email).collection("interests").document("interests").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                try{ArrayList<String> interests = (ArrayList<String>) task.getResult().get("interests");
                    toast(interests.toString());
                    Set<String> set = new HashSet<>(interests);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("interests", set);
                    editor.apply();
                }catch (Exception ignored){}

            }
        });

    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
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

    private void CustomLogin() {
        String email=Email.getText().toString();
        //String password=Password.getEditText().getText().toString();
        String password=Password.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()){
            custom_Login_start(email,password);
        } else if(email.isEmpty()){
            Email.setError("Please fill the email");
            Email.requestFocus();
        } else {
            Password.setError("Please fill the password");
            Password.requestFocus();
        }
    }

    private void custom_Login_start(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                assert firebaseUser != null;
                if(firebaseUser.isEmailVerified()){
                    toast("Login Successful");
                    firebaseFirestore.collection("USERS").document(email).get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            String nameString = task1.getResult().getString("Name");
                            Log.d(TAG, "The entire data obtained is: " + task1.getResult().getData());
                            saveDetails(nameString,email);
                        }else{
                            Toast.makeText(this, "Could not get username",Toast.LENGTH_SHORT).show();
                        }
                    });
                    firebaseFirestore.collection("USERS").document(email).collection("interests").document("interests").get().addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful() && !task1.getResult().exists()){
                            Intent intent = new Intent(this, InterestsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            setInterests(email);
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        finish();
                    });
                }
                else{ toast("Please verify your email first"); }
            }
            else{ toast("Authentication Failed | Create Account"); }
        });

    }

    private void toast(String text) {
        Toast.makeText(getApplicationContext(), ""+text, Toast.LENGTH_LONG).show();
    }

    private void google_login_callback() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignIn();
    }

    private void GoogleSignIn() {
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
                Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());
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
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {

                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    assert (user != null ? user.getEmail() : null) != null;
                    firebaseFirestore.collection("USERS").document(user.getEmail()).get().addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful() && task2.getResult().exists()) {
                            saveDetails(user.getDisplayName(), user.getEmail());
                            setInterests(user.getEmail());
                            Log.d(TAG, "Login with Google Successful");
                            toast("Welcome back "+ user.getDisplayName());
                            startMainActivity();
                        }else{
                            final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            assert currentUser != null;
                            currentUser.delete();
                            firebaseAuth.signOut();
                            toast("You are not registered with us. Please create a new account");
                        }
                    });
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private static class HiddenPassTransformationMethod implements TransformationMethod {

        private char DOT = '\u2022';

        @Override
        public CharSequence getTransformation(final CharSequence charSequence, final View view) {
            return new PassCharSequence(charSequence);
        }

        @Override
        public void onFocusChanged(final View view, final CharSequence charSequence, final boolean b, final int i,
                                   final Rect rect) {
            //nothing to do here
        }

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
                return new PassCharSequence(charSequence.subSequence(start, end));
            }
        }
    }

    public void ShowHidePass(View view){

        if(view.getId()==R.id.show_pass_btn){

            if(!Password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.show);

                //Show Password
                Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.hide);

                //Hide Password
                //Password.setTransformationMethod(HiddenPassTransformationMethod.getInstance());
                Password.setTransformationMethod(new HiddenPassTransformationMethod());

            }
        }
    }

}