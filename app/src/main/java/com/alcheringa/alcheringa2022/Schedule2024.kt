package com.alcheringa.alcheringa2022

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.venue
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.FragmentSchedule2024Binding
//import com.alcheringa.alcheringa2022.databinding.ScheduleFragmentBinding
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.darkerPurple
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.lightBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.Calendar
import java.util.TimeZone
import kotlin.properties.Delegates


class Schedule2024 : Fragment() {
    private lateinit var binding: FragmentSchedule2024Binding
    private lateinit var fm: FragmentManager
    private val homeviewmodel: viewModelHome by activityViewModels()
    private val datestate0 = mutableStateListOf<eventWithLive>()
    private val datestate1 = mutableStateListOf<eventWithLive>()
    private val datestate2 = mutableStateListOf<eventWithLive>()
    private val datestate3 = mutableStateListOf<eventWithLive>()
    private var selectedDayEvents = datestate0
    private var datestate = mutableStateOf<Int>(0)
    private var itemListMap = mapOf(
        "All" to listOf("Lecture Hall 1", "Lecture Hall 2", "Lecture Hall 3", "Lecture Hall 4","Core 5",
            "Front of Graffiti Wall","Behind Graffiti Wall","Old Sac Wall","Conference Hall 1","Conference Hall 2","Conference Hall 3","Conference Hall 4","Mini Auditorium","Auditorium","Audi Park","Senate Hall"
            ,"Library","Library Shed","Library Basement","Football Field", "Basketball Courts", "Volley Ball Court","Pronite Stage","Athletics Field", "New SAC"),
        "Lecture Halls" to listOf("Lecture Hall 1", "Lecture Hall 2", "Lecture Hall 3", "Lecture Hall 4","Core 5"),
        "Grounds" to listOf("Football Field", "Basketball Courts", "Volley Ball Court","Pronite Stage","Athletics Field"),
        "Library Area" to listOf("Library","Library Shed","Library Basement"),
        "Admin Area" to listOf("Senate Hall","Rocko Stage","Expo Stage"),
        "Auditorium" to listOf("Mini Auditorium","Auditorium","Audi Park"),
        "Conference Hall" to listOf("Conference Hall 1","Conference Hall 2","Conference Hall 3","Conference Hall 4"),
        "SAC Area" to listOf("Front of Graffiti Wall","Behind Graffiti Wall","Old Sac Wall", "Old Sac Stage", "New SAC")

    )
    var selectedItem = mutableStateOf("All")

//    val currentHour = mutableStateOf(13)
//    val currentMinute = mutableStateOf(30)
    private var yOffset = mutableStateOf(0f)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fm = parentFragmentManager

