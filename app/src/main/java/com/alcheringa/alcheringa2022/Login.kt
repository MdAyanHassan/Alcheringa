package com.alcheringa.alcheringa2022

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
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

class Login : ComponentActivity() {
    var SignupTextView: TextView? = null
    var backButton: ImageView? = null
    var google_login_btn: LinearLayout? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    var firebaseAuth: FirebaseAuth? = null
    var firebaseFirestore: FirebaseFirestore? = null
    private var Email: String? = null
    private var Password: String? = null

    //TextInputLayout Password;
    var loginButton: Button? = null
    var signInButtonO: LinearLayout? = null
    var loaderView = mutableStateOf(false)
    var sharedPreferences: SharedPreferences? = null

    //VideoView videoView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        setContent {
            LoginScreen2025(
                forgetPassword = {startActivity(
                    Intent(
                        applicationContext, ResetPassword::class.java
                    )
                )},
//                signUpHere = {
//                    startActivity(
//                        Intent(
//                            applicationContext ,  SignUp::class.java
//                        )
//                    )
//                },
                openPrivacyPolicy = {
                    startActivity(
                        Intent(
                            applicationContext ,  PrivacyPolicy::class.java
                        )
                    )
                },
                microsoftLogin = {MicrosoftLogin()},
                googleLogin = {google_login_callback()},

                appleLogin = { /**TODO**/ },
                onEmailChange = {email ->
                                Email = email

                },
                onPasswordChange = {password ->
                                   Password = password

                },
                login = {email,password ->
                    if (email != null) {
                        if (password != null) {
                            custom_Login_start(email , password)
                        }
                    }
                },

                onBack = {
                    /**TODO**/
                    goBack()
                }

            )
            if(loaderView.value){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colors.background.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center,

                ) {
                    LoadingAnimation3()
                }
            }
        }
