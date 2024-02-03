package com.alcheringa.alcheringa2022

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alcheringa.alcheringa2022.Model.OwnTime
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.eventdetail
import com.alcheringa.alcheringa2022.R
import com.alcheringa.alcheringa2022.ui.theme.black
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.highBlack
import com.alcheringa.alcheringa2022.ui.theme.highWhite
import com.alcheringa.alcheringa2022.ui.theme.white
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalPagerApi::class)
fun PagerState.offsetForPage(page: Int): Float{
    return (currentPage - page) + currentPageOffset
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeroSection(featuredEvents: List<eventWithLive>){
    val pagerState = rememberPagerState()
    Box(
        modifier = Modifier.padding(vertical = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(count = featuredEvents.size, state = pagerState, modifier = Modifier.width(320.dp)) { page_index ->
            FeaturedEventCard(
                event = featuredEvents[page_index],
                modifier = Modifier
                    .graphicsLayer {
//                        rotationZ = pagerState.offsetForPage(page_index)
                        translationX = (pagerState.offsetForPage(page_index) * 0.99f ) * 200
                    }
            )

        }
    }
}

@Composable
fun FeaturedEventCard(event: eventWithLive, modifier: Modifier){
    Card(
        modifier = modifier
            .width(260.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, colors.secondary)
    ){
        Column(
            Modifier.fillMaxSize()
        ) {
            GlideImage(
                requestOptions = {
                    RequestOptions.diskCacheStrategyOf(
                        DiskCacheStrategy.AUTOMATIC
                    )
                },
                imageModel = event.eventdetail.imgurl,
                contentDescription = "artist",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                shimmerParams = ShimmerParams(
                    baseColor = if (isSystemInDarkTheme()) black else highWhite,
                    highlightColor = if (isSystemInDarkTheme()) highBlack else white
                ), failure = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        val composition by rememberLottieComposition(
                            LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.comingsoondark else R.raw.comingsoonlight)
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ){
                Column {
                    MarqueeText(
                        text = event.eventdetail.artist,
                        fontFamily = futura,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        gradientEdgeColor = Color.Transparent,
                        color = colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val displayTime =
                        "${event.eventdetail.starttime.date} Feb, ${if (event.eventdetail.starttime.hours > 12) "${event.eventdetail.starttime.hours - 12}" else event.eventdetail.starttime.hours}${if (event.eventdetail.starttime.min != 0) ":${event.eventdetail.starttime.min}" else ""} ${if (event.eventdetail.starttime.hours >= 12) "PM" else "AM"} "

                    MarqueeText(
                        text = displayTime + " | " + event.eventdetail.venue,
                        fontFamily = futura,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        gradientEdgeColor = Color.Transparent,
                        color = colors.onBackground
                    )
                }

            }
        }

    }



}
