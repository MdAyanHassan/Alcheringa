package com.example.alcheringa2022

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.example.alcheringa2022.Database.ScheduleDatabase
import com.example.alcheringa2022.Model.addNewItem
import com.example.alcheringa2022.Model.removeAnItem
import com.example.alcheringa2022.Model.viewModelHome
import com.example.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.example.alcheringa2022.ui.theme.clash
import com.example.alcheringa2022.ui.theme.hk_grotesk
import com.example.alcheringa2022.ui.theme.orangeText
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
fun Event_card(eventdetail: eventWithLive,viewModelHm: viewModelHome,context: Context,FragmentManager: androidx.fragment.app.FragmentManager) {
    var ScheduleDatabase=ScheduleDatabase(context)
    var okstate= remember{ mutableStateOf(false)}
    var okstatenum= remember{ mutableStateOf(0)}

    viewModelHm.OwnEventsLiveState.forEach{
            data-> if( data==eventdetail.eventdetail){okstate.value=true;okstatenum.value+=1}
    }
    if(okstatenum.value==0){okstate.value=false}


    Box() {
        Text(text =viewModelHm.OwnEventsLiveState.size.toString(), fontSize = 0.sp )

        Card(modifier = Modifier.wrapContentWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = 5.dp) {
            Box(modifier = Modifier
                .height(256.dp)
                .width(218.dp)
                .clickable {
                    val frg=Events_Details_Fragment()
                    frg.arguments= bundleOf("Artist" to eventdetail.eventdetail.artist)
                    FragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView,frg ).addToBackStack(null)
                        .commit()
                }

            ){
                GlideImage(modifier = Modifier
                    .width(218.dp)
                    .height(256.dp),imageModel = eventdetail.eventdetail.imgurl, contentDescription = "artist", contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
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
                                               okstate.value=false},
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
                                Color.Black
                            ), startY = 300f
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
                        Text(text = "${eventdetail.eventdetail.starttime.date} Feb, ${if(eventdetail.eventdetail.starttime.hours>12)"${eventdetail.eventdetail.starttime.hours-12}" else eventdetail.eventdetail.starttime.hours}${if (eventdetail.eventdetail.starttime.min!=0) ":${eventdetail.eventdetail.starttime.min}" else ""} ${if (eventdetail.eventdetail.starttime.hours>=12)"PM" else "AM"} ", style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
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