package com.alcheringa.alcheringa2022

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.stallModel
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.FragmentCompetitionsBinding
import com.alcheringa.alcheringa2022.databinding.FragmentStallDetailsBinding
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.aileron
import com.alcheringa.alcheringa2022.ui.theme.black
import com.alcheringa.alcheringa2022.ui.theme.borderdarkpurple
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.creamWhite
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.highBlack
import com.alcheringa.alcheringa2022.ui.theme.highWhite
import com.alcheringa.alcheringa2022.ui.theme.lightBar
import com.alcheringa.alcheringa2022.ui.theme.lighterPurple
import com.alcheringa.alcheringa2022.ui.theme.white
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [stallDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class stallDetails : Fragment() {
    lateinit var args:Bundle
    private lateinit var binding: FragmentStallDetailsBinding
    lateinit var stallfordes: stallModel
    val homeViewModel: viewModelHome by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args = requireArguments()
        stallfordes = homeViewModel.stalllist.filter { data -> data.name == args.getString("stallName") }[0]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_events, container, false)
        binding = FragmentStallDetailsBinding.inflate(layoutInflater)
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


        binding.stallsCompose.setContent {
            MyContent(stallfordes)
        }
    }

    @Composable
    fun menu_items(dish_name: String, price: Double){
        Row (
            modifier = Modifier
                .padding(bottom = 5.dp)
        ){
            Text(
                text = dish_name,
                color = colors.onBackground,
                fontFamily = futura,
                fontSize = 14.sp
            )
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = price.toString(),
                    color = colors.onBackground,
                    fontSize = 14.sp,
                    fontFamily = futura
                )
            }
        }
    }

    @Composable
    fun MyContent(stallDetails: stallModel){
        Alcheringa2022Theme {
            Column {
                Divider(
                    thickness = 2.dp,
                    color = darkTealGreen
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.90f)
                            .wrapContentHeight()
                            .padding(top = 20.dp)
                            .border(1.dp, containerPurple, shape = RoundedCornerShape(5.dp))
                            .background(colors.background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight(0.3f)
                                    .fillMaxWidth(0.85f)
                                    .padding(top = 40.dp)
                            ) {
                                GlideImage(requestOptions = {
                                    RequestOptions.diskCacheStrategyOf(
                                        DiskCacheStrategy.AUTOMATIC
                                    )
                                },
                                    imageModel = stallDetails.imgurl,
                                    contentDescription = "stallImage",
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .fillMaxHeight(),

                                    alignment = Alignment.Center,
                                    contentScale = ContentScale.Crop,
                                    shimmerParams = ShimmerParams(
                                        baseColor = if (isSystemInDarkTheme()) black else highWhite,
                                        highlightColor = if (isSystemInDarkTheme()) highBlack else white,
                                        durationMillis = 1500,
                                        dropOff = 1f,
                                        tilt = 20f
                                    ), failure = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            val composition by rememberLottieComposition(
                                                LottieCompositionSpec.RawRes(
                                                    if (isSystemInDarkTheme()) R.raw.comingsoondark else R.raw.comingsoonlight
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

                                Column(
                                    modifier = Modifier
                                        .padding(start = 25.dp)
                                        .fillMaxHeight()
                                ) {
                                    Text(
                                        text = stallDetails.name,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = futura,
                                        color = colors.onBackground
                                    )
                                    Text(
                                        text = "Offer",
                                        fontSize = 11.sp,
                                        color = colors.onBackground,
                                        fontFamily = futura,
                                        modifier = Modifier
                                            .padding(top = 9.dp, bottom = 5.dp)
                                    )
                                    Text(
                                        text = stallDetails.description,
                                        fontSize = 14.sp,
                                        color = colors.onBackground,
                                        fontFamily = futura
                                    )
                                    Text(
                                        text = "Offer",
                                        fontSize = 11.sp,
                                        color = colors.onBackground,
                                        fontFamily = futura,
                                        modifier = Modifier
                                            .padding(top = 15.dp, bottom = 5.dp)
                                    )
                                    Text(
                                        text = stallDetails.description,
                                        fontSize = 14.sp,
                                        color = colors.onBackground,
                                        fontFamily = futura
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(40.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth(0.7f)
                                    .background(colors.onBackground)
                            )
                            Spacer(modifier = Modifier.height(40.dp))

                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .padding(start = 5.dp, end = 5.dp)
                                ) {
                                    Text(
                                        text = "Menu",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = futura,
                                        color = colors.onBackground,
                                        modifier = Modifier
                                            .padding(bottom = 10.dp)
                                    )

                                    for (i in stallDetails.menu) {
                                        menu_items(i.name, i.price)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(30.dp))
                            Box(
                                modifier = Modifier
                                    .border(
                                        1.dp,
                                        colors.onBackground,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .background(
                                        shape = RoundedCornerShape(4.dp),
                                        brush = Brush.verticalGradient(
                                            0f to lighterPurple,
                                            1f to borderdarkpurple
                                        )
                                    )
                                    .padding(horizontal = 15.dp, vertical = 3.dp)
                                    .height(30.dp)
                                    .wrapContentWidth()
                                    .clickable {

                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Direction",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = futura,
                                        color = creamWhite,
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Divider(
                                        color = creamWhite, modifier = Modifier
                                            .height(20.dp)
                                            .width(1.dp)
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Image(
                                        painter = painterResource(R.drawable.direction),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(40.dp))
//                        Button(
//                            onClick = { /*TODO*/ },
//                            colors = ButtonDefaults.buttonColors(Color.Transparent),
//                            border = BorderStroke(1.dp, colors.onBackground),
//                            modifier = Modifier
//                                .padding(top = 20.dp, bottom = 30.dp)
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .background(
//                                        brush = Brush.verticalGradient(
//                                            0f to lighterPurple,
//                                            1f to borderdarkpurple
//                                        )
//                                    ),
////                                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = "Click",
//                                    fontSize = 20.sp,
//                                    color = Color.White
//                                )
//                            }
//                        }
                        }

                    }

                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun defaultpreview(){
        Alcheringa2022Theme {
            MyContent(stallfordes)
        }
    }
}
