package com.alcheringa.alcheringa2022

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.TouchDelegate
import android.view.View
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.*
import com.alcheringa.alcheringa2022.databinding.ActivityEventDetailsBinding
import com.alcheringa.alcheringa2022.ui.theme.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import org.intellij.lang.annotations.JdkConstants
import java.util.*

class Events_Details_Fragment : Fragment() {
    private lateinit var fgman: FragmentManager
    lateinit var args:Bundle
    val homeViewModel: viewModelHome by activityViewModels()
    lateinit var binding:ActivityEventDetailsBinding
    lateinit var eventfordes: eventWithLive
    lateinit var similarlist:MutableList<eventWithLive>
    private lateinit var  scheduleDatabase: ScheduleDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("check","calling on create ")
        super.onCreate(savedInstanceState)


        args=requireArguments()
        eventfordes= homeViewModel.allEventsWithLive.filter { data-> data.eventdetail.artist== args.getString("Artist") }[0]
        fgman=parentFragmentManager
        similarlist= mutableListOf<eventWithLive>()
        similarlist.addAll(
            homeViewModel.allEventsWithLive.filter{ data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== eventfordes.eventdetail.type.replace("\\s".toRegex(), "").uppercase()})
        similarlist.remove(eventfordes)
        scheduleDatabase= ScheduleDatabase(context)


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ActivityEventDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dotsProgress.visibility=View.GONE
        binding.artistname.text=eventfordes.eventdetail.artist.uppercase()
        binding.artistname.setSelected(true)



        binding.backbtn2.setOnClickListener{requireActivity().onBackPressed()}


