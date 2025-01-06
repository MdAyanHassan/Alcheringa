package com.alcheringa.alcheringa2022

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alcheringa.alcheringa2022.Model.OrdersModel
import com.alcheringa.alcheringa2022.Model.YourOrders_model
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.borderdarkpurple
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.creamWhite
import com.alcheringa.alcheringa2022.ui.theme.darkBar
import com.alcheringa.alcheringa2022.ui.theme.darkGrey
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.grey
import com.alcheringa.alcheringa2022.ui.theme.lightBar
import com.alcheringa.alcheringa2022.ui.theme.lighterPurple
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class YourOrdersActivity: AppCompatActivity() {
    var yourOrders_modelList = mutableStateListOf<OrdersModel>()
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        populate_your_orders()
        setContent {
            Alcheringa2022Theme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.cart_bg),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .height(65.dp)
                            .fillMaxWidth()
                            .paint(
                                painter = painterResource(id = R.drawable.cart_header_bg),
                                contentScale = ContentScale.FillBounds
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(modifier = Modifier) {
                            Icon(
                                painter = painterResource(id = R.drawable.back_button_art),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8555.dp)
                                    .clickable {
                                        finish()
                                    }
                                    .padding(10.dp)
                            )
                        }
                        Text(
                            text = "Orders",
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel),
                                Font(R.font.alcher_pixel_bold)
                            ),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            color = Color(0xFFFF77A8),
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    }

//                    Divider(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(2.dp)
//                            .background(darkTealGreen)
//                    )


                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
//                            .paint(
//                                painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
//                                contentScale = ContentScale.Crop
//                            ),
                    ) {
                        if (yourOrders_modelList.isEmpty()) {

                            Text(
                                text = "Oops! You haven't ordered yet!!",
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel),
                                    Font(R.font.alcher_pixel_bold)
                                ),
                                color = Color(0xFFFFF1E8),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 22.sp
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 15.dp)
                                    .paint(
                                        painterResource(id = R.drawable.sign_in_button),
                                        contentScale = ContentScale.Crop
                                    )
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                this@YourOrdersActivity,
                                                MainActivity::class.java
                                            )
                                        )
                                    }
                                    .align(Alignment.BottomCenter),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "Start shopping",
                                    fontFamily = FontFamily(
                                        Font(R.font.alcher_pixel),
                                        Font(R.font.alcher_pixel_bold)
                                    ),
                                    color = Color(0xFFFFF1E8),
                                    fontSize = 22.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        else{
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 0.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                LazyColumn() {
                                    items(yourOrders_modelList.size) {
                                        OrdersCard(orderDetail = yourOrders_modelList[it])
                                    }
                                    item { 
                                        Spacer(modifier = Modifier.height(20.dp))
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    @Preview
    @Composable
    fun OrdersCardPreview() {
        OrdersCard(orderDetail = OrdersModel(
            status = "Placed",
            merch_name = "Nike Air Max",
            merch_type = "Shoes",
            merch_quantity = "1",
            merch_size = "XL",
            price = "200",
            delivery_date = "03-03-2025",
            Image = ""
        ))
    }

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    fun OrdersCard(orderDetail: OrdersModel){
        Column {
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
//                    .background(colors.background, RoundedCornerShape(4.dp))
                    .border(2.dp, Color(0xFF7E2553))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        rememberImagePainter(data = orderDetail.Image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .padding(15.dp)
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
                            text = orderDetail.merch_name,
                            color = Color(0xFFFFF1E8),
                            fontSize = 22.sp,
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel),
                                Font(R.font.alcher_pixel_bold)
                            ),
                            style = TextStyle(
                                shadow = Shadow(color = Color.Black, offset = Offset(2f, 2f))
                            ),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(2.dp)
                        )

                        Text(
                            text = "Quantity: ${orderDetail.merch_quantity}",
                            color = Color(0xFFFF77A8),
                            fontSize = 18.sp,
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel),
                                Font(R.font.alcher_pixel_bold)
                            ),
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(bottom = 2.dp, start = 2.dp, end = 2.dp)
                        )

                        Text(
                            text = "Size: ${orderDetail.merch_size}",
                            color = Color(0xFFFFF1E8),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel),
                                Font(R.font.alcher_pixel_bold)
                            ),
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(bottom = 2.dp, start = 2.dp, end = 2.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Row (
                            Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ){
                            Text(
                                text = "Order Placed",
                                color =  Color(0xFFFFF1E8),
                                fontSize = 20.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.alcher_pixel),
                                    Font(R.font.alcher_pixel_bold)
                                ),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(1.dp)
                            )
                        }

                    }
                }
            }
        }
    }

    private fun populate_your_orders() {
        val user = firebaseAuth.currentUser!!
        val email = user.email!!
        firestore.collection("USERS").document(email).collection("ORDERS").get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful && !task.result.isEmpty) {
                    for (documentSnapshot in task.result) {
                        val obj =
                            documentSnapshot["orders"] as ArrayList<HashMap<String, Any>>?
                        for (order in obj!!) {
                            yourOrders_modelList.add(
                                OrdersModel(
                                    order["isDelivered"].toString(),
                                    order["Name"].toString(),
                                    order["Type"].toString(),
                                    order["Count"].toString(),
                                    order["Size"].toString(),
                                    order["Price"].toString(),
                                    "12",
                                    order["image"].toString()
                                )
                            )
                        }
                    }
                }
            }
    }
}