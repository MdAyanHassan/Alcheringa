package com.example.alcheringa2022

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.example.alcheringa2022.ui.theme.clash
import com.example.alcheringa2022.ui.theme.hk_grotesk
import org.apache.commons.math3.geometry.euclidean.twod.Line


class Add_Address_Activity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    
                    ConstraintLayout(Modifier.fillMaxSize()) {
                        val (btn, topbox)= createRefs();
                        

                        
                    
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp)) {

                        Row(modifier= Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 25.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
                                Image(painter = painterResource(id = R.drawable.back), contentDescription = null,modifier= Modifier
                                    .height(16.dp)
                                    .width(9.dp), contentScale = ContentScale.FillHeight)
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = "Add Address", fontFamily = clash, fontWeight = FontWeight.W800, fontSize = 24.sp, color = Color.White)
                        }




//                    header

                        Row( verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {




                                Box(
                                    modifier = Modifier

                                        .wrapContentSize()
                                        .padding(horizontal = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ellipse_white),
                                        contentDescription = null
                                    )
                                    Text(
                                        text = "1",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W500,
                                        fontFamily = clash,
                                        color = Color.Black
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
                                        .padding(horizontal = 10.dp),
                                    contentAlignment = Alignment.Center
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
                                        color = Color.White
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
                                        .padding(horizontal = 10.dp),
                                    contentAlignment = Alignment.Center
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
                                        color = Color.White
                                    )

                                }

                        }

                Row(Modifier.fillMaxWidth().wrapContentHeight().padding(top=12.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                                text = "Address",
                        fontSize = 14.sp,
                        fontFamily = hk_grotesk,
                        fontWeight = FontWeight.W400,
                        color = colorResource(
                            id = R.color.textgreybehind
                        )
                    )
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "Order Summary",
                        fontSize = 14.sp,
                        fontFamily = hk_grotesk,
                        fontWeight = FontWeight.W400,
                        color = colorResource(
                            id = R.color.textgreybehind
                        )
                    )
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "Payment",
                        fontSize = 14.sp,
                        fontFamily = hk_grotesk,
                        fontWeight = FontWeight.W400,
                        color = colorResource(
                            id = R.color.textgreybehind
                        )
                    )
                }












                        Text( text = "*Mandatory Fields",fontFamily= hk_grotesk, modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                            fontWeight= FontWeight.W400, color = Color.White, textAlign = TextAlign.Left, fontSize = 16.sp)

                        var name by remember { mutableStateOf("") }
                            TextField(textStyle = (TextStyle(color = Color.White,fontWeight = FontWeight.W400,
                                fontFamily = hk_grotesk,
                                fontSize = 18.sp)),
                                modifier = Modifier

                                    .fillMaxWidth()
                                    .height(55.dp), maxLines = 1,
                                colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    backgroundColor = colorResource(id = R.color.textbackground)
                                ),shape= RoundedCornerShape(8.dp),
                                value = name,
                                onValueChange = { name = it }, placeholder = {
                                Text(
                                    text = "Full Name*", fontWeight = FontWeight.W400,color= colorResource(
                                        id = R.color.textfields
                                    ),
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp) })
                                Spacer(modifier = Modifier.height(12.dp))

                        var phone by remember { mutableStateOf("") }
                        TextField(textStyle = (TextStyle(color = Color.White,fontWeight = FontWeight.W400,
                            fontFamily = hk_grotesk,
                            fontSize = 18.sp)),shape= RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp), maxLines = 1,colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                backgroundColor = colorResource(id = R.color.textbackground)
                            ),
                            value = phone, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            onValueChange = { phone = it }, placeholder = {
                                Text(
                                    text = "Phone Number*", fontWeight = FontWeight.W400,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp,color= colorResource(
                                        id = R.color.textfields) )})
                        Spacer(modifier = Modifier.height(24.dp))


                        var house by remember { mutableStateOf("") }
                        TextField(
                            textStyle = (TextStyle(color = Color.White,fontWeight = FontWeight.W400,
                                fontFamily = hk_grotesk,
                                fontSize = 18.sp)),shape= RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp), maxLines = 1,colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                backgroundColor = colorResource(id = R.color.textbackground)
                            ),
                            value = house,
                            onValueChange = { house= it }, placeholder = {
                                Text(
                                    text = "House No, Building Name*", fontWeight = FontWeight.W400,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp,color= colorResource(
                                        id = R.color.textfields) )})
                        Spacer(modifier = Modifier.height(12.dp))

                        var road by remember { mutableStateOf("") }
                        TextField(textStyle = (TextStyle(color = Color.White,fontWeight = FontWeight.W400,
                            fontFamily = hk_grotesk,
                            fontSize = 18.sp)),shape= RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp), colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                backgroundColor = colorResource(id = R.color.textbackground)
                            ),
                            maxLines = 1,
                            value = phone,
                            onValueChange = { phone = it }, placeholder = {
                                Text(
                                    text = "Road Name, Area, Colony*", fontWeight = FontWeight.W400,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp,color= colorResource(
                                        id = R.color.textfields) ) })
                        Spacer(modifier = Modifier.height(12.dp))


                        var city by remember { mutableStateOf("") }
                        var state by remember { mutableStateOf("") }
                        val (rowcon, citycon, statecon) = this@ConstraintLayout.createRefs()

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                            TextField(textStyle = (TextStyle(color = Color.White,fontWeight = FontWeight.W400,
                                fontFamily = hk_grotesk,
                                fontSize = 18.sp)),shape= RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.487f)
                                    .height(55.dp), maxLines = 1,colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    backgroundColor = colorResource(id = R.color.textbackground)
                                ),
                                value = city,
                                onValueChange = { city= it }, placeholder = {
                                    Text(
                                        text = "City*", fontWeight = FontWeight.W400,color= colorResource(
                                            id = R.color.textfields
                                        ),
                                        fontFamily = hk_grotesk,
                                        fontSize = 18.sp) })


                            TextField(textStyle = (TextStyle(color = Color.White,fontWeight = FontWeight.W400,
                                fontFamily = hk_grotesk,
                                fontSize = 18.sp)),shape= RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.9207f)
                                    .height(55.dp), maxLines = 1,colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    backgroundColor = colorResource(id = R.color.textbackground)
                                ),
                                value = state,
                                onValueChange = { state = it }, placeholder = {
                                    Text(
                                        text = "State*", fontWeight = FontWeight.W400,color= colorResource(
                                            id = R.color.textfields
                                        ),
                                        fontFamily = hk_grotesk,
                                        fontSize = 18.sp) })

                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        var pincode by remember { mutableStateOf("") }
                        TextField(textStyle = (TextStyle(color = Color.White,fontWeight = FontWeight.W400,
                            fontFamily = hk_grotesk,
                            fontSize = 18.sp)),shape= RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp), maxLines = 1,
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                backgroundColor = colorResource(id = R.color.textbackground)
                            ),
                            value = pincode, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            onValueChange = { pincode = it }, placeholder = {
                                Text(
                                    text = "Pincode*", fontWeight = FontWeight.W400,
                                    fontFamily = hk_grotesk,
                                    fontSize = 18.sp,color= colorResource(
                                        id = R.color.textfields) )})
                        Spacer(modifier = Modifier.height(24.dp))


                    }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 15.dp)
                            .constrainAs(btn) { bottom.linkTo(parent.bottom) })
                        {
                        Button(
                            onClick = {
                                startActivity(Intent(applicationContext, OrderSummaryActivity::class.java))

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                            shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.btnOrange))
                        ) {
                                Text(text = "Proceed to Checkout", fontFamily = clash,
                                    fontWeight = FontWeight.W600, fontSize = 18.sp, color = Color.White
                                )
                        }}

                }
                }
            }
        }
    @Preview
@Composable
fun header(){
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

            Box(modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 10.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.ellipse_white), contentDescription =null )
                Text(text = "1", fontSize = 18.sp, fontWeight = FontWeight.W500, fontFamily = clash, color = Color.Black)
                
            }
            Box(modifier = Modifier
                .width(66.dp)
                .height(2.dp)
                .background(color = colorResource(id = R.color.LineGray)))
//            Image(painter = painterResource(id = R.drawable.line), contentDescription =null)
            Box(modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 10.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.ellipse_white_transparent), contentDescription =null )
                Text(text = "2", fontSize = 18.sp, fontWeight = FontWeight.W500, fontFamily = clash, color = Color.White)

            }
            Box(modifier = Modifier
                .width(66.dp)
                .height(2.dp)
                .background(color = colorResource(id = R.color.LineGray)))
//            Image(painter = painterResource(id = R.drawable.line), contentDescription =null, modifier = Modifier.width(65.dp))
            Box(modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 10.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.ellipse_white_transparent), contentDescription =null )
                Text(text = "3", fontSize = 18.sp, fontWeight = FontWeight.W500, fontFamily = clash, color = Color.White)

            }


        }
}}