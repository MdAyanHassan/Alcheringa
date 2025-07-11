package com.alcheringa.alcheringa2022

//import com.alcheringa.alcheringa2022.databinding.ScheduleFragmentBinding
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.FragmentSchedule2024Binding
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.futura
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar


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
        "All" to listOf("Lecture Hall 1", "Lecture Hall 2", "Lecture Hall 3", "Lecture Hall 4","Core 5", "Core 1",
            "Front of Graffiti Wall","Behind Graffiti Wall","Old Sac Wall", "New SAC", "Old Sac Stage",
            "Conference Hall 1","Conference Hall 2","Conference Hall 3","Conference Hall 4",
            "Mini Auditorium","Auditorium","Audi Park",
            "Senate Hall", "Rocko Stage","Expo Stage"
            ,"Library","Library Shed","Library Basement",
            "Football Field", "Basketball Courts", "Volley Ball Court","Pronite Stage","Athletics Field",
            "Entire Campus"

        ),


        "Lecture Halls" to listOf("Lecture Hall 1", "Lecture Hall 2", "Lecture Hall 3", "Lecture Hall 4","Core 5", "Core 1"),
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
                    painterResource(id = R.drawable.cart_bg),
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
//                Divider(modifier = Modifier
//                    .height(2.dp)
//                    .background(darkTealGreen))
                // Day select Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 16.dp)
                    ,

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                datestate.value = 0
                                selectedDayEvents = datestate0
                            }
//                            .background(
//                                if (datestate.value == 0) darkTealGreen else Color.Transparent,
//                                RoundedCornerShape(8.dp)
//                            )
//                            .border(1.dp, colors.onBackground, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "DAY 0",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel),
                                Font(R.font.alcher_pixel_bold)
                            ),
                            color = if (datestate.value == 0) Color(0xFFFFF1E8) else Color(0xFF83769C),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                datestate.value = 1
                                selectedDayEvents = datestate1
                            }
//                            .background(
//                                if (datestate.value == 1) darkTealGreen else Color.Transparent,
//                                RoundedCornerShape(8.dp)
//                            )
//                            .border(1.dp, colors.onBackground, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "DAY 1",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel),
                                Font(R.font.alcher_pixel_bold)
                            ),
                            color = if (datestate.value == 1) Color(0xFFFFF1E8) else Color(0xFF83769C),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                datestate.value = 2
                                selectedDayEvents = datestate2
                            }
//                            .background(
//                                if (datestate.value == 2) darkTealGreen else Color.Transparent,
//                                RoundedCornerShape(8.dp)
//                            )
//                            .border(1.dp, colors.onBackground, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "DAY 2",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel),
                                Font(R.font.alcher_pixel_bold)
                            ),
                            color = if (datestate.value == 2) Color(0xFFFFF1E8) else Color(0xFF83769C),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                datestate.value = 3
                                selectedDayEvents = datestate3
                            }
//                            .background(
//                                if (datestate.value == 3) darkTealGreen else Color.Transparent,
//                                RoundedCornerShape(8.dp)
//                            )
//                            .border(1.dp, colors.onBackground, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "DAY 3",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel),
                                Font(R.font.alcher_pixel_bold)
                            ),
                            color = if (datestate.value == 3) Color(0xFFFFF1E8) else Color(0xFF83769C),
                            fontWeight = FontWeight.Medium
                        )
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
                                    text = "30",
                                    color = Color(0xFFCA3562),
                                    fontSize = 24.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel),
                                        Font(R.font.alcher_pixel_bold)
                                    ),
                                    fontWeight = FontWeight.SemiBold

                                )
                            }
                            else if (datestate.value == 1){
                                Text(
                                    text = "31",
                                    color = Color(0xFFCA3562),
                                    fontSize = 24.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel),
                                        Font(R.font.alcher_pixel_bold)
                                    ),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            else if(datestate.value == 2){
                                Text(
                                    text = "01",
                                    color = Color(0xFFCA3562),
                                    fontSize = 24.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel),
                                        Font(R.font.alcher_pixel_bold)
                                    ),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            else if (datestate.value == 3){
                                Text(
                                    text = "02",
                                    color = Color(0xFFCA3562),
                                    fontSize = 24.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel),
                                        Font(R.font.alcher_pixel_bold)
                                    ),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = if (datestate.value == 0 || datestate.value == 1) "JAN" else "FEB",
                                color = Color(0xFFCA3562),
                                fontSize = 24.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel),
                                    Font(R.font.alcher_pixel_bold)
                                ),
                                fontWeight = FontWeight.SemiBold
                            )
