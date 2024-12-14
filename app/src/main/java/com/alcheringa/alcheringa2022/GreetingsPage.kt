package com.alcheringa.alcheringa2022

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// This composable display the video in loop and displays the login and sign up buttons

@Composable
fun GreetingPage2025(
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.welcome_page_bg),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 18.dp, end = 18.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    onLoginClick()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .paint(
                        painterResource(id = R.drawable.sign_in_button_bg_green),
                        contentScale = ContentScale.FillBounds
                    )
            ) {

                Text(
                    text = "Login",
                    lineHeight = 24.sp,
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFF1E8),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.alcher_pixel, FontWeight.Normal),
                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                        ),
                        shadow = Shadow(
                            color = Color(0xFF000000),
                            offset = Offset(3f, 2f)
                        )
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                )

            }

            Button(
                onClick = {
                    onSignupClick()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .paint(
                        painterResource(id = R.drawable.sign_in_button_bg_green),
                        contentScale = ContentScale.FillBounds
                    )
            ) {

                Text(
                    text = "Sign up",
                    lineHeight = 24.sp,
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFF1E8),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.alcher_pixel, FontWeight.Normal),
                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                        ),
                        shadow = Shadow(
                            color = Color(0xFF000000),
                            offset = Offset(3f, 2f)
                        )
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                )

            }
        }
    }
}

@Preview
@Composable
fun GreetingPage2025Preview() {
    GreetingPage2025({}, {})
}
//
//@Composable
//fun GreetingPage(onLoginClick: () -> Unit, onSignupClick: () -> Unit ) {
//    val context = LocalContext.current
//
//    val mediaItem = MediaItem.Builder()
//        .setUri(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.greeting_video))
//        .build()
//    val exoPlayer = remember(context, mediaItem) {
//        ExoPlayer.Builder(context)
//            .build()
//            .apply {
//                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
//                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
//                    context,
//                    defaultDataSourceFactory
//                )
//                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(mediaItem)
//
//                setMediaSource(source)
//                prepare()
//            }
//    }
//    exoPlayer.playWhenReady = true
//    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
//    exoPlayer.repeatMode = REPEAT_MODE_ALL
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//        DisposableEffect(
//            AndroidView(factory = {
//                PlayerView(context).apply {
//                    hideController()
//                    useController = false
//                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
//
//                    player = exoPlayer
//                    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
//                }
//            })
//        ) {
//            onDispose { exoPlayer.release() }
//        }
//    }
//
//    Box {
//        if(isSystemInDarkTheme()) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Transparent)
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Bottom
//            ) {
//
//                Box(
//                    modifier = Modifier
//                        .height(50.dp)
//                        .border(
//                            1.dp,
//                            MaterialTheme.colors.onBackground,
//                            shape = RoundedCornerShape(5.dp),
//                        )
//                        .background(
//                            brush = Brush.verticalGradient(
//                                0f to containerPurple,
//                                1f to borderdarkpurple
//                            ),
//                            shape = RoundedCornerShape(5.dp)
//                        )
//                        .clickable {
//                            onLoginClick()
//                        }
//                        .fillMaxWidth()
//
//
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .align(
//                                Alignment.Center
//                            )
//                            .width(150.dp),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Login",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            fontFamily = aileron,
//                            color = creamWhite,
//                        )
//
//                    }
//
//
//                }
//
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Box(
//                    modifier = Modifier
//                        .height(50.dp)
//                        .border(
//                            1.dp,
//                            MaterialTheme.colors.onBackground,
//                            shape = RoundedCornerShape(5.dp),
//                        )
//                        .background(
//                            brush = Brush.verticalGradient(
//                                0f to darkTealGreen,
//                                1f to darkerGreen
//                            ),
//                            shape = RoundedCornerShape(5.dp)
//                        )
//                        .clickable {
//                            onSignupClick()
//                        }
//                        .fillMaxWidth()
//
//
//
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .align(
//                                Alignment.Center
//                            )
//                            .width(150.dp),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Sign Up",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            fontFamily = aileron,
//                            color = creamWhite,
//                        )
//
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(30.dp))
//
//
//            }
//        }
//        else {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Transparent)
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Bottom
//            ) {
//
//                Box(
//                    modifier = Modifier
//                        .height(50.dp)
//                        .border(
//                            1.dp,
//                            MaterialTheme.colors.onBackground,
//                            shape = RoundedCornerShape(5.dp),
//                        )
//                        .background(
//                            brush = Brush.verticalGradient(
//                                0f to containerPurple,
//                                1f to borderdarkpurple
//                            ),
//                            shape = RoundedCornerShape(5.dp)
//                        )
//                        .clickable {
//                            onLoginClick()
//                        }
//                        .fillMaxWidth()
//
//
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .align(
//                                Alignment.Center
//                            )
//                            .width(150.dp),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Login",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            fontFamily = aileron,
//                            color = creamWhite,
//                        )
//
//                    }
//
//
//                }
//
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Box(
//                    modifier = Modifier
//                        .height(50.dp)
//                        .border(
//                            1.dp,
//                            MaterialTheme.colors.onBackground,
//                            shape = RoundedCornerShape(5.dp),
//                        )
//                        .background(
//                            brush = Brush.verticalGradient(
//                                0f to darkTealGreen,
//                                1f to darkerGreen
//                            ),
//                            shape = RoundedCornerShape(5.dp)
//                        )
//                        .clickable {
//                            onSignupClick()
//                        }
//                        .fillMaxWidth()
//
//
//
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .align(
//                                Alignment.Center
//                            )
//                            .width(150.dp),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Sign Up",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            fontFamily = aileron,
//                            color = creamWhite,
//                        )
//
//                    }
//                }
//                Spacer(modifier = Modifier.height(30.dp))
//
//            }
//        }
//    }
//
//
//}

//@Preview
//@Composable
//fun GreetingPagePreview() {
//    GreetingPage({} , {})
//}