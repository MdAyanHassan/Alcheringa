package com.alcheringa.alcheringa2022

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.TransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.alcheringa.alcheringa2022.Login
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import org.apache.commons.text.WordUtils
import java.util.regex.Pattern

class SignUp : ComponentActivity() {
    var sharedPreferences: SharedPreferences? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    var signInButton: LinearLayout? = null
    var firebaseAuth: FirebaseAuth? = null
    var signInButtonO: LinearLayout? = null
    var signupButton: Button? = null
    var loginTextView: TextView? = null
    var backButton: ImageView? = null
    var loaderView = mutableStateOf(false)
    private var Name: String? = null
    private var Email: String? = null
    private var Password: String? = null
    var firebaseFirestore: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SignUpScreen2025(
                onLoginHereClick ={
                    startActivity(
                        Intent(
                            applicationContext ,  Login::class.java
                        )
                    )
                },
                loginWithGoogle = {
                    GoogleSignIn()
                },
                loginWithOutlook = {
                    MicrosoftLogin()
                },
                onEmailChange = {email ->
                    Email = email

                },
                onPasswordChange = {password ->
                    Password = password

                },
                onNameChange = {name ->
                               Name = name

                },
                signUp = {name , email , password ->
                    StartCustomSignup( name,email,password )},
                onBack = {
                    goBack()
                },
                openPrivacyPolicy = {
                    startActivity(
                        Intent(
                            applicationContext ,  PrivacyPolicy::class.java
                        )
                    )
                },
                loginWithApple = { /*TODO*/ }
            )

            if(loaderView.value){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.background.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center,

                    ) {
                    LoadingAnimation3()
                }
            }
        }
