package com.alcheringa.alcheringa2022

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.FragmentCompetitionsBinding
import com.alcheringa.alcheringa2022.ui.theme.*
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
    lateinit var eventfordes: eventWithLive
    lateinit var  scheduleDatabase:ScheduleDatabase



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
        mun=homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== "MUN".replace("\\s".toRegex(), "").uppercase()}

        voguenationlist=homeViewModel.allEventsWithLive.filter {
          data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== "VOGUE NATION".replace("\\s".toRegex(), "").uppercase()}

        classapartlist =homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== "CLASS APART".replace("\\s".toRegex(), "").uppercase()}

        anybodycandancelist=homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== "ANY BODY CAN DANCE".replace("\\s".toRegex(), "").uppercase()}

        musiclist=homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== "MUSIC".replace("\\s".toRegex(), "").uppercase()}

        literarylist=homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== "LITERARY".replace("\\s".toRegex(), "").uppercase()}

        arttalikieslist=homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== "ART TALKIES".replace("\\s".toRegex(), "").uppercase()}

        digitaldextiritylist=homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== "DIGITAL DEXTERITY".replace("\\s".toRegex(), "").uppercase()}

        lighcameraactionlist=homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== "LIGHTS CAMERA ACTION".replace("\\s".toRegex(), "").uppercase()}

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
        binding.constraintLayout.setOnClickListener{requireActivity().onBackPressed()}

        binding.competitionsCompose.setContent {
            MyContent()
        }
    }



    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Events_row(heading: String,events_list:List<eventWithLive>) {

       if (events_list.isNotEmpty()){
        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp) ){
            Text(text = heading.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = TextStyle(
                    color = colors.onBackground,
                    fontFamily = aileron,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        }


        Spacer(modifier = Modifier.height(24.dp))}
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

        // Creating a Bottom Sheet
        Alcheringa2022Theme {
            BottomSheetScaffold(

                scaffoldState = bottomSheetScaffoldState,
                sheetContent = {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.75f)
                            .border(4.dp, colors.secondary, RoundedCornerShape(40.dp, 40.dp))
                    ) {
                        Box(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Icon(
                                painterResource(id = R.drawable.rectangle_expand), "",
                                Modifier
                                    .width(60.dp)
                                    .height(5.dp), tint = Color(0xffacacac)
                            )
                        }
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
                                if (eventfordes.eventdetail.category.replace("\\s".toRegex(), "")
                                        .uppercase() == "Competitions".uppercase()
                                ) {
                                    Bottomviewcomp(eventWithLive = eventfordes)
                                } else {
                                    Bottomviewnewevent(eventWithLive = eventfordes)
                                }
                                Spacer(modifier = Modifier.height(0.dp))
                            }
                        }
                        if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                            eventButtons(eventWithLive = eventfordes)
                        }
                    }


                },
                sheetPeekHeight = 0.dp, sheetShape = RoundedCornerShape(40.dp, 40.dp),
                sheetBackgroundColor = colors.background,


                ) {
                    Alcheringa2022Theme {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                            /*.background(Color.Black)*/
                        ) {
                            Spacer(modifier = Modifier.height(70.dp))
                            Events_row(heading = "VOGUE NATION",voguenationlist)
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            ) { items(voguenationlist) { dataEach -> context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    homeViewModel,
                                    it,
                                    "artist"
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
                            } } }

                            Events_row(heading = "CLASS APART",classapartlist)
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            ) { items(classapartlist) { dataEach -> context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    homeViewModel,
                                    it,
                                    "artist"
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
                            } } }

                            Events_row(heading = "ANY BODY CAN DANCE",anybodycandancelist)
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            ) { items(anybodycandancelist) { dataEach -> context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    homeViewModel,
                                    it,
                                    "artist"
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
                            } } }

                            Events_row(heading = "MUSIC",musiclist)
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            ) { items(musiclist) { dataEach -> context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    homeViewModel,
                                    it,
                                    "artist"
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
                            } } }


                            Events_row(heading = "LITERARY",literarylist)
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            ) { items(literarylist) { dataEach -> context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    homeViewModel,
                                    it,
                                    "artist"
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
                            } } }


                            Events_row(heading = "ART TALKIES", arttalikieslist)
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            ) { items(arttalikieslist) { dataEach -> context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    homeViewModel,
                                    it,
                                    "artist"
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
                            } } }

                            Events_row(heading = "DIGITAL DEXTERITY",digitaldextiritylist)

                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            ) { items(digitaldextiritylist) { dataEach -> context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    homeViewModel,
                                    it,
                                    "artist"
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
                            } } }


                            Events_row(heading = "LIGHTS CAMERA ACTION",lighcameraactionlist)
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            ) { items(lighcameraactionlist) { dataEach -> context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    homeViewModel,
                                    it,
                                    "artist"
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
                            } } }


                            Events_row(heading = "MODEL UNITED NATIONS",mun)
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 20.dp)
                            ) { items(mun) { dataEach -> context?.let {
                                Event_card_Scaffold(
                                    eventdetail = dataEach,
                                    homeViewModel,
                                    it,
                                    "artist"
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
                            } } }
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
                .padding(horizontal = 12.dp)
        ) {
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(284.dp),
                    shape = RoundedCornerShape(28.dp),
                    border = BorderStroke(2.dp, colors.secondary)
                ) {
                    GlideImage(
                        imageModel = eventWithLive.eventdetail.imgurl,
                        contentDescription = "artist",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(284.dp),
                        //                circularReveal = CircularReveal(300),

                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        shimmerParams = ShimmerParams(
                            baseColor = Color.Black,
                            highlightColor = orangeText,
                            durationMillis = 350,
                            dropOff = 0.65f,
                            tilt = 20f
                        ), failure = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(), contentAlignment = Alignment.Center
                            ) {
                                val composition by rememberLottieComposition(
                                    LottieCompositionSpec.RawRes(
                                        R.raw.comingsoon
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

                        }

                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    ) {
                        Row {
                            Text(
                                text = eventWithLive.eventdetail.artist.uppercase(),
                                color = colors.onBackground,
                                fontWeight = FontWeight.Normal,
                                fontSize = 28.sp,
                                fontFamily = star_guard
                            )
                            if (eventWithLive.isLive.value) {
                                Box(
                                    modifier = Modifier
                                        .width(68.dp)
                                        .height(21.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(liveGreen),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "⬤ LIVE ",
                                        color = white,
                                        fontFamily = hk_grotesk, fontWeight = FontWeight.W500,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = eventWithLive.eventdetail.category,
                            style = TextStyle(
                                color = colors.onBackground,
                                fontFamily = aileron,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        )


                    }

                }
            }


        }
    }

    @Composable
    fun eventButtons(eventWithLive: eventWithLive){
        if (eventfordes.eventdetail.category.replace("\\s".toRegex(), "")
                .uppercase() == "Competitions".uppercase()
        ) {

            if (eventWithLive.isLive.value) {
                Button(
                    onClick = {
                        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.joinlink)))
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        orangeText
                    )
                ) {
                    Text(
                        text = "Join Event",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = clash,
                        color = Color.White
                    )

                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            else if (!eventWithLive.isLive.value && eventWithLive.eventdetail.stream){
                Button(
                    onClick = {},
                    Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xff4A4949)
                    )
                ) { val c=Calendar.getInstance()
                    if( (c.get(Calendar.YEAR)>2022) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)> Calendar.MARCH)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
                                (c.get(Calendar.DATE)> eventWithLive.eventdetail.starttime.date)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
                                (c.get(Calendar.DATE)== eventWithLive.eventdetail.starttime.date)and
                                ( ((eventWithLive.eventdetail.starttime.hours*60 + eventWithLive.eventdetail.durationInMin))
                                        <((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) ))

                    ){ Text(text="Event Finished!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = clash,
                        color = Color(0xffA3A7AC)
                    )}
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
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }


    @Composable
    fun Bottomviewcomp(eventWithLive:eventWithLive){
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
            if (true) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), Arrangement.Start)
                {
                    Image(
                        painter = painterResource(id = R.drawable.schedule),
                        contentDescription = null,
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${eventWithLive.eventdetail.starttime.date} Mar, ${if(eventWithLive.eventdetail.starttime.hours>12)"${eventWithLive.eventdetail.starttime.hours-12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min!=0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours>=12)"PM" else "AM"} ",
                        style = TextStyle(
                            color = colors.onBackground,
                            fontFamily = aileron,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text =eventfordes.eventdetail.descriptionEvent ,
                fontFamily = aileron,
                fontWeight = FontWeight.Normal,
                color = colors.onBackground,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(36.dp))
            if (eventWithLive.isLive.value) {
                Button(
                    onClick = {
                        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.joinlink)))
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        orangeText
                    )
                ) {
                    Text(
                        text = "Join Event",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = clash,
                        color = Color.White
                    )

                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            else if (!eventWithLive.isLive.value && eventWithLive.eventdetail.stream){
                Button(
                    onClick = {},
                    Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xff4A4949)
                    )
                ) { val c=Calendar.getInstance()
                    if( (c.get(Calendar.YEAR)>2022) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)> Calendar.MARCH)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
                                (c.get(Calendar.DATE)> eventWithLive.eventdetail.starttime.date)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
                                (c.get(Calendar.DATE)== eventWithLive.eventdetail.starttime.date)and
                                ( ((eventWithLive.eventdetail.starttime.hours*60 + eventWithLive.eventdetail.durationInMin))
                                        <((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) ))

                    ){ Text(text="Event Finished!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = clash,
                        color = Color(0xffA3A7AC)
                    )}
                    else if (c.get(Calendar.DATE)==eventWithLive.eventdetail.starttime.date){
                        Text(
                            text = "Event will be available on  ${if (eventWithLive.eventdetail.starttime.hours > 12)"${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min != 0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = clash,
                            color = Color(0xffA3A7AC)
                        )
                    }
                    else{
                        Text(
                            text = "Event will be available on day ${eventWithLive.eventdetail.starttime.date-11}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = clash,
                            color = Color(0xffA3A7AC)
                        )

                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

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
    fun Bottomviewnewevent(eventWithLive:eventWithLive){
        var isadded=remember{ mutableStateOf(false)}
        LaunchedEffect(key1=Unit,block = {
            isadded.value=homeViewModel.OwnEventsLiveState.any { data-> data.artist==eventWithLive.eventdetail.artist }

        })

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(30.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween)
//            {
//                Row(Modifier.wrapContentSize()) {
//
//
//                    Image(
//                        painter = if (eventWithLive.eventdetail.mode.uppercase().contains("ONLINE")) {
//                            painterResource(id = R.drawable.online)
//                        } else {
//                            painterResource(id = R.drawable.onground)
//                        },
//                        contentDescription = null,
//                        modifier = Modifier
//                            .width(16.dp)
//                            .height(16.dp),
//                        alignment = Alignment.Center,
//                        contentScale = ContentScale.Crop
//
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        text = eventWithLive.eventdetail.mode.uppercase(),
//                        style = TextStyle(
//                            color = colorResource(id = R.color.textGray),
//                            fontFamily = hk_grotesk,
//                            fontWeight = FontWeight.Normal,
//                            fontSize = 14.sp
//                        )
//                    )
//                }
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
            if (eventWithLive.eventdetail.stream) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), Arrangement.Start)
                {
                    Image(
                        painter = painterResource(id = R.drawable.schedule),
                        contentDescription = null,
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${eventWithLive.eventdetail.starttime.date} Mar, ${if(eventWithLive.eventdetail.starttime.hours>12)"${eventWithLive.eventdetail.starttime.hours-12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min!=0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours>=12)"PM" else "AM"} ",
                        style = TextStyle(
                            color = colorResource(id = R.color.textGray),
                            fontFamily = clash,
                            fontWeight = FontWeight.W500,
                            fontSize = 20.sp
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text =eventfordes.eventdetail.descriptionEvent ,
                fontFamily = hk_grotesk,
                fontWeight = FontWeight.W600,
                color = Color(0xffC7CCD1),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(36.dp))
            if (eventWithLive.isLive.value) {
                Button(
                    onClick = {
                        if(eventWithLive.eventdetail.joinlink!=""){
                            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.joinlink)))

                        }

                    },
                    Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        orangeText
                    )
                ) {
                    Text(
                        text = if(eventWithLive.eventdetail.joinlink=="")  "Running Offline" else "Join Event",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = clash,
                        color = Color.White
                    )

                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            else if (!eventWithLive.isLive.value && eventWithLive.eventdetail.stream){
                Button(
                    onClick = {},
                    Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xff4A4949)
                    )
                ) { val c=Calendar.getInstance()
                    if( (c.get(Calendar.YEAR)>2022) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)> Calendar.MARCH)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
                                (c.get(Calendar.DATE)> eventWithLive.eventdetail.starttime.date)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
                                (c.get(Calendar.DATE)== eventWithLive.eventdetail.starttime.date)and
                                ( ((eventWithLive.eventdetail.starttime.hours*60 + eventWithLive.eventdetail.durationInMin))
                                        <((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) ))

                    ){ Text(text="Event Finished!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = clash,
                        color = Color(0xffA3A7AC)
                    )}
                    else if (c.get(Calendar.DATE)==eventWithLive.eventdetail.starttime.date){
                        Text(
                            text = "Event will be available on  ${if (eventWithLive.eventdetail.starttime.hours > 12)"${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min != 0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = clash,
                            color = Color(0xffA3A7AC)
                        )
                    }
                    else{
                        Text(
                            text = "Event will be available on day ${eventWithLive.eventdetail.starttime.date-11}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = clash,
                            color = Color(0xffA3A7AC)
                        )

                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

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

//            Button(
//                onClick =   { startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.reglink)))}
//                ,
//                Modifier
//                    .fillMaxWidth()
//                    .height(55.dp),
//                shape = RoundedCornerShape(18.dp),
//                colors = ButtonDefaults.buttonColors(Color(0xff2B2B2B))
//            ) {
//                Text(
//                    text = "Register",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.W600,
//                    fontFamily = clash,
//                    color = Color.White
//                )
//            }
            Spacer(modifier = Modifier.height(24.dp))









        }
    }
}