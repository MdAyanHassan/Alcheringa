package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.*
import com.alcheringa.alcheringa2022.databinding.FragmentEventsBinding

import com.alcheringa.alcheringa2022.databinding.FragmentSchedule2024Binding
import com.alcheringa.alcheringa2022.ui.theme.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//val venuelist = listOf<venue>(
//    venue("Auditorium", LatLng = LatLng(26.190638584006965, 91.69300641296921)),
//    venue("Mini Auditorium", LatLng = LatLng(26.190638584006965, 91.69300641296921)),
//    venue("Expo Stage", LatLng = LatLng(26.190636674443564, 91.69300170707142)),
//    venue("Front of Graffiti Wall", LatLng = LatLng(26.19191890474476, 91.6959432542479)),
//    venue("Behind Graffiti Wall", LatLng = LatLng(26.191951998262304, 91.69573068917616)),
//    venue("Football Field", LatLng = LatLng(26.193022124942964, 91.69694866004637)),
//    venue("Basketball Courts", LatLng = LatLng(26.19204463910141, 91.69785644684545)),
//    venue("Volley Ball Court", LatLng = LatLng(26.19204023599347, 91.69737065823848)),
//    venue("Library Shed", LatLng = LatLng(26.189551715246374, 91.69337795595325)),
//    venue("Lecture Hall 1", LatLng = LatLng(26.189005409728654, 91.69174350293444)),
//    venue("Lecture Hall 2", LatLng = LatLng(26.189005409728654, 91.69174350293444)),
//    venue("Lecture Hall 3", LatLng = LatLng(26.189005409728654, 91.69174350293444)),
//    venue("Lecture Hall 4", LatLng = LatLng(26.189005409728654, 91.69174350293444)),
//    venue("Rocko Stage", LatLng = LatLng(26.189865803005024, 91.693858248184)),
//    venue("Pronite Stage", LatLng = LatLng(26.1897968961693, 91.697434871485)),
//    venue("Conference Hall 1", LatLng = LatLng(26.190873224608982, 91.69225523976387)),
//    venue("Conference Hall 2", LatLng = LatLng(26.190873224608982, 91.69225523976387)),
//    venue("Conference Hall 3", LatLng = LatLng(26.190873224608982, 91.69225523976387)),
//    venue("Conference Hall 4", LatLng = LatLng(26.190873224608982, 91.69225523976387)),
//    venue("Core 5", LatLng = LatLng(26.18611265371866, 91.68929712769904)),
//    venue("Senate Hall", LatLng = LatLng(26.190302521884597, 91.69210573526523)),
//    venue("Old Sac Wall", LatLng = LatLng(26.192563559521556, 91.69583138918674)),
//    venue("Audi Park", LatLng = LatLng(26.190218676220958, 91.69300182135102)),
//    venue("Athletics Field", LatLng = LatLng(26.19290020378757, 91.69813175683815)),
//    venue("Entire Campus", LatLng = LatLng(26.190213221922317, 91.6929932588851)),
//    venue("Library Basement", LatLng = LatLng(26.18950374874834, 91.6934319403298)),
//)




class Events : Fragment() {

    private lateinit var fgm: FragmentManager
    private lateinit var binding: FragmentEventsBinding
    val homeViewModel: viewModelHome by activityViewModels()

    var firebaseFirestore: FirebaseFirestore? = null
    var sharedPreferences: SharedPreferences? = null

    val taglist = listOf(
        "🚽  Utilities",
        "💃  Dance",
        "🎵  Music",
        "🎭  Stagecraft",
        "🕶  Vogue nation",
        "🙋‍  Class apart",
        "🎨  Art talkies",
        "📖  Literature",
        "💻  Digital Dexterity",
        "🎥  Lights camera action",
        "🌐  Informal"
    )

    var markerList = mutableStateListOf<venue>()

