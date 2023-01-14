package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.ScheduleFragmentBinding
import com.alcheringa.alcheringa2022.ui.theme.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Schedule : Fragment() {
    private lateinit var binding: ScheduleFragmentBinding
    private lateinit var fm: FragmentManager
    private val homeviewmodel:viewModelHome by activityViewModels()
    private val datestate0 = mutableStateListOf<eventWithLive>()
    private val datestate1 = mutableStateListOf<eventWithLive>()
    private val datestate2 = mutableStateListOf<eventWithLive>()
    private val datestate3 = mutableStateListOf<eventWithLive>()
    private var datestate = mutableStateOf<Int>(0)
    var schedule=false;

    var firebaseFirestore: FirebaseFirestore? = null
    var sharedPreferences: SharedPreferences? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fm = parentFragmentManager

        CoroutineScope(Dispatchers.Main).launch {
            homeviewmodel.allEventsWithLivedata.observe(requireActivity()){data->
                datestate0.clear();
                datestate0.addAll((data.filter { data -> data.eventdetail.starttime.date == 10 }))
                datestate1.clear();
                datestate1.addAll((data.filter { data -> data.eventdetail.starttime.date == 11 }))
                datestate2.clear();
                datestate2.addAll((data.filter { data -> data.eventdetail.starttime.date == 12 }))
                datestate3.clear();
                datestate3.addAll((data.filter { data -> data.eventdetail.starttime.date == 13 }))
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScheduleFragmentBinding.inflate(layoutInflater)
        return binding.root
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
//            startActivity(Intent(context, ProfilePage::class.java));
            (activity as MainActivity).drawer.openDrawer(Gravity.RIGHT)
        }
        binding.pass.setOnClickListener{
            startActivity(Intent(context, NotificationActivity::class.java));
        }
        binding.scheduleCompose.setContent {
            Alcheringa2022Theme(){
            mySchedule()}
        }
    }
    @Composable
    fun mySchedule() {
        val headerbgcolor=if(isSystemInDarkTheme())Color(0xff1C1C1C)else Color(0xffFAFBF5)
        val bgcolor=if(isSystemInDarkTheme())Color(0xff2f2f2f)else Color(0xffababab)

        Column() {

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(colors.onBackground)
                .padding(horizontal = 9.dp, vertical = 6.dp), contentAlignment = Alignment.Center) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(colors.background)
                        .padding(2.dp),
//                        .padding(horizontal = 20.dp)
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(0.25f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(4.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = if (datestate.value == 0) colors.onBackground else colors.background),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Day 0",
                                    fontWeight = FontWeight.W400,
                                    fontFamily = star_guard,
                                    color = if (datestate.value == 0) colors.background else colors.onBackground,
//                            color = color1,
//                                color = bgcolor,
                                    fontSize = 18.sp,
                                    modifier = Modifier.clickable {
                                        datestate.value = 0;
                                    })
                            }
                        }