//                            Icon(
//                                ImageVector.vectorResource(R.drawable.squiggle),
//                                null,
//                                tint = darkTealGreen
//                            )
                        }
                    }

                    Canvas(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight(),
                    ){
                        drawLine(
                            color = Color(0xFF83769C),
                            start = Offset(size.width/2, 0f),
                            end = Offset(size.width/2, size.height),
                            strokeWidth = 3.dp.toPx()
                        )
                    }

                    Row (
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                        ) {
//                            OutlinedTextField(
//                                modifier = Modifier
//                                    .width(220.dp)
//
//                                    .background(colors.onSurface)
//                                    .border(
//                                        1.dp,
//                                        colors.onBackground,
//                                        shape = RoundedCornerShape(4.dp)
//                                    )
//                                    .clickable { expanded = !expanded },
//                                value = selectedItem.value,
//                                onValueChange = { selectedItem.value = it },
//                                trailingIcon = {
//                                    Icon(
//                                        icon,
//                                        "",
//                                        Modifier.clickable { expanded = !expanded },
//                                        tint = colors.onBackground
//                                    )
//                                },
//                                readOnly = true,
//                                enabled = false,
//                                colors = TextFieldDefaults.textFieldColors(textColor = colors.onBackground, disabledTextColor = colors.onBackground)
//                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        expanded = !expanded
                                    }
                                    .background(color = Color(0xFF1D2B53))
                                    .border(2.dp, Color(0xFF7E2553))
                                    .padding(horizontal = 24.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedItem.value,
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel),
                                        Font(R.font.alcher_pixel_bold)
                                    ),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 22.sp,
                                    color = Color(0xFFFFF1E8)

                                )

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Divider(
                                        Modifier
                                            .fillMaxHeight()
                                            .width(0.dp), color = colors.onBackground
                                    )
                                    Spacer(modifier = Modifier.width(24.dp))

                                    Icon(
                                        icon,
                                        "",
                                        Modifier.clickable { expanded = !expanded },
                                        tint = colors.onBackground
                                    )
                                }

                            }

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
                                        Text(text = label, color = Color(0xFFFFF1E8), fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel),
                                            Font(R.font.alcher_pixel_bold)
                                        ), fontWeight = FontWeight.Medium)
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
                        color = Color(0xFF83769C),
                        start = Offset(0f, size.height/2),
                        end = Offset(size.width, size.height/2),
                        strokeWidth = 3.dp.toPx()
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
                                    color = Color(0xFFFFF1E8),
                                    fontSize = 22.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel),
                                        Font(R.font.alcher_pixel_bold)
                                    )
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
                    .height(616.dp + 44.dp)
            ){
                drawLine(
                    color = Color(0xFF83769C),
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = 3.dp.toPx()
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
                                .padding(end = 4.dp)
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

                                        fontSize = 20.sp,
                                        fontFamily =  FontFamily(
                                            Font(R.font.alcher_pixel),
                                            Font(R.font.alcher_pixel_bold)
                                        ),
                                        color = Color(0xFFFFF1E8),

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
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel),
                                        Font(R.font.alcher_pixel_bold)
                                    ),
                                    color = Color(0xFFFFF1E8)
                                )
                            }
                            for (time in 1..10) {
                                Row(
                                    Modifier
                                        .height(44.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = time.toString() + " pm",
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel),
                                            Font(R.font.alcher_pixel_bold)
                                        ),
                                        color = Color(0xFFFFF1E8)
                                    )
                                }
                            }
                        }
                    }

                    if(Calendar.HOUR_OF_DAY >= 8 && Calendar.HOUR_OF_DAY <= 22){
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()


                        ) {
                            drawCircle(
                                color = Color(0xFFFFF1E8),
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
//        val horizontal_dash_color = if(isSystemInDarkTheme()){
//            containerPurple.copy(0.6f)
//        }
//        else{
//            containerPurple
//        }

        val horizontal_dash_color = Color(0xFF83769C)
//        val yOffset = with(LocalDensity.current) { ((currentHour.value - 8f) * 44.dp.toPx()) + (currentMinute.value * (44.dp.toPx()/60f)) + 21.dp.toPx()}
        val cal = Calendar.getInstance()
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//        ){
//            drawLine(
//                color = horizontal_dash_color,
//                start = Offset(size.width, 0f),
//                end = Offset(size.width, size.height),
//                strokeWidth = 1.5.dp.toPx()
//            )
//        }

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
                    fontSize = 20.sp,
                    fontFamily = FontFamily(
                        Font(R.font.alcher_pixel),
                        Font(R.font.alcher_pixel_bold)
                    ),
                    color = Color(0xFFFFF1E8),
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
                            for (time in 7..21) {
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
                            (((event.eventdetail.starttime.hours - 8) * 44.2).toFloat() + (event.eventdetail.starttime.min.toFloat() * (44.2f / 60f)) + 20f)
                        val xdisinpxcald = with(LocalDensity.current) { (xdis).dp.toPx() }


//                        val yOffset = ((event.eventdetail.starttime.hours - 8) * 44) + 21
                        val height = (event.eventdetail.durationInMin / 60f) * 44.2
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
                                        color = Color(0xFF1D2B53),
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
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel),
                                        Font(R.font.alcher_pixel_bold)
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = Color(0xFFFFF1E8),
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                if (event.eventdetail.durationInMin/60 > 1) {
                                    Text(
                                        text = event.eventdetail.type,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel),
                                            Font(R.font.alcher_pixel_bold)
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color(0xFFFFF1E8)
                                    )
                                }
                            }
//                            Image(
//                                painter = painterResource(id = R.drawable.card_background_transparent),
//                                contentDescription = null,
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(horizontal = 4.dp)
//                            )
                        }
                    }

                    if(cal.get(Calendar.HOUR_OF_DAY) in 8..22){
                        Canvas(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()

                        ) {

                            drawLine(
                                color = horizontal_dash_color,
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
                strokeWidth = 1.dp.toPx()
            )
        }

    }

    fun FilterVenueEvents(venue: String, selectedDayEvents: List<eventWithLive>) : List<eventWithLive>{
        return selectedDayEvents.filter { data-> data.eventdetail.venue.replace("\\s".toRegex(), "").uppercase()== venue.replace("\\s".toRegex(), "").uppercase()}
    }

    @Preview
    @Composable
    fun MySchedulePreview() {
        mySchedule()
    }
}