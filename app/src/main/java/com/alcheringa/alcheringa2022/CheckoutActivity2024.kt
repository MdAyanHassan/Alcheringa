package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.alcheringa.alcheringa2022.Database.DBHandler
import com.alcheringa.alcheringa2022.Model.cartModel
import com.alcheringa.alcheringa2022.services.Retrofit_Class
import com.alcheringa.alcheringa2022.ui.theme.darkBar
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder
import com.razorpay.RazorpayException
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.Objects
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun CheckoutLayout2025(
    dbHandler: DBHandler?,
    sharedPreferences: SharedPreferences,
    firebaseAuth: FirebaseAuth,
    onDecrementClick: (Int, ArrayList<cartModel>) -> Unit,
    onIncrementClick: (Int, ArrayList<cartModel>) -> Unit,
    setUserName: (String) -> Unit,
    setPhone: (String) -> Unit,
    setPincode: (String) -> Unit,
    setHouse: (String) -> Unit,
    setRoad: (String) -> Unit,
    setCity: (String) -> Unit,
    setState: (String) -> Unit,
    setEmail: (String) -> Unit,
    setTotalAmount: (Long) -> Unit,
    calculateTotalAmount: (Long, Long, Int) -> Long,
    getShippingCharges: () -> Long,
    setArrayListItems: (ArrayList<cartModel>) -> Unit,
    calculateAmount: (ArrayList<cartModel>, (Long, Int) -> Unit) -> Unit,
    onFinish: () -> Unit,
    startPayment: (Int) -> Unit,
    getTotalAmount: () -> Long,
    getOrderStatus: () -> MutableState<Int>,
    spName: String?
) {

    var cartModelItems by remember {
        mutableStateOf(dbHandler!!.readCourses())
    }

    var key by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = key) {
        cartModelItems = dbHandler!!.readCourses()
        setArrayListItems(cartModelItems)
    }


    var checkedState by remember {
        mutableStateOf(0)
    }

    var name by remember {
        mutableStateOf(spName)
    }

    var phone by remember {
        mutableStateOf(
            sharedPreferences.getString(
                "phone",
                ""
            )
        )
    }

    var pincode by remember {
        mutableStateOf(
            sharedPreferences.getString(
                "pincode",
                ""
            )
        )
    }

    var house by remember {
        mutableStateOf(
            sharedPreferences.getString(
                "house",
                ""
            )
        )
    }

    var road by remember {
        mutableStateOf(
            sharedPreferences.getString(
                "road",
                ""
            )
        )
    }

    var city by remember {
        mutableStateOf(
            sharedPreferences.getString(
                "city",
                ""
            )
        )
    }

    var state by remember {
        mutableStateOf(
            sharedPreferences.getString(
                "state",
                ""
            )
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var amount: Long by remember {
        mutableStateOf(0)
    }

    var totalItems by remember {
        mutableStateOf(0)
    }

    setTotalAmount(calculateTotalAmount(amount, getShippingCharges(), totalItems))

    LaunchedEffect(cartModelItems, Unit) {
        Log.d("MainActivity", "launched")

        calculateAmount(cartModelItems) { it1, it2 ->
            amount = it1
            totalItems = it2
        }
    }

    setUserName(name!!)
    setPhone(phone!!)
    setPincode(pincode!!)
    setHouse(house!!)
    setRoad(road!!)
    setCity(city!!)
    setState(state!!)
    setEmail(firebaseAuth.currentUser?.email.toString())

    BackHandler {
        focusManager.clearFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.cart_bg),
                contentScale = ContentScale.Crop
            )
            .pointerInput(Unit) {
                detectTapGestures {
                    keyboardController?.hide()
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .paint(
                    painterResource(id = R.drawable.cart_header_bg),
                    contentScale = ContentScale.Crop
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    if (checkedState == 0 || checkedState == 2) {
                        onFinish()
                    } else {
                        checkedState -= 1
                    }
                },
                modifier = Modifier
                    .padding(start = 10.dp, top = 4.dp, bottom = 4.dp)
                    .size(48.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.back_button_art),
                    contentDescription = "Back Button",
                    tint = Color.Unspecified
                )

            }

            Text(
                text = "Checkout",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 22.sp,
                    //fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF77A8),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.alcher_pixel, FontWeight.Normal),
                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                    )
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(12.dp)
            )
        }

        if (checkedState < 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 12.dp, end = 12.dp)
                    .paint(
                        painterResource(id = R.drawable.checkout_nav_tab_bg)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Details",
                    style = TextStyle(
                        fontSize = 20.sp,
                        //fontWeight = FontWeight.Bold,
                        color = if (checkedState == 0) Color(0xFFFFF1E8) else Color(0xFF83769C),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.alcher_pixel, FontWeight.Normal),
                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                        )
                    ),
                    modifier = Modifier
                        .padding(end = 50.dp)
                )
                Text(
                    text = "Review",
                    style = TextStyle(
                        fontSize = 20.sp,
                        //fontWeight = FontWeight.Bold,
                        color = if (checkedState == 1) Color(0xFFFFF1E8) else Color(0xFF83769C),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.alcher_pixel, FontWeight.Normal),
                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                        )
                    ),
                    modifier = Modifier
                        .padding(start = 50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val offsetRight1 by animateDpAsState(
            targetValue = if (checkedState == 1) 0.dp else 400.dp,
            animationSpec = spring()
        )
        val offsetRight2 by animateDpAsState(
            targetValue = if (checkedState == 2) 0.dp else 400.dp,
            animationSpec = spring()
        )

        when (checkedState) {
            0 -> {
                val alpha by animateFloatAsState(
                    targetValue = if (checkedState == 0) 1f else 0f,
                    animationSpec = spring(), label = "Alpha animation",
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp)
                    ) {
                        Text(
                            text = "Personal Information",
                            style = TextStyle(
                                fontSize = 20.sp,
                                //fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF1E8),
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                )
                            ),
                            modifier = Modifier
                                .padding(start = 6.dp, bottom = 4.dp)

                        )
                        TextField(
                            value = name!!,
                            onValueChange = {
                                name = it
                                setUserName(it)
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                //fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF1E8),
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                )
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                            ),
                            maxLines = 1,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, end = 4.dp)
                                .paint(
                                    painterResource(id = R.drawable.sprite_tf_check_bg),
                                    // contentScale = ContentScale.FillBounds
                                )
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            placeholder = {
                                Text(
                                    text = "Name",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(start = 4.dp)

                                )
                            }
                        )

                        TextField(
                            value = phone!!,
                            onValueChange = {
                                if (it.length <= 10) {
                                    phone = it
                                    setPhone(it)
                                }
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                //fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF1E8),
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                )
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                            ),
                            maxLines = 1,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, end = 4.dp, top = 8.dp)
                                .paint(
                                    painterResource(id = R.drawable.sprite_tf_check_bg),
                                    // contentScale = ContentScale.FillBounds
                                )
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            placeholder = {
                                Text(
                                    text = "Phone Number",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(start = 4.dp)

                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(68.dp))

                        Text(
                            text = "Address Details",
                            style = TextStyle(
                                fontSize = 20.sp,
                                //fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF1E8),
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                )
                            ),
                            modifier = Modifier
                                .padding(start = 6.dp, bottom = 4.dp)

                        )

                        TextField(
                            value = house!!,
                            onValueChange = {
                                house = it
                                setHouse(it)
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                //fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF1E8),
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                )
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                            ),
                            maxLines = 1,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, end = 4.dp)
                                .paint(
                                    painterResource(id = R.drawable.sprite_tf_check_bg),
                                    // contentScale = ContentScale.FillBounds
                                )
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            placeholder = {
                                Text(
                                    text = "Address Line 1",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(start = 4.dp)

                                )
                            }
                        )
                        TextField(
                            value = road!!,
                            onValueChange = {
                                road = it
                                setRoad(it)
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                //fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF1E8),
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                )
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                            ),
                            maxLines = 1,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, end = 4.dp, top = 8.dp)
                                .paint(
                                    painterResource(id = R.drawable.sprite_tf_check_bg),
                                    // contentScale = ContentScale.FillBounds
                                )
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            placeholder = {
                                Text(
                                    text = "Address Line 2",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(start = 4.dp)

                                )
                            }
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),

                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                value = city!!,
                                onValueChange = {
                                    city = it
                                    setCity(it)
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                textStyle = TextStyle(
                                    fontSize = 20.sp,
                                    //fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFF1E8),
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel, FontWeight.Normal),
                                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                    )
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    errorIndicatorColor = Color.Transparent,
                                ),
                                maxLines = 1,
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp, end = 4.dp)
                                    .paint(
                                        painterResource(id = R.drawable.sprite_tf_check_half_bg),
                                        //contentScale = ContentScale.FillBounds
                                    )
                                    .onFocusEvent { focusState ->
                                        if (focusState.isFocused) {
                                            coroutineScope.launch {
                                                bringIntoViewRequester.bringIntoView()
                                            }
                                        }
                                    },
                                placeholder = {
                                    Text(
                                        text = "City",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFF83769C),
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 4.dp)

                                    )
                                }
                            )
                            TextField(
                                value = state!!,
                                onValueChange = {
                                    state = it
                                    setState(it)
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                textStyle = TextStyle(
                                    fontSize = 20.sp,
                                    //fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFF1E8),
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel, FontWeight.Normal),
                                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                    )
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    errorIndicatorColor = Color.Transparent,
                                ),
                                maxLines = 1,
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp, end = 4.dp)
                                    .paint(
                                        painterResource(id = R.drawable.sprite_tf_check_half_bg),
                                        //contentScale = ContentScale.FillBounds
                                    )
                                    .onFocusEvent { focusState ->
                                        if (focusState.isFocused) {
                                            coroutineScope.launch {
                                                bringIntoViewRequester.bringIntoView()
                                            }
                                        }
                                    },
                                placeholder = {
                                    Text(
                                        text = "State",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFF83769C),
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 4.dp)

                                    )
                                }
                            )
                        }

                        TextField(
                            value = pincode!!,
                            onValueChange = {
                                pincode = it
                                setPincode(it)
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                //fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF1E8),
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                )
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                            ),
                            maxLines = 1,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, end = 4.dp, top = 8.dp)
                                .paint(
                                    painterResource(id = R.drawable.sprite_tf_check_bg),
                                    // contentScale = ContentScale.FillBounds
                                )
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            placeholder = {
                                Text(
                                    text = "Pincode",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(start = 4.dp)

                                )
                            }
                        )
                    }

                    Button(
                        onClick = {
                            val patternPhone = Regex("^[1-9][0-9]{9}$")
                            val isPhoneValid =
                                patternPhone.containsMatchIn(phone!!)

                            val patternPinCode = Regex("^[0-9]{6}$")
                            val isPinCodeValid =
                                patternPinCode.containsMatchIn(pincode!!)

                            when {
                                name!!.isEmpty() -> {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please enter your name",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                                !isPhoneValid -> {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please enter a 10 digit phone number",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                                house!!.isEmpty() -> {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please enter your House No and Building Name",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                                road!!.isEmpty() -> {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please enter your Road Name, Area and Colony",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                                city!!.isEmpty() -> {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please enter your City",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                                state!!.isEmpty() -> {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please enter your State",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                                (!isPinCodeValid) -> {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please enter a 6 digit numeric pincode",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                                else -> {
                                    val editor: SharedPreferences.Editor =
                                        sharedPreferences.edit()
                                    editor.putString("name", name)
                                    editor.putString("phone", phone)
                                    editor.putString("house", house)
                                    editor.putString("road", road)
                                    editor.putString("city", city)
                                    editor.putString("state", state)
                                    editor.putString("pincode", pincode)
                                    editor.apply()
                                    //editor.commit()
                                    checkedState = 1;
                                }


                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 24.dp)
                            .paint(
                                painter = painterResource(id = R.drawable.sign_in_button),
                                contentScale = ContentScale.FillBounds
                            ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Next",
                            style = TextStyle(
                                fontSize = 28.sp,
                                //fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF1E8),
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                ),
                                shadow = Shadow(
                                    color = Color(0xFF000000),
                                    offset = Offset(3f, 2f)
                                )
                            ),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        )
                    }
                }
            }

            1 -> {
                val alpha by animateFloatAsState(
                    targetValue = if (checkedState == 1) 1f else 0f,
                    animationSpec = tween(durationMillis = 3000)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(offsetRight1)
                        .alpha(alpha)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {

                                Column {


                                    Text(
                                        text = "Deliver to :",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFF83769C),
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 6.dp, bottom = 4.dp)

                                    )

                                    Text(
                                        text = name!!,
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFF1E8),
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 6.dp, bottom = 4.dp)

                                    )

                                }

                                Column {

                                    Text(
                                        text = "Address",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFF83769C),
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 6.dp, bottom = 4.dp)

                                    )
                                    Text(
                                        text = "$house, $road",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFF1E8),
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 6.dp, bottom = 4.dp)

                                    )
                                    Text(
                                        text = "$city, $state - $pincode",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFF1E8),
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 6.dp, bottom = 4.dp)

                                    )

                                }

                            }

                            Text(
                                text = "Recipient Ph No.",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    //fontWeight = FontWeight.Bold,
                                    color = Color(0xFF83769C),
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel, FontWeight.Normal),
                                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                    )
                                ),
                                modifier = Modifier
                                    .padding(start = 6.dp, bottom = 4.dp)

                            )

                            Text(
                                text = "$phone",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    //fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFF1E8),
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel, FontWeight.Normal),
                                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                    )
                                ),
                                modifier = Modifier
                                    .padding(start = 6.dp, bottom = 4.dp)

                            )
                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = "Your Orders",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    //fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFF77A8),
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel, FontWeight.Normal),
                                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                    )
                                ),
                                modifier = Modifier
                                    .padding(start = 6.dp, bottom = 4.dp)

                            )

                            Spacer(modifier = Modifier.height(8.dp))

                        }
                        itemsIndexed(cartModelItems) { index, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp)
                                    .paint(
                                        painterResource(id = R.drawable.cart_item_new_bg)
                                    ),
                                backgroundColor = Color.Transparent
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val painter = rememberImagePainter(
                                        data = item.image,
                                        builder = {
                                            placeholder(R.drawable.white_circle_bg)
                                            //error(R.drawable.ic_twitter)
                                            crossfade(true)
                                        }
                                    )