//

                    }
                    Column(
                        Modifier
                            .fillMaxWidth(0.33f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize(),
                            shape = RoundedCornerShape(4.dp)
                        ) {

                            Box(
                                modifier = Modifier.background(color = if(datestate.value==1)colors.onBackground else colors.background,),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Day 1",
                                    fontWeight = FontWeight.W400,
                                    fontFamily = star_guard,
                                    color =if(datestate.value==1)colors.background else colors.onBackground ,
//                            color = color1,
//                                color = bgcolor,
                                    fontSize = 18.sp,
                                    modifier = Modifier.clickable {
                                        datestate.value = 1;
                                    })
//                                Spacer(modifier = Modifier.height(9.dp))
//                    if (color2.value == orangeText.value) {
//                        Canvas(
//                            modifier = Modifier
//                                .wrapContentHeight()
//                                .fillMaxWidth()
//
//                        ) {
//                            drawLine(
//                                color = orangeText,
//                                start = Offset(0f, 0f),
//                                end = Offset(size.width, 0f),
//                                strokeWidth = 2.dp.toPx(),
//                            )
//                        }
//                    }
                            }
                        }
                    }

                    Column(
                        Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize(),
                            shape = RoundedCornerShape(4.dp)
                        ) {

                            Box(
                                modifier = Modifier.background(color = if(datestate.value==2)colors.onBackground else colors.background),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Day 2",
                                    fontWeight = FontWeight.W400,
                                    fontFamily = star_guard,
                                    color =if(datestate.value==2)colors.background else colors.onBackground,
//                            color = color1,
//                                color = bgcolor,
                                    fontSize = 18.sp,
                                    modifier = Modifier.clickable {

                                        datestate.value =2;
                                    })
//                                Spacer(modifier = Modifier.height(9.dp))
//                    if (color3.value == orangeText.value) {
//                        Canvas(
//                            modifier = Modifier
//                                .wrapContentHeight()
//                                .fillMaxWidth()
//
//                        ) {
//                            drawLine(
//                                color = orangeText,
//                                start = Offset(0f, 0f),
//                                end = Offset(size.width, 0f),
//                                strokeWidth = 2.dp.toPx(),
//                            )
//                        }
//                    }
                            }
                        }
                    }
                    Column(
                        Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize(),
                            shape = RoundedCornerShape(4.dp)
                        ) {

                            Box(
                                modifier = Modifier.background(color = if(datestate.value==3)colors.onBackground else colors.background),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Day 3",
                                    fontWeight = FontWeight.W400,
                                    fontFamily = star_guard,
                                    color = if(datestate.value==3)colors.background else colors.onBackground,
//                            color = color1,
//                                color = bgcolor,
                                    fontSize = 18.sp,
                                    modifier = Modifier.clickable {
                                        datestate.value = 3;
                                    })
//                                Spacer(modifier = Modifier.height(9.dp))
//                    if (color2.value == orangeText.value) {
//                        Canvas(
//                            modifier = Modifier
//                                .wrapContentHeight()
//                                .fillMaxWidth()
//
//                        ) {
//                            drawLine(
//                                color = orangeText,
//                                start = Offset(0f, 0f),
//                                end = Offset(size.width, 0f),
//                                strokeWidth = 2.dp.toPx(),
//                            )
//                        }
//                    }
                            }
                        }
                    }
                }
            }
            val horiscrollstate = rememberScrollState()
            Row(
                Modifier
//                    .width(500.dp)
                    .horizontalScroll(horiscrollstate)
                    .background(color = bgcolor)
            ) {
                Spacer(modifier = Modifier.width(65.dp))
                Row(
                    Modifier
//                        .width(1656.dp)
                        .width(5156.dp)
                        , horizontalArrangement = Arrangement.SpaceEvenly
                ) { Box(modifier = Modifier
                    .width(200.dp)
                    .height(54.dp)
                    .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                    .background(headerbgcolor), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Auditorium",
                        fontWeight = FontWeight.W400,
                        fontSize = 20.sp,
                        fontFamily = star_guard,
                        style = TextStyle(letterSpacing = 0.5.sp),
                        color = colors.onBackground
                    )
                }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Mini Auditorium",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Expo Stage",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "MCH",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Behind Graffiti Wall",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Football Field",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Basketball Courts",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }

                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Voley Ball Court",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }

                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Library Shed",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Lecture Hall 1",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Lecture Hall 2",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Lecture Hall 3",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Lecture Hall 4",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Rocko Stage",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Pronite Stage",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Conference Hall 1",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Conference Hall 2",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Conference Hall 3",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Conference Hall 4",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Core 1",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Senate Hall",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "OLD SAC WALL",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "AUDI PARK",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Near Lake \n( In front of Audi )",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                    Box(modifier = Modifier
                        .width(200.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp))
                        .background(headerbgcolor), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Entire Campus",
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp,
                            fontFamily = star_guard,
                            style = TextStyle(letterSpacing = 1.sp),
                            color = colors.onBackground
                        )
                    }
                }
            }

