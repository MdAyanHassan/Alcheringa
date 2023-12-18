package com.alcheringa.alcheringa2022

import androidx.compose.material.Divider


import android.graphics.DashPathEffect
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.layout
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import java.lang.NullPointerException



    @Composable
    fun BoughtPass(
    )
    {
        val textColor=MaterialTheme.colors.onBackground

//        val configuration = LocalConfiguration.current
//        val screenWidthPx = with(LocalDensity.current) {
//            configuration.screenWidthDp
//        }
//        val screenHeightPx= with(LocalDensity.current) {
//            configuration.screenHeightDp
//        }

        val heightBox=193.0
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.background
        ) {

//                    Greeting("Android")

            Box(modifier= Modifier


                .height(heightBox.dp)
                .padding(21.dp, 2.dp, 21.dp, 0.dp)

            ){
                val borderWidth=1.1649740934371948.dp
                val relBoxWidth=(323.7048/360).toFloat()
                Box(modifier = Modifier

                    .fillMaxWidth()
                    .border(borderWidth, textColor, RoundedCornerShape(5.dp))
                )
                {

                    Box(modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .align(Alignment.TopStart)
                        .border(borderWidth,textColor, RoundedCornerShape(5.dp, 0.dp, 5.dp, 0.dp))){
                        Box(modifier = Modifier.align(Alignment.Center))
                        {
                            RoundRectangle()
                        }}


                    Box(modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .align(Alignment.TopEnd)
                        .border(borderWidth, textColor, RoundedCornerShape(0.dp, 5.dp, 0.dp, 5.dp)))
                    {
                        Box(modifier = Modifier.align(Alignment.Center))
                        {
                            RoundRectangle()
                        }
                    }
                    Box(modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .align(Alignment.BottomStart)
                        .border(borderWidth, textColor, RoundedCornerShape(0.dp, 5.dp, 0.dp, 5.dp)))
                    {
                        Box(modifier = Modifier.align(Alignment.Center))
                        {
                            RoundRectangle()
                        }
                    }
                    Box(modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .align(Alignment.BottomEnd)
                        .border(borderWidth, textColor, RoundedCornerShape(5.dp, 0.dp, 5.dp, 0.dp)))
                    {
                        Box(modifier = Modifier.align(Alignment.Center))
                        {
                            RoundRectangle()
                        }
                    }
                    Divider(thickness = 1.3f.dp, modifier = Modifier.padding(20.dp,8.dp,20.dp,0.dp).fillMaxWidth(), color =textColor)
                    Divider(thickness = 1.3f.dp, modifier = Modifier.padding(20.dp,0.dp,20.dp,8.dp).align(Alignment.BottomEnd).fillMaxWidth(), color = textColor)
                    Divider(thickness = 1.3f.dp, modifier = Modifier.fillMaxHeight().padding(8.dp,20.dp,20.dp,20.dp).width(1.3f.dp), color = textColor)
                    Divider(thickness =1.3f.dp, modifier = Modifier.fillMaxHeight().padding(0.dp,20.dp,8.dp,20.dp).align(Alignment.TopEnd).width(1.3f.dp), color = textColor)

                    // ClippedCornerBox()
                }
            }

        }
    }




@Composable
fun RoundRectangle() {
    val textColor=MaterialTheme.colors.onBackground

    val borderWidth=1.1649740934371948.dp

    Box(modifier=Modifier) {
        Box(
            modifier = Modifier
                .height(12.dp)
                .width(12.dp)
                .align(Alignment.TopEnd)
                .border(borderWidth, textColor, RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
        )
    }
}

@Composable
fun dashLine(width:Dp) {

    val textColor=MaterialTheme.colors.onBackground

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Box(modifier = Modifier
        .fillMaxWidth()
    ) {


        Canvas(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(0.dp, 0.dp, width, 0.dp)) {

            drawLine(
                color = textColor,
                start = Offset(size.width, 0f),
                end = Offset(size.width, size.height),
                pathEffect = pathEffect
            )
        }
    }
}





