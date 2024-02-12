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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
                val quesAns = mapOf<String, String>(
                    "How can I buy merch?" to "You can buy the exclusive Alcheringa merch by using the Alcheringa mobile app. Just go to the 'shop' page, where you will find all the available merchandise. Select the merch of your choice and order!",
                    "Will the merch be delivered home ?" to "Yes! Your ordered merch will be shipped to the shipping address that you register while ordering them.",
                    "How can I buy Alcher cards?" to "Head over to the events section of the App and click on get card to purchase your Alcher cards for yourself and your friends.",
                    "How can I register for competitions?" to "Head over to the Competitions tab, choose the competition of your choice, get yourself registered and bring your game to Alcheringa 24’s exciting range of competitions.",
                    "How can I attend offline Alcheringa events?" to "You purchase Alcher cards through which you can avail a legion of benefits like Free access to pronites, discounts on merchandise and many more benefits awaiting in campus.",
                    "Where can I find the event schedule and lineup?" to "The event schedule for Alcheringa 2024 events can be found on the app under the Schedule tab.",
                    "Is there any registration fee for competitions?" to "There is no registration fee for competitions. All you have to do is register yourself at registration.alcheringa.in .",
                    "When will competition results be announced?" to "Competitions are judged and results released soon after in the coming weeks after Alcheringa 2024.",
                    "Where can I get updates on competitions?" to "Be the first to get all the latest updates on all the competitions and events on our social media and on our app. Make sure you have turned on notifications for our social media handles and App for regular notifications of all the fun-filled events and activities taking place around the campus.",
                )
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
                            .verticalScroll(rememberScrollState())

                            .paint(
                                painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                                contentScale = ContentScale.Crop
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        quesAns.forEach { (question, answer) ->
                            questionAnwerItem(question = question, answer = answer)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun questionAnwerItem(question: String, answer: String){
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

        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    isExpanded = !isExpanded
                }
        ){
            Text(
                text = question,
                fontSize = 18.sp,
                color = colors.onBackground,
                fontFamily = futura,
                modifier = Modifier
                    .weight(1f)
            )

            Icon(
                icon,
                contentDescription = null,
                tint = colors.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
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
                text = answer,
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