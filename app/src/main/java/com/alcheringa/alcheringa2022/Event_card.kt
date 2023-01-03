package com.alcheringa.alcheringa2022

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.*
import com.alcheringa.alcheringa2022.ui.theme.*
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage



val events=mutableListOf(

    eventdetail(
        "JUBIN NAUTIYAL",
        "Pro Nights",
        OwnTime(11,9,0),
        "ONLINE", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fjubin.jpg?alt=media&token=90983a9f-bd0d-483d-b2a8-542c1f1c0acb"
    ),

    eventdetail(
        "DJ SNAKE",
        "Pro Nights",
        OwnTime(12,12,0),
        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
    ),
    eventdetail(
        "TAYLOR SWIFT",
        "Pro Nights",
        OwnTime(12,14,0),
        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
    )
    ,

    eventdetail(
        "DJ SNAKE2",
        "Pro Nights",
        OwnTime(12,10,0),
        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
    ),
    eventdetail(
        "TAYLOR SWIFT2",
        "Pro Nights",
        OwnTime(12,15,0),
        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
    )
    ,
    eventdetail(
        "TAYLOR SWIFT3",
        "Pro Nights",
        OwnTime(12,14,30),
        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f"
    )



)

fun Modifier.coloredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = composed {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparent = color.copy(alpha= 0f).toArgb()

    this.drawBehind {

        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparent

            frameworkPaint.setShadowLayer(
                shadowRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
        }
    }
}

