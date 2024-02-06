package com.alcheringa.alcheringa2022

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.heartRed
import com.alcheringa.alcheringa2022.ui.theme.lighterPurple
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.IOException
import java.util.Locale

class ProfileActivity: AppCompatActivity() {

    private val TAG = ProfilePage::class.java.simpleName


    var nameshared: String = ""
    var user_dp: String = ""
    var shared_photoUrl: String = ""
    var firebaseAuth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance()
    var storageReference = storage.getReference()
    lateinit var sharedPreferences: SharedPreferences
    var interests = mutableListOf<String>()
    val REQUEST_IMAGE = 100
    private var filePath: Uri? = null


    @OptIn(ExperimentalCoilApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Alcheringa2022Theme {

                sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE)

                fill_user_details()

                interests =
                    sharedPreferences.getString("interests", "")!!.replace("[","").replace("]", "").split(',').toMutableList()

                interests.remove("null")
                for (i in 0..interests.size-1){
                    interests[i] = interests[i].replace("  ", "")
                }

                user_dp = sharedPreferences.getString("photourl", "")!!

                var profile_url by remember {
                    mutableStateOf(user_dp)
                }

                var name by remember {
                    mutableStateOf(nameshared)
                }



                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.background)
                ) {
                    Row(
                        modifier = Modifier
                            .height(65.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier) {
                            Icon(
                                painter = painterResource(id = R.drawable.cart_arrow),
                                contentDescription = null,
                                tint = darkTealGreen,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .clickable {
                                        finish()
                                    }
                                    .padding(10.dp)
                            )
                        }
                        Text(
                            text = "Profile",
                            fontFamily = futura,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            color = containerPurple
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(darkTealGreen)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))

                        Box(
                            modifier = Modifier
                                .width(160.dp)
                                .height(160.dp)
                        ) {
                            Image(
                                painter = rememberImagePainter(data = if (profile_url != "")profile_url else R.drawable.usernew),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(160.dp)
                                    .height(160.dp)
                                    .border(2.dp, colors.onBackground, RoundedCornerShape(5.dp)),
                                contentScale = ContentScale.Crop,

                                alignment = Alignment.Center,
                            )
                            
                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                                    .align(Alignment.TopEnd)
                                    .padding(top = 12.dp, end = 12.dp)
                                    .border(1.dp, colors.onBackground, RoundedCornerShape(4.dp))
                                    .background(colors.background, RoundedCornerShape(4.dp))
                                    .padding(5.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_edit_dp),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clickable { onProfileImageClick() },
                                    tint = colors.onBackground
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row{
                            var isEditing by remember { mutableStateOf(false) }
                            val keyboardController = LocalSoftwareKeyboardController.current

                            BasicTextField(
                                value = name,
                                onValueChange = { name = it },
                                textStyle = LocalTextStyle.current.copy(
                                    color = colors.onBackground,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = futura
                                    ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                modifier = Modifier
                                    .wrapContentSize(),
                                enabled = isEditing,
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        isEditing = false
                                        keyboardController?.hide()
                                    }
                                )
                                )

                            Spacer(modifier = Modifier.width(10.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit_dp),
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable { isEditing = true },
                                tint = colors.onBackground
                            )

                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Interests",
                                fontSize = 18.sp,
                                fontFamily = futura,
                                color = colors.onBackground
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        ){
                            InterestButton(name = "Dance", selected = interests.contains("Dance"))
                            InterestButton(name = "Music", selected = interests.contains("Music"))
                            InterestButton(name = "Rock", selected = interests.contains("Rock"))
                            InterestButton(name = "EDM", selected = interests.contains("EDM"))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        ){
                            InterestButton(name = "Fashion", selected = interests.contains("Fashion"))
                            InterestButton(name = "Classical", selected = interests.contains("Classical"))
                            InterestButton(name = "Aesthetics", selected = interests.contains("Aesthetics"))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        ){
                            InterestButton(name = "Art", selected = interests.contains("Art"))
                            InterestButton(name = "Metal", selected = interests.contains("Metal"))
                            InterestButton(name = "Soul", selected = interests.contains("Soul"))
                            InterestButton(name = "History", selected = interests.contains("History"))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        ){
                            InterestButton(name = "Ethnic", selected = interests.contains("Ethnic"))
                            InterestButton(name = "Indie", selected = interests.contains("Indie"))
                            InterestButton(name = "Bands", selected = interests.contains("Bands"))
                            InterestButton(name = "Colors", selected = interests.contains("Colors"))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        ){
                            InterestButton(name = "Traditional", selected = interests.contains("Traditional"))
                            InterestButton(name = "Soothing", selected = interests.contains("Soothing"))
                            InterestButton(name = "Serene", selected = interests.contains("Serene"))
                        }

                        Spacer(modifier = Modifier.height(80.dp))

                        Row (
                            modifier = Modifier
                                .clickable {
                                    signOut()
                                }
                        ){
                            Text(
                                text = "Sign Out",
                                fontSize = 22.sp,
                                fontFamily = futura,
                                fontWeight = FontWeight.Medium,
                                color = heartRed
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.signoutlogo),
                                contentDescription = null,
                                tint = heartRed
                            )
                        }
                    }
                }
            }
        }
    }

    fun signOut(){
        val editor = sharedPreferences.edit()
        editor.remove("name")
        editor.remove("email")
        editor.remove("photourl")
        editor.remove("interests")
        editor.apply()
        val intent = Intent(applicationContext, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        firebaseAuth.signOut()
        startActivity(intent)
        finish()
    }

    @Composable
    fun InterestButton(name: String, selected: Boolean){
        var _selected by remember {
            mutableStateOf(selected)
        }

        var ran = (1..2).random()
        val bb = if (_selected){
            if (ran==1) darkTealGreen else lighterPurple
        }
        else Color.Transparent

        Box(
            modifier = Modifier
                .background(
                    bb,
                    RoundedCornerShape(8.dp)
                )
                .border(1.dp, colors.onBackground, RoundedCornerShape(8.dp))
                .clickable {
                    if (_selected) {
                        _selected = false
                        interests.remove(name)
                    } else {
                        _selected = true
                        interests.add(name)
                    }
                }
                .padding(horizontal = 15.dp, vertical = 5.dp)


        ){
            Text(
                text = name,
                fontSize = 18.sp,
                fontFamily = futura,
                fontWeight = FontWeight.Medium,
                color = colors.onBackground
            )
        }
    }

    fun onProfileImageClick() {
        Dexter.withActivity(this)
            .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    launchGalleryIntent()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun launchGalleryIntent() {
        val intent = Intent(this@ProfileActivity, ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity.REQUEST_GALLERY_IMAGE
        )

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        startActivityForResult(intent, ProfilePage.REQUEST_IMAGE)
    }

    private fun uploadImage() {
        val user = firebaseAuth.currentUser!!
        val email = user.email!!
        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref = storageReference.child("Users/$email")
            ref.putFile(filePath!!)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                    Log.d("Image", "Success!!")
                    progressDialog.dismiss()
                    Toast.makeText(this@ProfileActivity, "Profile Image Updated", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { e: Exception? ->
                    progressDialog.dismiss()
                    Toast.makeText(this@ProfileActivity, "Profile Update Failed", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("Image", "Failed!!")
                }
                .addOnProgressListener { taskSnapshot: UploadTask.TaskSnapshot ->
                    val progress =
                        100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                            .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
            ref.downloadUrl.addOnSuccessListener { uri: Uri ->
                firestore.collection("USERS").document(email)
                    .update("PhotoURL", uri.toString())
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("photourl", uri.toString())
                editor.apply()
            }
        }
    }

    private fun startCrop(imageURI: Uri) {
        CropImage.activity(imageURI).setGuidelines(CropImageView.Guidelines.ON)
            .setMultiTouchEnabled(true).start(this)
    }

    private fun fill_user_details() {
        val shared_name = sharedPreferences.getString("name", "")
        shared_photoUrl = sharedPreferences.getString("photourl", "").toString()
        var shared_interest = sharedPreferences.getString("interests", "")!!.replace("[", "").replace("]", "").split(',')
        if (shared_name != "") {
            nameshared = shared_name!!.substring(0, 1).uppercase(Locale.getDefault()) + shared_name.substring(1)
        } else {
            nameshared = "No name found"
        }

        if (!shared_interest.contains("")) {
            interests = shared_interest.toMutableList()
        }else{
            val user = firebaseAuth.currentUser!!
            val email = user.email!!
            firestore.collection("USERS").document(email).collection("interests").document("interests").get()
                .addOnSuccessListener {
                    val interestList = it.get("interests").toString().replace("[", "").replace("]", "").split(',')
                    val editor = sharedPreferences.edit()
                    editor.putString("interests", interestList.toString())
                    editor.apply()

                }
                .addOnFailureListener{e->
                    Toast.makeText(this, "Failed to get interests", Toast.LENGTH_LONG).show()
                }


        }

        if (shared_photoUrl != "") {
            //Toast.makeText(this, ""+shared_photoUrl, Toast.LENGTH_SHORT).show();
            user_dp = shared_photoUrl

        } else {
            //Toast.makeText(this, ""+shared_photoUrl, Toast.LENGTH_SHORT).show();
            val user = firebaseAuth.currentUser!!
            val email = user.email!!
            firestore.collection("USERS").document(email).get()
                .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                    if (task.isSuccessful) {
                        val editor = sharedPreferences.edit()
                        val db_photourl = task.result.getString("PhotoURL")
                        if (db_photourl != null) {
                            user_dp = db_photourl
                            editor.putString("photourl", db_photourl)
                        }
                        editor.apply()
                    } else {
                        Log.d("TAG", "Error getting profile photo: ", task.exception)
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ProfilePage.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                val uri = data!!.getParcelableExtra<Uri>("path")
                try {
                    // You can update this bitmap to your server
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                    filePath = uri
                    // loading profile image from local cache
                    loadProfile(uri.toString())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun loadProfile(url: String) {
        Log.d("ProfilePage.TAG", "Image cache path: $url")
        user_dp = url
        uploadImage()
    }

}