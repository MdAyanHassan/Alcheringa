package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.alcheringa.alcheringa2022.ui.theme.aileron
import com.alcheringa.alcheringa2022.ui.theme.borderdarkpurple
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.creamWhite
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.darkerGreen

@Composable
fun GreetingPage(onLoginClick: () -> Unit, onSignupClick: () -> Unit ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.onBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .height(50.dp)
                .border(
                    1.dp,
                    MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(5.dp),
                )
                .background(
                    brush = Brush.verticalGradient(
                        0f to containerPurple,
                        1f to borderdarkpurple
                    ),
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable {
                    onLoginClick()
                }
                .fillMaxWidth()


        ) {
            Row(
                modifier = Modifier
                    .align(
                        Alignment.Center
                    )
                    .width(150.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = aileron,
                    color = creamWhite,
                )

            }


        }


        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .height(50.dp)
                .border(
                    1.dp,
                    MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(5.dp),
                )
                .background(
                    brush = Brush.verticalGradient(
                        0f to darkTealGreen,
                        1f to darkerGreen
                    ),
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable {
                    onSignupClick()
                }
                .fillMaxWidth()



        ) {
            Row(
                modifier = Modifier
                    .align(
                        Alignment.Center
                    )
                    .width(150.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = aileron,
                    color = creamWhite,
                )

            }
        }

    }
}

@Preview
@Composable
fun GreetingPagePreview() {
    GreetingPage({} , {})
}