//                            Image(
//                                painter = painterResource(id = R.drawable.merch2024),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .fillMaxWidth(0.5f)
//                                    .padding(8.dp)
//                                    .background(Color.Red),
//                                contentScale = ContentScale.Crop
//                            )
                                    Log.d("MainActivity", "image: ${item.image}")

                                    if (painter.state is ImagePainter.State.Error) {
                                        Log.e("ImageLoading", "Error loading image: ${item.image}")
                                    } else {
                                        Log.d(
                                            "ImageLoading",
                                            "Image loaded successfully: ${item.image}"
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(0.45f)
                                            .padding(start = 16.dp, bottom = 8.dp, top = 16.dp)
                                            .paint(
                                                painterResource(id = R.drawable.product_image_bg)
                                            )
                                    ) {
                                        Image(
                                            painter = painter,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(116.dp)
                                                .align(Alignment.Center),
                                            contentScale = ContentScale.Fit
                                        )

                                    }

                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = item.type!!,
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                color = Color(0xFFFFF1E8),
                                                fontFamily = FontFamily(
                                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                ),
                                                shadow = Shadow(
                                                    color = Color(0xFF000000),
                                                    offset = Offset(3f, 2f)
                                                )
                                            ),
                                        )
                                        Text(
                                            text = item.name!!,
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                color = Color(0xFFFF77A8),
                                                fontFamily = FontFamily(
                                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                )
                                            ),
                                            modifier = Modifier
                                                .padding(top = 4.dp)
                                        )
                                        Text(
                                            text = "Rs. ${item.price!!}.00/-",
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                color = Color(0xFFFFF1E8),
                                                fontFamily = FontFamily(
                                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                )
                                            ),
                                            modifier = Modifier
                                                .padding(top = 4.dp)
                                        )
                                        Text(
                                            text = "Size: ${item.size!!}",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                color = Color(0xFFFFF1E8),
                                                fontFamily = FontFamily(
                                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                )
                                            ),
                                            modifier = Modifier
                                                .padding(top = 8.dp)
                                        )

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    onDecrementClick(
                                                        index,
                                                        cartModelItems
                                                    )
                                                    key++
                                                },
                                                modifier = Modifier
                                                    .size(36.dp)
                                            ) {

                                                Icon(
                                                    painter = painterResource(id = R.drawable.sprite_decrement),
                                                    contentDescription = "Decrement",
                                                    tint = Color.Unspecified,
                                                )

                                            }

                                            Box(
                                                modifier = Modifier
                                                    .height(36.dp)
                                                    .width(40.dp)
                                                    .border(width = 3.dp, color = Color(0xFF1D2B53))
                                            ) {
                                                Text(
                                                    text = cartModelItems[index].count!!,
                                                    modifier = Modifier.align(Alignment.Center),
                                                    style = TextStyle(
                                                        fontSize = 28.sp,
                                                        //fontWeight = FontWeight.Bold,
                                                        color = Color(0xFFFF77A8),
                                                        //textAlign = TextAlign.Center,
                                                        fontFamily = FontFamily(
                                                            Font(
                                                                R.font.alcher_pixel,
                                                                FontWeight.Normal
                                                            ),
                                                            Font(
                                                                R.font.alcher_pixel_bold,
                                                                FontWeight.Bold
                                                            ),
                                                        )
                                                    )
                                                )
                                            }


                                            IconButton(
                                                onClick = {
                                                    onIncrementClick(
                                                        index,
                                                        cartModelItems
                                                    )
                                                    key++
                                                },
                                                modifier = Modifier
                                                    .size(36.dp)
                                            ) {

                                                Icon(
                                                    painter = painterResource(id = R.drawable.sprite_increment),
                                                    contentDescription = "Increment",
                                                    tint = Color.Unspecified,
                                                )

                                            }

                                        }

                                    }
                                }

                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(200.dp))
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1D2B53))
                                .border(3.dp, Color(0xFF83769C)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Price:",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)

                                )
                                Text(
                                    text = "Rs. $amount",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)

                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, end = 12.dp, bottom = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Shipping charges:",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)

                                )
                                Text(
                                    text = "Rs. ${getShippingCharges()}",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)

                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Total : $totalItems Item(s)",
                                    style = TextStyle(
                                        fontSize = 28.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)

                                )
                                Text(
                                    text = "Rs. ${getTotalAmount()}",
                                    style = TextStyle(
                                        fontSize = 28.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)

                                )
                            }

                        }
                        Button(
                            enabled = totalItems > 0,
                            onClick = {
                                checkedState = 2
                                startPayment(getTotalAmount().toInt())
                            },
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 4.dp)
                                .paint(
                                    painter = painterResource(id = R.drawable.sign_in_button),
                                    contentScale = ContentScale.FillBounds
                                ),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent
                            )
                        ) {
                            Text(
                                text = "Next",
                                style = TextStyle(
                                    fontSize = 28.sp,
                                    //fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFF1E8),
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel, FontWeight.Normal),
                                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                    ),
                                    shadow = Shadow(
                                        color = Color(0xFF000000),
                                        offset = Offset(3f, 2f)
                                    )
                                ),
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                            )
                        }
                    }
                }
            }

            2 -> {
                val alpha by animateFloatAsState(
                    targetValue = if (checkedState == 2) 1f else 0f,
                    animationSpec = spring(), label = "Alpha animation 2"
                )
                when (getOrderStatus().value) {
                    0 -> {
                        /*TODO(design a loading animation)*/
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = darkBar.copy(alpha = 0.6f)),
                            contentAlignment = Alignment.Center,

                            ) {
                            LoadingAnimation3()
                        }
                    }
                    1 -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(offsetRight2)
                                .alpha(alpha),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .alpha(0.3f)
                                        .padding(bottom = 16.dp)
                                        .background(Color(0xFFD9D9D9))
                                )
                                Text(
                                    text = "Order Placed",
                                    style = TextStyle(
                                        fontSize = 28.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF00E436),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 20.dp)

                                )
                                Text(
                                    text = "Our team will contact you regarding",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    )
                                )
                                Text(
                                    text = "further details of your order",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 24.dp)
                                )
                                Button(
                                    onClick = {
                                        onFinish()
                                    },
                                    modifier = Modifier
                                        .padding(top = 20.dp, bottom = 4.dp)
                                        .paint(
                                            painter = painterResource(id = R.drawable.sign_in_button),
                                            contentScale = ContentScale.FillBounds
                                        ),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Transparent
                                    )
                                ) {
                                    Text(
                                        text = "Continue shopping",
                                        style = TextStyle(
                                            fontSize = 28.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFF1E8),
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            ),
                                            shadow = Shadow(
                                                color = Color(0xFF000000),
                                                offset = Offset(3f, 2f)
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                                    )
                                }

                            }
                        }
                    }
                    2 -> {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(offsetRight2)
                                .alpha(alpha),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .alpha(0.3f)
                                        .padding(bottom = 16.dp)
                                        .background(Color(0xFFD9D9D9))
                                )
                                Text(
                                    text = "Error placing order",
                                    style = TextStyle(
                                        fontSize = 28.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color.Red,
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 20.dp)

                                )