@Composable
fun Event_card(eventdetail: eventWithLive, viewModelHm: viewModelHome, context: Context, Fragment : Fragment, FragmentManager: androidx.fragment.app.FragmentManager , a : Int) {
    var ScheduleDatabase=ScheduleDatabase(context)
    var okstate= remember{ mutableStateOf(false)}
    var okstatenum= remember{ mutableStateOf(0)}
    var M = Modifier.wrapContentWidth()
    val isdark= isSystemInDarkTheme()
    val animationProgress = remember {Animatable(300f)}
    LaunchedEffect(key1=Unit,block = {
        animationProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    })

    if (eventdetail.isLive.value){
        M = Modifier.clip(RoundedCornerShape(16.dp)).background(colors.background)
            .wrapContentWidth()
            .border(
                3.dp,
                color = liveGreen, RoundedCornerShape(16.dp)
            )
    }
    else{
        M = Modifier.clip(RoundedCornerShape(16.dp)).background(colors.background)
            .wrapContentWidth()
            .border(
                1.5f.dp,
                color = colors.onBackground, RoundedCornerShape(16.dp)
            )
    }

    LaunchedEffect(key1=Unit,block = {
        okstatenum.value=0
        viewModelHm.OwnEventsLiveState.forEach{
                data-> if( data.artist==eventdetail.eventdetail.artist){okstatenum.value+=1}
        }
        if(okstatenum.value==0){okstate.value=false}else{okstate.value=true}
    })
    val bm= if(isSystemInDarkTheme())Modifier.background(colors.background)
        .graphicsLayer(translationY = animationProgress.value)
        .width(220.dp)
    else Modifier.background(colors.background).graphicsLayer(translationY = animationProgress.value)
        .coloredShadow(colors.onBackground, 0.01f, 16.dp, 1.dp, 20.dp, 0.dp)
        .coloredShadow(colors.onBackground, 0.06f, 16.dp, 1.dp, 12.dp, 0.dp)
        .coloredShadow(colors.onBackground, 0.24f, 16.dp, 1.dp, 4.dp, 0.dp)

        .width(220.dp)
    Box(
        modifier=bm)
    {
        Text(text =viewModelHm.OwnEventsLiveState.size.toString(), fontSize = 0.sp )

        Card(modifier = M.padding(0.dp),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            backgroundColor = colors.background,
            elevation = 0.dp


            ){

            Box(modifier = Modifier
                .clickable {
                    val frg = Events_Details_Fragment()
                    val arguments = bundleOf("Artist" to eventdetail.eventdetail.artist)
//                    FragmentManager
//                        .beginTransaction()
//                        .replace(R.id.fragmentContainerView, frg)
//                        .commit()
                    NavHostFragment
                        .findNavController(Fragment)
                        .navigate(a, arguments);

                }

            ){
                Column {
                    Box(
                        modifier = Modifier
                            .width(220.dp)
                            .height(182.dp),
                    ) {
                        Card(shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) {
                            GlideImage(modifier = Modifier,
                                imageModel = eventdetail.eventdetail.imgurl,
                                contentDescription = "artist",
                                contentScale = ContentScale.Crop,

                                alignment = Alignment.Center,
                                shimmerParams = ShimmerParams(
                                    baseColor = blackbg,
                                    highlightColor = Color.LightGray,
                                    durationMillis = 350,
                                    dropOff = 0.65f,
                                    tilt = 20f
                                ),
                                failure = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(), contentAlignment = Alignment.Center
                                    ) {

                                        val composition by rememberLottieComposition(
                                            LottieCompositionSpec.RawRes(
                                                R.raw.comingsoon
                                            )
                                        )
                                        val progress by animateLottieCompositionAsState(
                                            composition,
                                            iterations = LottieConstants.IterateForever
                                        )
                                        LottieAnimation(
                                            composition,
                                            progress,
                                            modifier = Modifier.fillMaxHeight()
                                        )
                                        //                            Column(
                                        //                                Modifier
                                        //                                    .fillMaxWidth()
                                        //                                    .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                                        //                                Image(
                                        //                                    modifier = Modifier
                                        //                                        .width(60.dp)
                                        //                                        .height(60.dp),
                                        //                                    painter = painterResource(
                                        //                                        id = R.drawable.ic_sad_svgrepo_com
                                        //                                    ),
                                        //                                    contentDescription = null
                                        //                                )
                                        //                                Spacer(modifier = Modifier.height(10.dp))
                                        //                                Text(
                                        //                                    text = "Image Request Failed",
                                        //                                    style = TextStyle(
                                        //                                        color = Color(0xFF747474),
                                        //                                        fontFamily = hk_grotesk,
                                        //                                        fontWeight = FontWeight.Normal,
                                        //                                        fontSize = 12.sp
                                        //                                    )
                                        //                                )
                                        //                            }
                                    }

                                }
                            )
                        }
                        Row(modifier = Modifier.padding(12.dp)) {
//
                            if (eventdetail.isLive.value) {
                                Card(modifier = Modifier.wrapContentWidth(),
                                    shape = RoundedCornerShape(8.dp),) {
                                    Box(
                                        modifier = Modifier
                                            .width(52.dp)
                                            .height(23.dp)
                                            .background(
                                                color = liveGreen
                                            ), contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "⬤ LIVE",
                                            color = Color.White,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
//                            .padding(11.dp)
                                ,contentAlignment = Alignment.TopEnd
                            ) {


                                if (true) {
                                    if (!okstate.value) {

                                        Image(
                                            modifier = Modifier
                                                .width(30.dp)
                                                .height(30.dp)
                                                .clickable {
                                                    okstate.value = true
                                                    viewModelHm.OwnEventsWithLive.addNewItem(
                                                        eventdetail.eventdetail
                                                    );
                                                    ScheduleDatabase.addEventsInSchedule(
                                                        eventdetail.eventdetail,
                                                        context
                                                    )
                                                    okstate.value = true
                                                },
                                            painter = if(isSystemInDarkTheme()){painterResource(id = R.drawable.circle_plus_dark)}
                                            else{painterResource(id = R.drawable.circle_plus)},
                                            contentDescription = "null"
                                        )
                                    }
                                    if (okstate.value) {
                                        Image(
                                            modifier = Modifier
                                                .width(30.dp)
                                                .height(30.dp)
                                                .clickable {
                                                    Log.d("boxevent", eventdetail.toString())
// about the tick and plus symbol
                                                    viewModelHm.OwnEventsWithLive.removeAnItem(
                                                        eventdetail.eventdetail
                                                    )

                                                    ScheduleDatabase.DeleteItem(eventdetail.eventdetail.artist)
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "event removed from schedule",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                    okstate.value = false
                                                },
                                            painter = if(isSystemInDarkTheme()){painterResource(id = R.drawable.circle_check_dark)}
                                            else{painterResource(id = R.drawable.circle_check_light)},
                                            contentDescription = "null",
                                            contentScale = ContentScale.FillBounds
                                        )
                                    }
                                } else if (eventdetail.eventdetail.mode.replace("\\s".toRegex(), "")
                                        .uppercase() == "Offline".uppercase() && eventdetail.eventdetail.category.replace(
                                        "\\s".toRegex(),
                                        ""
                                    ) != "Competitions".uppercase()
                                ) {
                                    if (!okstate.value) {

                                        Image(
                                            modifier = Modifier
                                                .width(30.dp)
                                                .height(30.dp)
                                                .clickable {
                                                    okstate.value = true
                                                    viewModelHm.OwnEventsWithLive.addNewItem(
                                                        eventdetail.eventdetail
                                                    );
                                                    ScheduleDatabase.addEventsInSchedule(
                                                        eventdetail.eventdetail,
                                                        context
                                                    )
                                                    okstate.value = true
                                                },
                                            painter = if(isSystemInDarkTheme()){painterResource(id = R.drawable.circle_plus_dark)}
                                            else{painterResource(id = R.drawable.circle_plus)},
                                            contentDescription = "null"
                                        )
                                    }
                                    if (okstate.value) {
                                        Image(
                                            modifier = Modifier
                                                .width(30.dp)
                                                .height(30.dp)
                                                .clickable {
                                                    Log.d("boxevent", eventdetail.toString())

                                                    viewModelHm.OwnEventsWithLive.removeAnItem(
                                                        eventdetail.eventdetail
                                                    )

                                                    ScheduleDatabase.DeleteItem(eventdetail.eventdetail.artist)
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "event removed from schedule",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                    okstate.value = false
                                                },
                                            painter = if(isSystemInDarkTheme()){painterResource(id = R.drawable.circle_check_dark)}
                                            else{painterResource(id = R.drawable.circle_check_light)},
                                            contentDescription = "null",
                                            contentScale = ContentScale.FillBounds
                                        )
                                    }
                                }
                            }
                        }
                    }

//                Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom
                    ){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top=12.dp, bottom=16.dp,start=16.dp,end=0.dp), contentAlignment = Alignment.BottomStart){
                            Column {
                                MarqueeText(text = eventdetail.eventdetail.artist, color = colors.onBackground, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, fontFamily = aileron, gradientEdgeColor = Color.Transparent)
                                Spacer(modifier = Modifier.height(8.dp))
    //                        Text(text = eventdetail.eventdetail.category, style = TextStyle(color = Color.Black,fontFamily = clash,fontWeight = FontWeight.W600,fontSize = 14.sp))
    //                            Text(text = "Time  |   Loc", style = TextStyle(color = Color.Black,fontFamily = aileron,fontWeight = FontWeight.Normal,fontSize = 12.sp))

                                    MarqueeText(
                                        text = "${eventdetail.eventdetail.starttime.date} Mar, ${if (eventdetail.eventdetail.starttime.hours > 12) "${eventdetail.eventdetail.starttime.hours - 12}" else eventdetail.eventdetail.starttime.hours}${if (eventdetail.eventdetail.starttime.min != 0) ":${eventdetail.eventdetail.starttime.min}" else ""} ${if (eventdetail.eventdetail.starttime.hours >= 12) "PM" else "AM"}" + "   |   ${eventdetail.eventdetail.venue}",
                                        style = TextStyle(
                                            color = colors.onBackground,
                                            fontFamily = aileron,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                        ),
                                        gradientEdgeColor = Color.Transparent
                                    )


    //                        if(eventdetail.eventdetail.stream) {
    //                            Text(
    //                                text = "${eventdetail.eventdetail.starttime.date} Mar, ${if (eventdetail.eventdetail.starttime.hours > 12) "${eventdetail.eventdetail.starttime.hours - 12}" else eventdetail.eventdetail.starttime.hours}${if (eventdetail.eventdetail.starttime.min != 0) ":${eventdetail.eventdetail.starttime.min}" else ""} ${if (eventdetail.eventdetail.starttime.hours >= 12) "PM" else "AM"} ",
    //                                style = TextStyle(
    //                                    color = Color.Black,
    //                                    fontFamily = hk_grotesk,
    //                                    fontWeight = FontWeight.Normal,
    //                                    fontSize = 14.sp
    //                                )
    //                            )
    //                        }else if(!eventdetail.eventdetail.stream){
    //                            if(eventdetail.eventdetail.mode.replace("\\s".toRegex(), "").uppercase()=="OFFLINE" && eventdetail.eventdetail.category.replace("\\s".toRegex(), "").uppercase()!="Competitions".uppercase()){
    //                                Text(
    //                                    text = "${eventdetail.eventdetail.starttime.date} Mar, ${if (eventdetail.eventdetail.starttime.hours > 12) "${eventdetail.eventdetail.starttime.hours - 12}" else eventdetail.eventdetail.starttime.hours}${if (eventdetail.eventdetail.starttime.min != 0) ":${eventdetail.eventdetail.starttime.min}" else ""} ${if (eventdetail.eventdetail.starttime.hours >= 12) "PM" else "AM"} ",
    //                                    style = TextStyle(
    //                                        color = Color.Black,
    //                                        fontFamily = hk_grotesk,
    //                                        fontWeight = FontWeight.Normal,
    //                                        fontSize = 14.sp
    //                                    )
    //                                )
    //                            }
    //                            else{
    //                                Spacer(modifier = Modifier.height(16.dp))
    //                            }
    //                        }
    //                        else{
    //                            Spacer(modifier = Modifier.height(16.dp))
    //                        }
    //                        Spacer(modifier = Modifier.height(4.dp))
    //                        Row {
    //                            Box(modifier = Modifier
    //                                .height(20.dp)
    //                                .width(20.dp)) {
    //                                Image(
    //                                    painter = if (eventdetail.eventdetail.mode.uppercase().contains("ONLINE")) {
    //                                        painterResource(id = R.drawable.online)
    //                                    } else {
    //                                        painterResource(id = R.drawable.onground)
    //                                    },
    //                                    contentDescription = null, modifier = Modifier.fillMaxSize(),alignment = Alignment.Center, contentScale =ContentScale.Crop
    //                                )
    //                            }
    //                            Spacer(modifier = Modifier.width(4.dp))
    //                            Text(text = eventdetail.eventdetail.mode.uppercase(),style = TextStyle(color = Color.Black,fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
    //                        }
                            }
                        }
                    }
                }
            }
        }
    }
}












//@Composable
//@Preview
//fun PreviewItem(){
//    Alcheringa2022Theme {
//        LazyRow(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp),
//            contentPadding = PaddingValues(horizontal = 20.dp)
//        ) {
//            items(events)
//            { dataEach -> Event_card(
//                eventdetail = eventWithLive(dataEach))}
//    }
//}