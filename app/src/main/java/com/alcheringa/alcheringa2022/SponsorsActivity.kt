package com.alcheringa.alcheringa2022

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alcheringa.alcheringa2022.Model.sponsersnew
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.black
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.darkBar
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.highBlack
import com.alcheringa.alcheringa2022.ui.theme.highWhite
import com.alcheringa.alcheringa2022.ui.theme.lighterPurple
import com.alcheringa.alcheringa2022.ui.theme.white
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

class SponsorsActivity: AppCompatActivity() {

    lateinit var firestore: FirebaseFirestore
    var sponserlist= mutableListOf<sponsersnew>()
    val headersponsers= mutableStateListOf<sponsersnew>()
    val generalsponsers= mutableStateListOf<sponsersnew>()
    val loaderView = mutableStateOf(true)

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firestore = FirebaseFirestore.getInstance()
        sponserlist.clear()
        populate_sponsors()

        setContent {
            Alcheringa2022Theme {
                Column(
                    modifier = Modifier
                        .background(colors.background)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .height(65.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier) {
                            Icon(
                                painter = painterResource(id = R.drawable.cart_arrow),
                                contentDescription = null,
                                tint = lighterPurple,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .clickable {
                                        finish()
                                    }
                                    .padding(10.dp)
                            )
                        }
                        Text(
                            text = "Sponsors",
                            fontFamily = futura,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            color = containerPurple
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(darkTealGreen)
                    )


                    if (headersponsers.isNotEmpty() || generalsponsers.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp)
                                .paint(
                                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                                    contentScale = ContentScale.Crop
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                item(span = { GridItemSpan(2) }) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                                items(
                                    span = { GridItemSpan(2) },
                                    count = headersponsers.size
                                ) { index ->
                                    topSponsor(sponsersnew = headersponsers[index])
                                }
                                items(span = { GridItemSpan(2) }, count = 1) {
                                    Spacer(modifier = Modifier.height(20.dp))
                                }

                                items(count = generalsponsers.size) { index ->
                                    subtopSponser(sponsersnew = generalsponsers[index])
                                }
                            }

                        }
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sponsors coming soon",
                                fontSize = 20.sp,
                                fontFamily = futura,
                                color = colors.onBackground
                            )
                        }
                    }
                }


                if(loaderView.value){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = darkBar.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center,

                        ) {
                        LoadingAnimation3()
                    }
                }
            }
        }
    }

    private fun populate_sponsors() {
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("SponsersNew").get().addOnSuccessListener { task ->
            Log.i("EE", "populate task 1 $task")
            for (documentSnapshot in task) {
                Log.d("sponsor", documentSnapshot.getString("imageurl")!!)
                sponserlist.add(
                    documentSnapshot.toObject(sponsersnew::class.java)
                )
                loaderView.value = false
            }
            headersponsers.addAll(sponserlist.filter { sponsersnew -> sponsersnew.heading })
            generalsponsers.addAll(sponserlist.filter { sponsersnew -> !sponsersnew.heading })
        }.addOnFailureListener{ Log.d("sponsor", "failed"); loaderView.value = false}
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun topSponsor(sponsersnew: sponsersnew){
        Column {
            Text(
                text = sponsersnew.title,
                fontSize = 18.sp,
                fontFamily = futura,
                color = colors.onBackground,
                modifier = Modifier
                    .align(Alignment.Start)
                    .basicMarquee()
            )
            
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(1.dp, lighterPurple, RoundedCornerShape(4.dp))
                    .background(colors.background, RoundedCornerShape(4.dp))
            ) {
                GlideImage(
                    requestOptions = { RequestOptions.diskCacheStrategyOf(
                        DiskCacheStrategy.AUTOMATIC)},
                    modifier = Modifier
                        .height(124.dp),
                    imageModel = sponsersnew.imageurl,
                    contentDescription = "Sponsor",
                    contentScale = ContentScale.Inside,
                    alignment = Alignment.Center,
//                    shimmerParams = ShimmerParams(
//                        baseColor = if(isSystemInDarkTheme()) black else highWhite,
//                        highlightColor = if(isSystemInDarkTheme()) highBlack else white,
//                        durationMillis = 1500,
//                        dropOff = 1f,
//                        tilt = 20f
//                    )
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun subtopSponser(sponsersnew: sponsersnew) {
        Column {
            Text(
                text = sponsersnew.title,
                fontSize = 18.sp,
                fontFamily = futura,
                color = colors.onBackground,
                modifier = Modifier
                    .align(Alignment.Start)
                    .basicMarquee(),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(1.dp, lighterPurple, RoundedCornerShape(4.dp))
                    .background(colors.background, RoundedCornerShape(4.dp))
            ) {
                GlideImage(
                    requestOptions = { RequestOptions.diskCacheStrategyOf(
                        DiskCacheStrategy.AUTOMATIC)},
                    modifier = Modifier
                        .height(80.dp),
                    imageModel = sponsersnew.imageurl,
                    contentDescription = "Sponsor",
                    contentScale = ContentScale.Inside,
                    alignment = Alignment.Center,
//                    shimmerParams = ShimmerParams(
//                        baseColor = Color.Transparent,
//                        highlightColor = Color.LightGray,
//                        durationMillis = 350,
//                        dropOff = 0.65f,
//                        tilt = 20f
//                    )
                )
            }
        }
    }
}