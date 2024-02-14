package com.alcheringa.alcheringa2022

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.lighterPurple

class ContactUsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Alcheringa2022Theme {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
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
                            text = "Contact Us",
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
                        Spacer(modifier = Modifier.height(15.dp))

                        ContactCard(imgInt = R.drawable.siddhant, name = "Anurag", position = "Pr & Branding", phoneno = "9864558336", mail = "anurag@alcheringa.in")
                        ContactCard(imgInt = R.drawable.siddhant, name = "Ankit Kumar", position = "Pr & Branding", phoneno = "8092267185", mail = "ankit.k@alcheringa.in")
                        ContactCard(imgInt = R.drawable.siddhant, name = "Lakshya Kohli", position = "Pr & Branding", phoneno = "7082763383", mail = "lakshya@alcheringa.in")
                    }
                }
            }
        }
    }


    @Composable
    fun ContactCard(imgInt: Int, name: String, position: String, phoneno: String, mail: String){
        Column {
            Spacer(modifier = Modifier.height(15.dp))
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
                            Icon(
                                painter = painterResource(id =R.drawable.ic_email_icon),
                                contentDescription = null,
                                tint = lighterPurple,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { mailing(mail)}
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            Icon(
                                painter = painterResource(id =R.drawable.outline_call_24),
                                contentDescription = null,
                                tint = lighterPurple,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { Calling(phoneno) }
                            )
                        }
                    }
                }
            }
        }
    }




    fun Calling(number: String){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.setData(Uri.parse("tel:+91$number"))
        startActivity(intent)
    }

    fun mailing(mailTo: String){
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("mailto:$mailTo")
        )
        startActivity(intent)
    }
}