//            Row(
//                Modifier
//                    .width(1245.dp)
//                    .horizontalScroll(horiscrollstate)
//
//            ) {
//                Spacer(modifier = Modifier.width(70.dp))
//                Row(
//                    Modifier
//                        .width(2025.dp)
//                    , horizontalArrangement = Arrangement.SpaceEvenly
//                ) { Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
//                    Text(
//                        text = "Offline",
//                        fontWeight = FontWeight.W600,
//                        fontSize = 14.sp,
//                        fontFamily = hk_grotesk,
//                        color = Color(0xffA3A7AC)
//                    )
//                }
//
//                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
//                        Text(
//                            text = "Offline",
//                            fontWeight = FontWeight.W600,
//                            fontSize = 14.sp,
//                            fontFamily = hk_grotesk,
//                            color = Color(0xffA3A7AC)
//                        )
//                    }
//                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
//                        Text(
//                            text = "Offline",
//                            fontWeight = FontWeight.W600,
//                            fontSize = 14.sp,
//                            fontFamily = hk_grotesk,
//                            color = Color(0xffA3A7AC)
//                        )
//                    }
//                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
//                        Text(
//                            text = "PayTM Insider",
//                            fontWeight = FontWeight.W600,
//                            fontSize = 14.sp,
//                            fontFamily = hk_grotesk,
//                            color = Color(0xffA3A7AC)
//                        )
//                    }
//                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
//                        Text(
//                            text = "PayTM Insider",
//                            fontWeight = FontWeight.W600,
//                            fontSize = 14.sp,
//                            fontFamily = hk_grotesk,
//                            color = Color(0xffA3A7AC)
//                        )
//                    }
//                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
//                        Text(
//                            text = "PayTM Insider",
//                            fontWeight = FontWeight.W600,
//                            fontSize = 14.sp,
//                            fontFamily = hk_grotesk,
//                            color = Color(0xffA3A7AC)
//                        )
//                    }
//                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
//                        Text(
//                            text = "PayTM Insider",
//                            fontWeight = FontWeight.W600,
//                            fontSize = 14.sp,
//                            fontFamily = hk_grotesk,
//                            color = Color(0xffA3A7AC)
//                        )
//                    }
//
//                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
//                        Text(
//                            text = "PayTM Insider",
//                            fontWeight = FontWeight.W600,
//                            fontSize = 14.sp,
//                            fontFamily = hk_grotesk,
//                            color = Color(0xffA3A7AC)
//                        )
//                    }
//                }
//            }




