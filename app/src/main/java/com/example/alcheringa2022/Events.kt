package com.example.alcheringa2022

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.alcheringa2022.Database.ScheduleDatabase
import com.example.alcheringa2022.Model.viewModelHome
import com.example.alcheringa2022.databinding.FragmentEventsBinding
import com.example.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.example.alcheringa2022.ui.theme.clash
import com.example.alcheringa2022.ui.theme.hk_grotesk
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import java.util.*


class Events : Fragment() {

    private lateinit var fgm:FragmentManager
    private lateinit var binding: FragmentEventsBinding
    val homeViewModel: viewModelHome by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fgm=parentFragmentManager
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_events, container, false)
        binding = FragmentEventsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scheduleDatabase=ScheduleDatabase(context)
        val eventslist=scheduleDatabase.schedule;
        binding.account.setOnClickListener {
            startActivity(Intent(context,Account::class.java));

        }

        binding.eventsCompose.setContent {
            Full_view()
        }
    }

    @Composable
    @Preview
    fun Full_view() {
        Alcheringa2022Theme {
            Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                    /*.background(Color.Black)*/
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                Events_row(heading = "Pronites")
                Events_row(heading = "Proshows")
                Events_row(heading = "Creator's Camp")
                Events_row(heading = "Humor Fest")
                Column(modifier =Modifier.padding(horizontal = 20.dp, vertical = 12.dp) ){
                    Text(text = "COMPETITIONS", fontWeight = FontWeight.W600, fontSize = 16.sp, fontFamily = clash,color = Color.White)
                    Spacer(modifier = Modifier.height(14.dp))
                    imgcomp()

                }

            }
        }
    }

    @Composable
    fun Events_row(heading: String) {
        Box(modifier =Modifier.padding(horizontal = 20.dp, vertical = 12.dp) ){Text(text = heading.uppercase(Locale.getDefault()), fontWeight = FontWeight.W600, fontSize = 16.sp, fontFamily = clash, color = Color.White)}
        LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
        ) { items(homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.category.replace("\\s".toRegex(), "").uppercase()== heading.replace("\\s".toRegex(), "").uppercase()})
        {dataEach -> context?.let { Event_card(eventdetail = dataEach,homeViewModel, it, fgm) } } }

        Spacer(modifier = Modifier.height(24.dp))
    }

    @Composable
    fun imgcomp() {

        Box(
            modifier = Modifier
                .height(256.dp)
                .fillMaxWidth().clip(RoundedCornerShape(8.dp)).clickable {
                    fgm
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView,CompetitionsFragment() ).addToBackStack(null)
                        .commit()
                }
        ) {
            GlideImage(
                imageModel = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitionHeader.png?alt=media&token=7f350d9e-dbad-427a-822f-e3586bfa5e4c",
                contentDescription = "imghead",
                modifier= Modifier
                    .fillMaxWidth()
                    .height(256.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                shimmerParams = ShimmerParams(
                    baseColor = Color.Black,
                    highlightColor = Color.LightGray,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),failure = {
                    Box(modifier= Modifier
                        .fillMaxWidth()
                        .height(256.dp), contentAlignment = Alignment.Center) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), startY = 100f
                        )
                    )
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(17.dp), contentAlignment = Alignment.BottomStart){
                Text(text = "SEE ALL COMPETITIONS", color = Color.White, fontFamily = clash, fontWeight = FontWeight.W700, fontSize = 18.sp)
            }


        }
    }
}

