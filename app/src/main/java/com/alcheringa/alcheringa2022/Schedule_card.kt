package com.alcheringa.alcheringa2022

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
        M = Modifier
            .wrapContentWidth()
            .border(
                3.dp, color = if (isSystemInDarkTheme()) {
                    liveGreenDark
                } else {
                    liveGreen
                },
                RoundedCornerShape(16.dp)
            )
    } else {
        M = Modifier
            .wrapContentWidth()
            .border(
                1.5f.dp, color = colors.onBackground, RoundedCornerShape(16.dp)
            )
    }
        val scm=if(isSystemInDarkTheme()) Modifier

//            .coloredShadow(
//                if (eventdetail.isLive.value) {
//                    liveGreen
//                } else {
//                    colors.onBackground
//                },
//                0.39f,
//                16.dp,
//                25.dp,
//                0.dp,
//                0.dp
//            ).background(colors.background)
            .graphicsLayer(translationY = animationProgress.value)
                else Modifier

            .coloredShadow(colors.onBackground, 0.01f, 18.dp, 1.dp, 20.dp, 0.dp)
            .coloredShadow(colors.onBackground, 0.06f, 18.dp, 1.dp, 12.dp, 0.dp)
            .coloredShadow(colors.onBackground, 0.24f, 18.dp, 1.dp, 4.dp, 0.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colors.background)
            .graphicsLayer(translationY = animationProgress.value)
    Box(
       modifier = scm
    ) {
        Text(text = viewModelHm.OwnEventsLiveState.size.toString(), fontSize = 0.sp)

        Card(
            modifier = M
                .height(106.dp)
                .width(220.dp)
                .clickable {
                    val arguments = bundleOf("Artist" to eventdetail.eventdetail.artist)
                    NavHostFragment
                        .findNavController(Fragment)
                        .navigate(a, arguments);
                },
            shape = RoundedCornerShape(16.dp),
            elevation = 0.dp,
            backgroundColor = colors.background,


        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(Modifier.fillMaxSize()) {
                    MarqueeText(
                        text = eventdetail.eventdetail.artist,
                        color = colors.onBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = aileron,
                        modifier = Modifier.padding(end = 8.dp),
                        gradientEdgeColor = Color.Transparent
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = eventdetail.eventdetail.category,
                        style = TextStyle(
                            color = colors.onBackground,
                            fontFamily = aileron,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                    MarqueeText(
                        text = "${eventdetail.eventdetail.starttime.date} Mar, ${if (eventdetail.eventdetail.starttime.hours > 12) "${eventdetail.eventdetail.starttime.hours - 12}" else eventdetail.eventdetail.starttime.hours}${if (eventdetail.eventdetail.starttime.min != 0) ":${eventdetail.eventdetail.starttime.min}" else ""} ${if (eventdetail.eventdetail.starttime.hours >= 12) "PM" else "AM"} " + "| ${eventdetail.eventdetail.venue}",
                        style = TextStyle(
                            color = colors.onBackground,
                            fontFamily = aileron,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        ),
                        gradientEdgeColor = Color.Transparent,
                        modifier = Modifier.fillMaxWidth()
                    )



                }
                if(eventdetail.isLive.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Card(
                            modifier = Modifier
                                .wrapContentWidth(),
                            //.weight(1f)
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor =
                            if (isSystemInDarkTheme()) {
                                liveGreenDark
                            } else {
                                liveGreen
                            },
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                //
                                , contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "● Live",
                                    color = colors.background,
                                    fontSize = 12.sp,
                                    fontFamily = aileron,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}