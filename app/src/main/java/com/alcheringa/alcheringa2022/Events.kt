package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.tagwithicon

import com.alcheringa.alcheringa2022.Model.venue
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.FragmentEventsBinding
import com.alcheringa.alcheringa2022.ui.theme.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.*

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.maps.android.compose.*
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class Events : Fragment() {

    private lateinit var fgm:FragmentManager
    private lateinit var binding: FragmentEventsBinding
    val homeViewModel: viewModelHome by activityViewModels()

    var firebaseFirestore: FirebaseFirestore? = null
    var sharedPreferences: SharedPreferences? = null
    val venuelist= listOf<venue>(
            venue("IITG Auditorium"),
            venue("Pronites Ground", LatLng(26.190761044728855, 91.69699071630549)),
            venue("Alcheringa Wall", LatLng(26.191978820911885, 91.69572236815209))

        )
    val taglist= listOf("💃  Dance","🎵  Music", "🎭  Stagecraft", "🕶  Vogue nation", "🙋‍  Class apart", "🎨  Art talkies", "📖  Literature","💻  Digital Dexterity", "🎥  Lights camera action","🌐  Informal")


    val searchlist = mutableStateListOf<eventWithLive>()
    var tg= mutableStateOf("")
    var searchtext= mutableStateOf("")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fgm=parentFragmentManager
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
        val scheduleDatabase=ScheduleDatabase(context)
        val eventslist=scheduleDatabase.schedule;

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
//        binding.account.setOnClickListener {
//            startActivity(Intent(context,Account::class.java));
//
//        }
//        binding.pass.setOnClickListener{
//            startActivity(Intent(context, NotificationActivity::class.java));
//        }

        binding.eventsCompose.setContent {

Alcheringa2022Theme() {
    MyContent()
}




            
        }
    }

    @Composable
    @Preview
    fun Full_view() {
        Alcheringa2022Theme {
            Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                    /*.background(Color.Black)*/
            ) {
                if(searchtext.value!="" || tg.value!=""){
                 searchresultrow(heading = "SEARCH RESULTS")
                }

                Events_row(heading = "Critical Damage")
                Events_row(heading = "Pronites")
                Events_row(heading = "Proshows")
                Events_row(heading = "Creators' Camp")
                Events_row(heading = "Humor Fest")
                Events_row(heading = "Campaigns")
                Column(modifier =Modifier.padding(horizontal = 20.dp, vertical = 12.dp) ){
                    Box() {val alphaval= 0.2f
                        Card(
                            Modifier
                                .height(10.dp)
                                .offset(x = -5.dp, y = 16.dp)
                                .alpha(alphaval),
                            shape = RoundedCornerShape(100.dp),
                            backgroundColor = textbg

                        ){
                            Text(

                                text = "Competitions  ",
                                fontFamily = aileron,
                                fontWeight = FontWeight.Bold,
                                color = Color.Transparent,
                                fontSize = 21.sp
                            )
                        }
                        Text(

                            text = "Competitions",
                            fontFamily = aileron,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground,
                            fontSize = 21.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    imgcomp()

                }


            }
        }
    }

    @Composable
    fun Events_row(heading: String) {val alphaval= 0.2f
        val list=homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== heading.replace("\\s".toRegex(), "").uppercase()}
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

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(list)
                { dataEach ->
                    context?.let {
                        Event_card(
                            eventdetail = dataEach,
                            homeViewModel,
                            it,
                                this@Events,
                            fgm,
                                R.id.action_events_to_events_Details_Fragment2
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    @Composable
    fun searchresultrow(heading: String) {

            Box(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            ) {
                Text(
                    text = heading,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    fontFamily = clash,
                    color = colors.onBackground
                )
            }
if (searchlist.isNotEmpty()) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(searchlist)
        { dataEach ->
            context?.let {
                Event_card(
                    eventdetail = dataEach,
                    homeViewModel,
                    it,
                    this@Events,
                    fgm,
                    R.id.action_events_to_events_Details_Fragment2
                )
            }
        }
    }
}
        else{
            Box(
        modifier = Modifier.padding(
        horizontal = 20.dp,
                )
            ) {
                Text(
                    text = "No results found for \"${searchtext.value} ${tg.value.drop(3)}\"",
                    color = colors.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = aileron
                )
            }
}
            Spacer(modifier = Modifier.height(24.dp))

    }

    @Composable
    fun imgcomp() {

        Box(
            modifier = Modifier
                .height(256.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable {
//                    fgm
//                        .beginTransaction()
//                        .replace(R.id.fragmentContainerView,CompetitionsFragment() ).addToBackStack(null)
//                        .commit()
                    NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.action_eventFragment_to_competitionsFragment);

                }
        ) {
            GlideImage(
                imageModel = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitionHeader.png?alt=media&token=7f350d9e-dbad-427a-822f-e3586bfa5e4c",
                contentDescription = "imghead",
                modifier= Modifier
                    .fillMaxWidth()
                    .height(256.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                shimmerParams = ShimmerParams(
                    baseColor = Color.Black,
                    highlightColor = Color.LightGray,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),failure = {
                    Box(modifier= Modifier
                        .fillMaxWidth()
                        .height(256.dp), contentAlignment = Alignment.Center) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_sad_svgrepo_com
                                ),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Image Request Failed",
                                style = TextStyle(
                                    color = Color(0xFF747474),
                                    fontFamily = hk_grotesk,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }

                }


            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), startY = 100f
                        )
                    )
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(17.dp), contentAlignment = Alignment.BottomStart){
                Text(text = "SEE ALL COMPETITIONS", color = Color.White, fontFamily = clash, fontWeight = FontWeight.W700, fontSize = 18.sp)
            }


        }
    }




    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun MyContent(){

        // Declaring a Boolean value to
        // store bottom sheet collapsed state
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState =
        BottomSheetState(BottomSheetValue.Collapsed)
        )

        // Declaring Coroutine scope
        val coroutineScope = rememberCoroutineScope()

        // Creating a Bottom Sheet
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState, backgroundColor = colors.background, sheetBackgroundColor = colors.background, drawerBackgroundColor = colors.background,
            sheetContent =  {
               Box(
                   Modifier
                       .wrapContentHeight()
                       .fillMaxWidth()
                       .padding(top = 8.dp, bottom = 4.dp), contentAlignment = Alignment.TopCenter)
               {
                   Icon(painterResource(id = R.drawable.rectangle_expand), "",
                       Modifier
                           .width(60.dp)
                           .height(5.dp), tint = colors.onSurface)
               }
                Full_view()
            },
            sheetPeekHeight = if(tg.value=="" && searchtext.value=="")280.dp else 520.dp, sheetShape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp)
        
        ){
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 184.dp), contentAlignment = Alignment.TopCenter) {

              mapview()


               Column( modifier = Modifier
                   .fillMaxWidth()
                   .wrapContentHeight()
                   .padding(vertical = 20.dp) ) {
                    val keyboardController = LocalSoftwareKeyboardController.current
                   Box(modifier = Modifier
                       .fillMaxWidth()
                       .wrapContentHeight()
                       .padding(horizontal = 15.dp,)){
                   TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(100.dp))
                            .border(
                                1.dp,
                                if (isSystemInDarkTheme()) highWhite else midWhite,
                                RoundedCornerShape(100.dp)
                            ),
//                        shape = RoundedCornerShape(100.dp),
                        placeholder = { Text("Search an event",) },
                       leadingIcon = {Icon(Icons.Outlined.Search,"", tint =  if (isSystemInDarkTheme()) highWhite else midWhite)},
                        value = searchtext.value,
                        textStyle = TextStyle(
                        fontFamily = aileron,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,),
                        onValueChange = { v: String -> searchtext.value = v;filterlist() },
                       keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Search),
                       keyboardActions = KeyboardActions(onSearch = {keyboardController?.hide();filterlist()}),
                       singleLine = true,


                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colors.background,
                            textColor = colors.onBackground,
                            placeholderColor = Color(0xffacacac),
                            cursorColor = colors.onBackground,
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        )
                    )
                   }
                   Spacer(modifier = Modifier.height(8.dp))
                   tags()

                }

        }

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
        searchlist.addAll(homeViewModel.allEventsWithLive.filter { it.eventdetail.toString().contains(searchtext.value,true) && it.eventdetail.toString().contains(tg.value.drop(3),true) })

        //zooming map at the first event venue
