package com.alcheringa.alcheringa2022

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Model.InformalModel
import com.alcheringa.alcheringa2022.ui.theme.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun InformalCard(informal: InformalModel, onCLick: () -> Unit) {

    var M = Modifier.wrapContentWidth()
    val isdark= isSystemInDarkTheme()
    val animationProgress = remember { Animatable(300f) }

    LaunchedEffect(key1=Unit,block = {
        animationProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    })

    M = Modifier
        .clip(RoundedCornerShape(4.dp)) // made changes 16.dp to 7.dp
        .wrapContentWidth()
        .border(
            1.dp,
            color = MaterialTheme.colors.primary, RoundedCornerShape(4.dp)// made changes 16.dp to 7.dp
        )



    val bm= Modifier
        .graphicsLayer(translationY = animationProgress.value)
        .width(231.dp)
        .clickable(
            enabled = true,
            onClick = onCLick
        )

    Box(
        modifier=bm)
    {

        Card(modifier = M.padding(0.dp),
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
            backgroundColor = MaterialTheme.colors.onBackground,
            elevation = 0.dp


        ){

            Box(
            ){
                Column {
                    Box(
                        modifier = Modifier
                            .width(231.dp)
                            .height(194.dp),
                    ) {
                        Card(shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)) {
                            GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(
                                DiskCacheStrategy.AUTOMATIC)},modifier = Modifier,
                                imageModel = informal.imgUrl,
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

                                    }

                                }
                            )
                        }

                    }

//                Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.Top
                    ){


                                MarqueeText(
                                    text = informal.name,
                                    color = MaterialTheme.colors.background,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = futura,
                                    gradientEdgeColor = Color.Transparent
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                MarqueeText(
                                    text = "Click to Navigate to location",
                                    style = TextStyle(
                                        color = MaterialTheme.colors.background,
                                        fontFamily = futura,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp
                                    ),
                                    gradientEdgeColor = Color.Transparent
                                )


                    }
                }
            }
        }
    }
}