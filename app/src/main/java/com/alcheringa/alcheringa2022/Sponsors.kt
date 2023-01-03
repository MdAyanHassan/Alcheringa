package com.alcheringa.alcheringa2022

import androidx.appcompat.app.AppCompatActivity
import com.alcheringa.alcheringa2022.onItemClick
import com.alcheringa.alcheringa2022.Model.Sponsor_model
import com.alcheringa.alcheringa2022.SponsorsAdapter
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.ImageButton
import com.alcheringa.alcheringa2022.LoaderView
import android.os.Bundle
import com.alcheringa.alcheringa2022.R
import java.util.ArrayList
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import android.net.Uri
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Model.sponsersnew
import com.alcheringa.alcheringa2022.databinding.ActivitySponsorsBinding
import com.alcheringa.alcheringa2022.ui.theme.clash
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

class Sponsors : AppCompatActivity() {
    lateinit var binding: ActivitySponsorsBinding
    lateinit var firestore:FirebaseFirestore
    lateinit var loaderView:LoaderView
    var sponserlist= mutableListOf<sponsersnew>()
    val headersponsers= mutableStateListOf<sponsersnew>()
    val generalsponsers=mutableStateListOf<sponsersnew>()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySponsorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
      firestore = FirebaseFirestore.getInstance()
       loaderView = binding.dotsProgress
        loaderView.setVisibility(View.VISIBLE)
        sponserlist.clear()
        populate_sponsors()








        binding.sponsorCv.setContent {

            LazyVerticalGrid(cells=GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                items (span = {GridItemSpan(2)}, count = 1){
                        Spacer(modifier = Modifier.height(80.dp))
                }
                items (span = {GridItemSpan(2)}, count = headersponsers.size){
                   index ->  topSponser(sponsersnew = headersponsers[index])
                }
                items (span = {GridItemSpan(2)}, count = 1){
                    Spacer(modifier = Modifier.height(20.dp))
                }

                items ( count = generalsponsers.size){
                        index ->  subtopSponser(sponsersnew = generalsponsers[index])
                }

}


        }


    }

    private fun populate_sponsors() {
        firestore!!.collection("SponsersNew").get().addOnSuccessListener { task ->
            for (documentSnapshot in task) {
                Log.d("sponsor", documentSnapshot.getString("imageurl")!!)
                sponserlist.add(
                    documentSnapshot.toObject(sponsersnew::class.java)
                )
            }
           headersponsers.addAll(sponserlist.filter { sponsersnew -> sponsersnew.heading })
             generalsponsers.addAll(sponserlist.filter { sponsersnew -> !sponsersnew.heading })
            loaderView.visibility = View.GONE
        }.addOnFailureListener{Log.d("sponsor", "failed")}
    }

    @Composable
    fun topSponser(sponsersnew:sponsersnew){
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = sponsersnew.title, fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 16.sp)
               Spacer(modifier = Modifier.height(5.dp))
                GlideImage(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                    imageModel = sponsersnew.imageurl,
                    contentDescription = "merch",
                    contentScale = ContentScale.FillHeight,
                    alignment = Alignment.Center,
                    shimmerParams = ShimmerParams(
                        baseColor = Color.Transparent,
                        highlightColor = Color.LightGray,
                        durationMillis = 350,
                        dropOff = 0.65f,
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
//                            Column(
//                                Modifier
//                                    .fillMaxWidth()
//                                    .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
//                                Image(
//                                    modifier = Modifier
//                                        .width(60.dp)
//                                        .height(60.dp),
//                                    painter = painterResource(
//                                        id = R.drawable.ic_sad_svgrepo_com
//                                    ),
//                                    contentDescription = null
//                                )
//                                Spacer(modifier = Modifier.height(10.dp))
//                                Text(
//                                    text = "Image Request Failed",
//                                    style = TextStyle(
//                                        color = Color(0xFF747474),
//                                        fontFamily = hk_grotesk,
//                                        fontWeight = FontWeight.Normal,
//                                        fontSize = 12.sp
//                                    )
//                                )
//                            }
                        }

                    }
                )
                Spacer(modifier = Modifier.height(15.dp))

            }
        }


    }


    @Composable
    fun subtopSponser(sponsersnew: sponsersnew){
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(5.dp)), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(modifier = Modifier.fillMaxWidth(), text = sponsersnew.title, fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 16.sp, textAlign = TextAlign.Center)
           Spacer(modifier = Modifier.height(5.dp))
            GlideImage(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
                imageModel = sponsersnew.imageurl,
                contentDescription = "merch",
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.Center,
                shimmerParams = ShimmerParams(
                    baseColor = Color.Transparent,
                    highlightColor = Color.LightGray,
                    durationMillis = 350,
                    dropOff = 0.65f,
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
//                            Column(
//                                Modifier
//                                    .fillMaxWidth()
//                                    .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
//                                Image(
//                                    modifier = Modifier
//                                        .width(60.dp)
//                                        .height(60.dp),
//                                    painter = painterResource(
//                                        id = R.drawable.ic_sad_svgrepo_com
//                                    ),
//                                    contentDescription = null
//                                )
//                                Spacer(modifier = Modifier.height(10.dp))
//                                Text(
//                                    text = "Image Request Failed",
//                                    style = TextStyle(
//                                        color = Color(0xFF747474),
//                                        fontFamily = hk_grotesk,
//                                        fontWeight = FontWeight.Normal,
//                                        fontSize = 12.sp
//                                    )
//                                )
//                            }
                    }

                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    }

