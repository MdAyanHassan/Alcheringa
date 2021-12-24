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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private final static String[] SCOPES = {"user.read"};
    /* Azure AD v2 Configs */
    //final static String AUTHORITY = "https://login.microsoftonline.com/common";
    private ISingleAccountPublicClientApplication mSingleAccountApp;

    private static final String TAG = MainActivity.class.getSimpleName();

    LinearLayout signInButtonO;
    Button signOutButton;
    Button callGraphApiInteractiveButton;
    Button callGraphApiSilentButton;
    TextView logTextView;
    ImageView backbutton;

    TextView currentUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.signInButton);
        //TextView textView = (TextView) signInButton.getChildAt(0);
        //textView.setText("Continue with Google");
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        logTextView=findViewById(R.id.login_here);
        backbutton=findViewById(R.id.back_button);
        signInButton.setOnClickListener(v -> GooogleSignIn());
        logTextView.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
        backbutton.setOnClickListener(v -> finish());


//        if(isLoggedIn){
//            startMainActivity();
//        }
        initializeUI();

        PublicClientApplication.createSingleAccountPublicClientApplication(getApplicationContext(),
                R.raw.auth_config_single_account, new IPublicClientApplication.ISingleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(ISingleAccountPublicClientApplication application) {
                        mSingleAccountApp = application;
                        loadAccount();
                    }
                    @Override
                    public void onError(MsalException exception) {
                        displayError(exception);
                    }
                });



    }

    private void GooogleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String name = account.getDisplayName();
            String email = account.getEmail();

            Toast.makeText(getApplicationContext(), "Signed up successfully", Toast.LENGTH_SHORT).show();

            saveDetails(name, email);
            startMainActivity();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SignUp", "signInResult:failed code=" + e.getStatusCode());

        }
    }



    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void saveDetails(String name, String email){
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("name", name);
        editor.putString("email",email);
        editor.apply();
    }


    /* ################################ Outlook Signup Functions ############################### */

    //When app comes to the foreground, load existing account to determine if user is signed in
    private void loadAccount() {
        if (mSingleAccountApp == null) {
            return;
        }
        mSingleAccountApp.getCurrentAccountAsync(new ISingleAccountPublicClientApplication.CurrentAccountCallback() {
            @Override
            public void onAccountLoaded(@Nullable IAccount activeAccount) {
                Log.d(TAG, "account loaded");

                // You can use the account data to update your UI or your app database.
                //updateUI(activeAccount);
            }

            @Override
            public void onAccountChanged(@Nullable IAccount priorAccount, @Nullable IAccount currentAccount) {
                if (currentAccount == null) {
                    // Perform a cleanup task as the signed-in account changed.
                    performOperationOnSignOut();
                }
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                displayError(exception);
            }
        });
    }

    private void initializeUI(){
        signInButtonO = findViewById(R.id.sign_in_outlook);
        //signOutButton = findViewById(R.id.clearCache);

        //Sign in user
        signInButtonO.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Outlook Signin  ", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "clicked the button 1");
            if (mSingleAccountApp == null) {
                return;
            }
            AuthenticationCallback callback = getAuthInteractiveCallback();
            mSingleAccountApp.signIn(SignUp.this, null, SCOPES, callback);
            Log.d(TAG, "signed_in");
        });

        //Sign out user
        /*signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSingleAccountApp == null){
                    return;
                }
                mSingleAccountApp.signOut(new ISingleAccountPublicClientApplication.SignOutCallback() {
                    @Override
                    public void onSignOut() {
                        updateUI(null);
                        performOperationOnSignOut();
                    }
                    @Override
                    public void onError(@NonNull MsalException exception){
                        displayError(exception);
                    }
                });
            }
        });*/
    }


    private AuthenticationCallback getAuthInteractiveCallback() {
        Log.d(TAG, "getAuthInteractiveCallback");
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                Log.d(TAG, "Successfully authenticated");
                /* Update UI */
                String name = "No name found";
                String email = "No email found";
                try{
                    email = authenticationResult.getAccount().getUsername();
                }catch(Exception ignored){}
                try{
                    name = authenticationResult.getAccount().getClaims().get("name").toString();
                }catch(Exception ignored){}
                Log.d(TAG, "Name: " + name);
                Log.d(TAG, "Email: " + email);
                Toast.makeText(SignUp.this, "Name: " + name + " \n" + "Email: " + email, Toast.LENGTH_LONG).show();

                saveDetails(name, email);
                startMainActivity();

                /* call graph */
                //callGraphAPI(authenticationResult);
            }

            @Override
            public void onError(MsalException exception) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());
                //displayError(exception);
            }
            @Override
            public void onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    private SilentAuthenticationCallback getAuthSilentCallback() {
        return new SilentAuthenticationCallback() {
            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                Log.d(TAG, "Successfully authenticated");
                callGraphAPI(authenticationResult);
            }
            @Override
            public void onError(MsalException exception) {
                Log.d(TAG, "Authentication failed: " + exception.toString());
                displayError(exception);
            }
        };
    }

    private void callGraphAPI(IAuthenticationResult authenticationResult) {

        final String accessToken = authenticationResult.getAccessToken();

        IGraphServiceClient graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(request -> {
                            Log.d(TAG, "Authenticating request," + request.getRequestUrl());
                            request.addHeader("Authorization", "Bearer " + accessToken);
                        })
                        .buildClient();
        graphClient
                .me()
                .drive()
                .buildRequest()
                .get(new ICallback<Drive>() {
                    @Override
                    public void success(final Drive drive) {
                        Log.d(TAG, "Found Drive " + drive.id);
                        displayGraphResult(drive.getRawObject());
                    }

                    @Override
                    public void failure(ClientException ex) {
                        displayError(ex);
                    }
                });
    }

    private void updateUI(@Nullable final IAccount account) {
        if (account != null) {
            signInButtonO.setEnabled(false);
            signOutButton.setEnabled(true);
            callGraphApiInteractiveButton.setEnabled(true);
            callGraphApiSilentButton.setEnabled(true);
            currentUserTextView.setText(account.getUsername());
        } else {
            signInButtonO.setEnabled(true);
            signOutButton.setEnabled(false);
            callGraphApiInteractiveButton.setEnabled(false);
            callGraphApiSilentButton.setEnabled(false);
            currentUserTextView.setText("");
            logTextView.setText("");
        }
    }

    private void displayError(@NonNull final Exception exception) {
        Log.d(TAG, "displayError: " + exception.toString() + exception.getMessage());
    }

    private void displayGraphResult(@NonNull final JsonObject graphResponse) {
        //logTextView.setText(graphResponse.toString());
        Log.d(TAG, "displayGraphResult: " + graphResponse.toString());
    }

    private void performOperationOnSignOut() {
        final String signOutText = "Signed Out.";
        currentUserTextView.setText("");
        Toast.makeText(getApplicationContext(), signOutText, Toast.LENGTH_SHORT)
                .show();
    }
}