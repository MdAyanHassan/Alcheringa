package com.example.alcheringa2022

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.alcheringa2022.databinding.FragmentHomeBinding
import com.example.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.example.alcheringa2022.ui.theme.clash
import com.example.alcheringa2022.ui.theme.hk_grotesk
import org.intellij.lang.annotations.JdkConstants

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    lateinit var binding: FragmentHomeBinding
    val events=mutableListOf(

        eventdetail(
        "JUBIN NAUTIYAL",
        "Pro Nights",
        "12 Feb, 4 PM",
        "ONLINE", R.drawable.jubin
    ),

        eventdetail(
            "DJ SNAKE",
            "Pro Nights",
            "11 Feb, 4 PM",
            "ON GROUND", R.drawable.djsnake
        ),
        eventdetail(
            "TAYLOR SWIFT",
        "Pro Nights",
        "13 Feb, 4 PM",
        "ON GROUND", R.drawable.taylor
    )



        )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater)
        return (binding.root)

//        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.compose1.setContent {
            Alcheringa2022Theme() {

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(events) { dataeach -> ongoingEvents(eventdetail = dataeach) }
                    }
                }
                
                

//                Box(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp)) {
//                    LazyRow(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        items(events) { dataeach -> upcomingEvents(eventdetail = dataeach) }
//                    }
//                }



            }
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): Home {
            val fragment = Home()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
    @Preview
    @Composable
    fun upcomingEvents(eventdetail: eventdetail= events[1]) {

        Box() {

            Card(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
            elevation = 5.dp) {
                    Box(modifier = Modifier
                        .height(256.dp)
                        .width(218.dp)){
                        Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ), startY = 300f
                                )
                            ))
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp), contentAlignment = Alignment.BottomStart){
                            Column {
                                Text(text = eventdetail.artist, style = MaterialTheme.typography.h1)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = eventdetail.category, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = clash,fontWeight = FontWeight.W600,fontSize = 14.sp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = eventdetail.time, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                                Spacer(modifier = Modifier.height(2.dp))
                                Row {
                                    Box(modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp)) {
                                        Image(
                                            painter = if (eventdetail.mode.contains("ONLINE")) {
                                                painterResource(id = R.drawable.online)
                                            } else {
                                                painterResource(id = R.drawable.onground)
                                            },
                                            contentDescription = null, modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = eventdetail.mode,style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                            }
                        }

                    }


            }
        }
    }



}

    @Preview
    @Composable
    fun ongoingEvents(eventdetail: eventdetail= events[1]) {

        Box() {

            Card(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = 5.dp) {
                Box(modifier = Modifier
                    .height(256.dp)
                    .width(218.dp)){
                    Image(painter = painterResource(id = eventdetail.imgurl), contentDescription = "artist", contentScale = ContentScale.Crop)
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(21.dp)
                        .background(
                            color = colorResource(
                                id = R.color.ThemeRed
                            )
                        )
                    ){ Text(text = "⬤ LIVE", color = Color.White, modifier = Modifier.align(alignment = Alignment.Center), fontSize = 12.sp)}
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ), startY = 300f
                            )
                        ))
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp), contentAlignment = Alignment.BottomStart){
                        Column {
                            Text(text = eventdetail.artist, style = MaterialTheme.typography.h1)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = eventdetail.category, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = clash,fontWeight = FontWeight.W600,fontSize = 14.sp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = eventdetail.time, style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                            Spacer(modifier = Modifier.height(2.dp))
                            Row {
                                Box(modifier = Modifier
                                    .height(20.dp)
                                    .width(20.dp)) {
                                    Image(
                                        painter = if (eventdetail.mode.contains("ONLINE")) {
                                            painterResource(id = R.drawable.online)
                                        } else {
                                            painterResource(id = R.drawable.onground)
                                        },
                                        contentDescription = null, modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = eventdetail.mode,style = TextStyle(color = colorResource(id = R.color.textGray),fontFamily = hk_grotesk,fontWeight = FontWeight.Normal,fontSize = 14.sp))
                            }
                        }

                    }


                }
            }
        }



    }
}

