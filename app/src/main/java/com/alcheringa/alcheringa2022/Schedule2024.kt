package com.alcheringa.alcheringa2022

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.ScheduleFragmentBinding
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.lightBar
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

    @OptIn(ExperimentalFoundationApi::class)
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
        Box(
            modifier = Modifier
                .background(colors.background)
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
                        .padding(horizontal = 48.dp)
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
                    val filterList = listOf("All", "Admin Area", "Lecture Halls", "Grounds")

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
                                    text = "01",
                                    color = darkTealGreen,
                                    fontSize = 22.sp,
                                    fontFamily = futura,
                                    fontWeight = FontWeight.SemiBold

                                )
                            }
                            else if (datestate.value == 1){
                                Text(
                                    text = "02",
                                    color = darkTealGreen,
                                    fontSize = 22.sp,
                                    fontFamily = futura,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            else if(datestate.value == 2){
                                Text(
                                    text = "03",
                                    color = darkTealGreen,
                                    fontSize = 22.sp,
                                    fontFamily = futura,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            else if (datestate.value == 3){
                                Text(
                                    text = "04",
                                    color = darkTealGreen,
                                    fontSize = 22.sp,
                                    fontFamily = futura,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = "Oct",
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
                                    .height(45.dp)
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

                val selectedVenueList = itemListMap.get(selectedItem.value)!!

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


                Row(Modifier.fillMaxSize()) {

                    TimeColumn(scroll = verticalScroll)

                    if (selectedVenueList.isNotEmpty()) {

                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .horizontalScroll(horizontalScroll)
                        ) {

                            Row(Modifier.wrapContentSize()) {
                                selectedVenueList.forEach { venue ->

                                    val eventList = FilterVenueEvents(venue, selectedDayEvents)
                                    if (eventList.isNotEmpty()) {
                                        VenueSchedule(
                                            venue = venue,
                                            scroll = verticalScroll,
                                            eventList
                                        )
                                    }

                                }


                            }


                        }
                    }
                    else{
                        Box (
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "There is no event \nat the current location",
                                color = colors.onBackground,
                                fontSize = 20.sp
                            )
                        }
                    }
                }

            }
        }
    }

    @Composable
    fun TimeColumn(scroll: ScrollState){
        Box {
            val onbackgroundcolor = colors.onBackground
            val horizontal_dash_color = if (isSystemInDarkTheme()) containerPurple else darkTealGreen
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
                Text(
                    text = "",
                    fontSize = 18.sp,
                    fontFamily = futura,
                    fontWeight = FontWeight.Medium,
                    color = Color.Transparent
                )
                Row(
                    Modifier
                        .height(44.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "",
                        fontSize = 16.sp,
                        fontFamily = futura,
                        color = Color.Transparent
                    )
                }

                Box(
                    Modifier
                        .fillMaxSize()
                ) {
                    Box {
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
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 12.dp)
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
                                        color = colors.onBackground
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
                }


            }
        }
    }
    @Composable
    fun VenueSchedule(venue: String, scroll: ScrollState, eventList: List<eventWithLive>){
        val horizontal_dash_color = if (isSystemInDarkTheme()) containerPurple else darkTealGreen

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
                Text(
                    text = venue,
                    fontSize = 18.sp,
                    fontFamily = futura,
                    color = colors.onBackground,
                    fontWeight = FontWeight.Medium
                )

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
                            for (time in 6..20) {
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


                        val yOffset = ((event.eventdetail.starttime.hours - 8) * 44) + 21
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
                                    .background(color = colors.background, shape = RoundedCornerShape(4.dp))
                                    .clickable {
                                        val arguments = bundleOf("Artist" to event.eventdetail.artist)

                                        NavHostFragment
                                            .findNavController(this@Schedule2024)
                                            .navigate(R.id.action_schedule_to_events_Details_Fragment, arguments);
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
                strokeWidth = 1.dp.toPx()
            )
        }
    }

    fun FilterVenueEvents(venue: String, selectedDayEvents: List<eventWithLive>) : List<eventWithLive>{
        return selectedDayEvents.filter { data-> data.eventdetail.venue.replace("\\s".toRegex(), "").uppercase()== venue.replace("\\s".toRegex(), "").uppercase()}
    }

}