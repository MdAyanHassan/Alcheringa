package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.eventdetail
import com.alcheringa.alcheringa2022.Model.venue
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.ScheduleFragmentBinding
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Schedule2024 : Fragment() {
    private lateinit var binding: ScheduleFragmentBinding
    private lateinit var fm: FragmentManager
    private val homeviewmodel: viewModelHome by activityViewModels()
    private val datestate0 = mutableStateListOf<eventWithLive>()
    private val datestate1 = mutableStateListOf<eventWithLive>()
    private val datestate2 = mutableStateListOf<eventWithLive>()
    private val datestate3 = mutableStateListOf<eventWithLive>()
    private var selectedDayEvents = datestate0
    private var datestate = mutableStateOf<Int>(0)
    private var itemListMap = mapOf(
        "All" to listOf(),
        "Lecture Halls" to listOf("Lecture Hall 1", "Lecture Hall 2", "Lecture Hall 3", "Lecture Hall 4"),
        "Grounds" to listOf("Football Field", "Basketball Courts", "Volley Ball Court")
    )
    var selectedItem = mutableStateOf("All")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fm = parentFragmentManager

        CoroutineScope(Dispatchers.Main).launch {
            homeviewmodel.allEventsWithLivedata.observe(requireActivity()){data->
                datestate0.clear();
                datestate0.addAll((data.filter { data -> data.eventdetail.starttime.date == 2 }))
                datestate1.clear();
                datestate1.addAll((data.filter { data -> data.eventdetail.starttime.date == 3 }))
                datestate2.clear();
                datestate2.addAll((data.filter { data -> data.eventdetail.starttime.date == 4 }))
                datestate3.clear();
                datestate3.addAll((data.filter { data -> data.eventdetail.starttime.date == 5 }))
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScheduleFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Day select Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 48.dp)
                ,

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .clickable {
                            datestate.value = 0
                            selectedDayEvents = datestate0
                        }
                        .padding(top = 26.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Day 0",
                        fontSize = 16.sp
                    )
                    if(datestate.value == 0){
                        Spacer(modifier = Modifier.height(7.dp))
                        Icon(ImageVector.vectorResource(R.drawable.squiggle), null)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .clickable {
                            datestate.value = 1
                            selectedDayEvents = datestate1
                        }
                        .padding(top = 26.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Day 1",
                        fontSize = 16.sp
                    )
                    if(datestate.value == 1){
                        Spacer(modifier = Modifier.height(7.dp))
                        Icon(ImageVector.vectorResource(R.drawable.squiggle), null)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .clickable {
                            datestate.value = 2
                            selectedDayEvents = datestate2
                        }
                        .padding(top = 26.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Day 2",
                        fontSize = 16.sp
                    )
                    if(datestate.value == 2){
                        Spacer(modifier = Modifier.height(7.dp))
                        Icon(ImageVector.vectorResource(R.drawable.squiggle), null)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .clickable {
                            datestate.value = 3
                            selectedDayEvents = datestate3
                        }
                        .padding(top = 26.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Day 3",
                        fontSize = 16.sp
                    )
                    if(datestate.value == 3){
                        Spacer(modifier = Modifier.height(7.dp))
                        Icon(ImageVector.vectorResource(R.drawable.squiggle), null)
                    }
                }
            }

            // Filter menu row
            Row(
                modifier = Modifier
                    .height(74.dp)
                    .fillMaxWidth()
                    .border(border = BorderStroke(1.dp, color = colors.onBackground))
            ){

                var expanded by remember{ mutableStateOf(false)}
                val filterList = listOf("All","Admin Area", "Lecture Halls", "Grounds")

                val icon = if (expanded){
                    Icons.Filled.KeyboardArrowUp
                }else{
                    Icons.Filled.KeyboardArrowDown
                }


                Column {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(220.dp),
                        value = selectedItem.value,
                        onValueChange = { selectedItem.value = it},
                        trailingIcon = {
                            Icon(icon, "", Modifier.clickable { expanded = !expanded })
                        },
                        readOnly = true

                    )

                    DropdownMenu(
                        expanded = expanded ,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.width(220.dp)
                    ) {
                        filterList.forEach { label -> 
                            DropdownMenuItem(onClick = {
                                selectedItem.value = label
                                expanded = false
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                }
            }

            val verticalScroll = rememberScrollState()
            val horizontalScroll = rememberScrollState()
            val testList = datestate1.filter { data-> data.eventdetail.venue.replace("\\s".toRegex(), "").uppercase()== "AUDITORIUM".replace("\\s".toRegex(), "").uppercase()}

            val selectedVenueList = itemListMap.get(selectedItem.value)!!


            Row(Modifier.fillMaxSize()) {

                TimeColumn(scroll = verticalScroll)

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .horizontalScroll(horizontalScroll)
                ) {

                    Row(Modifier.wrapContentSize()){
                        selectedVenueList.forEach { venue ->

                            val eventList = FilterVenueEvents(venue, selectedDayEvents)
                            if (eventList.isNotEmpty()) {
                                VenueSchedule(venue = venue, scroll = verticalScroll, eventList)
                            }

                        }
                    }


                }
            }

        }
    }

    @Composable
    fun TimeColumn(scroll: ScrollState){
        Column(
            modifier = Modifier
                .width(72.dp)
                .verticalScroll(scroll)
                .wrapContentHeight()
        ) {
            Text(
                text = "",
                fontSize = 16.sp,
                color = Color.Transparent
            )

            Box(
                Modifier
                    .fillMaxSize()
            ){
                Box {
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        for (time in 7..20) {
                            Row(
                                Modifier
                                    .height(44.dp)
                                    .fillMaxWidth()
                                ,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Canvas(
                                    modifier = Modifier
                                        .height(5.dp)
                                        .fillMaxWidth()

                                ) {
                                    drawLine(
                                        color = Color.Black,
                                        start = Offset(size.width - 24f, 0f),
                                        end = Offset(size.width, 0f),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }

                            }
                        }
                    }
                }
                Box(
                    Modifier.fillMaxSize()
                ){
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp)
                        ) {
                        for( time in 8..11){
                            Row(
                                Modifier
                                    .height(44.dp)
                                    .fillMaxWidth()
                                ,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = time.toString() + " am",
                                    fontSize = 16.sp
                                )
                            }

                        }

                        Row(
                            Modifier
                                .height(44.dp)
                                .fillMaxWidth()
                            ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "12 pm",
                                fontSize = 16.sp
                            )
                        }
                        for( time in 1..9){
                            Row(
                                Modifier
                                    .height(44.dp)
                                    .fillMaxWidth()
                                ,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = time.toString() + " pm",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }


        }
    }
    @Composable
    fun VenueSchedule(venue: String, scroll: ScrollState, eventList: List<eventWithLive>){
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .width(208.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = venue,
                fontSize = 16.sp
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll)
            ){
                Box(
                    Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        for (time in 7..20) {
                            Row(
                                Modifier
                                    .height(44.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Canvas(
                                    modifier = Modifier
                                        .height(5.dp)
                                        .fillMaxWidth()

                                ) {
                                    drawLine(
                                        color = Color.Black,
                                        start = Offset(0f, 0f),
                                        end = Offset(size.width, 0f),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }

                            }
                        }
                    }
                }

                eventList.forEach { event ->

                    val xdis =
                        (((event.eventdetail.starttime.hours - 8) * 44).toFloat() + (event.eventdetail.starttime.min.toFloat() * (44f / 60f)) + 22f)
                    val xdisinpxcald = with(LocalDensity.current) { (xdis ).dp.toPx() }


                    val yOffset = ((event.eventdetail.starttime.hours - 8) * 44) + 21
                    val height = (event.eventdetail.durationInMin/60) * 44
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .offset { IntOffset(0, xdisinpxcald.toInt()) }
                            .height(height.dp)
                            .padding(horizontal = 4.dp),
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp)
                                .border(
                                    shape = RoundedCornerShape(4.dp),
                                    border = BorderStroke(color = colors.onBackground, width = 1.dp)
                                )
                                .background(color = colors.background),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = event.eventdetail.artist,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = event.eventdetail.type,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }

    fun FilterVenueEvents(venue: String, selectedDayEvents: List<eventWithLive>) : List<eventWithLive>{
        return selectedDayEvents.filter { data-> data.eventdetail.venue.replace("\\s".toRegex(), "").uppercase()== venue.replace("\\s".toRegex(), "").uppercase()}
    }

}