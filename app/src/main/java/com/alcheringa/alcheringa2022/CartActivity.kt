package com.alcheringa.alcheringa2022

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.alcheringa.alcheringa2022.Database.DBHandler
import com.alcheringa.alcheringa2022.Model.cartModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CartLayout2025(
    dbHandler: DBHandler?,
    onFinish: () -> Unit,
    onDecrementClick: (Int, ArrayList<cartModel>) -> Unit,
    onIncrementClick: (Int, ArrayList<cartModel>) -> Unit,
    startCheckout: () -> Unit,
    calculateAmount: (ArrayList<cartModel>, (Long, Int) -> Unit) -> Unit
) {
//    // Sample
//    val cartModelItems = listOf(
//        cartModel(
//            price = "450",
//            type = "Sweatshirt",
//            name = "Crazy eyes",
//            size = "L",
//            image = "",
//            count = "2",
//            image1 = ""
//        )
//    )
    var cartModelItems by remember { mutableStateOf(dbHandler!!.readCourses()) }
    var totalAmount: Long by remember {
        mutableStateOf(0)
    }
    var totalItems: Int by remember {
        mutableStateOf(0)
    }

    var key by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key) {
        Log.d("MainActivity", "recount")
        cartModelItems = dbHandler!!.readCourses()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.cart_bg),
                contentScale = ContentScale.Crop
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .paint(
                    painterResource(id = R.drawable.cart_header_bg),
                    contentScale = ContentScale.Crop
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    onFinish()
                },
                modifier = Modifier
                    .padding(start = 10.dp, top = 4.dp, bottom = 4.dp)
                    .size(48.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.back_button_art),
                    contentDescription = "Back Button",
                    tint = Color.Unspecified
                )

            }

            Text(
                text = "CART",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 22.sp,
                    //fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF77A8),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.alcher_pixel, FontWeight.Normal),
                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                    )
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(12.dp)
            )
        }

        LaunchedEffect(cartModelItems, Unit) {
            Log.d("MainActivity", "launched")

            calculateAmount(cartModelItems) { it1, it2 ->
                totalAmount = it1
                totalItems = it2
            }


        }

        Column(
            modifier = Modifier
                .width(300.dp)
                .padding(28.dp)
                .align(Alignment.CenterHorizontally)
                .paint(
                    painterResource(id = R.drawable.price_item_count_bg)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Rs. $totalAmount",
                lineHeight = 24.sp,
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFF1E8),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.alcher_pixel, FontWeight.Normal),
                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                    ),
                    shadow = Shadow(
                        color = Color(0xFF000000),
                        offset = Offset(6f, 4f)
                    )
                ),
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp)
            )

            Text(
                text = "Total : $totalItems Item(s)",
                lineHeight = 24.sp,
                style = TextStyle(
                    fontSize = 18.sp,
                    //fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFF1E8),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.alcher_pixel, FontWeight.Normal),
                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                    ),
                    shadow = Shadow(
                        color = Color(0xFF000000),
                        offset = Offset(3f, 2f)
                    )
                ),
                letterSpacing = 1.sp,

                modifier = Modifier
                    .padding(bottom = 4.dp)

            )

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 15.dp)
        ) {

            LazyColumn(
                modifier = Modifier
            ) {
                itemsIndexed(cartModelItems) { index, item ->

                    val bottomDp = if (index == cartModelItems.size - 1) 90.dp else 10.dp

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = bottomDp)
                            .paint(
                                painterResource(id = R.drawable.cart_item_new_bg)
                            ),
                        backgroundColor = Color.Transparent
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val painter = rememberImagePainter(
                                data = item.image,
                                builder = {
                                    placeholder(R.drawable.white_circle_bg)
                                    //error(R.drawable.ic_twitter)
                                    crossfade(true)
                                }
                            )
//                            Image(
//                                painter = painterResource(id = R.drawable.merch2024),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .fillMaxWidth(0.5f)
//                                    .padding(8.dp)
//                                    .background(Color.Red),
//                                contentScale = ContentScale.Crop
//                            )
                            Log.d("MainActivity", "image: ${item.image}")

                            if (painter.state is ImagePainter.State.Error) {
                                Log.e("ImageLoading", "Error loading image: ${item.image}")
                            }
                            else {
                                Log.d("ImageLoading", "Image loaded successfully: ${item.image}")
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.45f)
                                    .padding(start = 16.dp, bottom = 8.dp, top = 16.dp)
                                    .paint(
                                        painterResource(id = R.drawable.product_image_bg)
                                    )
                            ) {
                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(116.dp)
                                        .align(Alignment.Center),
                                    contentScale = ContentScale.Fit
                                )

                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = item.type!!,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = Color(0xFFFFF1E8),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        ),
                                        shadow = Shadow(
                                            color = Color(0xFF000000),
                                            offset = Offset(3f, 2f)
                                        )
                                    ),
                                )
                                Text(
                                    text = item.name!!,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xFFFF77A8),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                )
                                Text(
                                    text = "Rs. ${item.price!!}.00/-",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = Color(0xFFFFF1E8),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                )
                                Text(
                                    text = "Size: ${item.size!!}",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        color = Color(0xFFFFF1E8),
                                        fontFamily = FontFamily(
                                            Font(R.font.alcher_pixel, FontWeight.Normal),
                                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    IconButton(
                                        onClick = {
                                            onDecrementClick(
                                                index,
                                                cartModelItems
                                            )
                                            key++
                                        },
                                        modifier = Modifier
                                            .size(36.dp)
                                    ) {

                                        Icon(
                                            painter = painterResource(id = R.drawable.sprite_decrement),
                                            contentDescription = "Decrement",
                                            tint = Color.Unspecified,
                                        )

                                    }

                                    Box(
                                        modifier = Modifier
                                            .height(36.dp)
                                            .width(40.dp)
                                            .border(width = 3.dp, color = Color(0xFF1D2B53))
                                    ) {
                                        Text(
                                            text = cartModelItems[index].count!!,
                                            modifier = Modifier.align(Alignment.Center),
                                            style = TextStyle(
                                                fontSize = 28.sp,
                                                //fontWeight = FontWeight.Bold,
                                                color = Color(0xFFFF77A8),
                                                //textAlign = TextAlign.Center,
                                                fontFamily = FontFamily(
                                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                )
                                            )
                                        )
                                    }


                                    IconButton(
                                        onClick = {
                                            onIncrementClick(
                                                index,
                                                cartModelItems
                                            )
                                            key++
                                        },
                                        modifier = Modifier
                                            .size(36.dp)
                                    ) {

                                        Icon(
                                            painter = painterResource(id = R.drawable.sprite_increment),
                                            contentDescription = "Increment",
                                            tint = Color.Unspecified,
                                        )

                                    }

                                }

                            }
                        }

                    }

                }
            }

            Button(
                enabled = totalItems > 0,
                onClick = {
                    onFinish()
                    startCheckout()
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .paint(
                        painter = painterResource(id = R.drawable.sign_in_button),
                        contentScale = ContentScale.FillBounds
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Buy",
                    style = TextStyle(
                        fontSize = 28.sp,
                        //fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFF1E8),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.alcher_pixel, FontWeight.Normal),
                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                        ),
                        shadow = Shadow(
                            color = Color(0xFF000000),
                            offset = Offset(3f, 2f)
                        )
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                )
            }

        }




    }
}

