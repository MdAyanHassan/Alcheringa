package com.alcheringa.alcheringa2022

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.widget.Constraints
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.*
import com.alcheringa.alcheringa2022.databinding.FragmentHomeBinding
import com.alcheringa.alcheringa2022.ui.theme.*
import com.google.accompanist.pager.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.microsoft.identity.common.internal.cache.SharedPreferencesFileManager.getSharedPreferences
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
    lateinit var fm:FragmentManager
    lateinit var binding: FragmentHomeBinding
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

    var firebaseFirestore: FirebaseFirestore? = null
    var sharedPreferences: SharedPreferences? = null

    val events=mutableListOf(

            eventdetail(
                    "JUBIN NAUTIYAL2",
                    "Pro Nights",
                    OwnTime(11,9,0),
                    "ONLINE", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fjubin.jpg?alt=media&token=90983a9f-bd0d-483d-b2a8-542c1f1c0acb"
            ),

            eventdetail(
                    "DJ SNAKE4",
                    "Pro Nights",
                OwnTime(11,12,0),
                    "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
            ),
            eventdetail(
                    "TAYLOR SWIFT6",
                    "Pro Nights",
                OwnTime(11,14,0),
                    "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
            )
        ,

        eventdetail(
            "DJ SNAKE7",
            "Pro Nights",
            OwnTime(13,10,0),
            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
        ),
        eventdetail(
            "TAYLOR SWIFT8",
            "Pro Nights",
            OwnTime(13,15,0),
            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
        )
        ,
        eventdetail(
            "TAYLOR SWIFT9",
            "Pro Nights",
            OwnTime(13,14,30),
            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f"
        )



    )


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
            startActivity(Intent(context,Account::class.java));

        }

        binding.notification.setOnClickListener{
            startActivity(Intent(context,
                NotificationActivity::class.java));
        }
        binding.compose1.setContent {
            Alcheringa2022Theme {
                val scrollState= rememberScrollState()

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(scrollState)) {
                    if (scrollState.value==0){binding.logoAlcher.setImageDrawable(resources.getDrawable(R.drawable.ic_alcher_final_logo))
                            binding.logoAlcher.layoutParams.width=with(LocalDensity.current){162.dp.toPx().toInt()}
                        binding.logoAlcher.layoutParams.height=with(LocalDensity.current){50.dp.toPx().toInt()}
                    }
                    else{binding.logoAlcher.setImageDrawable(resources.getDrawable(R.drawable.ic_alcher_final_small_logo));
                        binding.logoAlcher.layoutParams.width= ViewGroup.LayoutParams.WRAP_CONTENT
                        binding.logoAlcher.layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT
                    }

                    horizontalScroll(eventdetails = homeViewModel.featuredEventsWithLivestate)
                    if (homeViewModel.allEventsWithLive.filter { data-> data.isLive.value }.size!=0) {
                        Text(
                            modifier = Modifier.padding(
                                start = 20.dp,
                                bottom = 12.dp,
                                top = 48.dp
                            ),
                            text = "ONGOING EVENTS",
                            fontFamily = clash,
                            fontWeight = FontWeight.W500,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                    Box(
                            modifier = Modifier
                                    .fillMaxWidth()

                    ) {
                        LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            items(homeViewModel.allEventsWithLive.filter { data-> data.isLive.value }) { dataeach -> context?.let { Event_card(eventdetail = dataeach,homeViewModel, it,fm) } }
                        }

                    }
                    if(homeViewModel.upcomingEventsLiveState.filter { data-> !(data.isLive.value) }.isNotEmpty()) {
                        Text(
                            modifier = Modifier.padding(
                                start = 20.dp,
                                bottom = 12.dp,
                                top = 48.dp
                            ),
                            text = "UPCOMING EVENTS",
                            fontFamily = clash,
                            fontWeight = FontWeight.W500,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                    Box(modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            items(homeViewModel.upcomingEventsLiveState.filter { data-> !(data.isLive.value) }) { dataeach -> context?.let { Event_card_upcoming(eventdetail = dataeach,homeViewModel, it,fm) } }
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp, top = 48.dp)
                        .clickable {
                            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.selectedItemId =
                                R.id.merch;
                        }
//                        main.bottomNavigationView.selectedItemId=R.id.merch}

                    ) {
                        val drbls= listOf(R.drawable.hoodie_bg,R.drawable.tale_bg,R.drawable.voyager_bg)
                        merchBox(merch = homeViewModel.merchhome
                            .filter { it.Available }
                                            ,drbls)
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(start = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text( text = "MY SCHEDULE", fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 18.sp)
                        Text(text = "See Full Schedule>", fontFamily = hk_grotesk, fontSize = 15.sp, fontWeight = FontWeight.W500, color =Color(0xffEE6337)
                            ,modifier = Modifier.clickable {

                                activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.selectedItemId =
                                    R.id.schedule;  activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.selectedItemId =
                                    R.id.schedule;
//                            fm.beginTransaction()
//                                .replace(R.id.fragmentContainerView,Schedule()).addToBackStack(null)
//                                .commit()
//                            })
                            })
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    mySchedule()
                    Text(modifier = Modifier.padding(start = 20.dp, bottom = 12.dp, top = 48.dp), text = "RECOMMENDED FOR YOU", fontFamily = clash, fontWeight = FontWeight.W500, color = Color.White, fontSize = 18.sp)
                    Box(modifier = Modifier
                        .fillMaxWidth()
                    ) {

                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            items(homeViewModel.allEventsWithLive.take(7)) { dataeach -> context?.let { Event_card(eventdetail = dataeach,homeViewModel, it,fm) } }
                        }
                    }


                }
            }
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
            }
            homeViewModel.featuredEventsWithLivedata.observe(requireActivity()){   data->
                homeViewModel.featuredEventsWithLivestate.clear()
                homeViewModel.featuredEventsWithLivestate.addAll(data)
            }
            homeViewModel.OwnEventsWithLive.observe(requireActivity()) { data ->
                homeViewModel.OwnEventsLiveState.clear()
                homeViewModel.OwnEventsLiveState.addAll(data)
                datestate1.clear();
                datestate1.addAll(liveToWithY(data.filter { data -> data.starttime.date == 11 }))
                datestate2.clear();
                datestate2.addAll(liveToWithY(data.filter { data -> data.starttime.date == 12 }))
                datestate3.clear();
                datestate3.addAll(liveToWithY(data.filter { data -> data.starttime.date == 13 }))
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
    fun horizontalScroll(eventdetails:SnapshotStateList<eventWithLive>){

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
            LaunchedEffect(key1 = pagerState.currentPage) {
                launch {

                    delay(3000)
                    with(pagerState) {
                        val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
                        animateScrollToPage(
                            page = target,
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
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
                                        .fillMaxWidth().background(blackbg)
                            ) {
                                GlideImage(
                                        imageModel = eventdetails[page].eventdetail.imgurl,
                                        contentDescription = "artist",
                                        modifier= Modifier
                                            .fillMaxWidth()
                                            .height(473.dp)
                                            .clickable {
                                                val frg = Events_Details_Fragment()
                                                frg.arguments =
                                                    bundleOf("Artist" to eventdetails[page].eventdetail.artist)
                                                fm
                                                    .beginTransaction()
                                                    .replace(R.id.fragmentContainerView, frg)
                                                    .addToBackStack(null)
                                                    .commit()
                                            },
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Crop,
                                    shimmerParams = ShimmerParams(
                                        baseColor = blackbg,
                                        highlightColor = Color.LightGray,
                                        durationMillis = 350,
                                        dropOff = 0.65f,
                                        tilt = 20f
                                    ),failure = {
                                        Box(modifier= Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(), contentAlignment = Alignment.Center) {
                                            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.comingsoon))
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


                                )
                                Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        blackbg,
                                                    ),
                                                    startY = with(LocalDensity.current) { 100.dp.toPx() }
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
                                                text = eventdetails[page].eventdetail.artist.uppercase(),
                                                color = Color.White,
                                                fontWeight = FontWeight.W600,
                                                fontSize = 60.sp,
                                                fontFamily = vanguard, textAlign = TextAlign.Center
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
                                                    text = "${eventdetails[page].eventdetail.starttime.date} Mar, ${if(eventdetails[page].eventdetail.starttime.hours>12)"${eventdetails[page].eventdetail.starttime.hours-12}" else eventdetails[page].eventdetail.starttime.hours}${if (eventdetails[page].eventdetail.starttime.min!=0) ":${eventdetails[page].eventdetail.starttime.min}" else ""} ${if (eventdetails[page].eventdetail.starttime.hours>=12)"PM" else "AM"} ",
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
       datestate=remember{ mutableStateOf(1)}
       Column() {

           Row(
                   Modifier
                           .fillMaxWidth()
                           .padding(horizontal = 32.dp), horizontalArrangement = Arrangement.SpaceBetween) {
               Text(text = "Day 1", fontWeight = FontWeight.W700, fontFamily = clash, color = color1, fontSize = 18.sp,
                   modifier = Modifier.clickable { color1= orangeText;color2= greyText;color3=
                       greyText
                       datestate.value=1
                   })

               Text(text = "Day 2", fontWeight = FontWeight.W700, fontFamily = clash, color = color2,fontSize = 18.sp,
                   modifier = Modifier.clickable { color1= greyText;color2= orangeText;color3= greyText;
                       datestate.value=2
               })

               Text(text = "Day 3", fontWeight = FontWeight.W700, fontFamily = clash, color = color3,fontSize = 18.sp,
                   modifier = Modifier.clickable { color1= greyText;color2= greyText;color3=
                       orangeText
                       datestate.value=3
                   })

           }
           Spacer(modifier = Modifier.height(16.dp))
           scheduleBox()


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
                            frg.arguments = bundleOf("Artist" to eventdetail.eventWithLive.artist)
                            fm
                                    .beginTransaction()
                                    .replace(R.id.fragmentContainerView, frg)
                                    .addToBackStack(null)
                                    .commit()

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



                                        if ((182.dp..221.dp).contains(offsetY.value.toDp()) and
                                                ((horiscrollowneventstate.value + (boxwidth.value.toPx() / 2).toInt() - lengthdp.value.dp
                                                        .toPx()
                                                        .toInt()..horiscrollowneventstate.value + (boxwidth.value.toPx() / 2).toInt()).contains(
                                                        (offsetX.value)
                                                                .toInt()
                                                ))
                                        ) {
                                            onActiveDel.value = true
                                        } else {
                                            onActiveDel.value = false
                                        }


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
                                                lengthdp.animateTo(0f, animationSpec = tween(
                                                        durationMillis = 300,
                                                        delayMillis = 0, easing = FastOutSlowInEasing))
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


    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun merchBox(merch: List<merchmodelforHome>, drbls:List<Int>){
        val pagerState = rememberPagerState()
        LaunchedEffect(key1 = pagerState.currentPage) {
            launch {

                delay(2000)
                with(pagerState) {
                    val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
                    animateScrollToPage(
                        page = target,
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = FastOutSlowInEasing
                        )
                    )

                }
            }
        }


        HorizontalPager(
            count = merch.size, modifier = Modifier
                .fillMaxWidth()
                .height(218.dp), state = pagerState
        ) { page ->
        Card(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 0.dp,
        ) {
            Box(modifier = Modifier
                    .clickable {
                        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.selectedItemId = R.id.merch;

//                fm.beginTransaction()
//                    .replace(R.id.fragmentContainerView,MerchFragment()).addToBackStack(null)
//                    .commit()
                    }
                    .height(218.dp)
                    .fillMaxWidth()
                    ){
                Image(painter = painterResource(id = drbls[page]), contentDescription = null,Modifier.height(218.dp).fillMaxWidth(), contentScale = ContentScale.Crop, alignment = Alignment.Center)

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
                            text = merch[page].Name.uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.W900,
                            fontSize = 46.sp,
                            fontFamily = FontFamily(Font(R.font.morganiteextrabold))
                        )

                        Text(text = merch[page].Type.uppercase(), style = MaterialTheme.typography.h1)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Out now!", fontFamily = clash, fontSize = 16.sp, fontWeight = FontWeight.W500, color = Color.LightGray)
                        Spacer(modifier = Modifier.height(20.dp))
                        Box(Modifier.height(44.dp).width(113.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xffEE6337)), contentAlignment = Alignment.Center){
                        Text(text = "BUY NOW", color = Color.White, fontFamily = clash, fontWeight = FontWeight.W700, fontSize = 16.sp)}

                    }

                    GlideImage(modifier = Modifier
                            .width(167.dp)
                            .height(167.dp).align(Alignment.Bottom),
                    imageModel = merch[page].Image, contentDescription = "merch", contentScale = ContentScale.Crop,
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

    override fun onResume() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.menu?.findItem(R.id.home_nav)?.setChecked(true);
       MainActivity.index=R.id.home_nav;
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

