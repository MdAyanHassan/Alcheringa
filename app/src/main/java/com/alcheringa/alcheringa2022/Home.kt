package com.alcheringa.alcheringa2022


import ViewPagernew
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.lerp
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.*
import com.alcheringa.alcheringa2022.databinding.FragmentHomeBinding
import com.alcheringa.alcheringa2022.ui.theme.*
import com.google.accompanist.pager.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.*
import java.util.*
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
    lateinit var fm:FragmentManager
    lateinit var binding: FragmentHomeBinding
    lateinit var navController :NavController
    lateinit var  scheduleDatabase:ScheduleDatabase
    val homeViewModel : viewModelHome by activityViewModels()
    val ranges= mutableSetOf<ClosedFloatingPointRange<Float>>()

    val datestate1 = mutableStateListOf<ownEventBoxUiModel>()
    val datestate2 = mutableStateListOf<ownEventBoxUiModel>()
    val datestate3 = mutableStateListOf<ownEventBoxUiModel>()
    lateinit var datestate:MutableState<Int>
    var onActiveDel= mutableStateOf(false)
    var isdragging=mutableStateOf(false)
    var home=false;
    public val artistLive = MutableLiveData<String>()


    var firebaseFirestore: FirebaseFirestore? = null
    var sharedPreferences: SharedPreferences? = null

    lateinit var eventfordes: eventWithLive
    lateinit var similarlist:MutableList<eventWithLive>

//    val events=mutableListOf(

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

//    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
        fm=parentFragmentManager

        scheduleDatabase=ScheduleDatabase(context)
        homeViewModel.fetchlocaldbandupdateownevent(scheduleDatabase)



        homeViewModel.getfeaturedEvents()
        homeViewModel.getAllEvents()
        homeViewModel.getMerchHome()
        homeViewModel.getMerchMerch()
//        Log.d("vipin",eventslist.toString());
//        homeViewModel.pushEvents(homeViewModel.AllEvents)







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


        var unseen_notif_count = 0

        firebaseFirestore = FirebaseFirestore.getInstance()
        sharedPreferences = activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)

        firebaseFirestore!!.collection("Notification").get().addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot> ->
            if (task.isSuccessful) {
                val notifs = task.result.size()
                Log.d("Notification Count", "No of notifications: " + notifs)
                val seen_notifs = sharedPreferences?.getInt("seen_notifs_count", 0)
                Log.d("seen notification", seen_notifs.toString());
                unseen_notif_count = notifs - seen_notifs!!

                if (unseen_notif_count <= 0) {
                    binding.notificationCount.visibility = View.INVISIBLE
                } else if (unseen_notif_count <= 9) {
                    binding.notificationCount.visibility = View.VISIBLE
                    binding.notificationCount.text = unseen_notif_count.toString()
                } else {
                    binding.notificationCount.visibility = View.VISIBLE
                    binding.notificationCount.text = "9+"
                }
            } else {
                Log.d("Error", "Error loading notification count", task.exception)
            }
        })


        binding.account.setOnClickListener {
//            startActivity(Intent(context,Account::class.java));
            (activity as MainActivity).drawer.openDrawer(Gravity.RIGHT)
        }

        binding.pass.setOnClickListener{
            startActivity(Intent(context,
                NotificationActivity::class.java));
        }
        binding.compose1.setContent {
            MyContent();
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
            homeViewModel.allEventsWithLivedata.observe(requireActivity()){   data->
                homeViewModel.allEventsWithLive.clear()
                homeViewModel.allEventsWithLive.addAll(data)
                homeViewModel.upcomingEventsLiveState.clear()
                homeViewModel.upcomingEventsLiveState.addAll(data)
                homeViewModel.forYouEvents.clear()
                homeViewModel.forYouEvents.addAll(homeViewModel.allEventsWithLive.shuffled().take(7))
            }
            homeViewModel.featuredEventsWithLivedata.observe(requireActivity()){   data->
                homeViewModel.featuredEventsWithLivestate.clear()
                homeViewModel.featuredEventsWithLivestate.addAll(data)

            }
            homeViewModel.OwnEventsWithLive.observe(requireActivity()) { data ->
                homeViewModel.OwnEventsWithLiveState.clear()
                homeViewModel.OwnEventsWithLiveState.addAll(data.map{eventWithLive(it)})
                homeViewModel.OwnEventsLiveState.clear()
                homeViewModel.OwnEventsLiveState.addAll(data)
                homeViewModel.OwnEventsWithLiveState.sortedBy{ data -> (data.eventdetail.starttime.date * 24 * 60 + ((data.eventdetail.starttime.hours * 60)).toFloat() + (data.eventdetail.starttime.min.toFloat())) }


//                datestate1.clear();
//                datestate1.addAll(liveToWithY(data.filter { data -> data.starttime.date == 11 }))
//                datestate2.clear();
//                datestate2.addAll(liveToWithY(data.filter { data -> data.starttime.date == 12 }))
//                datestate3.clear();
//                datestate3.addAll(liveToWithY(data.filter { data -> data.starttime.date == 13 }))
            }


        }
    }





    fun liveToWithY(list:List<eventdetail>): List<ownEventBoxUiModel> {
        val ranges= mutableListOf<ClosedFloatingPointRange<Float>>()
        ranges.clear()
        val withylist= mutableListOf<ownEventBoxUiModel>()
        list.sortedBy { (((it.starttime.hours-9)*100).toFloat() + (it.starttime.min.toFloat() * (5f/3f)) + 75f)}
        list.forEach{ data->
            var l = 0;
            val lengthdp= (data.durationInMin.toFloat() * (5f/3f))
            val xdis= (((data.starttime.hours-9)*100).toFloat() + (data.starttime.min.toFloat() * (5f/3f)) + 75f)

            for (range in ranges) {
                if (range.contains(xdis) or range.contains(xdis + lengthdp) or ((xdis..xdis + lengthdp).contains(range.start) and (xdis..xdis + lengthdp).contains(range.endInclusive))) {
                    l += 1
                }
               if( range.start==xdis+lengthdp){l-=1}
                if( range.endInclusive==xdis){l-=1}
                if( (range.start==xdis+lengthdp) and (range.endInclusive==xdis)){l+=1}

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
    fun horizontalScroll(eventdetails:List<eventWithLive>){
        val count = eventdetails.size
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
                Column() {

                    val pagerState = rememberPagerState()
                    //            LaunchedEffect(Unit) {
                    //                while(true) {
                    //                    yield()
                    //                    delay(3000)
                    //                    pagerState.animateScrollToPage(
                    //                        page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                    //                        animationSpec = tween(1000)
                    //                    )
                    //                }
                    //            }
//                    LaunchedEffect(key1 = pagerState.currentPage) {
//                        launch {
//
//                            delay(3000)
//                            with(pagerState) {
//                                val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
//                                tween<Float>(
//                                    durationMillis = 300,
//                                    easing = FastOutSlowInEasing
//                                )
//                                animateScrollToPage(page = target)
//
//                            }
//                        }
//                    }
                    val hpm= if(isSystemInDarkTheme()) Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .coloredShadow(colors.onBackground, 0.25f, 16.dp, 20.dp, -5.dp, -5.dp)
                    else Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                    HorizontalPager(
                        count = count,
                        modifier = hpm,
                        state = pagerState,
                        contentPadding = PaddingValues(top = 0.dp, start = 5.dp, end = 40.dp, bottom = 0.dp),
                        itemSpacing = (5.dp),

                        ) { page ->


                        Card(
                            modifier = Modifier.clip(RoundedCornerShape(16.dp) )
                             ,
                            border = BorderStroke(1.5.dp, colors.onBackground),
                            shape = RoundedCornerShape(16.dp)

                        ) {
                            Box() {

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(0.781f)

                                        .graphicsLayer {
                                            // Calculate the absolute offset for the current page from the
                                            // scroll position. We use the absolute value which allows us to mirror
                                            // any effects for both directions
                                            val pageOffset =
                                                calculateCurrentOffsetForPage(page).absoluteValue
                                            Log.d("pageoffsetpager", "$pageOffset")

                                            transformOrigin = TransformOrigin(
                                                if (calculateCurrentOffsetForPage(page) >= 0) 1f else
                                                    0f,
                                                0f
                                            )
                                            // We animate the scaleX + scaleY, between 85% and 100%
                                            lerp(
                                                start = 0.11f,
                                                stop = 1f,
                                                fraction = 1f - (pageOffset.coerceIn(0.0f, 1f))
                                            ).also { scale ->
                                                if (pageOffset % 1f != 0f) {
                                                    scaleX =
//                                                    if(calculateCurrentOffsetForPage(page)==0.0f) 1f else
                                                        scale
                                                }
                                                //scaleY = scale
                                            }


                                            //                                     We animate the alpha, between 50% and 100%
                                            //                                    alpha = lerp(
                                            //                                            start = 0.5f,
                                            //                                            stop = 1f,
                                            //                                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                            //                                    )
                                        },


                                    ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth()
                                    ) {
                                        GlideImage(
                                            imageModel = eventdetails[page].eventdetail.imgurl,
                                            contentDescription = "artist",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight()
                                                .clickable {
                                                    val arguments =
                                                        bundleOf("Artist" to eventdetails[page].eventdetail.artist)
                                                    findNavController(this@Home).navigate(
                                                        R.id.action_home2_to_events_Details_Fragment,
                                                        arguments
                                                    )
                                                },
                                            alignment = Alignment.Center,
                                            contentScale = ContentScale.Crop,
                                            shimmerParams = ShimmerParams(
                                                baseColor = blackbg,
                                                highlightColor = Color.LightGray,
                                                durationMillis = 350,
                                                dropOff = 0.65f,
                                                tilt = 20f
                                            ), failure = {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    val composition by rememberLottieComposition(
                                                        LottieCompositionSpec.RawRes(R.raw.comingsoon)
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
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                            //                                            .background(
                                            //                                                brush = Brush.verticalGradient(
                                            //                                                    colors = listOf(
                                            //                                                        Color.Transparent,
                                            //                                                        black,
                                            //                                                    ),
                                            //                                                    startY = with(LocalDensity.current) { 100.dp.toPx() }
                                            //                                                )
                                            //                                            )
                                        )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(4.dp)
                                                ,
                                            contentAlignment = Alignment.BottomStart
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight()
                                                    .graphicsLayer {
                                                        // Calculate the absolute offset for the current page from the
                                                        // scroll position. We use the absolute value which allows us to mirror
                                                        // any effects for both directions
                                                        val pageOffset =
                                                            calculateCurrentOffsetForPage(page).absoluteValue

                                                        transformOrigin = TransformOrigin(0.5f, 1f)
                                                        // We animate the scaleX + scaleY, between 85% and 100%
                                                        lerp(
                                                            start = 0f,
                                                            stop = 1f,
                                                            fraction = 1f - (pageOffset.coerceIn(
                                                                0f,
                                                                1f
                                                            ))
                                                        ).also { scale ->
                                                            if (pageOffset % 1f != 0f) {
                                                                scaleY = scale
                                                            }
                                                            //scaleY = scale

                                                        }
                                                    }
                                                    .clip(
                                                        RoundedCornerShape(
                                                            topStart = 16.dp,
                                                            topEnd = 0.dp,
                                                            bottomStart = 16.dp, bottomEnd = 16.dp
                                                        )
                                                    )
                                                    .background(Color(0xff0e0e0f))
                                                    .padding(
                                                        top = 12.dp,
                                                        bottom = 20.dp,
                                                        start = 20.dp
                                                    )



                                                ,
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                MarqueeText(
                                                    text = "${eventdetails[page].eventdetail.artist}",
                                                    color = Color.White,
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 36.sp,
                                                    fontFamily = aileron,
                                                    textAlign = TextAlign.Start,
                                                    gradientEdgeColor = Color.Transparent,
                                                )
                                                Spacer(modifier = Modifier.height(0.dp))
//                                                Text(
//                                                    text = eventdetails[page].eventdetail.category,
//                                                    style = TextStyle(
//                                                        color = colorResource(id = R.color.White),
//                                                        fontFamily = aileron,
//                                                        fontWeight = FontWeight.Normal,
//                                                        fontSize = 16.sp
//                                                    )
//                                                )
//                                                Spacer(modifier = Modifier.height(4.dp))

                                                Row() {
                                                    Text(
                                                        text = "${eventdetails[page].eventdetail.starttime.date} Mar, ${if (eventdetails[page].eventdetail.starttime.hours > 12) "${eventdetails[page].eventdetail.starttime.hours - 12}" else eventdetails[page].eventdetail.starttime.hours}${if (eventdetails[page].eventdetail.starttime.min != 0) ":${eventdetails[page].eventdetail.starttime.min}" else ""} ${if (eventdetails[page].eventdetail.starttime.hours >= 12) "PM" else "AM"} |",
                                                        style = TextStyle(
                                                            color = white,
                                                            fontFamily = aileron,
                                                            fontWeight = FontWeight.Normal,
                                                            fontSize = 16.sp,
                                                        ),

                                                    )

                                                    //Spacer(modifier = Modifier.width(4.dp))
                                                    MarqueeText(
                                                        text = " ${eventdetails[page].eventdetail.venue}",
                                                        style = TextStyle(
                                                            color = white,
                                                            fontFamily = aileron,
                                                            fontWeight = FontWeight.Normal,
                                                            fontSize = 16.sp
                                                        ),gradientEdgeColor = Color.Transparent
                                                    )
                                                }
                                            }

                                        }


                                    }
                                }
                            }
                        }
                    }

//                    Box(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .align(Alignment.CenterHorizontally)
//                            .padding(vertical = 8.dp)
//                    ) {
//                        Card(
//                            modifier = Modifier
//
//                                .width(184.dp)
//                                .height(20.dp),
//                            shape = RoundedCornerShape(36.dp),
//                            backgroundColor = midWhite
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .fillMaxHeight()
//                            ) {
//                                HorizontalPagerIndicator(
//
//                                    pagerState = pagerState,
//                                    activeColor = colors.onBackground,
//                                    inactiveColor = midWhite,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .fillMaxHeight()
//                                        .padding(3.dp),
//                                    indicatorShape = RoundedCornerShape(36.dp),
//                                    indicatorWidth = (178 / 5).dp
//
//                                )
//                            }
//                        }
//                    }
                }
            }


    }


