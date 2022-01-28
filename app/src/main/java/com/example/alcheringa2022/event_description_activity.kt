package com.example.alcheringa2022

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.State
import com.example.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.example.alcheringa2022.ui.theme.clash
import com.example.alcheringa2022.ui.theme.hk_grotesk
import com.example.alcheringa2022.ui.theme.orangeText
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

class event_description_activity : ComponentActivity() {

    val ev=eventdetail(
                        "JUBIN NAUTIYAL2",
                    "Pro Nights",
                    OwnTime(11,9,0),
                    "ONLINE", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fjubin.jpg?alt=media&token=90983a9f-bd0d-483d-b2a8-542c1f1c0acb"
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xff00010C))
                    .verticalScroll(rememberScrollState())) {
                Defaultimg(eventWithLive = eventWithLive(ev))
                Bottomview( eventWithLive = eventWithLive(ev))
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}




@Composable
fun Defaultimg(eventWithLive: eventWithLive) {
 
        Box(
            modifier = Modifier
                .height(473.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                imageModel = eventWithLive.eventdetail.imgurl,
                contentDescription = "artist",
                modifier= Modifier
                    .fillMaxWidth()
                    .height(473.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                shimmerParams = ShimmerParams(
                    baseColor = Color.Black,
                    highlightColor = Color.LightGray,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),failure = {
                    Box(modifier= Modifier
                        .fillMaxWidth()
                        .height(473.dp), contentAlignment = Alignment.Center) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_sad_svgrepo_com
                                ),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Image Request Failed",
                                style = TextStyle(
                                    color = Color(0xFF747474),
                                    fontFamily = hk_grotesk,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }

                }


            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), startY = 100f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp), contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = eventWithLive.eventdetail.artist,
                        color = Color.White,
                        fontWeight = FontWeight.W700,
                        fontSize = 78.sp,
                        fontFamily = FontFamily(Font(R.font.morganitemedium))
                    )
                    Spacer(modifier = Modifier.height(11.dp))
                    Text(
                        text = eventWithLive.eventdetail.category,
                        style = TextStyle(
                            color = colorResource(id = R.color.textGray),
                            fontFamily = clash,
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(11.dp))
                    Box(modifier = Modifier
                        .width(68.dp)
                        .height(21.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xffC80915)), contentAlignment = Alignment.Center){
                        Text(
                            text = "⬤ LIVE",
                            color = Color.White,
                            fontFamily = hk_grotesk, fontWeight = FontWeight.W500,
                            fontSize = 12.sp
                        )
                    }


                }

            }


        }
    }
@Composable
fun Bottomview(eventWithLive:eventWithLive){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),Arrangement.Start)
        {
            Image(
                painter = if (eventWithLive.eventdetail.mode.contains("ONLINE")) {
                    painterResource(id = R.drawable.online)
                } else {
                    painterResource(id = R.drawable.onground)
                },
                contentDescription = null,
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop

            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = eventWithLive.eventdetail.mode,
                style = TextStyle(
                    color = colorResource(id = R.color.textGray),
                    fontFamily = hk_grotesk,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),Arrangement.Start)
        {
            Image(
                painter = painterResource(id = R.drawable.schedule),
                contentDescription = null,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
            text = "${eventWithLive.eventdetail.starttime.date} Feb, ${if (eventWithLive.eventdetail.starttime.hours > 12) "${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"}",
            style = TextStyle(
                color = colorResource(id = R.color.textGray),
                fontFamily = clash,
                fontWeight = FontWeight.W500,
                fontSize = 20.sp
            )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text ="dfdfafdadfdfsafdddfdfadfdsdfsdffdasdfdfdfasasdfdfsdaffdasdfsdfdfdscfdsdf" ,
            fontFamily = hk_grotesk,
            fontWeight = FontWeight.W600,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(36.dp))
        Button(onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth()
                .height(55.dp), shape = RoundedCornerShape(18.dp), colors = ButtonDefaults.buttonColors(orangeText)
        ) {
            Text(text = "Join Event", fontSize = 18.sp, fontWeight = FontWeight.W600, fontFamily = clash, color = Color.White)

        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth()
                .height(55.dp), shape = RoundedCornerShape(18.dp), colors = ButtonDefaults.buttonColors(Color(0xff2B2B2B))
        ) {
            Text(text = "Add to My Schedule", fontSize = 18.sp, fontWeight = FontWeight.W600, fontFamily = clash, color = Color.White)
        }

        
        


    }
}