    val selectedVenue = mutableStateOf("")
    var selectedVenueEvents = mutableStateListOf<eventWithLive>()
    val selectedVenueInformals = mutableListOf<InformalModel>()
    var selectedVenue1 = mutableStateListOf<venue>(venue("", LatLng(26.191117262340942, 91.69295134231831), "", ""))

    var venueslist = mutableStateListOf<venue>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fgm = parentFragmentManager

        venueslist = homeViewModel.venuesList
        venueslist.forEach{d ->
            markerList.add(d)
        }

        if(homeViewModel.utilityList.isEmpty()){
            homeViewModel.getUtilities()
        }
        if(homeViewModel.informalList.isEmpty()){
            homeViewModel.getInformals()
        }
        Log.d("Informals Test", homeViewModel.informalList.size.toString())

        try{
            selectedVenue1[0].name = requireArguments().getString("venue", "")
            filterWithLocation()
        } catch (e: IllegalStateException){
            Log.d("Events", "No argument")
        }


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
        val eventslist = scheduleDatabase.schedule;

//        var unseen_notif_count = 0
//
//        firebaseFirestore = FirebaseFirestore.getInstance()
//        sharedPreferences = activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)
//
//        firebaseFirestore!!.collection("Notification").get().addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot> ->
//            if (task.isSuccessful) {
//                val notifs = task.result.size()
//                Log.d("Notification Count", "No of notifications: " + notifs)
//                val seen_notifs = sharedPreferences?.getInt("seen_notifs_count", 0)
//                Log.d("seen notification", seen_notifs.toString());
//                unseen_notif_count = notifs - seen_notifs!!
//
//                if (unseen_notif_count <= 0) {
//                    binding.notificationCount.visibility = View.INVISIBLE
//                } else if (unseen_notif_count <= 9) {
//                    binding.notificationCount.visibility = View.VISIBLE
//                    binding.notificationCount.text = unseen_notif_count.toString()
//                } else {
//                    binding.notificationCount.visibility = View.VISIBLE
//                    binding.notificationCount.text = "9+"
//                }
//            } else {
//                Log.d("Error", "Error loading notification count", task.exception)
//            }
//        })
//
        binding.account.setOnClickListener {
            (activity as MainActivity).drawer.openDrawer(Gravity.RIGHT)
//
        }
//        binding.pass.setOnClickListener{
//            startActivity(Intent(context, NotificationActivity::class.java));
//        }

        binding.search.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_events_to_searchFragment)
        }

        binding.eventsCompose.setContent {

            Alcheringa2022Theme() {
                MyContent()
            }


        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
    @Composable
    fun Full_view(bottomSheetScaffoldState: BottomSheetScaffoldState, coroutineScope: CoroutineScope) {
        Alcheringa2022Theme {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = selectedVenue1[0].name,
                    fontFamily = futura,
                    fontWeight = FontWeight.Medium,
                    fontSize = 26.sp,
                    color = colors.onBackground,
                    modifier = Modifier

                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        rememberImagePainter(data = selectedVenue1[0].imgurl),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2.5f),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(28.dp))
                if(selectedVenueEvents.isNotEmpty()){
                    Events_row(heading = "Events in this area", list = selectedVenueEvents)
                }
                else{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "No Upcoming events at current location",
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            fontFamily = futura,
                            color = colors.onBackground.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                0f to containerPurple,
                                1f to borderdarkpurple
                            ),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(6.dp),
                            color = colors.onBackground
                        )
                    ,
                    contentAlignment = Alignment.Center
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(horizontal = 28.dp, vertical = 18.dp)
                            .clickable {
                                val venue = venueslist.filter {
                                    it.name == selectedVenue1[0].name
                                }[0]
                                val gmmIntentUri =
                                    Uri.parse("google.navigation:q=${venue.LatLng.latitude},${venue.LatLng.longitude}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                startActivity(mapIntent)
                            }

                        ,


                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Directions on Map",
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = futura,
                                color = Color.White,
                                modifier=Modifier
                            )
                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp),
                                color = Color.White
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.direction),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f),
                                tint = Color.White

                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(44.dp))

            }
        }
    }

    @Composable
    fun Events_row(heading: String, list: List<eventWithLive>) {
        val alphaval = 0.2f
        if (list.isNotEmpty()) {
            Box(
                modifier = Modifier.padding(
                    horizontal = 24.dp  
                )
            ) {
                Text(

                    text = heading,
                    fontFamily = futura,
                    fontWeight = FontWeight.Normal,
                    color = colors.onBackground,
                    fontSize = 22.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
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
                            viewModelHm = homeViewModel,
                            context = requireContext(),
                            artist = ""
                        ) {
                            val arguments = bundleOf("Artist" to dataEach.eventdetail.artist)

                            NavHostFragment
                                .findNavController(this@Events)
                                .navigate(R.id.action_events_to_events_Details_Fragment2, arguments);

                        }
                    }
                }
            }

