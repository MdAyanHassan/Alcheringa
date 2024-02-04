package com.alcheringa.alcheringa2022

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.addNewItem
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.removeAnItem
import com.alcheringa.alcheringa2022.Model.stallModel
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.FragmentCompetitionsBinding
import com.alcheringa.alcheringa2022.ui.theme.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.R.id.action_home2_to_events_Details_Fragment
 * Use the [CompetitionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompetitionsFragment : Fragment() {




    private lateinit var binding: FragmentCompetitionsBinding
    val homeViewModel:viewModelHome by activityViewModels()
    lateinit var args: Bundle
    lateinit var Fm:FragmentManager
    lateinit var mun: List<eventWithLive>
    lateinit var voguenationlist: List<eventWithLive>
    lateinit var classapartlist: List<eventWithLive>
    lateinit var anybodycandancelist: List<eventWithLive>
    lateinit var musiclist: List<eventWithLive>
    lateinit var literarylist: List<eventWithLive>
    lateinit var arttalikieslist: List<eventWithLive>
    lateinit var digitaldextiritylist: List<eventWithLive>
    lateinit var lighcameraactionlist: List<eventWithLive>
    lateinit var sportslist: List<eventWithLive>
    lateinit var quizlist: List<eventWithLive>
    lateinit var upcomingcompetitions: List<eventWithLive>
    lateinit var otherlist: List<eventWithLive>
    lateinit var eventfordes: eventWithLive
    lateinit var scheduleDatabase:ScheduleDatabase
    lateinit var upcomingEvents: List<eventWithLive>
    lateinit var criticaldamageslist: List<eventWithLive>
    lateinit var proniteslist: List<eventWithLive>
    lateinit var proshowslist: List<eventWithLive>
    lateinit var creatorscampslist: List<eventWithLive>
    lateinit var humorfestslist: List<eventWithLive>
    lateinit var camppaignslist: List<eventWithLive>
    lateinit var otherEventlist: List<eventWithLive>
    lateinit var stallList: List<stallModel>
    var selecteedtile = 0
    val taglist= listOf("💃  Dance","🎵  Music", "🎭  Stagecraft", "🕶  Vogue nation", "🙋‍  Class apart", "🎨  Art talkies", "📖  Literature","💻  Digital Dexterity", "🎥  Lights camera action","🌐  Informal")


    val searchlist = mutableStateListOf<eventWithLive>()
    var tg= mutableStateOf("")
    var searchtext= mutableStateOf("")
    val complist = listOf("All", "Sports", "Quiz", "Music", "MUN", "Any Body Can Dance", "Vogue Nation", "Lights Camera Action", "Class Apart", "Literary", "Digital Dexterity", "Art Talkies", "Others")
    var selectedView by mutableStateOf(0)




//    val events=mutableListOf(
//
//        eventdetail(
//            "JUBIN NAUTIYAL",
//            "Pro Nights",
//            OwnTime(11,9,0),
//            "ONLINE", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fjubin.jpg?alt=media&token=90983a9f-bd0d-483d-b2a8-542c1f1c0acb"
//        ),
//
//        eventdetail(
//            "DJ SNAKE",
//            "Pro Nights",
//            OwnTime(12,12,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
//        ),
//        eventdetail(
//            "TAYLOR SWIFT",
//            "Pro Nights",
//            OwnTime(12,14,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
//        )
//        ,
//
//        eventdetail(
//            "DJ SNAKE2",
//            "Pro Nights",
//            OwnTime(12,10,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
//        ),
//        eventdetail(
//            "TAYLOR SWIFT2",
//            "Pro Nights",
//            OwnTime(12,15,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
//        )
//        ,
//        eventdetail(
//            "TAYLOR SWIFT3",
//            "Pro Nights",
//            OwnTime(12,14,30),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f"
//        )
//
//
//
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fm= parentFragmentManager
        lifecycleScope.launch{
            mun = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "MODEL UNITED NATIONS".replace("\\s".toRegex(), "").uppercase()
            }

            voguenationlist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "VOGUE NATION".replace("\\s".toRegex(), "").uppercase()
            }

            classapartlist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "CLASS APART".replace("\\s".toRegex(), "").uppercase()
            }

            anybodycandancelist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "ANY BODY CAN DANCE".replace("\\s".toRegex(), "").uppercase()
            }

            musiclist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "MUSIC".replace("\\s".toRegex(), "").uppercase()
            }

            literarylist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "LITERARY".replace("\\s".toRegex(), "").uppercase()
            }

            arttalikieslist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "ART TALKIES".replace("\\s".toRegex(), "").uppercase()
            }

            digitaldextiritylist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "DIGITAL DEXTERITY".replace("\\s".toRegex(), "").uppercase()
            }

            lighcameraactionlist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "LIGHTS CAMERA ACTION".replace("\\s".toRegex(), "").uppercase()
            }

            sportslist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "SPORTS".replace("\\s".toRegex(), "").uppercase()
            }
            otherlist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "OTHER COMPETITIONS".replace("\\s".toRegex(), "").uppercase()
            }
            quizlist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "QUIZ".replace("\\s".toRegex(), "").uppercase()
            }

            upcomingcompetitions = homeViewModel.upcomingEventsLiveState.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "COMPETITIONS".replace("\\s".toRegex(), "").uppercase()
            }


            criticaldamageslist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "Critical Damage".replace("\\s".toRegex(), "").uppercase()
            }

            proniteslist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "Pronites".replace("\\s".toRegex(), "").uppercase()
            }

            proshowslist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "Proshows".replace("\\s".toRegex(), "").uppercase()
            }

            creatorscampslist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "Creators' Camp".replace("\\s".toRegex(), "").uppercase()
            }

            humorfestslist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "Humor Fest".replace("\\s".toRegex(), "").uppercase()
            }

            camppaignslist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "Kartavya".replace("\\s".toRegex(), "").uppercase()
            }

            otherEventlist = homeViewModel.allEventsWithLive.filter { data ->
                data.eventdetail.type.replace(
                    "\\s".toRegex(),
                    ""
                ).uppercase() == "OTHER EVENTS".replace("\\s".toRegex(), "").uppercase()
            }
            upcomingEvents = homeViewModel.upcomingEventsLiveState.filter { data ->
                data.eventdetail.type.replace("\\s".toRegex(), "")
                    .uppercase() != "COMPETITIONS".replace("\\s".toRegex(), "").uppercase()
            }
            stallList = homeViewModel.stalllist
        }

        args = requireArguments()
        selecteedtile = args.getString("Tab")?.toInt() ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_events, container, false)
        binding = FragmentCompetitionsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val scheduleDatabase=ScheduleDatabase(context)
