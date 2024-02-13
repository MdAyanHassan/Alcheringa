package com.alcheringa.alcheringa2022


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
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.*
import com.alcheringa.alcheringa2022.databinding.FragmentHomeBinding
import com.alcheringa.alcheringa2022.ui.theme.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.*
import java.util.*
import kotlin.math.abs


/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    lateinit var fm: FragmentManager
    lateinit var binding: FragmentHomeBinding
    lateinit var navController: NavController
    lateinit var scheduleDatabase: ScheduleDatabase
    val homeViewModel: viewModelHome by activityViewModels()
    val ranges = mutableSetOf<ClosedFloatingPointRange<Float>>()
    lateinit var loaderView: MutableState<Boolean>

    val datestate1 = mutableStateListOf<ownEventBoxUiModel>()
    val datestate2 = mutableStateListOf<ownEventBoxUiModel>()
    val datestate3 = mutableStateListOf<ownEventBoxUiModel>()
    lateinit var datestate: MutableState<Int>
    var onActiveDel = mutableStateOf(false)
    var isdragging = mutableStateOf(false)
    var home = false;
    public val artistLive = MutableLiveData<String>()
    private val mun_reg_link = "https://docs.google.com/forms/d/e/1FAIpQLSeUMeC2RhKMXHpgDtd-RgozYZeb_HwR298-3XPe9D4RVU_LGQ/viewform"



    var firebaseFirestore: FirebaseFirestore? = null
    var sharedPreferences: SharedPreferences? = null

    lateinit var eventfordes: eventWithLive
    lateinit var similarlist: MutableList<eventWithLive>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
        fm = parentFragmentManager

        scheduleDatabase = ScheduleDatabase(context)

        if (homeViewModel.OwnEventsWithLiveState.isEmpty()) {
            homeViewModel.fetchlocaldbandupdateownevent(scheduleDatabase)
        }

        if (homeViewModel.stalllist.isEmpty()) {
            homeViewModel.getStalls()
        }

        if (homeViewModel.venuesList.isEmpty()){
            homeViewModel.getVenues()
        }


        if (homeViewModel.featuredEventsWithLivestate.isEmpty()) {
            homeViewModel.getfeaturedEvents()
        }
        if (homeViewModel.allEventsWithLive.isEmpty()) {
            homeViewModel.getAllEvents()
        }
//        homeViewModel.getMerchHome()
        if (homeViewModel.merchMerch.isEmpty()) {
            homeViewModel.getMerchMerch()
        }
        if (homeViewModel.informalList.isEmpty()) {
            homeViewModel.getInformals()
        }
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


//        var unseen_notif_count = 0
//
//        firebaseFirestore = FirebaseFirestore.getInstance()
//        sharedPreferences = activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)
//
//        firebaseFirestore!!.collection("Notification").get()
//            .addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot> ->
//                if (task.isSuccessful) {
//                    val notifs = task.result.size()
//                    Log.d("Notification Count", "No of notifications: " + notifs)
//                    val seen_notifs = sharedPreferences?.getInt("seen_notifs_count", 0)
//                    Log.d("seen notification", seen_notifs.toString());
//                    unseen_notif_count = notifs - seen_notifs!!
//
//                    if (unseen_notif_count <= 0) {
//                        binding.notificationCount.visibility = View.INVISIBLE
//                    } else if (unseen_notif_count <= 9) {
//                        binding.notificationCount.visibility = View.VISIBLE
//                        binding.notificationCount.text = unseen_notif_count.toString()
//                    } else {
//                        binding.notificationCount.visibility = View.VISIBLE
//                        binding.notificationCount.text = "9+"
//                    }
//                } else {
//                    Log.d("Error", "Error loading notification count", task.exception)
//                }
//            })


        binding.account.setOnClickListener {
//            startActivity(Intent(context,Account::class.java));
            (activity as MainActivity).drawer.openDrawer(Gravity.RIGHT)
        }

