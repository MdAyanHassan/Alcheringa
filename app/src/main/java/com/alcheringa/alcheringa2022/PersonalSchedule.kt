package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.alcheringa.alcheringa2022.MainActivity.index
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.eventdetail
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

class PersonalSchedule : Fragment() {
    private lateinit var binding: ScheduleFragmentBinding
    private lateinit var fm: FragmentManager
    private val homeviewmodel:viewModelHome by activityViewModels()
    private val datestate1 = mutableStateListOf<eventWithLive>()
    private val datestate2 = mutableStateListOf<eventWithLive>()
    private val datestate3 = mutableStateListOf<eventWithLive>()
    private var datestate = mutableStateOf<Int>(1)
    private var timing = mutableStateOf<Int>(0)
    private var spacing = mutableStateOf<Int>(0)

    var schedule=false;

    var firebaseFirestore: FirebaseFirestore? = null
    var sharedPreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fm = parentFragmentManager

        CoroutineScope(Dispatchers.Main).launch {
            homeviewmodel.allEventsWithLivedata.observe(requireActivity()){data->
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
            startActivity(Intent(context, Account::class.java));
        }
        binding.pass.setOnClickListener{
            startActivity(Intent(context, NotificationActivity::class.java));
        }
        binding.scheduleCompose.setContent {
            Alcheringa2022Theme() {


            mySchedule()}
        }
    }