//        loaderView = findViewById(R.id.dots_progress)
//        //loaderView.setVisibility(View.GONE)
//        SignupTextView = findViewById(R.id.signup_here)
//        backButton = findViewById(R.id.back_button)
//        google_login_btn = findViewById(R.id.google_login_btn)
//        Email = findViewById(R.id.email)
//        Password = findViewById(R.id.password)
//        signInButtonO = findViewById(R.id.sign_in_outlook)
//        loginButton = findViewById(R.id.loginbtn)
//        val forgotPassword = findViewById<TextView>(R.id.forgot_password)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE)
//        Password.setTransformationMethod(HiddenPassTransformationMethod())
//        Password.setSelection(Password.getText().length)
//        loginButton.setOnClickListener(View.OnClickListener { v: View? -> CustomLogin() })
//        google_login_btn.setOnClickListener(View.OnClickListener { v: View? ->
//            loaderView.setVisibility(View.VISIBLE)
//            google_login_callback()
//        })
//        signInButtonO.setOnClickListener(View.OnClickListener { v: View? ->
//            loaderView.setVisibility(View.VISIBLE)
//            MicrosoftLogin()
//        })
//        backButton.setOnClickListener(View.OnClickListener { v: View? -> goBack() })
//        SignupTextView.setOnClickListener(View.OnClickListener { v: View? ->
//            val i = Intent(this, SignUp::class.java)
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//            startActivity(i)
//            finish()
//        })
//        forgotPassword.setOnClickListener { v: View? ->
//
//        }
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
        //loadVideo();
    }

    private fun loadVideo() {
        //videoView=findViewById(R.id.videoview);
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.greeting_video)
        //videoView.setVideoURI(uri);
        //videoView.start();

        //videoView.setOnPreparedListener(mp -> mp.setLooping(true));
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
                    TAG, "Successfully authenticated with Outlook" + authResult.additionalUserInfo!!
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
                    //Toast.makeText(Login.this, "Name: " + name + " \n" + "Email: " + email, Toast.LENGTH_LONG).show();
                    val finalName = name
                    firebaseFirestore!!.collection("USERS").document(email).get()
                        .addOnCompleteListener { task2: Task<DocumentSnapshot> ->
                            if (task2.isSuccessful && task2.result.exists()) {
                                toast("Welcome back $finalName")
                                saveDetails(finalName, email, task2.result.getString("PhotoURL"))
                                setInterests(email)
                                loaderView.value = false
                                if (task2.result.getString("mode") == "white" || task2.result.getString(
                                        "mode"
                                    ) == "black"
                                ) startMainActivity() else startInterestActivity()
                            } else {
                                //final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                //assert currentUser != null;
                                //currentUser.delete();
                                //firebaseAuth.signOut();
                                RegisterUserInDatabase()
                                saveDetails(finalName, email, "")
                                loaderView.value = false
                                toast("Welcome $finalName")
                                startInterestActivity()
                            }
                        }
                } catch (e: Exception) {
                    firebaseAuth!!.signOut()
                    loaderView.value = false
                    toast("Could not get your email from Outlook")
                }
            }
            .addOnFailureListener { e: Exception ->
                Log.d(TAG, "onFailure" + e)
                Toast.makeText(this, "Login with Microsoft failed", Toast.LENGTH_LONG).show()
//                loaderView!!.visibility = View.GONE
            }
    }

    private fun setInterests(email: String?) {
        firebaseFirestore!!.collection("USERS").document(email!!).collection("interests")
            .document("interests").get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
            if (task.isSuccessful) {
                try {
                    val interests = task.result["interests"] as ArrayList<String>?
//                    val set: Set<String> = HashSet(interests)
                    val editor = sharedPreferences!!.edit()
                    editor.putString("interests", interests.toString())
                    editor.apply()
                } catch (ignored: Exception) {
                }
            }
        }
    }

    private fun startMainActivity() {
        //Intent intent = new Intent(this, MainActivity.class);
        val intent = Intent(this, PickASide::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun saveDetails(name: String?, email: String?, photoURL: String?) {
        val localSharedPreferences = getSharedPreferences("USER", MODE_PRIVATE)
        val editor = localSharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("photourl", photoURL)
        editor.apply()
    }

    private fun CustomLogin() {
        Log.d(TAG , "Inside custom login")
        val email = Email.toString().trim { it <= ' ' }
        val password = Password.toString().trim { it <= ' ' }
        val emailRegex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        val isEmailValid = Pattern.compile(emailRegex)
            .matcher(email)
            .matches()
        if (isEmailValid && password.length > 7) {

            custom_Login_start(email, password)
        } //else if (email.isEmpty()) {
//            Email!!.error = "Please fill your email"
//            Email!!.requestFocus()
//        } else if (!isEmailValid) {
//            Email!!.error = "Please enter a valid email"
//            Email!!.requestFocus()
//        } else if (Password!!.length() == 0) {
//            Password!!.error = "Please fill your password"
//            Password!!.requestFocus()
//        } else {
//            Password!!.error = "Password is too short."
//            Password!!.requestFocus()
//        }
    }

    private fun custom_Login_start(email: String, password: String) {
        loaderView.value = true
        firebaseAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth?.currentUser?.let {firebaseUser ->
                        if (firebaseUser.isEmailVerified) {
                            toast("Login Successful")
                            firebaseFirestore!!.collection("USERS").document(email)
                                .collection("interests").document("interests").get()
                                ?.addOnCompleteListener { task1->
                                    loaderView.value = false

                                }

                            firebaseFirestore?.collection("USERS")?.document(email)?.get()
                                ?.addOnCompleteListener { task1 ->
                                    if (task1.isSuccessful) {
                                        val nameString = task1.result.getString("Name")
                                        Log.d(TAG, "The entire data obtained is: " + task1.result.data)
                                        saveDetails(
                                            nameString,
                                            email,
                                            task1.result.getString("PhotoURL")
                                        )
                                        loaderView.value = false
                                        if (task1.result.getString("mode") == "white" || task1.result.getString(
                                                "mode"
                                            ) == "black"
                                        ) startMainActivity() else startInterestActivity()
                                    } else {
                                        loaderView.value = false
                                        Toast.makeText(
                                            this,
                                            "Could not get username",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }else {
                            loaderView.value = false
                            firebaseAuth?.signOut()
                            toast("Please verify your email first")
                        }

                    }?: run {
                        loaderView.value = false
                        toast("User not found")
                    }

                } else {
                    loaderView.value = false
                    toast("Login Failed | Wrong Email or Password")
                }
            }
    }

    private fun toast(text: String) {
        Toast.makeText(applicationContext, "" + text, Toast.LENGTH_LONG).show()
    }

    private fun google_login_callback() {
        loaderView.value = true
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        GoogleSignIn()
    }

    private fun GoogleSignIn() {
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
                Log.d(TAG, "firebaseAuthWithGoogle: " + account.id)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(applicationContext, "" + e.message, Toast.LENGTH_SHORT).show()
                loaderView.value = false
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
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth!!.currentUser
                    assert(user?.email != null)
                    firebaseFirestore!!.collection("USERS").document(user!!.email!!).get()
                        .addOnCompleteListener { task2: Task<DocumentSnapshot> ->
                            if (task2.isSuccessful && task2.result.exists()) {
                                saveDetails(
                                    user.displayName,
                                    user.email,
                                    task2.result.getString("PhotoURL")
                                )
                                val sharedPreferences = getSharedPreferences("USERS", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("photourl", task2.result.getString("PhotoURL"))
                                editor.apply()
                                setInterests(user.email)
                                loaderView.value = false
                                Log.d(TAG, "Login with Google Successful")
                                toast("Welcome back " + user.displayName)
                                if (task2.result.getString("mode") == "white" || task2.result.getString(
                                        "mode"
                                    ) == "black"
                                ) startMainActivity() else startInterestActivity()
                            } else {
                                //final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                //assert currentUser != null;
                                //currentUser.delete();
                                //firebaseAuth.signOut();
                                RegisterUserInDatabase()
                                saveDetails(user.displayName, user.email, "")
                                loaderView.value = false
                                toast("Welcome " + user.displayName)
                                startInterestActivity()
                            }
                        }
                } else {
                    loaderView.value = false
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(applicationContext, "" + task.exception, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun startInterestActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
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

//    private class HiddenPassTransformationMethod : TransformationMethod {
//        private val DOT = '\u2022'
////        override fun getTransformation(charSequence: CharSequence, view: View): CharSequence {
////            return PassCharSequence(charSequence)
////        }
//
//        override fun onFocusChanged(
//            view: View, charSequence: CharSequence, b: Boolean, i: Int,
//            rect: Rect
//        ) {
//            //nothing to do here
//        }
//
////        private inner class PassCharSequence(private val charSequence: CharSequence) :
////            CharSequence {
//////            override fun charAt(index: Int): Char {
//////                return DOT
//////            }
////
//////            override fun length(): Int {
//////                return charSequence.length
//////            }
////
////            override fun subSequence(start: Int, end: Int): CharSequence {
////                return PassCharSequence(charSequence.subSequence(start, end))
////            }
////        }
//    }

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
//                //Password!!.transformationMethod = HiddenPassTransformationMethod()
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
    }

    companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "TAG"
    }
}