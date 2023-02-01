package com.alcheringa.alcheringa2022

import android.content.Intent
import android.net.Uri
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

    var M = Modifier.wrapContentWidth()
    val isdark= isSystemInDarkTheme()
    val animationProgress = remember { Animatable(300f) }

    LaunchedEffect(key1=Unit,block = {
        animationProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    })

    M = Modifier.clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colors.background)
        .wrapContentWidth()
        .border(
            1.5f.dp,
            color = MaterialTheme.colors.onBackground, RoundedCornerShape(16.dp)
        )



    val bm= if(isSystemInDarkTheme()) Modifier.background(MaterialTheme.colors.background)
        .graphicsLayer(translationY = animationProgress.value)
        .width(220.dp)
    else Modifier.background(MaterialTheme.colors.background).graphicsLayer(translationY = animationProgress.value)
        .coloredShadow(MaterialTheme.colors.onBackground, 0.01f, 16.dp, 1.dp, 20.dp, 0.dp)
        .coloredShadow(MaterialTheme.colors.onBackground, 0.06f, 16.dp, 1.dp, 12.dp, 0.dp)
        .coloredShadow(MaterialTheme.colors.onBackground, 0.24f, 16.dp, 1.dp, 4.dp, 0.dp)

        .width(220.dp)
    Box(
        modifier=bm)
    {

        Card(modifier = M.padding(0.dp),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp


        ){

            Box(modifier = Modifier
                .clickable(
                    enabled = true,
                    onClick = onClick
                )
            ){
                Column {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom
                    ){

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 0.dp, end = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier
                                .fillMaxWidth(0.27f)
                                .aspectRatio(1f)
                                .padding(0.dp)
                                , contentAlignment = Alignment.Center
                            ) {
                                GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(
                                    DiskCacheStrategy.AUTOMATIC)},modifier = Modifier,
                                    imageModel = utlt.imgUrl,
                                    contentDescription = "utility",
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
                                                    R.raw.failure
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

                                        }

                                    }
                                )


                            }
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp, top = 12.dp)
                            ) {
                                MarqueeText(
                                    text = utlt.name,
                                    color = MaterialTheme.colors.onBackground,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = aileron,
                                    gradientEdgeColor = Color.Transparent
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                MarqueeText(
                                    text = "Click to view location on map",
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

}