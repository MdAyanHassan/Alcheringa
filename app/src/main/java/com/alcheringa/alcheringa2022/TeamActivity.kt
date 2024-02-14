package com.alcheringa.alcheringa2022

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alcheringa.alcheringa2022.Model.member
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.lighterPurple

class TeamActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Alcheringa2022Theme {

                val devteam= listOf(
                    member(
                        R.drawable.shanta,
                        "Shantanu Chaudhari",
                        "Head",
                        "https://www.instagram.com/krab_shanta/",
                        "shantanuc1111@gmail.com",
                        lnkdurl = "https://www.linkedin.com/in/shanta11")
                    , member(
                        R.drawable.jivesh,
                        "Jivesh Firke",
                        "Executive",
                        "https://www.instagram.com/jiveshfirke/",
                        "jiveshfirke4749@gmail.com" ,
                        lnkdurl = "https://www.linkedin.com/in/jiveshfirke")
                    , member(
                        R.drawable.simon,
                        "Simon Shangpliang",
                        "Executive",
                        "https://www.instagram.com/extremely_cool_cuber/",
                        "simonrema123@gmail.com",
                        lnkdurl = "https://in.linkedin.com/in/simon-lalremsiama-shangpliang-65130b287"),
                    member(
                        R.drawable.mayank,
                        "Mayank Choudhary",
                        "Executive",
                        "https://www.instagram.com/mayank_chowdhry?igsh=NzJxZWZwYmZ2YnI3",
                        "choudhary.mayank1310@gmail.com",
                        lnkdurl = "https://www.linkedin.com/in/mayank-choudhary-333a8a260"),
                    member(
                        R.drawable.rupayan,
                        "Rupayan Daripa",
                        "Executive",
                        "https://www.instagram.com/rupayandaripa?igsh=cmJhaDlsajdoOGd1",
                        "rupayandaripa@gmail.com",
                        lnkdurl = "https://www.linkedin.com/in/rupayan-daripa-0ba98a261?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"),
                    member(
                        R.drawable.aradhya,
                        "Aradhya",
                        "Executive",
                        "https://www.instagram.com/the_aradhs?igsh=a2gwbzdkMjJkM2Ix",
                        "a.aradhya@iitg.ac.in",
                        lnkdurl = "https://www.linkedin.com/in/aradhya-singh-843b71261?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app")
                )
                val desteam = listOf(
                    member(
                        R.drawable.ayush,
                        "Swapnil Banerjee",
                        "Head",
                        "https://www.instagram.com/mosaicoswap",
                        "banerjeeswapnil2003@gmail.com",
                        "https://www.linkedin.com/in/swapnil-banerjee-2650ab1a1/"
                    ),
                    member(
                        R.drawable.sai,
                        "Sai Ejagar",
                        "Executive",
                        "https://www.instagram.com/sai.e_e?igsh=MXU0Ym1kNHVmb3E4eg%3D%3D&utm_source=q",
                        "e.sai@iitg.ac.in",
                        "https://www.linkedin.com/in/sai-ejagar-823824253?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app"
                    ),
                    member(
                        R.drawable.anushka,
                        "Anushka Mittal",
                        "Executive",
                        "https://www.instagram.com/anushka_mittal_?igsh=ZXNsdmhmdW05dmx5",
                        "anushkamittal0515@gmail.com",
                        lnkdurl = "https://www.linkedin.com/in/anushka-mittal-b6b049216?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"
                    )
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
                            text = "Team",
                            fontFamily = futura,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            color = containerPurple
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
                            )
                    ) {
                        LazyColumn() {
                            item{
                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = "Developers",
                                    fontSize = 18.sp,
                                    fontFamily = futura,
                                    color = colors.onBackground
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                            }
                            items(devteam.size) {
                                TeamCard(

                                    imgInt = devteam[it].imgdrw,
                                    name = devteam[it].name,
                                    position = devteam[it].pos,
                                    instaurl = devteam[it].instaurl,
                                    lnkurl = devteam[it].lnkdurl,
                                    mail = devteam[it].mail
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = "Designers",
                                    fontSize = 18.sp,
                                    fontFamily = futura,
                                    color = colors.onBackground
                                )
                                
                                Spacer(modifier = Modifier.height(10.dp))
                            }

                            items(desteam.size) {
                                TeamCard(
                                    imgInt = desteam[it].imgdrw,
                                    name = desteam[it].name,
                                    position = desteam[it].pos,
                                    instaurl = desteam[it].instaurl,
                                    lnkurl = desteam[it].lnkdurl,
                                    mail = desteam[it].mail
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TeamCard(imgInt: Int, name: String, position: String, instaurl: String, lnkurl: String, mail: String){
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(MaterialTheme.colors.background, RoundedCornerShape(4.dp))
                    .border(1.dp, lighterPurple, RoundedCornerShape(4.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = imgInt),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(4.dp))
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = name,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 18.sp,
                            fontFamily = futura,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .align(Alignment.Start)
                        )
                        Text(
                            text = position,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 14.sp,
                            fontFamily = futura,
                            modifier = Modifier
                                .align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Row {
                            if (instaurl != "") {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_instagram),
                                    contentDescription = null,
                                    tint = lighterPurple,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable { insopen(instaurl) }
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            if (lnkurl != "") {
                                Icon(
                                    painter = painterResource(id = R.drawable.linkedin),
                                    contentDescription = null,
                                    tint = lighterPurple,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable { lnkopen(lnkurl) }
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            if (mail != "") {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_email_icon),
                                    contentDescription = null,
                                    tint = lighterPurple,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable { mailing(mail) }
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

        }
    }

    fun lnkopen(url: String){
        startActivity(
            Intent(Intent.ACTION_VIEW).setData(
                Uri.parse(url)
            )
        )
    }

    fun insopen(url: String){
        startActivity(
            Intent(Intent.ACTION_VIEW).setData(
                Uri.parse(url)
            )
        )
    }

    fun mailing(mailTo: String){
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("mailto:$mailTo")
        )
        startActivity(intent)
    }
}