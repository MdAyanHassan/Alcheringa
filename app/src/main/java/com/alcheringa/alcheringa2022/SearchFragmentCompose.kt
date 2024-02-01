package com.alcheringa.alcheringa2022


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.InformalModel
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.eventdetail
import com.alcheringa.alcheringa2022.Model.stallModel
import com.alcheringa.alcheringa2022.Model.utilityModel
import com.alcheringa.alcheringa2022.Model.venue
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.FragmentEventsBinding
import com.alcheringa.alcheringa2022.ui.theme.aileron
import com.alcheringa.alcheringa2022.ui.theme.black
import com.alcheringa.alcheringa2022.ui.theme.creamWhite
import com.alcheringa.alcheringa2022.ui.theme.darkBar
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.grey
import com.alcheringa.alcheringa2022.ui.theme.highBlack
import com.alcheringa.alcheringa2022.ui.theme.highWhite
import com.alcheringa.alcheringa2022.ui.theme.midWhite
import com.alcheringa.alcheringa2022.ui.theme.textbg
import com.alcheringa.alcheringa2022.ui.theme.white
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.model.LatLng
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentEventsBinding
    val taglist = listOf(
        "Dance",
        "Music",
        "Stagecraft",
        "Vogue nation",
        "Class apart",
        "Art talkies",
        "Literature",
        "Digital Dexterity",
        "Lights camera action",
        "Informals",
        "Stalls"
    )
    lateinit var criticaldamageslist: List<eventWithLive>
    lateinit var proniteslist: List<eventWithLive>
    lateinit var proshowslist: List<eventWithLive>
    lateinit var creatorscampslist: List<eventWithLive>
    lateinit var humorfestslist: List<eventWithLive>
    lateinit var camppaignslist: List<eventWithLive>
    lateinit var mun: List<eventWithLive>
    lateinit var otherlist: List<eventWithLive>
    val searchlist = mutableStateListOf<eventWithLive>()
    var markerList = mutableStateListOf<venue>()
    var tg = mutableStateOf("")
    val homeViewModel: viewModelHome by activityViewModels()
    var searchtext = mutableStateOf("")
    private lateinit var fgm: FragmentManager
    var informals = mutableStateListOf<InformalModel>()
    lateinit var suggestions: List<eventWithLive>
    var stalls = mutableStateListOf<stallModel>()

    fun filterlist() {
        searchlist.clear();
        searchlist.addAll(homeViewModel.allEventsWithLive.filter {
//            it.eventdetail.toString().contains(searchtext.value, true) && it.eventdetail.toString()
//                .contains(tg.value.drop(3).trim(), true)
            (it.eventdetail.artist+ it.eventdetail.category + it.eventdetail.genre.toString() + it.eventdetail.type + it.eventdetail.venue).contains(searchtext.value , true) && (it.eventdetail.artist.toString() + it.eventdetail.category.toString() + it.eventdetail.genre.toString() + it.eventdetail.type.toString())
                .contains(tg.value , true)
        })

        informals.clear()
        informals.addAll(homeViewModel.informalList.filter {
            (it.name + "Informals").contains(searchtext.value , true) && (it.name + "Informals").contains(tg.value , true)
        })

        Log.d("INFORMALS" , "informal list is: ${informals}")

        stalls.clear()
        stalls.addAll(homeViewModel.stalllist.filter {
            (it.name + "Stalls").contains(searchtext.value , true) && (it.name + "Stalls").contains(tg.value , true)
        })
        Log.d("STALLS" , "stalls list is: ${stalls}")
        //zooming map at the first event venue
//        val firsteventvenue= venuelist.find { it.name==searchlist[0].eventdetail.venue }
//        if(searchlist.isNotEmpty() && firsteventvenue!=null ){
//            cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition(venuelist.random().LatLng, 16f,0f,0f)))
//    }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fgm = parentFragmentManager

        venuelist.forEach{d ->
            markerList.add(d)
        }

        if(homeViewModel.utilityList.isEmpty()){
            homeViewModel.getUtilities()
        }
        if(homeViewModel.informalList.isEmpty()){
            homeViewModel.getInformals()
        }
        Log.d("Informals Test", homeViewModel.informalList.size.toString())

        suggestions = (homeViewModel.allEventsWithLive).shuffled().take(5)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_events, container, false)
        binding = FragmentEventsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scheduleDatabase = ScheduleDatabase(context)