//        binding.pass.setOnClickListener {
//            startActivity(
//                Intent(
//                    context,
//                    NotificationActivity::class.java
//                )
//            );
//        }

        binding.search.setOnClickListener {
            findNavController(this).navigate(R.id.action_home_nav_to_searchFragment)
        }

        binding.search.setOnClickListener {
            findNavController(this).navigate(R.id.action_home_nav_to_searchFragment)
        }


        binding.compose1.setContent {
            MyContent()

            loaderView = mutableStateOf(homeViewModel.allEventsWithLive.isEmpty())

            if(loaderView.value){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colors.background.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center,

                    ) {
                    LoadingAnimation3()
                }
            }
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //scheduleDatabase=ScheduleDatabase(context)
        super.onActivityCreated(savedInstanceState)
        kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
            homeViewModel.allEventsWithLivedata.observe(requireActivity()) { data ->
                homeViewModel.allEventsWithLive.clear()
                homeViewModel.allEventsWithLive.addAll(data)
                homeViewModel.upcomingEventsLiveState.clear()
                homeViewModel.upcomingEventsLiveState.addAll(data)
                homeViewModel.forYouEvents.clear()
                homeViewModel.forYouEvents.addAll(
                    homeViewModel.allEventsWithLive.shuffled().take(7)
                )
            }
            homeViewModel.featuredEventsWithLivedata.observe(requireActivity()) { data ->
                homeViewModel.featuredEventsWithLivestate.clear()
                homeViewModel.featuredEventsWithLivestate.addAll(data)

            }
            homeViewModel.OwnEventsWithLive.observe(requireActivity()) { data ->
                homeViewModel.OwnEventsWithLiveState.clear()
                homeViewModel.OwnEventsWithLiveState.addAll(data.map { eventWithLive(it) })
                homeViewModel.OwnEventsLiveState.clear()
                homeViewModel.OwnEventsLiveState.addAll(data)
                homeViewModel.OwnEventsWithLiveState.sortedBy { data -> (data.eventdetail.starttime.date * 24 * 60 + ((data.eventdetail.starttime.hours * 60)).toFloat() + (data.eventdetail.starttime.min.toFloat())) }
//                Log.d("liked_check" , "${data}")


//                datestate1.clear();
//                datestate1.addAll(liveToWithY(data.filter { data -> data.starttime.date == 11 }))
//                datestate2.clear();
//                datestate2.addAll(liveToWithY(data.filter { data -> data.starttime.date == 12 }))
//                datestate3.clear();
//                datestate3.addAll(liveToWithY(data.filter { data -> data.starttime.date == 13 }))
            }


        }
    }


    fun liveToWithY(list: List<eventdetail>): List<ownEventBoxUiModel> {
        val ranges = mutableListOf<ClosedFloatingPointRange<Float>>()
        ranges.clear()
        val withylist = mutableListOf<ownEventBoxUiModel>()
        list.sortedBy { (((it.starttime.hours - 9) * 100).toFloat() + (it.starttime.min.toFloat() * (5f / 3f)) + 75f) }
        list.forEach { data ->
            var l = 0;
            val lengthdp = (data.durationInMin.toFloat() * (5f / 3f))
            val xdis =
                (((data.starttime.hours - 9) * 100).toFloat() + (data.starttime.min.toFloat() * (5f / 3f)) + 75f)

            for (range in ranges) {
                if (range.contains(xdis) or range.contains(xdis + lengthdp) or ((xdis..xdis + lengthdp).contains(
                        range.start
                    ) and (xdis..xdis + lengthdp).contains(range.endInclusive))
                ) {
                    l += 1
                }
                if (range.start == xdis + lengthdp) {
                    l -= 1
                }
                if (range.endInclusive == xdis) {
                    l -= 1
                }
                if ((range.start == xdis + lengthdp) and (range.endInclusive == xdis)) {
                    l += 1
                }

            }
            ranges.add((xdis..xdis + lengthdp))
            withylist.add(ownEventBoxUiModel(data, l))

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


    @OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
    @Composable
//    fun horizontalScroll(eventdetails:List<eventWithLive>){
//        val count = eventdetails.size
//        Box() {
//                Column() {
//
//                    val pagerState = rememberPagerState()
//                    //            LaunchedEffect(Unit) {
//                    //                while(true) {
//                    //                    yield()
//                    //                    delay(3000)
//                    //                    pagerState.animateScrollToPage(
//                    //                        page = (pagerState.currentPage + 1) % (pagerState.pageCount),
//                    //                        animationSpec = tween(1000)
//                    //                    )
//                    //                }
//                    //            }
////                    LaunchedEffect(key1 = pagerState.currentPage) {
////                        launch {
////
////                            delay(3000)
////                            with(pagerState) {
////                                val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
////                                tween<Float>(
////                                    durationMillis = 300,
////                                    easing = FastOutSlowInEasing
////                                )
////                                animateScrollToPage(page = target)
////
////                            }
////                        }
////                    }
////                    val hpm= if(isSystemInDarkTheme()) Modifier
////                        .padding(start = 15.dp)
////                        .fillMaxWidth()
////
////
//////                        .coloredShadow(colors.onBackground, 0.25f, 16.dp, 20.dp, -5.dp, -5.dp)
////                    else Modifier
////                        .padding(start = 15.dp,bottom=20.dp)
////                        .fillMaxWidth()
//
//
//
//                    HorizontalPager(
//                        count = count,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 15.dp)
//                            .aspectRatio(0.8f),
//                        state = pagerState,
//                        contentPadding = PaddingValues(top = 0.dp, start = 5.dp, end = 50.dp, bottom = 0.dp),
//                        itemSpacing = (5.dp),
//
//                        ) { page ->
//                        val widthparent=remember { mutableStateOf(0f)}
//                        val localdensity= LocalDensity.current
//
//
//
//                       Box(
//                            modifier = Modifier
//                                .onGloballyPositioned { coordinates ->
//                                    widthparent.value =
//                                        with(localdensity) { coordinates.size.width.dp.toPx() }
//                                }
//                                .fillMaxWidth()
//                                .aspectRatio(0.781f)
//
//                             , contentAlignment = if(calculateCurrentOffsetForPage(page)<=0)Alignment.CenterStart else Alignment.CenterEnd
//
//                        ) { val bmh=if(isSystemInDarkTheme()) Modifier else Modifier
//                           .coloredShadow(colors.onBackground, 0.01f, 18.dp, 1.dp, 20.dp, 0.dp)
//                           .coloredShadow(colors.onBackground, 0.06f, 18.dp, 1.dp, 12.dp, 0.dp)
//                           .coloredShadow(colors.onBackground, 0.24f, 18.dp, 1.dp, 4.dp, 0.dp)
//                            Box(
//                                Modifier
//                                    .coloredShadow(
//                                        colors.onBackground,
//                                        0.01f,
//                                        18.dp,
//                                        1.dp,
//                                        20.dp,
//                                        0.dp
//                                    )
//                                    .coloredShadow(
//                                        colors.onBackground,
//                                        0.06f,
//                                        18.dp,
//                                        1.dp,
//                                        12.dp,
//                                        0.dp
//                                    )
//                                    .coloredShadow(
//                                        colors.onBackground,
//                                        0.24f,
//                                        18.dp,
//                                        1.dp,
//                                        4.dp,
//                                        0.dp
//                                    )
//                            ) {
//                                val pageOffset =
//                                    calculateCurrentOffsetForPage(page).absoluteValue
//                                var widthfr= remember {mutableStateOf(0.12f)}
//                                if (pageOffset % 1f != 0f) {
//                                    widthfr.value =
//                                        lerp(
//                                            start = 0.12f,
//                                            stop = 1f,
//                                            fraction = 1f - (pageOffset.coerceIn(
//                                                0.0f,
//                                                1f
//                                            ))
//                                        )
//                                }
//
//                                Card(
//                                    modifier = Modifier
//                                        .fillMaxWidth(if (0.01f >= pageOffset) 1f else widthfr.value)
//                                        .clip(RoundedCornerShape(16.dp))
//                                        .border(
//                                            1.5.dp,
//                                            colors.onBackground,
//                                            RoundedCornerShape(16.dp)
//                                        )
//                                       ,
//                                    ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxHeight()
//                                            .fillMaxWidth()
//                                    ) {
//                                        GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)},
//                                            imageModel = eventdetails[page].eventdetail.imgurl,
//                                            contentDescription = "artist",
//                                            modifier = Modifier
//                                                .fillMaxWidth()
//                                                .fillMaxHeight()
//                                                .clickable {
//                                                    val arguments =
//                                                        bundleOf("Artist" to eventdetails[page].eventdetail.artist)
//                                                    findNavController(this@Home).navigate(
//                                                        R.id.action_home2_to_events_Details_Fragment,
//                                                        arguments
//                                                    )
//                                                },
//                                            alignment = Alignment.Center,
//                                            contentScale = ContentScale.Crop,
//                                          shimmerParams = ShimmerParams(
//                                    baseColor = if(isSystemInDarkTheme()) black else highWhite,
//                                    highlightColor = if(isSystemInDarkTheme()) highBlack else white,
//                                    durationMillis = 1500,
//                                    dropOff = 1f,
//                                    tilt = 20f
//                                ), failure = {
//                                                Box(
//                                                    modifier = Modifier
//                                                        .fillMaxWidth()
//                                                        .fillMaxHeight(),
//                                                    contentAlignment = Alignment.Center
//                                                ) {
//                                                    val composition by rememberLottieComposition(
//                                                        LottieCompositionSpec.RawRes(if (isSystemInDarkTheme())R.raw.comingsoondark else R.raw.comingsoonlight)
//                                                    )
//                                                    val progress by animateLottieCompositionAsState(
//                                                        composition,
//                                                        iterations = LottieConstants.IterateForever
//                                                    )
//                                                    LottieAnimation(
//                                                        composition,
//                                                        progress,
//                                                        modifier = Modifier.fillMaxHeight()
//                                                    )
//                                                    //                            Column(
//                                                    //                                Modifier
//                                                    //                                    .fillMaxWidth()
//                                                    //                                    .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
//                                                    //                                Image(
//                                                    //                                    modifier = Modifier
//                                                    //                                        .width(60.dp)
//                                                    //                                        .height(60.dp),
//                                                    //                                    painter = painterResource(
//                                                    //                                        id = R.drawable.ic_sad_svgrepo_com
//                                                    //                                    ),
//                                                    //                                    contentDescription = null
//                                                    //                                )
//                                                    //                                Spacer(modifier = Modifier.height(10.dp))
//                                                    //                                Text(
//                                                    //                                    text = "Image Request Failed",
//                                                    //                                    style = TextStyle(
//                                                    //                                        color = Color(0xFF747474),
//                                                    //                                        fontFamily = hk_grotesk,
//                                                    //                                        fontWeight = FontWeight.Normal,
//                                                    //                                        fontSize = 12.sp
//                                                    //                                    )
//                                                    //                                )
//                                                    //                            }
//                                                }
//
//                                            }
//
//
//                                        )
//                                        Box(
//                                            modifier = Modifier
//                                                .fillMaxSize()
//                                            //                                            .background(
//                                            //                                                brush = Brush.verticalGradient(
//                                            //                                                    colors = listOf(
//                                            //                                                        Color.Transparent,
//                                            //                                                        black,
//                                            //                                                    ),
//                                            //                                                    startY = with(LocalDensity.current) { 100.dp.toPx() }
//                                            //                                                )
//                                            //                                            )
//                                        )
//                                        Box(
//                                            modifier = Modifier
//                                                .fillMaxSize()
//                                                .padding(4.dp)
//                                                ,
//                                            contentAlignment = Alignment.BottomStart
//                                        ) {
//                                            val heightparent=remember{ mutableStateOf(0f) }
//                                            var heightoff= remember {mutableStateOf(0f)}
//
//                                                heightoff.value =
//                                                    lerp(
//                                                        start = 0f,
//                                                        stop = 1f,
//                                                        fraction = (pageOffset.coerceIn(
//                                                            0.0f,
//                                                            1f
//                                                        ))
//                                                    )
//
//                                            if(0.9999f>pageOffset ){
//
//                                            Column(
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .wrapContentHeight()
//                                                    .onGloballyPositioned { coordinates ->
//                                                        heightparent.value =
//                                                            with(localdensity) { coordinates.size.height.dp.toPx() }
//                                                    }
//                                                    .graphicsLayer {
//                                                        // Calculate the absolute offset for the current page from the
//                                                        // scroll position. We use the absolute value which allows us to mirror
//                                                        // any effects for both directions
//
//                                                        if (pageOffset % 1f != 0f) {
//                                                            translationY =
//                                                                (heightoff.value * heightoff.value * heightoff.value * heightparent.value)
//                                                        }
//                                                        //scaleY = scale
//
//
//                                                    }
//                                                    .clip(
//                                                        RoundedCornerShape(
//                                                            topStart = 16.dp,
//                                                            topEnd = 0.dp,
//                                                            bottomStart = 16.dp, bottomEnd = 16.dp
//                                                        )
//                                                    )
//                                                    .background(Color(0xff0e0e0f))
//                                                    .padding(
//                                                        top = 12.dp,
//                                                        bottom = 20.dp,
//                                                        start = 20.dp
//                                                    )
//
//
//
//
//                                                ,
//                                                horizontalAlignment = Alignment.Start
//                                            ) {
//                                                MarqueeText(
//                                                    text = "${eventdetails[page].eventdetail.artist}",
//                                                    color = Color.White,
//                                                    fontWeight = FontWeight.SemiBold,
//                                                    fontSize = 36.sp,
//                                                    fontFamily = aileron,
//                                                    textAlign = TextAlign.Start,
//                                                    gradientEdgeColor = Color.Transparent,
//                                                )
//                                                Spacer(modifier = Modifier.height(0.dp))
//
//                                                Row() {
//                                                    Text(
//                                                        text = "${eventdetails[page].eventdetail.starttime.date} Feb, ${if (eventdetails[page].eventdetail.starttime.hours > 12) "${eventdetails[page].eventdetail.starttime.hours - 12}" else eventdetails[page].eventdetail.starttime.hours}${if (eventdetails[page].eventdetail.starttime.min != 0) ":${eventdetails[page].eventdetail.starttime.min}" else ""} ${if (eventdetails[page].eventdetail.starttime.hours >= 12) "PM" else "AM"} |",
//                                                        style = TextStyle(
//                                                            color = white,
//                                                            fontFamily = aileron,
//                                                            fontWeight = FontWeight.Normal,
//                                                            fontSize = 16.sp,
//                                                        ),maxLines=1
//
//                                                    )
//
//                                                    MarqueeText(
//                                                        text = " ${eventdetails[page].eventdetail.venue}",
//                                                        style = TextStyle(
//                                                            color = white,
//                                                            fontFamily = aileron,
//                                                            fontWeight = FontWeight.Normal,
//                                                            fontSize = 16.sp
//                                                        ),gradientEdgeColor = Color.Transparent
//                                                    )
//                                                }
//                                            }
//                                            }
//
//                                        }
//
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//
////                    Box(
////                        modifier = Modifier
////                            .fillMaxHeight()
////                            .align(Alignment.CenterHorizontally)
////                            .padding(vertical = 8.dp)
////                    ) {
////                        Card(
////                            modifier = Modifier
////
////                                .width(184.dp)
////                                .height(20.dp),
////                            shape = RoundedCornerShape(36.dp),
////                            backgroundColor = midWhite
////                        ) {
////                            Box(
////                                modifier = Modifier
////                                    .fillMaxWidth()
////                                    .fillMaxHeight()
////                            ) {
////                                HorizontalPagerIndicator(
////
////                                    pagerState = pagerState,
////                                    activeColor = colors.onBackground,
////                                    inactiveColor = midWhite,
////                                    modifier = Modifier
////                                        .fillMaxWidth()
////                                        .fillMaxHeight()
////                                        .padding(3.dp),
////                                    indicatorShape = RoundedCornerShape(36.dp),
////                                    indicatorWidth = (178 / 5).dp
////
////                                )
////                            }
////                        }
////                    }
//                }
//            }
//
//
//    }
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
    fun compbox() {

        val externalFont = FontFamily(Font(R.font.futuraptbook))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            // item 1 merch
            item {
                Card(
                    elevation = 6.dp,
                    modifier = Modifier
                        .size(width = 100.dp, height = 100.dp),
                    onClick = {
//                        findNavController(this@Home)
//                            .navigate(R.id.action_home2_to_merchFragment)
                        val navView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView) as ShadowIndicatorBottomNavigationView
//                        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?
                        navView.onItemSelected(R.id.merch)
                        navView.selectedItemId = R.id.merch

                    }
                ) {
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.frame_15202_merch_background),
                            contentDescription = "",
                            modifier = Modifier
                                .size(width = 100.dp, height = 100.dp)
                        )
                        Text(
                            text = "Merch",
                            color = Color.White,
                            fontFamily = externalFont,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),

                            textAlign = TextAlign.Center,
                        )

                    }
                }
            }
            // item 2 event
            item {
                Card(
                    elevation = 6.dp,
                    modifier = Modifier
                        .size(width = 100.dp, height = 100.dp),
                    onClick = {
                        loaderView.value = true
                        val argument = bundleOf("Tab" to "0")
                        NavHostFragment.findNavController(this@Home)
                            .navigate(R.id.action_home_nav_to_competitionsFragment, argument)
                        loaderView.value = false
                    }
                ) {
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.frame_15207_events),
                            contentDescription = "",
                            modifier = Modifier
                                .size(width = 100.dp, height = 100.dp)
                        )
                        Text(
                            text = "Events",
                            color = Color.White,
                            fontFamily = externalFont,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )

                    }
                }
            }
            // item 3 competitions
            item {
                Card(
                    elevation = 6.dp,
                    modifier = Modifier
                        .size(width = 100.dp, height = 100.dp)
                        .clickable {
                            loaderView.value = true
                            val arguments = bundleOf("Tab" to "1")
                            NavHostFragment
                                .findNavController(this@Home)
                                .navigate(R.id.action_home_nav_to_competitionsFragment, arguments)
                            loaderView.value = false
                        }
                ) {
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.frame_15209_compback),
                            contentDescription = "",
                            modifier = Modifier
                                .size(width = 100.dp, height = 100.dp)
                        )
                        Text(
                            text = "Competition",
                            color = Color.White,
                            fontFamily = externalFont,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )

                    }
                }
            }
            // item 4 Stalls

            item {
                Card(
                    elevation = 6.dp,
                    modifier = Modifier
                        .size(width = 100.dp, height = 100.dp),
                    onClick = {
                        loaderView.value = true
                        val argument = bundleOf("Tab" to "2")
                        NavHostFragment.findNavController(this@Home)
                            .navigate(R.id.action_home_nav_to_competitionsFragment, argument)
                        loaderView.value = false
                    }
                ) {
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.frame_15202_merch_background),
                            contentDescription = "",
                            modifier = Modifier
                                .size(width = 100.dp, height = 100.dp)
                        )
                        Text(
                            text = "Stalls",
                            color = Color.White,
                            fontFamily = externalFont,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )

                    }
                }
            }
        }
    }