//                                Text(
//                                    text = "Our team will contact you regarding",
//                                    style = TextStyle(
//                                        fontSize = 24.sp,
//                                        //fontWeight = FontWeight.Bold,
//                                        color = Color(0xFF83769C),
//                                        fontFamily = FontFamily(
//                                            Font(R.font.alcher_pixel, FontWeight.Normal),
//                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
//                                        )
//                                    )
//                                )
                                Text(
                                    text = "Contact us if the issue persists",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color(0xFF83769C),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 24.dp)
                                )
                                Button(
                                    onClick = {
                                        onFinish()
                                    },
                                    modifier = Modifier
                                        .padding(top = 20.dp, bottom = 4.dp)
                                        .paint(
                                            painter = painterResource(id = R.drawable.sign_in_button),
                                            contentScale = ContentScale.FillBounds
                                        ),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Transparent
                                    )
                                ) {
                                    Text(
                                        text = "Continue shopping",
                                        style = TextStyle(
                                            fontSize = 28.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFF1E8),
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            ),
                                            shadow = Shadow(
                                                color = Color(0xFF000000),
                                                offset = Offset(3f, 2f)
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                                    )
                                }

                            }
                        }

                    }
                }
            }
        }


    }
}

//@Preview
//@Composable
//fun CheckoutLayout2025Preview() {
//    CheckoutLayout2025(
//        dbHandler = null,
//        sharedPreferences = null,
//        setUserName = { _ -> },
//        setPhone = { _ -> },
//        setPincode = { _ -> },
//        setHouse = { _ -> },
//        setRoad = { _ -> },
//        setCity = { _ -> },
//        setState = { _ -> },
//        setEmail = { _ -> },
//        setTotalAmount = { _ -> },
//        calculateTotalAmount = { _, _, _ -> 0L },
//        getShippingCharges = { 0L },
//        setArrayListItems = { _ -> },
//        calculateAmount = { _, _ -> },
//        onFinish = { },
//        firebaseAuth = null,
//        onIncrementClick = { _, _ ->
//        },
//        onDecrementClick = { _, _ ->
//        },
//        startPayment = {
//
//        },
//        getTotalAmount = {
//            0L
//        },
//        getOrderStatus = {
//            mutableStateOf(0)
//        },
//        spName = ""
//    )
//}


class CheckoutActivity2024 : AppCompatActivity() {

    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbHandler: DBHandler? = null
    lateinit var builder: Retrofit.Builder
    lateinit var retrofit: Retrofit
    val firebaseAuth = FirebaseAuth.getInstance()
    var user_name = ""
    var user_phone = ""
    var user_house = ""
    var user_road = ""
    var user_state = ""
    var user_city = ""
    var user_pin_code = ""
    var total_amount = 0L
    var Email = ""
    var shipping_charges = 0L
    lateinit var arrayList: ArrayList<cartModel>
    val DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val orderStatus = mutableStateOf(0)