//        val eventslist=scheduleDatabase.schedule;
//        binding.account.setOnClickListener {
//            startActivity(Intent(context,Account::class.java));
//
//        }
        binding.backbtn2.setOnClickListener{requireActivity().onBackPressed()}

        binding.competitionsCompose.setContent {
            MyContent()
        }
    }



    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Events_row(heading: String,events_list:List<eventWithLive>) {
        val alphaval= 0.2f
        Spacer(modifier = Modifier.height(20.dp))
        if (events_list.isNotEmpty()){
            Box(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            ) {
                Box(
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

                            text = ""+heading+"  ",
                            fontFamily = aileron,
                            fontWeight = FontWeight.Bold,
                            color = Color.Transparent,
                            fontSize = 21.sp
                        )
                    }
                    Text(

                        text = heading,
                        fontFamily = aileron,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground,
                        fontSize = 21.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(0.dp))}
    }

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
        val viewModelHome: viewModelHome = viewModelHome()


        selectedView = selecteedtile


        Alcheringa2022Theme {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(colors.background)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(darkTealGreen)
                )
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.competitionfragmentbg),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Events",
                            fontFamily = futura,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = if (selectedView == 0) colors.onBackground else colors.onBackground.copy(
                                alpha = 0.4f
                            ),
                            modifier = Modifier.clickable(enabled = (selectedView != 0)) {
                                selectedView = 0
                                selecteedtile = 0
                            }
                        )
                        Text(
                            text = "Competitions",
                            fontFamily = futura,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = if (selectedView == 1) colors.onBackground else colors.onBackground.copy(
                                alpha = 0.4f
                            ),
                            modifier = Modifier.clickable(enabled = (selectedView != 1)) {
                                selectedView = 1
                                selecteedtile=1
                            }
                        )
                        Text(
                            text = "Stalls",
                            fontFamily = futura,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = if (selectedView == 2) colors.onBackground else colors.onBackground.copy(
                                alpha = 0.4f
                            ),
                            modifier = Modifier.clickable(enabled = (selectedView != 2)) {
                                selectedView = 2
                                selecteedtile=2
                            }
                        )
                    }
                }
                when(selectedView){
                    0 -> EventsView()
                    1 -> CompetitionsView()
                    2 -> StallsView()
                }
            }
        }

    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun CompetitionsView(){

        var expanded by remember { mutableStateOf(false) }
        val icon = if (expanded) {
            Icons.Filled.KeyboardArrowUp
        } else {
            Icons.Filled.KeyboardArrowDown
        }
        val selectedNames = remember { mutableStateListOf<String>("All") }

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .offset(y = -6.dp)) {

            if(upcomingcompetitions.isNotEmpty()) {
                Column(){
                    Text(
                        text = "Upcoming Events",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontWeight = FontWeight.Normal,
                        fontFamily = futura

                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(upcomingcompetitions) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            Spacer (modifier = Modifier.height(20.dp))
            Row (
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Column {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(300.dp)
                            .height(50.dp)
                            .background(colors.onSurface)
                            .border(
                                1.dp,
                                darkTealGreen,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        value = selectedNames.joinToString(", "),
                        onValueChange = {selectedNames},
                        readOnly = true, // Makes the TextField clickable
                        trailingIcon = {
                            Icon(
                                icon,
                                "",
                                Modifier.clickable { expanded = !expanded },
                                tint = colors.onBackground
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(colors.onBackground),
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(300.dp)
                            .background(colors.background)
                            .height(300.dp)
                    ) {
                        complist.forEach { item ->
                            val selected_already = selectedNames.contains(item)
                            DropdownMenuItem(
                                onClick = {
                                    if (selected_already) {
                                        selectedNames.remove(item)
                                    } else if (item == "All") {
                                        selectedNames.clear()
                                        selectedNames.add("All")
                                    } else {
                                        selectedNames.add(item)
                                        selectedNames.remove("All")
                                    }

                                    if (selectedNames.isEmpty()) {
                                        selectedNames.add("All")
                                    }
                                }
                            ) {
                                val isChecked = when {
                                    item == "All" -> selectedNames.contains("All")
                                    else -> selectedNames.contains(item)
                                }

                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = {
                                        if (isChecked) {
                                            selectedNames.remove(item)
                                        } else if (item == "All") {
                                            selectedNames.clear()
                                            selectedNames.add("All")
                                        } else {
                                            selectedNames.add(item)
                                            selectedNames.remove("All")
                                        }

                                        if (selectedNames.isEmpty()) {
                                            selectedNames.add("All")
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(uncheckedColor = darkTealGreen),
                                    modifier = Modifier
                                        .offset(x=-5.dp, y=0.dp)
                                )
                                Text(text = item, color = colors.onBackground)
                            }
                        }
                    }
                }
            }

            if(sportslist.isNotEmpty() && (selectedNames.contains("Sports") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Sports",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(sportslist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(quizlist.isNotEmpty() && (selectedNames.contains("Quiz") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Quiz",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(quizlist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(musiclist.isNotEmpty() && (selectedNames.contains("Music") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Music",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(musiclist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(mun.isNotEmpty() && (selectedNames.contains("MUN") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Model United Nations",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(mun) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(anybodycandancelist.isNotEmpty() && (selectedNames.contains("Any Body Can Dance") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Any Body Can Dance",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(anybodycandancelist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(voguenationlist.isNotEmpty() && (selectedNames.contains("Vogue Nation") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Vogue Nation",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(voguenationlist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(lighcameraactionlist.isNotEmpty() && (selectedNames.contains("Lights Camera Action") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Lights Camera Action",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(lighcameraactionlist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(classapartlist.isNotEmpty() && (selectedNames.contains("Class Apart") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Class Apart",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(classapartlist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(literarylist.isNotEmpty() && (selectedNames.contains("Literary") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Literary",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(literarylist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(digitaldextiritylist.isNotEmpty() && (selectedNames.contains("Digital Dexterity") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Digital Dexterity",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(digitaldextiritylist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(arttalikieslist.isNotEmpty() && (selectedNames.contains("Art Talkies") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Art Talkies",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(arttalikieslist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            if(otherlist.isNotEmpty() && (selectedNames.contains("Others") || selectedNames.contains("All"))) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    Text(
                        text = "Other Competitions",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(otherlist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }




        }
    }

    @Composable
    fun EventsView(){
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .offset(y = -6.dp)) {

            if(upcomingEvents.isNotEmpty()) {
                Column(){
                    Text(
                        text = "Upcoming Events",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(upcomingcompetitions) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            Spacer (modifier = Modifier.height(20.dp))
            if(proniteslist.isNotEmpty()) {
                Column(){
                    Text(
                        text = "Pronites",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(proniteslist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if(proshowslist.isNotEmpty()) {
                Column(){
                    Text(
                        text = "Proshows",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(proshowslist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            if(creatorscampslist.isNotEmpty()) {
                Column(){
                    Text(
                        text = "Creators' Camp",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(creatorscampslist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if(humorfestslist.isNotEmpty()) {
                Column(){
                    Text(
                        text = "Humor Fest",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(humorfestslist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if(camppaignslist.isNotEmpty()) {
                Column(){
                    Text(
                        text = "Kartavya",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(camppaignslist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if(otherEventlist.isNotEmpty()) {
                Column(){
                    Text(
                        text = "Other Events",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 26.dp,bottom = 10.dp),
                        color = colors.onBackground,
                        fontFamily = futura
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(otherEventlist) { dataEach ->
                            context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    viewModelHm = homeViewModel,
                                    context = it,
                                    artist = "artist"
                                ) {
                                    val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                    NavHostFragment
                                        .findNavController(this@CompetitionsFragment)
                                        .navigate(R.id.action_competitionsFragment_to_events_Details_Fragment, arguments);

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun StallsView(){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Stalls",
                color = colors.onBackground,
                fontFamily = futura,
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 26.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(25.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(stallList.size) {index->
                    context?.let { 
                        StallCard(stallDetail = stallList[index]){
                            val arguments = bundleOf("stallName" to stallList[index].name)
                            NavHostFragment
                                .findNavController(this@CompetitionsFragment)
                                .navigate(R.id.action_competitionsFragment_to_stallDetails, arguments);
                        }
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
                        requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC) },
                        imageModel = eventWithLive.eventdetail.imgurl,
                        contentDescription = "artist",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(297.dp),
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
                                        if (isSystemInDarkTheme())R.raw.comingsoondark else R.raw.comingsoonlight
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
        val isFinished = (c.get(Calendar.YEAR)>2023) or
                ((c.get(Calendar.YEAR)==2023) and
                        (c.get(Calendar.MONTH)> Calendar.FEBRUARY)) or
                ((c.get(Calendar.YEAR)==2023) and
                        (c.get(Calendar.MONTH)== Calendar.FEBRUARY) and
                        (c.get(Calendar.DATE)> eventWithLive.eventdetail.starttime.date)) or
                ((c.get(Calendar.YEAR)==2023) and
                        (c.get(Calendar.MONTH)== Calendar.FEBRUARY) and
                        (c.get(Calendar.DATE)== eventWithLive.eventdetail.starttime.date)and
                        ( ((eventWithLive.eventdetail.starttime.hours*60 + eventWithLive.eventdetail.durationInMin))
                                <((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) ))
        var v = venuelist.find { it.name.replace("\\s".toRegex(), "").uppercase() == eventWithLive.eventdetail.venue.replace("\\s".toRegex(), "").uppercase() }

        if ( // TODO: replace with below check, commented out temporarily for demonstrations

            eventWithLive.eventdetail.category.replace("\\s".toRegex(), "")
                .uppercase() == "Competitions".uppercase()
        )
        {
            if (isFinished){
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

                    Button(
                        onClick = {
                            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.reglink)))
                        },
                        Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .weight(1f),
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

                    Button(
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

                    }


                }
            }
        }
    }


    @Composable
    fun Bottomviewcomp(eventWithLive:eventWithLive){
        val c=Calendar.getInstance()

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
                        painter = if(isSystemInDarkTheme()) {
                            painterResource(id = R.drawable.locationpin_dark)} else {
                            painterResource(id = R.drawable.locationpin_light)},
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
                        text = "${eventWithLive.eventdetail.starttime.date} Feb, ${if (eventWithLive.eventdetail.starttime.hours > 12) "${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min != 0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"} ",
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
                                    scheduleDatabase.DeleteItem(eventWithLive.eventdetail.artist, context)
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
    @Composable
    fun tags(){

        Row(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .wrapContentHeight()
            , horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            val colors= colors
            val isdark= isSystemInDarkTheme()
            val selectedbgcolor=if (isdark) Color(0xff79C3D2) else Color(0xffCDE9EE)
            val selectdbordercolor=if (isdark) Color(0xff034653) else Color(0xff7DC5D3)

            Spacer(modifier = Modifier.width(15.dp))
            taglist.forEach {
                var bgcolor=remember{ mutableStateOf(colors.background) }
                var bordercolor=remember{ mutableStateOf(Color(0xffacacac)) }

                if(tg.value==it){
                    bgcolor.value=selectedbgcolor
                    bordercolor.value=selectdbordercolor

                }
                else
                {
                    bgcolor.value=colors.background
                    bordercolor.value=Color(0xffacacac)
                }


                Card(
                    Modifier
                        .clickable {
                            if (tg.value == it) {
                                tg.value = "";
                                bgcolor.value = colors.background
                                bordercolor.value = Color(0xffacacac)
                            } else {
                                tg.value = it;
                                bgcolor.value = selectedbgcolor
                                bordercolor.value = selectdbordercolor
                            }

                            filterlist()
                        }
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    border = BorderStroke(1.dp, bordercolor.value),
                    shape = RoundedCornerShape(45.dp),
                    backgroundColor = colors.background,
                )
                {
                    Box(modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .background(bgcolor.value), contentAlignment = Alignment.Center) {

                        val textcolor=if (isdark) {if(tg.value==it)colors.background else colors.onBackground}else colors.onBackground
                        Text(modifier=Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            text = it,
                            fontSize = 14.sp,
                            fontFamily = aileron,
                            fontWeight = FontWeight.Normal,
                            color = textcolor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
        }

    }


    fun filterlist(){
        searchlist.clear();
        searchlist.addAll(homeViewModel.allEventsWithLive.filter {it.eventdetail.category.replace(
            "\\s".toRegex(),
            ""
        ).uppercase() == "Competitions".uppercase() && it.eventdetail.toString().contains(searchtext.value,true) && it.eventdetail.toString().contains(tg.value.drop(3).trim(),true) })

        //zooming map at the first event venue
//        val firsteventvenue= venuelist.find { it.name==searchlist[0].eventdetail.venue }
//        if(searchlist.isNotEmpty() && firsteventvenue!=null )
//            cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition(venuelist.random().LatLng, 16f,0f,0f)))
//    }
    }

}