//            Spacer(modifier = Modifier.height(16.dp))
//            Log.d("XValue","hello! ${vert.value}")
            val verticalscrollstate=rememberScrollState()
            Row(
                Modifier
                    .width(1095.dp)
//                    .width(3000.dp)
                    .height(975.dp)
                    .verticalScroll(verticalscrollstate)
                    .background(color = bgcolor)
                    ){
                timecolummn(verticalscrollstate)

                Row(
                    Modifier
                        .wrapContentSize()
                        .horizontalScroll(horiscrollstate)) {
                    fullscheduleBox()}
            }
        }
    }

    @Composable
    fun timecolummn(state: ScrollState) {
        val headerbgcolor=if(isSystemInDarkTheme())Color(0xff1C1C1C)else Color(0xffFAFBF5)
        val colors=colors
        var columnHeightpx by remember {
            mutableStateOf(0f)
        }
        val localdensity=LocalDensity.current
        val currentdisplayheight= with(localdensity){LocalConfiguration.current.screenHeightDp.dp.toPx()}


        Column(Modifier.width(54.dp).onGloballyPositioned { coordinates ->

        }) {

            Canvas(
                modifier = Modifier
                    .width(16.dp)
            ) {
                drawLine(
                    color = headerbgcolor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, 975.dp.toPx()),
                    strokeWidth = 16.dp.toPx()
                )

                drawCircle(color=headerbgcolor, radius = 8.dp.toPx(), center = Offset(this.center.x-6.dp.toPx(),this.center.y+6.dp.toPx()))



//                drawCircle(color=colors.onBackground, radius = 6.dp.toPx(), center = Offset(this.center.x-8.dp.toPx(),( state.value+ 6.dp.toPx() +((state.value * currentdisplayheight)/2600f))))
            }

            Column(
                Modifier
                    .width(70.dp)
                    .padding(start = 0.dp)
                    .height(975.dp), verticalArrangement = Arrangement.SpaceEvenly
            ) {
                for (time in 9..11) {
                    Row(
                        Modifier
                            .height(65.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier
                                .width(55.dp)
                                .height(34.dp),
                            shape = AbsoluteRoundedCornerShape(topLeft = 0.dp, topRight = 50.dp, bottomLeft = 0.dp, bottomRight = 50.dp)
                        ) {
                            Box(
                                modifier = Modifier.background(headerbgcolor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$time AM",
                                    style = TextStyle(
//                                color = colorResource(id = R.color.textGray),
                                        color = colors.onBackground,
                                        fontFamily = star_guard,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }
                    }
                }

                Row(
                    Modifier
                        .height(65.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .width(55.dp)
                            .height(34.dp),
                        shape = AbsoluteRoundedCornerShape(topLeft = 0.dp, topRight = 50.dp, bottomLeft = 0.dp, bottomRight = 50.dp)
                    ) {
                        Box(
                            modifier = Modifier.background(headerbgcolor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "12 PM",
                                style = TextStyle(
//                        color = colorResource(id = R.color.textGray),
                                    color = colors.onBackground,
                                    fontFamily = star_guard,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                }

                for (time in 1..11) {
                    Row(
                        Modifier
                            .height(65.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier
                                .width(55.dp)
                                .height(34.dp),
                            shape = AbsoluteRoundedCornerShape(topLeft = 0.dp, topRight = 50.dp, bottomLeft = 0.dp, bottomRight = 50.dp)
                        ) {
                            Box(
                                modifier = Modifier.background(headerbgcolor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$time PM",
                                    style = TextStyle(
//                            color = colorResource(id = R.color.textGray),\
                                        color = colors.onBackground,
                                        fontFamily = star_guard,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

        }
        Canvas(
            modifier = Modifier
                .width(16.dp)
        ) {
            drawCircle(
                color = colors.onBackground,
                radius = 6.dp.toPx(),
                center = Offset(
                    this.center.x - 61.dp.toPx(),
                    (state.value + 6.dp.toPx() + ((state.value *(currentdisplayheight-250.dp.toPx())/(900.dp.toPx()-(currentdisplayheight-250.dp.toPx()))) ))
                )
            )
        }
    }

    @Composable
    fun fullscheduleBox() {

        Box(
            Modifier
                .offset(-35.dp)
//                .width(2025.dp)
//                .width(1700.dp)
                .width(5218.dp)
                .height(975.dp)
//                .horizontalScroll(rememberScrollState())
               ) {

                   Column(
                       Modifier
                           .fillMaxWidth()
                           .height(975.dp), verticalArrangement = Arrangement.SpaceEvenly
                   ) {
                       for (time in 9..11) {
                           Row(
                               Modifier
                                   .height(65.dp)
                                   .fillMaxWidth(),
                               verticalAlignment = Alignment.CenterVertically
                           ) {

                               Canvas(
                                   modifier = Modifier
                                       .height(5.dp)
                                       .fillMaxWidth()

                               ) {
                                   drawLine(
                                       color = Color(0xff000000),
                                       start = Offset(100f, 0f),
                                       end = Offset(size.width, 0f),
                                       strokeWidth = 1.dp.toPx()
                                   )
                               }

                           }

                       }

                       Row(
                           Modifier
                               .height(65.dp)
                               .fillMaxWidth(),
                           verticalAlignment = Alignment.CenterVertically
                       ) {


                           Canvas(
                               modifier = Modifier
                                   .height(5.dp)
                                   .fillMaxWidth()

                           ) {
                               drawLine(
                                   color = Color(0xff000000),
                                   start = Offset(100f, 0f),
                                   end = Offset(size.width, 0f),
                                   strokeWidth = 1.dp.toPx()
                               )
                           }

                       }

                       for (time in 1..11) {
                           Row(
                               Modifier
                                   .height(65.dp)
                                   .fillMaxWidth(),
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Canvas(
                                   modifier = Modifier
                                       .height(5.dp)
                                       .fillMaxWidth()

                               ) {
                                   drawLine(
                                       color = Color(0xff000000),
//                                       color = Color.White,
                                       start = Offset(100f, 0f),
                                       end = Offset(size.width-6, 0f),
                                       strokeWidth = 1.dp.toPx()
                                   )
                               }
                           }
                       }
                   }
            val coloroverlay=if(isSystemInDarkTheme())Color(0x50000000)else Color(0x50ffffff)
            Box(modifier = Modifier
                .offset(x = 36.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 242.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 448.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 654.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 860.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 1066.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 1272.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 1478.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 1684.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 1890.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 2096.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 2302.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 2508.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 2714.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 2920.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 3126.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 3332.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 3538.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 3744.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 3950.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 4156.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 4362.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 4568.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 4774.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))
            Box(modifier = Modifier
                .offset(x = 4980.dp)
                .fillMaxHeight()
                .width(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(coloroverlay))


            if (datestate.value == 1) {
                datestate1.forEach { data -> fullSchUserBox(eventdetail = data) }
            }
            if (datestate.value == 2) {
                datestate2.forEach { data -> fullSchUserBox(eventdetail = data) }
            }
            if (datestate.value == 3) {
                datestate3.forEach { data -> fullSchUserBox(eventdetail = data) }
            }
           }

    }



    @Composable
    fun fullSchUserBox(
        eventdetail: eventWithLive
    ) {
//        val coroutineScope = rememberCoroutineScope()
//        val color = remember {
//            mutableStateOf(
//                listOf(
//                    Color(0xffC80915),
//                    Color(0xff1E248D),
//                    Color(0xffEE6337)
//                ).random()
//            )
//        }
        var catid= remember { mutableStateOf(6) }
            when(eventdetail.eventdetail.venue.replace("\\s".toRegex(), "").uppercase()){

                "IITG Auditorium".replace("\\s".toRegex(), "").uppercase()-> catid.value=0
                "Pronites Ground".replace("\\s".toRegex(), "").uppercase()->catid.value=1
                "NEU Stage".replace("\\s".toRegex(), "").uppercase()-> catid.value=2
                "Auditorium 1".replace("\\s".toRegex(), "").uppercase()-> catid.value=3
                "Auditorium 2".replace("\\s".toRegex(), "").uppercase()->catid.value=4
                "Creators' Camp".replace("\\s".toRegex(), "").uppercase()-> catid.value=5
                "Gaming".replace("\\s".toRegex(), "").uppercase()-> catid.value=6
                "Campaign".replace("\\s".toRegex(), "").uppercase()-> catid.value=7

            }

        var lengthdp =
            remember { Animatable(eventdetail.eventdetail.durationInMin.toFloat() * (13f / 12f)) }
        val xdis =
            (((eventdetail.eventdetail.starttime.hours - 9) * 65).toFloat() + (eventdetail.eventdetail.starttime.min.toFloat() * (13f / 12f)) + 32f)
        val ydis = (35 + (206 *catid.value))
        val xdisinpxcald = with(LocalDensity.current) { (xdis - 2).dp.toPx() }
        val ydisinpxcald = with(LocalDensity.current) { (ydis).dp.toPx() }
        var offsetX = remember { Animatable(ydisinpxcald) }
        var offsetY = remember { Animatable(xdisinpxcald) }

        Box(modifier=
        Modifier
            .offset {
                IntOffset(
                    offsetX.value
                        .toInt(),
                    offsetY.value
                        .toInt()
                )
            }
            .height(lengthdp.value.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(
                BorderStroke(0.75.dp, if (isSystemInDarkTheme()) midWhite else black),
                RoundedCornerShape(4.dp)
            )
//                .background(color.value)
            .background(if (isSystemInDarkTheme()) Color(0xff1a1a1a) else Color.White)
            .clickable {

                val frg = Events_Details_Fragment()
                val arguments = bundleOf("Artist" to eventdetail.eventdetail.artist)
                findNavController(this@Schedule).navigate(
                    R.id.action_schedule_to_events_Details_Fragment,
                    arguments
                );
//                    fm
//                        .beginTransaction()
//                        .replace(R.id.fragmentContainerView, frg)
//                        .addToBackStack(null)
//                        .commit()

            }
            .pointerInput(Unit) {
            }


        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp)), horizontalArrangement = Arrangement.SpaceBetween) {

                Canvas(
                    modifier = Modifier
                        .width(8.dp)
                ) {
                    drawLine(
                        color = Color(0xffF5C771),
                        start = Offset(0f, 0f),
                        end = Offset(0f, lengthdp.value.dp.toPx()),
                        strokeWidth = 8.dp.toPx()
                    )
                }





                Column(
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .fillMaxSize()
                        .padding(top = 10.dp, start = 12.dp, end = 15.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = eventdetail.eventdetail.artist,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = aileron,
                    fontSize = 16.sp,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = eventdetail.eventdetail.category,
                    style = TextStyle(
//                        color = colorResource(id = R.color.textGray),
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        fontFamily = aileron,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                )
            }
        }
        }


    }

    override fun onResume() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.menu?.findItem(R.id.schedule)?.setChecked(true);
        MainActivity.index=R.id.schedule;
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