//


    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun MyContent() {
        var artist: String by rememberSaveable { mutableStateOf("") }


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
                            .paint(
                                painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                                contentScale = ContentScale.Crop
                            )
                            .border(1.dp, colors.onBackground, RoundedCornerShape(16.dp, 16.dp))
                    ) {
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

                                Spacer(modifier = Modifier.height(20.dp))
                                if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                                    eventButtons(eventWithLive = eventfordes)
                                }
                                Spacer(modifier = Modifier.height(40.dp))
                            }
                        }

                    }


                },
                sheetPeekHeight = 0.dp, sheetShape = RoundedCornerShape(16.dp, 16.dp),
                sheetBackgroundColor = colors.background,


                ) {
                Alcheringa2022Theme() {
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .paint(
                                painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                                contentScale = ContentScale.Crop
                            )
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

                        if (homeViewModel.featuredEventsWithLivestate.isNotEmpty()) {

//                            horizontalScroll(eventdetails = List(10) {homeViewModel.featuredEventsWithLivestate}.flatten())
                            HeroSection(homeViewModel.featuredEventsWithLivestate) {
                                val arguments = bundleOf("Artist" to it)

                                NavHostFragment
                                    .findNavController(this@Home)
                                    .navigate(
                                        R.id.action_home2_to_events_Details_Fragment,
                                        arguments
                                    );

                            }
                        }


                        val alphaval = 0.2f

                        Box(
                            modifier = Modifier.padding(
                                start = 20.dp,
                                bottom = 12.dp,

                                ),
                        ) {
                            Text(

                                text = "Explore",
                                fontFamily = futura,
                                fontWeight = FontWeight.Normal,
                                color = colors.onBackground,
                                fontSize = 22.sp
                            )
                        }

                        compbox()





                        if (homeViewModel.liveEvents.isNotEmpty()) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    bottom = 12.dp,
                                    top = 36.dp
                                ),
                            ) {

                                Text(

                                    text = "Ongoing Events",
                                    fontFamily = futura,
                                    fontWeight = FontWeight.Normal,
                                    color = colors.onBackground,
                                    fontSize = 22.sp
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
                                items(homeViewModel.liveEvents) { dataeach ->
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
                                    bottom = 12.dp,
                                    top = 36.dp
                                ),
                            ) {
                                Text(

                                    text = "Upcoming Events",
                                    fontFamily = futura,
                                    fontWeight = FontWeight.Normal,
                                    color = colors.onBackground,
                                    fontSize = 22.sp
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
                                    top = 30.dp,
                                    start = 20.dp,
                                    bottom = 10.dp,
                                ),

                            ) {
                            Text(

                                text = "Limited Time Merch",
                                fontFamily = futura,
                                fontWeight = FontWeight.Normal,
                                color = colors.onBackground,
                                fontSize = 22.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.selectedItemId =
                                    R.id.merch;
                            }

                        ) {
                            val drbls = listOf(
                                R.drawable.behindmerch,
                                R.drawable.behindmerch2,
                                R.drawable.behindmerch,
                                R.drawable.behindmerch2,
                                R.drawable.behindmerch,
                                R.drawable.behindmerch2,
                                R.drawable.behindmerch,
                                R.drawable.behindmerch2,

                                )
                            if (homeViewModel.merchMerch.isNotEmpty()) {
                                merchBoxnew(
                                    merch1 = homeViewModel.merchMerch
                                        .filter { it.is_available }, drbls
                                )
                            }
                        }

                        if (homeViewModel.OwnEventsLiveState.isNotEmpty()) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    bottom = 12.dp,
                                    top = 36.dp
                                ),
                            ) {
                                Text(

                                    text = "Your Liked Events",
                                    fontFamily = futura,
                                    fontWeight = FontWeight.Normal,
                                    color = colors.onBackground,
                                    fontSize = 22.sp
                                )
                            }
                        }


                        if (homeViewModel.OwnEventsWithLiveState.isNotEmpty()) {
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
                                            Event_card_Scaffold(
                                                eventdetail = dataeach,
                                                viewModelHm = homeViewModel,
                                                context = requireContext(),
                                                artist = dataeach.eventdetail.artist
                                            ) {
                                                coroutineScope.launch {
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
                        }

                        if (homeViewModel.informalList.isNotEmpty()) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    bottom = 12.dp,
                                    top = 36.dp
                                ),
                            ) {
                                Text(

                                    text = "Informals",
                                    fontFamily = futura,
                                    fontWeight = FontWeight.Normal,
                                    color = colors.onBackground,
                                    fontSize = 22.sp
                                )
                            }
                        }


                        if (homeViewModel.informalList.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 20.dp)
                                ) {
                                    items(homeViewModel.informalList) { dataeach ->
                                        context?.let {
                                            InformalCard(informal = dataeach) {
                                                val gmmIntentUri =
                                                    Uri.parse("google.navigation:q=${dataeach.location.latitude},${dataeach.location.longitude}")
                                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                mapIntent.setPackage("com.google.android.apps.maps")
                                                requireContext().startActivity(mapIntent)
                                            }
                                        }
                                    }
                                }

                            }
                        }



                        if (homeViewModel.forYouEvents.isNotEmpty()) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    bottom = 12.dp,
                                    top = 36.dp
                                ),
                            ) {
                                Text(

                                    text = "For You",
                                    fontFamily = futura,
                                    fontWeight = FontWeight.Normal,
                                    color = colors.onBackground,
                                    fontSize = 22.sp
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
                                                artist,
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
                    Divider(modifier = Modifier
                        .height(2.dp)
                        .background(darkTealGreen))
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
        var isadded = remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit, block = {
            isadded.value =
                homeViewModel.OwnEventsLiveState.any { data -> data.artist == eventWithLive.eventdetail.artist }

        })

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()

        ) {
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(287.dp),
                    shape = RoundedCornerShape(16.dp, 16.dp),

                    ) {
                    GlideImage(
                        requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC) },
                        imageModel = eventWithLive.eventdetail.imgurl,
                        contentDescription = "artist",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(284.dp),
                        //                circularReveal = CircularReveal(300),

                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        shimmerParams = ShimmerParams(
                            baseColor = if (isSystemInDarkTheme()) black else highWhite,
                            highlightColor = if (isSystemInDarkTheme()) highBlack else white,
                            durationMillis = 1500,
                            dropOff = 1f,
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
                                        if (isSystemInDarkTheme()) R.raw.comingsoondark else R.raw.comingsoonlight
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

                        },
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 16.dp)
                ) {

                    Column(horizontalAlignment = Alignment.Start) {

                        Text(
                            text = eventWithLive.eventdetail.artist,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = futura,
                            color = colors.onBackground,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.wrapContentSize()
                        )

                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = eventWithLive.eventdetail.type,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = futura,
                            color = colors.onBackground,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.wrapContentSize()


                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        if (!isadded.value) {
                            Image(
                                painter = if (isSystemInDarkTheme()) {
                                    painterResource(id = R.drawable.emptyheart_dark)
                                } else {
                                    painterResource(id = R.drawable.emptyheart_light)
                                },
                                contentDescription = null,
                                alignment = Alignment.CenterStart,
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {
                                        isadded.value =
                                            true; homeViewModel.OwnEventsWithLive.addNewItem(
                                        eventWithLive.eventdetail
                                    )
                                        scheduleDatabase.addEventsInSchedule(
                                            eventWithLive.eventdetail,
                                            context
                                        )
                                    }
                            )
                        }

                        if (isadded.value) {
                            Image(
                                painter = if (isSystemInDarkTheme()) {
                                    painterResource(id = R.drawable.filledheart_dark)
                                } else {
                                    painterResource(id = R.drawable.filledheart_light)
                                },
                                contentDescription = null,
                                alignment = Alignment.CenterStart,
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {
                                        isadded.value = false
                                        homeViewModel.OwnEventsWithLive.removeAnItem(
                                            eventWithLive.eventdetail
                                        )
                                        scheduleDatabase.DeleteItem(
                                            eventWithLive.eventdetail.artist,
                                            context
                                        )
                                        scheduleDatabase.DeleteItem(
                                            eventWithLive.eventdetail.artist,
                                            context
                                        )

                                    }
                            )
                        }
                    }

                }
            }


        }
    }

    @Composable
    fun eventButtons(eventWithLive: eventWithLive) {
        val c = Calendar.getInstance()
        val isFinished = (c.get(Calendar.YEAR) > 2024) or
                ((c.get(Calendar.YEAR) == 2024) and
                        (c.get(Calendar.MONTH) > Calendar.MARCH)) or
                ((c.get(Calendar.YEAR) == 2024) and
                        (c.get(Calendar.MONTH) == Calendar.MARCH) and
                        (c.get(Calendar.DATE) > eventWithLive.eventdetail.starttime.date)) or
                ((c.get(Calendar.YEAR) == 2024) and
                        (c.get(Calendar.MONTH) == Calendar.MARCH) and
                        (c.get(Calendar.DATE) == eventWithLive.eventdetail.starttime.date) and
                        (((eventWithLive.eventdetail.starttime.hours * 60 + eventWithLive.eventdetail.durationInMin))
                                < ((c.get(Calendar.HOUR_OF_DAY) * 60) + c.get(Calendar.MINUTE))))
        var v = homeViewModel.venuesList.find {
            it.name.replace("\\s".toRegex(), "")
                .uppercase() == eventWithLive.eventdetail.venue.replace("\\s".toRegex(), "")
                .uppercase()
        }

        if ( // TODO: replace with below check, commented out temporarily for demonstrations

            eventWithLive.eventdetail.category.replace("\\s".toRegex(), "")
                .uppercase() == "Competitions".uppercase()
        ) {
            if (isFinished) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {},
                        Modifier
                            .width(500.dp)
                            .height(50.dp)
                            .border(1.dp, colors.onBackground, shape = RoundedCornerShape(10.dp)),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            colors.background
                        )
                    ) {
                        Text(
                            text = "Event Finished!",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = futura,
                            color = colors.onBackground,
                            textAlign = TextAlign.Center
                        )
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


            }
            else {
                if( eventWithLive.eventdetail.type.replace(
                        "\\s".toRegex(),
                        ""
                    ).uppercase() == "MODEL UNITED NATIONS".replace("\\s".toRegex(), "").uppercase() ){
                    val day1_link = "https://bit.ly/3OFUO3E"
                    val day2_link = "https://bit.ly/3wdV4k0"
                    val day3_link = "https://bit.ly/48b0QQw"

                    val linkToDisplay = if(eventWithLive.eventdetail.artist.contains("Day 1", ignoreCase = true)) day1_link
                    else if (eventWithLive.eventdetail.artist.contains("Day 2", ignoreCase = true)) day2_link
                    else if (eventWithLive.eventdetail.artist.contains("Day 3", ignoreCase = true)) day3_link
                    else ""
                    if(linkToDisplay != ""){
                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .padding(horizontal = 20.dp)
                                .border(
                                    1.dp,
                                    colors.onBackground,
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
                                    startActivity(
                                        Intent(Intent.ACTION_VIEW).setData(
                                            Uri.parse(
                                                linkToDisplay
                                            )
                                        )
                                    )
                                }


                        ) {
                            Row(
                                modifier = Modifier
                                    .align(
                                        Alignment.Center
                                    )
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Quidnunc - The Newsletter",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = aileron,
                                    color = creamWhite,
                                )

                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    if (v != null) {

                        //Spacer(modifier = Modifier.width(50.dp))
                        /*Button(
                            onClick = {
                                //TODO: (Shantanu) Implement all venue locations
                                val gmmIntentUri =
                                    Uri.parse("google.navigation:q=${v.LatLng.latitude},${v.LatLng.longitude}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                startActivity(mapIntent)
                            },
                            Modifier
                                .weight(0.5f)
                                .height(50.dp)
                                .border(
                                    1.dp,
                                    colors.onBackground,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                colors.background
                            )
                        ) {
                            Text(
                                text = "Direction",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = futura,
                                color = colors.onBackground,
                                textAlign = TextAlign.Left
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Divider(color = colors.onSurface , modifier = Modifier
                                .height(30.dp)
                                .width(1.dp))

                            Spacer(modifier = Modifier.width(5.dp))

                            Icon(painter = painterResource(id = R.drawable.baseline_north_east_24) , contentDescription = null)

                        }*/
                        Box(
                            modifier = Modifier
                                .height(50.dp).weight(1f)
                                .border(
                                    1.dp,
                                    colors.onBackground,
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
                                    if( eventWithLive.eventdetail.type.replace(
                                            "\\s".toRegex(),
                                            ""
                                        ).uppercase() == "MODEL UNITED NATIONS".replace("\\s".toRegex(), "").uppercase() ){
                                        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(mun_reg_link)))
                                    }
                                    else{
                                        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://registration.alcheringa.in")))
                                    }

                                }


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
                                    text = "Register",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = aileron,
                                    color = creamWhite,
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                Divider(
                                    color = creamWhite, modifier = Modifier
                                        .height(25.dp)
                                        .width(1.dp)
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                Image(
                                    painter = painterResource(R.drawable.register),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(30.dp))

                        Box(
                            modifier = Modifier
                                .height(50.dp).weight(1f)
                                .border(
                                    1.dp,
                                    colors.onBackground,
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
                                    val gmmIntentUri =
                                        Uri.parse("google.navigation:q=${v?.LatLng?.latitude},${v?.LatLng?.longitude}")
                                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                    mapIntent.setPackage("com.google.android.apps.maps")
                                    startActivity(mapIntent)
                                }


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
                                    text = "Direction",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = aileron,
                                    color = creamWhite,
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                Divider(
                                    color = creamWhite, modifier = Modifier
                                        .height(25.dp)
                                        .width(1.dp)
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                Image(
                                    painter = painterResource(R.drawable.direction),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                    }






                }


            }
        } else {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .height(50.dp).weight(1f)
                        .border(
                            1.dp,
                            colors.onBackground,
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
                            startActivity(
                                Intent(Intent.ACTION_VIEW).setData(
                                    Uri.parse(/*eventWithLive.eventdetail.reglink*/"https://card.alcheringa.in")
                                )
                            )

                        }


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
                            text = "Get Card",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = futura,
                            color = creamWhite,
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Divider(
                            color = creamWhite, modifier = Modifier
                                .height(25.dp)
                                .width(1.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Image(
                            painter = painterResource(R.drawable.buy_pass),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }



                if( v != null) {
                    Spacer(modifier = Modifier.width(30.dp))
                    Box(
                        modifier = Modifier
                            .height(50.dp).weight(1f)
                            .border(
                                1.dp,
                                colors.onBackground,
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
                                val gmmIntentUri =
                                    Uri.parse("google.navigation:q=${v?.LatLng?.latitude},${v?.LatLng?.longitude}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                startActivity(mapIntent)
                            }


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
                                text = "Direction",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = futura,
                                color = creamWhite,
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Divider(
                                color = creamWhite, modifier = Modifier
                                    .height(25.dp)
                                    .width(1.dp)
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Image(
                                painter = painterResource(R.drawable.direction),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }




                //Spacer(modifier = Modifier.width(50.dp))


            }

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
//                        fontFamily = futura,
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
            /* if (
                 isFinished
             ){
                 Button(
                     onClick = {},
                     Modifier
                         .fillMaxWidth()
                         .height(72.dp)
                         .border(1.dp, colors.onBackground),
                     shape = RoundedCornerShape(0.dp),
                     colors = ButtonDefaults.buttonColors(
                         colors.background
                     )
                 ) {
                     Text(text="Event Finished!",
                         fontSize = 20.sp,
                         fontWeight = FontWeight.SemiBold,
                         fontFamily = aileron,
                         color = colors.onSurface)
                 }
             }
             else {
                 Row {
                     if(v != null) {
                         Button(
                             onClick = {
                                 //TODO: (Shantanu) Implement all venue locations
                                 val gmmIntentUri =
                                     Uri.parse("google.navigation:q=${v.LatLng.latitude},${v.LatLng.longitude}")
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
                     }

                     /*Button(
                         onClick = {
                             //TODO: Set Buy pass link
                             startActivity(
                                 Intent(
                                     Intent.ACTION_VIEW,
                                     Uri.parse("http://card.alcheringa.in")
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
                             text = "Get Card",
                             fontSize = 20.sp,
                             fontWeight = FontWeight.SemiBold,
                             fontFamily = aileron,
                             color = colors.onBackground
                         )

                     }*/


                 }
             }*/
        }
    }


    @Composable
    fun Bottomviewcomp(eventWithLive: eventWithLive) {
        val c = Calendar.getInstance()
        val isFinished = (c.get(Calendar.YEAR) > 2024) or
                ((c.get(Calendar.YEAR) == 2024) and
                        (c.get(Calendar.MONTH) > Calendar.MARCH)) or
                ((c.get(Calendar.YEAR) == 2024) and
                        (c.get(Calendar.MONTH) == Calendar.MARCH) and
                        (c.get(Calendar.DATE) > eventWithLive.eventdetail.starttime.date)) or
                ((c.get(Calendar.YEAR) == 2024) and
                        (c.get(Calendar.MONTH) == Calendar.MARCH) and
                        (c.get(Calendar.DATE) == eventWithLive.eventdetail.starttime.date) and
                        (((eventWithLive.eventdetail.starttime.hours * 60 + eventWithLive.eventdetail.durationInMin))
                                < ((c.get(Calendar.HOUR_OF_DAY) * 60) + c.get(Calendar.MINUTE))))

        var isadded = remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit, block = {
            isadded.value =
                homeViewModel.OwnEventsLiveState.any { data -> data.artist == eventWithLive.eventdetail.artist }

        })

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
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
            if (eventWithLive.eventdetail.venue != "") {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    )
                    {
                        Image(
                            painter = if (isSystemInDarkTheme()) {
                                painterResource(id = R.drawable.locationpin_dark)
                            } else {
                                painterResource(id = R.drawable.locationpin_light)
                            },
                            contentDescription = null,


                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(colors.onBackground),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = eventWithLive.eventdetail.venue,
//                            style = TextStyle(
//                                color = colors.onBackground,
//                                fontFamily = futura,
//                                fontWeight = FontWeight.Medium,
//                                fontSize = 25.sp
//                            )
//                        )

                        MarqueeText(
                            text = eventWithLive.eventdetail.venue,
                            style = TextStyle(
                                color = colors.onBackground,
                                fontFamily = futura,
                                fontWeight = FontWeight.Medium,
                                fontSize = 25.sp
                            ),
                            gradientEdgeColor = Color.Transparent
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(0.8f)
                    )
                    {
                        Image(
                            painter = if (eventWithLive.isLive.value) {
                                painterResource(id = R.drawable.schedule_live)
                            } else {
                                if (isSystemInDarkTheme()) {
                                    painterResource(id = R.drawable.schedule_dark)
                                } else {
                                    painterResource(id = R.drawable.schedule_light)
                                }
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .width(22.dp)
                                .height(22.dp),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop,
                            //colorFilter = ColorFilter.tint(colors.onBackground)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        MarqueeText(
                            text = if (eventWithLive.isLive.value) {
                                "Live"
                            } else {
                                "${eventWithLive.eventdetail.starttime.date} Feb, ${if (eventWithLive.eventdetail.starttime.hours > 12) "${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min != 0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"} "
                            },
                            style = TextStyle(
                                color = if (eventWithLive.isLive.value) {
                                    darkTealGreen
                                } else {
                                    colors.onBackground
                                },
                                fontFamily = futura,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            ),
                            gradientEdgeColor = Color.Transparent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = eventfordes.eventdetail.descriptionEvent,
                fontFamily = futura,
                fontWeight = FontWeight.Normal,
                color = colors.onBackground,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
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


    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun merchBoxnew(merch1: List<merchModel>, drbls: List<Int>) {

        val configuration = LocalConfiguration.current
        val merch = merch1.reversed()
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp
        val pagerState = rememberPagerState(
            pageCount = { merch.size },
            initialPage = merch.size - 1
        )
//

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            state = pagerState,
            beyondBoundsPageCount = 4,
            pageSpacing = (-screenWidth / 2),
            reverseLayout = true

        )
        { page ->
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationX =
                            if (pagerState.offsetForPage(page) > 0) (pagerState.offsetForPage(page)) * -(screenWidth.toPx() / 2 - 50) else (pagerState.offsetForPage(
                                page
                            )) * (screenWidth.toPx() / 2)
                        translationY = if (pagerState.offsetForPage(page) > 0) abs(
                            pagerState.offsetForPage(page)
                        ) * -50 else 0f
                    }
                    .padding(start = 20.dp, end = 34.dp),

                contentAlignment = Alignment.BottomCenter,
            ) {
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.background)
                        .fillMaxWidth()
                        .aspectRatio(1.94f)
                        .padding(horizontal = 0.dp),
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    border = BorderStroke(
                        2.dp,
                        brush = if (page % 2 == 0) {
                            Brush.verticalGradient(
                                0f to colors.primary,
                                1f to borderdarkpurple
                            )
                        } else {
                            Brush.verticalGradient(
                                0f to darkTealGreen,
                                1f to borderdarkTealGreen
                            )
                        }

                    )
                ) {
                    Box(modifier = Modifier
                        .clickable {
                            val navView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView) as ShadowIndicatorBottomNavigationView
//                        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?
                            navView.onItemSelected(R.id.merch)
                            navView.selectedItemId = R.id.merch

//                            val arguments = bundleOf("merchId" to merch.size - page - 1)
//                            NavHostFragment
//                                .findNavController(this@Home)
//                                .navigate(R.id.action_home2_to_merchFragment, arguments)

                            //                fm.beginTransaction()
                            //                    .replace(R.id.fragmentContainerView,MerchFragment()).addToBackStack(null)
                            //                    .commit()
                        }
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))

                    ) {
                        Image(
                            painter = painterResource(id = drbls[page]),
                            contentDescription = null,
                            Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.94f)
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

                                ) {
                                    //                                Text(
                                    //                                    text = merch[page].Name.uppercase(),
                                    //                                    color = Color.White,
                                    //                                    fontWeight = FontWeight.Normal,
                                    //                                    fontSize = 32.sp,
                                    //                                    fontFamily = star_guard,
                                    //                                )
                                    Column(Modifier.padding(top = 15.dp)) {
                                        Text(
                                            text = merch[page].material,
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily(Font(R.font.futuraptbook)),
                                            color = black
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = merch[page].name,
                                            fontSize = 18.sp,
                                            fontFamily = FontFamily(Font(R.font.futuraptbook)),
                                            color = black
                                        )
//                                        Canvas(
//                                            modifier = Modifier.fillMaxWidth(),
//                                            onDraw = {
//                                                drawIntoCanvas {
//                                                    it.nativeCanvas.drawText(
//                                                        merch[page].name,
//                                                        0f,
//                                                        0.dp.toPx(),
//                                                        textPaintStroke
//                                                    )
//                                                    it.nativeCanvas.drawText(
//                                                        merch[page].name,
//                                                        0f,
//                                                        0.dp.toPx(),
//                                                        textPaint
//                                                    )
//                                                }
//                                            }
//                                        )
//                                        Spacer(modifier = Modifier.height(4.dp))
//                                        Text(
//                                            text = merch[page].material,
//                                            color = black,
//                                            fontWeight = FontWeight.Normal,
//                                            fontSize = 12.sp,
//                                            fontFamily = star_guard,
//                                        )

                                    }

                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(bottom = 15.dp),
                                        verticalArrangement = Arrangement.Bottom
                                    ) {
                                        Text(
                                            text = "Rs. ${merch[page].price}/-",
                                            color = black,
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily(Font(R.font.futuraptbook))
                                        )
//                                        Canvas(
//                                            modifier = Modifier.fillMaxWidth(),
//                                            onDraw = {
//                                                drawIntoCanvas {
//                                                    it.nativeCanvas.drawText(
//                                                        "At just",
//                                                        0f,
//                                                        0.dp.toPx(),
//                                                        textPaintStroke1
//                                                    )
//                                                    it.nativeCanvas.drawText(
//                                                        "At just",
//                                                        0f,
//                                                        0.dp.toPx(),
//                                                        textPaint1
//                                                    )
//                                                }
//                                            }
//                                        )
//                                        Spacer(modifier = Modifier.height(28.dp))
//
//                                        Canvas(
//                                            modifier = Modifier.fillMaxWidth(),
//                                            onDraw = {
//                                                drawIntoCanvas {
//                                                    it.nativeCanvas.drawText(
//                                                        "Rs. " + merch[page].price,
//                                                        0f,
//                                                        0.dp.toPx(),
//                                                        textPaintStroke2
//                                                    )
//                                                    it.nativeCanvas.drawText(
//                                                        "Rs. " + merch[page].price,
//                                                        0f,
//                                                        0.dp.toPx(),
//                                                        textPaint2
//                                                    )
//                                                }
//                                            }
//                                        )
                                    }

                                }

                                GlideImage(requestOptions = {
                                    RequestOptions.diskCacheStrategyOf(
                                        DiskCacheStrategy.AUTOMATIC
                                    )
                                },
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .rotate(10f),
                                    imageModel = merch[page].image_url,
                                    contentDescription = "merch",
                                    contentScale = ContentScale.Fit,
                                    alignment = Alignment.Center,
                                    shimmerParams = ShimmerParams(
                                        baseColor = if (isSystemInDarkTheme()) black else highWhite,
                                        highlightColor = if (isSystemInDarkTheme()) highBlack else white,
                                        durationMillis = 1500,
                                        dropOff = 1f,
                                        tilt = 20f
                                    ),
                                    failure = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            val composition by rememberLottieComposition(
                                                LottieCompositionSpec.RawRes(R.raw.failure)
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
//
                                        }

                                    }
                                )

                            }


                        }
                    }

                }
            }
