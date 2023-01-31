package com.alcheringa.alcheringa2022

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alcheringa.alcheringa2022.Model.utilityModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.eventdetail
import com.alcheringa.alcheringa2022.ui.theme.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun UtilityCard(utlt: utilityModel, onClick: () -> Unit) {

    val animationProgress = remember { Animatable(300f) }
    LaunchedEffect(key1 = Unit, block = {
        animationProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    })


    val M = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(colors.background)
        .wrapContentWidth()
        .border(
            1.5f.dp,
            color = colors.onBackground, RoundedCornerShape(16.dp)
        )

    val bm = if (isSystemInDarkTheme()) Modifier
        .background(colors.background)
        .graphicsLayer(translationY = animationProgress.value)
        .width(220.dp)
    else Modifier
        .background(colors.background)
        .graphicsLayer(translationY = animationProgress.value)
        .coloredShadow(colors.onBackground, 0.01f, 16.dp, 1.dp, 20.dp, 0.dp)
        .coloredShadow(colors.onBackground, 0.06f, 16.dp, 1.dp, 12.dp, 0.dp)
        .coloredShadow(colors.onBackground, 0.24f, 16.dp, 1.dp, 4.dp, 0.dp)

        .width(220.dp)
    Box(
        modifier = bm
    )
    {
        Card(
            modifier = M.padding(0.dp),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            backgroundColor = colors.background,
            elevation = 0.dp


        ) {

            Box(
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = onClick
                    )
            ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .width(220.dp)
                                .wrapContentHeight(),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp,
                                        top = 12.dp
                                    ),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                MarqueeText(
                                    text = utlt.name,
                                    color = colors.onBackground,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = aileron,
                                    gradientEdgeColor = Color.Transparent
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                MarqueeText(
                                    text = "Click to see ${utlt.name}' locations",
                                    style = TextStyle(
                                        color = colors.onBackground,
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp
                                    ),
                                    gradientEdgeColor = Color.Transparent
                                )


                            }
                        }
                    }

            }
        }
    }

}