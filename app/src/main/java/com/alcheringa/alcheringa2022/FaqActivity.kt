package com.alcheringa.alcheringa2022

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.lighterPurple

class FaqActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Alcheringa2022Theme {
                val q1 = "What is Alcheringa?"
                val a1 = "IITG cult fest that has a lot of people working and stuff. There will be one more line of text hereeeeeeeeeeeeee. It can also have something"
                Column(
                    modifier = Modifier
                        .background(colors.background)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .height(65.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier) {
                            Icon(
                                painter = painterResource(id = R.drawable.cart_arrow),
                                contentDescription = null,
                                tint = lighterPurple,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .clickable {
                                        finish()
                                    }
                                    .padding(10.dp)
                            )
                        }
                        Text(
                            text = "FAQs",
                            fontFamily = futura,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            color = lighterPurple
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(darkTealGreen)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                            .paint(
                                painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                                contentScale = ContentScale.Crop
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        qq(q1, a1)
                        qq(q1,a1)
                        qq(q1,a1)
                        qq(q1,a1)
                    }
                }
            }
        }
    }

    @Composable
    fun qq(q1: String, a1: String){
        Spacer(modifier = Modifier.height(10.dp))
        var isExpanded by remember {
            mutableStateOf(false)
        }
        val icon = if (isExpanded) {
            Icons.Filled.KeyboardArrowUp
        } else {
            Icons.Filled.KeyboardArrowDown
        }

        val expandableHeight by animateDpAsState(
            targetValue = if (isExpanded) 200.dp else 0.dp,
            animationSpec = tween(durationMillis = 300)
        )

        Box(
            Modifier
                .fillMaxWidth()
                .clickable {
                    isExpanded = !isExpanded
                }
        ){
            Text(
                text = q1,
                fontSize = 18.sp,
                color = colors.onBackground,
                fontFamily = futura
            )

            Icon(
                icon,
                contentDescription = null,
                tint = colors.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .heightIn(max = expandableHeight)
        ){
            Text(
                text = a1,
                color = colors.onBackground,
                fontSize = 14.sp,
                fontFamily = futura
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(darkTealGreen)
        )
    }
}