//   @Composable
//   fun mySchedule(){
//       var color1 by remember { mutableStateOf(orangeText)}
//       var color2 by remember { mutableStateOf(greyText)}
//       var color3 by remember { mutableStateOf(greyText)}
//       datestate=remember{ mutableStateOf(1)}
//       Column() {
//
//           Row(
//               Modifier
//                   .fillMaxWidth()
//                   .padding(horizontal = 32.dp), horizontalArrangement = Arrangement.SpaceBetween) {
//               Text(text = "Day 0", fontWeight = FontWeight.W700, fontFamily = clash, color = color1, fontSize = 18.sp,
//                   modifier = Modifier.clickable { color1= orangeText;color2= greyText;color3=
//                       greyText
//                       datestate.value=1
//                   })
//
//               Text(text = "Day 1", fontWeight = FontWeight.W700, fontFamily = clash, color = color2,fontSize = 18.sp,
//                   modifier = Modifier.clickable { color1= greyText;color2= orangeText;color3= greyText;
//                       datestate.value=2
//               })
//
//               Text(text = "Day 2", fontWeight = FontWeight.W700, fontFamily = clash, color = color3,fontSize = 18.sp,
//                   modifier = Modifier.clickable { color1= greyText;color2= greyText;color3=
//                       orangeText
//                       datestate.value=3
//                   })
//
//           }
//           Spacer(modifier = Modifier.height(16.dp))
//           scheduleBox()
//
//
//       }
//
//
//
//   }


