package com.alcheringa.alcheringa2022

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.addNewItem
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.removeAnItem
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.ui.theme.*
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

//val events=mutableListOf(
//
//    eventdetail(
//        "JUBIN NAUTIYAL",
//        "Pro Nights",
//        OwnTime(11,9,0),
//        "ONLINE", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fjubin.jpg?alt=media&token=90983a9f-bd0d-483d-b2a8-542c1f1c0acb"
//    ),
//
//    eventdetail(
//        "DJ SNAKE",
//        "Pro Nights",
//        OwnTime(12,12,0),
//        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
//    ),
//    eventdetail(
//        "TAYLOR SWIFT",
//        "Pro Nights",
//        OwnTime(12,14,0),
//        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
//    )
//    ,
//
//    eventdetail(
//        "DJ SNAKE2",
//        "Pro Nights",
//        OwnTime(12,10,0),
//        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
//    ),
//    eventdetail(
//        "TAYLOR SWIFT2",
//        "Pro Nights",
//        OwnTime(12,15,0),
//        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
//    )
//    ,
//    eventdetail(
//        "TAYLOR SWIFT3",
//        "Pro Nights",
//        OwnTime(12,14,30),
//        "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f"
//    )
//
//
//
//)

@Composable
fun Event_card(eventdetail: eventWithLive, viewModelHm: viewModelHome, context: Context, FragmentManager: androidx.fragment.app.FragmentManager) {
    var ScheduleDatabase=ScheduleDatabase(context)
    var okstate= remember{ mutableStateOf(false)}
    var okstatenum= remember{ mutableStateOf(0)}

    viewModelHm.OwnEventsLiveState.forEach{
            data-> if( data.artist==eventdetail.eventdetail.artist){okstate.value=true;okstatenum.value+=1}
    }
    if(okstatenum.value==0){okstate.value=false}
    val animationProgress = remember {Animatable(300f)}
    LaunchedEffect(key1=Unit,block = {
        animationProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    })


    Box( Modifier
        .graphicsLayer(translationY = animationProgress.value)) {
        Text(text =viewModelHm.OwnEventsLiveState.size.toString(), fontSize = 0.sp )

        Card(modifier = Modifier.wrapContentWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = 5.dp) {
            Box(modifier = Modifier.background(blackbg)
                .height(256.dp)
                .width(218.dp)
                .clickable {
                    val frg = Events_Details_Fragment()
                    frg.arguments = bundleOf("Artist" to eventdetail.eventdetail.artist)
                    FragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, frg)
                        .addToBackStack(null)
                        .commit()
                }

            ){
                GlideImage(modifier = Modifier
                    .width(218.dp)
                    .height(256.dp),imageModel = eventdetail.eventdetail.imgurl, contentDescription = "artist", contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                    shimmerParams = ShimmerParams(
                        baseColor = blackbg,
                        highlightColor = Color.LightGray,
                        durationMillis = 350,
                        dropOff = 0.65f,
                        tilt = 20f
                    ),failure = {
                        Box(modifier= Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(), contentAlignment = Alignment.Center) {

                            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.comingsoon))
                            val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
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
//                Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
                Column() {
//
                    if(eventdetail.isLive.value){Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(21.dp)
                            .background(
                                color = colorResource(
                                    id = R.color.ThemeRed
                                )
                            )
                    ) {
                        Text(
                            text = "⬤ LIVE",
                            color = Color.White,
                            modifier = Modifier.align(alignment = Alignment.Center),
                            fontSize = 12.sp
                        )
                    }}

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(11.dp), contentAlignment = Alignment.TopEnd){


                        if( !okstate.value) {

                        Image( modifier = Modifier
                            .width(18.dp)
                            .height(18.dp)
                            .clickable {
                                viewModelHm.OwnEventsWithLive.addNewItem(eventdetail.eventdetail);
                                ScheduleDatabase.addEventsInSchedule(
                                    eventdetail.eventdetail,
                                    context
                                )
                            },
                            painter = painterResource(id = R.drawable.add_icon),
                             contentDescription ="null")
                        }
                        if(okstate.value)
                        {
                            Image( modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .clickable {
                                    Log.d("boxevent", eventdetail.toString())
                                    viewModelHm.OwnEventsWithLive.removeAnItem(eventdetail.eventdetail)
                                    okstate.value = false
                                    ScheduleDatabase.DeleteItem(eventdetail.eventdetail.artist)
                                    Toast
                                        .makeText(
                                            context,
                                            "event removed from schedule",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                },
                                painter = painterResource(id = R.drawable.tickokay),
                                contentDescription ="null", contentScale = ContentScale.FillBounds)
                        }
                    }
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                blackbg
                            ), startY = with(LocalDensity.current) { 70.dp.toPx() }
                        )
                    ))
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp), contentAlignment = Alignment.BottomStart){
                    Column {
                        Text(text = eventdetail.eventdetail.artist, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.W700, fontFamily = clash )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = eventdetail.eventdetail.category, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = clash,fontWeight = FontWeight.W600,fontSize = 14.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${eventdetail.eventdetail.starttime.date} Mar, ${if(eventdetail.eventdetail.starttime.hours>12)"${eventdetail.eventdetail.starttime.hours-12}" else eventdetail.eventdetail.starttime.hours}${if (eventdetail.eventdetail.starttime.min!=0) ":${eventdetail.eventdetail.starttime.min}" else ""} ${if (eventdetail.eventdetail.starttime.hours>=12)"PM" else "AM"} ", style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                        Spacer(modifier = Modifier.height(2.dp))
                        Row {
                            Box(modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)) {
                                Image(
                                        painter = if (eventdetail.eventdetail.mode.contains("ONLINE")) {
                                            painterResource(id = R.drawable.online)
                                        } else {
                                            painterResource(id = R.drawable.onground)
                                        },
                                        contentDescription = null, modifier = Modifier.fillMaxSize(),alignment = Alignment.Center, contentScale =ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = eventdetail.eventdetail.mode,style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                        }
                    }

                }


            }
        }
    }



}





