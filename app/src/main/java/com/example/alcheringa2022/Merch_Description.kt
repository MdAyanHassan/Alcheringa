package com.example.alcheringa2022

import androidx.appcompat.app.AppCompatActivity
import com.example.alcheringa2022.DBHandler
import com.example.alcheringa2022.Model.Merch_model
import android.widget.TextView
import android.os.Bundle
import com.example.alcheringa2022.R
import android.content.Intent

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.alcheringa2022.Cart
import com.example.alcheringa2022.ui.theme.hk_grotesk
import com.google.accompanist.pager.*
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.absoluteValue

class Merch_Description : AppCompatActivity(), View.OnClickListener {
    lateinit var small_btn: Button
    lateinit var medium_btn: Button
    lateinit var large_btn: Button
    lateinit var xlarge_btn: Button
    var merch_size = "S"
    lateinit var buy_now: Button
    lateinit var add_to_cart: Button
    lateinit var dbHandler: DBHandler
    lateinit var merch_model: Merch_model
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
        merch_model = intent.extras!!.getParcelable("item")!!
        small_btn = findViewById(R.id.small_size)
        medium_btn = findViewById(R.id.media_size)
        large_btn = findViewById(R.id.large_size)
        xlarge_btn = findViewById(R.id.xlarge_size)
        buy_now = findViewById(R.id.buy_now)
        add_to_cart = findViewById(R.id.add_to_cart)
        name = findViewById(R.id.merch_name)
        type = findViewById(R.id.merch_type)
        price = findViewById(R.id.merch_price)
        description = findViewById(R.id.merch_description)
        small_btn.setOnClickListener(this)
        medium_btn.setOnClickListener(this)
        large_btn.setOnClickListener(this)
        xlarge_btn.setOnClickListener(this)
        buy_now.setOnClickListener(this)
        add_to_cart.setOnClickListener(this)
        cart.setOnClickListener(this)
        dbHandler = DBHandler(this)
        small_btn.setVisibility(View.GONE)
        large_btn.setVisibility(View.GONE)
        medium_btn.setVisibility(View.GONE)
        xlarge_btn.setVisibility(View.GONE)


        val cv1:ComposeView= findViewById(R.id.cv1)
        cv1.setContent {
            horizontalpager(merModel = merch_model)
        }
        val backbtn:ImageButton=findViewById(R.id.backbtn)
        backbtn.setOnClickListener{finish()}


        /// setting buttons
        setting_buttons()
        populate_details()
        //dbHandler.Delete_all();
    }

    private fun populate_details() {
        name.text = merch_model.name_hoddie
        type!!.text = merch_model!!.material
        price!!.text = "₹ " + merch_model!!.price + ".00"
        description!!.text = merch_model!!.description
    }

    private fun setting_buttons() {
        if (merch_model!!.small) {
            small_btn!!.visibility = View.VISIBLE
        }
        if (merch_model!!.large) {
            large_btn!!.visibility = View.VISIBLE
        }
        if (merch_model!!.medium) {
            medium_btn!!.visibility = View.VISIBLE
        }
        if (merch_model!!.large) {
            xlarge_btn!!.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.small_size -> {
                deselect_size()
                select_size(small_btn)
                merch_size = "S"
            }
            R.id.media_size -> {
                deselect_size()
                select_size(medium_btn)
                merch_size = "M"
            }
            R.id.large_size -> {
                deselect_size()
                select_size(large_btn)
                merch_size = "L"
            }
            R.id.xlarge_size -> {
                deselect_size()
                select_size(xlarge_btn)
                merch_size = "XL"
            }
            R.id.buy_now ->                // dbHandler.addNewitemIncart(merch_model.getName_hoddie(),merch_model.getPrice(),merch_size,"1",merch_model.getImage_url(),merch_model.getMaterial());
                startActivity(Intent(applicationContext, Cart::class.java))
            R.id.add_to_cart -> {
                dbHandler!!.addNewitemIncart(
                        merch_model!!.name_hoddie, merch_model!!.price, merch_size,
                        "1", merch_model!!.image_url, merch_model!!.material,
                        applicationContext
                )
                startActivity(Intent(applicationContext, Cart::class.java))
            }
            R.id.cart -> startActivity(Intent(applicationContext, Cart::class.java))
        }
    }

    private fun select_size(btn: Button?) {
        btn!!.background = getDrawable(R.drawable.merch_size_btn_selected)
        btn.setTextColor(0x000000)
    }

    private fun deselect_size() {
        small_btn!!.background = getDrawable(R.drawable.merch_size_btn_deselect)
        medium_btn!!.background = getDrawable(R.drawable.merch_size_btn_deselect)
        large_btn!!.background = getDrawable(R.drawable.merch_size_btn_deselect)
        xlarge_btn.background = getDrawable(R.drawable.merch_size_btn_deselect)
        small_btn.setTextColor(0xFFFFFF)
        medium_btn.setTextColor(0xFFFFFF)
        large_btn.setTextColor(0xFFFFFF)
        xlarge_btn.setTextColor(0xFFFFFF)
    }



    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun horizontalpager(merModel: Merch_model){
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
                                            Column(Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
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