@Composable
fun compbox(){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) {

        val cmpm=if(isSystemInDarkTheme()) Modifier
            .coloredShadow(Color(0xffffc311), 0.7f, 16.dp, 30.dp, 0.dp, 0.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.background)
            .border(3.dp, Color(0xffffc311), RoundedCornerShape(16.dp))
            .clickable {
                NavHostFragment
                    .findNavController(this@Home)
                    .navigate(R.id.action_home_nav_to_competitionsFragment);
            }
            else
            Modifier
                .coloredShadow(colors.onBackground, 0.01f, 18.dp, 1.dp, 20.dp, 0.dp)
                .coloredShadow(colors.onBackground, 0.06f, 18.dp, 1.dp, 12.dp, 0.dp)
                .coloredShadow(colors.onBackground, 0.24f, 18.dp, 1.dp, 4.dp, 0.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(colors.background)
                .border(1.5f.dp, colors.onBackground, RoundedCornerShape(16.dp))
                .clickable {
                    NavHostFragment
                        .findNavController(this@Home)
                        .navigate(R.id.action_home_nav_to_competitionsFragment);
                }



        Box(
            modifier=cmpm
        )
            {
            
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column() {
                        Text(fontFamily = aileron, fontWeight = FontWeight.W600, fontSize = 16.sp, color = colors.onBackground, text = "Explore our Competetions")
                        Spacer(Modifier.height(6.dp))
                        Text(fontFamily = aileron, fontWeight = FontWeight.W400, fontSize = 12.sp, color = colors.onBackground, text = "Register. Compete. Win")
                        Spacer(Modifier.height(12.dp))
                        Text(fontFamily = aileron, fontWeight = FontWeight.W700, fontSize = 13.sp, color = darkBlu, text = "Explore more")

                    }
                    Image(painter = painterResource(id = R.drawable.cup1), contentDescription ="" ,modifier=Modifier.height(72.dp) )


                    
                }
                
             }
        }
}

    @Composable
    fun scheduleBox() {
        val horiscrollowneventstate = rememberScrollState()
        var boxwidth=remember{ mutableStateOf(0.dp)}
        Box(Modifier
            .height(279.dp)) {
            Box(
                Modifier
                    .width(1550.dp)
                    .height(279.dp)
                    .background(color = blackbg)
                    .horizontalScroll(horiscrollowneventstate)
            ) {
                Row(
                    Modifier
                        .width(1550.dp)
                        .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (time in 9..11) {
                        Column(
                            Modifier
                                .width(50.dp)
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$time AM",
                                style = TextStyle(
                                    color = Color(0xffC7CCD1),
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
                                    color = Color(0xff4C5862),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height),
                                    strokeWidth = 1.dp.toPx()
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
                                color = Color(0xffC7CCD1),
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
                                color = Color(0xff4C5862),
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                    }
                    for (time in 1..11) {
                        Column(
                            Modifier
                                .width(50.dp)
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$time PM",
                                style = TextStyle(
                                    color = Color(0xffC7CCD1),
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
                                    color = Color(0xff4C5862),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }

                        }

                    }

                }

                if (datestate.value==1) {
                    datestate1.forEach { data -> userBox(eventdetail = data, horiscrollowneventstate, boxwidth) }
                }
                if (datestate.value==2) {
                    datestate2.forEach { data -> userBox(eventdetail = data, horiscrollowneventstate, boxwidth) }
                }
                if (datestate.value==3) {
                    datestate3.forEach { data -> userBox(eventdetail = data, horiscrollowneventstate, boxwidth) }
                }



            }
            if (isdragging.value) {

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(0.dp, 200.dp)
                        .height(80.dp)
                        .background(color = Color.Transparent),
                    contentAlignment = Alignment.BottomCenter
                )
                {
                    boxwidth.value = maxWidth
                    if (!onActiveDel.value) {
                        Lottieonactivedelete(R.raw.binanim)
                    } else {
                        Lottieonactivedelete(R.raw.crossanim)
                    }


                }
            }




        }
    }


    @Composable
    fun userBox(
        eventdetail: ownEventBoxUiModel,
        horiscrollowneventstate: ScrollState,
        boxwidth: MutableState<Dp>
    ){
        val coroutineScope = rememberCoroutineScope()
         val color= remember{ mutableStateOf(listOf(Color(0xffC80915), Color(0xff1E248D), Color(0xffEE6337)).random())}

        var lengthdp=remember{ Animatable(eventdetail.eventWithLive.durationInMin.toFloat() * (5f/3f)) }
        val xdis= remember{(((eventdetail.eventWithLive.starttime.hours-9)*100).toFloat() + (eventdetail.eventWithLive.starttime.min.toFloat() * (5f/3f)) + 75f)}
            val ydis= (30+(eventdetail.ydis*70))
        val xdisinpxcald=with(LocalDensity.current){(xdis-2).dp.toPx()}
        val ydisinpxcald=with(LocalDensity.current){(ydis).dp.toPx()}
        var offsetX = remember { Animatable(xdisinpxcald) }
        var offsetY = remember { Animatable(ydisinpxcald) }

        Box(
            Modifier
                .offset {
//                    xdis.dp - 2.dp, ydis.dp
                    IntOffset(
                        offsetX.value
                            .toInt(),
                        offsetY.value
                            .toInt()
                    )
                }
                .height(58.dp)
                .width(lengthdp.value.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color.value)
                .clickable {

                    val frg = Events_Details_Fragment()
                    val arguments = bundleOf("Artist" to eventdetail.eventWithLive.artist)
//                            fm
//                                    .beginTransaction()
//                                    .replace(R.id.fragmentContainerView, frg)
//                                    .addToBackStack(null)
//                                    .commit()
                    findNavController(this).navigate(
                        R.id.action_home2_to_events_Details_Fragment,
                        arguments
                    );


                }
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = { isdragging.value = true },
                        onDrag = { _, dragAmount ->
                            val original = Offset(offsetX.value, offsetY.value)
                            val summed = original + dragAmount
                            val newValue = Offset(
                                x = summed.x,
                                y = summed.y.coerceIn(30.dp.toPx(), 221.dp.toPx())
                            )
                            coroutineScope.launch {
                                offsetY.snapTo(newValue.y)
                                offsetX.snapTo(newValue.x)
                            }



                            onActiveDel.value = (182.dp..221.dp).contains(offsetY.value.toDp()) and
                                    ((horiscrollowneventstate.value + (boxwidth.value.toPx() / 2).toInt() - lengthdp.value.dp
                                        .toPx()
                                        .toInt()..horiscrollowneventstate.value + (boxwidth.value.toPx() / 2).toInt()).contains(
                                        (offsetX.value)
                                            .toInt()
                                    ))


                        },
                        onDragCancel = {
                            isdragging.value = false;
                            coroutineScope.launch {
                                offsetX.animateTo(
                                    xdisinpxcald, animationSpec = tween(
                                        durationMillis = 400,
                                        delayMillis = 0, easing = FastOutSlowInEasing
                                    )
                                );
                                offsetY.animateTo(
                                    ydisinpxcald, animationSpec = tween(
                                        durationMillis = 400,
                                        delayMillis = 0, easing = FastOutSlowInEasing
                                    )
                                );
                            }
                        },
                        onDragEnd = {
                            isdragging.value = false
                            if (onActiveDel.value) {
                                coroutineScope.launch {
                                    lengthdp.animateTo(
                                        0f, animationSpec = tween(
                                            durationMillis = 300,
                                            delayMillis = 0, easing = FastOutSlowInEasing
                                        )
                                    )
//                                                homeViewModel.OwnEventsLiveState.remove(eventdetail.eventWithLive)
//                                                val list=homeViewModel.OwnEventsLiveState
//                                                homeViewModel.OwnEventsLiveState.clear()
//                                                homeViewModel.OwnEventsWithLive.removeAnItem(eventdetail.eventWithLive)
//
                                    scheduleDatabase.DeleteItem(eventdetail.eventWithLive.artist)
                                    homeViewModel.OwnEventsWithLive.value?.clear()
                                    datestate1.clear()
                                    datestate2.clear()
                                    datestate3.clear()
//                                                delay(1000)
                                    homeViewModel.fetchlocaldbandupdateownevent(scheduleDatabase)
//                                                homeViewModel.allEventsWithLivedata.removeAndAddItemAtPos(
//                                                    homeViewModel.allEventsWithLivedata.value?.find { data->data.eventdetail.artist==eventdetail.eventWithLive.artist }!!
//                                                )

//                                    val dataevnetcurrent= homeViewModel.upcomingEventsLiveState.toList()
//                                    homeViewModel.allEventsWithLivedata.postValue(mutableListOf(eventWithLive(
//                                        eventdetail()
//                                    )))
//                                                delay(100)
//                                    homeViewModel.allEventsWithLivedata.value?.clear()
//                                                homeViewModel.upcomingEventsLiveState.clear()
//                                                delay(100)
//                                                homeViewModel.upcomingEventsLiveState.addAll(dataevnetcurrent)
//                                                homeViewModel.upcomingEventsLiveState.add(
//                                                    eventWithLive(eventdetail())
//                                                )
//                                                homeViewModel.allEventsWithLivedata.postValue(dataevnetcurrent)

//                                  homeViewModel.allEventsWithLivedata.addNewItem(eventWithLive(eventdetail()))
//                                    homeViewModel.allEventsWithLivedata.removeAnItem(eventWithLive(eventdetail()))
                                }
//                                datestate.forEach { data -> list.add(data) }
//                                datestate.remove(eventdetail)
//                                Log.d("boxevent", eventdetail.toString())


//                                Log.d("boxevent", list.toString())


//                             val res2=homeViewModel.OwnEventsWithLive.value!!.remove(eventWithLive(eventdetail.eventWithLive.eventdetail, mutableStateOf(false)))
//                                Log.d("resdel",res1.toString())
//                                Log.d("resdel",res2.toString())
                                onActiveDel.value = false
//                                if (res  ) {
//                                    Toast
//                                        .makeText(activity, "event removed", Toast.LENGTH_SHORT)
//                                        .show()
//                                }


                            } else {
                                coroutineScope.launch {
                                    offsetX.animateTo(
                                        xdisinpxcald, animationSpec = tween(
                                            durationMillis = 400,
                                            delayMillis = 0, easing = FastOutSlowInEasing
                                        )
                                    );
                                    offsetY.animateTo(
                                        ydisinpxcald, animationSpec = tween(
                                            durationMillis = 400,
                                            delayMillis = 0, easing = FastOutSlowInEasing
                                        )
                                    );
                                }


                            }


                        }

                    )


                }


        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(12.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
                Text(
                    text = eventdetail.eventWithLive.artist,
                    color = Color.White,
                    fontWeight = FontWeight.W700,
                    fontFamily = clash,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = eventdetail.eventWithLive.category,
                    style = TextStyle(
                        color = colorResource(id = R.color.textGray),
                        fontFamily = clash,
                        fontWeight = FontWeight.W600,
                        fontSize = 12.sp
                    )
                )
            }
        }




    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun MyContent() {
        var artist : String by rememberSaveable{ mutableStateOf("") }


        // Declaring a Boolean value to
        // store bottom sheet collapsed state
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState =
            BottomSheetState(BottomSheetValue.Collapsed)
        )

        // Declaring Coroutine scope
        val coroutineScope = rememberCoroutineScope()

        // Creating a Bottom Sheet
        Alcheringa2022Theme {
            BottomSheetScaffold(

                scaffoldState = bottomSheetScaffoldState,
                sheetContent = {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.95f)
                        .border(2.dp, colors.secondary, RoundedCornerShape(40.dp, 40.dp))
                ) {
                    Box(
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Icon(
                            painterResource(id = R.drawable.rectangle_expand), "",
                            Modifier
                                .width(60.dp)
                                .height(5.dp), tint = colors.onSurface
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                            eventfordes =
                                homeViewModel.allEventsWithLive.filter { data -> data.eventdetail.artist == artist }[0]
                            scheduleDatabase = ScheduleDatabase(context)

                            Defaultimg(eventWithLive = eventfordes)
//                            if (eventfordes.eventdetail.category.replace("\\s".toRegex(), "")
//                                    .uppercase() == "Competitions".uppercase()
//                            ) {
//                                Bottomviewcomp(eventWithLive = eventfordes)
//                            } else {
//                                Bottomviewnewevent(eventWithLive = eventfordes)
//                            }
                            Bottomviewcomp(eventWithLive = eventfordes)

                            Spacer(modifier = Modifier.height(0.dp))
                        }
                    }
                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        eventButtons(eventWithLive = eventfordes)
                    }
                }


                },
                sheetPeekHeight = 0.dp, sheetShape = RoundedCornerShape(40.dp, 40.dp),
                sheetBackgroundColor = colors.background,


            ) {
                Alcheringa2022Theme() {
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .verticalScroll(scrollState)
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    coroutineScope.launch {
                                        if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                                })
                            },
                    ) {
                        //                    if (scrollState.value==0){binding.logoAlcher.setImageDrawable(resources.getDrawable(R.drawable.ic_alcher_final_logo))
                        //                            binding.logoAlcher.layoutParams.width=with(LocalDensity.current){162.dp.toPx().toInt()}
                        //                        binding.logoAlcher.layoutParams.height=with(LocalDensity.current){50.dp.toPx().toInt()}
                        //                    }
                        //                    else{binding.logoAlcher.setImageDrawable(resources.getDrawable(R.drawable.ic_alcher_final_small_logo));
                        //                        binding.logoAlcher.layoutParams.width= ViewGroup.LayoutParams.WRAP_CONTENT
                        //                        binding.logoAlcher.layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT
                        //                    }

                        if(homeViewModel.featuredEventsWithLivestate.isNotEmpty()) {
                            Spacer(Modifier.height(20.dp))
                            horizontalScroll(eventdetails = List(10) {homeViewModel.featuredEventsWithLivestate}.flatten())
                        }

                        val alphaval= 0.2f

                        Box(
                            modifier = Modifier.padding(
                                start = 20.dp,
                                bottom = 24.dp,
                                top = 36.dp
                            ),
                        ) {
                            Card(
                                Modifier
                                    .height(10.dp)
                                    .offset(x = -5.dp, y = 16.dp)
                                    .alpha(alphaval),
                                shape = RoundedCornerShape(100.dp),
                                backgroundColor = textbg

                            ){
                                Text(

                                    text = "Competetions  ",
                                    fontFamily = aileron,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Transparent,
                                    fontSize = 21.sp
                                )
                            }
                            Text(

                                text = "Competetions",
                                fontFamily = aileron,
                                fontWeight = FontWeight.Bold,
                                color = colors.onBackground,
                                fontSize = 21.sp
                            )
                        }
                        compbox()





                        if (homeViewModel.allEventsWithLive.filter { data -> data.isLive.value }
                                .isNotEmpty()) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    bottom = 24.dp,
                                    top = 36.dp
                                ),
                            ) {
                                Card(
                                    Modifier
                                        .height(10.dp)
                                        .offset(x = -5.dp, y = 16.dp)
                                        .alpha(alphaval),
                                    shape = RoundedCornerShape(100.dp),
                                    backgroundColor = textbg

                                ){
                                    Text(

                                        text = "Ongoing Events  ",
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent,
                                        fontSize = 21.sp
                                    )
                                }
                                Text(

                                    text = "Ongoing Events",
                                    fontFamily = aileron,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onBackground,
                                    fontSize = 21.sp
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 20.dp)
                            ) {
                                items(homeViewModel.allEventsWithLive.filter { data -> data.isLive.value }) { dataeach ->
                                    context?.let {
                                        Event_card_Scaffold(
                                            eventdetail = dataeach,
                                            homeViewModel,
                                            it,
                                            artist
                                        ) {
                                            artist = dataeach.eventdetail.artist
                                            coroutineScope.launch {
                                                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                                    bottomSheetScaffoldState.bottomSheetState.expand()

                                                } else {
                                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }


                        //TODO: Replace with actual check
                        //if(homeViewModel.upcomingEventsLiveState.filter { data-> !(data.isLive.value) }.isNotEmpty()) {
                        if (homeViewModel.upcomingEventsLiveState.isNotEmpty()) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    bottom = 24.dp,
                                    top = 36.dp
                                ),
                            ) {
                                Card(
                                    Modifier
                                        .height(10.dp)
                                        .offset(x = -5.dp, y = 16.dp)
                                        .alpha(alphaval),
                                    shape = RoundedCornerShape(100.dp),
                                    backgroundColor = textbg

                                ){
                                    Text(

                                        text = "Upcoming Events  ",
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent,
                                        fontSize = 21.sp
                                    )
                                }
                                Text(

                                    text = "Upcoming Events",
                                    fontFamily = aileron,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onBackground,
                                    fontSize = 21.sp
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 20.dp)
                            ) {
                                items(homeViewModel.upcomingEventsLiveState)
                                { dataEach ->
                                    context?.let {
                                        Event_card_Scaffold(
                                            eventdetail = dataEach,
                                            homeViewModel,
                                            it,
                                            artist
                                        ) {
                                            artist = dataEach.eventdetail.artist
                                            coroutineScope.launch {
                                                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                                } else {
                                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            //                        LazyRow(
                            //                                modifier = Modifier.fillMaxWidth(),
                            //                                horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            //                        ) {
                            //                            items(homeViewModel.upcomingEventsLiveState.filter { data-> !(data.isLive.value) }.sortedBy { data->  (data.eventdetail.starttime.date*24*60 + ((data.eventdetail.starttime.hours*60)).toFloat() + (data.eventdetail.starttime.min.toFloat()))
                            //                            }) { dataeach -> context?.let { Event_card_upcoming(eventdetail = dataeach,homeViewModel, it,this@Home,fm,R.id.action_home2_to_events_Details_Fragment) } }
                            //                        }
                        }

                        Box(
                            modifier = Modifier
                                .padding(
                                    start = 20.dp,
                                    bottom = 24.dp,
                                )
                                .offset(y = 60.dp),

                        ) {
                            Card(
                                Modifier
                                    .height(10.dp)
                                    .offset(x = -5.dp, y = 16.dp)
                                    .alpha(alphaval),
                                shape = RoundedCornerShape(100.dp),
                                backgroundColor = textbg

                            ){
                                Text(

                                    text = "Limited Time Merch  ",
                                    fontFamily = aileron,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Transparent,
                                    fontSize = 21.sp
                                )
                            }
                            Text(

                                text = "Limited Time Merch",
                                fontFamily = aileron,
                                fontWeight = FontWeight.Bold,
                                color = colors.onBackground,
                                fontSize = 21.sp
                            )
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.selectedItemId =
                                    R.id.merch;
                            }

                        ) {
                            val drbls = listOf(
                                R.drawable.merch_bg_1,
                                R.drawable.merch_bg_2,
                                R.drawable.merch_bg_3
                            )
                            if(homeViewModel.merchMerch.isNotEmpty()){
                            merchBoxnew(merch = homeViewModel.merchMerch
                                .filter { it.is_available }, drbls
                            )}
                        }
                        //                    Row(
                        //                        Modifier
                        //                            .fillMaxWidth()
                        //                            .wrapContentHeight()
                        //                            .padding(start = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        //                        Text( text = "MY SCHEDULE", fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 18.sp)
                        //                        Text(text = "See Full Schedule>", fontFamily = hk_grotesk, fontSize = 15.sp, fontWeight = FontWeight.W500, color =Color(0xffEE6337)
                        //                            ,modifier = Modifier.clickable {
                        //
                        ////                                activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.selectedItemId =
                        ////                                    R.id.schedule;
                        //                            findNavController(this@Home).navigate(R.id.action_home2_to_schedule2);
                        //
                        ////                            fm.beginTransaction()
                        ////                                .replace(R.id.fragmentContainerView,Schedule()).addToBackStack(null)
                        ////                                .commit()
                        ////                            })
                        //                            })
                        //                    }
                        //                    Spacer(modifier = Modifier.height(20.dp))
                        //                    mySchedule()
                        //                    Spacer(modifier = Modifier.height(20.dp))
                        //                    Text(modifier = Modifier
                        //                        .fillMaxWidth()
                        //                        .padding(horizontal = 10.dp), text = "Hold and Drag to remove events", fontFamily = hk_grotesk, fontWeight = FontWeight.Bold, color = Color(0xffffffff), fontSize = 16.sp, textAlign = TextAlign.Center)


                        if(homeViewModel.OwnEventsWithLiveState.isNotEmpty()) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    bottom = 24.dp,
                                    top = 36.dp
                                ),
                            ) {
                                Card(
                                    Modifier
                                        .height(10.dp)
                                        .offset(x = -5.dp, y = 16.dp)
                                        .alpha(alphaval),
                                    shape = RoundedCornerShape(100.dp),
                                    backgroundColor = textbg

                                ){
                                    Text(

                                        text = "In Your Schedule  ",
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent,
                                        fontSize = 21.sp
                                    )
                                }
                                Text(

                                    text = "In Your Schedule",
                                    fontFamily = aileron,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onBackground,
                                    fontSize = 21.sp
                                )
                            }
                        }


                        if(homeViewModel.allEventsWithLive.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 20.dp)
                                ) {
                                    items(homeViewModel.OwnEventsWithLiveState) { dataeach ->
                                        context?.let {
                                            Schedule_card(
                                                eventdetail = dataeach,
                                                homeViewModel,
                                                it,
                                                this@Home,
                                                fm,
                                                R.id.action_home2_to_events_Details_Fragment
                                            )
                                        }
                                    }
                                }

                            }
                        }



                        if(homeViewModel.forYouEvents.isNotEmpty()) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    bottom = 24.dp,
                                    top = 36.dp
                                ),
                            ) {
                                Card(
                                    Modifier
                                        .height(10.dp)
                                        .offset(x = -5.dp, y = 16.dp)
                                        .alpha(alphaval),
                                    shape = RoundedCornerShape(100.dp),
                                    backgroundColor = textbg

                                ) {
                                    Text(

                                        text = "For You  ",
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent,
                                        fontSize = 21.sp
                                    )
                                }
                                Text(

                                    text = "For You",
                                    fontFamily = aileron,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onBackground,
                                    fontSize = 21.sp
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {

                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 20.dp)
                                ) {
                                    items(
                                        homeViewModel.forYouEvents
                                    ) { dataeach ->
                                        context?.let {
                                            /*if(dataeach.eventdetail.stream){
                                            Event_card(eventdetail = dataeach,homeViewModel, it,fm)
                                        }*/
                                            Event_card_Scaffold(
                                                eventdetail = dataeach,
                                                homeViewModel,
                                                it,
                                                artist
                                            ) {
                                                artist = dataeach.eventdetail.artist
                                                coroutineScope.launch {
                                                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                                        bottomSheetScaffoldState.bottomSheetState.expand()
                                                    } else {
                                                        bottomSheetScaffoldState.bottomSheetState.collapse()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                    }
                }
                BackHandler(enabled = bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
    }

    @Composable
    fun Defaultimg(eventWithLive: eventWithLive) {

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()

        ) {
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(284.dp),
                    shape = RoundedCornerShape(28.dp, 28.dp),

                ) {
                    GlideImage(
                        imageModel = eventWithLive.eventdetail.imgurl,
                        contentDescription = "artist",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(297.dp),
                        //                circularReveal = CircularReveal(300),

                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        shimmerParams = ShimmerParams(
                            baseColor = Color.Black,
                            highlightColor = orangeText,
                            durationMillis = 350,
                            dropOff = 0.65f,
                            tilt = 20f
                        ), failure = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(), contentAlignment = Alignment.Center
                            ) {
                                val composition by rememberLottieComposition(
                                    LottieCompositionSpec.RawRes(
                                        R.raw.comingsoon
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
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()

                        .border(1.dp, colors.secondary)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = eventWithLive.eventdetail.artist,
                                color = colors.onBackground,
                                fontWeight = FontWeight.Bold,
                                fontSize = 42.sp,
                                fontFamily = aileron
                            )
//                            if (eventWithLive.isLive.value) {
//                                Box(
//                                    modifier = Modifier
//                                        .width(68.dp)
//                                        .height(21.dp)
//                                        .clip(RoundedCornerShape(4.dp))
//                                        .background(liveGreen),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Text(
//                                        text = "⬤ LIVE ",
//                                        color = white,
//                                        fontFamily = hk_grotesk, fontWeight = FontWeight.W500,
//                                        fontSize = 12.sp
//                                    )
//                                }
//                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = eventWithLive.eventdetail.category,
                            style = TextStyle(
                                color = colors.onBackground,
                                fontFamily = aileron,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))


                    }

                }
            }


        }
    }

    @Composable
    fun eventButtons(eventWithLive: eventWithLive){
        val c=Calendar.getInstance()
        val isFinished = (c.get(Calendar.YEAR)>2022) or
                ((c.get(Calendar.YEAR)==2022) and
                        (c.get(Calendar.MONTH)> Calendar.MARCH)) or
                ((c.get(Calendar.YEAR)==2022) and
                        (c.get(Calendar.MONTH)== Calendar.MARCH) and
                        (c.get(Calendar.DATE)> eventWithLive.eventdetail.starttime.date)) or
                ((c.get(Calendar.YEAR)==2022) and
                        (c.get(Calendar.MONTH)== Calendar.MARCH) and
                        (c.get(Calendar.DATE)== eventWithLive.eventdetail.starttime.date)and
                        ( ((eventWithLive.eventdetail.starttime.hours*60 + eventWithLive.eventdetail.durationInMin))
                                <((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) ))

        if ( // TODO: replace with below check, commented out temporarily for demonstrations
            false
//            eventWithLive.eventdetail.category.replace("\\s".toRegex(), "")
//                .uppercase() == "Competitions".uppercase()
        )
        {

            if (eventWithLive.isLive.value && eventWithLive.eventdetail.joinlink != "") {
                Button(
                    onClick = {
                        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.joinlink)))
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        blu
                    )
                ) {
                    Text(
                        text = "Join Event",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = aileron,
                        color = black
                    )

                }

            }
            else if (isFinished){
                Button(
                    onClick = {},
                    Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        midWhite
                    )
                ) {
                     Text(text="Event Finished!",
                         fontSize = 20.sp,
                         fontWeight = FontWeight.SemiBold,
                         fontFamily = aileron,
                         color = black)
//                    else if (c.get(Calendar.DATE)==eventWithLive.eventdetail.starttime.date){
//                        Text(
//                            text = "Event will be available on  ${if (eventWithLive.eventdetail.starttime.hours > 12)"${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min != 0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"}",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.W600,
//                            fontFamily = clash,
//                            color = Color(0xffA3A7AC)
//                        )
//                    }
//                    else{
//                        Text(
//                            text = "Event will be available on day ${eventWithLive.eventdetail.starttime.date-11}",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.W600,
//                            fontFamily = clash,
//                            color = Color(0xffA3A7AC)
//                        )
//
//                    }
                }


            }
            else{
                Button(
                    onClick = {
                        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.reglink)))
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        blu
                    )
                ) {
                    Text(
                        text = "Register",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = aileron,
                        color = black
                    )

                }

            }
        }
        else
        {
//            if (eventWithLive.isLive.value) {
//                Button(
//                    onClick = {
//                        if(eventWithLive.eventdetail.joinlink!=""){
//                            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.joinlink)))
//
//                        }
//
//                    },
//                    Modifier
//                        .fillMaxWidth()
//                        .height(72.dp),
//                    shape = RoundedCornerShape(0.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        blu
//                    )
//                ) {
//                    Text(
//                        text = if(eventWithLive.eventdetail.joinlink=="")  "Running Offline" else "Join Event",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        fontFamily = aileron,
//                        color = black
//                    )
//
//                }
//
//            }
//            else if (!eventWithLive.isLive.value && eventWithLive.eventdetail.stream){
//                Button(
//                    onClick = {},
//                    Modifier
//                        .fillMaxWidth()
//                        .height(72.dp),
//                    shape = RoundedCornerShape(0.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        midWhite
//                    )
//                ) { val c=Calendar.getInstance()
//                    if( (c.get(Calendar.YEAR)>2022) or
//                        ((c.get(Calendar.YEAR)==2022) and
//                                (c.get(Calendar.MONTH)> Calendar.MARCH)) or
//                        ((c.get(Calendar.YEAR)==2022) and
//                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
//                                (c.get(Calendar.DATE)> eventWithLive.eventdetail.starttime.date)) or
//                        ((c.get(Calendar.YEAR)==2022) and
//                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
//                                (c.get(Calendar.DATE)== eventWithLive.eventdetail.starttime.date)and
//                                ( ((eventWithLive.eventdetail.starttime.hours*60 + eventWithLive.eventdetail.durationInMin))
//                                        <((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) ))
//
//                    ){ Text(text="Event Finished!",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        fontFamily = aileron,
//                        color = black
//                    )}
//                    else if (c.get(Calendar.DATE)==eventWithLive.eventdetail.starttime.date){
//                        Text(
//                            text = "Event will be available on  ${if (eventWithLive.eventdetail.starttime.hours > 12)"${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min != 0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"}",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            fontFamily = aileron,
//                            color = black
//                        )
//                    }
//                    else{
//                        Text(
//                            text = "Event will be available on day ${eventWithLive.eventdetail.starttime.date-11}",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            fontFamily = aileron,
//                            color = black
//                        )
//
//                    }
//                }
//            }
            if (
//                isFinished
            false
            ){
                Button(
                    onClick = {},
                    Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        midWhite
                    )
                ) {
                    Text(text="Event Finished!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = aileron,
                        color = black)
                }
            }
            else if(eventWithLive.eventdetail.venue != "") {
                Row {

                    Button(
                        onClick = {
                            //TODO: (Shantanu) Implement all venue locations
                            val gmmIntentUri =
                                Uri.parse("google.navigation:q=26.190761044728855,91.69699071630549")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            startActivity(mapIntent)
                        },
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(72.dp)
                            .border(1.dp, colors.onBackground),
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            colors.background
                        )
                    ) {
                        Text(
                            text = "Navigate▲",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = aileron,
                            color = colors.onBackground,
                            textAlign = TextAlign.Center
                        )

                    }


                        Button(
                            onClick = {
                                //TODO: Set Buy pass link
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("http://pass.alcheringa.in")
                                    )
                                )

                            },
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .height(72.dp)
                                .border(1.dp, colors.onBackground),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                blu
                            )
                        ) {
                            Text(
                                text = if(eventWithLive.eventdetail.venue.uppercase() == "CREATORS' CAMP") "Buy Tickets"
                                else "Get Passes",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = aileron,
                                color = colors.onBackground
                            )

                        }


                }
            }
        }
    }


    @Composable
    fun Bottomviewcomp(eventWithLive:eventWithLive){
        val c=Calendar.getInstance()
        val isFinished = (c.get(Calendar.YEAR)>2022) or
                ((c.get(Calendar.YEAR)==2022) and
                        (c.get(Calendar.MONTH)> Calendar.MARCH)) or
                ((c.get(Calendar.YEAR)==2022) and
                        (c.get(Calendar.MONTH)== Calendar.MARCH) and
                        (c.get(Calendar.DATE)> eventWithLive.eventdetail.starttime.date)) or
                ((c.get(Calendar.YEAR)==2022) and
                        (c.get(Calendar.MONTH)== Calendar.MARCH) and
                        (c.get(Calendar.DATE)== eventWithLive.eventdetail.starttime.date)and
                        ( ((eventWithLive.eventdetail.starttime.hours*60 + eventWithLive.eventdetail.durationInMin))
                                <((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) ))

        var isadded=remember{ mutableStateOf(false)}
        LaunchedEffect(key1=Unit,block = {
            isadded.value=homeViewModel.OwnEventsLiveState.any { data-> data.artist==eventWithLive.eventdetail.artist }

        })

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween)
//            {
//
//                if (eventWithLive.eventdetail.stream) {
//                    Box(modifier = Modifier
//                        .wrapContentSize()
//                    ){
//
//
//                        if( !isadded.value) {
//
//                            Image( modifier = Modifier
//                                .width(18.dp)
//                                .height(18.dp)
//                                .clickable {
//                                    isadded.value = true
//                                    homeViewModel.OwnEventsWithLive.addNewItem(eventWithLive.eventdetail)
//                                    scheduleDatabase.addEventsInSchedule(
//                                        eventWithLive.eventdetail,
//                                        context
//                                    )
//                                },
//                                painter = painterResource(id = R.drawable.add_icon),
//                                contentDescription ="null")
//                        }
//                        if(isadded.value)
//                        {
//                            Image( modifier = Modifier
//                                .width(20.dp)
//                                .height(20.dp)
//                                .clickable {
//                                    isadded.value = false
//                                    homeViewModel.OwnEventsWithLive.removeAnItem(eventWithLive.eventdetail)
//                                    scheduleDatabase.DeleteItem(eventWithLive.eventdetail.artist)
//                                    Toast
//                                        .makeText(
//                                            context,
//                                            "Event removed from My Schedule",
//                                            Toast.LENGTH_SHORT
//                                        )
//                                        .show()
//                                },
//                                painter = painterResource(id = R.drawable.tickokay),
//                                contentDescription ="null", contentScale = ContentScale.FillBounds)
//                        }
//                    }
//                }
//            }
                if(eventWithLive.eventdetail.venue != ""){
                    Row(
                        modifier = Modifier.fillMaxWidth(), Arrangement.Start, verticalAlignment = Alignment.CenterVertically)
                    {
                        Image(
                            painter = painterResource(id = R.drawable.location_pin),
                            contentDescription = null,


                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(colors.onBackground))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = eventWithLive.eventdetail.venue,
                            style = TextStyle(
                                color = colors.onBackground,
                                fontFamily = aileron,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.schedule),
                        contentDescription = null,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(colors.onBackground)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${eventWithLive.eventdetail.starttime.date} Mar, ${if (eventWithLive.eventdetail.starttime.hours > 12) "${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min != 0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"} ",
                        style = TextStyle(
                            color = colors.onBackground,
                            fontFamily = aileron,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))

                Box(modifier = Modifier
                    .height(40.dp)
                    .border(1.dp, colors.secondary)
                    .animateContentSize()
                    .wrapContentWidth()
                    .background(
                        if (isSystemInDarkTheme() && isadded.value) {
                            Color(31, 89, 22, 255)
                        } else if (isadded.value) {
                            green
                        } else {
                            colors.background
                        }
                    )
                ){
                    if( !isadded.value) {
                        Row(
                            Modifier
                                .clickable {
                                    isadded.value = true
                                    homeViewModel.OwnEventsWithLive.addNewItem(
                                        eventWithLive.eventdetail
                                    )
                                    scheduleDatabase.addEventsInSchedule(
                                        eventWithLive.eventdetail,
                                        context
                                    )


                                }
                                .padding(10.dp))
                        {

                            Image(
                                modifier = Modifier
                                    .width(18.dp)
                                    .height(18.dp)
                                    ,
                                painter = painterResource(id = R.drawable.add_icon),
                                contentDescription = "null",
                                colorFilter = ColorFilter.tint(colors.onBackground)
                            )
                            Spacer(Modifier.width(10.dp))
                            MarqueeText(
                                text = "Add to Schedule",
                                fontFamily = aileron,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.onBackground,
                                fontSize = 16.sp,
                                gradientEdgeColor = Color.Transparent
                            )
                        }

                    }
                    if(isadded.value)
                    {
                        Row(
                            Modifier
                                .clickable {
                                    isadded.value = false
                                    homeViewModel.OwnEventsWithLive.removeAnItem(
                                        eventWithLive.eventdetail
                                    )
                                    scheduleDatabase.DeleteItem(eventWithLive.eventdetail.artist)
                                }
                                .padding(10.dp)

                            ) {
                            Image(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    ,
                                painter = painterResource(id = R.drawable.tickokay),
                                contentDescription = "null",
                                contentScale = ContentScale.FillBounds,
                                colorFilter = ColorFilter.tint(colors.onBackground)
                            )
                            Spacer(Modifier.width(10.dp))
                            MarqueeText(
                                text = "Added to Schedule",
                                fontFamily = aileron,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.onBackground,
                                fontSize = 16.sp,
                                gradientEdgeColor = Color.Transparent

                            )
                        }
                    }
                }


            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text =eventfordes.eventdetail.descriptionEvent ,
                fontFamily = aileron,
                fontWeight = FontWeight.Normal,
                color = colors.onBackground,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(36.dp))
//            if (!eventWithLive.isLive.value  && !isFinished){
//                Button(
//                    onClick = {},
//                    Modifier
//                        .fillMaxWidth()
//                        .height(72.dp),
//                    shape = RoundedCornerShape(0.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        midWhite
//                    )
//                ) {
//
//                        if (c.get(Calendar.DATE)==eventWithLive.eventdetail.starttime.date){
//                            Text(
//                                text = "Event will be available on  ${if (eventWithLive.eventdetail.starttime.hours > 12)"${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min != 0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"}",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                fontFamily = aileron,
//                                color = black
//                            )
//                        }
//                        else{
//                            Text(
//                                text = "Event will be available on day ${eventWithLive.eventdetail.starttime.date-11}",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                fontFamily = aileron,
//                                color = black
//                            )
//
//                        }
//
//                }
//
//            }

//            if (isadded.value) {
//                Button(
//                    onClick = {
//                        isadded.value= false
//                        viewModelHome.OwnEventsWithLive.removeAnItem(eventWithLive.eventdetail)
//                        scheduleDatabase.DeleteItem(eventWithLive.eventdetail.artist)
//                    },
//                    Modifier
//                        .fillMaxWidth()
//                        .height(55.dp),
//                    shape = RoundedCornerShape(18.dp),
//                    colors = ButtonDefaults.buttonColors(Color(0xff2B2B2B))
//                ) {
//                    Text(
//                        text = "Remove from My Schedule",
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.W600,
//                        fontFamily = clash,
//                        color = Color.White
//                    )
//                }
//            }
//            if (!isadded.value){
//                Button(
//                    onClick = { isadded.value= true
//                        viewModelHome.OwnEventsWithLive.addNewItem(eventWithLive.eventdetail)
//                        scheduleDatabase.addEventsInSchedule(
//                            eventWithLive.eventdetail,
//                            context
//                        )
//                    },
//                    Modifier
//                        .fillMaxWidth()
//                        .height(55.dp),
//                    shape = RoundedCornerShape(18.dp),
//                    colors = ButtonDefaults.buttonColors(Color(0xff2B2B2B))
//                ) {
//                    Text(
//                        text = "Add to My Schedule",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.W600,
//                        fontFamily = clash,
//                        color = Color.White
//                    )
//                }
//            }
            Spacer(modifier = Modifier.height(24.dp))









        }
    }



    @OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun merchBox(merch: List<merchModel>, drbls:List<Int>){
        val pagerState = rememberPagerState()
        val textPaintStroke = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = 88f
            color = android.graphics.Color.BLACK
            strokeWidth = 18f
            typeface = resources.getFont(R.font.starguard)
            strokeMiter= 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
        }

        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = 88F
            typeface = resources.getFont(R.font.starguard)
            color = android.graphics.Color.WHITE
        }

        val textPaintStroke1 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = 44F
            color = android.graphics.Color.BLACK
            strokeWidth = 9f
            typeface = resources.getFont(R.font.starguard)
            strokeMiter= 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
        }

        val textPaint1 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = 44F
            typeface = resources.getFont(R.font.starguard)
            color = android.graphics.Color.WHITE
        }

        val textPaintStroke2 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = 66F
            color = android.graphics.Color.BLACK
            strokeWidth = 16f
            typeface = resources.getFont(R.font.starguard)
            strokeMiter= 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
        }

        val textPaint2 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = 66F
            typeface = resources.getFont(R.font.starguard)
            color = ContextCompat.getColor(requireContext(), R.color.Green)
        }

        LaunchedEffect(key1 = pagerState.currentPage) {
            launch {

                delay(3500)
                with(pagerState) {
                    val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
                    tween<Float>(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                    animateScrollToPage(page = target )

                }
            }
        }

        HorizontalPager(
            count = merch.size, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), state = pagerState
        ) { page ->
            Card(
                modifier = Modifier
                    .background(colors.background)
                    .coloredShadow(colors.secondaryVariant, 0.2f, 12.dp, 30.dp, 5.dp, 0.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                border = BorderStroke(1.5.dp, colors.secondary)
            ) {
                Box(modifier = Modifier
                    .clickable {
                        findNavController(this@Home).navigate(R.id.action_home2_to_merchFragment)

//                fm.beginTransaction()
//                    .replace(R.id.fragmentContainerView,MerchFragment()).addToBackStack(null)
//                    .commit()
                    }
                    .height(200.dp)
                    .fillMaxWidth()

                ){
                    Image(painter = painterResource(id = drbls[page]), contentDescription = null,
                        Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter), contentScale = ContentScale.Crop, alignment = Alignment.Center)

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()

                    ){

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .align(Alignment.BottomStart)
                                .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {



                            Column(
                                Modifier
                                    .fillMaxWidth(0.5F)
                                    .fillMaxHeight()
                                    .padding(top = 60.dp)

                            ) {
//                                Text(
//                                    text = merch[page].Name.uppercase(),
//                                    color = Color.White,
//                                    fontWeight = FontWeight.Normal,
//                                    fontSize = 32.sp,
//                                    fontFamily = star_guard,
//                                )
                                Canvas(
                                    modifier = Modifier.fillMaxWidth(),
                                    onDraw = {
                                        drawIntoCanvas {
                                            it.nativeCanvas.drawText(
                                                merch[page].name,
                                                0f,
                                                0.dp.toPx(),
                                                textPaintStroke
                                            )
                                            it.nativeCanvas.drawText(
                                                merch[page].name,
                                                0f,
                                                0.dp.toPx(),
                                                textPaint
                                            )
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = merch[page].material,
                                    color = black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    fontFamily = star_guard,
                                )
                                Spacer(modifier = Modifier.height(52.dp))
                                Canvas(
                                    modifier = Modifier.fillMaxWidth(),
                                    onDraw = {
                                        drawIntoCanvas {
                                            it.nativeCanvas.drawText(
                                                "At just",
                                                0f,
                                                0.dp.toPx(),
                                                textPaintStroke1
                                            )
                                            it.nativeCanvas.drawText(
                                                "At just",
                                                0f,
                                                0.dp.toPx(),
                                                textPaint1
                                            )
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.height(34.dp))

                                Canvas(
                                    modifier = Modifier.fillMaxWidth(),
                                    onDraw = {
                                        drawIntoCanvas {
                                            it.nativeCanvas.drawText(
                                                "Rs. "+merch[page].price,
                                                0f,
                                                0.dp.toPx(),
                                                textPaintStroke2
                                            )
                                            it.nativeCanvas.drawText(
                                                "Rs. "+merch[page].price,
                                                0f,
                                                0.dp.toPx(),
                                                textPaint2
                                            )
                                        }
                                    }
                                )

                            }

                            GlideImage(modifier = Modifier
                                .fillMaxHeight()
                                .align(Alignment.CenterVertically)
                                .padding(vertical = 10.dp),
                                imageModel = merch[page].image_url, contentDescription = "merch", contentScale = ContentScale.Fit,
                                alignment = Alignment.Center,
                                shimmerParams = ShimmerParams(
                                    baseColor = Color.Transparent,
                                    highlightColor = Color.LightGray,
                                    durationMillis = 350,
                                    dropOff = 0.65f,
                                    tilt = 20f
                                ),failure = {
                                    Box(modifier= Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(), contentAlignment = Alignment.Center) {
                                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.failure))
                                        val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
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
                            )}





                    }}}}


    }


    @OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun merchBoxnew(merch: List<merchModel>, drbls:List<Int>){
        val textPaintStroke = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = 88f
            color = android.graphics.Color.BLACK
            strokeWidth = 18f
            typeface = resources.getFont(R.font.starguard)
            strokeMiter= 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
        }

        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = 88F
            typeface = resources.getFont(R.font.starguard)
            color = android.graphics.Color.WHITE
        }

        val textPaintStroke1 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = 44F
            color = android.graphics.Color.BLACK
            strokeWidth = 9f
            typeface = resources.getFont(R.font.starguard)
            strokeMiter= 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
        }

        val textPaint1 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = 44F
            typeface = resources.getFont(R.font.starguard)
            color = android.graphics.Color.WHITE
        }

        val textPaintStroke2 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = 66F
            color = android.graphics.Color.BLACK
            strokeWidth = 16f
            typeface = resources.getFont(R.font.starguard)
            strokeMiter= 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
        }

        val textPaint2 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = 66F
            typeface = resources.getFont(R.font.starguard)
            color = ContextCompat.getColor(requireContext(), R.color.Green)
        }

        ViewPagernew(modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
        )
        {repeat(merch.size){page ->ViewPagerChild{
            Box(
                contentAlignment = Alignment.BottomCenter,
            ) {
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(colors.background)
                        .coloredShadow(colors.secondaryVariant, 0.2f, 16.dp, 30.dp, 5.dp, 0.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 0.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    border = BorderStroke(1.5.dp, colors.secondary)
                ) {
                    Box(modifier = Modifier
                        .clickable {
                            findNavController(this@Home).navigate(R.id.action_home2_to_merchFragment)

                            //                fm.beginTransaction()
                            //                    .replace(R.id.fragmentContainerView,MerchFragment()).addToBackStack(null)
                            //                    .commit()
                        }
                        .height(200.dp)
                        .fillMaxWidth()

                    ) {
                        Image(
                            painter = painterResource(id = drbls[page]),
                            contentDescription = null,
                            Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()

                        ) {

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .align(Alignment.BottomStart)
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {


                                Column(
                                    Modifier
                                        .fillMaxWidth(0.5F)
                                        .fillMaxHeight()
                                        .padding(top = 60.dp)

                                ) {
                                    //                                Text(
                                    //                                    text = merch[page].Name.uppercase(),
                                    //                                    color = Color.White,
                                    //                                    fontWeight = FontWeight.Normal,
                                    //                                    fontSize = 32.sp,
                                    //                                    fontFamily = star_guard,
                                    //                                )
                                    Canvas(
                                        modifier = Modifier.fillMaxWidth(),
                                        onDraw = {
                                            drawIntoCanvas {
                                                it.nativeCanvas.drawText(
                                                    merch[page].name,
                                                    0f,
                                                    0.dp.toPx(),
                                                    textPaintStroke
                                                )
                                                it.nativeCanvas.drawText(
                                                    merch[page].name,
                                                    0f,
                                                    0.dp.toPx(),
                                                    textPaint
                                                )
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = merch[page].material,
                                        color = black,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp,
                                        fontFamily = star_guard,
                                    )
                                    Spacer(modifier = Modifier.height(52.dp))
                                    Canvas(
                                        modifier = Modifier.fillMaxWidth(),
                                        onDraw = {
                                            drawIntoCanvas {
                                                it.nativeCanvas.drawText(
                                                    "At just",
                                                    0f,
                                                    0.dp.toPx(),
                                                    textPaintStroke1
                                                )
                                                it.nativeCanvas.drawText(
                                                    "At just",
                                                    0f,
                                                    0.dp.toPx(),
                                                    textPaint1
                                                )
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(34.dp))

                                    Canvas(
                                        modifier = Modifier.fillMaxWidth(),
                                        onDraw = {
                                            drawIntoCanvas {
                                                it.nativeCanvas.drawText(
                                                    "Rs. " + merch[page].price,
                                                    0f,
                                                    0.dp.toPx(),
                                                    textPaintStroke2
                                                )
                                                it.nativeCanvas.drawText(
                                                    "Rs. " + merch[page].price,
                                                    0f,
                                                    0.dp.toPx(),
                                                    textPaint2
                                                )
                                            }
                                        }
                                    )

                                }

                            }


                        }
                    }

                }
            }
            Box(
                Modifier.fillMaxSize(),
                Alignment.TopEnd
            ){
                GlideImage(
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .fillMaxWidth(0.5f),
                    imageModel = merch[page].image_url, contentDescription = "merch", contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                    shimmerParams = ShimmerParams(
                        baseColor = Color.Transparent,
                        highlightColor = Color.LightGray,
                        durationMillis = 350,
                        dropOff = 0.65f,
                        tilt = 20f
                    ),failure = {
                        Box(modifier= Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(), contentAlignment = Alignment.Center) {
                            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.failure))
                            val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
                            LottieAnimation(
                                composition,
                                progress,
                                modifier = Modifier.fillMaxHeight()
                            )
//
                        }

                    }
                )
            }
        }}}


    }






    @Composable
    fun Lottieonactivedelete(rId:Int) {

        // to keep track if the animation is playing
        // and play pause accordingly

        // for speed


        // remember lottie composition ,which
        // accepts the lottie composition result
        val composition by rememberLottieComposition(
            LottieCompositionSpec
                .RawRes(rId)
        )


        // to control the animation
        val progress by animateLottieCompositionAsState(
            // pass the composition created above
            composition,

            // Iterates Forever
            iterations = LottieConstants.IterateForever,

            // pass isPlaying we created above,
            // changing isPlaying will recompose
            // Lottie and pause/play
            isPlaying = true,

            // pass speed we created above,
            // changing speed will increase Lottie
            speed = 1f,

            // this makes animation to restart when paused and play
            // pass false to continue the animation at which is was paused
            restartOnPlay = false

        )
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.size(80.dp)
        )


    }





    @Composable
    fun newhorizontalscroll(eventdetails:SnapshotStateList<eventWithLive>){
                var count= mutableStateOf(eventdetails.size)

                Box(modifier = Modifier
                    .fillMaxWidth()
//                    .padding(start=20.dp)
                    .wrapContentHeight()) {
                    Column() {
                        ViewPagernew(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            ) {
                            repeat(count.value){
                                    page ->
                                ViewPagerChild {

                            Card(
                                border = BorderStroke(1.dp, colors.secondary),
                                shape = RoundedCornerShape(16.dp)

                            ) {
                                Box() {

                                    Card(
                                        modifier = Modifier.fillMaxWidth(),


                                        ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth()
                                        ) {
                                            GlideImage(
                                                imageModel = eventdetails[page].eventdetail.imgurl,
                                                contentDescription = "artist",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .aspectRatio(0.76f)
                                                    .clickable {
                                                        val arguments =
                                                            bundleOf("Artist" to eventdetails[page].eventdetail.artist)
                                                        findNavController(this@Home).navigate(
                                                            R.id.action_home2_to_events_Details_Fragment,
                                                            arguments
                                                        )
                                                    },
                                                alignment = Alignment.Center,
                                                contentScale = ContentScale.Crop,
                                                shimmerParams = ShimmerParams(
                                                    baseColor = blackbg,
                                                    highlightColor = Color.LightGray,
                                                    durationMillis = 350,
                                                    dropOff = 0.65f,
                                                    tilt = 20f
                                                ), failure = {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .fillMaxHeight(),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        val composition by rememberLottieComposition(
                                                            LottieCompositionSpec.RawRes(R.raw.comingsoon)
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
                                                    .fillMaxSize()
                                                    .background(
                                                        brush = Brush.verticalGradient(
                                                            colors = listOf(
                                                                Color.Transparent,
                                                                black,
                                                            ),
                                                            startY = with(LocalDensity.current) { 100.dp.toPx() }
                                                        )
                                                    )
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(horizontal = 20.dp, vertical = 28.dp),
                                                contentAlignment = Alignment.BottomStart
                                            ) {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        text = eventdetails[page].eventdetail.artist,
                                                        color = Color.White,
                                                        fontWeight = FontWeight.SemiBold,
                                                        fontSize = 36.sp,
                                                        fontFamily = aileron,
                                                        textAlign = TextAlign.Start
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                    Text(
                                                        text = eventdetails[page].eventdetail.category,
                                                        style = TextStyle(
                                                            color = colorResource(id = R.color.White),
                                                            fontFamily = aileron,
                                                            fontWeight = FontWeight.Normal,
                                                            fontSize = 16.sp
                                                        )
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))

                                                    Row {
                                                        Text(
                                                            text = "${eventdetails[page].eventdetail.starttime.date} Mar, ${if (eventdetails[page].eventdetail.starttime.hours > 12) "${eventdetails[page].eventdetail.starttime.hours - 12}" else eventdetails[page].eventdetail.starttime.hours}${if (eventdetails[page].eventdetail.starttime.min != 0) ":${eventdetails[page].eventdetail.starttime.min}" else ""} ${if (eventdetails[page].eventdetail.starttime.hours >= 12) "PM" else "AM"} ",
                                                            style = TextStyle(
                                                                color = white,
                                                                fontFamily = aileron,
                                                                fontWeight = FontWeight.Normal,
                                                                fontSize = 16.sp
                                                            )
                                                        )

                                                        //Spacer(modifier = Modifier.width(4.dp))
                                                        Text(
                                                            text = "| ${eventdetails[page].eventdetail.venue}",
                                                            style = TextStyle(
                                                                color = white,
                                                                fontFamily = aileron,
                                                                fontWeight = FontWeight.Normal,
                                                                fontSize = 16.sp
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
                        }
                        }

                    }
                }
            }







    override fun onResume() {
//        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.menu?.findItem(R.id.home_nav)?.setChecked(true);
//       MainActivity.index=R.id.home_nav;
        super.onResume()

        var unseen_notif_count = 0

        firebaseFirestore = FirebaseFirestore.getInstance()
        sharedPreferences = activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)

        firebaseFirestore!!.collection("Notification").get().addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot> ->
            if (task.isSuccessful) {
                val notifs = task.result.size()
                Log.d("Notification Count", "No of notifications: " + notifs)
                val seen_notifs = sharedPreferences?.getInt("seen_notifs_count", 0)
                Log.d("seen notification", seen_notifs.toString());
                unseen_notif_count = notifs - seen_notifs!!

                if (unseen_notif_count <= 0) {
                    binding.notificationCount.visibility = View.INVISIBLE
                } else if (unseen_notif_count <= 9) {
                    binding.notificationCount.visibility = View.VISIBLE
                    binding.notificationCount.text = unseen_notif_count.toString()
                } else {
                    binding.notificationCount.visibility = View.VISIBLE
                    binding.notificationCount.text = "9+"
                }
            } else {
                Log.d("Error", "Error loading notification count", task.exception)
            }
        })
    }



}