//        val eventslist = scheduleDatabase.schedule;


        binding.eventsCompose.setContent {


                SearchFragmentCompose()



        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun SearchFragmentCompose() {
        val coroutineScope = rememberCoroutineScope()
        val keyboardController = LocalSoftwareKeyboardController.current
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState =
            BottomSheetState(BottomSheetValue.Collapsed)
        )

        Alcheringa2022Theme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colors.background),
            ) {
                // Search Bar at the top
                Row(modifier = Modifier.fillMaxWidth()) {
                    val id = if(isSystemInDarkTheme()) {R.drawable.vector_26} else { R.drawable.vector_26__2_}

                    Image(
                        painter = painterResource(id = id),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterVertically)
                            .weight(0.25f)
                            .padding(start = 16.dp)
                            .clickable {
                                NavHostFragment
                                    .findNavController(this@SearchFragment)
                                    .navigate(
                                        //R.id.action_home2_to_events_Details_Fragment,
                                        R.id.action_searchFragment_to_home_nav,
                                        arguments
                                    )
                            },
                    )
                    TextField(
                        value = searchtext.value,
                        onValueChange = { v: String -> searchtext.value = v ; filterlist()},
                        placeholder = { Text("Search for events, competitions, etc " , modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth()
                            .weight(3f) , fontSize = 12.sp , color = colors.onBackground)},
                        trailingIcon = {

                            Box(modifier = Modifier
                                .weight(1f)
                                .size(50.dp)) {
                                Divider(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(1.dp)
                                        .align(Alignment.CenterStart),
                                    color = grey,

                                    )
                                if(searchtext.value != "") {
                                    Icon(
                                        Icons.Outlined.Close,
                                        "",
                                        tint = colors.onBackground,
                                        modifier = Modifier
                                            .clickable {
                                                searchtext.value = ""
                                                markerList.clear()
                                                for (venue in venuelist) {
                                                    markerList.add(venue)
                                                }
                                            }
                                            .align(Alignment.CenterEnd)
                                            .padding(end = 16.dp),
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                            .padding(end = 16.dp),
                                        tint = colors.onBackground

                                    )
                                }

                            }
                        },
                        modifier = Modifier
                            .background(
                                color = colors.background,
                                shape = RoundedCornerShape(2.dp)
                            )
                            .padding(16.dp)
                            .height(50.dp)
                            .weight(3f)
                            .wrapContentSize()
                            .border(1.dp, colors.onBackground, RoundedCornerShape(2.dp)),
                        colors = TextFieldDefaults.textFieldColors(textColor = colors.onBackground,cursorColor = colors.onBackground,focusedIndicatorColor = Color.Transparent),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide();filterlist()}),
                        singleLine = true,
                    )
                }

                Box(modifier = Modifier.height(60.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    tags()
                }

//                Column() {
//                    LazyRow(horizontalArrangement = Arrangement.SpaceBetween) {
//                        items(searchlist) { dataEach ->
//                            context?.let {
//                                Event_card(
//                                    eventdetail = dataEach,
//                                    homeViewModel,
//                                    it,
//                                    this@SearchFragment,
//                                    fgm,
//                                    R.id.action_events_to_events_Details_Fragment2
//                                )
//                            }
//                        }
//                    }
//                }
                Full_view(bottomSheetScaffoldState,coroutineScope)
            }
        }
    }


    @Composable
    fun tags() {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .wrapContentHeight(), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val colors = colors
            val isdark = isSystemInDarkTheme()
            val selectedbgcolor = darkTealGreen
            val selectdbordercolor = if (isdark) creamWhite else darkBar

            Spacer(modifier = Modifier.width(15.dp))

            taglist.forEach {
                var bgcolor = remember { mutableStateOf(colors.background) }
                var bordercolor = remember { mutableStateOf(colors.onBackground) }

                if (tg.value == it) {
                    bgcolor.value = selectedbgcolor
                    bordercolor.value = selectdbordercolor

                } else {
                    bgcolor.value = colors.background
                    bordercolor.value = colors.onBackground
                }


                Card(
                    Modifier
                        .clickable {

                            if (tg.value == it) {
                                tg.value = "";
                                bgcolor.value = colors.background
                                bordercolor.value = colors.onBackground
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
                    shape = RoundedCornerShape(5.dp),
                    backgroundColor = colors.background,
                )
                {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .background(bgcolor.value), contentAlignment = Alignment.Center
                    ) {

                        val textcolor = if (isdark) {
                            if (tg.value == it) colors.onBackground else colors.onBackground
                        } else colors.onBackground
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            text = it,
                            fontSize = 14.sp,
                            fontFamily = futura,
                            fontWeight = FontWeight.Normal,
                            color = textcolor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
        }

    }
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Full_view(bottomSheetScaffoldState: BottomSheetScaffoldState ,coroutineScope: CoroutineScope) {
        Alcheringa2022Theme {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                /*.background(Color.Black)*/
            ) {

                
                if(searchtext.value != "" || tg.value != "") {
                    Informal_row(heading = "Informals", list = informals, coroutineScope = coroutineScope)
                    Stalls_row(heading = "Stalls", list = stalls)
                    searchresultrow(bottomSheetScaffoldState = bottomSheetScaffoldState, coroutineScope = coroutineScope,heading = "Search Results")
                    
                } else {
                    if(suggestions.isNotEmpty()) {
                        Events_row(heading = "Suggestions", list = suggestions)
                    }
                }
//                if(homeViewModel.informalList.isNotEmpty()){
//                    Informal_row(heading = "Informals", list = homeViewModel.informalList,  coroutineScope)

//                    if (criticaldamageslist.isNotEmpty()) {
//                        Events_row(heading = "Critical Damage", criticaldamageslist)
//                    }
//                    if (proniteslist.isNotEmpty()) {
//                        Events_row(heading = "Pronites", proniteslist)
//                    }
//                    if (proshowslist.isNotEmpty()) {
//                        Events_row(heading = "Proshows", proshowslist)
//                    }
//                    if (creatorscampslist.isNotEmpty()) {
//                        Events_row(heading = "Creators' Camp", creatorscampslist)
//                    }
//                    if (humorfestslist.isNotEmpty()) {
//                        Events_row(heading = "Humor Fest", humorfestslist)
//                    }
//                    if (camppaignslist.isNotEmpty()) {
//                        Events_row(heading = "Kartavya", camppaignslist)
//                    }
//                    if (mun.isNotEmpty()) {
//                        Events_row(heading = "Model United Nations", mun)
//                    }
//                    if (otherlist.isNotEmpty()) {
//                        Events_row(heading = "Other Events", list = otherlist)
//                    }
//



//                Column(modifier =Modifier.padding(horizontal = 20.dp, vertical = 12.dp) ){
//                    Box() {val alphaval= 0.2f
//                        Card(
//                            Modifier
//                                .height(10.dp)
//                                .offset(x = -5.dp, y = 16.dp)
//                                .alpha(alphaval),
//                            shape = RoundedCornerShape(100.dp),
//                            backgroundColor = textbg
//
//                        ){
//                            Text(
//
//                                text = "Competitions  ",
//                                fontFamily = aileron,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.Transparent,
//                                fontSize = 21.sp
//                            )
//                        }
//                        Text(
//
//                            text = "Competitions",
//                            fontFamily = aileron,
//                            fontWeight = FontWeight.Bold,
//                            color = colors.onBackground,
//                            fontSize = 21.sp
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(14.dp))
//                    imgcomp()
//
//                }


            }
        }
    }

    @Composable
    fun Events_row(heading: String, list: List<eventWithLive>) {
        val alphaval = 0.2f
        if (list.isNotEmpty()) {
            Box(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            ) {
                Box(
                ) {
                    Text(

                        text = heading,
                        fontFamily = futura,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground,
                        fontSize = 21.sp
                    )
                }
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(list)
                { dataEach ->
                    context?.let {
                        Event_card_Scaffold(
                            eventdetail = dataEach,
                            homeViewModel,
                            it,
                            "",
                            onCardClick = {
                                val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                NavHostFragment
                                    .findNavController(this@SearchFragment)
                                    .navigate(R.id.action_searchFragment_to_events_Details_Fragment, arguments);
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    @Composable
    fun Stalls_row(heading: String, list: List<stallModel>) {
        val alphaval = 0.2f
        if (list.isNotEmpty()) {
            Box(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            ) {
                Text(

                        text = heading,
                        fontFamily = futura,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground,
                        fontSize = 21.sp
                    )

            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(list)
                { dataEach ->
                    context?.let {
                       stallCard(stallDetail = dataEach)
                        
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun stallCard(stallDetail: stallModel) {
        Card(
            modifier = Modifier
                .width(155.dp)
                .height(175.dp),
            backgroundColor = colors.onBackground,
            onClick = {
                val arguments = bundleOf("stallName" to stallDetail.name)
                NavHostFragment
                    .findNavController(this@SearchFragment)
                    .navigate(R.id.action_searchFragment_to_stallDetails, arguments);
            }
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ){
//                    Image(
//                        painter = painterResource(id = imageResource),
//                        contentDescription = null,
//                        contentScale = ContentScale.FillBounds,
//                        alignment = Alignment.Center
//                    )
                    GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(
                        DiskCacheStrategy.AUTOMATIC)},
                        modifier = Modifier,
                        imageModel = stallDetail.imgurl,
                        contentDescription = "stallName",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        shimmerParams = ShimmerParams(
                            baseColor = if(isSystemInDarkTheme()) black else highWhite,
                            highlightColor = if(isSystemInDarkTheme()) highBlack else white,
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
                            }

                        }
                    )
                }

                Row (
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stallDetail.name,
                        color = colors.background,
                        fontSize = 18.sp,
                        fontFamily = futura,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Informal_row(heading: String, list: List<InformalModel>, coroutineScope: CoroutineScope) {
        val alphaval = 0.2f
        if (list.isNotEmpty()) {
            Box(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            ) {
                Text(

                    text = heading,
                    fontFamily = futura,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    fontSize = 21.sp
                )

            }
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(list)
                { dataEach ->
                    context?.let {
                        InformalCard(dataEach, context = context!!) {
                            markerList.clear()
                            val loc = venue(dataEach.name, LatLng(dataEach.location.latitude, dataEach.location.longitude))
                            markerList.add(loc)

                           // coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun searchresultrow(heading: String , bottomSheetScaffoldState: BottomSheetScaffoldState , coroutineScope: CoroutineScope) {

        if (searchlist.isNotEmpty()) {
            Box(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            ) {
                Text(

                    text = heading,
                    fontFamily = futura,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    fontSize = 21.sp
                )

            }
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(searchlist)
                { dataEach ->
                    context?.let {
 //                       Event_card(
//                            eventdetail = dataEach,
//                            homeViewModel,
//                            it,
//                            this@SearchFragment,
//                            fgm,
//                            R.id.action_searchFragment_to_events_Details_Fragment
//                        )

                        Event_card_Scaffold(
                            eventdetail = dataEach,
                            homeViewModel,
                            it,
                            "",
                            onCardClick = {
                                val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                                NavHostFragment
                                    .findNavController(this@SearchFragment)
                                    .navigate(R.id.action_searchFragment_to_events_Details_Fragment, arguments);
                            }
                        )

                    }
                }
            }
        } else {
            if(informals.isEmpty() && stalls.isEmpty()) {
                Box(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    )
                ) {
                    Text(

                        text = heading,
                        fontFamily = futura,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground,
                        fontSize = 21.sp
                    )

                }
                Box(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                    )
                ) {
                    Text(
                        text = "No results found for \"${searchtext.value} ${tg.value}\"",
                        color = colors.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = futura
                    )
                }
            }
        }

    }


    @Preview
    @Composable
    fun SearchFragmentPreview() {
        SearchFragmentCompose()

    }
}





