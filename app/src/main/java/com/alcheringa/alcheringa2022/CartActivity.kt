package com.alcheringa.alcheringa2022

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
import com.alcheringa.alcheringa2022.Database.DBHandler
import com.alcheringa.alcheringa2022.MainActivity.index
import com.alcheringa.alcheringa2022.Model.cartModel
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.bumptech.glide.Glide
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import com.alcheringa.alcheringa2022.ui.theme.*
import kotlinx.coroutines.launch

//new 2024 version of Cart
class CartActivity : AppCompatActivity() {
//    var recyclerView: RecyclerView? = null
//    var cartItemsAdapter: CartItemsAdapter? = null
    var list: List<cartModel>? = null
//    var amount: TextView? = null
    var items: TextView? = null
//    var checkout_btn: Button? = null
    var dbHandler: DBHandler? = null
    var cart: ImageView? = null

    // var cartModelArrayList: ArrayList<cartModel>? = null
  //  var startShopping: Button? = null
    var loaderView: LoaderView? = null

    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            dbHandler = DBHandler(applicationContext)
            var cartModelItems by remember { mutableStateOf(dbHandler!!.readCourses()) }
            //    val items= intArrayOf(1,2,3,4,5)
            var key by remember {
                mutableStateOf(0)
            }
            LaunchedEffect(key)
            {
                Log.d("MainActivity", "recount")
                cartModelItems = dbHandler!!.readCourses()
            }
            var totalAmount: Long by remember {
                mutableStateOf(0)
            }
            var totalItems: Int by remember {
                mutableStateOf(0)
            }
            val gradientColorsPurple = listOf(lighterPurple, darkerPurple)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.08f)
                        .background(darkBar)
                )
                {

                    Row() {
                        Image(painterResource(id = R.drawable.cart_arrow),
                            contentDescription = "cart_arrow",
                            modifier = Modifier
                                .clickable { finish() }
                                .padding(start = 25.dp, top = 24.dp, bottom = 20.dp, end = 7.dp)
                                .size(22.dp))
                        Text(
                            "Cart",
                            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, start = 8.dp),
                            color = lighterPurple,
                            fontFamily = futura,
                            fontWeight = FontWeight(500),
                            fontSize = 26.sp,


                            )

                    }
                }
                LaunchedEffect(cartModelItems, Unit) {
                    Log.d("MainActivity", "launched")

                    calculate_amount(cartModelItems) { it1, it2 ->
                        totalAmount = it1
                        totalItems = it2
                    }


                }
                Divider(
                    modifier = Modifier
                        .height(2.dp)
                        .background(lighterGreen)
                )
                //   cartModelArrayList = dbHandler!!.readCourses()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.95f)
                        .padding(start = 20.dp, end = 20.dp, bottom = 15.dp, top = 15.dp)
                        .clip(RoundedCornerShape(8.dp)),

                    ) {
                    if (cartModelItems.size == 0) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "No Item in Cart", modifier = Modifier.padding(20.dp),
                                color = lightBar,
                                fontFamily = futura,
                                fontWeight = FontWeight(300),
                                fontSize = 25.sp,


                                )
                            Image(
                                painter = painterResource(id = R.drawable.shopping_cart),
                                contentDescription = "Empty Cart",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                    LazyColumn(modifier = Modifier) {

                        itemsIndexed(cartModelItems) { index, item ->
                            var bottomDp = 10.dp
                            if (index == cartModelItems.size - 1) {
                                bottomDp = 90.dp
                            }
                            Card(

                                modifier = Modifier
                                    .padding(0.dp, 10.dp, 0.dp, bottomDp)
                                    .height(210.dp)
                                    .border(
                                        1.dp,
                                        lightBar,
                                        RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)
                                    ),

                                //set card elevation of the card

                                backgroundColor = darkBar,
                            ) {
                                Column(modifier = Modifier) {
                                    Row() {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth(0.46f)


                                        ) {
                                            //   Box(modifier=Modifier.fillMaxHeight().background(color=Color.Blue,shape= RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))){
                                            Image(
                                                painter = painterResource(id = R.drawable.cart_item_bg),
                                                contentDescription = "cart_item_bg",
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(
                                                        RoundedCornerShape(
                                                            topStart = 8.dp,
                                                            bottomStart = 8.dp
                                                        )
                                                    ),
                                                contentScale = ContentScale.Crop
                                            )
                                            //   }
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize(0.8f)
                                                    .align(Alignment.Center)
                                            ) {
                                                Image(
                                                    rememberImagePainter(
                                                        item.image,
                                                        builder = {
                                                            placeholder(R.drawable.white_circle_bg)
                                                        }
                                                    ),
                                                    contentDescription = null,
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            }
                                        }
                                        Box(modifier = Modifier.fillMaxSize()) {

                                            Column(
                                                modifier = Modifier
                                                    .padding(16.dp)
                                                    .fillMaxWidth()
                                            ) {


                                                Text(
                                                    text = item.type!!,
                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                                    color = lightBar, fontFamily = futura,
                                                    fontWeight = FontWeight(400),
                                                    fontSize = 18.sp,
                                                    lineHeight = 24.sp

                                                )
                                                Text(
                                                    text = item.name!!,
                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                                    fontWeight = FontWeight(300),
                                                    fontFamily = futura,
                                                    lineHeight = 18.sp,


                                                    color = lightBar,
                                                    fontSize = 14.sp


                                                )

                                                Spacer(modifier = Modifier.height(10.dp))

                                                Text(
                                                    text = "Rs. " + item.price!! + ".00/-",
                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                                    color = lightBar,
                                                    fontWeight = FontWeight(450),
                                                    fontSize = 18.sp,
                                                    fontFamily = futura,
                                                    lineHeight = 24.sp


                                                    //maxLines = 1,
                                                    //overflow = TextOverflow.Ellipsis,
                                                )
                                                Spacer(modifier = Modifier.height(10.dp))

                                                Row(
                                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                                ) {
                                                    Text(
                                                        text = "Size: ",
                                                        modifier = Modifier,
                                                        color = lightBar,
                                                        fontWeight = FontWeight(300),
                                                        fontSize = 14.sp,
                                                        fontFamily = futura,
                                                        lineHeight = 18.sp


                                                        //maxLines = 1,
                                                        //overflow = TextOverflow.Ellipsis,

                                                    )
                                                    Text(
                                                        text = item.size!!,
                                                        modifier = Modifier,
                                                        color = lightBar,
                                                        fontWeight = FontWeight(400),
                                                        fontFamily = futura,
                                                        lineHeight = 18.sp

                                                        //maxLines = 1,
                                                        //overflow = TextOverflow.Ellipsis,

                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(15.dp))
                                                Row() {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.Center
                                                    ) {

                                                        Box(
                                                            modifier = Modifier
                                                                .clickable {
                                                                    OnDecrementClick(
                                                                        index,
                                                                        cartModelItems
                                                                    )
                                                                    Log.d(
                                                                        "MainActivity",
                                                                        cartModelItems.size.toString()
                                                                    )
                                                                    key++
                                                                }
                                                                .background(
                                                                    color = Color(0xff979797),
                                                                    shape = RoundedCornerShape(
                                                                        topStart = 22.dp,
                                                                        bottomStart = 22.dp,
                                                                        topEnd = 0.dp,
                                                                        bottomEnd = 0.dp
                                                                    )

                                                                )
                                                                .width(40.dp)
                                                                .height(35.dp)
                                                        ) {
                                                            Text(
                                                                "-",
                                                                modifier = Modifier.align(Alignment.Center)
                                                                ,
                                                                fontSize = 28.sp,
                                                                fontFamily = futura,

                                                                color = darkBar
                                                            )
                                                        }
                                                        Box(
                                                            modifier = Modifier
                                                                .padding(horizontal = 1.dp)

                                                                .background(
                                                                    color =
                                                                    Color(0xff979797),
                                                                    shape = RoundedCornerShape(
                                                                        topStart = 0.dp,
                                                                        bottomStart = 0.dp,
                                                                        topEnd = 0.dp,
                                                                        bottomEnd = 0.dp
                                                                    )

                                                                )
                                                                .width(40.dp)
                                                                .height(35.dp),

                                                        ) {
                                                            Text(
                                                                cartModelItems[index].count!!,
                                                                modifier = Modifier.align(Alignment.Center),
                                                                fontSize = 28.sp,
                                                                fontFamily = futura,
                                                                color = darkBar
                                                            )
                                                        }
                                                        Box(
                                                            modifier = Modifier
                                                                .clickable {
                                                                    OnIncrementClick(
                                                                        index,
                                                                        cartModelItems
                                                                    )
                                                                    key++
                                                                }
                                                                .background(
                                                                    color = Color(0xff979797),
                                                                    shape = RoundedCornerShape(
                                                                        topStart = 0.dp,
                                                                        bottomStart = 0.dp,
                                                                        topEnd = 22.dp,
                                                                        bottomEnd = 22.dp
                                                                    )

                                                                )
                                                                .width(40.dp)
                                                                .height(35.dp),

                                                            ) {
                                                            Text(
                                                                "+",
                                                                modifier = Modifier.align(Alignment.Center),
                                                                fontSize = 28.sp,
                                                                fontFamily = futura,
                                                                color = darkBar
                                                            )
                                                        }
                                                    }


                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .align(Alignment.BottomCenter)
                    )
                    {
                        Row() {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.43f)
                                    .fillMaxHeight()
                                    .border(
                                        1.dp,
                                        lightBar,
                                        RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                                    )
                                    .background(
                                        color =
                                        darkBar
                                        ,
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            bottomStart = 8.dp
                                        )

                                    )
                            )
                            {
                                Column(modifier = Modifier.padding(start = 20.dp, top = 12.dp)) {
                                    Text(
                                        text = "Rs. " + totalAmount.toString() + ".00",
                                        color = lightBar,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight(450),
                                        fontFamily = futura
                                    )
                                    Row(verticalAlignment = Alignment.Bottom) {
                                        Text(
                                            text = "Total : ",
                                            color = Color(0xff979797),
                                            fontSize = 19.sp,
                                            fontWeight = FontWeight(400),
                                            fontFamily = futura,

                                            )

                                        Text(
                                            text = totalItems.toString() + " Item(s)",
                                            color = Color(0xff979797),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight(400),
                                            fontFamily = futura,
                                            modifier = Modifier.padding(bottom = 1.5f.dp)
                                        )
                                    }
                                }
                            }
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .border(
                                    1.dp,
                                    lightBar,
                                    RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                                )
                                .clickable {
                                    startActivity(
                                        Intent(
                                            applicationContext, AddAddressActivity::class.java
                                        )
                                    )
                                }
                                .background(
                                    brush = Brush.verticalGradient(gradientColorsPurple),
                                    shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)


                                )) {
                                Text(
                                    text = "Place Order",
                                    color = lightBar,
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight(400),
                                    fontFamily = futura,
                                    modifier = Modifier.align(Alignment.Center)

                                )
                            }

                        }
                    }

                }


            }

        }
