package com.alcheringa.alcheringa2022

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alcheringa.alcheringa2022.Model.member
import com.alcheringa.alcheringa2022.databinding.ActivityTeamBinding
import com.alcheringa.alcheringa2022.ui.theme.*

class team : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding
    val devteam= listOf(
        member(
            R.drawable.nitish,
            "Nitish Singh Chauhan",
            "Head")
        ,member(
            R.drawable.atharva,
            "Atharva Tagalpallewar",
            "Head",
            "https://www.instagram.com/atagalpallewar/",
            "https://www.linkedin.com/in/atharva-tagalpallewar/"),
        member(
            R.drawable.shanta,
            "Shantanu Chaudhari",
            "Executive",
            "https://www.instagram.com/krab_shanta/",
            lnkdurl = "https://www.linkedin.com/in/shanta11")
        ,member(
            R.drawable.krishi,
            "Vedhant Krishi S",
            "Executive",
            "https://www.instagram.com/vedhant.krishi._/",
            lnkdurl = "https://www.linkedin.com/in/vedhant-krishi-74224b21a/")
        ,member(
            R.drawable.shri,
            "Shrivathsa",
            "Executive",
            "https://www.instagram.com/5hri_07/",
            lnkdurl = "https://www.linkedin.com/in/shrivathsa729/"),
        member(
            R.drawable.divyesh,
            "Divyesh Agrawal",
            "Executive",
            "https://www.instagram.com/agarwal_divyesh/",
            lnkdurl = "https://www.linkedin.com/in/divyesh-agarwal-48a750231"),
        member(
            R.drawable.rashmi,
            "Rashmi Bajaj",
            "Executive",
            "https://www.instagram.com/r_bajaj047/",

            lnkdurl = "https://www.linkedin.com/in/rashmi-bajaj-70b386230")
    )
    val desteam = listOf(
        member(
            R.drawable.ayush,
            "Ayush Singh",
            "Head",
            "https://www.instagram.com/sigh.yush/",
            "https://www.facebook.com/profile.php?id=100002442294419",
            "https://www.linkedin.com/in/ayush-singh-5065881a1/"
        ),
        member(
            R.drawable.xork,
            "Kavinash S",
            "Executive",
            "https://www.instagram.com/xorkavi/",
            "https://www.facebook.com/kavinash8/",
            "https://www.linkedin.com/in/kavinas-sundaramurthy-a2abb5226/"
        ),
        member(
            R.drawable.shivam,
            "Shivam Kumar Roy",
            "Executive",
            "https://www.instagram.com/_shivam.roy/",
            lnkdurl = "https://www.linkedin.com/in/shivam-roy1/"
        )
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