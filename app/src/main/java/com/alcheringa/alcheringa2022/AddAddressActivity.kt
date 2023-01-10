package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alcheringa.alcheringa2022.ui.theme.*
import kotlinx.coroutines.launch


class AddAddressActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class,
        androidx.compose.foundation.ExperimentalFoundationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("address", Context.MODE_PRIVATE)
        setContent {
            // A surface container using the 'background' color from the theme
            Alcheringa2022Theme() {


            Surface(
                modifier = Modifier.fillMaxSize().background(color = colors.background),
                ){
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    val (btn, _) = createRefs()

                    val sp = getSharedPreferences("USER", MODE_PRIVATE)
                    var spName = sp.getString("name", "")
                    if (spName == "") {
                        spName = sharedPreferences.getString("name", "")
                    }

                    var name by remember { mutableStateOf(spName) }
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
                    var road by remember { mutableStateOf(sharedPreferences.getString("road", "")) }
                    var city by remember { mutableStateOf(sharedPreferences.getString("city", "")) }
                    var state by remember {
                        mutableStateOf(
                            sharedPreferences.getString(
                                "state",
                                ""
                            )
                        )
                    }

                    val keyboardController = LocalSoftwareKeyboardController.current
                    val bringIntoViewRequester = remember { BringIntoViewRequester() }
                    val coroutineScope = rememberCoroutineScope()

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 100.dp)
                    ) {
                        val focusManager = LocalFocusManager.current
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.back),tint=colors.secondaryVariant,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(32.dp)
                                    .padding(top = 32.dp, bottom = 32.dp, start = 7.dp, end = 16.dp)
                                    .clickable(
                                        enabled = true,
                                        onClickLabel = "Back Button",
                                        onClick = { finish() })
                            )
                            Text(
                                text = "  Checkout",
                                fontFamily = star_guard,
                                fontWeight = FontWeight.W400,
                                fontSize = 36.sp,
                                color = colors.onBackground,
                                modifier = Modifier.padding(vertical = 25.dp)
                            )
                        }
//                      Header
                        Card(modifier = Modifier
                            .width(388.dp)
                            .height(52.dp)
//                            .background(color = Color(0xffACACAC), shape = RoundedCornerShape(100.dp))
                            ,shape = RoundedCornerShape(100.dp)) {
                            Box(
                                modifier = Modifier
                                    .width(388.dp)
                                    .height(52.dp)
                                    .background(color = Color(0xffACACAC)),
                                contentAlignment = Alignment.Center
                            ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .height(52.dp)
//                                    .padding(start = 6.dp)
                                    .background(color = Color(0xffACACAC))
                            ) {
                                Card(
                                    modifier = Modifier
                                        .width(143.dp)
                                        .height(40.dp)
//                                        .background(color = Color(0xff73D9ED))
                                        .border(
                                            2.dp,
                                            color = Color(0xff0E0E0F),
                                            shape = RoundedCornerShape(100.dp)
                                        ), shape = RoundedCornerShape(100.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .width(143.dp)
                                            .height(40.dp)
                                            .background(color = Color(0xff73D9ED)),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.home),
                                            contentDescription = null
                                        )
                                        Box(
                                            modifier = Modifier
                                                .wrapContentWidth()
                                                .height(19.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = " Add address",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.W600,
                                                fontFamily = aileron,
//                                                color = colors.onBackground
                                            color = Color.Black
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(23.dp))

//                                Box(
//                                    modifier = Modifier
//                                        .width(66.dp)
//                                        .height(2.dp)
//                                        .background(color = colorResource(id = R.color.LineGray))
//                                )
//            Image(painter = painterResource(id = R.drawable.line), contentDescription =null)
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(horizontal = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_frame_order_cinfirmed),
                                        contentDescription = null
                                    )
                                }

                                Spacer(modifier = Modifier.width(84.dp))
//                                Box(
//                                    modifier = Modifier
//                                        .width(66.dp)
//                                        .height(2.dp)
//                                        .background(color = colorResource(id = R.color.LineGray))
//                                )
//            Image(painter = painterResource(id = R.drawable.line), contentDescription =null, modifier = Modifier.width(65.dp))
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(horizontal = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_frame_payment),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        }