        binding.cvevdetail.setContent{
            Alcheringa2022Theme {
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Defaultimg(eventWithLive = eventfordes)
                        Spacer(modifier = Modifier.height(8.dp))
                        EventName(eventWithLive = eventfordes)
                        Spacer(modifier = Modifier.height(20.dp))
                        Column() {
                            Bottomviewcomp(eventWithLive = eventfordes)

                            Spacer(modifier = Modifier.height(20.dp))
                            eventButtons(eventWithLive = eventfordes)
                        }
                        Spacer(modifier = Modifier.height(40.dp))

                        Divider(color = colors.onSurface,modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp))

                        if(similarlist.isNotEmpty()){
                            similarEvents(heading = "SIMILAR EVENTS",similarlist)}
                        Spacer(modifier = Modifier.height(24.dp))
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
                    shape = RoundedCornerShape(0.dp),

                    ) {
                    GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(
                        DiskCacheStrategy.AUTOMATIC)},
                        imageModel = eventWithLive.eventdetail.imgurl,
                        contentDescription = "artist",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(297.dp),
                        //                circularReveal = CircularReveal(300),

                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        shimmerParams = ShimmerParams(
                            baseColor = if(isSystemInDarkTheme()) black else highWhite,
                            highlightColor = if(isSystemInDarkTheme()) highBlack else white,
                            durationMillis = 1500,
                            dropOff = 1f,
                            tilt = 20f
                        ), failure = {
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

                        }

                    )
                }





            }

        }
    }

    @Composable
    fun EventName(eventWithLive: eventWithLive) {

        var isadded=remember{ mutableStateOf(false)}
        LaunchedEffect(key1=Unit,block = {
            isadded.value=homeViewModel.OwnEventsLiveState.any { data-> data.artist==eventWithLive.eventdetail.artist }

        })
        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 16.dp)) {

            Column(horizontalAlignment = Alignment.Start,) {

                Text(
                    text = eventWithLive.eventdetail.artist,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = futura,
                    color = colors.onBackground,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.wrapContentSize()
                )

                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = eventWithLive.eventdetail.type,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = futura,
                    color = colors.onBackground,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.wrapContentSize()


                )
            }

            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 16.dp)) {
                if(!isadded.value) {
                    Image(
                        painter = if(isSystemInDarkTheme()) {
                            painterResource(id =R.drawable.emptyheart_dark )} else {
                            painterResource(id = R.drawable.emptyheart_light)},
                        contentDescription = null,
                        alignment = Alignment.CenterStart,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                isadded.value = true; homeViewModel.OwnEventsWithLive.addNewItem(
                                eventWithLive.eventdetail
                            )
                                scheduleDatabase.addEventsInSchedule(
                                    eventWithLive.eventdetail,
                                    context
                                )
                            }
                    )
                }

                if(isadded.value) {
                    Image(
                        painter = if(isSystemInDarkTheme()) {
                            painterResource(id =R.drawable.filledheart_dark)} else {
                            painterResource(id = R.drawable.filledheart_light)},
                        contentDescription = null,
                        alignment = Alignment.CenterStart,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                isadded.value = false
                                homeViewModel.OwnEventsWithLive.removeAnItem(
                                    eventWithLive.eventdetail
                                )
                                scheduleDatabase.DeleteItem(eventWithLive.eventdetail.artist)
                            }
                    )
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
                Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()) {
                    Button(
                        onClick = {},
                        Modifier
                            .width(500.dp)
                            .height(50.dp)
                            .border(1.dp, colors.onBackground, shape = RoundedCornerShape(10.dp)),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            colors.background
                        )
                    ) {
                        Text(text="Event Finished!",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = futura,
                            color = colors.onBackground,
                            textAlign = TextAlign.Center
                        )
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


            }
            else{
                Row(horizontalArrangement = Arrangement.Center , modifier = Modifier.fillMaxWidth()) {
                    if(v != null) {

                        //Spacer(modifier = Modifier.width(50.dp))
                        /*Button(
                            onClick = {
                                //TODO: (Shantanu) Implement all venue locations
                                val gmmIntentUri =
                                    Uri.parse("google.navigation:q=${v.LatLng.latitude},${v.LatLng.longitude}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                startActivity(mapIntent)
                            },
                            Modifier
                                .weight(0.5f)
                                .height(50.dp)
                                .border(
                                    1.dp,
                                    colors.onBackground,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                colors.background
                            )
                        ) {
                            Text(
                                text = "Direction",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = futura,
                                color = colors.onBackground,
                                textAlign = TextAlign.Left
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Divider(color = colors.onSurface , modifier = Modifier
                                .height(30.dp)
                                .width(1.dp))

                            Spacer(modifier = Modifier.width(5.dp))

                            Icon(painter = painterResource(id = R.drawable.baseline_north_east_24) , contentDescription = null)

                        }*/
                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .border(
                                    1.dp,
                                    colors.onBackground,
                                    shape = RoundedCornerShape(5.dp),
                                )
                                .background(
                                    brush = Brush.verticalGradient(
                                        0f to darkTealGreen,
                                        1f to darkerGreen
                                    ),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .clickable {
                                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.alcheringa.in")))
                                }


                        ) {
                            Row(
                                modifier = Modifier
                                    .align(
                                        Alignment.Center
                                    )
                                    .width(150.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Register",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = aileron,
                                    color = creamWhite,
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                Divider(color = creamWhite , modifier = Modifier
                                    .height(25.dp)
                                    .width(1.dp))

                                Spacer(modifier = Modifier.width(5.dp))

                                Image(
                                    painter = painterResource(R.drawable.register),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.width(30.dp))

                    /*Button(
                        onClick = {
                            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.reglink)))
                        },
                        Modifier
                            .weight(0.5f)
                            .height(50.dp)
                            .border(1.dp, colors.onBackground, shape = RoundedCornerShape(10.dp)),
                        shape  = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            colors.background
                        )
                    ) {
                        Text(
                            text = "Register",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = futura,
                            color = colors.onBackground,
                            textAlign = TextAlign.Left
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Divider(color = colors.onSurface , modifier = Modifier
                            .height(30.dp)
                            .width(1.dp))

                        Spacer(modifier = Modifier.width(5.dp))

                        Icon(painter = painterResource(id = R.drawable.vector) , contentDescription = null , modifier = Modifier.size(20.dp) )


                    }*/
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .border(
                                1.dp,
                                colors.onBackground,
                                shape = RoundedCornerShape(5.dp),
                            )
                            .background(
                                brush = Brush.verticalGradient(
                                    0f to containerPurple,
                                    1f to borderdarkpurple
                                ),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .clickable {
                                val gmmIntentUri =
                                    Uri.parse("google.navigation:q=${v?.LatLng?.latitude},${v?.LatLng?.longitude}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                startActivity(mapIntent)
                            }


                    ) {
                        Row(
                            modifier = Modifier
                                .align(
                                    Alignment.Center
                                )
                                .width(150.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Direction",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = aileron,
                                color = creamWhite,
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Divider(color = creamWhite , modifier = Modifier
                                .height(25.dp)
                                .width(1.dp))

                            Spacer(modifier = Modifier.width(5.dp))

                            Image(
                                painter = painterResource(R.drawable.direction),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }


                    //Spacer(modifier = Modifier.width(50.dp))


                }


            }
        }
        else
        {
            Row(horizontalArrangement = Arrangement.Center , modifier = Modifier.fillMaxWidth()) {
                if(v != null) {

                    //Spacer(modifier = Modifier.width(50.dp))
                    /*Button(
                        onClick = {
                            //TODO: (Shantanu) Implement all venue locations
                            val gmmIntentUri =
                                Uri.parse("google.navigation:q=${v.LatLng.latitude},${v.LatLng.longitude}")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            startActivity(mapIntent)
                        },
                        Modifier
                            .weight(0.5f)
                            .height(50.dp)
                            .border(
                                1.dp,
                                colors.onBackground,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            colors.background
                        )
                    ) {
                        Text(
                            text = "Direction",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = futura,
                            color = colors.onBackground,
                            textAlign = TextAlign.Left
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Divider(color = colors.onSurface , modifier = Modifier
                            .height(30.dp)
                            .width(1.dp))

                        Spacer(modifier = Modifier.width(5.dp))

                        Icon(painter = painterResource(id = R.drawable.baseline_north_east_24) , contentDescription = null)

                    }*/
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .border(
                                1.dp,
                                colors.onBackground,
                                shape = RoundedCornerShape(5.dp),
                            )
                            .background(
                                brush = Brush.verticalGradient(
                                    0f to darkTealGreen,
                                    1f to darkerGreen
                                ),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .clickable {
                                startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(/*eventWithLive.eventdetail.reglink*/"https://www.alcheringa.in")))
                                /*val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.reglink))
                                if(context?.let { intent.resolveActivity(it.packageManager) } != null) {
                                    startActivity(intent)
                                } else {
                                    Log.d(TAG , "No app found to handle the intent")
                                }*/
                            }


                    ) {
                        Row(
                            modifier = Modifier
                                .align(
                                    Alignment.Center
                                )
                                .width(150.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Buy Pass",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = futura,
                                color = creamWhite,
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Divider(color = creamWhite , modifier = Modifier
                                .height(25.dp)
                                .width(1.dp))

                            Spacer(modifier = Modifier.width(5.dp))

                            Image(
                                painter = painterResource(R.drawable.buy_pass),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.width(30.dp))

                /*Button(
                    onClick = {
                        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(eventWithLive.eventdetail.reglink)))
                    },
                    Modifier
                        .weight(0.5f)
                        .height(50.dp)
                        .border(1.dp, colors.onBackground, shape = RoundedCornerShape(10.dp)),
                    shape  = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        colors.background
                    )
                ) {
                    Text(
                        text = "Register",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = futura,
                        color = colors.onBackground,
                        textAlign = TextAlign.Left
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Divider(color = colors.onSurface , modifier = Modifier
                        .height(30.dp)
                        .width(1.dp))

                    Spacer(modifier = Modifier.width(5.dp))

                    Icon(painter = painterResource(id = R.drawable.vector) , contentDescription = null , modifier = Modifier.size(20.dp) )


                }*/
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .border(
                            1.dp,
                            colors.onBackground,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                0f to containerPurple,
                                1f to borderdarkpurple
                            ),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            val gmmIntentUri =
                                Uri.parse("google.navigation:q=${v?.LatLng?.latitude},${v?.LatLng?.longitude}")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            startActivity(mapIntent)
                        }


                ) {
                    Row(
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .width(150.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Direction",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = futura,
                            color = creamWhite,
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Divider(color = creamWhite , modifier = Modifier
                            .height(25.dp)
                            .width(1.dp))

                        Spacer(modifier = Modifier.width(5.dp))

                        Image(
                            painter = painterResource(R.drawable.direction),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }


                //Spacer(modifier = Modifier.width(50.dp))


            }


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
//                        fontFamily = futura,
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
            /* if (
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

                     /*Button(
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

                     }*/


                 }
             }*/
        }
    }



    @Composable
    fun Bottomviewcomp(eventWithLive:eventWithLive){
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

        var isadded=remember{ mutableStateOf(false)}
        LaunchedEffect(key1=Unit,block = {
            isadded.value=homeViewModel.OwnEventsLiveState.any { data-> data.artist==eventWithLive.eventdetail.artist }

        })

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
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

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        Image(
                            painter = if(isSystemInDarkTheme()) {
                                painterResource(id = R.drawable.locationpin_dark)} else {
                                painterResource(id = R.drawable.locationpin_light)},
                            contentDescription = null,


                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(colors.onBackground),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = eventWithLive.eventdetail.venue,
                            style = TextStyle(
                                color = colors.onBackground,
                                fontFamily = futura,
                                fontWeight = FontWeight.Medium,
                                fontSize = 25.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Image(
                            painter = if(eventWithLive.isLive.value) { painterResource(id = R.drawable.schedule_live)} else {if(isSystemInDarkTheme()) {
                                painterResource(id = R.drawable.schedule_dark)} else {
                                painterResource(id = R.drawable.schedule_light)}},
                            contentDescription = null,
                            modifier = Modifier
                                .width(22.dp)
                                .height(22.dp),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop,
                            //colorFilter = ColorFilter.tint(colors.onBackground)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if(eventWithLive.isLive.value){"Live"} else {"${eventWithLive.eventdetail.starttime.date} Feb, ${if (eventWithLive.eventdetail.starttime.hours > 12) "${eventWithLive.eventdetail.starttime.hours - 12}" else eventWithLive.eventdetail.starttime.hours}${if (eventWithLive.eventdetail.starttime.min != 0) ":${eventWithLive.eventdetail.starttime.min}" else ""} ${if (eventWithLive.eventdetail.starttime.hours >= 12) "PM" else "AM"} "},
                            style = TextStyle(
                                color = if(eventWithLive.isLive.value) {
                                    darkTealGreen} else {colors.onBackground},
                                fontFamily = futura,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                        )
                    }
                }
            }
            //Spacer(modifier = Modifier.height(20.dp))

            /*Row(modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                /*Row(
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
                }*/
                Spacer(modifier = Modifier.width(24.dp))

                /*Box(modifier = Modifier
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
                    /*if( !isadded.value) {
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

                    }*/
                    /*if(isadded.value)
                    {
                        Row(
                            Modifier
                                .clickable {
                                    isadded.value = false
                                    homeViewModel.OwnEventsWithLive.removeAnItem(
                                        eventWithLive.eventdetail
                                    )
                                    scheduleDatabase.DeleteItem(eventWithLive.eventdetail.artist)
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
                    }*/
                }*/


            }*/

            Spacer(modifier = Modifier.height(28.dp))
            Text(text =eventfordes.eventdetail.descriptionEvent ,
                fontFamily = futura,
                fontWeight = FontWeight.Normal,
                color = colors.onBackground,
                fontSize = 16.sp
            )
            //Spacer(modifier = Modifier.height(36.dp))
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
    fun similarEvents(heading: String, similarlist: MutableList<eventWithLive>) {
        Box(
            modifier =Modifier.padding(horizontal = 20.dp, vertical = 12.dp) )
        {
            Text(
                text = heading,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = futura,
                color = colors.onBackground)}
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
        ) { items(similarlist)
        {dataEach -> context?.let { Event_card(eventdetail = dataEach, homeViewModel, it, this@Events_Details_Fragment,fgman,R.id.action_events_Details_Fragment_self2) } } }

        Spacer(modifier = Modifier.height(5.dp))
    }


}