    @Composable
    fun mySchedule() {
        val colors= colors
        var color1 by remember { mutableStateOf(colors.onBackground) }
        var color2 by remember { mutableStateOf(colors.background) }
        var color3 by remember { mutableStateOf(colors.background) }
        var color4 by remember { mutableStateOf(colors.background) }

        var color11 by remember { mutableStateOf(colors.background) }
        var color22 by remember { mutableStateOf(colors.onBackground) }
        var color33 by remember { mutableStateOf(colors.onBackground) }
        var color44 by remember { mutableStateOf(colors.onBackground) }

        Column() {

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(colorResource(id = R.color.Black)), contentAlignment = Alignment.Center)
            {
                Box(
                    modifier = Modifier
                        .width(410.dp)
                        .height(36.dp)
                        .background(colors.background),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
//                    Modifier
//                        .fillMaxWidth(),
//                        .padding(horizontal = 20.dp)
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            Modifier
//                            .fillMaxWidth(0.25f)
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(32.dp),
                                shape = RoundedCornerShape(4.dp)
                            ) {

                                Box(
                                    modifier = Modifier.background(color = color1),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Day 0",
                                        fontWeight = FontWeight.W400,
                                        fontFamily = star_guard,
                                        color = color11,
//                            color = color1,
//                                color = colorResource(id = R.color.Grey_bg),
                                        fontSize = 18.sp,
                                        modifier = Modifier.clickable {
                                            color1 = colors.onBackground;color2 = colors.background; color3 =
                                            colors.background;color4 = colors.background
                                            datestate.value = 1;
                                            color11 = colors.background;color22 = colors.onBackground;color33 =
                                            colors.onBackground;color44 = colors.onBackground
                                        })
                                }
                            }
                        }
                        Column(
                            Modifier
//                            .fillMaxWidth(0.33f)
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(32.dp),
                                shape = RoundedCornerShape(4.dp)
                            ) {

                                Box(
                                    modifier = Modifier.background(color = color2),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Day 1",
                                        fontWeight = FontWeight.W400,
                                        fontFamily = star_guard,
                                        color = color22,
//                            color = color1,
//                                color = colorResource(id = R.color.Grey_bg),
                                        fontSize = 18.sp,
                                        modifier = Modifier.clickable {
                                            color1 = colors.background;color2 = colors.onBackground;color3 =
                                            colors.background;color4 = colors.background
                                            datestate.value = 2;
                                            color22 = colors.background;color11 = colors.onBackground;color33 =
                                            colors.onBackground;color44 = colors.onBackground
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
//                            .fillMaxWidth(0.5f)
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(32.dp),
                                shape = RoundedCornerShape(4.dp)
                            ) {

                                Box(
                                    modifier = Modifier.background(color = color3),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Day 2",
                                        fontWeight = FontWeight.W400,
                                        fontFamily = star_guard,
                                        color = color33,
//                            color = color1,
//                                color = colorResource(id = R.color.Grey_bg),
                                        fontSize = 18.sp,
                                        modifier = Modifier.clickable {
                                            color1 = colors.background;color2 = colors.background;color3 =
                                            colors.onBackground;color4 = colors.background
                                            datestate.value = 3;
                                            color33 = colors.background;color22 = colors.onBackground;color11 =
                                            colors.onBackground;color44 = colors.onBackground
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
//                            .fillMaxWidth(1f)
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(32.dp),
                                shape = RoundedCornerShape(4.dp)
                            ) {

                                Box(
                                    modifier = Modifier.background(color = color4),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Day 3",
                                        fontWeight = FontWeight.W400,
                                        fontFamily = star_guard,
                                        color = color44,
//                            color = color1,
//                                color = colorResource(id = R.color.Grey_bg),
                                        fontSize = 18.sp,
                                        modifier = Modifier.clickable {
                                            color1 = colors.background;color2 = colors.background;color3 =
                                            colors.background;color4 = colors.onBackground
                                            datestate.value = 1;
                                            color44 = colors.background;color22 = colors.onBackground;color33 =
                                            colors.onBackground;color11 = colors.onBackground
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
            }

//            Spacer(modifier = Modifier.height(6.dp))
//            val horiscrollstate = rememberScrollState()
//            Row(
//                Modifier
////                    .width(500.dp)
//                    .horizontalScroll(horiscrollstate)
//                    .background(color = colorResource(id = R.color.Grey_bg))
//            ) {
//                Spacer(modifier = Modifier.width(100.dp))
//                Row(
//                    Modifier
////                        .width(2025.dp)
//                        .width(1656.dp)
//                    , horizontalArrangement = Arrangement.SpaceEvenly
//                ) { Box(modifier = Modifier.width(200.dp).height(54.dp).background(colorResource(id = R.color.MidWhite)).clip(RoundedCornerShape(20.dp)), contentAlignment = Alignment.Center) {
//                    Text(
//                        text = "IITG Auditorium",
//                        fontWeight = FontWeight.W400,
//                        fontSize = 20.sp,
//                        fontFamily = star_guard,
//                        color = Color.Black
//                    )
//                }
//                    Box(modifier = Modifier.width(200.dp).height(54.dp).background(colorResource(id = R.color.Shiny_White)), contentAlignment = Alignment.Center) {
//                        Text(
//                            text = "Pronites Ground",
//                            fontWeight = FontWeight.W400,
//                            fontSize = 20.sp,
//                            fontFamily = star_guard,
//                            color = Color.Black
//                        )
//                    }
//                    Box(modifier = Modifier.width(200.dp).height(54.dp).background(colorResource(id = R.color.Shiny_White)), contentAlignment = Alignment.Center) {
//                        Text(
//                            text = "NEU Stage",
//                            fontWeight = FontWeight.W400,
//                            fontSize = 20.sp,
//                            fontFamily = star_guard,
//                            color = Color.Black
//                        )
//                    }
//                    Box(modifier = Modifier.width(200.dp).height(54.dp).background(colorResource(id = R.color.Shiny_White)), contentAlignment = Alignment.Center) {
//                        Text(
//                            text = "Auditorium 1",
//                            fontWeight = FontWeight.W400,
//                            fontSize = 20.sp,
//                            fontFamily = star_guard,
//                            color = Color.Black
//                        )
//                    }
//                    Box(modifier = Modifier.width(200.dp).height(54.dp).background(colorResource(id = R.color.Shiny_White)), contentAlignment = Alignment.Center) {
//                        Text(
//                            text = "Auditorium 2",
//                            fontWeight = FontWeight.W400,
//                            fontSize = 20.sp,
//                            fontFamily = star_guard,
//                            color = Color.Black
//                        )
//                    }
//                    Box(modifier = Modifier.width(200.dp).height(54.dp).background(colorResource(id = R.color.Shiny_White)), contentAlignment = Alignment.Center) {
//                        Text(
//                            text = "Creators' Camp",
//                            fontWeight = FontWeight.W400,
//                            fontSize = 20.sp,
//                            fontFamily = star_guard,
//                            color = Color.Black
//                        )
//                    }
//                    Box(modifier = Modifier.width(200.dp).height(54.dp).background(colorResource(id = R.color.Shiny_White)), contentAlignment = Alignment.Center) {
//                        Text(
//                            text = "Gaming",
//                            fontWeight = FontWeight.W400,
//                            fontSize = 20.sp,
//                            fontFamily = star_guard,
//                            color = Color.Black
//                        )
//                    }
//
//                    Box(modifier = Modifier.width(200.dp).height(54.dp).background(colorResource(id = R.color.Shiny_White)), contentAlignment = Alignment.Center) {
//                        Text(
//                            text = "Campaign",
//                            fontWeight = FontWeight.W400,
//                            fontSize = 20.sp,
//                            fontFamily = star_guard,
//                            color = Color.Black
//                        )
//                    }
//                }
//            }

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

            val verticalscrollstate = rememberScrollState()

            Box(
                Modifier
                    .width(428.dp)
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
                    .background(color = colorResource(id = R.color.MidWhite))
            ){
                //                Spacer(modifier = Modifier.width(100.dp))
                Column(
                    Modifier
                        .wrapContentHeight()

                ) {
                    Spacer(modifier = Modifier.height(60.dp))
                    var time = 9

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 9) {
                                timing.value = 0
                            } else {
                                timing.value = 9
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 9){
                            display()}
                    }
                    }
                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 10) {
                                timing.value = 0
                            } else {
                                timing.value = 10
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 10){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 11) {
                                timing.value = 0
                            } else {
                                timing.value = 11
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)), contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 11){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 12) {
                                timing.value = 0
                            } else {
                                timing.value = 12
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 12){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 13) {
                                timing.value = 0
                            } else {
                                timing.value = 13
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 13){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 14) {
                                timing.value = 0
                            } else {
                                timing.value = 14
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 14){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 15) {
                                timing.value = 0
                            } else {
                                timing.value = 15
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 15){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 16) {
                                timing.value = 0
                            } else {
                                timing.value = 16
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 16){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 17) {
                                timing.value = 0
                            } else {
                                timing.value = 17
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 17){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 18) {
                                timing.value = 0
                            } else {
                                timing.value = 18
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 18){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 19) {
                                timing.value = 0
                            } else {
                                timing.value = 19
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 19){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 20) {
                                timing.value = 0
                            } else {
                                timing.value = 20
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 20){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 21) {
                                timing.value = 0
                            } else {
                                timing.value = 21
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 21){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 22) {
                                timing.value = 0
                            } else {
                                timing.value = 22
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 22){
                            display()}
                    }
                    }