//        list = ArrayList()
//
//        if (cartModelArrayList!!.size == 0) {
//            setContentView(R.layout.empty_shopping_cart)
//            startShopping = findViewById(R.id.start_shopping)
//            startShopping!!.setOnClickListener(View.OnClickListener { v: View? ->
//                val i = Intent(this, MainActivity::class.java)
//                i.putExtra("fragment", "merch")
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//                startActivity(i)
//                finish()
//            })
//        } else {
//            setContentView(R.layout.activity_cart)
//            amount = findViewById(R.id.order_total_value)
//            items = findViewById(R.id.item_counter)
//            checkout_btn = findViewById(R.id.checkout_button)
//            recyclerView = findViewById(R.id.cart_recyclerview)
//            recyclerView!!.setLayoutManager(LinearLayoutManager(this))
//            recyclerView!!.setHasFixedSize(true)
//            populate_cart()
//            calculate_amount()
//            checkout_btn!!.setOnClickListener(View.OnClickListener { v: View? ->
//                startActivity(
//                    Intent(
//                        applicationContext, AddAddressActivity::class.java
//                    )
//                )
//            })
//
//            /*loaderView = findViewById(R.id.dots_progress);
//            loaderView.setVisibility(View.GONE);*/
//        }
//        val backBtn = findViewById<ImageButton>(R.id.backbtn)
//        backBtn.setOnClickListener { v: View? -> finish() }
    }

    private fun calculate_amount(
        cartModelList: ArrayList<cartModel>,
        onTotalAmount: (Long, Int) -> Unit
    ) {
        var amt: Long = 0
        var item_count: Int = 0
        if (cartModelList.size > 0) {
            for (i in cartModelList.indices) {
                //Toast.makeText(getApplicationContext(), ""+ cartModelArrayList.get(i).getCount(), Toast.LENGTH_SHORT).show();
                amt =
                    amt + cartModelList!![i].price!!.toLong() * cartModelList!![i].count!!.toLong()
                item_count = item_count + cartModelList!![i].count!!.toInt()
            }
            Log.d("MainCart", amt.toString())
            //  total_amount = "Rs. $amt."
            onTotalAmount.invoke(amt, item_count)
            //  amount!!.text = total_amount
            //  items!!.text = "$item_count items"}
        } else {
            onTotalAmount.invoke(0, 0)
        }
    }

