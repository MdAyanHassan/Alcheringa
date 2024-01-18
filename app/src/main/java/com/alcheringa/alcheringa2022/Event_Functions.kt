package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.alpha
import androidx.fragment.app.Fragment
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.addNewItem
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.removeAnItem
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.ui.theme.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun Event_card_Scaffold(
    eventdetail: eventWithLive,
    viewModelHm: viewModelHome,
    context: Context,
    artist: String,
    onCardClick: () -> Unit
) {
    var ScheduleDatabase = ScheduleDatabase(context)
    var okstate = remember { mutableStateOf(false) }
    var okstatenum = remember { mutableStateOf(0) }
    var M = Modifier.wrapContentWidth()
    val isdark = isSystemInDarkTheme()
    val animationProgress = remember { Animatable(300f) }
    LaunchedEffect(key1 = Unit, block = {
        animationProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    })

    if (eventdetail.isLive.value) {
        M = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(colors.background)
            .wrapContentWidth()
            .border(
                2.dp,
                color = lighterGreen,
                RoundedCornerShape(4.dp)
            )
    } else {
        M = Modifier
            .clip(RoundedCornerShape(4.dp)) // made changes 16.dp to 7.dp
            .background(colors.background)
            .wrapContentWidth()
            .border(
                1.dp,
                color = colors.primary, RoundedCornerShape(4.dp)// made changes 16.dp to 7.dp
            )
    }

    LaunchedEffect(key1 = Unit, block = {
        okstatenum.value = 0
        viewModelHm.OwnEventsLiveState.forEach { data ->
            if (data.artist == eventdetail.eventdetail.artist) {
                okstatenum.value += 1
            }
        }
        if (okstatenum.value == 0) {
            okstate.value = false
        } else {
            okstate.value = true
        }
    })


    val bm = if (isSystemInDarkTheme()) Modifier
        .background(colors.background)
        .graphicsLayer(translationY = animationProgress.value)
        .width(231.dp)

         else Modifier
        .background(colors.background)
        .graphicsLayer(translationY = animationProgress.value)

        .coloredShadow(colors.onBackground, 0.01f, 16.dp, 1.dp, 20.dp, 0.dp)
        .coloredShadow(colors.onBackground, 0.06f, 16.dp, 1.dp, 12.dp, 0.dp)
        .coloredShadow(colors.onBackground, 0.24f, 16.dp, 1.dp, 4.dp, 0.dp)

        .width(231.dp)

    Box(modifier = bm)

    {
        Text(text = viewModelHm.OwnEventsLiveState.size.toString(), fontSize = 0.sp)


        Card(
            modifier = M.padding(0.dp),
            shape = RoundedCornerShape(4.dp),
            backgroundColor = colors.background,
            elevation = 0.dp


        ) {

            Box(
                modifier = Modifier
                    .clickable(

                        onClick = onCardClick,
                        enabled = true,
                    )
//                {
//                    val frg = Events_Details_Fragment()
//
//
//                    val arguments = bundleOf("Artist" to eventdetail.eventdetail.artist)
////                    FragmentManager
////                        .beginTransaction()
////                        .replace(R.id.fragmentContainerView, frg)
////                        .commit()
//                    NavHostFragment
//                        .findNavController(Fragment)
//                        .navigate(a, arguments);
//
//                }

            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .width(231.dp)
                            .height(194.dp), // changed from 182 to 178
                    ) {
                        Card(shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)) {
                           GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)},
                               modifier = Modifier,
                                imageModel = eventdetail.eventdetail.imgurl,
                                contentDescription = "artist",
                                contentScale = ContentScale.Crop,

                                alignment = Alignment.Center,
                                shimmerParams = ShimmerParams(
                                    baseColor = if(isSystemInDarkTheme()) black else highWhite,
                                    highlightColor = if(isSystemInDarkTheme()) highBlack else white,
                                    durationMillis = 1500,
                                    dropOff = 1f,
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
                                                if (isSystemInDarkTheme())R.raw.comingsoondark else R.raw.comingsoonlight
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
                         /*  if (eventdetail.isLive.value) {
                                Card(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                    ,
                                    shape = RoundedCornerShape(8.dp),
                                    backgroundColor =
                                        if (isSystemInDarkTheme()) {
                                            liveGreenDark
                                        } else {
                                            liveGreen
                                        }
                                    ,
                                )
                            {
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
//
                                            , contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Live",
                                            color = colors.background,
                                            fontSize = 16.sp,
                                            fontFamily = aileron,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }*/

                            // start
//                            if (true) {
//
//                                    Box(
//                                        modifier = Modifier
//                                            .wrapContentSize()
//                                            .padding(horizontal = 6.dp, vertical = 2.dp)
//
////
//                                        , contentAlignment = Alignment.Center
//
//                                    ) {  Column(verticalArrangement = Arrangement.Bottom) {
//                                        Text(
//                                            text = "Live",
//                                            color = if (isSystemInDarkTheme()) {
//                                                liveGreenDark
//                                            } else {
//                                                liveGreen
//                                            },
//                                            fontSize = 16.sp,
//                                            fontFamily = aileron,
//                                            fontWeight = FontWeight.Normal
//                                        )
//                                    }
//                                    }
//                                }

                            /// end
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
//                            .padding(11.dp)
                                , contentAlignment = Alignment.TopEnd
                            ) {


                                if (true) {
                                    if (!okstate.value) {

                                        Image(
                                            modifier = Modifier
                                                .width(18.dp)
                                                .height(16.dp)
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
                                            painter = if (isSystemInDarkTheme()) {
                                                painterResource(id = R.drawable.light_heart)
                                            } else {
                                                painterResource(id = R.drawable.blheart)
                                            },
                                            contentDescription = "null"
                                        )
                                    }
                                    if (okstate.value) {
                                        Image(
                                            modifier = Modifier
                                                .width(18.dp)
                                                .height(16.dp)
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
                                            painter = if (isSystemInDarkTheme()) {
                                                painterResource(id = R.drawable.heart)
                                            } else {
                                                painterResource(id = R.drawable.heart)
                                            },
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
                                                .width(18.dp)
                                                .height(16.dp)
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
                                            painter = if (isSystemInDarkTheme()) {
                                                painterResource(id = R.drawable.light_heart)
                                            } else {
                                                painterResource(id = R.drawable.blheart)
                                            },
                                            contentDescription = "null"
                                        )
                                    }
                                    if (okstate.value) {
                                        Image(
                                            modifier = Modifier
                                                .width(18.dp)
                                                .height(16.dp)
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
                                            painter = if (isSystemInDarkTheme()) {
                                                painterResource(id = R.drawable.heart)
                                            } else {
                                                painterResource(id = R.drawable.heart)
                                            },
                                            contentDescription = "null",
                                            contentScale = ContentScale.FillBounds
                                        )
                                    }
                                }
                            }
                        }
                    }