                    time += 1
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(id = R.color.MidWhite))
                        .clickable {
                            if (timing.value == 23) {
                                timing.value = 0
                            } else {
                                timing.value = 23
                            }
                        }
                        , contentAlignment = Alignment.CenterEnd
                    ) {Column(modifier = Modifier.wrapContentHeight()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(colorResource(id = R.color.MidWhite)),contentAlignment = Alignment.CenterEnd) {
                            Text(
                                text = "Click here to view events between $time:00 and ${time + 1}:00",
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                fontFamily = aileron,
                                color = colorResource(id = R.color.MidWhite)
                            )
                        }
                        if (timing.value == 23){
                            display()}
                    }
                    }
                }
//                Column(modifier = Modifier.height(1800.dp)) {
//                    for (time in 9..23) {
//                        Row(
//                            Modifier
//                                .height(120.dp)
//                                .fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Canvas(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//
//                            ) {
//                                drawLine(
//                                    color = Color(0x70000000),
//                                    start = Offset(80f, 0f),
//                                    end = Offset(size.width, 0f),
//                                    strokeWidth = 2.dp.toPx()
//                                )
//                            }
//                        }
//                    }
//                }
                timecolummn()
            }
        }
    }



    @Composable()
    fun display(){

        var color = remember {
            mutableStateOf(
                listOf(
                    Color(0xffF2A23C),
                    Color(0xff55D1E9),
                    Color(0xffE57563),
                    Color(0xffBFCF68),
                ).random()
            )
        }

        spacing.value = 0
        if ((datestate.value == 1) and (timing.value>8)) {
            Box(modifier = Modifier
                .width(428.dp)
                .wrapContentHeight()
                .background(color = colorResource(id = R.color.MidWhite))){
                Column() {
                    var index = 0
                    var arr = listOf<eventWithLive>(datestate1[0])
                    for (eventdetail in datestate1){
                        if ((eventdetail.eventdetail.starttime.hours >= timing.value) and (eventdetail.eventdetail.starttime.hours < (timing.value+1))){
                            spacing.value += 77
                            arr +=eventdetail
                            var color = remember {
                                mutableStateOf(
                                    listOf(
                                        Color(0xffF2A23C),
                                        Color(0xff55D1E9),
                                        Color(0xffE57563),
                                        Color(0xffBFCF68),
                                    ).random()
                                )
                            }
                            var x = arr.indexOf(eventdetail)
                            Canvas(
                                modifier = Modifier
                                    .wrapContentWidth()
                            ) {
                                drawLine(
                                    color = color.value,
                                    start = Offset((10+12*x+1).dp.toPx(), 100f),
                                    end = Offset(300.dp.toPx(), 100f),
                                    strokeWidth = 8.dp.toPx()
                                )
                                drawLine(
                                    color = color.value,
                                    start = Offset((10+12*x+1).dp.toPx(), 0-50*((x+1)%2).dp.toPx()),
                                    end = Offset((10+12*x+1).dp.toPx(), 500-10*((x+1)%2).dp.toPx()),
                                    strokeWidth = 8.dp.toPx()
                                )
                                drawCircle(
                                    color = color.value,
                                    radius = 4.dp.toPx(),
                                    center = Offset((10+12*x+1).dp.toPx(), 0-50*((x+1)%2).dp.toPx())
                                )
                                drawCircle(
                                    color = color.value,
                                    radius = 4.dp.toPx(),
                                    center = Offset((10+12*x+1).dp.toPx(), 500-10*((x+1)%2).dp.toPx())
                                )
                            }
                            Row(
                                Modifier
                                    .height(64.dp)
                                    .fillMaxWidth()
                                    .padding(start = 100.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),){
                                Box(
                                    modifier = Modifier
                                        .width(224.dp)
                                        .wrapContentHeight()
                                        .border(2.dp, Color.Black)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .width(256.dp)
                                            .height(64.dp)
                                            .background(color = colorResource(id = R.color.MidWhite))
                                            .padding(14.dp, 0.dp), verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = eventdetail.eventdetail.artist,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontFamily = aileron,
                                                fontWeight = FontWeight.W700,
                                                fontSize = 14.sp
                                            )
                                        )
                                        Text(
                                            text = eventdetail.eventdetail.category,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontFamily = aileron,
                                                fontWeight = FontWeight.W400,
                                                fontSize = 12.sp
                                            )
                                        )
                                        Row() {
                                            Text(
                                                text = eventdetail.eventdetail.venue,
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontFamily = aileron,
                                                    fontWeight = FontWeight.W700,
                                                    fontSize = 12.sp
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(9.dp))
                                            Text(
                                                text = eventdetail.eventdetail.starttime.hours.toString() + ":00",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontFamily = aileron,
                                                    fontWeight = FontWeight.W700,
                                                    fontSize = 12.sp
                                                )
                                            )
                                        }
                                    }
                                }
                                Box(
                                    Modifier
                                        .width(50.dp)
                                        .height(64.dp)
                                        .background(Color.Black), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "+",
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 30.sp
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            index += 1
                        }
                    }
                    if (index == 0){
                        Box(
                            Modifier
                                .wrapContentWidth()
                                .padding(50.dp, 10.dp)){
                            Box(
                                modifier = Modifier
                                    .width(306.dp)
                                    .height(64.dp)
                                    .border(2.dp, Color.Black)
                                    .padding(5.dp, 5.dp), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Take a break! No events in this inverval!",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                        spacing.value += 90
                    }
                }
            }
        }
        if ((datestate.value == 2) and (timing.value>8)){
            Box(modifier = Modifier
                .width(428.dp)
                .wrapContentHeight()
                .background(color = colorResource(id = R.color.MidWhite))){
                Column() {
                    var index = 0
                    var arr = listOf<eventWithLive>(datestate2[0])
                    for (eventdetail in datestate2){
                        if ((eventdetail.eventdetail.starttime.hours >= timing.value) and (eventdetail.eventdetail.starttime.hours < (timing.value+1))){
                            spacing.value += 77
                            arr +=eventdetail
                            var color = remember {
                                mutableStateOf(
                                    listOf(
                                        Color(0xffF2A23C),
                                        Color(0xff55D1E9),
                                        Color(0xffE57563),
                                        Color(0xffBFCF68),
                                    ).random()
                                )
                            }
                            var x = arr.indexOf(eventdetail)
                            Canvas(
                                modifier = Modifier
                                    .wrapContentWidth()
                            ) {
                                drawLine(
                                    color = color.value,
                                    start = Offset((10+12*x+1).dp.toPx(), 100f),
                                    end = Offset(300.dp.toPx(), 100f),
                                    strokeWidth = 8.dp.toPx()
                                )
                                drawLine(
                                    color = color.value,
                                    start = Offset((10+12*x+1).dp.toPx(), 0-50*((x+1)%2).dp.toPx()),
                                    end = Offset((10+12*x+1).dp.toPx(), 500-10*((x+1)%2).dp.toPx()),
                                    strokeWidth = 8.dp.toPx()
                                )
                                drawCircle(
                                    color = color.value,
                                    radius = 4.dp.toPx(),
                                    center = Offset((10+12*x+1).dp.toPx(), 0-50*((x+1)%2).dp.toPx())
                                )
                                drawCircle(
                                    color = color.value,
                                    radius = 4.dp.toPx(),
                                    center = Offset((10+12*x+1).dp.toPx(), 500-10*((x+1)%2).dp.toPx())
                                )
                            }
                            Row(
                                Modifier
                                    .height(64.dp)
                                    .fillMaxWidth()
                                    .padding(start = 100.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),){
                                Box(
                                    modifier = Modifier
                                        .width(224.dp)
                                        .wrapContentHeight()
                                        .border(2.dp, Color.Black)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .width(256.dp)
                                            .height(64.dp)
                                            .background(color = colorResource(id = R.color.MidWhite))
                                            .padding(14.dp, 0.dp), verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = eventdetail.eventdetail.artist,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontFamily = aileron,
                                                fontWeight = FontWeight.W700,
                                                fontSize = 14.sp
                                            )
                                        )
                                        Text(
                                            text = eventdetail.eventdetail.category,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontFamily = aileron,
                                                fontWeight = FontWeight.W400,
                                                fontSize = 12.sp
                                            )
                                        )
                                        Row() {
                                            Text(
                                                text = eventdetail.eventdetail.venue,
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontFamily = aileron,
                                                    fontWeight = FontWeight.W700,
                                                    fontSize = 12.sp
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(9.dp))
                                            Text(
                                                text = eventdetail.eventdetail.starttime.hours.toString() + ":00",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontFamily = aileron,
                                                    fontWeight = FontWeight.W700,
                                                    fontSize = 12.sp
                                                )
                                            )
                                        }
                                    }
                                }
                                Box(
                                    Modifier
                                        .width(50.dp)
                                        .height(64.dp)
                                        .background(Color.Black), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "+",
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 30.sp
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            index += 1
                        }
                    }
                    if (index == 0){
                        Box(
                            Modifier
                                .wrapContentWidth()
                                .padding(50.dp, 10.dp)){
                            Box(
                                modifier = Modifier
                                    .width(306.dp)
                                    .height(64.dp)
                                    .border(2.dp, Color.Black)
                                    .padding(5.dp, 5.dp), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Take a break! No events in this inverval!",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                        spacing.value += 90
                    }
                }
            }
        }
        if ((datestate.value == 3) and (timing.value>8)){
            Box(modifier = Modifier
                .width(428.dp)
                .wrapContentHeight()
                .background(color = colorResource(id = R.color.MidWhite))){
                Column() {
                    var index = 0
                    var arr = listOf<eventWithLive>(datestate3[0])
                    for (eventdetail in datestate3){
                        if ((eventdetail.eventdetail.starttime.hours >= timing.value) and (eventdetail.eventdetail.starttime.hours < (timing.value+1))){
                            spacing.value += 77
                            arr +=eventdetail
                            var color = remember {
                                mutableStateOf(
                                    listOf(
                                        Color(0xffF2A23C),
                                        Color(0xff55D1E9),
                                        Color(0xffE57563),
                                        Color(0xffBFCF68),
                                    ).random()
                                )
                            }
                            var x = arr.indexOf(eventdetail)
                            Canvas(
                                modifier = Modifier
                                    .wrapContentWidth()
                            ) {
                                drawLine(
                                    color = color.value,
                                    start = Offset((10+12*x+1).dp.toPx(), 100f),
                                    end = Offset(300.dp.toPx(), 100f),
                                    strokeWidth = 8.dp.toPx()
                                )
                                drawLine(
                                    color = color.value,
                                    start = Offset((10+12*x+1).dp.toPx(), 0-50*((x+1)%2).dp.toPx()),
                                    end = Offset((10+12*x+1).dp.toPx(), 500-10*((x+1)%2).dp.toPx()),
                                    strokeWidth = 8.dp.toPx()
                                )
                                drawCircle(
                                    color = color.value,
                                    radius = 4.dp.toPx(),
                                    center = Offset((10+12*x+1).dp.toPx(), 0-50*((x+1)%2).dp.toPx())
                                )
                                drawCircle(
                                    color = color.value,
                                    radius = 4.dp.toPx(),
                                    center = Offset((10+12*x+1).dp.toPx(), 500-10*((x+1)%2).dp.toPx())
                                )
                            }
                            Row(
                                Modifier
                                    .height(64.dp)
                                    .fillMaxWidth()
                                    .padding(start = 100.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),){
                                Box(
                                    modifier = Modifier
                                        .width(224.dp)
                                        .wrapContentHeight()
                                        .border(2.dp, Color.Black)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .width(256.dp)
                                            .height(64.dp)
                                            .background(color = colorResource(id = R.color.MidWhite))
                                            .padding(14.dp, 0.dp), verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = eventdetail.eventdetail.artist,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontFamily = aileron,
                                                fontWeight = FontWeight.W700,
                                                fontSize = 14.sp
                                            )
                                        )
                                        Text(
                                            text = eventdetail.eventdetail.category,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontFamily = aileron,
                                                fontWeight = FontWeight.W400,
                                                fontSize = 12.sp
                                            )
                                        )
                                        Row() {
                                            Text(
                                                text = eventdetail.eventdetail.venue,
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontFamily = aileron,
                                                    fontWeight = FontWeight.W700,
                                                    fontSize = 12.sp
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(9.dp))
                                            Text(
                                                text = eventdetail.eventdetail.starttime.hours.toString() + ":00",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontFamily = aileron,
                                                    fontWeight = FontWeight.W700,
                                                    fontSize = 12.sp
                                                )
                                            )
                                        }
                                    }
                                }
                                Box(
                                    Modifier
                                        .width(50.dp)
                                        .height(64.dp)
                                        .background(Color.Black), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "+",
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 30.sp
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            index += 1
                        }
                    }
                    if (index == 0){
                        Box(
                            Modifier
                                .wrapContentWidth()
                                .padding(50.dp, 10.dp)){
                            Box(
                                modifier = Modifier
                                    .width(306.dp)
                                    .height(64.dp)
                                    .border(2.dp, Color.Black)
                                    .padding(5.dp, 5.dp), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Take a break! No events in this inverval!",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                        spacing.value += 90
                    }
                }
            }
        }
    }


    @Composable
    fun timecolummn() {

        Column(
            Modifier
                .wrapContentHeight()
                .wrapContentHeight()) {
            for (time in 9..23) {
                Row(
                    Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Canvas(
                        modifier = Modifier
                            .height(5.dp)
                            .fillMaxWidth()

                    ) {
                        drawLine(
                            color = Color(0x70000000),
                            start = Offset(80f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                }
                if (timing.value == time) {
                    Spacer(modifier = Modifier.height(spacing.value.dp))
                }
            }
        }

        Column(
            Modifier
                .width(72.dp)
//                    .padding(start = 5.dp)
                .wrapContentHeight(),
        )
        {
            for (time in 9..23) {
                var color = remember {
                    mutableStateOf(
                        listOf(
                            Color(0xff489AB8),
                            Color(0xff7EC4C4),
                            Color(0xff8FCCA9),
                            Color(0xffBFCF68),
                            Color(0xffE2CB6A),
                            Color(0xffACACAC),
                        ).random()
                    )
                }
                Row(
                    Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Column(modifier = Modifier.wrapContentHeight()) {
                        Card(
                            modifier = Modifier
                                .width(72.dp)
                                .height(36.dp)
                                .border(
                                    2.dp,
                                    color = color.value,
                                    shape = AbsoluteRoundedCornerShape(0.dp, 50.dp, 50.dp, 0.dp)
                                ), shape = AbsoluteRoundedCornerShape(0.dp, 50.dp, 50.dp, 0.dp)
                        ) {
                            Box(
                                modifier = Modifier.background(colorResource(id = R.color.MidWhite)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$time:00",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontFamily = star_guard,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                    }
                }
                if (timing.value == time){
                    Spacer(modifier = Modifier.height(spacing.value.dp))}
            }
        }
    }

    @Composable
    fun fullscheduleBox() {

//        Box(
//            Modifier
////                .width(2025.dp)
//                .width(1700.dp)
//                .height(975.dp)
////                .horizontalScroll(rememberScrollState())
//        ) {
//
//            Column(
//                Modifier
//                    .fillMaxWidth()
//                    .height(975.dp), verticalArrangement = Arrangement.SpaceEvenly
//            ) {
//                for (time in 9..11) {
//                    Row(
//                        Modifier
//                            .height(65.dp)
//                            .fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Canvas(
//                            modifier = Modifier
//                                .height(5.dp)
//                                .fillMaxWidth()
//
//                        ) {
//                            drawLine(
//                                color = Color(0x70FFFFFF),
//                                start = Offset(100f, 0f),
//                                end = Offset(size.width, 0f),
//                                strokeWidth = 1.dp.toPx()
//                            )
//                        }
//
//                    }
//
//                }
//
//                Row(
//                    Modifier
//                        .height(65.dp)
//                        .fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//
//                    Canvas(
//                        modifier = Modifier
//                            .height(5.dp)
//                            .fillMaxWidth()
//
//                    ) {
//                        drawLine(
//                            color = Color(0x70FFFFFF),
//                            start = Offset(100f, 0f),
//                            end = Offset(size.width, 0f),
//                            strokeWidth = 1.dp.toPx()
//                        )
//                    }
//
//                }
//
//                for (time in 1..11) {
//                    Row(
//                        Modifier
//                            .height(65.dp)
//                            .fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Canvas(
//                            modifier = Modifier
//                                .height(5.dp)
//                                .fillMaxWidth()
//
//                        ) {
//                            drawLine(
//                                color = Color(0x70FFFFFF),
////                                       color = Color.White,
//                                start = Offset(100f, 0f),
//                                end = Offset(size.width, 0f),
//                                strokeWidth = 1.dp.toPx()
//                            )
//                        }
//                    }
//                }
//            }
//
//            Box(modifier = Modifier
//                .offset(x = 36.dp)
//                .fillMaxHeight()
//                .width(200.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(colorResource(id = R.color.Trans_White)))
//            Box(modifier = Modifier
//                .offset(x = 242.dp)
//                .fillMaxHeight()
//                .width(200.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(colorResource(id = R.color.Trans_White)))
//            Box(modifier = Modifier
//                .offset(x = 448.dp)
//                .fillMaxHeight()
//                .width(200.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(colorResource(id = R.color.Trans_White)))
//            Box(modifier = Modifier
//                .offset(x = 654.dp)
//                .fillMaxHeight()
//                .width(200.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(colorResource(id = R.color.Trans_White)))
//            Box(modifier = Modifier
//                .offset(x = 860.dp)
//                .fillMaxHeight()
//                .width(200.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(colorResource(id = R.color.Trans_White)))
//            Box(modifier = Modifier
//                .offset(x = 1066.dp)
//                .fillMaxHeight()
//                .width(200.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(colorResource(id = R.color.Trans_White)))
//            Box(modifier = Modifier
//                .offset(x = 1272.dp)
//                .fillMaxHeight()
//                .width(200.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(colorResource(id = R.color.Trans_White)))
//            Box(modifier = Modifier
//                .offset(x = 1478.dp)
//                .fillMaxHeight()
//                .width(200.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(colorResource(id = R.color.Trans_White)))
//
//            if (datestate.value == 1) {
//                datestate1.forEach { data -> fullSchUserBox(eventdetail = data) }
//            }
//            if (datestate.value == 2) {
//                datestate2.forEach { data -> fullSchUserBox(eventdetail = data) }
//            }
//            if (datestate.value == 3) {
//                datestate3.forEach { data -> fullSchUserBox(eventdetail = data) }
//            }
//
//
//
//        }

    }



    @Composable
    fun fullSchUserBox(
        eventdetail: eventWithLive
    ) {
        val coroutineScope = rememberCoroutineScope()
        val color = remember {
            mutableStateOf(
                listOf(
                    Color(0xffC80915),
                    Color(0xff1E248D),
                    Color(0xffEE6337)
                ).random()
            )
        }
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

        Box(
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
                .clip(RoundedCornerShape(8.dp))
//                .background(color.value)
                .background(Color.White)
                .clickable {

                    val frg = Events_Details_Fragment()
                    val arguments = bundleOf("Artist" to eventdetail.eventdetail.artist)
                    findNavController(this@PersonalSchedule).navigate(
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
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = eventdetail.eventdetail.artist,
                    color = Color.Black,
                    fontWeight = FontWeight.W700,
                    fontFamily = clash,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = eventdetail.eventdetail.category,
                    style = TextStyle(
//                        color = colorResource(id = R.color.textGray),
                        color = Color.Black,
                        fontFamily = clash,
                        fontWeight = FontWeight.W600,
                        fontSize = 12.sp
                    )
                )
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






