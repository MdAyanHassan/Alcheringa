package com.alcheringa.alcheringa2022

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.passModel
import com.alcheringa.alcheringa2022.ui.theme.AlcherCardColor
import com.alcheringa.alcheringa2022.ui.theme.black
import com.alcheringa.alcheringa2022.ui.theme.creamWhite
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.highBlack
import com.alcheringa.alcheringa2022.ui.theme.highWhite
import com.alcheringa.alcheringa2022.ui.theme.vacation
import com.alcheringa.alcheringa2022.ui.theme.white
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.aztec.AztecWriter
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int): Float{
    return (currentPage - page) + currentPageOffsetFraction
}


@OptIn( ExperimentalFoundationApi::class)
@Composable
fun HeroSection(eventsList: List<eventWithLive>, onCardClick: (artist: String) -> Unit){
    val configuration = LocalConfiguration.current
    val featuredEvents = eventsList.reversed()
    //val screenHeight = configuration.screenHeightDp.dp
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
                        rotationZ =
                            if (pagerState.offsetForPage(page_index) > 0) pagerState.offsetForPage(
                                page_index
                            ) * 2 else 0f
                        translationX =
                            if (pagerState.offsetForPage(page_index) > 0) (pagerState.offsetForPage(
                                page_index
                            )) * -(screenWidth.toPx() / 2 - 50) - (screenWidth.toPx() / 20) else (pagerState.offsetForPage(
                                page_index
                            )) * (screenWidth.toPx() / 2) - (screenWidth.toPx() / 20)
                        translationY = if (pagerState.offsetForPage(page_index) > 0) abs(
                            pagerState.offsetForPage(page_index)
                        ) * 20 else 0f
//                        shadowElevation = pagerState.pageCount - abs(pagerState.offsetForPage(page_index))
                        alpha = 1f

                    }
                    .clickable {
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
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
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
                        .aspectRatio(1.02f),
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
                            "${event.eventdetail.starttime.date} Mar, ${if (event.eventdetail.starttime.hours > 12) "${event.eventdetail.starttime.hours - 12}" else event.eventdetail.starttime.hours}${if (event.eventdetail.starttime.min != 0) ":${event.eventdetail.starttime.min}" else ""} ${if (event.eventdetail.starttime.hours >= 12) "PM" else "AM"} "

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

@Composable
fun rememberQrBitmapPainter(
    id: String,
    size: Dp = 150.dp,
    padding: Dp = 4.dp
): BitmapPainter {
    val density = LocalDensity.current
    val sizePx = with(density) { size.roundToPx() }
    val paddingPx = with(density) { padding.roundToPx() }

    var bitmap by remember(id) {
        mutableStateOf<Bitmap?>(null)
    }

    LaunchedEffect(bitmap) {
        if(bitmap != null) return@LaunchedEffect

        launch(Dispatchers.IO) {
            //val qrCodeWriter = QRCodeWriter()
            val aztecCodeWriter = AztecWriter()
            val encodeHints = mutableMapOf<EncodeHintType, Any?>()
                .apply {
                    this[EncodeHintType.MARGIN] = paddingPx
                }

            val bitmapMatrix = try {
                aztecCodeWriter.encode(
                    id, BarcodeFormat.AZTEC,
                    sizePx , sizePx , encodeHints
                )
            } catch(ex: WriterException){
                null
            }

            val matrixWidth = bitmapMatrix?.width ?: sizePx
            val matrixHeight = bitmapMatrix?.height ?: sizePx

            val newBitmap = Bitmap.createBitmap(
                bitmapMatrix?.width ?: sizePx,
                bitmapMatrix?.height ?: sizePx,
                Bitmap.Config.ARGB_8888,
            )

            for (x in 0 until matrixWidth) {
                for (y in 0 until matrixHeight) {
                    val shouldColorPixel = bitmapMatrix?.get(x, y) ?: false
                    val pixelColor = if (shouldColorPixel) android.graphics.Color.BLACK else android.graphics.Color.TRANSPARENT

                    newBitmap.setPixel(x, y, pixelColor)
                }
            }

            bitmap = newBitmap


        }




    }
    return remember(bitmap) {
        val currentBitmap = bitmap ?: Bitmap.createBitmap(
            sizePx, sizePx,
            Bitmap.Config.ARGB_8888,
        ).apply { eraseColor(android.graphics.Color.TRANSPARENT) }

        BitmapPainter(currentBitmap.asImageBitmap())
    }
    //return BitmapPainter()


}


@Composable
fun AlcherCard(name: String , id: String) {

    Box(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .background(color = AlcherCardColor, shape = RoundedCornerShape(5.dp))
                .clip(RoundedCornerShape(5.dp))
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = "Your Alcher Card",
                        fontSize = 22.sp,
                        color = creamWhite,
                        modifier = Modifier.padding(16.dp),
                        fontFamily = vacation
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vector_11),
                            contentDescription = null,
                            modifier = Modifier
                                .width(150.dp)
                                .height(60.dp),
                            contentScale = ContentScale.Inside,


                            )

                        Text(
                            text = name,
                            color = creamWhite,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 10.dp)
                                .align(Alignment.CenterStart),
                            fontSize = 18.sp,
                            fontFamily = futura
                        )

                    }
                }


                Box(
                    modifier = Modifier.align(Alignment.CenterEnd).offset(x = (110).dp,)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.vector__7_),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd).size(300.dp)
                        //.offset(x = (100).dp,)
                        ,
                        contentScale = ContentScale.Crop
                    )


                    Image(
                        painter = rememberQrBitmapPainter(id = id),
                        contentDescription = null,
                        modifier = Modifier.size(110.dp).align(Alignment.Center).padding(end = 25.dp)
                    )
                }
            }




        }
    }




}

@Preview
@Composable
fun AlcherCardView() {
    //AlcherCard(name = "Rupayan Daripa" , content = "123456789")
}
