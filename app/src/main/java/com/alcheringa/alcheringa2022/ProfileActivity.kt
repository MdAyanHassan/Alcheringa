package com.alcheringa.alcheringa2022

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.darkBar
import com.alcheringa.alcheringa2022.ui.theme.darkGrey
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.heartRed
import com.alcheringa.alcheringa2022.ui.theme.lightBar
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
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Locale

class ProfileActivity: AppCompatActivity() {

    private val TAG = ProfilePage::class.java.simpleName


    var nameshared: String = ""
    var user_dp = mutableStateOf("")
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

        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE)

        fill_user_details()

        setContent {
            Alcheringa2022Theme {

                val context = LocalContext.current

                interests =
                    sharedPreferences.getString("interests", "")!!.replace("[","").replace("]", "").replace(" ", "").split(',').toMutableList()

                interests.remove("null")
//                for (i in 0..interests.size-1){
//                    while (interests[i][0] == ' ') {
//                        interests[i] = interests[i].replace(" ", "")
//                    }
//                }


//                user_dp.value = sharedPreferences.getString("photourl", "")!!
//                Log.d("Profile", user_dp.toString())


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
                                tint = lighterPurple,
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
                            .padding(horizontal = 30.dp)
                            .paint(
                                painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                                contentScale = ContentScale.Crop
                            )
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))

                        Box(
                            modifier = Modifier
                                .width(160.dp)
                                .height(160.dp)
                        ) {
                            Image(
                                painter = rememberImagePainter(data = if (user_dp.value != "")user_dp.value else R.drawable.usernew),
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
                            val keyboardController = LocalSoftwareKeyboardController.current
                            var showDialog by remember { mutableStateOf(false) }
                            val context= LocalContext.current



//                            BasicTextField(
//                                value = name,
//                                onValueChange = { name = it },
//                                textStyle = LocalTextStyle.current.copy(
//                                    color = colors.onBackground,
//                                    fontSize = 22.sp,
//                                    fontWeight = FontWeight.Medium,
//                                    fontFamily = futura
//                                    ),
//                                keyboardOptions = KeyboardOptions.Default.copy(
//                                    imeAction = ImeAction.Done
//                                ),
//                                modifier = Modifier
//                                    .wrapContentSize(),
//                                enabled = isEditing,
//                                keyboardActions = KeyboardActions(
//                                    onDone = {
//                                        isEditing = false
//                                        keyboardController?.hide()
//                                    }
//                                )
//                            )

                            Text(
                                text = name,
                                fontFamily = futura,
                                fontSize = 22.sp,
                                color = colors.onBackground
                            )

                            if (showDialog){
                                Dialog(onDismissRequest = { showDialog = false }) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.8f)
                                            .background(
                                                colors.background,
                                                RoundedCornerShape(4.dp)
                                            )
                                            .border(
                                                1.dp,
                                                colors.onBackground,
                                                RoundedCornerShape(4.dp)
                                            )
                                            .padding(20.dp)
                                            .align(Alignment.CenterVertically)
                                    ) {
                                        Column {
                                            Text(
                                                text = "Name:",
                                                color = colors.onBackground,
                                                fontSize = 14.sp,
                                                fontFamily = futura
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                            BasicTextField(
                                                value = name,
                                                onValueChange = { name = it },
                                                modifier = Modifier
                                                    .border(1.dp, darkGrey)
                                                    .fillMaxWidth()
                                                    .padding(5.dp),
                                                textStyle = TextStyle(
                                                    color = colors.onBackground
                                                )
                                            )
                                            Spacer(modifier = Modifier.height(15.dp))

                                            Button(onClick = {
                                                updateName(name)
                                                showDialog = false
                                            }) {
                                                Text(text = "Save")
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit_dp),
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable { showDialog = true },
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

                        Spacer(modifier = Modifier.height(40.dp))

//                        Switch(
//                            checked = isSystemInDarkTheme(),
//                            onCheckedChange = {
//                                if(context.getResources().getConfiguration().uiMode==33){
//                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                                }
//                                else{
//                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                                }
//                            }
//                        )

                        Switch2(context = context)
                        Spacer(modifier = Modifier.height(40.dp))

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

                        Spacer(modifier = Modifier.height(40.dp))

                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Switch2(
        scale: Float = 1.5f,
        width: Dp = 140.dp,
        height: Dp = 40.dp,
        context: Context
    ) {
        var isSystemindark = isSystemInDarkTheme()
        var switchON = remember {
            mutableStateOf(isSystemindark) // Initially the switch is ON
        }

        val swipeableState = rememberSwipeableState(0)
        val sizePx = with(LocalDensity.current) { (width - height).toPx() }
        val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states



        AnimatedContent(targetState = switchON, label = "Theme switch") { switch ->
            Box(
                modifier = Modifier
                    .background(colors.onBackground, RoundedCornerShape(20.dp))
                    .width(width)
                    .height(height)
                    .border(1.dp, colors.onBackground, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        switch.value = !switch.value
                        changetheme(context)
                    }
            ) {
                Box(
                    modifier = Modifier
                        .align(
                            if (switch.value) {
                                Alignment.CenterStart
                            } else {
                                Alignment.CenterEnd
                            }
                        )
                        .background(colors.background, RoundedCornerShape(20.dp))
                        .width(75.dp)
                        .height(height)
                        .border(1.dp, colors.onBackground, RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            switch.value = !switch.value
                            changetheme(context)
                        }
                )

                Text(
                    text = "Dark",
                    fontSize = 18.sp,
                    fontFamily = futura,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    color = lightBar,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.CenterStart)
                )
                Text(
                    text = "Light",
                    fontSize = 18.sp,
                    fontFamily = futura,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    color = darkBar,
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }

//        Canvas(
//            modifier = Modifier
//                .size(width = width, height = height)
//                .scale(scale = scale)
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onTap = {
//                            // This is called when the user taps on the canvas
//                            if(context.getResources().getConfiguration().uiMode == 33){
//                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                            }
//                            else{
//                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                            }
//                            switchON.value != switchON.value
//                        }
//                    )
//                }
//        ) {
//            // Track
//            drawRoundRect(
//                color = bgcolor,
//                cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
//                style = Stroke(width = strokeWidth.toPx()),
//                size = size
//            )
//
//            // Thumb
//            drawCircle(
//                color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
//                radius = thumbRadius.toPx(),
//                center = Offset(
//                    x = animatePosition.value,
//                    y = size.height / 2
//                )
//            )
//
//            drawText(
//                textMeasurer = textMeasurer,
//                text = "Dark",
//                style = TextStyle(
//                    fontSize = 15.sp,
//                    fontFamily = futura,
//                    fontWeight = FontWeight.Medium,
//                    color = textcolor
//                ),
//                topLeft = Offset(
//                    x = -center.x/2,
//                    y = center.y/2
//                )
//            )
//        }
//
//        Spacer(modifier = Modifier.height(18.dp))
//
//        Text(text = if (switchON.value) "ON" else "OFF")
    }

    fun changetheme(context: Context){
        if(context.getResources().getConfiguration().uiMode==33){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    fun updateName(name: String){
        val editor = sharedPreferences.edit()
        editor.putString("name" , name)
        editor.apply()
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

    @OptIn(ExperimentalFoundationApi::class)
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
                .padding(horizontal = 2.dp)
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
                    val editor = sharedPreferences.edit()
                    editor.putString("interests", interests.toString())
                    editor.apply()
                }
                .padding(vertical = 5.dp)


        ){
            Text(
                text = "   $name   ",
                fontSize = 18.sp,
                fontFamily = futura,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium,
                color = colors.onBackground,
                modifier = Modifier
                    .basicMarquee(
                        iterations = 100
                    )
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
                    e?.printStackTrace()
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
                user_dp.value = uri.toString()
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
        val shared_interest = sharedPreferences.getString("interests", "")!!.replace("[", "").replace("]", "").split(',')


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
            user_dp.value = shared_photoUrl

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
                            user_dp.value = db_photourl
                            editor.putString("photourl", db_photourl)
                        }
                        editor.apply()
                        Log.d("Profile", "Got profile url ${db_photourl}")
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
                    var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos)

                    if(baos.toByteArray().size  > 250 ){
                        bitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, false)

                    }

                    val newURI = MediaStore.Images.Media.insertImage(this@ProfileActivity.contentResolver, bitmap, nameshared, null)
                    filePath = Uri.parse(newURI)
                    // loading profile image from local cache
                    loadProfile(filePath.toString())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun loadProfile(url: String) {
        Log.d("ProfilePage.TAG", "Image cache path: $url")
//        user_dp.value = url
        uploadImage()
    }

}