@Preview
@Composable
fun CartLayout2025Preview() {
    CartLayout2025(null, {}, { _, _ -> }, { _, _ -> }, {}, { _, _ -> })
}
//new 2024 version of Cart
class CartActivity : AppCompatActivity() {
//    var recyclerView: RecyclerView? = null
//    var cartItemsAdapter: CartItemsAdapter? = null
    //var list: List<cartModel>? = null
//    var amount: TextView? = null
    //var items: TextView? = null
//    var checkout_btn: Button? = null
    var dbHandler: DBHandler? = null
   // var cart: ImageView? = null

    // var cartModelArrayList: ArrayList<cartModel>? = null
  //  var startShopping: Button? = null
   // var loaderView: LoaderView? = null

    @OptIn(
        ExperimentalComposeUiApi::class,
        androidx.compose.foundation.ExperimentalFoundationApi::class
    )    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            dbHandler = DBHandler(applicationContext)

            CartLayout2025(
                dbHandler = dbHandler,
                onFinish = {
                    finish()
                },
                onDecrementClick = { it1, it2 ->
                    OnDecrementClick(it1, it2)
                },
                onIncrementClick = { it1, it2 ->
                    OnIncrementClick(it1, it2)
                },
                startCheckout = {
                    startActivity(
                        Intent(
                            applicationContext, CheckoutActivity2024::class.java
                        )
                    )
                },
                calculateAmount = { it1, it2 ->
                    calculate_amount(
                        it1,
                        it2
                    )
                }
            )
//            dbHandler = DBHandler(this.applicationContext)
//            var cartModelItems by remember { mutableStateOf(dbHandler!!.readCourses()) }
//            //    val items= intArrayOf(1,2,3,4,5)
//            var key by remember {
//                mutableStateOf(0)
//            }
//            LaunchedEffect(key)
//            {
//                Log.d("MainActivity", "recount")
//                cartModelItems = dbHandler!!.readCourses()
//            }
//            var totalAmount: Long by remember {
//                mutableStateOf(0)
//            }
//            var totalItems: Int by remember {
//                mutableStateOf(0)
//            }
//            val gradientColorsPurple = listOf(lighterPurple, darkerPurple)
//            val context= LocalContext.current
//            var fontCol:Color by remember{ mutableStateOf( if(context.getResources().getConfiguration().uiMode==33)
//            {
//               lightBar
//            }
//            else
//            {
//                darkBar
//            }) }
//            var tempBgCol:Color by remember{ mutableStateOf( if(context.getResources().getConfiguration().uiMode==33)
//            {
//               Color.Black
//            }
//            else
//            {
//              lightBar
//            }) }
//            var surfaceCol:Color by remember{ mutableStateOf( if(context.getResources().getConfiguration().uiMode==33)
//            {
//                darkBar
//            }
//            else
//            {
//              lightBar
//            }) }
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .paint(
//                        painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
//                        contentScale = ContentScale.Crop
//                    )
//
//                // .background()
//            ) {
//                Row(
//                    modifier = Modifier
//                        .background(if (isSystemInDarkTheme()) darkBar else lightBar)
//                        .height(65.dp)
//                        .fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Box() {
//                        Icon(
//                            painter = painterResource(id = R.drawable.cart_arrow),
//                            contentDescription = null,
//                            tint = lighterPurple,
//                            modifier = Modifier
//
//                                .padding(start = 10.dp, end = 10.dp)
//                                .clickable {
//                                    finish()
//                                }
//                                .padding(10.dp)
//                        )
//                    }
//                    Text(
//                        text = "Cart",
//                        fontFamily = futura,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 22.sp,
//                        color = containerPurple
//                    )
//                }
//                LaunchedEffect(cartModelItems, Unit) {
//                    Log.d("MainActivity", "launched")
//
//                    calculate_amount(cartModelItems) { it1, it2 ->
//                        totalAmount = it1
//                        totalItems = it2
//                    }
//
//
//                }
//                Divider(
//                    modifier = Modifier
//                        .height(2.dp)
//                        .background(lighterGreen)
//                )
//                //   cartModelArrayList = dbHandler!!.readCourses()
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(0.95f)
//                        .padding(start = 20.dp, end = 20.dp, bottom = 15.dp, top = 15.dp)
//                        .clip(RoundedCornerShape(8.dp)),
//
//                    ) {
//                    if (cartModelItems.size == 0) {
//                        Column(
//                            modifier = Modifier.fillMaxSize(),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Text(
//                                "No Item in Cart", modifier = Modifier.padding(20.dp),
//                                color =fontCol,
//                                fontFamily = futura,
//                                fontWeight = FontWeight(300),
//                                fontSize = 25.sp,
//
//
//                                )
//                            Image(
//                                painter = painterResource(id = R.drawable.shopping_cart),
//                                contentDescription = "Empty Cart",
//                                modifier = Modifier.size(100.dp)
//                            )
//                        }
//                    }
//                    LazyColumn(modifier = Modifier) {
//
//                        itemsIndexed(cartModelItems) { index, item ->
//                            var bottomDp = 10.dp
//                            if (index == cartModelItems.size - 1) {
//                                bottomDp = 90.dp
//                            }
//                            Card(
//
//                                modifier = Modifier
//                                    .padding(0.dp, 10.dp, 0.dp, bottomDp)
//                                    .height(210.dp)
//                                    .border(
//                                        1.dp,
//                                        fontCol,
//                                        RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)
//                                    ),
//
//                                //set card elevation of the card
//
//                                backgroundColor = surfaceCol,
//                            ) {
//                                Column(modifier = Modifier) {
//                                    Row() {
//                                        Box(
//                                            modifier = Modifier
//                                                .fillMaxHeight()
//                                                .fillMaxWidth(0.46f)
//
//
//                                        ) {
//                                            //   Box(modifier=Modifier.fillMaxHeight().background(color=Color.Blue,shape= RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))){
//                                            Image(
//                                                painter = painterResource(id = R.drawable.cart_item_bg),
//                                                contentDescription = "cart_item_bg",
//                                                modifier = Modifier
//                                                    .fillMaxHeight()
//                                                    .clip(
//                                                        RoundedCornerShape(
//                                                            topStart = 8.dp,
//                                                            bottomStart = 8.dp
//                                                        )
//                                                    ),
//                                                contentScale = ContentScale.Crop
//                                            )
//                                            //   }
//                                            Box(
//                                                modifier = Modifier
//                                                    .fillMaxSize(0.8f)
//                                                    .align(Alignment.Center)
//                                            ) {
//                                                Image(
//                                                    rememberImagePainter(
//                                                        item.image,
//                                                        builder = {
//                                                            placeholder(R.drawable.white_circle_bg)
//                                                        }
//                                                    ),
//                                                    contentDescription = null,
//                                                    modifier = Modifier.align(Alignment.Center)
//                                                )
//                                            }
//                                        }
//                                        Box(modifier = Modifier.fillMaxSize()) {
//
//                                            Column(
//                                                modifier = Modifier
//                                                    .padding(16.dp)
//                                                    .fillMaxWidth()
//                                            ) {
//
//
//                                                Text(
//                                                    text = item.type!!,
//                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
//                                                    color = fontCol, fontFamily = futura,
//                                                    fontWeight = FontWeight(400),
//                                                    fontSize = 18.sp,
//                                                    lineHeight = 24.sp
//
//                                                )
//                                                Text(
//                                                    text = item.name!!,
//                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
//                                                    fontWeight = FontWeight(300),
//                                                    fontFamily = futura,
//
//
//                                                    color =fontCol,
//                                                    fontSize = 14.sp
//
//
//                                                )
//
//                                                Spacer(modifier = Modifier.height(10.dp))
//
//                                                Text(
//                                                    text = "Rs. " + item.price!! + ".00/-",
//                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
//                                                    color = fontCol,
//                                                    fontWeight = FontWeight(450),
//                                                    fontSize = 18.sp,
//                                                    fontFamily = futura,
//                                                    lineHeight = 24.sp
//
//
//                                                    //maxLines = 1,
//                                                    //overflow = TextOverflow.Ellipsis,
//                                                )
//                                                Spacer(modifier = Modifier.height(10.dp))
//
//                                                Row(
//                                                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                                                ) {
//                                                    Text(
//                                                        text = "Size: ",
//                                                        modifier = Modifier,
//                                                        color =  fontCol,
//                                                        fontWeight = FontWeight(300),
//                                                        fontSize = 14.sp,
//                                                        fontFamily = futura,
//                                                        lineHeight = 18.sp
//
//
//                                                        //maxLines = 1,
//                                                        //overflow = TextOverflow.Ellipsis,
//
//                                                    )
//                                                    Text(
//                                                        text = item.size!!,
//                                                        modifier = Modifier,
//                                                        color = fontCol,
//                                                        fontWeight = FontWeight(400),
//                                                        fontFamily = futura,
//                                                        lineHeight = 18.sp
//
//                                                        //maxLines = 1,
//                                                        //overflow = TextOverflow.Ellipsis,
//
//                                                    )
//                                                }
//                                                Spacer(modifier = Modifier.height(15.dp))
//                                                Row() {
//                                                    Row(
//                                                        modifier = Modifier.fillMaxWidth(),
//                                                        horizontalArrangement = Arrangement.Center
//                                                    ) {
//
//                                                        Box(
//                                                            modifier = Modifier
//                                                                .clickable {
//                                                                    OnDecrementClick(
//                                                                        index,
//                                                                        cartModelItems
//                                                                    )
//                                                                    Log.d(
//                                                                        "MainActivity",
//                                                                        cartModelItems.size.toString()
//                                                                    )
//                                                                    key++
//                                                                }
//                                                                .background(
//                                                                    color = Color(0xff979797),
//                                                                    shape = RoundedCornerShape(
//                                                                        topStart = 22.dp,
//                                                                        bottomStart = 22.dp,
//                                                                        topEnd = 0.dp,
//                                                                        bottomEnd = 0.dp
//                                                                    )
//
//                                                                )
//                                                                .width(40.dp)
//                                                                .height(35.dp)
//                                                        ) {
//                                                            Text(
//                                                                "-",
//                                                                modifier = Modifier.align(Alignment.Center)
//                                                                ,
//                                                                fontSize = 28.sp,
//                                                                fontFamily = futura,
//
//                                                                color = surfaceCol
//                                                            )
//                                                        }
//                                                        Box(
//                                                            modifier = Modifier
//                                                                .padding(horizontal = 1.dp)
//
//                                                                .background(
//                                                                    color =
//                                                                    Color(0xff979797),
//                                                                    shape = RoundedCornerShape(
//                                                                        topStart = 0.dp,
//                                                                        bottomStart = 0.dp,
//                                                                        topEnd = 0.dp,
//                                                                        bottomEnd = 0.dp
//                                                                    )
//
//                                                                )
//                                                                .width(40.dp)
//                                                                .height(35.dp),
//
//                                                        ) {
//                                                            Text(
//                                                                cartModelItems[index].count!!,
//                                                                modifier = Modifier.align(Alignment.Center),
//                                                                fontSize = 28.sp,
//                                                                fontFamily = futura,
//                                                                color = surfaceCol
//                                                            )
//                                                        }
//                                                        Box(
//                                                            modifier = Modifier
//                                                                .clickable {
//                                                                    OnIncrementClick(
//                                                                        index,
//                                                                        cartModelItems
//                                                                    )
//                                                                    key++
//                                                                }
//                                                                .background(
//                                                                    color = Color(0xff979797),
//                                                                    shape = RoundedCornerShape(
//                                                                        topStart = 0.dp,
//                                                                        bottomStart = 0.dp,
//                                                                        topEnd = 22.dp,
//                                                                        bottomEnd = 22.dp
//                                                                    )
//
//                                                                )
//                                                                .width(40.dp)
//                                                                .height(35.dp),
//
//                                                            ) {
//                                                            Text(
//                                                                "+",
//                                                                modifier = Modifier.align(Alignment.Center),
//                                                                fontSize = 28.sp,
//                                                                fontFamily = futura,
//                                                                color = surfaceCol
//                                                            )
//                                                        }
//                                                    }
//
//
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(70.dp)
//                            .align(Alignment.BottomCenter)
//                    )
//                    {
//                        Row() {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth(0.43f)
//                                    .fillMaxHeight()
//                                    .border(
//                                        1.dp,
//                                        fontCol,
//                                        RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
//                                    )
//                                    .background(
//                                        color =
//                                        surfaceCol,
//                                        shape = RoundedCornerShape(
//                                            topStart = 8.dp,
//                                            bottomStart = 8.dp
//                                        )
//
//                                    )
//                            )
//                            {
//                                Column(modifier = Modifier.padding(start = 20.dp, top = 12.dp)) {
//                                    Text(
//                                        text = "Rs. $totalAmount.00",
//                                        color =  fontCol,
//                                        fontSize = 20.sp,
//                                        fontWeight = FontWeight(450),
//                                        fontFamily = futura
//                                    )
//                                    Row(verticalAlignment = Alignment.Bottom) {
//                                        Text(
//                                            text = "Total : ",
//                                            color = Color(0xff979797),
//                                            fontSize = 19.sp,
//                                            fontWeight = FontWeight(400),
//                                            fontFamily = futura,
//
//                                            )
//
//                                        Text(
//                                            text = "$totalItems Item(s)",
//                                            color = Color(0xff979797),
//                                            fontSize = 15.sp,
//                                            fontWeight = FontWeight(400),
//                                            fontFamily = futura,
//                                            modifier = Modifier.padding(bottom = 1.5f.dp)
//                                        )
//                                    }
//                                }
//
//                            }
//                            var brush:Brush by remember{ mutableStateOf(    Brush.verticalGradient(listOf(Color.Gray, Color.Gray)) // Use Gray brush when there are no items
//                            ) }
//                            if(totalItems==0)
//                            {
//                                brush=   Brush.verticalGradient(listOf(Color.Gray, Color.Gray))
//                            }else
//                            {
//                                brush=  Brush.verticalGradient(gradientColorsPurple)
//                            }
//                            Box(modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight()
//                                .border(
//                                    1.dp,
//                                    fontCol,
//                                    RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
//                                )
//                                .clickable(enabled = totalItems > 0) {
//                                    finish()
//                                    startActivity(
//                                        Intent(
//                                            applicationContext, CheckoutActivity2024::class.java
//                                        )
//                                    )
//                                }
//                                .background(
//                                    brush = brush,
//                                    shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
//
//
//                                )) {
//
//                                Text(
//                                    text = "Place Order",
//                                    color = lightBar,
//                                    fontSize = 23.sp,
//                                    fontWeight = FontWeight(400),
//                                    fontFamily = futura,
//                                    modifier = Modifier.align(Alignment.Center)
//
//                                )
//                            }
//
//                        }
//                    }
//
//                }
//
//
//            }

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