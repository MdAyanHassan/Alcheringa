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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alcheringa.alcheringa2022.Model.OrdersModel
import com.alcheringa.alcheringa2022.Model.YourOrders_model
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
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
                            text = "Orders",
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
                    ) {
                        LazyColumn(){
                            items(yourOrders_modelList.size){
                                OrdersCard(orderDetail = yourOrders_modelList[it])
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    fun OrdersCard(orderDetail: OrdersModel){
        Column {
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(colors.background, RoundedCornerShape(4.dp))
                    .border(1.dp, lighterPurple, RoundedCornerShape(4.dp))
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
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 18.sp,
                            fontFamily = futura,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .align(Alignment.Start)
                        )
                        Text(
                            text = "${orderDetail.merch_size}, ${orderDetail.merch_quantity} Qty",
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 14.sp,
                            fontFamily = futura,
                            modifier = Modifier
                                .align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Row (
                            horizontalArrangement = Arrangement.End
                        ){
                            Text(
                                text = orderDetail.status,
                                color =  colors.onBackground,
                                fontSize = 14.sp,
                                fontFamily = futura,
                                fontWeight = FontWeight.Medium
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