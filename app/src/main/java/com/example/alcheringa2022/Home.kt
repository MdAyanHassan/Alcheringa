package com.example.alcheringa2022

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.fragment.app.Fragment
import com.example.alcheringa2022.databinding.FragmentHomeBinding
import com.example.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.example.alcheringa2022.ui.theme.clash
import com.example.alcheringa2022.ui.theme.hk_grotesk
import com.google.accompanist.pager.*
import org.intellij.lang.annotations.JdkConstants
import kotlin.math.absoluteValue

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val events = mutableListOf(

        eventdetail(
            "JUBIN NAUTIYAL",
            "Pro Nights",
            "12 Feb, 4 PM",
            "Online", R.drawable.jubin
        ),
        "JUBIN NAUTIYAL",
        "Pro Nights",
        "12 Feb, 4 PM",
        "ONLINE", R.drawable.jubin
    ),

        eventdetail(
            "DJ SNAKE",
            "Pro Nights",
            "11 Feb, 4 PM",
            "ON GROUND", R.drawable.djsnake
        ),
        eventdetail(
            "TAYLOR SWIFT",
            "Pro Nights",
            "13 Feb, 4 PM",
            "Online", R.drawable.taylor
        )
        "Pro Nights",
        "13 Feb, 4 PM",
        "ON GROUND", R.drawable.taylor
    )


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return (binding.root)

//        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.compose1.setContent {
            Alcheringa2022Theme() {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(events) { dataEach -> UpcomingEvents(eventdetail = dataEach) }

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                horizontalScroll(eventdetails = events)

                Text(modifier = Modifier.padding(start = 20.dp, bottom = 12.dp, top = 48.dp), text = "ONGOING EVENTS", fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 18.sp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                             LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                      ) {
                                             items(events) { dataeach -> ongoingEvents(eventdetail = dataeach) }
                                          }

                }
                Text(modifier = Modifier.padding(start = 20.dp, bottom = 12.dp, top = 48.dp), text = "UPCOMING EVENTS", fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 18.sp)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    ) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(events) { dataeach -> upcomingEvents(eventdetail = dataeach) }
                    }

                }
            }
            }
        }
    }

    @Composable
    fun UpcomingEvents(eventdetail: eventdetail) {

        Box {
        Box() {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = 5.dp
            ) {
                Box(
                    modifier = Modifier
                        .height(256.dp)
                        .width(218.dp)
                ) {
                    Image(
                        painter = painterResource(id = eventdetail.imgurl),
                        contentDescription = "artist",
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ), startY = 300f
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Text(text = eventdetail.artist, style = MaterialTheme.typography.h1)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = eventdetail.time,
                                style = TextStyle(
                                    color = colorResource(id = R.color.textGray),
                                    fontFamily = clash,

                            .padding(12.dp), contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Text(text = eventdetail.artist, style = MaterialTheme.typography.h1)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = eventdetail.category,
                                style = TextStyle(
                                    color = colorResource(id = R.color.textGray),
                                    fontFamily = clash,
                                    fontWeight = FontWeight.W600,
                                    fontSize = 14.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = eventdetail.time,
                                style = TextStyle(
                                    color = colorResource(id = R.color.textGray),
                                    fontFamily = hk_grotesk,

                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(2.dp))
                            Row {
                                Box(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp)
                                ) {
                                    Image(
                                        painter = if (eventdetail.mode.contains("ONLINE")) {
                                            painterResource(id = R.drawable.online)
                                        } else {
                                            painterResource(id = R.drawable.onground)
                                        },
                                        contentDescription = null, modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = eventdetail.mode,
                                    style = TextStyle(
                                        color = colorResource(id = R.color.textGray),
                                        fontFamily = hk_grotesk,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }

                    }


                }
            }
        }
    }






    @Composable
    fun ongoingEvents(eventdetail: eventdetail) {

        Box() {

            Card(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = 5.dp) {
                Box(modifier = Modifier
                    .height(256.dp)
                    .width(218.dp)){
                    Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(21.dp)
                        .background(
                            color = colorResource(
                                id = R.color.ThemeRed
                            )
                        )
                    ){ Text(text = "⬤ LIVE", color = Color.White, modifier = Modifier.align(alignment = Alignment.Center), fontSize = 12.sp)}
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ), startY = 300f
                            )
                        ))
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp), contentAlignment = Alignment.BottomStart){
                        Column {
                            Text(text = eventdetail.artist, style = MaterialTheme.typography.h1)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = eventdetail.category, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = clash,fontWeight = FontWeight.W600,fontSize = 14.sp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = eventdetail.time, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                            Spacer(modifier = Modifier.height(2.dp))
                            Row {
                                Box(modifier = Modifier
                                    .height(20.dp)
                                    .width(20.dp)) {
                                    Image(
                                        painter = if (eventdetail.mode.contains("ONLINE")) {
                                            painterResource(id = R.drawable.online)
                                        } else {
                                            painterResource(id = R.drawable.onground)
                                        },
                                        contentDescription = null, modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = eventdetail.mode,style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                            }
                        }
                    }

                }

                }
            }
        }



    }

    @OptIn(ExperimentalPagerApi::class)

    @Composable
    fun horizontalScroll(eventdetails:List<eventdetail>){

        Column() {
            val pagerState = rememberPagerState()
            HorizontalPager(
                count = eventdetails.size, modifier = Modifier
                    .fillMaxWidth()
                    .height(473.dp), state = pagerState
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
                    Box() {

                        Card(
                            modifier = Modifier.fillMaxWidth(),

                            elevation = 5.dp
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(473.dp)
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = eventdetails[page].imgurl),
                                    contentDescription = "artist",
                                    contentScale = ContentScale.Crop
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
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp), contentAlignment = Alignment.BottomStart
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = eventdetails[page].artist,
                                            color = Color.White,
                                            fontWeight = FontWeight.W700,
                                            fontSize = 78.sp,
                                            fontFamily = FontFamily(Font(R.font.morganitemedium))
                                        )
                                        Spacer(modifier = Modifier.height(11.dp))
                                        Text(
                                            text = eventdetails[page].category,
                                            style = TextStyle(
                                                color = colorResource(id = R.color.textGray),
                                                fontFamily = clash,
                                                fontWeight = FontWeight.W600,
                                                fontSize = 16.sp
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(11.dp))

                                        Row {
                                            Text(
                                                text = eventdetails[page].time,
                                                style = TextStyle(
                                                    color = colorResource(id = R.color.textGray),
                                                    fontFamily = hk_grotesk,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 14.sp
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(11.dp))
                                            Box(
                                                modifier = Modifier
                                                    .height(20.dp)
                                                    .width(20.dp)
                                            ) {
                                                Image(
                                                    painter = if (eventdetails[page].mode.contains("ONLINE")) {
                                                        painterResource(id = R.drawable.online)
                                                    } else {
                                                        painterResource(id = R.drawable.onground)
                                                    },
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = eventdetails[page].mode,
                                                style = TextStyle(
                                                    color = colorResource(id = R.color.textGray),
                                                    fontFamily = hk_grotesk,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 14.sp
                                                )
                                            )
                                        }
                                    }

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

    @Composable
    @Preview
    fun PreviewItem(){
        Alcheringa2022Theme() {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(events) { dataEach -> UpcomingEvents(eventdetail = dataEach) }
            }
        }
    }
}