    fun randomString(len: Int): String {
        val sb = StringBuilder(len)
        for (i in 0 until len) {
            sb.append(DATA[Random.nextInt(DATA.length)])
        }
        return sb.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("address", Context.MODE_PRIVATE)
        firebaseFirestore.collection("Constants").document("Merch").get()
            .addOnSuccessListener { task ->
                if (task.exists()) {
                    shipping_charges = task.get("shipping").toString().toLong()
                }
            }
        val gson = GsonBuilder()
            .setLenient()
            .create()

        builder = Retrofit.Builder()
            .baseUrl("https://docs.google.com/forms/d/e/1FAIpQLSf71ob3UMuWymlglEleeEGEWtRVE01kzl-dJuRbciDVvszDEw/")
            .addConverterFactory(GsonConverterFactory.create(gson))

        retrofit = builder.build()
        val firebaseAuth = FirebaseAuth.getInstance()


        setContent {
            val sp = getSharedPreferences("USER", MODE_PRIVATE)
            var spName = sp.getString("name", "")
            if (spName == "") {
                spName = sharedPreferences.getString("name", "")
            }

            dbHandler = DBHandler(applicationContext)

            CheckoutLayout2025(
                dbHandler = dbHandler,
                sharedPreferences = sharedPreferences,
                firebaseAuth = firebaseAuth,
                onDecrementClick = { it1, it2 ->
                    OnDecrementClick(it1, it2)
                },
                onIncrementClick = { it1, it2 ->
                    OnIncrementClick(it1, it2)
                },
                setUserName = {
                    user_name = it
                },
                setPhone = {
                    user_phone = it
                },
                setPincode = {
                    user_pin_code = it
                },
                setHouse = {
                    user_house = it
                },
                setRoad = {
                    user_road = it
                },
                setCity = {
                    user_city = it
                },
                setState = {
                    user_state = it
                },
                setEmail = {
                    Email = it
                },
                setTotalAmount = {
                    total_amount = it
                },
                calculateTotalAmount = { it1, it2, it3 ->
                    calculate_totalAmount(it1, it2, it3)
                },
                getShippingCharges = {
                    shipping_charges
                },
                setArrayListItems = {
                    arrayList = it
                },
                calculateAmount = { it1, it2 ->
                    calculate_amount(it1, it2)
                },
                onFinish = { finish() },
                startPayment = {
                    startPayment(it)
                },
                getTotalAmount = {
                    total_amount
                },
                getOrderStatus = {
                    orderStatus
                },
                spName = spName
            )
            // A surface container using the 'background' color from the theme
//            Alcheringa2022Theme {
//                val sp = getSharedPreferences("USER", MODE_PRIVATE)
//                var spName = sp.getString("name", "")
//                if (spName == "") {
//                    spName = sharedPreferences.getString("name", "")
//                }
//
//
//                var key by remember {
//                    mutableStateOf(0)
//                }
//
//                dbHandler = DBHandler(applicationContext)
//
//                var cartModelItems by remember { mutableStateOf(dbHandler!!.readCourses()) }
//
//                LaunchedEffect(key)
//                {
//                    cartModelItems = dbHandler!!.readCourses()
//                    arrayList = dbHandler!!.readCourses()
//                }
//
//                var name by remember {
//                    mutableStateOf(spName)
//                }
//
//                var phone by remember {
//                    mutableStateOf(
//                        sharedPreferences.getString(
//                            "phone",
//                            ""
//                        )
//                    )
//                }
//                var pincode by remember {
//                    mutableStateOf(
//                        sharedPreferences.getString(
//                            "pincode",
//                            ""
//                        )
//                    )
//                }
//
//                var house by remember {
//                    mutableStateOf(
//                        sharedPreferences.getString(
//                            "house",
//                            ""
//                        )
//                    )
//                }
//                var road by remember { mutableStateOf(sharedPreferences.getString("road", "")) }
//                var city by remember { mutableStateOf(sharedPreferences.getString("city", "")) }
////                var email by remember { mutableStateOf(sharedPreferences.getString("email", "")) }
//                var state by remember {
//                    mutableStateOf(
//                        sharedPreferences.getString(
//                            "state",
//                            ""
//                        )
//                    )
//                }
//                var checkedState by remember {
//                    mutableStateOf(0)
//                }
//
//                val keyboardController = LocalSoftwareKeyboardController.current
//                val bringIntoViewRequester = remember { BringIntoViewRequester() }
//                val coroutineScope = rememberCoroutineScope()
//
//                var amount: Long by remember {
//                    mutableStateOf(0L)
//                }
//                var totalItems: Int by remember {
//                    mutableStateOf(0)
//                }
//
//                total_amount = calculate_totalAmount(amount, shipping_charges, totalItems)
//
//                LaunchedEffect(cartModelItems, Unit) {
//                    Log.d("MainActivity", "launched")
//
//                    calculate_amount(cartModelItems) { it1, it2 ->
//                        amount = it1
//                        totalItems = it2
//                    }
//                }
//
//                user_name = name!!
//                user_phone = phone!!
//                user_house = house!!
//                user_road = road!!
//                user_state = state!!
//                user_city = city!!
//                user_pin_code = pincode!!
//                Email = firebaseAuth.currentUser?.email.toString()
//
//                var greyColor = if (isSystemInDarkTheme()) grey else darkGrey
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(colors.background)
//                        .pointerInput(Unit) {
//                            detectTapGestures(
//                                onPress = { keyboardController?.hide() }
//                            )
//                        }
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .height(65.dp)
//                                .fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.cart_arrow),
//                                contentDescription = null,
//                                tint = lighterPurple,
//                                modifier = Modifier
//                                    .padding(start = 10.dp, end = 10.dp)
//                                    .clickable {
//                                        if (checkedState == 0 || checkedState == 2) {
//                                            finish()
//                                        } else {
//                                            checkedState -= 1
//                                        }
//                                    }
//                                    .padding(10.dp)
//                            )
//                            Text(
//                                text = "Checkout",
//                                fontFamily = futura,
//                                fontWeight = FontWeight.SemiBold,
//                                fontSize = 22.sp,
//                                color = containerPurple
//                            )
//
//                        }
//                        Divider(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(2.dp)
//                                .background(darkTealGreen)
//                        )
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(horizontal = 20.dp)
//                                .paint(
//                                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
//                                    contentScale = ContentScale.Crop
//                                )
//                        ) {
//                            val focusManager = LocalFocusManager.current
//                            Spacer(modifier = Modifier.height(30.dp))
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .wrapContentHeight()
//                                    .padding(start = 10.dp),
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Canvas(
//                                    modifier = Modifier
//                                        .wrapContentSize()
//                                        .alpha(if (checkedState == 2) 0f else 1f)
//                                ) {
//                                    drawCircle(
//                                        color = darkTealGreen,
//                                        radius = 35f,
//                                        style = Stroke(width = 15f, cap = StrokeCap.Round)
//                                    )
//                                    if (checkedState == 0) {
//                                        drawCircle(
//                                            color = darkTealGreen,
//                                            radius = 20f,
//                                        )
//                                    }
//                                }
//                                Text(
//                                    text = "Details",
//                                    modifier = Modifier
//                                        .padding(start = 20.dp, end = 10.dp)
//                                        .alpha(if (checkedState == 2) 0f else 1f),
//                                    color = darkTealGreen,
//                                    fontFamily = futura,
//                                    fontSize = 14.sp
//                                )
//                                Divider(
//                                    modifier = Modifier
//                                        .weight(1.0f)
//                                        .height(1.dp)
//                                        .alpha(if (checkedState == 2) 0f else 1f)
//                                        .background(
//                                            if (checkedState >= 1) darkTealGreen else greyColor
//                                        )
//                                )
//                                Canvas(
//                                    modifier = Modifier
//                                        .wrapContentSize()
//                                        .padding(start = 20.dp)
//                                        .alpha(if (checkedState == 2) 0f else 1f)
//                                ) {
//                                    drawCircle(
//                                        color = if (checkedState >= 1) darkTealGreen else greyColor,
//                                        radius = 35f,
//                                        style = Stroke(width = 15f, cap = StrokeCap.Round)
//                                    )
//                                    if (checkedState == 1) {
//                                        drawCircle(
//                                            color = darkTealGreen,
//                                            radius = 20f,
//                                        )
//                                    }
//                                }
//                                Text(
//                                    text = "Review",
//                                    modifier = Modifier
//                                        .padding(start = 20.dp, end = 10.dp)
//                                        .alpha(if (checkedState == 2) 0f else 1f),
//                                    color = if (checkedState >= 1) darkTealGreen else greyColor,
//                                    fontFamily = futura,
//                                    fontSize = 14.sp
//                                )
//                                Divider(
//                                    modifier = Modifier
//                                        .weight(1.0f)
//                                        .height(1.dp)
//                                        .alpha(if (checkedState == 2) 0f else 1f)
//                                        .background(if (checkedState >= 2) darkTealGreen else greyColor)
//                                )
//                                Canvas(
//                                    modifier = Modifier
//                                        .wrapContentSize()
//                                        .padding(start = 20.dp)
//                                        .alpha(if (checkedState == 2) 0f else 1f)
//                                ) {
//                                    drawCircle(
//                                        color = if (checkedState >= 2) darkTealGreen else greyColor,
//                                        radius = 35f,
//                                        style = Stroke(width = 15f, cap = StrokeCap.Round)
//                                    )
//                                    if (checkedState == 2) {
//                                        drawCircle(
//                                            color = darkTealGreen,
//                                            radius = 20f,
//                                        )
//                                    }
//                                }
//                                MarqueeText(
//                                    text = "Order Confirmation",
//                                    modifier = Modifier
//                                        .padding(start = 20.dp, end = 10.dp)
//                                        .alpha(if (checkedState == 2) 0f else 1f),
//                                    color = if (checkedState >= 2) darkTealGreen else greyColor,
//                                    fontFamily = futura,
//                                    fontSize = 14.sp,
//                                    gradientEdgeColor = Color.Transparent
//                                )
//                            }
//                            Spacer(modifier = Modifier.height(40.dp))
//
//                            val offsetRight1 by animateDpAsState(
//                                targetValue = if (checkedState == 1) 0.dp else 400.dp,
//                                animationSpec = spring()
//                            )
//                            val offsetRight2 by animateDpAsState(
//                                targetValue = if (checkedState == 2) 0.dp else 400.dp,
//                                animationSpec = spring()
//                            )
//
//                            when (checkedState) {
//                                0 -> {
//                                    val alpha by animateFloatAsState(
//                                        targetValue = if (checkedState == 0) 1f else 0f,
//                                        animationSpec = spring(),
//                                    )
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .alpha(alpha)
//
//                                    ) {
//                                        Column(
//                                            modifier = Modifier
//                                                .fillMaxSize()
//                                                .verticalScroll(rememberScrollState())
//                                                .padding(bottom = 100.dp)
//
//                                        ) {
//                                            Text(
//                                                text = "Personal Information",
//                                                fontSize = 14.sp,
//                                                fontFamily = futura,
//                                                color = colors.onBackground,
//                                            )
//
//                                            Spacer(modifier = Modifier.height(10.dp))
//
//                                            TextField(
//                                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                                                keyboardActions = KeyboardActions(
//                                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
//                                                ),
//                                                textStyle = (
//                                                        TextStyle(
//                                                            color = colors.onBackground,
//                                                            fontFamily = futura,
//                                                            fontSize = 14.sp
//                                                        )
//                                                        ),
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .onFocusEvent { focusState ->
//                                                        if (focusState.isFocused) {
//                                                            coroutineScope.launch {
//                                                                bringIntoViewRequester.bringIntoView()
//                                                            }
//                                                        }
//                                                    }
//                                                    .border(
//                                                        1.dp,
//                                                        colors.onBackground,
//                                                        RoundedCornerShape(4.dp)
//                                                    ),
//                                                maxLines = 1,
//                                                singleLine = true,
//                                                colors = TextFieldDefaults.textFieldColors(
//                                                    focusedIndicatorColor = Color.Transparent,
//                                                    backgroundColor = Color.Transparent
//                                                ),
//                                                shape = RoundedCornerShape(4.dp),
//                                                value = name!!,
//                                                onValueChange = {
//                                                    name = it
//                                                    user_name = it
//                                                },
//                                                placeholder = {
//                                                    Text(
//                                                        text = "Full Name",
//                                                        color = greyColor,
//                                                        fontFamily = futura,
//                                                        fontSize = 14.sp
//                                                    )
//                                                }
//                                            )
//
//                                            Spacer(modifier = Modifier.height(10.dp))
//
//                                            TextField(
//                                                keyboardOptions = KeyboardOptions(
//                                                    keyboardType = KeyboardType.Phone,
//                                                    imeAction = ImeAction.Next
//                                                ),
//                                                keyboardActions = KeyboardActions(
//                                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
//                                                ),
//                                                textStyle = (
//                                                        TextStyle(
//                                                            color = colors.onBackground,
//                                                            fontFamily = futura,
//                                                            fontSize = 14.sp
//                                                        )
//                                                        ),
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .onFocusEvent { focusState ->
//                                                        if (focusState.isFocused) {
//                                                            coroutineScope.launch {
//                                                                bringIntoViewRequester.bringIntoView()
//                                                            }
//                                                        }
//                                                    }
//                                                    .border(
//                                                        1.dp,
//                                                        colors.onBackground,
//                                                        RoundedCornerShape(4.dp)
//                                                    ),
//                                                maxLines = 1,
//                                                singleLine = true,
//                                                colors = TextFieldDefaults.textFieldColors(
//                                                    focusedIndicatorColor = Color.Transparent,
//                                                    backgroundColor = Color.Transparent
//                                                ),
//                                                shape = RoundedCornerShape(4.dp),
//                                                value = phone!!,
//                                                onValueChange = {
//                                                    if (it.length <= 10) {
//                                                        phone = it
//                                                        user_phone = it
//                                                    }
//                                                },
//                                                placeholder = {
//                                                    Text(
//                                                        text = "Phone Number",
//                                                        color = greyColor,
//                                                        fontFamily = futura,
//                                                        fontSize = 14.sp
//                                                    )
//                                                }
//                                            )
//
//                                            Spacer(modifier = Modifier.height(40.dp))
//
//                                            Text(
//                                                text = "Address Details",
//                                                fontSize = 14.sp,
//                                                fontFamily = futura,
//                                                color = colors.onBackground
//                                            )
//
//                                            Spacer(modifier = Modifier.height(10.dp))
//
//                                            TextField(
//                                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                                                keyboardActions = KeyboardActions(
//                                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
//                                                ),
//                                                textStyle = (
//                                                        TextStyle(
//                                                            color = colors.onBackground,
//                                                            fontFamily = futura,
//                                                            fontSize = 14.sp
//                                                        )
//                                                        ),
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .onFocusEvent { focusState ->
//                                                        if (focusState.isFocused) {
//                                                            coroutineScope.launch {
//                                                                bringIntoViewRequester.bringIntoView()
//                                                            }
//                                                        }
//                                                    }
//                                                    .border(
//                                                        1.dp,
//                                                        colors.onBackground,
//                                                        RoundedCornerShape(4.dp)
//                                                    ),
//                                                maxLines = 1,
//                                                singleLine = true,
//                                                colors = TextFieldDefaults.textFieldColors(
//                                                    focusedIndicatorColor = Color.Transparent,
//                                                    backgroundColor = Color.Transparent
//                                                ),
//                                                shape = RoundedCornerShape(4.dp),
//                                                value = house!!,
//                                                onValueChange = {
//                                                    house = it
//                                                    user_house = it
//                                                },
//                                                placeholder = {
//                                                    Text(
//                                                        text = "Address Line 1",
//                                                        color = greyColor,
//                                                        fontFamily = futura,
//                                                        fontSize = 14.sp
//                                                    )
//                                                }
//                                            )
//
//                                            Spacer(modifier = Modifier.height(10.dp))
//
//                                            TextField(
//                                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                                                keyboardActions = KeyboardActions(
//                                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
//                                                ),
//                                                textStyle = (
//                                                        TextStyle(
//                                                            color = colors.onBackground,
//                                                            fontFamily = futura,
//                                                            fontSize = 14.sp
//                                                        )
//                                                        ),
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .onFocusEvent { focusState ->
//                                                        if (focusState.isFocused) {
//                                                            coroutineScope.launch {
//                                                                bringIntoViewRequester.bringIntoView()
//                                                            }
//                                                        }
//                                                    }
//                                                    .border(
//                                                        1.dp,
//                                                        colors.onBackground,
//                                                        RoundedCornerShape(4.dp)
//                                                    ),
//                                                maxLines = 1,
//                                                singleLine = true,
//                                                colors = TextFieldDefaults.textFieldColors(
//                                                    focusedIndicatorColor = Color.Transparent,
//                                                    backgroundColor = Color.Transparent
//                                                ),
//                                                shape = RoundedCornerShape(4.dp),
//                                                value = road!!,
//                                                onValueChange = {
//                                                    road = it
//                                                    user_road = it
//                                                },
//                                                placeholder = {
//                                                    Text(
//                                                        text = "Address Line 2",
//                                                        color = greyColor,
//                                                        fontFamily = futura,
//                                                        fontSize = 14.sp
//                                                    )
//                                                }
//                                            )
//
//                                            Spacer(modifier = Modifier.height(10.dp))
//
//                                            Row(
//                                                modifier = Modifier
//                                                    .fillMaxWidth(),
//                                                horizontalArrangement = Arrangement.SpaceBetween
//                                            ) {
//                                                TextField(
//                                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                                                    keyboardActions = KeyboardActions(
//                                                        onNext = {
//                                                            focusManager.moveFocus(
//                                                                FocusDirection.Down
//                                                            )
//                                                        }
//                                                    ),
//                                                    textStyle = (
//                                                            TextStyle(
//                                                                color = colors.onBackground,
//                                                                fontFamily = futura,
//                                                                fontSize = 14.sp
//                                                            )
//                                                            ),
//                                                    modifier = Modifier
//                                                        .fillMaxWidth(0.489f)
//                                                        .onFocusEvent { focusState ->
//                                                            if (focusState.isFocused) {
//                                                                coroutineScope.launch {
//                                                                    bringIntoViewRequester.bringIntoView()
//                                                                }
//                                                            }
//                                                        }
//                                                        .border(
//                                                            1.dp,
//                                                            colors.onBackground,
//                                                            RoundedCornerShape(4.dp)
//                                                        ),
//                                                    maxLines = 1,
//                                                    singleLine = true,
//                                                    colors = TextFieldDefaults.textFieldColors(
//                                                        focusedIndicatorColor = Color.Transparent,
//                                                        backgroundColor = Color.Transparent
//                                                    ),
//                                                    shape = RoundedCornerShape(8.dp),
//                                                    value = city!!,
//                                                    onValueChange = {
//                                                        city = it
//                                                        user_city = it
//                                                    },
//                                                    placeholder = {
//                                                        Text(
//                                                            text = "City",
//                                                            color = greyColor,
//                                                            fontFamily = futura,
//                                                            fontSize = 14.sp
//                                                        )
//                                                    }
//                                                )
//
//                                                Spacer(modifier = Modifier.width(5.dp))
//
//                                                TextField(
//                                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                                                    keyboardActions = KeyboardActions(
//                                                        onNext = {
//                                                            focusManager.moveFocus(
//                                                                FocusDirection.Down
//                                                            )
//                                                        }
//                                                    ),
//                                                    textStyle = (
//                                                            TextStyle(
//                                                                color = colors.onBackground,
//                                                                fontFamily = futura,
//                                                                fontSize = 14.sp
//                                                            )
//                                                            ),
//                                                    modifier = Modifier
//                                                        .fillMaxWidth()
//                                                        .onFocusEvent { focusState ->
//                                                            if (focusState.isFocused) {
//                                                                coroutineScope.launch {
//                                                                    bringIntoViewRequester.bringIntoView()
//                                                                }
//                                                            }
//                                                        }
//                                                        .border(
//                                                            1.dp,
//                                                            colors.onBackground,
//                                                            RoundedCornerShape(4.dp)
//                                                        ),
//                                                    maxLines = 1,
//                                                    singleLine = true,
//                                                    colors = TextFieldDefaults.textFieldColors(
//                                                        focusedIndicatorColor = Color.Transparent,
//                                                        backgroundColor = Color.Transparent
//                                                    ),
//                                                    shape = RoundedCornerShape(8.dp),
//                                                    value = state!!,
//                                                    onValueChange = {
//                                                        state = it
//                                                        user_state = it
//                                                    },
//                                                    placeholder = {
//                                                        Text(
//                                                            text = "State",
//                                                            color = greyColor,
//                                                            fontFamily = futura,
//                                                            fontSize = 14.sp
//                                                        )
//                                                    }
//                                                )
//                                            }
//
//                                            Spacer(modifier = Modifier.height(10.dp))
//
//                                            TextField(
//                                                keyboardOptions = KeyboardOptions(
//                                                    keyboardType = KeyboardType.Phone,
//                                                    imeAction = ImeAction.Next
//                                                ),
//                                                keyboardActions = KeyboardActions(
//                                                    onDone = { keyboardController?.hide() }
//                                                ),
//                                                textStyle = (
//                                                        TextStyle(
//                                                            color = colors.onBackground,
//                                                            fontFamily = futura,
//                                                            fontSize = 14.sp
//                                                        )
//                                                        ),
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .onFocusEvent { focusState ->
//                                                        if (focusState.isFocused) {
//                                                            coroutineScope.launch {
//                                                                bringIntoViewRequester.bringIntoView()
//                                                            }
//                                                        }
//                                                    }
//                                                    .border(
//                                                        1.dp,
//                                                        colors.onBackground,
//                                                        RoundedCornerShape(4.dp)
//                                                    ),
//                                                maxLines = 1,
//                                                singleLine = true,
//                                                colors = TextFieldDefaults.textFieldColors(
//                                                    focusedIndicatorColor = Color.Transparent,
//                                                    backgroundColor = Color.Transparent
//                                                ),
//                                                shape = RoundedCornerShape(8.dp),
//                                                value = pincode!!,
//                                                onValueChange = {
//                                                    pincode = it
//                                                    user_pin_code = it
//                                                },
//                                                placeholder = {
//                                                    Text(
//                                                        text = "Pincode",
//                                                        color = greyColor,
//                                                        fontFamily = futura,
//                                                        fontSize = 14.sp
//                                                    )
//                                                }
//                                            )
//                                        }
//                                        Box(
//                                            modifier = Modifier
//                                                .fillMaxSize()
//                                                .padding(bottom = 20.dp),
//                                            contentAlignment = Alignment.BottomCenter
//                                        ) {
//                                            Box(
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .wrapContentHeight()
//                                                    .border(
//                                                        1.dp,
//                                                        colors.onBackground,
//                                                        RoundedCornerShape(6.dp)
//                                                    )
//                                                    .background(
//                                                        Brush.verticalGradient(
//                                                            0f to lighterPurple,
//                                                            1f to borderdarkpurple
//                                                        ),
//                                                        shape = RoundedCornerShape(6.dp)
//                                                    )
//                                                    .clickable {
//                                                        val patternPhone = Regex("^[1-9][0-9]{9}$")
//                                                        var isPhoneValid =
//                                                            patternPhone.containsMatchIn(phone!!)
//
//                                                        val patternPinCode = Regex("^[0-9]{6}$")
//                                                        var isPinCodeValid =
//                                                            patternPinCode.containsMatchIn(pincode!!)
//
//                                                        when {
//                                                            name!!.isEmpty() -> {
//                                                                Toast
//                                                                    .makeText(
//                                                                        applicationContext,
//                                                                        "Please enter your name",
//                                                                        Toast.LENGTH_SHORT
//                                                                    )
//                                                                    .show()
//                                                            }
//
//                                                            !isPhoneValid -> {
//                                                                Toast
//                                                                    .makeText(
//                                                                        applicationContext,
//                                                                        "Please enter a 10 digit phone number",
//                                                                        Toast.LENGTH_SHORT
//                                                                    )
//                                                                    .show()
//                                                            }
//
//                                                            house!!.isEmpty() -> {
//                                                                Toast
//                                                                    .makeText(
//                                                                        applicationContext,
//                                                                        "Please enter your House No and Building Name",
//                                                                        Toast.LENGTH_SHORT
//                                                                    )
//                                                                    .show()
//                                                            }
//
//                                                            road!!.isEmpty() -> {
//                                                                Toast
//                                                                    .makeText(
//                                                                        applicationContext,
//                                                                        "Please enter your Road Name, Area and Colony",
//                                                                        Toast.LENGTH_SHORT
//                                                                    )
//                                                                    .show()
//                                                            }
//
//                                                            city!!.isEmpty() -> {
//                                                                Toast
//                                                                    .makeText(
//                                                                        applicationContext,
//                                                                        "Please enter your City",
//                                                                        Toast.LENGTH_SHORT
//                                                                    )
//                                                                    .show()
//                                                            }
//
//                                                            state!!.isEmpty() -> {
//                                                                Toast
//                                                                    .makeText(
//                                                                        applicationContext,
//                                                                        "Please enter your State",
//                                                                        Toast.LENGTH_SHORT
//                                                                    )
//                                                                    .show()
//                                                            }
//
//                                                            (!isPinCodeValid) -> {
//                                                                Toast
//                                                                    .makeText(
//                                                                        applicationContext,
//                                                                        "Please enter a 6 digit numeric pincode",
//                                                                        Toast.LENGTH_SHORT
//                                                                    )
//                                                                    .show()
//                                                            }
//
//                                                            else -> {
//                                                                val editor: SharedPreferences.Editor =
//                                                                    sharedPreferences.edit()
//                                                                editor.putString("name", name)
//                                                                editor.putString("phone", phone)
//                                                                editor.putString("house", house)
//                                                                editor.putString("road", road)
//                                                                editor.putString("city", city)
//                                                                editor.putString("state", state)
//                                                                editor.putString("pincode", pincode)
//                                                                editor.apply()
//                                                                //editor.commit()
//                                                                checkedState = 1;
//                                                            }
//
//
//                                                        }
//                                                    }
//                                                    .padding(15.dp),
//                                                contentAlignment = Alignment.Center,
//                                            ) {
//                                                Text(
//                                                    text = "Proceed",
//                                                    fontSize = 22.sp,
//                                                    fontFamily = futura,
//                                                    color = lightBar
//                                                )
//                                            }
//                                        }
//                                    }
//                                }
//
//                                1 -> {
//                                    val alpha by animateFloatAsState(
//                                        targetValue = if (checkedState == 1) 1f else 0f,
//                                        animationSpec = tween(durationMillis = 3000)
//                                    )
//                                    Box(
//                                        modifier = Modifier
//                                            .offset(x = offsetRight1)
//                                            .alpha(alpha)
//                                    ) {
//                                        LazyColumn(
//                                        ) {
//                                            item {
//                                                Text(
//                                                    text = "Deliver To:",
//                                                    fontSize = 14.sp,
//                                                    fontFamily = futura,
//                                                    color = greyColor
//                                                )
//
//                                                Spacer(modifier = Modifier.height(10.dp))
//
//                                                Text(
//                                                    text = name!!,
//                                                    color = colors.onBackground,
//                                                    fontSize = 18.sp,
//                                                    fontFamily = futura
//                                                )
//
//                                                Spacer(modifier = Modifier.height(10.dp))
//
//                                                Text(
//                                                    text = "$house, $road",
//                                                    color = colors.onBackground,
//                                                    fontSize = 14.sp,
//                                                    fontFamily = futura
//                                                )
//
//                                                Spacer(modifier = Modifier.height(5.dp))
//
//                                                Text(
//                                                    text = "$city, $state - $pincode",
//                                                    color = colors.onBackground,
//                                                    fontSize = 14.sp,
//                                                    fontFamily = futura
//                                                )
//
//                                                Spacer(modifier = Modifier.height(30.dp))
//
//                                                Text(
//                                                    text = "Phone Number:",
//                                                    color = greyColor,
//                                                    fontSize = 14.sp,
//                                                    fontFamily = futura
//                                                )
//
//                                                Spacer(modifier = Modifier.height(10.dp))
//
//                                                Text(
//                                                    text = "$phone",
//                                                    color = colors.onBackground,
//                                                    fontSize = 18.sp,
//                                                    fontFamily = futura
//                                                )
//
//                                                Spacer(modifier = Modifier.height(40.dp))
//
//                                                Text(
//                                                    text = "Your Orders:",
//                                                    color = colors.onBackground,
//                                                    fontSize = 18.sp,
//                                                    fontFamily = futura
//                                                )
//
//                                                Spacer(modifier = Modifier.height(10.dp))
//                                            }
//                                            itemsIndexed(cartModelItems) { index, item ->
//                                                Card(
//                                                    modifier = Modifier
//                                                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
//                                                        .height(210.dp)
//                                                        .background(
//                                                            colors.background,
//                                                            shape = RoundedCornerShape(8.dp)
//                                                        )
//                                                        .border(
//                                                            1.dp,
//                                                            colors.onBackground,
//                                                            RoundedCornerShape(8.dp)
//                                                        ),
//
//                                                    //set card elevation of the card
//
//                                                    backgroundColor = colors.background,
//                                                ) {
//                                                    Column(modifier = Modifier) {
//                                                        Row() {
//                                                            Box(
//                                                                modifier = Modifier
//                                                                    .fillMaxHeight()
//                                                                    .fillMaxWidth(0.46f)
//
//
//                                                            ) {
//                                                                //   Box(modifier=Modifier.fillMaxHeight().background(color=Color.Blue,shape= RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))){
//                                                                Image(
//                                                                    painter = painterResource(id = R.drawable.cart_item_bg),
//                                                                    contentDescription = "cart_item_bg",
//                                                                    modifier = Modifier
//                                                                        .fillMaxHeight()
//                                                                        .clip(
//                                                                            RoundedCornerShape(
//                                                                                topStart = 8.dp,
//                                                                                bottomStart = 8.dp
//                                                                            )
//                                                                        ),
//                                                                    contentScale = ContentScale.Crop
//                                                                )
//                                                                //   }
//                                                                Box(
//                                                                    modifier = Modifier
//                                                                        .fillMaxSize(0.8f)
//                                                                        .align(Alignment.Center)
//                                                                ) {
//                                                                    Image(
//                                                                        rememberImagePainter(
//                                                                            item.image,
//                                                                            builder = {
//                                                                                placeholder(R.drawable.white_circle_bg)
//                                                                            }
//                                                                        ),
//                                                                        contentDescription = null,
//                                                                        modifier = Modifier.align(
//                                                                            Alignment.Center
//                                                                        )
//                                                                    )
//                                                                }
//                                                            }
//                                                            Box(modifier = Modifier.fillMaxSize()) {
//
//                                                                Column(
//                                                                    modifier = Modifier
//                                                                        .padding(16.dp)
//                                                                        .fillMaxWidth()
//                                                                ) {
//
//
//                                                                    Text(
//                                                                        text = item.type!!,
//                                                                        modifier = Modifier.align(
//                                                                            Alignment.CenterHorizontally
//                                                                        ),
//                                                                        color = colors.onBackground,
//                                                                        fontFamily = futura,
//                                                                        fontWeight = FontWeight(
//                                                                            400
//                                                                        ),
//                                                                        fontSize = 18.sp,
//                                                                        lineHeight = 24.sp
//
//                                                                    )
//                                                                    Text(
//                                                                        text = item.name!!,
//                                                                        modifier = Modifier.align(
//                                                                            Alignment.CenterHorizontally
//                                                                        ),
//                                                                        fontWeight = FontWeight(
//                                                                            300
//                                                                        ),
//                                                                        fontFamily = futura,
//                                                                        lineHeight = 18.sp,
//
//
//                                                                        color = colors.onBackground,
//                                                                        fontSize = 14.sp
//
//
//                                                                    )
//
//                                                                    Spacer(
//                                                                        modifier = Modifier.height(
//                                                                            10.dp
//                                                                        )
//                                                                    )
//
//                                                                    Text(
//                                                                        text = "Rs. " + item.price!! + ".00/-",
//                                                                        modifier = Modifier.align(
//                                                                            Alignment.CenterHorizontally
//                                                                        ),
//                                                                        color = colors.onBackground,
//                                                                        fontWeight = FontWeight(
//                                                                            450
//                                                                        ),
//                                                                        fontSize = 18.sp,
//                                                                        fontFamily = futura,
//                                                                        lineHeight = 24.sp
//
//
//                                                                        //maxLines = 1,
//                                                                        //overflow = TextOverflow.Ellipsis,
//                                                                    )
//                                                                    Spacer(
//                                                                        modifier = Modifier.height(
//                                                                            10.dp
//                                                                        )
//                                                                    )
//
//                                                                    Row(
//                                                                        modifier = Modifier.align(
//                                                                            Alignment.CenterHorizontally
//                                                                        )
//                                                                    ) {
//                                                                        Text(
//                                                                            text = "Size: ",
//                                                                            modifier = Modifier,
//                                                                            color = colors.onBackground,
//                                                                            fontWeight = FontWeight(
//                                                                                300
//                                                                            ),
//                                                                            fontSize = 14.sp,
//                                                                            fontFamily = futura,
//                                                                            lineHeight = 18.sp
//
//
//                                                                            //maxLines = 1,
//                                                                            //overflow = TextOverflow.Ellipsis,
//
//                                                                        )
//                                                                        Text(
//                                                                            text = item.size!!,
//                                                                            modifier = Modifier,
//                                                                            color = colors.onBackground,
//                                                                            fontWeight = FontWeight(
//                                                                                400
//                                                                            ),
//                                                                            fontFamily = futura,
//                                                                            lineHeight = 18.sp
//
//                                                                            //maxLines = 1,
//                                                                            //overflow = TextOverflow.Ellipsis,
//
//                                                                        )
//                                                                    }
//                                                                    Spacer(
//                                                                        modifier = Modifier.height(
//                                                                            15.dp
//                                                                        )
//                                                                    )
//                                                                    Row() {
//                                                                        Row(
//                                                                            modifier = Modifier.fillMaxWidth(),
//                                                                            horizontalArrangement = Arrangement.Center
//                                                                        ) {
//
//                                                                            Box(
//                                                                                modifier = Modifier
//                                                                                    .clickable {
//                                                                                        OnDecrementClick(
//                                                                                            index,
//                                                                                            cartModelItems
//                                                                                        )
//                                                                                        Log.d(
//                                                                                            "MainActivity",
//                                                                                            cartModelItems.size.toString()
//                                                                                        )
//                                                                                        key++
//                                                                                    }
//                                                                                    .background(
//                                                                                        greyColor,
//                                                                                        shape = RoundedCornerShape(
//                                                                                            topStart = 22.dp,
//                                                                                            bottomStart = 22.dp,
//                                                                                            topEnd = 0.dp,
//                                                                                            bottomEnd = 0.dp
//                                                                                        )
//
//                                                                                    )
//                                                                                    .width(40.dp)
//                                                                                    .height(35.dp),
//                                                                                contentAlignment = Alignment.Center
//                                                                            ) {
//                                                                                Text(
//                                                                                    "-",
//                                                                                    modifier = Modifier.align(
//                                                                                        Alignment.Center
//                                                                                    ),
//                                                                                    fontSize = 28.sp,
//                                                                                    fontFamily = futura,
//                                                                                    color = colors.background
//                                                                                )
//                                                                            }
//                                                                            Box(
//                                                                                modifier = Modifier
//                                                                                    .padding(
//                                                                                        horizontal = 1.dp
//                                                                                    )
//
//                                                                                    .background(
//                                                                                        greyColor,
//                                                                                        shape = RoundedCornerShape(
//                                                                                            topStart = 0.dp,
//                                                                                            bottomStart = 0.dp,
//                                                                                            topEnd = 0.dp,
//                                                                                            bottomEnd = 0.dp
//                                                                                        )
//
//                                                                                    )
//                                                                                    .width(40.dp)
//                                                                                    .height(35.dp),
//
//                                                                                ) {
//                                                                                Text(
//                                                                                    cartModelItems[index].count!!,
//                                                                                    modifier = Modifier.align(
//                                                                                        Alignment.Center
//                                                                                    ),
//                                                                                    fontSize = 28.sp,
//                                                                                    fontFamily = futura,
//                                                                                    color = colors.background
//                                                                                )
//                                                                            }
//                                                                            Box(
//                                                                                modifier = Modifier
//                                                                                    .clickable {
//                                                                                        OnIncrementClick(
//                                                                                            index,
//                                                                                            cartModelItems
//                                                                                        )
//                                                                                        key++
//                                                                                    }
//                                                                                    .background(
//                                                                                        greyColor,
//                                                                                        shape = RoundedCornerShape(
//                                                                                            topStart = 0.dp,
//                                                                                            bottomStart = 0.dp,
//                                                                                            topEnd = 22.dp,
//                                                                                            bottomEnd = 22.dp
//                                                                                        )
//
//                                                                                    )
//                                                                                    .width(40.dp)
//                                                                                    .height(35.dp),
//
//                                                                                ) {
//                                                                                Text(
//                                                                                    "+",
//                                                                                    modifier = Modifier.align(
//                                                                                        Alignment.Center
//                                                                                    ),
//                                                                                    fontSize = 28.sp,
//                                                                                    fontFamily = futura,
//                                                                                    color = colors.background
//                                                                                )
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                            item {
//                                                Spacer(modifier = Modifier.height(210.dp))
//                                            }
//                                        }
//
//                                        Box(
//                                            modifier = Modifier
//                                                .fillMaxSize()
//                                                .padding(bottom = 10.dp),
//                                            contentAlignment = Alignment.BottomCenter
//                                        ) {
//                                            Column {
//                                                Column(
//                                                    modifier = Modifier
//                                                        .background(
//                                                            colors.background,
//                                                            RoundedCornerShape(4.dp)
//                                                        )
//                                                        .border(
//                                                            1.dp,
//                                                            colors.onBackground,
//                                                            RoundedCornerShape(4.dp)
//                                                        )
//                                                        .padding(10.dp)
//                                                ) {
//                                                    Row {
//                                                        Text(
//                                                            text = "Price:",
//                                                            color = greyColor,
//                                                            fontFamily = futura,
//                                                            fontSize = 14.sp
//                                                        )
//                                                        Box(
//                                                            contentAlignment = Alignment.TopEnd,
//                                                            modifier = Modifier
//                                                                .fillMaxWidth()
//                                                        ) {
//                                                            Text(
//                                                                text = "Rs. $amount",
//                                                                color = greyColor,
//                                                                fontSize = 14.sp,
//                                                                fontFamily = futura
//                                                            )
//                                                        }
//                                                    }
//
//                                                    Spacer(modifier = Modifier.height(5.dp))
//
//                                                    Row {
//                                                        Text(
//                                                            text = "Shipping Charges:",
//                                                            color = greyColor,
//                                                            fontFamily = futura,
//                                                            fontSize = 14.sp
//                                                        )
//                                                        Box(
//                                                            contentAlignment = Alignment.TopEnd,
//                                                            modifier = Modifier
//                                                                .fillMaxWidth()
//                                                        ) {
//                                                            Text(
//                                                                text = "Rs. $shipping_charges",
//                                                                color = greyColor,
//                                                                fontSize = 14.sp,
//                                                                fontFamily = futura
//                                                            )
//                                                        }
//                                                    }
//
//                                                    Spacer(modifier = Modifier.height(5.dp))
//
//                                                    Row {
//                                                        Text(
//                                                            text = "Total : ",
//                                                            color = greyColor,
//                                                            fontFamily = futura,
//                                                            fontSize = 18.sp
//                                                        )
//                                                        Text(
//                                                            text = "$totalItems Item(s)",
//                                                            color = greyColor,
//                                                            fontFamily = futura,
//                                                            fontSize = 18.sp
//                                                        )
//                                                        Box(
//                                                            contentAlignment = Alignment.TopEnd,
//                                                            modifier = Modifier
//                                                                .fillMaxWidth()
//                                                        ) {
//                                                            Text(
//                                                                text = "Rs. $total_amount",
//                                                                color = colors.onBackground,
//                                                                fontSize = 18.sp,
//                                                                fontFamily = futura
//                                                            )
//                                                        }
//                                                    }
//                                                }
//
//                                                Spacer(modifier = Modifier.height(15.dp))
//                                                var brush: Brush by remember {
//                                                    mutableStateOf(
//                                                        Brush.verticalGradient(
//                                                            listOf(
//                                                                Color.Gray,
//                                                                Color.Gray
//                                                            )
//                                                        ) // Use Gray brush when there are no items
//                                                    )
//                                                }
//                                                if (totalItems == 0) {
//                                                    brush = Brush.verticalGradient(
//                                                        listOf(
//                                                            Color.Gray,
//                                                            Color.Gray
//                                                        )
//                                                    )
//                                                } else {
//                                                    brush = Brush.verticalGradient(
//                                                        0f to lighterPurple,
//                                                        1f to borderdarkpurple
//                                                    )
//                                                }
//
//                                                Box(
//                                                    modifier = Modifier
//                                                        .fillMaxWidth()
//                                                        .background(
//                                                            brush,
//                                                            shape = RoundedCornerShape(6.dp)
//                                                        )
//                                                        .border(
//                                                            1.dp,
//                                                            colors.onBackground,
//                                                            RoundedCornerShape(6.dp)
//                                                        )
//                                                        .clickable(enabled = totalItems > 0) {
//                                                            checkedState = 2
//                                                            startPayment(total_amount.toInt())
//                                                        }
//                                                        .padding(vertical = 15.dp),
//                                                    contentAlignment = Alignment.Center
//                                                ) {
//                                                    Text(
//                                                        text = "Place Pre-Order",
//                                                        color = lightBar,
//                                                        fontSize = 22.sp,
//                                                        fontFamily = futura
//                                                    )
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//
//                                2 -> {
//                                    val alpha by animateFloatAsState(
//                                        targetValue = if (checkedState == 2) 1f else 0f,
//                                        animationSpec = spring()
//                                    )
//
//                                    when (orderStatus.value) {
//                                        0 -> {
//                                            Box(
//                                                modifier = Modifier
//                                                    .fillMaxSize()
//                                                    .background(color = darkBar.copy(alpha = 0.6f)),
//                                                contentAlignment = Alignment.Center,
//
//                                                ) {
//                                                LoadingAnimation3()
//                                            }
//                                        }
//
//                                        1 -> {
//                                            Box(
//                                                modifier = Modifier
//                                                    .fillMaxSize()
//                                                    .offset(x = offsetRight2)
//                                                    .alpha(alpha),
//                                                contentAlignment = Alignment.Center
//                                            ) {
//                                                Column(
//                                                    horizontalAlignment = Alignment.CenterHorizontally,
//                                                    verticalArrangement = Arrangement.Center
//                                                ) {
//                                                    Box(
//                                                        modifier = Modifier
//                                                            .size(100.dp)
//                                                            .border(
//                                                                2.dp,
//                                                                darkTealGreen,
//                                                                RoundedCornerShape(100)
//                                                            ),
//                                                        contentAlignment = Alignment.Center
//                                                    ) {
//                                                        Icon(
//                                                            imageVector = Icons.Default.Done,
//                                                            contentDescription = null,
//                                                            tint = darkTealGreen,
//                                                            modifier = Modifier
//                                                                .size(70.dp)
//                                                        )
//                                                    }
//
//                                                    Spacer(modifier = Modifier.height(15.dp))
//
//                                                    Text(
//                                                        text = "Order Placed",
//                                                        fontSize = 18.sp,
//                                                        fontFamily = futura,
//                                                        color = greyColor
//                                                    )
//
//                                                    Spacer(modifier = Modifier.height(15.dp))
//
//                                                    Text(
//                                                        text = "Our team will contact you regarding",
//                                                        fontSize = 18.sp,
//                                                        fontFamily = futura,
//                                                        color = greyColor
//                                                    )
//                                                    Text(
//                                                        text = "further details of your order",
//                                                        fontSize = 18.sp,
//                                                        fontFamily = futura,
//                                                        color = greyColor
//                                                    )
//                                                }
//                                            }
//                                        }
//
//                                        2 -> {
//                                            Box(
//                                                modifier = Modifier
//                                                    .fillMaxSize()
//                                                    .offset(x = offsetRight2)
//                                                    .alpha(alpha),
//                                                contentAlignment = Alignment.Center
//                                            ) {
//                                                Column(
//                                                    horizontalAlignment = Alignment.CenterHorizontally,
//                                                    verticalArrangement = Arrangement.Center
//                                                ) {
//                                                    Box(
//                                                        modifier = Modifier
//                                                            .size(100.dp)
//                                                            .border(
//                                                                2.dp,
//                                                                Color.Red,
//                                                                RoundedCornerShape(100)
//                                                            ),
//                                                        contentAlignment = Alignment.Center
//                                                    ) {
//                                                        Icon(
//                                                            imageVector = Icons.Default.Close,
//                                                            contentDescription = null,
//                                                            tint = Color.Red,
//                                                            modifier = Modifier
//                                                                .size(70.dp)
//                                                        )
//                                                    }
//
//                                                    Spacer(modifier = Modifier.height(15.dp))
//
//                                                    Text(
//                                                        text = "Error Placing Order",
//                                                        fontSize = 18.sp,
//                                                        fontFamily = futura,
//                                                        color = greyColor
//                                                    )
//
//                                                    Spacer(modifier = Modifier.height(15.dp))
//
//                                                    Text(
//                                                        text = "Contact us if the issue persists",
//                                                        fontSize = 18.sp,
//                                                        fontFamily = futura,
//                                                        color = greyColor
//                                                    )
////                                                    Text(
////                                                        text = "further details of your order",
////                                                        fontSize = 18.sp,
////                                                        fontFamily = futura,
////                                                        color = greyColor
////                                                    )
//                                                }
//                                            }
//                                        }
//                                    }
//
//
//                                }
//                            }
//
//                            BackHandler {
//                                focusManager.clearFocus()
//                            }
//                        }
//                    }
//                }
//            }
        }
    }

    fun OnIncrementClick(position: Int, cartModelArrayList: ArrayList<cartModel>) {
        var count = cartModelArrayList!![position].count!!.toInt()
        count++
        //cartModelArrayList!![position].count = count.toString() + ""
        dbHandler!!
            .addNewitemIncart(
                cartModelArrayList!![position].name,
                cartModelArrayList!![position].price,
                cartModelArrayList!![position].size,
                cartModelArrayList!![position].count,
                cartModelArrayList!![position].image,
                cartModelArrayList!![position].type, applicationContext
            )
        //  cartItemsAdapter!!.notifyDataSetChanged()
        //  calculate_amount()
        //refreshView()
    }

    fun OnDecrementClick(position: Int, cartModelArrayList: ArrayList<cartModel>) {
        var count = cartModelArrayList!![position].count!!.toInt()
        if (count > 1) {
            count--
            dbHandler!!.RemoveFromCart(
                cartModelArrayList!![position].name, cartModelArrayList!![position].price,
                cartModelArrayList!![position].size, cartModelArrayList!![position].count
            )
        } else if (count == 1) {
            count--
            dbHandler!!.DeleteItem(
                cartModelArrayList!![position].name,
                cartModelArrayList!![position].size
            )
        }
    }

    private fun calculate_amount(
        cartModelList: ArrayList<cartModel>,
        onTotalAmount: (Long, Int) -> Unit
    ) {
        var amt: Long = 0
        var item_count: Int = 0
        if (cartModelList.size > 0) {
            for (i in cartModelList.indices) {
                //Toast.makeText(getApplicationContext(), ""+ cartModelArrayList.get(i).getCount(), Toast.LENGTH_SHORT).show();
                amt =
                    amt + cartModelList!![i].price!!.toLong() * cartModelList!![i].count!!.toLong()
                item_count = item_count + cartModelList!![i].count!!.toInt()
            }
            Log.d("MainCart", amt.toString())
            //  total_amount = "Rs. $amt."
            onTotalAmount.invoke(amt, item_count)
            //  amount!!.text = total_amount
            //  items!!.text = "$item_count items"}
        } else {
            onTotalAmount.invoke(0, 0)
        }
    }

    private fun calculate_totalAmount(
        amt: Long,
        shippingCharges: Long,
        items: Int
    ): Long {
        if (user_pin_code == "781039") {
            return amt;
        } else if (items != 0) {
            return amt + shippingCharges
        } else {
            return amt
        }
    }

    fun clear_cart() {
        dbHandler = DBHandler(this)
        dbHandler!!.Delete_all()
    }

    private fun Volley(map: MutableMap<String, Any?>) {
        val retrofit_class = retrofit.create(Retrofit_Class::class.java)
        val call = retrofit_class.DataToExcel(map)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("post", response.toString())

                if (response.isSuccessful) {
                    orderStatus.value = 1
                    Toast.makeText(
                        applicationContext,
                        "Your order is placed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    orderStatus.value = 2
                }

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Toast.makeText(getApplicationContext(), "error occured code x16389", Toast.LENGTH_SHORT).show();
                orderStatus.value = 2
                Log.d("post", t.toString())
            }
        })
    }

    private fun AddToExcel(order_list: ArrayList<cartModel>, PaymentId: String) {
//        for (i in order_list.indices) {
//            val data: MutableMap<String, Any?> = hashMapOf(
//                "entry.370760790" to order_list[i].size
//            )
//
////            data["entry.1217211808"] = order_list[i].name
////            data["entry.365380202"] = order_list[i].price
////            data["entry.1596700490"] = order_list[i].size
////            data["entry.1406401370"] = order_list[i].type
////            data["entry.461981693"] = Date().toString() + ""
////            data["entry.271845590"] = user_phone
////            data["entry.1853344447"] = user_house
////            data["entry.1389883145"] = user_road
////            data["entry.1369895676"] = user_state
////            data["entry.701619952"] = user_city
////            data["entry.1333764685"] = user_pin_code
////            data["entry.505659208"] = PaymentId
////            data["entry.1800146690"] = "" + total_amount
////            data["entry.1596610928"] = order_list[i].count
////            data["entry.2032856044"] = user_name
////            data["entry.370760790"] = Email
//            Volley(data)
//        }

        val name = user_name
        val address =
            user_house + ", " + user_road + ", " + user_state + ", " + user_city + " " + user_pin_code
        val phone = user_phone

        for (order in order_list) {
            val data: MutableMap<String, Any?> = HashMap()

            data["entry.209006984"] = name
            data["entry.669861633"] = address
            data["entry.946404472"] = phone

            if (order.type!!.contains("t-shirt", ignoreCase = true)) {
                data["entry.1783824976"] = "Regular Fit T-shirt"
                if (order.size == "S") {
                    data["entry.2102505125"] = order.count
                } else if (order.size == "M") {
                    data["entry.2122892541"] = order.count
                } else if (order.size == "L") {
                    data["entry.675675266"] = order.count
                } else if (order.size == "XL") {
                    data["entry.569976089"] = order.count
                } else if (order.size == "XXL") {
                    data["entry.522997590"] = order.count
                }
            } else if (order.type!!.contains("oversized", ignoreCase = true)) {
                data["entry.1783824976"] = "Oversized T-shirt"
                if (order.size == "S") {
                    data["entry.1269969584"] = order.count
                } else if (order.size == "M") {
                    data["entry.1904638183"] = order.count
                } else if (order.size == "L") {
                    data["entry.1260631301"] = order.count
                } else if (order.size == "XL") {
                    data["entry.1446741844"] = order.count
                }
            } else if (order.type!!.contains("sweatshirt", ignoreCase = true)) {
                data["entry.1783824976"] = "Sweatshirt"
                if (order.size == "S") {
                    data["entry.1505860099"] = order.count
                } else if (order.size == "M") {
                    data["entry.1339123637"] = order.count
                } else if (order.size == "L") {
                    data["entry.1180361216"] = order.count
                } else if (order.size == "XL") {
                    data["entry.1748703153"] = order.count
                } else if (order.size == "XXL") {
                    data["entry.768636551"] = order.count
                }
            }

            Volley(data)


        }






        clear_cart()
    }

    private fun AddOrderToFirebase(order_list: ArrayList<cartModel>, PaymentId: String) {
        val email = Objects.requireNonNull<FirebaseUser>(firebaseAuth.getCurrentUser()).email
        val id = firebaseFirestore.collection("USERS").document().id
        val data: MutableMap<String, Any> = HashMap()
        val list = ArrayList<Map<String, Any?>>()
        for (i in order_list.indices) {
            val map: MutableMap<String, Any?> = HashMap()
            map["Name"] = order_list[i].name
            map["Count"] = order_list[i].count
            map["Price"] = order_list[i].price
            map["Size"] = order_list[i].size
            map["Type"] = order_list[i].type
            map["isDelivered"] = false
            map["image"] = order_list[i].image
            map["Timestamp"] = Date()
            list.add(map)
            //total_price += Integer.parseInt(order_list.get(i).getPrice());
        }
        data["orders"] = list
        data["Name"] = user_name
        data["Phone"] = user_phone
        data["House_No"] = user_house
        data["Area"] = user_road
        data["Order ID"] = PaymentId
        data["State"] = user_state
        data["City"] = user_city
        data["Pincode"] = user_pin_code
        data["Method"] = "Online Payment"
        assert(email != null)
        firebaseFirestore.collection("USERS").document(email!!).collection("ORDERS").document(id)
            .set(data).addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    firebaseFirestore.collection("ORDERS").document(id).set(data)
                        .addOnCompleteListener { task1 ->
                            if (task1.isSuccessful) {
                                AddToExcel(arrayList, PaymentId)

                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Some error occurred while placing your orders, try again ! ",
                                    Toast.LENGTH_SHORT
                                ).show()
                                orderStatus.value = 2
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Some error occurred while placing your orders, try again !",
                        Toast.LENGTH_SHORT
                    ).show()
                    orderStatus.value = 2
                    finish()
                }
            }
    }

    private fun startPayment(total_price: Int) {
        try {
//            val razorpay = RazorpayClient("rzp_live_0MqrfaJ3rgG7Bh", "MLVrcuKucdYi9qCSho3ACUB7")
//            val orderRequest = JSONObject()
//            orderRequest.put(
//                "amount",
//                (total_price + shipping_charges) * 100
//            ) // amount in the smallest currency unit
//            orderRequest.put("currency", "INR")

            //Order order = razorpay.Orders.create(orderRequest);
            //Log.d("TAG", order.get("id"));
            //  checkoutOrder(order.get("id"), total_price);
            AddOrderToFirebase(arrayList, randomString(20))

        } catch (e: RazorpayException) {
            Toast.makeText(
                applicationContext,
                "Error: Could not proceed to payment page",
                Toast.LENGTH_SHORT
            ).show()
            println(e.message)
        }
    }
}