        CoroutineScope(Dispatchers.Main).launch {
            homeviewmodel.allEventsWithLivedata.observe(requireActivity()){data->
                datestate0.clear();
                datestate0.addAll((data.filter { data -> data.eventdetail.starttime.date == 7 }))
                datestate1.clear();
                datestate1.addAll((data.filter { data -> data.eventdetail.starttime.date == 8 }))
                datestate2.clear();
                datestate2.addAll((data.filter { data -> data.eventdetail.starttime.date == 9 }))
                datestate3.clear();
                datestate3.addAll((data.filter { data -> data.eventdetail.starttime.date == 10 }))
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchedule2024Binding.inflate(layoutInflater)
        return binding.root
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.account.setOnClickListener {
//            startActivity(Intent(context, ProfilePage::class.java));
            (activity as MainActivity).drawer.openDrawer(Gravity.RIGHT)
        }
//        binding.pass.setOnClickListener{
//            startActivity(Intent(context, NotificationActivity::class.java));
//        }

        binding.search.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_schedule_to_searchFragment)
        }
        binding.scheduleCompose.setContent {
            Alcheringa2022Theme(){
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ){
                    mySchedule()
                }
            }
        }
    }

    @Composable
    fun mySchedule() {
        val onbackgroundcolor = colors.onBackground
        val lcd = LocalDensity.current
        LaunchedEffect(true) {
            while (true){
                val cal = Calendar.getInstance()
                val currentHour = cal.get(Calendar.HOUR_OF_DAY)
                val currentMin = cal.get(Calendar.MINUTE)
                yOffset.value = with(lcd) { ((currentHour - 8f) * 44.dp.toPx()) + (currentMin * (44.dp.toPx()/60f)) + 21.dp.toPx()}
                Log.d("Schedule", "$currentHour : $currentMin")
                delay(60000)
            }
        }
        Box(
            modifier = Modifier
                .paint(
                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                    contentScale = ContentScale.Crop
                )
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
//                Canvas(
//                    modifier = Modifier
//                        .height(1.dp)
//                        .fillMaxWidth()
//                ){
//                    drawLine(
//                        color = darkTealGreen,
//                        start = Offset(0f, 0f),
//                        end = Offset(size.width,0f),
//                        strokeWidth = 1.dp.toPx()
//                    )
//                }
                Divider(modifier = Modifier
                    .height(2.dp)
                    .background(darkTealGreen))
                // Day select Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .paint(
                            painterResource(id = R.drawable.day_number_transparent),
                            contentScale = ContentScale.FillBounds
                        )
                        .padding(horizontal = 16.dp)
                    ,

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth()
                            .clickable {
                                datestate.value = 0
                                selectedDayEvents = datestate0
                            }
                            .padding(top = 20.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Day 0",
                            fontSize = 18.sp,
                            fontFamily = futura,
                            color = colors.onBackground,
                            fontWeight = FontWeight.Medium
                        )
                        if (datestate.value == 0) {
                            Spacer(modifier = Modifier.height(7.dp))
                            Icon(
                                ImageVector.vectorResource(R.drawable.squiggle),
                                null,
                                tint = colors.onBackground
                            )
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
                            .padding(top = 20.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Day 1",
                            fontSize = 18.sp,
                            fontFamily = futura,
                            color = colors.onBackground,
                            fontWeight = FontWeight.Medium
                        )
                        if (datestate.value == 1) {
                            Spacer(modifier = Modifier.height(7.dp))
                            Icon(
                                ImageVector.vectorResource(R.drawable.squiggle),
                                null,
                                tint = colors.onBackground
                            )
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
                            .padding(top = 20.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Day 2",
                            fontSize = 18.sp,
                            fontFamily = futura,
                            color = colors.onBackground,
                            fontWeight = FontWeight.Medium
                        )
                        if (datestate.value == 2) {
                            Spacer(modifier = Modifier.height(7.dp))
                            Icon(
                                ImageVector.vectorResource(R.drawable.squiggle),
                                null,
                                tint = colors.onBackground
                            )
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
                            .padding(top = 20.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Day 3",
                            fontSize = 18.sp,
                            fontFamily = futura,
                            color = colors.onBackground,
                            fontWeight = FontWeight.Medium
                        )
                        if (datestate.value == 3) {
                            Spacer(modifier = Modifier.height(7.dp))
                            Icon(
                                ImageVector.vectorResource(R.drawable.squiggle),
                                null,
                                tint = colors.onBackground
                            )
                        }
                    }
                }

                // Filter menu row
                Row(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
//                        .border(border = BorderStroke(1.dp, color = colors.onBackground))
                ) {

                    var expanded by remember { mutableStateOf(false) }
                    val filterList = itemListMap.keys

                    val icon = if (expanded) {
                        Icons.Filled.KeyboardArrowUp
                    } else {
                        Icons.Filled.KeyboardArrowDown
                    }

                    Row (
                        modifier = Modifier
                            .width(71.dp)
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            if (datestate.value == 0) {
                                Text(
                                    text = "07",
                                    color = darkTealGreen,
                                    fontSize = 22.sp,
                                    fontFamily = futura,
                                    fontWeight = FontWeight.SemiBold

                                )
                            }
                            else if (datestate.value == 1){
                                Text(
                                    text = "08",
                                    color = darkTealGreen,
                                    fontSize = 22.sp,
                                    fontFamily = futura,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            else if(datestate.value == 2){
                                Text(
                                    text = "09",
                                    color = darkTealGreen,
                                    fontSize = 22.sp,
                                    fontFamily = futura,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            else if (datestate.value == 3){
                                Text(
                                    text = "10",
                                    color = darkTealGreen,
                                    fontSize = 22.sp,
                                    fontFamily = futura,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = "Mar",
                                color = darkTealGreen,
                                fontSize = 22.sp,
                                fontFamily = futura,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                ImageVector.vectorResource(R.drawable.squiggle),
                                null,
                                tint = darkTealGreen
                            )
                        }
                    }

                    Canvas(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight(),
                    ){
                        drawLine(
                            color = onbackgroundcolor,
                            start = Offset(size.width/2, 0f),
                            end = Offset(size.width/2, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    Row (
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Column {
                            OutlinedTextField(
                                modifier = Modifier
                                    .width(220.dp)

                                    .background(colors.onSurface)
                                    .border(
                                        1.dp,
                                        colors.onBackground,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .clickable { expanded = !expanded },
                                value = selectedItem.value,
                                onValueChange = { selectedItem.value = it },
                                trailingIcon = {
                                    Icon(
                                        icon,
                                        "",
                                        Modifier.clickable { expanded = !expanded },
                                        tint = colors.onBackground
                                    )
                                },
                                readOnly = true,
                                enabled = false,
                                colors = TextFieldDefaults.textFieldColors(textColor = colors.onBackground, disabledTextColor = colors.onBackground)
                            )

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .width(220.dp)
                                    .background(colors.background)
                                    .border(1.dp, colors.onBackground, RoundedCornerShape(4.dp)),
                            ) {
                                filterList.forEach { label ->
                                    DropdownMenuItem(onClick = {
                                        selectedItem.value = label
                                        expanded = false
                                    }) {
                                        Text(text = label, color = colors.onBackground, fontFamily = futura, fontWeight = FontWeight.Medium)
                                    }
                                }
                            }
                        }
                    }
                }

                val verticalScroll = rememberScrollState()
                val horizontalScroll = rememberScrollState()
                val testList = datestate1.filter { data ->
                    data.eventdetail.venue.replace("\\s".toRegex(), "")
                        .uppercase() == "AUDITORIUM".replace("\\s".toRegex(), "").uppercase()
                }

                val selectedVenueList = itemListMap[selectedItem.value]!!
//                val selectedVenueList by remember { mutableStateOf(itemListMap[selectedItem.value]!!) }
                val filteredList = mutableStateMapOf<String, List<eventWithLive>>()

                selectedVenueList.forEach{
                    filteredList[it] = FilterVenueEvents(it, selectedDayEvents)
                }

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                ){
                    drawLine(
                        color = onbackgroundcolor,
                        start = Offset(0f, size.height/2),
                        end = Offset(size.width, size.height/2),
                        strokeWidth = 1.dp.toPx()
                    )
                }


                Box(
                    Modifier.fillMaxSize()
                ) {
                    Row(Modifier.fillMaxSize()) {

                        TimeColumn(scroll = verticalScroll)

                        if (CheckEmptyMap(filteredList)) {

                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .horizontalScroll(horizontalScroll)
                            ) {


                                Row(Modifier.wrapContentSize()) {
                                    var count = 0
                                    selectedVenueList.forEach { venue ->


                                        if (filteredList[venue]!!.isNotEmpty()) {
                                            VenueSchedule(
                                                venue = venue,
                                                scroll = verticalScroll,
                                                filteredList[venue]!!
                                            )
                                            count++
                                        }

                                    }
                                    if(count==1){
                                        VenueSchedule(venue = " ", scroll = verticalScroll, eventList = listOf() )
                                    }


                                }


                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "There is no event \nat the current location",
                                    color = colors.onBackground,
                                    fontSize = 20.sp,
                                    fontFamily = futura
                                )
                            }
                        }
                    }

//                    Box(modifier = Modifier.fillMaxSize()) {
//                        Canvas(
//                            modifier = Modifier
//                                .height(5.dp)
////                                .verticalScroll(verticalScroll)
//
//
//                        ) {
//                            drawCircle(
//                                color = darkTealGreen,
//                                radius = 12f,
//
//                                )
//                        }
//                    }
                }

            }
        }
    }

    private fun CheckEmptyMap(filteredList: SnapshotStateMap<String, List<eventWithLive>>): Boolean {
        for (key in filteredList.keys) {
            if(filteredList[key]!!.isNotEmpty()){
                return true
            }
        }
        return false
    }

    @Composable
    fun TimeColumn(scroll: ScrollState){
        Box {
            val onbackgroundcolor = colors.onBackground
            val horizontal_dash_color = containerPurple

//            val yOffset = with(LocalDensity.current) { ((currentHour.value - 8f) * 44.dp.toPx()) + (currentMinute.value * (44.dp.toPx()/60f)) + 21.dp.toPx()}
//            + (currentMinute.value * (44f/60f))

            Canvas(
                modifier = Modifier
                    .width(72.dp)
                    .height(616.dp)
            ){
                drawLine(
                    color = onbackgroundcolor,
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }


            Column(
                modifier = Modifier
                    .width(72.dp)
                    .verticalScroll(scroll)
                    .wrapContentHeight()
                    .padding(start = 10.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp), color = Color.Transparent)
                Text(
                    text = "",
                    fontSize = 18.sp,
                    fontFamily = futura,
                    fontWeight = FontWeight.Medium,
                    color = Color.Transparent
                )
                Spacer(modifier = Modifier.height(10.dp))


                Box(
                    Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 7.dp)
                        ) {
                            for (time in 8..11) {
                                Row(
                                    Modifier
                                        .height(44.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = time.toString() + " am",

                                        fontSize = 16.sp,
                                        fontFamily = futura,
                                        color = colors.onBackground,

                                    )
                                }

                            }

                            Row(
                                Modifier
                                    .height(44.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "12 pm",
                                    fontSize = 16.sp,
                                    fontFamily = futura,
                                    color = colors.onBackground
                                )
                            }
                            for (time in 1..9) {
                                Row(
                                    Modifier
                                        .height(44.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = time.toString() + " pm",
                                        fontSize = 16.sp,
                                        fontFamily = futura,
                                        color = colors.onBackground
                                    )
                                }
                            }
                        }
                    }

                    if(Calendar.HOUR_OF_DAY >= 8 && Calendar.HOUR_OF_DAY <= 21){
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()


                        ) {
                            drawCircle(
                                color = darkTealGreen,
                                radius = 18f,
                                center = Offset(size.width, yOffset.value)
                            )
                        }
                    }




                }


            }
        }
    }
    @Composable
    fun VenueSchedule(venue: String, scroll: ScrollState, eventList: List<eventWithLive>){
        val horizontal_dash_color = containerPurple
//        val yOffset = with(LocalDensity.current) { ((currentHour.value - 8f) * 44.dp.toPx()) + (currentMinute.value * (44.dp.toPx()/60f)) + 21.dp.toPx()}
        val cal = Calendar.getInstance()
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ){
            drawLine(
                color = horizontal_dash_color,
                start = Offset(size.width, 0f),
                end = Offset(size.width, size.height),
                strokeWidth = 1.5.dp.toPx()
            )
        }

        Box (
            modifier = Modifier
                .width(208.dp)
                .wrapContentHeight()
        ){
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(208.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = venue,
                    fontSize = 18.sp,
                    fontFamily = futura,
                    color = colors.onBackground,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp), color = horizontal_dash_color)

                Box(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(scroll)
                ) {
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
                                            color = horizontal_dash_color,
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
                            (((event.eventdetail.starttime.hours - 8) * 44.2).toFloat() + (event.eventdetail.starttime.min.toFloat() * (44.2f / 60f)) + 23f)
                        val xdisinpxcald = with(LocalDensity.current) { (xdis + 41).dp.toPx() }


//                        val yOffset = ((event.eventdetail.starttime.hours - 8) * 44) + 21
                        val height = (event.eventdetail.durationInMin / 60) * 44.2
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .offset { IntOffset(0, xdisinpxcald.toInt()) }
                                .height(height.dp)
                                .padding(horizontal = 4.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 4.dp)
                                    .border(
                                        shape = RoundedCornerShape(4.dp),
                                        border = BorderStroke(
                                            color = colors.onBackground,
                                            width = 1.dp
                                        )
                                    )
                                    .background(
                                        color = colors.background,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .clickable {
                                        val arguments =
                                            bundleOf("Artist" to event.eventdetail.artist)

                                        NavHostFragment
                                            .findNavController(this@Schedule2024)
                                            .navigate(
                                                R.id.action_schedule_to_events_Details_Fragment,
                                                arguments
                                            );
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = event.eventdetail.artist,
                                    fontSize = 18.sp,
                                    fontFamily = futura,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = colors.onBackground
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = event.eventdetail.type,
                                    fontSize = 14.sp,
                                    fontFamily = futura,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = colors.onBackground
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.card_background_transparent),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 4.dp)
                            )
                        }
                    }

                    if(cal.get(Calendar.HOUR_OF_DAY) in 8..21){
                        Canvas(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()

                        ) {

                            drawLine(
                                color = darkTealGreen,
                                start = Offset(0f, yOffset.value),
                                end = Offset(size.width, yOffset.value),
                                strokeWidth = 2.5.dp.toPx()
                            )
//                            drawCircle(
//                                color = darkTealGreen,
//                                radius = 18f,
//                                center = Offset(0f, yOffset.value)
//                            )
                        }
                    }
                }
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ){
            drawLine(
                color = horizontal_dash_color,
                start = Offset(size.width, 0f),
                end = Offset(size.width, size.height),
                strokeWidth = 1.5.dp.toPx()
            )
        }

    }

    fun FilterVenueEvents(venue: String, selectedDayEvents: List<eventWithLive>) : List<eventWithLive>{
        return selectedDayEvents.filter { data-> data.eventdetail.venue.replace("\\s".toRegex(), "").uppercase()== venue.replace("\\s".toRegex(), "").uppercase()}
    }

}