//                Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.onBackground)
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                        ) {
                            MarqueeText(
                                text = eventdetail.eventdetail.artist,
                                color = colors.background,
                                fontSize = 18.sp,
                                fontFamily = futura,
                                gradientEdgeColor = Color.Transparent
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            //                        Text(text = eventdetail.eventdetail.category, style = TextStyle(color = Color.Black,fontFamily = clash,fontWeight = FontWeight.W600,fontSize = 14.sp))
                            //                            Text(text = "Time  |   Loc", style = TextStyle(color = Color.Black,fontFamily = aileron,fontWeight = FontWeight.Normal,fontSize = 12.sp))


                            val displayTime = "${eventdetail.eventdetail.starttime.date} Feb, ${if (eventdetail.eventdetail.starttime.hours > 12) "${eventdetail.eventdetail.starttime.hours - 12}" else eventdetail.eventdetail.starttime.hours}${if (eventdetail.eventdetail.starttime.min != 0) ":${eventdetail.eventdetail.starttime.min}" else ""} ${if (eventdetail.eventdetail.starttime.hours >= 12) "PM" else "AM"} "


                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                            ){
                                if(eventdetail.isLive.value){
                                    Text(
                                        text = "Live ",
                                        color = lighterGreen,
                                        fontSize = 14.sp,
                                        fontFamily = futura
                                    )
                                    MarqueeText(
                                        text = "| " + eventdetail.eventdetail.venue,
                                        color = colors.background,
                                        fontSize = 14.sp,
                                        fontFamily = futura,
                                        gradientEdgeColor = Color.Transparent
                                    )
                                }
                                else{
                                    MarqueeText(
                                        text = displayTime + " | " + eventdetail.eventdetail.venue,
                                        color = colors.background,
                                        fontSize = 14.sp,
                                        fontFamily = futura,
                                        gradientEdgeColor = Color.Transparent
                                    )
                                }

                            }


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

//                            var v = venuelist.find {
//                                it.name.replace("\\s".toRegex(), "")
//                                    .uppercase() == eventdetail.eventdetail.venue.replace(
//                                    "\\s".toRegex(),
//                                    ""
//                                ).uppercase()
//                            }
//                            if (v != null) {
//
//                                Box(modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clickable {
//                                        val gmmIntentUri =
//                                            Uri.parse("google.navigation:q=${v.LatLng.latitude},${v.LatLng.longitude}")
//                                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                                        mapIntent.setPackage("com.google.android.apps.maps")
//                                        context.startActivity(mapIntent)
//
//                                    }, contentAlignment = Alignment.Center) {
//
//                                    val compositionbg by rememberLottieComposition(
//                                        LottieCompositionSpec.RawRes(
//                                            R.raw.navigatebganimm
//                                        )
//                                    )
//
//                                    val progressbg by animateLottieCompositionAsState(
//                                        compositionbg, iterations = 3
//                                    )
//                                    LottieAnimation(
//                                        composition = compositionbg,
//                                        progress = progressbg,
//
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .height(50.dp)
//                                    )
//
//                                    val compositionfg by rememberLottieComposition(
//                                        LottieCompositionSpec.RawRes(
//                                            R.raw.navigatefganimm
//                                        )
//                                    )
//
//                                    val progressfg by animateLottieCompositionAsState(
//                                        compositionfg, iterations = 3
//                                    )
//                                    LottieAnimation(
//                                        compositionfg,
//                                        progressfg,
//                                        modifier = Modifier.height(18.dp)
//                                    )
//
//                                }
//                            }
                }
            }
        }
    }
}