//                        Row(
//                            Modifier
//                                .fillMaxWidth()
//                                .wrapContentHeight()
//                                .padding(top = 12.dp),
//                            horizontalArrangement = Arrangement.SpaceEvenly
//                        ) {
//                            Text(
//                                modifier = Modifier.wrapContentSize(),
//                                text = "Add Address",
//                                fontSize = 14.sp,
//                                fontFamily = hk_grotesk,
//                                fontWeight = FontWeight.W400,
//                                color = colorResource(
//                                    id = R.color.textgreybehind
//                                )
//                            )
//                            Text(
//                                modifier = Modifier.wrapContentSize(),
//                                text = "Order Summary",
//                                fontSize = 14.sp,
//                                fontFamily = hk_grotesk,
//                                fontWeight = FontWeight.W400,
//                                color = colorResource(
//                                    id = R.color.textgreybehind
//                                )
//                            )
//                            Text(
//                                modifier = Modifier.wrapContentSize(),
//                                text = "Payment",
//                                fontSize = 14.sp,
//                                fontFamily = hk_grotesk,
//                                fontWeight = FontWeight.W400,
//                                color = colorResource(
//                                    id = R.color.textgreybehind
//                                )
//                            )
//                        }

//                        Text(
//                            text = "*Mandatory Fields",
//                            fontFamily = hk_grotesk,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 24.dp),
//                            fontWeight = FontWeight.W400,
//                            color = colors.onBackground,
//                            textAlign = TextAlign.Left,
//                            fontSize = 16.sp
//                        )

                        TextField(
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            textStyle = (
                                    TextStyle(
                                        color = colors.onBackground, fontWeight = FontWeight.W400,
                                        fontFamily = hk_grotesk,
                                        fontSize = 18.sp
                                    )
                                    ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            maxLines = 1,
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = colors.onBackground,
                               focusedIndicatorColor = colors.onBackground,
                               unfocusedIndicatorColor = colors.onSurface,
                                backgroundColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            value = name!!,
                            onValueChange = { name = it },
                            placeholder = {
                                Text(
                                    text = "Full Name*",
                                    fontWeight = FontWeight.W400,
                                    color = colors.onSurface,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp
                                )
                            })
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            textStyle = (
                                    TextStyle(
                                        color = colors.onBackground, fontWeight = FontWeight.W400,
                                        fontFamily = hk_grotesk,
                                        fontSize = 18.sp
                                    )
                                    ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            maxLines = 1,
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = colors.onBackground,
                               focusedIndicatorColor = colors.onBackground,
                               unfocusedIndicatorColor = colors.onSurface,
                                backgroundColor = Color.Transparent
                            ),
                            value = phone!!,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            onValueChange = {
                                if (it.length <= 10) {
                                    phone = it
                                }
                            },
                            placeholder = {
                                Text(
                                    text = "Phone Number*", fontWeight = FontWeight.W400,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp, color = colors.onSurface
                                )
                            },
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            textStyle = (TextStyle(
                                color = colors.onBackground, fontWeight = FontWeight.W400,
                                fontFamily = hk_grotesk,
                                fontSize = 18.sp
                            )),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            maxLines = 1,
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = colors.onBackground,
                               focusedIndicatorColor = colors.onBackground,
                               unfocusedIndicatorColor = colors.onSurface,
                                backgroundColor = Color.Transparent
                            ),
                            value = house!!,
                            onValueChange = { house = it },
                            placeholder = {
                                Text(
                                    text = "House No, Building Name*", fontWeight = FontWeight.W400,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp, color = colors.onSurface
                                )
                            })
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            textStyle = (TextStyle(
                                color = colors.onBackground, fontWeight = FontWeight.W400,
                                fontFamily = hk_grotesk,
                                fontSize = 18.sp
                            )), shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = colors.onBackground,
                               focusedIndicatorColor = colors.onBackground,
                               unfocusedIndicatorColor = colors.onSurface,
                                backgroundColor = Color.Transparent
                            ),
                            maxLines = 1,
                            singleLine = true,
                            value = road!!,
                            onValueChange = { road = it },
                            placeholder = {
                                Text(
                                    text = "Road Name, Area, Colony*", fontWeight = FontWeight.W400,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp, color = colors.onSurface
                                )
                            })
                        Spacer(modifier = Modifier.height(16.dp))


                        //var city by remember { mutableStateOf("") }
                        //var state by remember { mutableStateOf("") }
                        //val (rowcon, citycon, statecon) = this@ConstraintLayout.createRefs()

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            TextField(
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Right) }
                                ),
                                textStyle = (TextStyle(
                                    color = colors.onBackground, fontWeight = FontWeight.W400,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp
                                )),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.487f)
                                    .onFocusEvent { focusState ->
                                        if (focusState.isFocused) {
                                            coroutineScope.launch {
                                                bringIntoViewRequester.bringIntoView()
                                            }
                                        }
                                    },
                                maxLines = 1,
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = colors.onBackground,
                                   focusedIndicatorColor = colors.onBackground,
                                   unfocusedIndicatorColor = colors.onSurface,
                                    backgroundColor = Color.Transparent
                                ),
                                value = city!!,
                                onValueChange = { city = it },
                                placeholder = {
                                    Text(
                                        text = "City*",
                                        fontWeight = FontWeight.W400,
                                        color = colorResource(
                                            id = R.color.textfields
                                        ),
                                        fontFamily = hk_grotesk,
                                        fontSize = 18.sp
                                    )
                                })


                            TextField(
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                textStyle = (
                                        TextStyle(
                                            color = colors.onBackground, fontWeight = FontWeight.W400,
                                            fontFamily = hk_grotesk,
                                            fontSize = 18.sp
                                        )
                                        ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.9207f)
                                    .onFocusEvent { focusState ->
                                        if (focusState.isFocused) {
                                            coroutineScope.launch {
                                                bringIntoViewRequester.bringIntoView()
                                            }
                                        }
                                    },
                                maxLines = 1,
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = colors.onBackground,
                                   focusedIndicatorColor = colors.onBackground,
                                   unfocusedIndicatorColor = colors.onSurface,
                                    backgroundColor = Color.Transparent
                                ),
                                value = state!!,
                                onValueChange = { state = it },
                                placeholder = {
                                    Text(
                                        text = "State*",
                                        fontWeight = FontWeight.W400,
                                        color = colorResource(
                                            id = R.color.textfields
                                        ),
                                        fontFamily = hk_grotesk,
                                        fontSize = 18.sp
                                    )
                                })

                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            textStyle = (
                                    TextStyle(
                                        color = colors.onBackground, fontWeight = FontWeight.W400,
                                        fontFamily = hk_grotesk,
                                        fontSize = 18.sp
                                    )
                                    ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth(),
                            maxLines = 1,
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = colors.onBackground,
                               focusedIndicatorColor = colors.onBackground,
                               unfocusedIndicatorColor = colors.onSurface,
                                backgroundColor = Color.Transparent
                            ),
                            value = pincode!!,
                            onValueChange = { pincode = it },
                            placeholder = {
                                Text(
                                    text = "Pincode*", fontWeight = FontWeight.W400,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp, color = colors.onSurface
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Phone
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { keyboardController?.hide() }
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))


                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                        .constrainAs(btn) { bottom.linkTo(parent.bottom) })
                    {
                        Button(
                            onClick = {

                                val patternPhone = Regex("^[1-9][0-9]{9}$")
                                var isPhoneValid = patternPhone.containsMatchIn(phone!!)

                                val patternPinCode = Regex("^[0-9]{6}$")
                                var isPinCodeValid = patternPinCode.containsMatchIn(pincode!!)

                                when {
                                    name!!.isEmpty() -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Please enter your name",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    !isPhoneValid -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Please enter a 10 digit phone number",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    house!!.isEmpty() -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Please enter your House No and Building Name",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    road!!.isEmpty() -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Please enter your Road Name, Area and Colony",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    city!!.isEmpty() -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Please enter your City",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    state!!.isEmpty() -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Please enter your State",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    (!isPinCodeValid) -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Please enter a 6 digit numeric pincode",
                                            Toast.LENGTH_SHORT
                                        ).show()
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

                                        val intent = Intent(
                                            applicationContext,
                                            OrderSummaryActivity::class.java
                                        )
                                        intent.putExtra("name", name)
                                        intent.putExtra("phone", phone)
                                        intent.putExtra("house", house)
                                        intent.putExtra("road", road)
                                        intent.putExtra("city", city)
                                        intent.putExtra("state", state)
                                        intent.putExtra("pincode", pincode)
                                        startActivity(intent)
                                    }


                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = blu)
                        ) {
                            Row(modifier=
                            Modifier
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Proceed",
                                    fontFamily = aileron,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 22.sp,
                                    color = Color.Black
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_right),
                                    contentDescription = "arrowright",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
        }
    }

    @Preview
    @Composable
    fun header() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 10.dp), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ellipse_white),
                    contentDescription = null
                )
                Text(
                    text = "1",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = aileron,
                    color = colors.onBackground
                )

            }
            Box(
                modifier = Modifier
                    .width(66.dp)
                    .height(2.dp)
                    .background(color = colorResource(id = R.color.LineGray))
            )
//            Image(painter = painterResource(id = R.drawable.line), contentDescription =null)
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 10.dp), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ellipse_white_transparent),
                    contentDescription = null
                )
                Text(
                    text = "2",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = clash,
                    color = colors.onBackground
                )

            }
            Box(
                modifier = Modifier
                    .width(66.dp)
                    .height(2.dp)
                    .background(color = colorResource(id = R.color.LineGray))
            )
//            Image(painter = painterResource(id = R.drawable.line), contentDescription =null, modifier = Modifier.width(65.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 10.dp), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ellipse_white_transparent),
                    contentDescription = null
                )
                Text(
                    text = "3",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = clash,
                    color = colors.onBackground
                )

            }
        }
    }
}