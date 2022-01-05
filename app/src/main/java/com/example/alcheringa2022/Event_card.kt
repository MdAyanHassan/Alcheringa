package com.example.alcheringa2022

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
import com.example.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.example.alcheringa2022.ui.theme.clash

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

@Composable
fun Event_card(eventdetail: eventdetail) {


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
    Alcheringa2022Theme {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(events) { dataEach -> Event_card(eventdetail = dataEach) }
        }
    }
}