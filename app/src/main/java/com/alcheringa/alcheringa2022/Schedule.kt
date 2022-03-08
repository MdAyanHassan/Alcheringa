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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.ScheduleFragmentBinding
import com.alcheringa.alcheringa2022.ui.theme.clash
import com.alcheringa.alcheringa2022.ui.theme.greyText
import com.alcheringa.alcheringa2022.ui.theme.hk_grotesk
import com.alcheringa.alcheringa2022.ui.theme.orangeText
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
    private val datestate1 = mutableStateListOf<eventWithLive>()
    private val datestate2 = mutableStateListOf<eventWithLive>()
    private val datestate3 = mutableStateListOf<eventWithLive>()
    private var datestate = mutableStateOf<Int>(1)
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
        binding.notification.setOnClickListener{
            startActivity(Intent(context, NotificationActivity::class.java));
        }
        binding.scheduleCompose.setContent {
            mySchedule()
        }
    }


    @Composable
    fun mySchedule() {
        var color1 by remember { mutableStateOf(orangeText) }
        var color2 by remember { mutableStateOf(greyText) }
        var color3 by remember { mutableStateOf(greyText) }

        Column() {


            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.33f)
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "Day 0",
                        fontWeight = FontWeight.W700,
                        fontFamily = clash,
                        color = color1,
                        fontSize = 18.sp,
                        modifier = Modifier.clickable {
                            color1 = orangeText;color2 = greyText;color3 =
                            greyText
                            datestate.value = 1;
                        })
                    Spacer(modifier = Modifier.height(9.dp))
                    if (color1.value == orangeText.value) {
                        Canvas(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()

                        ) {
                            drawLine(
                                color = orangeText,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 2.dp.toPx(),
                            )
                        }
                    }

                }
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "Day 1",
                        fontWeight = FontWeight.W700,
                        fontFamily = clash,
                        color = color2,
                        fontSize = 18.sp,
                        modifier = Modifier.clickable {
                            color1 = greyText;color2 = orangeText;color3 =
                            greyText
                            datestate.value = 2;
                        })
                    Spacer(modifier = Modifier.height(9.dp))
                    if (color2.value == orangeText.value) {
                        Canvas(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()

                        ) {
                            drawLine(
                                color = orangeText,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 2.dp.toPx(),
                            )
                        }
                    }

                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "Day 2",
                        fontWeight = FontWeight.W700,
                        fontFamily = clash,
                        color = color3,
                        fontSize = 18.sp,
                        modifier = Modifier.clickable {
                            color1 = greyText;color2 = greyText;color3 =
                            orangeText
                            datestate.value = 3;
                        })
                    Spacer(modifier = Modifier.height(9.dp))
                    if (color3.value == orangeText.value) {
                        Canvas(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()

                        ) {
                            drawLine(
                                color = orangeText,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 2.dp.toPx(),
                            )
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            val horiscrollstate = rememberScrollState()
            Row(
                Modifier
                    .width(1245.dp)
                    .horizontalScroll(horiscrollstate)

            ) {
                Spacer(modifier = Modifier.width(70.dp))
                Row(
                    Modifier
                        .width(2025.dp)
                        , horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                    Text(
                        text = "IITG Auditorium",
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp,
                        fontFamily = clash,
                        color = Color.White
                    )
                }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "Pronites Ground",
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            fontFamily = clash,
                            color = Color.White
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "NEU Stage",
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            fontFamily = clash,
                            color = Color.White
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "Auditorium 1",
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            fontFamily = clash,
                            color = Color.White
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "Auditorium 2",
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            fontFamily = clash,
                            color = Color.White
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "Creators' Camp",
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            fontFamily = clash,
                            color = Color.White
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "Gaming",
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            fontFamily = clash,
                            color = Color.White
                        )
                    }

                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "Campaign",
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            fontFamily = clash,
                            color = Color.White
                        )
                    }
                }
            }

            Row(
                Modifier
                    .width(1245.dp)
                    .horizontalScroll(horiscrollstate)

            ) {
                Spacer(modifier = Modifier.width(70.dp))
                Row(
                    Modifier
                        .width(2025.dp)
                    , horizontalArrangement = Arrangement.SpaceEvenly
                ) { Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                    Text(
                        text = "Offline",
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        fontFamily = hk_grotesk,
                        color = Color(0xffA3A7AC)
                    )
                }

                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "Offline",
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            fontFamily = hk_grotesk,
                            color = Color(0xffA3A7AC)
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "Offline",
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            fontFamily = hk_grotesk,
                            color = Color(0xffA3A7AC)
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "PayTM Insider",
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            fontFamily = hk_grotesk,
                            color = Color(0xffA3A7AC)
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "PayTM Insider",
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            fontFamily = hk_grotesk,
                            color = Color(0xffA3A7AC)
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "PayTM Insider",
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            fontFamily = hk_grotesk,
                            color = Color(0xffA3A7AC)
                        )
                    }
                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "PayTM Insider",
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            fontFamily = hk_grotesk,
                            color = Color(0xffA3A7AC)
                        )
                    }

                    Box(modifier = Modifier.width(225.dp), contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "PayTM Insider",
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            fontFamily = hk_grotesk,
                            color = Color(0xffA3A7AC)
                        )
                    }
                }
            }




            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier
                    .width(1095.dp)
                    .height(975.dp)
                    .verticalScroll(rememberScrollState())
                    ){
                timecolummn()
                Row(
                    Modifier
                        .wrapContentSize()
                        .horizontalScroll(horiscrollstate)) {
                    fullscheduleBox()}

            }

        }


    }

    @Composable
    fun timecolummn() {

        Column(
            Modifier
                .width(70.dp)
                .padding(start = 20.dp)
                .height(975.dp), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            for (time in 9..11) {
                Row(
                    Modifier
                        .height(65.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$time AM", Modifier.width(50.dp),
                        style = TextStyle(
                            color = colorResource(id = R.color.textGray),
                            fontFamily = clash,
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp
                        )
                    )


                }

            }

            Row(
                Modifier
                    .height(65.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "12 PM", Modifier.width(50.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.textGray),
                        fontFamily = clash,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp
                    )
                )


            }

            for (time in 1..11) {
                Row(
                    Modifier
                        .height(65.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$time PM", Modifier.width(50.dp),
                        style = TextStyle(
                            color = colorResource(id = R.color.textGray),
                            fontFamily = clash,
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp
                        )
                    )


                }


            }


        }

    }

    @Composable
    fun fullscheduleBox() {

        Box(
            Modifier
                .width(2025.dp)
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
                                       color = Color(0xff192A3E),
                                       start = Offset(0f, 0f),
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
                                   color = Color(0xff192A3E),
                                   start = Offset(0f, 0f),
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
                                       color = Color(0xff192A3E),
                                       start = Offset(0f, 0f),
                                       end = Offset(size.width, 0f),
                                       strokeWidth = 1.dp.toPx()
                                   )
                               }

                           }


                       }


                   }
            
            Box(modifier = Modifier.offset(x=25.dp).fillMaxHeight().width(225.dp).clip(RoundedCornerShape(8.dp)).background(Color(0x3D323C47)))
            Box(modifier = Modifier.offset(x=275.dp).fillMaxHeight().width(225.dp).clip(RoundedCornerShape(8.dp)).background(Color(0x3D323C47)))
            Box(modifier = Modifier.offset(x=525.dp).fillMaxHeight().width(225.dp).clip(RoundedCornerShape(8.dp)).background(Color(0x3D323C47)))
            Box(modifier = Modifier.offset(x=775.dp).fillMaxHeight().width(225.dp).clip(RoundedCornerShape(8.dp)).background(Color(0x3D323C47)))
            Box(modifier = Modifier.offset(x=1025.dp).fillMaxHeight().width(225.dp).clip(RoundedCornerShape(8.dp)).background(Color(0x3D323C47)))
            Box(modifier = Modifier.offset(x=1275.dp).fillMaxHeight().width(225.dp).clip(RoundedCornerShape(8.dp)).background(Color(0x3D323C47)))
            Box(modifier = Modifier.offset(x=1525.dp).fillMaxHeight().width(225.dp).clip(RoundedCornerShape(8.dp)).background(Color(0x3D323C47)))
            Box(modifier = Modifier.offset(x=1775.dp).fillMaxHeight().width(225.dp).clip(RoundedCornerShape(8.dp)).background(Color(0x3D323C47)))

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
        val ydis = (25
                + (250
                *catid.value
                )
                )
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
                .width(225.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color.value)
                .clickable {

                    val frg = Events_Details_Fragment()
                    val arguments = bundleOf("Artist" to eventdetail.eventdetail.artist)
                    findNavController(this@Schedule).navigate(R.id.action_schedule_to_events_Details_Fragment,arguments);
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
                    color = Color.White,
                    fontWeight = FontWeight.W700,
                    fontFamily = clash,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = eventdetail.eventdetail.category,
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