@Composable
fun Event_card_upcoming(eventdetail: eventWithLive,viewModelHm: viewModelHome,context: Context,FragmentManager: androidx.fragment.app.FragmentManager) {
    var ScheduleDatabase=ScheduleDatabase(context)
    var okstate= remember{ mutableStateOf(false)}
    var okstatenum= remember{ mutableStateOf(0)}
    var crtime=viewModelHm.converttoOwnTime(viewModelHm.converttomin(eventdetail.eventdetail.starttime)-viewModelHm.converttomin(viewModelHm.crnttime.value))

    viewModelHm.OwnEventsLiveState.forEach{
            data-> if( data.artist==eventdetail.eventdetail.artist){okstate.value=true;okstatenum.value+=1}
    }
    if(okstatenum.value==0){okstate.value=false}

    val animationProgress = remember {Animatable(300f)}
    LaunchedEffect(key1=Unit,block = {
        animationProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    })




    Box( Modifier
        .graphicsLayer(translationY = animationProgress.value)) {
        Text(text =viewModelHm.OwnEventsLiveState.size.toString(), fontSize = 0.sp )

        Card(modifier = Modifier.wrapContentWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = 5.dp) {
            Box(modifier = Modifier.background(blackbg)
                .height(256.dp)
                .width(218.dp)
                .clickable {
                    val frg = Events_Details_Fragment()
                    frg.arguments = bundleOf("Artist" to eventdetail.eventdetail.artist)
                    FragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, frg)
                        .addToBackStack(null)
                        .commit()
                }

            ){
                GlideImage(modifier = Modifier
                    .width(218.dp)
                    .height(256.dp),imageModel = eventdetail.eventdetail.imgurl, contentDescription = "artist", contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    shimmerParams = ShimmerParams(
                        baseColor = blackbg,
                        highlightColor = Color.LightGray,
                        durationMillis = 350,
                        dropOff = 0.65f,
                        tilt = 20f
                    ),failure = {
                        Box(modifier= Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(), contentAlignment = Alignment.Center) {
                            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.comingsoon))
                            val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
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
//                Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
                Column() {
//
                   Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(21.dp)
                            .background(
                                Color(0xffEC840A)
                            )
                    ) {
                        Text(
                            text =
                            "Starts in${if(crtime.date>0){" ${crtime.date}d"} else ""}${if(crtime.hours>0){" ${crtime.hours}h"} else ""}${if(crtime.min>0){" ${crtime.min}m"} else ""}"
                            ,
                            color = Color.White,
                            modifier = Modifier.align(alignment = Alignment.Center),
                            fontSize = 12.sp
                        )
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(11.dp), contentAlignment = Alignment.TopEnd){


                        if( !okstate.value) {

                            Image( modifier = Modifier
                                .width(18.dp)
                                .height(18.dp)
                                .clickable {
                                    viewModelHm.OwnEventsWithLive.addNewItem(eventdetail.eventdetail);
                                    ScheduleDatabase.addEventsInSchedule(
                                        eventdetail.eventdetail,
                                        context
                                    )
                                },
                                painter = painterResource(id = R.drawable.add_icon),
                                contentDescription ="null")
                        }
                        if(okstate.value)
                        {
                            Image( modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .clickable {
                                    Log.d("boxevent", eventdetail.toString())
                                    viewModelHm.OwnEventsWithLive.removeAnItem(eventdetail.eventdetail)
                                    okstate.value = false
                                    ScheduleDatabase.DeleteItem(eventdetail.eventdetail.artist)
                                    Toast
                                        .makeText(
                                            context,
                                            "event removed from schedule",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                },
                                painter = painterResource(id = R.drawable.tickokay),
                                contentDescription ="null", contentScale = ContentScale.FillBounds)
                        }
                    }
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                blackbg
                            ), startY = with(LocalDensity.current) { 70.dp.toPx() }
                        )
                    ))
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp), contentAlignment = Alignment.BottomStart){
                    Column {
                        Text(text = eventdetail.eventdetail.artist, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.W700, fontFamily = clash )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = eventdetail.eventdetail.category, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = clash,fontWeight = FontWeight.W600,fontSize = 14.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${eventdetail.eventdetail.starttime.date} Mar, ${if(eventdetail.eventdetail.starttime.hours>12)"${eventdetail.eventdetail.starttime.hours-12}" else eventdetail.eventdetail.starttime.hours}${if (eventdetail.eventdetail.starttime.min!=0) ":${eventdetail.eventdetail.starttime.min}" else ""} ${if (eventdetail.eventdetail.starttime.hours>=12)"PM" else "AM"} ", style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                        Spacer(modifier = Modifier.height(2.dp))
                        Row {
                            Box(modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)) {
                                Image(
                                    painter = if (eventdetail.eventdetail.mode.contains("ONLINE")) {
                                        painterResource(id = R.drawable.online)
                                    } else {
                                        painterResource(id = R.drawable.onground)
                                    },
                                    contentDescription = null, modifier = Modifier.fillMaxSize(),alignment = Alignment.Center, contentScale =ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = eventdetail.eventdetail.mode,style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                        }
                    }

                }


            }
        }
    }



}







//
//@Composable
//@Preview
//fun PreviewItem(){
//    Alcheringa2022Theme {
//        LazyRow(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(15.dp)
//        ) {
//            items(events) { dataEach -> Event_card(eventdetail = dataEach) }
//        }
//    }
//}