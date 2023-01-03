package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.alcheringa.alcheringa2022.Model.member
import com.alcheringa.alcheringa2022.databinding.ActivityTeamBinding
import com.alcheringa.alcheringa2022.databinding.FragmentEventsBinding
import com.alcheringa.alcheringa2022.ui.theme.*

class team : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding
    val devteam= listOf(
        member(R.drawable.vipin,"Vipin Jaluthria","Head","https://www.instagram.com/vip_itd/","https://www.facebook.com/vipin.jaluthria.9/","https://www.linkedin.com/in/vipinjaluthria/")
        ,member(R.drawable.grid,"Shreya Goel","Core Team Member","https://www.instagram.com/shreyagoel2706/","https://www.facebook.com/shreya.goel.7399","https://www.linkedin.com/in/shreyagoel/")
        ,member(R.drawable.nitish,"Nitish Singh Chauhan","Executive")
        ,member(R.drawable.atharva,"Atharva Tagalpallewar","Executive","https://www.instagram.com/atagalpallewar/",lnkdurl ="https://www.linkedin.com/in/atharva-tagalpallewar/")
    )
    val desteam= listOf(
        member(R.drawable.fahim,"Mohammed Fahim","Head","https://www.instagram.com/faahym/","https://www.facebook.com/faahym","https://www.linkedin.com/in/faahym/")
        ,member(R.drawable.rishikesh,"Rishikesh Aryan C","Executive","https://instagram.com/rishhiiikesh","https://www.facebook.com/Rishhiiikesh","https://www.linkedin.com/in/rishhiiikesh")
        ,member(R.drawable.bodh,"Tsewang Bodh","Executive","https://www.instagram.com/tsewang.png/", lnkdurl = "https://www.linkedin.com/in/tsewang-bodh-7b20a1210/")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.constraintLayout.setOnClickListener { finish() }
        binding.backbtn.setOnClickListener { finish() }
        binding.teamcp.setContent {
            Alcheringa2022Theme(){

                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(70.dp))
                    }

                    item {
                        Text(
                            text = "Made with ❤ by,",
                            fontWeight = FontWeight.W700,
                            fontSize = 16.sp,
                            fontFamily = aileron,
                            color = colors.onBackground,
                            modifier = Modifier.padding(top = 30.dp)
                        )
                    }
                    item {
                        Text(
                            text = "DEVELOPERS",
                            fontWeight = FontWeight.W400,
                            fontSize = 28.sp,
                            fontFamily = star_guard,
                            color = colors.onBackground,
                            modifier = Modifier.padding(bottom = 8.dp, top = 22.dp)
                        )
                    }
                    items(devteam) { data ->
                        teamCard(memb = data)
                    }
                    item {
                        Text(
                            text = "DESIGN",
                            fontWeight = FontWeight.W400,
                            fontSize = 28.sp, fontFamily = star_guard, color = colors.onBackground,
                            modifier = Modifier.padding(bottom = 8.dp, top = 22.dp)
                        )
                    }
                    items(desteam) { data ->
                        teamCard(memb = data)
                    }
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
    @Composable
    fun teamCard(memb:member=desteam[1]){
        val animationProgress = remember {Animatable(700f)}
        LaunchedEffect(key1=Unit,block = {
            animationProgress.animateTo(
                targetValue = 0f,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            )
        })
        Box(Modifier
            .graphicsLayer { translationX = animationProgress.value }
            .height(114.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(colors.background)
            .border(width = 1.dp, color = Color(0xff464646), shape = RoundedCornerShape(18.dp))
            .padding(17.dp), contentAlignment = Alignment.Center) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Image(modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(9.dp)), painter = painterResource(id = memb.imgdrw), contentDescription = null)

                Spacer(modifier = Modifier.width(24.dp))
                var headfont=16.sp
                if (memb.name.contains("Atharva")){headfont=16.sp}
                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                    Text(text = memb.name, fontSize = headfont, fontFamily = aileron, fontWeight = FontWeight.W700, color = colors.onBackground)

                    Text(text = memb.pos, fontSize = 14.sp, fontFamily = aileron, fontWeight = FontWeight.W400, color = Color(0xffACACAC))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(Modifier.wrapContentWidth(), horizontalArrangement = Arrangement.spacedBy(36.dp)) {
                        if (memb.instaurl!=""){
                            Icon(
                                painter = painterResource(id = R.drawable.ic_instagram),
                                tint = Color(0xff656565),
                                contentDescription =null,
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(24.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(Intent.ACTION_VIEW).setData(
                                                Uri.parse(memb.instaurl)
                                            )
                                        )
                                    }
                            )

                        }
                        if (memb.fburl!=""){
                            Icon(
                                painter = painterResource(id = R.drawable.ic_facebook) ,
                                contentDescription =null ,
                                Modifier
                                    .height(24.dp)
                                    .width(24.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(Intent.ACTION_VIEW).setData(
                                                Uri.parse(memb.fburl)
                                            )
                                        )
                                    },
                                tint = Color(0xff656565)
                            )

                        }
                        if (memb.lnkdurl!=""){
                            Icon(
                                painter = painterResource(id = R.drawable.linkedin) ,
                                contentDescription =null,
                                Modifier
                                    .height(24.dp)
                                    .width(24.dp)
                                    .clickable {
                                        startActivity(
                                            Intent(Intent.ACTION_VIEW).setData(
                                                Uri.parse(memb.lnkdurl)
                                            )
                                        )
                                    },
                                tint = Color(0xff656565)
                            )
                        }
                    }

                }
            }
        }
    }
}