//            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Utility_row(heading: String, list: List<utilityModel>, bottomSheetScaffoldState: BottomSheetScaffoldState, coroutineScope: CoroutineScope) {
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
                    Card(
                        Modifier
                            .height(10.dp)
                            .offset(x = -5.dp, y = 16.dp)
                            .alpha(alphaval),
                        shape = RoundedCornerShape(100.dp),
                        backgroundColor = textbg

                    ) {
                        Text(

                            text = "" + heading + "  ",
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

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(list)
                { dataEach ->
                    context?.let {
                        UtilityCard(utlt = dataEach) {
                            markerList.clear()
                            dataEach.locations.forEachIndexed{ i, d ->
                                val loc = venue(dataEach.name.dropLast(1) + " " + (i+1), LatLng(d.latitude, d.longitude))
                                markerList.add(loc)
                            }
                            coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Informal_row(heading: String, list: List<InformalModel>, bottomSheetScaffoldState: BottomSheetScaffoldState, coroutineScope: CoroutineScope) {
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
                    Card(
                        Modifier
                            .height(10.dp)
                            .offset(x = -5.dp, y = 16.dp)
                            .alpha(alphaval),
                        shape = RoundedCornerShape(100.dp),
                        backgroundColor = textbg

                    ) {
                        Text(

                            text = "" + heading + "  ",
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

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(list)
                { dataEach ->
                    context?.let {
                        InformalCard(dataEach) {
                            markerList.clear()
                            val loc = venue(dataEach.name, LatLng(dataEach.location.latitude, dataEach.location.longitude))
                            markerList.add(loc)

                            coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }



    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun MyContent() {

        // Declaring a Boolean value to
        // store bottom sheet collapsed state
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = if (selectedVenue1[0].name != "") BottomSheetState(BottomSheetValue.Expanded)
            else BottomSheetState(BottomSheetValue.Collapsed)
        )

        // Declaring Coroutine scope
        val coroutineScope = rememberCoroutineScope()

        // Creating a Bottom Sheet
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            backgroundColor = colors.background,
            sheetBackgroundColor = colors.background,
            drawerBackgroundColor = colors.background,
            sheetContent = {
                Box(
                    Modifier
                        .fillMaxHeight(0.6f)
                        .paint(
                            painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                            contentScale = ContentScale.Crop
                        )) {
                    Column {
                        Box(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 4.dp),
                            contentAlignment = Alignment.TopCenter
                        )
                        {
                            Icon(
                                painterResource(id = R.drawable.rectangle_expand), "",
                                Modifier
                                    .width(60.dp)
                                    .height(5.dp), tint = colors.onBackground
                            )
                        }

                        Full_view(bottomSheetScaffoldState, coroutineScope)
                    }
                }
            },
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)

        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    , contentAlignment = Alignment.Center
            ) {
                mapview(bottomSheetScaffoldState, coroutineScope)

                Divider(modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(2.dp)
                    .background(darkTealGreen))



//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                        .padding(vertical = 20.dp)
//                ) {
//
//
//                }

            }

        }


    }



    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun mapview(bottomSheetScaffoldState: BottomSheetScaffoldState, coroutineScope: CoroutineScope) {
        val cameraPositionState = CameraPositionState(
            position = CameraPosition.fromLatLngZoom(markerList[0].LatLng, 17f)
        )

//        LaunchedEffect(key1 = true) {
//            cameraPositionState.animate(
//                update = CameraUpdateFactory.newCameraPosition(
//                    CameraPosition(markerList[0].LatLng, 17f, 0f, 0f)
//                ),
//                durationMs = 1000
//            )
//        }


        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    })
                },
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    if (isSystemInDarkTheme()) R.raw.mapdark else R.raw.maplight
                ),
                maxZoomPreference = 18f,
                minZoomPreference = 16f,


            )
        ) {


                markerList.forEach { v ->
                    Marker(
                        state = MarkerState(
                            position = v.LatLng
                        ),
                        icon = bitmapDescriptor(
                            requireContext(),
                            R.drawable.mapmarker
                        ),
                        title = v.name,
                        snippet = v.des,

                        onClick = {
                            selectedVenue1[0] = v

                            filterWithLocation()

                            coroutineScope.launch{
                                if(bottomSheetScaffoldState.bottomSheetState.isCollapsed){
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                                cameraPositionState.animate(CameraUpdateFactory.newLatLng(it.position), durationMs = 500)
                            }
                            false

                        },
//                        onInfoWindowClick = {
//                            val gmmIntentUri =
//                                Uri.parse("google.navigation:q=${v.LatLng.latitude},${v.LatLng.longitude}")
//                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                            mapIntent.setPackage("com.google.android.apps.maps")
//                            startActivity(mapIntent)
//                        },
                    )
                }


        }
    }

    fun filterWithLocation(){
        if(selectedVenue1[0].name != ""){
            selectedVenueEvents.clear()
            selectedVenueEvents.addAll(homeViewModel.liveEvents.filter {
                it.eventdetail.venue == selectedVenue1[0].name
            })
            selectedVenueEvents.addAll(
                homeViewModel.upcomingEventsLiveState.filter {
                    it.eventdetail.venue == selectedVenue1[0].name
                }
            )
            if (BuildConfig.DEBUG){
                selectedVenueEvents.addAll(
                    homeViewModel.allEventsWithLive.filter {
                        it.eventdetail.venue == selectedVenue1[0].name
                    }
                )
            }
//            selectedVenueInformals.clear(){
//
//            }
        }
    }


    fun bitmapDescriptor(
        context: Context,
        vectorResId: Int
    ): BitmapDescriptor? {

        // retrieve the actual drawable
        val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bm = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // draw it onto the bitmap
        val canvas = android.graphics.Canvas(bm)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bm)
    }


    override fun onResume() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.menu?.findItem(R.id.events)
            ?.setChecked(true);
        MainActivity.index = R.id.events;
        super.onResume()

//        var unseen_notif_count = 0
//
//        firebaseFirestore = FirebaseFirestore.getInstance()
//        sharedPreferences = activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)
//
//        firebaseFirestore!!.collection("Notification").get().addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot> ->
//            if (task.isSuccessful) {
//                val notifs = task.result.size()
//                Log.d("Notification Count", "No of notifications: " + notifs)
//                val seen_notifs = sharedPreferences?.getInt("seen_notifs_count", 0)
//                Log.d("seen notification", seen_notifs.toString());
//                unseen_notif_count = notifs - seen_notifs!!
//
//                if (unseen_notif_count <= 0) {
//                    binding.notificationCount.visibility = View.INVISIBLE
//                } else if (unseen_notif_count <= 9) {
//                    binding.notificationCount.visibility = View.VISIBLE
//                    binding.notificationCount.text = unseen_notif_count.toString()
//                } else {
//                    binding.notificationCount.visibility = View.VISIBLE
//                    binding.notificationCount.text = "9+"
//                }
//            } else {
//                Log.d("Error", "Error loading notification count", task.exception)
//            }
//        })
    }
}