//        setContentView(R.layout.activity_sign_up)
//        loaderView = findViewById(R.id.dots_progress)
//        loaderView.setVisibility(View.GONE)
//        Name = findViewById(R.id.name)
//        Email = findViewById(R.id.email)
//        Password = findViewById(R.id.password)
//        signInButton = findViewById(R.id.google_login_btn)
//        signupButton = findViewById(R.id.signupbutton)
//        signInButtonO = findViewById(R.id.sign_in_outlook)
//        backButton = findViewById(R.id.back_button)
//        loginTextView = findViewById(R.id.login_here)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
//        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE)
//        Password?.setTransformationMethod(HiddenPassTransformationMethod())
//        Password?.setSelection(Password!!.getText().length)
//        loginTextView?.setOnClickListener(View.OnClickListener { v: View? ->
//            val i = Intent(this, Login::class.java)
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//            startActivity(i)
//            finish()
//        })
//        signupButton?.setOnClickListener(View.OnClickListener { v: View? -> CustomSignup() })
//        signInButton?.setOnClickListener(View.OnClickListener { v: View? ->
//            loaderView?.setVisibility(View.VISIBLE)
//            GoogleSignIn()
//        })
//        signInButtonO?.setOnClickListener(View.OnClickListener { v: View? ->
//            loaderView?.setVisibility(View.VISIBLE)
//            MicrosoftLogin()
//        })
//        backButton?.setOnClickListener(View.OnClickListener { v: View? -> goBack() })
//        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                goBack()
//            }
//        }
//        this.onBackPressedDispatcher.addCallback(this, callback)

        //video_load();
    }

    private fun goBack() {
        val intent = Intent(applicationContext, Greeting_page::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    private fun MicrosoftLogin() {
        loaderView.value = true
        Log.d(TAG, "MicrosoftLogin")
        val provider = OAuthProvider.newBuilder("microsoft.com")
        provider.addCustomParameter("tenant", "850aa78d-94e1-4bc6-9cf3-8c11b530701c")
        firebaseAuth!!.startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener { authResult: AuthResult ->
                Log.d(
                    TAG, "Authenticated with Outlook" + authResult.additionalUserInfo!!
                        .profile!!["displayName"].toString()
                )

                // Update UI
                val email: String
                var name = "No name found"
                var rollno = "No Roll No. found"
                try {
                    email = authResult.additionalUserInfo!!.profile!!["mail"].toString()
                    try {
                        name = WordUtils.capitalizeFully(
                            authResult.additionalUserInfo!!.profile!!["displayName"].toString()
                        )
                    } catch (ignored: Exception) {
                    }
                    try {
                        rollno = authResult.additionalUserInfo!!.profile!!["surname"].toString()
                    } catch (ignored: Exception) {
                    }
                    Log.d(TAG, "Name: $name")
                    Log.d(TAG, "Email: $email")
                    Log.d(TAG, "Roll No: $rollno")
                    val finalName = name
                    saveDetails(finalName, email)
                    firebaseFirestore!!.collection("USERS").document(email).get()
                        .addOnCompleteListener { task2: Task<DocumentSnapshot> ->
                            loaderView.value = false
                            if (task2.isSuccessful && !task2.result.exists()) {
                                RegisterUserInDatabase()
                                toast("Welcome $finalName")
                                startInterestActivity()
                            } else {
                                toast("Welcome back $finalName")
                                if (task2.result.getString("mode") == "white" || task2.result.getString(
                                        "mode"
                                    ) == "black"
                                ) startMainActivity() else startInterestActivity()
                                /*Intent intent = new Intent(this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();*/
                            }
                        }
                } catch (e: Exception) {
                    firebaseAuth!!.signOut()
                    loaderView.value = false
                    toast("Could not get your email from Outlook")
                }
            }
            .addOnFailureListener { e: Exception ->
                Log.d(TAG, "onFailure" + e.message)
                Toast.makeText(this, "Signup with Microsoft failed", Toast.LENGTH_LONG).show()
                loaderView.value = false
            }
    }

    private fun toast(text: String) {
        Toast.makeText(applicationContext, "" + text, Toast.LENGTH_LONG).show()
    }

//    private fun CustomSignup() {
//        val emailRegex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
//                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
//        val isEmailValid = Pattern.compile(emailRegex)
//            .matcher(Email!!.text.toString().trim { it <= ' ' })
//            .matches()
//        if (Name!!.length() > 0 && isEmailValid && Password!!.length() > 7) {
//            val name = Name!!.text.toString()
//            val email = Email!!.text.toString().trim { it <= ' ' }
//            val password = Password!!.text.toString().trim { it <= ' ' }
//            loaderView!!.visibility = View.VISIBLE
//            StartCustomSignup(name, email, password)
//        } else if (Name!!.length() == 0) {
//            Name!!.error = "Please fill your name"
//            Name!!.requestFocus()
//        } else if (Email!!.length() == 0) {
//            Email!!.error = "Please fill your email address"
//            Email!!.requestFocus()
//        } else if (!isEmailValid) {
//            Email!!.error = "Please enter a valid email address"
//            Email!!.requestFocus()
//        } else if (Password!!.length() == 0) {
//            Password!!.error = "Please enter a password"
//            Password!!.requestFocus()
//        } else if (Password!!.length() <= 7) {
//            Password!!.error = "Password length must be greater than 7"
//            Password!!.requestFocus()
//        }
//    }

    private fun StartCustomSignup(name: String, email: String, password: String) {
        loaderView.value = true
        firebaseAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "The mail has been sent to your email address please verify",
                        Toast.LENGTH_SHORT
                    ).show()
                    emailVerification()
                    RegisterUserInDatabaseCustom(name, email)
                } else {
                    loaderView.value = false
                    task.exception?.let {
                        Log.w(TAG, "createUserWithEmail:failure", it)
                        Toast.makeText(
                            applicationContext, "Signup failed: ${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }


                }


            }

    }

    private fun emailVerification() {
        val firebaseUser = firebaseAuth!!.currentUser!!
        firebaseUser.sendEmailVerification()
            .addOnCompleteListener { task: Task<Void?>? ->
                FirebaseAuth.getInstance().signOut()
                loaderView.value = false
                startActivity(Intent(applicationContext, Login::class.java))
            }
    }

    private fun RegisterUserInDatabaseCustom(name: String, email: String) {
        firebaseFirestore!!.collection("USERS").document(email)
            .addSnapshotListener { value: DocumentSnapshot?, error: FirebaseFirestoreException? ->
                //assert value != null;
                try {
                    if (!(value != null && value.exists())) {
                        val data: MutableMap<String, Any> = HashMap()
                        data["Name"] = name
                        data["Email"] = email
                        firebaseFirestore!!.collection("USERS").document(email).set(data)
                            .addOnCompleteListener { task: Task<Void?> ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Added user in database")
                                } /*else {
                                    Toast.makeText(getApplicationContext(), "Error Occurred while adding user to the database", Toast.LENGTH_SHORT).show();
                                }*/
                            }
                    }
                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
    }

    private fun RegisterUserInDatabase() {
        val user = firebaseAuth!!.currentUser!!
        val email = user.email!!
        firebaseFirestore!!.collection("USERS").document(email)
            .addSnapshotListener { value: DocumentSnapshot?, error: FirebaseFirestoreException? ->
                assert(value != null)
                if (!value!!.exists()) {
                    val data: MutableMap<String, Any?> = HashMap()
                    data["Name"] = user.displayName
                    data["Email"] = email
                    firebaseFirestore!!.collection("USERS").document(email).set(data)
                        .addOnCompleteListener { task: Task<Void?> ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "Added user in database")
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Error Occurred while adding user to the database",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
    }

    private fun GoogleSignIn() {
        loaderView.value = true
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                loaderView.value = false
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(applicationContext, "" + e.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            loaderView.value = false
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val user = firebaseAuth!!.currentUser
                    assert(user?.email != null)
                    saveDetails(user!!.displayName, user.email)
                    Log.d(TAG, "Signup with Google Successful")
                    firebaseFirestore!!.collection("USERS").document(user.email!!).get()
                        .addOnCompleteListener { task2: Task<DocumentSnapshot> ->
                            loaderView.value = false
                            if (task2.isSuccessful && !task2.result.exists()) {
                                RegisterUserInDatabase()
                                toast("Welcome " + user.displayName)
                                startInterestActivity()
                            } else {
                                toast("Welcome back " + user.displayName)
                                if (task2.result.getString("mode") == "white" || task2.result.getString(
                                        "mode"
                                    ) == "black"
                                ) startMainActivity() else startInterestActivity()
                                /*Intent intent = new Intent(this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();*/
                            }
                        }
                } else {
                    loaderView.value = false
                    Log.d(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(applicationContext, "" + task.exception, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    /* ################################ Custom Signup Functions ############################### */
    private fun startInterestActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun startMainActivity() {
        //Intent intent = new Intent(this, MainActivity.class);
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun saveDetails(name: String?, email: String?) {
        val localSharedPreferences = getSharedPreferences("USER", MODE_PRIVATE)
        val editor = localSharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.apply()
    }

    private inner class HiddenPassTransformationMethod : TransformationMethod {
        private val DOT = '\u2022'
        override fun getTransformation(charSequence: CharSequence, view: View): CharSequence {
            return PassCharSequence(charSequence)
        }

        override fun onFocusChanged(
            view: View, charSequence: CharSequence, b: Boolean, i: Int,
            rect: Rect
        ) {
        }

        private inner class PassCharSequence(private val charSequence: CharSequence) :
            CharSequence {
//            override fun charAt(index: Int): Char {
//                return DOT
//            }

            override val length: Int
                get() = charSequence.length


            override fun get(index: Int): Char {
                return '\u2022' // DOT character
            }


            override fun subSequence(start: Int, end: Int): CharSequence {
                return PassCharSequence(charSequence.subSequence(start, end))
            }
        }
    }

//    fun ShowHidePass(view: View) {
//        if (view.id == R.id.show_pass_btn) {
//            if (Password!!.transformationMethod != HideReturnsTransformationMethod.getInstance()) {
//                (view as ImageView).setImageResource(R.drawable.hide)
//
//                //Show Password
//                Password!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
//                Password!!.setSelection(Password!!.text.length)
//            } else {
//                (view as ImageView).setImageResource(R.drawable.show)
//
//                //Hide Password
//                //Password.setTransformationMethod(HiddenPassTransformationMethod.getInstance());
//                Password!!.transformationMethod = HiddenPassTransformationMethod()
//                Password!!.setSelection(Password!!.text.length)
//            }
//        }
//    }

    override fun onResume() {
        //videoView.resume();
        super.onResume()
    }

    override fun onRestart() {
        //videoView.start();
        super.onRestart()
    }

    override fun onPause() {
        //videoView.suspend();
        super.onPause()
    }

    override fun onDestroy() {
        //videoView.stopPlayback();
        super.onDestroy()
    } /*private void video_load() {
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
    }*/

    companion object {
        private const val RC_SIGN_IN = 100

        //VideoView videoView;
        private val TAG = SignUp::class.java.simpleName
    }
}