package com.example.alcheringa2022

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.example.alcheringa2022.ui.theme.clash
import com.example.alcheringa2022.ui.theme.hk_grotesk

val events = mutableListOf(

    eventdetail(
        "JUBIN NAUTIYAL",
        "Pro Nights",
        OwnTime(11,4,30),
        "ONLINE", R.drawable.jubin
    ),

    eventdetail(
        "DJ SNAKE",
        "Pro Nights",
        OwnTime(12,16,0),
        "ON GROUND", R.drawable.djsnake
    ),
    eventdetail(
        "TAYLOR SWIFT",
        "Pro Nights",
        OwnTime(12,21,30),
        "ON GROUND", R.drawable.taylor
    )


)

@Composable
fun Event_card(eventdetail: eventdetail ) {
    Box() {

        Card(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = 5.dp) {
            Box(modifier = Modifier
                .height(256.dp)
                .width(218.dp)){
                Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                )
                Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
                Column() {
//
                    Box(
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
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(11.dp), contentAlignment = Alignment.TopEnd){ Image( modifier = Modifier.width(18.dp).height(18.dp),
                        painter = painterResource(id = R.drawable.add_icon),
                        contentDescription ="null"
                    )}
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
                        Text(text = eventdetail.artist, style = MaterialTheme.typography.h1)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = eventdetail.category, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = clash,fontWeight = FontWeight.W600,fontSize = 14.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${eventdetail.Starttime.date} Feb, ${if(eventdetail.Starttime.hours>12)"${eventdetail.Starttime.hours-12}" else eventdetail.Starttime.hours}${if (eventdetail.Starttime.min!=0) ":${eventdetail.Starttime.min}" else ""} ${if (eventdetail.Starttime.hours>=12)"PM" else "AM"} ", style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                        Spacer(modifier = Modifier.height(2.dp))
                        Row {
                            Box(modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)) {
                                Image(
                                        painter = if (eventdetail.mode.contains("ONLINE")) {
                                            painterResource(id = R.drawable.online)
                                        } else {
                                            painterResource(id = R.drawable.onground)
                                        },
                                        contentDescription = null, modifier = Modifier.fillMaxSize(),alignment = Alignment.Center, contentScale =ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = eventdetail.mode,style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                        }
                    }

                }


            }
        }
    }



}






@Composable
@Preview
fun PreviewItem(){
    Alcheringa2022Theme {
        LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(events) { dataEach -> Event_card(eventdetail = dataEach) }
        }
    }
}