//        val firsteventvenue= venuelist.find { it.name==searchlist[0].eventdetail.venue }
//        if(searchlist.isNotEmpty() && firsteventvenue!=null ){
//            cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition(venuelist.random().LatLng, 16f,0f,0f)))
//    }
    }

    @Composable
    fun mapview() {
        var cameraPositionState = CameraPositionState(
            position = CameraPosition.fromLatLngZoom(venuelist[2].LatLng, 16f)
        )

        val coroutinescope= rememberCoroutineScope()
        coroutinescope.launch {
        cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(CameraPosition(venuelist.random().LatLng, 16f,0f,0f)))
        }

        val mainaudi = LatLng(26.191117262340942, 91.69295134231831)

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState, properties = MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    if (isSystemInDarkTheme())R.raw.mapdark else R.raw.maplight
                )
            )
        ) {
            venuelist.forEach {v->
                Marker(

                    icon = bitmapDescriptor(requireContext(),R.drawable.partylocationicon),
                    position = v.LatLng,
                    title = v.name,
                    snippet = v.des,
//                    onClick = { searchtext.value=v.name;false}
                onInfoWindowClick = {val gmmIntentUri =
                    Uri.parse("google.navigation:q=${v.LatLng.latitude},${v.LatLng.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }
                )
            }
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
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.menu?.findItem(R.id.events)?.setChecked(true);
        MainActivity.index=R.id.events;
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

