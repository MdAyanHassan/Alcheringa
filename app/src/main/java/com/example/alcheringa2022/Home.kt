package com.example.alcheringa2022

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.fragment.app.activityViewModels
import com.example.alcheringa2022.Model.merchmodelforHome
import com.example.alcheringa2022.Model.ownEventBoxUiModel
import com.example.alcheringa2022.Model.viewModelHome
import com.example.alcheringa2022.databinding.FragmentHomeBinding
import com.example.alcheringa2022.ui.theme.*
import com.google.accompanist.pager.*
import com.google.common.io.Resources
import com.google.firebase.firestore.core.ActivityScope
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.*
import kotlin.math.absoluteValue


/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    lateinit var binding: FragmentHomeBinding
   val homeViewModel : viewModelHome by activityViewModels()

    val ranges= mutableSetOf<ClosedFloatingPointRange<Float>>()

    val datestate1 = mutableStateListOf<ownEventBoxUiModel>()
    val datestate2 = mutableStateListOf<ownEventBoxUiModel>()
    val datestate3 = mutableStateListOf<ownEventBoxUiModel>()
    var datestate:SnapshotStateList<ownEventBoxUiModel> = datestate1


//    val events=mutableListOf(
//
//            eventdetail(
//                    "JUBIN NAUTIYAL2",
//                    "Pro Nights",
//                    OwnTime(11,9,0),
//                    "ONLINE", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fjubin.jpg?alt=media&token=90983a9f-bd0d-483d-b2a8-542c1f1c0acb"
//            ),
//
//            eventdetail(
//                    "DJ SNAKE4",
//                    "Pro Nights",
//                OwnTime(11,12,0),
//                    "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
//            ),
//            eventdetail(
//                    "TAYLOR SWIFT6",
//                    "Pro Nights",
//                OwnTime(11,14,0),
//                    "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
//            )
//        ,
//
//        eventdetail(
//            "DJ SNAKE7",
//            "Pro Nights",
//            OwnTime(13,10,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
//        ),
//        eventdetail(
//            "TAYLOR SWIFT8",
//            "Pro Nights",
//            OwnTime(13,15,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
//        )
//        ,
//        eventdetail(
//            "TAYLOR SWIFT9",
//            "Pro Nights",
//            OwnTime(13,14,30),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f"
//        )
//
//
//
//    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }


        homeViewModel.getAllEvents()
        homeViewModel.getMerchHome()

       kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
            homeViewModel.allEventsWithLivedata.observe(requireActivity()){   data->
                homeViewModel.allEventsWithLive.clear()
                homeViewModel.allEventsWithLive.addAll(data)
            }
            homeViewModel.OwnEventsWithLive.observe(requireActivity()) { data ->
                datestate1.clear();
                datestate1.addAll(liveToWithY(data.filter { data -> data.eventdetail.starttime.date == 11 }))
                datestate2.clear();
                datestate2.addAll(liveToWithY(data.filter { data -> data.eventdetail.starttime.date == 12 }))
                datestate3.clear();
                datestate3.addAll(liveToWithY(data.filter { data -> data.eventdetail.starttime.date == 13 }))
            }


        }





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
        binding.account.setOnClickListener {
            startActivity(Intent(context,Account::class.java));

        }
        binding.compose1.setContent {
            Alcheringa2022Theme {
                val scrollState= rememberScrollState()
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(scrollState)) {
                    if (scrollState.value!=0){binding.logoAlcher.background= resources.getDrawable(R.drawable.add_icon)}
                    else{binding.logoAlcher.background= resources.getDrawable(R.drawable.ic_alcher_logo_top_nav)}
                    horizontalScroll(eventdetails = homeViewModel.allEventsWithLive)
                    Text(modifier = Modifier.padding(start = 20.dp, bottom = 12.dp, top = 48.dp), text = "ONGOING EVENTS", fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 18.sp)
                    Box(
                            modifier = Modifier
                                    .fillMaxWidth()

                    ) {
                        LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            items(homeViewModel.allEventsWithLive.filter { data-> data.isLive.value }) { dataeach -> Event_card(eventdetail = dataeach,homeViewModel) }
                        }

                    }
                    Text(modifier = Modifier.padding(start = 20.dp, bottom = 12.dp, top = 48.dp), text = "UPCOMING EVENTS", fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 18.sp)
                    Box(modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            items(homeViewModel.allEventsWithLive.filter { data-> !(data.isLive.value) }) { dataeach -> Event_card(eventdetail = dataeach,homeViewModel) }
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp, top = 48.dp)
                    ) {
                        val color= listOf(Color(0xffC80915), Color(0xffEE6337), Color(0xff11D3D3))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            itemsIndexed(homeViewModel.merchhome.filter { it.Available }) { index,dataeach -> merchBox(dataeach, color[index]) }
                        }
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, bottom = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text( text = "MY SCHEDULE", fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 18.sp)
                        Text(text = "See Full Schedule >", fontFamily = hk_grotesk, fontSize = 18.sp, fontWeight = FontWeight.W500, color =Color(0xffEE6337) )
                    }
                    mySchedule()





                }
            }
        }
    }

    fun liveToWithY(list:List<eventWithLive>): List<ownEventBoxUiModel> {
        val ranges= mutableListOf<ClosedFloatingPointRange<Float>>()
        val withylist= mutableListOf<ownEventBoxUiModel>()
        list.forEach{ data->
            var l = 0;

            val lengthdp= (data.eventdetail.durationInMin.toFloat() * (5f/3f))
            val xdis= (((data.eventdetail.starttime.hours-9)*100).toFloat() + (data.eventdetail.starttime.min.toFloat() * (5f/3f)) + 75f)

            for (range in ranges) {
                if (range.contains(xdis) or range.contains(xdis + lengthdp)) {
                    l += 1
                }
                if ((xdis..xdis + lengthdp).contains(range.start) and (xdis..xdis + lengthdp).contains(range.endInclusive))
                {
                    l += 1
                }
            }
            ranges.add((xdis..xdis+lengthdp))
            withylist.add(ownEventBoxUiModel(data,l))

        }
        return withylist


    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): Home {
            val fragment = Home()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

//    @Composable
//    fun upcomingEvents(eventdetail: eventdetail) {
//
//        Box() {
//
//            Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(8.dp),
//                    elevation = 5.dp
//            ) {
//                Box(
//                        modifier = Modifier
//                                .height(256.dp)
//                                .width(218.dp)
//                ) {
//                    Image(
//                            painter = painterResource(id = eventdetail.imgurl),
//                            contentDescription = "artist",
//                            contentScale = ContentScale.Crop,
//                            alignment = Alignment.Center,
//
//                            )
//
//                    Box(
//                            modifier = Modifier
//                                    .fillMaxSize()
//                                    .background(
//                                            brush = Brush.verticalGradient(
//                                                    colors = listOf(
//                                                            Color.Transparent,
//                                                            Color.Black
//                                                    ), startY = 300f
//                                            )
//                                    )
//                    )
//                    Box(
//                            modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(12.dp), contentAlignment = Alignment.BottomStart
//                    ) {
//                        Column {
//                            Text(text = eventdetail.artist, style = MaterialTheme.typography.h1)
//                            Spacer(modifier = Modifier.height(2.dp))
//                            Text(
//                                    text = eventdetail.category,
//                                    style = TextStyle(
//                                            color = colorResource(id = R.color.textGray),
//                                            fontFamily = clash,
//                                            fontWeight = FontWeight.W600,
//                                            fontSize = 14.sp
//                                    )
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                    text = "eventdetail.starttime",
//                                    style = TextStyle(
//                                            color = colorResource(id = R.color.textGray),
//                                            fontFamily = hk_grotesk,
//                                            fontWeight = FontWeight.Normal,
//                                            fontSize = 14.sp
//                                    )
//                            )
//                            Spacer(modifier = Modifier.height(2.dp))
//                            Row {
//                                Box(
//                                        modifier = Modifier
//                                                .height(20.dp)
//                                                .width(20.dp)
//                                ) {
//                                    Image(
//                                            painter = if (eventdetail.mode.contains("ONLINE")) {
//                                                painterResource(id = R.drawable.online)
//                                            } else {
//                                                painterResource(id = R.drawable.onground)
//                                            },
//                                            contentDescription = null, modifier = Modifier.fillMaxSize(),
//                                            alignment = Alignment.Center,
//                                            contentScale = ContentScale.Crop
//
//                                    )
//                                }
//                                Spacer(modifier = Modifier.width(4.dp))
//                                Text(
//                                        text = eventdetail.mode,
//                                        style = TextStyle(
//                                                color = colorResource(id = R.color.textGray),
//                                                fontFamily = hk_grotesk,
//                                                fontWeight = FontWeight.Normal,
//                                                fontSize = 14.sp
//                                        )
//                                )
//                            }
//                        }
//
//                    }
//
//
//                }
//            }
//        }
//    }






//    @Composable
//    fun ongoingEvents(eventdetail: eventdetail) {
//
//        Box() {
//
//            Card(modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(8.dp),
//                    elevation = 5.dp) {
//                Box(modifier = Modifier
//                        .height(256.dp)
//                        .width(218.dp)){
//                    Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop,
//                            alignment = Alignment.Center
//                    )
//                    Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
//
//                    Box(modifier = Modifier
//                            .fillMaxWidth()
//                            .height(21.dp)
//                            .background(
//                                    color = colorResource(
//                                            id = R.color.ThemeRed
//                                    )
//                            )
//                    ){ Text(text = "⬤ LIVE", color = Color.White, modifier = Modifier.align(alignment = Alignment.Center), fontSize = 12.sp)}
//                    Box(modifier = Modifier
//                            .fillMaxSize()
//                            .background(
//                                    brush = Brush.verticalGradient(
//                                            colors = listOf(
//                                                    Color.Transparent,
//                                                    Color.Black
//                                            ), startY = 300f
//                                    )
//                            ))
//                    Box(modifier = Modifier
//                            .fillMaxSize()
//                            .padding(12.dp), contentAlignment = Alignment.BottomStart){
//                        Column {
//                            Text(text = eventdetail.artist, style = MaterialTheme.typography.h1)
//                            Spacer(modifier = Modifier.height(2.dp))
//                            Text(text = eventdetail.category, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = clash,fontWeight = FontWeight.W600,fontSize = 14.sp))
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(text = eventdetail.starttime, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
//                            Spacer(modifier = Modifier.height(2.dp))
//                            Row {
//                                Box(modifier = Modifier
//                                        .height(20.dp)
//                                        .width(20.dp)) {
//                                    Image(
//                                            painter = if (eventdetail.mode.contains("ONLINE")) {
//                                                painterResource(id = R.drawable.online)
//                                            } else {
//                                                painterResource(id = R.drawable.onground)
//                                            },
//                                            contentDescription = null, modifier = Modifier.fillMaxSize(),alignment = Alignment.Center, contentScale =ContentScale.Crop
//                                    )
//                                }
//                                Spacer(modifier = Modifier.width(4.dp))
//                                Text(text = eventdetail.mode,style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
//                            }
//                        }
//
//                    }
//
//
//                }
//            }
//        }
//
//
//
//    }


    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun horizontalScroll(eventdetails:SnapshotStateList<eventWithLive>){

        Column() {

            val pagerState = rememberPagerState()
            LaunchedEffect(key1 = pagerState.currentPage) {
                launch {
                    delay(3000)
                    with(pagerState) {
                        val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
                        animateScrollToPage(
                            page = target,
//                            animationSpec = tween(
//                                durationMillis = 3000,
//                                easing = FastOutSlowInEasing
                            )

                    }
                }
            }
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
                                GlideImage(
                                        imageModel = eventdetails[page].eventdetail.imgurl,
                                        contentDescription = "artist",
                                        modifier= Modifier
                                            .fillMaxWidth()
                                            .height(473.dp),
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
                                            .height(473.dp), contentAlignment = Alignment.Center) {
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
                                                text = eventdetails[page].eventdetail.artist,
                                                color = Color.White,
                                                fontWeight = FontWeight.W700,
                                                fontSize = 78.sp,
                                                fontFamily = FontFamily(Font(R.font.morganitemedium))
                                        )
                                        Spacer(modifier = Modifier.height(11.dp))
                                        Text(
                                                text = eventdetails[page].eventdetail.category,
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
                                                    text = "${eventdetails[page].eventdetail.starttime.date} Feb, ${if(eventdetails[page].eventdetail.starttime.hours>12)"${eventdetails[page].eventdetail.starttime.hours-12}" else eventdetails[page].eventdetail.starttime.hours} ${if (eventdetails[page].eventdetail.starttime.hours>=12)"PM" else "AM"}",
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
                                                        painter = if (eventdetails[page].eventdetail.mode.contains("ONLINE")) {
                                                            painterResource(id = R.drawable.online)
                                                        } else {
                                                            painterResource(id = R.drawable.onground)
                                                        },
                                                        contentDescription = null,
                                                        modifier = Modifier.fillMaxSize(),
                                                        alignment = Alignment.Center,
                                                        contentScale = ContentScale.Crop

                                                )
                                            }
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                    text = eventdetails[page].eventdetail.mode,
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
   fun mySchedule(){
       var color1 by remember { mutableStateOf(orangeText)}
       var color2 by remember { mutableStateOf(greyText)}
       var color3 by remember { mutableStateOf(greyText)}

       Column() {

           Row(
               Modifier
                   .fillMaxWidth()
                   .padding(horizontal = 32.dp), horizontalArrangement = Arrangement.SpaceBetween) {
               Text(text = "Day1", fontWeight = FontWeight.W700, fontFamily = clash, color = color1,
                   modifier = Modifier.clickable { color1= orangeText;color2= greyText;color3=
                       greyText
                       ranges.clear()
                       datestate= datestate1
                   })

               Text(text = "Day2", fontWeight = FontWeight.W700, fontFamily = clash, color = color2,
                   modifier = Modifier.clickable { color1= greyText;color2= orangeText;color3= greyText;
                       ranges.clear()
                       datestate=datestate2
               })

               Text(text = "Day3", fontWeight = FontWeight.W700, fontFamily = clash, color = color3,
                   modifier = Modifier.clickable { color1= greyText;color2= greyText;color3=
                       orangeText
                       ranges.clear()
                       datestate=datestate3
                   })
               
           }
           Spacer(modifier = Modifier.height(16.dp))
           scheduleBox(addedList = datestate)
       }



   }




    @Composable
    fun scheduleBox(addedList:SnapshotStateList<ownEventBoxUiModel>){
        Box(
            Modifier
                .width(1550.dp)
                .height(279.dp)
                .background(color = Color.Black)
                .horizontalScroll(rememberScrollState())) {
            Row(
                Modifier
                    .width(1550.dp)
                    .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (time in 9..11) {
                    Column(
                        Modifier
                            .width(50.dp)
                            .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "$time AM",
                            style = TextStyle(
                                color = colorResource(id = R.color.textGray),
                                fontFamily = clash,
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp
                            )
                        )
                        Canvas(
                            modifier = Modifier
                                .width(5.dp)
                                .height(260.dp)
                        ) {
                            drawLine(
                                color = Color.DarkGray,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = Stroke.DefaultMiter
                            )
                        }

                    }

                }

                Column(
                    Modifier
                        .width(50.dp)
                        .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "12 PM",
                        style = TextStyle(
                            color = colorResource(id = R.color.textGray),
                            fontFamily = clash,
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp
                        )
                    )
                    Canvas(
                        modifier = Modifier
                            .width(5.dp)
                            .height(260.dp)
                    ) {
                        drawLine(
                            color = Color.DarkGray,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = Stroke.DefaultMiter
                        )
                    }
                }
                for (time in 1..11) {
                    Column(
                        Modifier
                            .width(50.dp)
                            .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "$time PM",
                            style = TextStyle(
                                color = colorResource(id = R.color.textGray),
                                fontFamily = clash,
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp
                            )
                        )
                        Canvas(
                            modifier = Modifier
                                .width(5.dp)
                                .height(260.dp)
                        ) {
                            drawLine(
                                color = Color.DarkGray,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = Stroke.DefaultMiter
                            )
                        }

                    }

                }

            }


            addedList.forEach{data->userBox(eventdetail = data)}


        }



    }
    @Composable
    fun userBox(eventdetail: ownEventBoxUiModel){
         val color= listOf(Color(0xffC80915), Color(0xff1E248D), Color(0xffEE6337)).random()

        val lengthdp= (eventdetail.eventWithLive.eventdetail.durationInMin.toFloat() * (5f/3f))
        val xdis= (((eventdetail.eventWithLive.eventdetail.starttime.hours-9)*100).toFloat() + (eventdetail.eventWithLive.eventdetail.starttime.min.toFloat() * (5f/3f)) + 75f)
            val ydis= (30+(eventdetail.ydis*70))

        Box(
            Modifier
                .offset(xdis.dp-2.dp, ydis.dp)
                .height(58.dp)
                .width(lengthdp.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(12.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
                Text(
                    text = eventdetail.eventWithLive.eventdetail.artist,
                    color = Color.White,
                    fontWeight = FontWeight.W700,
                    fontFamily = clash,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = eventdetail.eventWithLive.eventdetail.category,
                    style = TextStyle(
                        color = colorResource(id = R.color.textGray),
                        fontFamily = clash,
                        fontWeight = FontWeight.W600,
                        fontSize = 12.sp
                    )
                )
            }
        }
        ranges.add((xdis..xdis+lengthdp))



    }


    @Composable
    fun merchBox(merch: merchmodelforHome, color: Color){
        Card(modifier = Modifier.wrapContentWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = 0.dp, backgroundColor = color) {
            Box(modifier = Modifier
                .height(218.dp)
                .width(350.dp)){

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xff000111)
                            ), startY = 0f
                        )
                    ))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 16.dp, top = 24.dp), horizontalArrangement = Arrangement.SpaceBetween) {



                    Column(
                        Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()) {
                        Text(
                            text = merch.Name.uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.W700,
                            fontSize = 46.sp,
                            fontFamily = FontFamily(Font(R.font.morganitemedium))
                        )
                        Text(text = merch.Type.uppercase(), style = MaterialTheme.typography.h1)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Out now!", fontFamily = clash, fontSize = 16.sp, fontWeight = FontWeight.W500, color = Color.LightGray)
                        Spacer(modifier = Modifier.height(35.dp))
                        Text(text = "BUY NOW", color = Color.White, fontFamily = clash, fontWeight = FontWeight.W700, fontSize = 16.sp)

                    }

                    GlideImage(modifier = Modifier
                        .width(241.dp)
                        .height(257.dp),
                    imageModel = merch.Image, contentDescription = "merch", contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    shimmerParams = ShimmerParams(
                        baseColor = Color.Black,
                        highlightColor = Color.LightGray,
                        durationMillis = 350,
                        dropOff = 0.65f,
                        tilt = 20f
                    ),failure = {
                        Box(modifier= Modifier
                            .width(241.dp)
                            .height(257.dp), contentAlignment = Alignment.Center) {
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
                )}





    }}}





}