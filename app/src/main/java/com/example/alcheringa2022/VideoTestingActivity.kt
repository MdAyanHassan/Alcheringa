package com.example.alcheringa2022

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.alcheringa2022.ui.theme.hk_grotesk
import com.google.accompanist.pager.*
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import java.util.ArrayList
import kotlin.math.absoluteValue

class VideoTestingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_testing)

        val cv1: ComposeView = findViewById(R.id.cv1)
        cv1.setContent { HorizontalPager(context = this, lifecycle = lifecycle) }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPager(context: Context, lifecycle: Lifecycle) {

    val images: ArrayList<String> = ArrayList<String>()
    images.add("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Merch%2FHoodie1%2Fhoodie.png?alt=media&token=02880cd4-a097-494e-bb6e-72dfab612434")
    images.add("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Merch%2FHoodie1%2Fhoodie.png?alt=media&token=02880cd4-a097-494e-bb6e-72dfab612434")
    images.add("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Merch%2FHoodie1%2Fhoodie.png?alt=media&token=02880cd4-a097-494e-bb6e-72dfab612434")

    Column {
        val pagerState = rememberPagerState()

        val videoUrl = "https://cdn.videvo.net/videvo_files/video/free/2020-05/large_watermarked/3d_ocean_1590675653_preview.mp4"



        HorizontalPager(
            count = images.size + 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) { page ->


            Card(
                Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                if (page < images.size) {
                    Box {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = 5.dp
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.LightGray)
                                    .padding(20.dp)
                                    .height(400.dp)
                                    .fillMaxWidth(), contentAlignment = Alignment.Center
                            ) {
                                GlideImage(
                                    imageModel = images[page],
                                    contentDescription = "artist",
                                    modifier = Modifier
                                        .width(348.dp)
                                        .height(360.dp),
                                    alignment = Alignment.Center,
                                    contentScale = ContentScale.Crop,
                                    shimmerParams = ShimmerParams(
                                        baseColor = Color.Black,
                                        highlightColor = Color.LightGray,
                                        durationMillis = 350,
                                        dropOff = 0.65f,
                                        tilt = 20f
                                    ), failure = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(473.dp), contentAlignment = Alignment.Center
                                        ) {
                                            Column(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
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
                            }
                        }
                    }
                } else {
                    Box {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = 5.dp
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Black)
                                    .padding(8.dp)
                                    .height(400.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                val mediaItem = MediaItem.fromUri(videoUrl)
                                val exoPlayer = SimpleExoPlayer.Builder(context).build().apply {
                                    setMediaItem(mediaItem)
                                    playWhenReady = true
                                    prepare()
                                    play()
                                }
                                DisposableEffect(
                                    AndroidView(factory = { PlayerView(context).apply { player = exoPlayer } }))
                                { onDispose { exoPlayer.release() } }

                            }
                        }
                    }
                }
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor= colorResource(id = R.color.textGray),
            inactiveColor = colorResource(id = R.color.darkGray),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }

}

@SuppressLint("RememberReturnType")
@Composable
fun PlayVideo(context: Context){

    val videoUrl = "https://cdn.videvo.net/videvo_files/video/free/2020-05/large_watermarked/3d_ocean_1590675653_preview.mp4"

    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory : DataSource.Factory = DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.packageName))

            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoUrl))

            this.prepare(source)
        }
    }

    AndroidView(factory = { context ->
        PlayerView(context).apply {
            player = exoPlayer
        }
    })

}