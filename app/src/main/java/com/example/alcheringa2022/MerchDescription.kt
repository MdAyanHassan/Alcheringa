package com.example.alcheringa2022

import androidx.appcompat.app.AppCompatActivity
import com.example.alcheringa2022.Model.merchModel
import android.os.Bundle
import android.content.Intent
import android.text.Html
import android.util.Log

import android.view.View
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.alcheringa2022.ui.theme.hk_grotesk
import com.google.accompanist.pager.*
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.absoluteValue

class MerchDescription : AppCompatActivity(), View.OnClickListener {
    lateinit var smallBtn: Button
    lateinit var mediumBtn: Button
    lateinit var largeBtn: Button
    lateinit var xlargeBtn: Button
    var merchSize = "S"
    lateinit var buyNow: Button
    lateinit var addToCart: Button
    lateinit var dbHandler: DBHandler
    lateinit var merchModel: merchModel
    lateinit var name: TextView
    lateinit var type: TextView
    lateinit var price: TextView
    lateinit var description: TextView
    lateinit var cart: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merch_description)
        cart = findViewById(R.id.cart)
        val intent = intent
        merchModel = intent.extras!!.getParcelable("item")!!
        smallBtn = findViewById(R.id.small_size)
        mediumBtn = findViewById(R.id.media_size)
        largeBtn = findViewById(R.id.large_size)
        xlargeBtn = findViewById(R.id.xlarge_size)
        buyNow = findViewById(R.id.buy_now)
        addToCart = findViewById(R.id.add_to_cart)
        name = findViewById(R.id.merch_name)
        type = findViewById(R.id.merch_type)
        price = findViewById(R.id.merch_price)
        description = findViewById(R.id.merch_description)
        smallBtn.setOnClickListener(this)
        mediumBtn.setOnClickListener(this)
        largeBtn.setOnClickListener(this)
        xlargeBtn.setOnClickListener(this)
        buyNow.setOnClickListener(this)
        addToCart.setOnClickListener(this)
        cart.setOnClickListener(this)
        dbHandler = DBHandler(this)
        smallBtn.visibility = View.GONE
        largeBtn.visibility = View.GONE
        mediumBtn.visibility = View.GONE
        xlargeBtn.visibility = View.GONE


        val cv1:ComposeView= findViewById(R.id.cv1)
        cv1.setContent { horizontalpager(merModel = merchModel) }

        val backBtn:ImageButton=findViewById(R.id.backbtn)
        backBtn.setOnClickListener{finish()}


        /// setting buttons
        settingButtons()
        populate_details()
        //dbHandler.Delete_all();
    }

    private fun populate_details() {
        name.text = merchModel.name
        type.text = merchModel.material
        price.text = "₹ " + merchModel.price + "."
        description.text = merchModel.description
    }

    private fun settingButtons() {
        if (merchModel.small) {
            smallBtn.visibility = View.VISIBLE
        }
        if (merchModel.large) {
            largeBtn.visibility = View.VISIBLE
        }
        if (merchModel.medium) {
            mediumBtn.visibility = View.VISIBLE
        }
        if (merchModel.large) {
            xlargeBtn.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.small_size -> {
                deselect_size()
                select_size(smallBtn)
                merchSize = "S"
            }
            R.id.media_size -> {
                deselect_size()
                select_size(mediumBtn)
                merchSize = "M"
            }
            R.id.large_size -> {
                deselect_size()
                select_size(largeBtn)
                merchSize = "L"
            }
            R.id.xlarge_size -> {
                deselect_size()
                select_size(xlargeBtn)
                merchSize = "XL"
            }
            R.id.buy_now -> {
                dbHandler.addNewitemIncart(
                    merchModel.name,
                    merchModel.price,
                    merchSize,
                    "1",
                    merchModel.image_url,
                    merchModel.material,
                    applicationContext
                )
                startActivity(Intent(applicationContext, CartActivity::class.java))
            }
            R.id.add_to_cart -> {
                dbHandler.addNewitemIncart(
                    merchModel.name,
                    merchModel.price,
                    merchSize,
                    "1",
                    merchModel.image_url,
                    merchModel.material,
                    applicationContext
                )
                Toast.makeText(applicationContext, merchModel.name + " added to cart", Toast.LENGTH_SHORT).show()
                //startActivity(Intent(applicationContext, CartActivity::class.java))
            }
            R.id.cart -> startActivity(Intent(applicationContext, CartActivity::class.java))
        }
    }

    private fun select_size(btn: Button?) {
        btn!!.background = AppCompatResources.getDrawable(applicationContext, R.drawable.merch_size_btn_selected)
        btn!!.setTextColor(0x000000)

    }

    private fun deselect_size() {
        smallBtn.background = AppCompatResources.getDrawable(applicationContext, R.drawable.merch_size_btn_deselect)
        mediumBtn.background = AppCompatResources.getDrawable(applicationContext, R.drawable.merch_size_btn_deselect)
        largeBtn.background = AppCompatResources.getDrawable(applicationContext, R.drawable.merch_size_btn_deselect)
        xlargeBtn.background = AppCompatResources.getDrawable(applicationContext, R.drawable.merch_size_btn_deselect)
        smallBtn.setTextColor(0xFFFFFF)
        mediumBtn.setTextColor(0xFFFFFF)
        largeBtn.setTextColor(0xFFFFFF)
        xlargeBtn.setTextColor(0xFFFFFF)
        Log.d("abcd", "deselect_size:")

    }



    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun horizontalpager(merModel: merchModel){
        var images= merModel.images

        Column() {
            val pagerState = rememberPagerState()
            HorizontalPager(
                    count = images.size, modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp), state = pagerState, contentPadding = PaddingValues(horizontal = 20.dp)
            ) { page ->
                Card(
                        Modifier
                                .graphicsLayer {
                                    // Calculate the absolute offset for the current page from the
                                    // scroll position. We use the absolute value which allows us to mirror
                                    // any effects for both directions
                                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                                    // We animate the scaleX + scaleY, between 85% and 100%
                                    lerp(
                                            start = 0.85f,
                                            stop = 1f,
                                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    ).also { scale ->
                                        scaleX = scale
                                        scaleY = scale
                                    }

                                    // We animate the alpha, between 50% and 100%
                                    alpha = lerp(
                                            start = 0.5f,
                                            stop = 1f,
                                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }
                ) {
                    Box() {

                        Card(
                                modifier = Modifier.fillMaxWidth(),

                                elevation = 5.dp
                        ) {
                            Box(
                                    modifier = Modifier
                                        .background(Color.LightGray)
                                        .padding(20.dp)
                                        .height(400.dp)
                                        .fillMaxWidth(), contentAlignment = Alignment.Center
                            ) {
                                GlideImage(
                                        imageModel = images[page],
                                        contentDescription = "artist",
                                        modifier= Modifier
                                            .width(348.dp)
                                            .height(360.dp),
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Crop ,
                                        shimmerParams = ShimmerParams(
                                                baseColor = androidx.compose.ui.graphics.Color.Black,
                                                highlightColor = androidx.compose.ui.graphics.Color.LightGray,
                                                durationMillis = 350,
                                                dropOff = 0.65f,
                                                tilt = 20f
                                        ),failure = {
                                        Box(modifier= Modifier
                                            .fillMaxWidth()
                                            .height(473.dp), contentAlignment = Alignment.Center) {
                                            Column(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                                                Image(
                                                    modifier = Modifier
                                                        .width(60.dp)
                                                        .height(60.dp),
                                                    painter = painterResource(
                                                        id = R.drawable.ic_sad_svgrepo_com
                                                    ),
                                                    contentDescription = null
                                                )
                                                Spacer(modifier = Modifier.height(10.dp))
                                                Text(
                                                    text = "Image Request Failed",
                                                    style = TextStyle(
                                                        color = Color(0xFF747474),
                                                        fontFamily = hk_grotesk,
                                                        fontWeight = FontWeight.Normal,
                                                        fontSize = 12.sp
                                                    )
                                                )
                                            }
                                        }

                                    }

                                )
                            }
                        }
                    }
                }
            }
            HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor= colorResource(id = R.color.textGray),
                    inactiveColor = colorResource(id = R.color.darkGray),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
            )
        }

    }
}