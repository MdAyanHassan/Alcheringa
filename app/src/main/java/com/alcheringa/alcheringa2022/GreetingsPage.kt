package com.alcheringa.alcheringa2022

import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.alcheringa.alcheringa2022.ui.theme.aileron
import com.alcheringa.alcheringa2022.ui.theme.borderdarkpurple
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.creamWhite
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.darkerGreen
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import com.google.android.exoplayer2.Player.REPEAT_MODE_OFF
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

// This composable display the video in loop and displays the login and sign up buttons

@Composable
fun GreetingPage(onLoginClick: () -> Unit, onSignupClick: () -> Unit ) {
    val context = LocalContext.current

    val mediaItem = MediaItem.Builder()
        .setUri(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.greeting_video))
        .build()
    val exoPlayer = remember(context, mediaItem) {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem)

                setMediaSource(source)
                prepare()
            }
    }
    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
    exoPlayer.repeatMode = REPEAT_MODE_ALL
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        DisposableEffect(
            AndroidView(factory = {
                PlayerView(context).apply {
                    hideController()
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                }
            })
        ) {
            onDispose { exoPlayer.release() }
        }
    }

    Box {
        if(isSystemInDarkTheme()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colors.onBackground,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                0f to containerPurple,
                                1f to borderdarkpurple
                            ),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            onLoginClick()
                        }
                        .fillMaxWidth()


                ) {
                    Row(
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .width(150.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Login",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = aileron,
                            color = creamWhite,
                        )

                    }


                }


                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colors.onBackground,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                0f to darkTealGreen,
                                1f to darkerGreen
                            ),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            onSignupClick()
                        }
                        .fillMaxWidth()



                ) {
                    Row(
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .width(150.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = aileron,
                            color = creamWhite,
                        )

                    }
                }

                Spacer(modifier = Modifier.height(30.dp))


            }
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colors.onBackground,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                0f to containerPurple,
                                1f to borderdarkpurple
                            ),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            onLoginClick()
                        }
                        .fillMaxWidth()


                ) {
                    Row(
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .width(150.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Login",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = aileron,
                            color = creamWhite,
                        )

                    }


                }


                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colors.onBackground,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                0f to darkTealGreen,
                                1f to darkerGreen
                            ),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            onSignupClick()
                        }
                        .fillMaxWidth()



                ) {
                    Row(
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .width(150.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = aileron,
                            color = creamWhite,
                        )

                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

            }
        }
    }


}

@Preview
@Composable
fun GreetingPagePreview() {
    GreetingPage({} , {})
}