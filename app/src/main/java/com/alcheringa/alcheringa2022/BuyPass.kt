package com.alcheringa.alcheringa2022


import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme

@Composable
fun PassBuy()
{
    val textColor=MaterialTheme.colors.onBackground

    val borderWidth=1.1649740934371948.dp
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(LocalDensity.current) {
        configuration.screenWidthDp
    }
//    val screenHeightPx= with(LocalDensity.current) {
//        configuration.screenHeightDp
//    }
    Log.d("width",screenWidthPx.toString())

    val picWidth=240.0/360*screenWidthPx
    val boxWidth=112.0/360*screenWidthPx
    val dashWidth=18.0/360*screenWidthPx
    val heightBox=193.0
    val smallBoxHeight=64.0
    Surface(
        modifier = Modifier
            .fillMaxWidth()

        ,
        color = MaterialTheme.colors.background
    ) {

//                    Greeting("Android")
        Row(modifier= Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier

                    .padding(15.8f.dp, 8.dp, 2.dp, 2.dp)

            ) {
                Box(
                    modifier = Modifier
                        .height(heightBox.dp)
                        .width(picWidth.dp)
                        .padding(2.dp, 0.dp, 0.dp, 0.dp)

                        .border(borderWidth, textColor, RoundedCornerShape(5.dp))
                )
                {
                    Text(modifier = Modifier.padding(20.dp,30.dp,0.dp,0.dp), text = "Buy your Alcher Pass")
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .align(Alignment.TopStart)

                            .border(
                                borderWidth,
                                textColor,
                                RoundedCornerShape(5.dp, 0.dp, 5.dp, 0.dp)
                            )
                    ) {
                        Box(modifier = Modifier.align(Alignment.Center))
                        {
                            RoundRectangle()

                        }
                    }


                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .align(Alignment.TopEnd)
                            .border(
                                borderWidth,
                                textColor,
                                RoundedCornerShape(0.dp, 5.dp, 0.dp, 5.dp)
                            )
                    )
                    {
                        Box(modifier = Modifier.align(Alignment.Center))
                        {
                            RoundRectangle()
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .align(Alignment.BottomStart)
                            .border(
                                borderWidth,
                                textColor,
                                RoundedCornerShape(0.dp, 5.dp, 0.dp, 5.dp)
                            )
                    )
                    {
                        Box(modifier = Modifier.align(Alignment.Center))
                        {
                            RoundRectangle()
                        }

                    }
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .align(Alignment.BottomEnd)
                            .border(
                                borderWidth,
                                textColor,
                                RoundedCornerShape(5.dp, 0.dp, 5.dp, 0.dp)
                            )
                    )
                    {
                        Box(modifier = Modifier.align(Alignment.Center))
                        {
                            RoundRectangle()
                        }
                    }
                    Divider(thickness =1.3f.dp, modifier = Modifier
                        .padding(20.dp, 8.dp, 20.dp, 0.dp)
                        .fillMaxWidth()
                        .height(1.3f.dp), color = textColor)
                    Divider(thickness = 1.3f.dp, modifier = Modifier
                        .padding(20.dp, 0.dp, 20.dp, 8.dp)
                        .align(Alignment.BottomEnd)
                        .fillMaxWidth()
                        .height(1.3f.dp), color =textColor)
                    Divider(thickness = 1.3f.dp, modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp, 20.dp, 20.dp, 20.dp)
                        .width(1.3f.dp), color = textColor)

                }
            }
            Column(modifier = Modifier.padding(2.dp,8.dp,8.dp,2.dp)) {

                Box(
                    modifier = Modifier
                        .height(smallBoxHeight.dp)
                        .width(boxWidth.dp)
                        .padding(0.dp, 0.dp, 11.dp, 0.dp)
                        .border(borderWidth, textColor, RoundedCornerShape(4.dp))
                )
                {

                    dashLine(dashWidth.dp)
                    Column() {
                        Text(
                            modifier = Modifier.padding(10.dp, 8.dp, 0.dp, 0.dp),
                            text = "Platinum",color=textColor
                        )
                        Text(
                            modifier = Modifier.padding(10.dp, 2.dp, 0.dp, 0.dp),
                            text = "Rs. 750/-",color=textColor
                        )
                    }
                }
                PassPrice("Gold","Rs. 250/-",boxWidth.dp,dashWidth.dp,smallBoxHeight.dp)
                PassPrice("Silver","Rs. 150/-",boxWidth.dp,dashWidth.dp,smallBoxHeight.dp)

            }


        }
    }}








@Composable
fun PassPrice(text:String,price:String,width: Dp,dashWidth:Dp,smallBoxHeight:Dp)
{
    val textColor=MaterialTheme.colors.onBackground

    val borderWidth=1.1649740934371948.dp
    Box(
        modifier = Modifier
            .height(smallBoxHeight)
            .width(width)
            .padding(0.dp, 2.dp, 11.dp, 0.dp)
            .border(borderWidth, textColor, RoundedCornerShape(4.dp))
    )
    {
        Column() {
            Text(modifier = Modifier.padding(10.dp, 8.dp, 0.dp, 0.dp), text = text, color = textColor)
            Text(modifier = Modifier.padding(10.dp, 2.dp, 0.dp, 0.dp), text = price,color=textColor)
        }
        dashLine(dashWidth)
    }
}

@Preview
@Composable
fun PassBuyPreview() {
    Alcheringa2022Theme {
        PassBuy()
    }
}

