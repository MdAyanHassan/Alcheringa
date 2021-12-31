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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val events = mutableListOf(

        eventdetail(
            "JUBIN NAUTIYAL",
            "Pro Nights",
            "12 Feb, 4 PM",
            "Online", R.drawable.jubin
        ),

        eventdetail(
            "DJ SNAKE",
            "Pro Nights",
            "11 Feb, 4 PM",
            "Online", R.drawable.djsnake
        ),
        eventdetail(
            "TAYLOR SWIFT",
            "Pro Nights",
            "13 Feb, 4 PM",
            "Online", R.drawable.taylor
        )


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(events) { dataEach -> UpcomingEvents(eventdetail = dataEach) }
                }
            }

        }
    }

    @Composable
    fun UpcomingEvents(eventdetail: eventdetail) {

        Box {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = 5.dp
            ) {
                Box(
                    modifier = Modifier
                        .height(256.dp)
                        .width(218.dp)
                ) {
                    Image(
                        painter = painterResource(id = eventdetail.imgurl),
                        contentDescription = "artist",
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ), startY = 300f
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Text(text = eventdetail.artist, style = MaterialTheme.typography.h1)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = eventdetail.time,
                                style = TextStyle(
                                    color = colorResource(id = R.color.textGray),
                                    fontFamily = clash,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }

                }

            }
        }
    }

    @Composable
    @Preview
    fun PreviewItem(){
        Alcheringa2022Theme() {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(events) { dataEach -> UpcomingEvents(eventdetail = dataEach) }
            }
        }
    }
}