//    private fun populate_cart() {
//        cartItemsAdapter = CartItemsAdapter(cartModelArrayList!!, this, applicationContext)
//        recyclerView!!.adapter = cartItemsAdapter
//    }

//     fun OnAnyClick(position: Int) {}
//     fun Onclick(position: Int,cartModelArrayList:ArrayList<cartModel>) {
//        dbHandler!!.DeleteItem(
//            cartModelArrayList!![position].name,
//            cartModelArrayList!![position].size
//        )
//        cartModelArrayList!!.remove(cartModelArrayList!![position])
////        cartItemsAdapter = CartItemsAdapter(cartModelArrayList!!, this, applicationContext)
////        recyclerView!!.adapter = cartItemsAdapter
//      //  calculate_amount()
//  //      refreshView()
//    }

    fun OnIncrementClick(position: Int, cartModelArrayList: ArrayList<cartModel>) {
        var count = cartModelArrayList!![position].count!!.toInt()
        count++
        //cartModelArrayList!![position].count = count.toString() + ""
        dbHandler!!
            .addNewitemIncart(
                cartModelArrayList!![position].name,
                cartModelArrayList!![position].price,
                cartModelArrayList!![position].size,
                cartModelArrayList!![position].count,
                cartModelArrayList!![position].image,
                cartModelArrayList!![position].type, applicationContext
            )
        //  cartItemsAdapter!!.notifyDataSetChanged()
        //  calculate_amount()
        //refreshView()
    }

    fun OnDecrementClick(position: Int, cartModelArrayList: ArrayList<cartModel>) {
        var count = cartModelArrayList!![position].count!!.toInt()
        if (count > 1) {
            count--
            //   cartModelArrayList!![position].count = count.toString() + ""
            dbHandler!!.RemoveFromCart(
                cartModelArrayList!![position].name, cartModelArrayList!![position].price,
                cartModelArrayList!![position].size, cartModelArrayList!![position].count
            )
            //  cartItemsAdapter!!.notifyDataSetChanged()
            //calculate_amount()
        } else if (count == 1) {
            count--
//            dbHandler!!.RemoveFromCart(
//                cartModelArrayList!![position].name, cartModelArrayList!![position].price,
//                cartModelArrayList!![position].size, cartModelArrayList!![position].count
//            )
            dbHandler!!.DeleteItem(
                cartModelArrayList!![position].name,
                cartModelArrayList!![position].size
            )
            //           cartModelArrayList!!.remove(cartModelArrayList!![position])
//            cartItemsAdapter = CartItemsAdapter(cartModelArrayList!!, this, applicationContext)
//            recyclerView!!.adapter = cartItemsAdapter
            //    calculate_amount()
        }
        //refreshView()
    }

//    private fun refreshView() {
//        if (cartModelArrayList!!.size == 0) {
//            setContentView(R.layout.empty_shopping_cart)
//            startShopping = findViewById(R.id.start_shopping)
//            startShopping!!.setOnClickListener(View.OnClickListener { v: View? ->
//                val i = Intent(this, MainActivity::class.java)
//                i.putExtra("fragment", "merch")
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//                startActivity(i)
//                finish()
//            })
//            val backBtn = findViewById<ImageButton>(R.id.backbtn)
//            backBtn.setOnClickListener { v: View? -> finish() }
//        }
//    }
}