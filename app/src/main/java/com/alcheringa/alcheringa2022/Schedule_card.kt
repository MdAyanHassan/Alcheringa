package com.alcheringa.alcheringa2022

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.ui.theme.*

@Composable
fun Schedule_card(eventdetail: eventWithLive, viewModelHm: viewModelHome, context: Context, Fragment : Fragment, FragmentManager: androidx.fragment.app.FragmentManager, a : Int) {
    var M = Modifier.wrapContentWidth()

    val animationProgress = remember { Animatable(300f) }
    LaunchedEffect(key1=Unit,block = {
        animationProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    })

    if (eventdetail.isLive.value) {
        M = Modifier.wrapContentWidth().border(
            3.dp, color = liveGreen, RoundedCornerShape(16.dp)
        )
    } else {
        M = Modifier.wrapContentWidth().border(
            1.dp, color = black, RoundedCornerShape(16.dp))
    }

    Box(
        Modifier
            .graphicsLayer(translationY = animationProgress.value)
    ) {
        Text(text = viewModelHm.OwnEventsLiveState.size.toString(), fontSize = 0.sp)

        Card(
            modifier = M,
            shape = RoundedCornerShape(16.dp),
            elevation = 5.dp,
        ) {

            Box(modifier = Modifier.background(Color.White)
                .height(106.dp)
                .width(220.dp)
                .clickable {
                    val arguments = bundleOf("Artist" to eventdetail.eventdetail.artist)
                    NavHostFragment.findNavController(Fragment).navigate(a,arguments);
                }
            )
            Row(horizontalArrangement = Arrangement.Center,) {
//
                if (eventdetail.isLive.value) {
                    Card(
                        modifier = Modifier.wrapContentWidth(),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .width(45.dp)
                                .height(22.dp)
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
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(16.dp), contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text(
                        text = eventdetail.eventdetail.artist,
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = clash
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = eventdetail.eventdetail.category,
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = clash,
                            fontWeight = FontWeight.W400,
                            fontSize = 12.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        Text(
                            text = "${eventdetail.eventdetail.starttime.date} Mar, ${if (eventdetail.eventdetail.starttime.hours > 12) "${eventdetail.eventdetail.starttime.hours - 12}" else eventdetail.eventdetail.starttime.hours}${if (eventdetail.eventdetail.starttime.min != 0) ":${eventdetail.eventdetail.starttime.min}" else ""} ${if (eventdetail.eventdetail.starttime.hours >= 12) "PM" else "AM"} ",
                            style = TextStyle(
                                color = black,
                                fontFamily = aileron,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        )

                        //Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "| ${eventdetail.eventdetail.venue}",
                            style = TextStyle(
                                color = black,
                                fontFamily = aileron,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}