//            Box(
//                Modifier.fillMaxSize(),
//                Alignment.TopEnd
//            ){
//                GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)},
//                    modifier = Modifier
//                        .padding(end = 16.dp)
//                        .fillMaxWidth(0.5f),
//                    imageModel = merch[page].image_url, contentDescription = "merch", contentScale = ContentScale.Fit,
//                    alignment = Alignment.Center,
//                  shimmerParams = ShimmerParams(
//                                    baseColor = if(isSystemInDarkTheme()) black else highWhite,
//                                    highlightColor = if(isSystemInDarkTheme()) highBlack else white,
//                                    durationMillis = 1500,
//                                    dropOff = 1f,
//                                    tilt = 20f
//                                ),failure = {
//                        Box(modifier= Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight(), contentAlignment = Alignment.Center) {
//                            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.failure))
//                            val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
//                            LottieAnimation(
//                                composition,
//                                progress,
//                                modifier = Modifier.fillMaxHeight()
//                            )
////
//                        }
//
//                    }
//                )
//            }
        }
    }


    @Composable
    fun Lottieonactivedelete(rId: Int) {

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


//    @Composable
//    fun newhorizontalscroll(eventdetails: SnapshotStateList<eventWithLive>) {
//        var count = remember { mutableStateOf(eventdetails.size) }
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
////                    .padding(start=20.dp)
//                .wrapContentHeight()
//        ) {
//            Column() {
//                ViewPagernew(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight(),
//                ) {
//                    repeat(count.value) { page ->
//                        ViewPagerChild {
//
//                            Card(
//                                border = BorderStroke(1.dp, colors.secondary),
//                                shape = RoundedCornerShape(16.dp)
//
//                            ) {
//                                Box() {
//
//                                    Card(
//                                        modifier = Modifier.fillMaxWidth(),
//
//
//                                        ) {
//                                        Box(
//                                            modifier = Modifier
//                                                .fillMaxHeight()
//                                                .fillMaxWidth()
//                                        ) {
//                                            GlideImage(requestOptions = {
//                                                RequestOptions.diskCacheStrategyOf(
//                                                    DiskCacheStrategy.AUTOMATIC
//                                                )
//                                            },
//                                                imageModel = eventdetails[page].eventdetail.imgurl,
//                                                contentDescription = "artist",
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .aspectRatio(0.76f)
//                                                    .clickable {
//                                                        val arguments =
//                                                            bundleOf("Artist" to eventdetails[page].eventdetail.artist)
//                                                        findNavController(this@Home).navigate(
//                                                            R.id.action_home2_to_events_Details_Fragment,
//                                                            arguments
//                                                        )
//                                                    },
//                                                alignment = Alignment.Center,
//                                                contentScale = ContentScale.Crop,
//                                                shimmerParams = ShimmerParams(
//                                                    baseColor = if (isSystemInDarkTheme()) black else highWhite,
//                                                    highlightColor = if (isSystemInDarkTheme()) highBlack else white,
//                                                    durationMillis = 1500,
//                                                    dropOff = 1f,
//                                                    tilt = 20f
//                                                ), failure = {
//                                                    Box(
//                                                        modifier = Modifier
//                                                            .fillMaxWidth()
//                                                            .fillMaxHeight(),
//                                                        contentAlignment = Alignment.Center
//                                                    ) {
//                                                        val composition by rememberLottieComposition(
//                                                            LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.comingsoondark else R.raw.comingsoonlight)
//                                                        )
//                                                        val progress by animateLottieCompositionAsState(
//                                                            composition,
//                                                            iterations = LottieConstants.IterateForever
//                                                        )
//                                                        LottieAnimation(
//                                                            composition,
//                                                            progress,
//                                                            modifier = Modifier.fillMaxHeight()
//                                                        )
//                                                    }
//
//                                                }
//
//
//                                            )
//                                            Box(
//                                                modifier = Modifier
//                                                    .fillMaxSize()
//                                                    .background(
//                                                        brush = Brush.verticalGradient(
//                                                            colors = listOf(
//                                                                Color.Transparent,
//                                                                black,
//                                                            ),
//                                                            startY = with(LocalDensity.current) { 100.dp.toPx() }
//                                                        )
//                                                    )
//                                            )
//                                            Box(
//                                                modifier = Modifier
//                                                    .fillMaxSize()
//                                                    .padding(horizontal = 20.dp, vertical = 28.dp),
//                                                contentAlignment = Alignment.BottomStart
//                                            ) {
//                                                Column(
//                                                    modifier = Modifier.fillMaxWidth(),
//                                                    horizontalAlignment = Alignment.Start
//                                                ) {
//                                                    Text(
//                                                        text = eventdetails[page].eventdetail.artist,
//                                                        color = Color.White,
//                                                        fontWeight = FontWeight.SemiBold,
//                                                        fontSize = 36.sp,
//                                                        fontFamily = aileron,
//                                                        textAlign = TextAlign.Start
//                                                    )
//                                                    Spacer(modifier = Modifier.height(4.dp))
//                                                    Text(
//                                                        text = eventdetails[page].eventdetail.category,
//                                                        style = TextStyle(
//                                                            color = colorResource(id = R.color.White),
//                                                            fontFamily = aileron,
//                                                            fontWeight = FontWeight.Normal,
//                                                            fontSize = 16.sp
//                                                        )
//                                                    )
//                                                    Spacer(modifier = Modifier.height(4.dp))
//
//                                                    Row {
//                                                        Text(
//                                                            text = "${eventdetails[page].eventdetail.starttime.date} Feb, ${if (eventdetails[page].eventdetail.starttime.hours > 12) "${eventdetails[page].eventdetail.starttime.hours - 12}" else eventdetails[page].eventdetail.starttime.hours}${if (eventdetails[page].eventdetail.starttime.min != 0) ":${eventdetails[page].eventdetail.starttime.min}" else ""} ${if (eventdetails[page].eventdetail.starttime.hours >= 12) "PM" else "AM"} ",
//                                                            style = TextStyle(
//                                                                color = white,
//                                                                fontFamily = aileron,
//                                                                fontWeight = FontWeight.Normal,
//                                                                fontSize = 16.sp
//                                                            )
//                                                        )
//
//                                                        //Spacer(modifier = Modifier.width(4.dp))
//                                                        Text(
//                                                            text = "| ${eventdetails[page].eventdetail.venue}",
//                                                            style = TextStyle(
//                                                                color = white,
//                                                                fontFamily = aileron,
//                                                                fontWeight = FontWeight.Normal,
//                                                                fontSize = 16.sp
//                                                            )
//                                                        )
//                                                    }
//                                                }
//
//                                            }
//
//
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//            }
//        }
//    }


    override fun onResume() {
//        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.menu?.findItem(R.id.home_nav)?.setChecked(true);
//       MainActivity.index=R.id.home_nav;
        super.onResume()

//        var unseen_notif_count = 0
//
//        firebaseFirestore = FirebaseFirestore.getInstance()
//        sharedPreferences = activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)
//
//        firebaseFirestore!!.collection("Notification").get()
//            .addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot> ->
//                if (task.isSuccessful) {
//                    val notifs = task.result.size()
//                    Log.d("Notification Count", "No of notifications: " + notifs)
//                    val seen_notifs = sharedPreferences?.getInt("seen_notifs_count", 0)
//                    Log.d("seen notification", seen_notifs.toString());
//                    unseen_notif_count = notifs - seen_notifs!!
//
//                    if (unseen_notif_count <= 0) {
//                        binding.notificationCount.visibility = View.INVISIBLE
//                    } else if (unseen_notif_count <= 9) {
//                        binding.notificationCount.visibility = View.VISIBLE
//                        binding.notificationCount.text = unseen_notif_count.toString()
//                    } else {
//                        binding.notificationCount.visibility = View.VISIBLE
//                        binding.notificationCount.text = "9+"
//                    }
//                } else {
//                    Log.d("Error", "Error loading notification count", task.exception)
//                }
//            })
    }

}



