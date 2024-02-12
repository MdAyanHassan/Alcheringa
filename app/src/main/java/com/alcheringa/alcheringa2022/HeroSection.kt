package com.alcheringa.alcheringa2022

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.platform.LocalConfiguration
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
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.highBlack
import com.alcheringa.alcheringa2022.ui.theme.highWhite
import com.alcheringa.alcheringa2022.ui.theme.white
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.pager.ExperimentalPagerApi
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.abs

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int): Float{
    return (currentPage - page) + currentPageOffsetFraction
}


@OptIn( ExperimentalFoundationApi::class)
@Composable
fun HeroSection(eventsList: List<eventWithLive>, onCardClick: (artist: String) -> Unit){
    val configuration = LocalConfiguration.current
    val featuredEvents = eventsList.reversed()
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val pagerState = rememberPagerState(pageCount = { featuredEvents.size }, initialPage = featuredEvents.size-1)
    Box(
        modifier = Modifier
            .padding(vertical = 30.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(state = pagerState, beyondBoundsPageCount = 4, pageSpacing = -(screenWidth/2), reverseLayout = true
            ) { page_index ->
            FeaturedEventCard(
                event = featuredEvents[page_index],
                modifier = Modifier

                    .graphicsLayer {
                        rotationZ = if( pagerState.offsetForPage(page_index) > 0) pagerState.offsetForPage(page_index) * 2 else 0f
                        translationX = if( pagerState.offsetForPage(page_index) > 0) (pagerState.offsetForPage(page_index)) * -(screenWidth.toPx()/2 - 50) - (screenWidth.toPx()/20) else (pagerState.offsetForPage(page_index)) * (screenWidth.toPx()/2) - (screenWidth.toPx()/20)
                        translationY = if( pagerState.offsetForPage(page_index) > 0) abs(pagerState.offsetForPage(page_index)) * 20 else 0f
//                        shadowElevation = pagerState.pageCount - abs(pagerState.offsetForPage(page_index))
                        alpha = 1f

                    }
                    .clickable{
                        onCardClick(featuredEvents[page_index].eventdetail.artist)
                    }
            )

        }
    }
}

@Composable
fun FeaturedEventCard(event: eventWithLive, modifier: Modifier){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(0.72f),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, darkTealGreen),
            backgroundColor = colors.background
        ) {
            Column(
                Modifier.fillMaxWidth().wrapContentHeight()
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
                        .aspectRatio(1.14f),
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

                ) {
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



}
