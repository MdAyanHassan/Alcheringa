package com.alcheringa.alcheringa2022

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.MaterialTheme.colors

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.DBHandler
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.merchModel
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.ui.theme.*
import com.google.accompanist.pager.*
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firestore.admin.v1.Index
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MerchFragmentCompose.newInstance] factory method to
 * create an instance of this fragment.
 */
class MerchFragmentCompose : Fragment() {
    val TAG = "MerchFragment"

    var recyclerView: RecyclerView? = null
    lateinit var merchModelList: SnapshotStateList<merchModel>
    var firebaseAuth: FirebaseAuth? = null
    lateinit var cartCountIcon: TextView
    lateinit var loaderView: LoaderView
    var firestore: FirebaseFirestore? = null
    lateinit var compose: ComposeView
    val homeViewModel : viewModelHome by activityViewModels()
    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getMerchMerch()
        dbHandler= DBHandler(requireContext())
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_merch_compose, container, false)
        loaderView = view.findViewById<LoaderView>(R.id.dots_progress)


        loaderView.visibility = View.INVISIBLE

        cartCountIcon = view.findViewById(R.id.cart_count2)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        val cart = view.findViewById<ImageView>(R.id.cart2)
        cart.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    activity,
                    CartActivity::class.java
                )
            )
        }
        cartCountIcon.setOnClickListener(View.OnClickListener { v: View? ->
            startActivity(
                Intent(
                    activity,
                    CartActivity::class.java
                )
            )
        })
        val account = view.findViewById<ImageView>(R.id.account2)
        account.setOnClickListener(View.OnClickListener { v: View? ->
            (activity as MainActivity).drawer.openDrawer(Gravity.RIGHT)
        })
        merchModelList = SnapshotStateList()
        //populate_merch()
        setCartCountIcon()



        compose = view.findViewById(R.id.compose1)
        compose.setContent {
            if(homeViewModel.merchMerch.isNotEmpty()){
                MyContent()
            }
        }

        return view
    }

    override fun onResume() {
        setCartCountIcon()
        (activity!!.findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView).menu.findItem(
            R.id.merch
        ).isChecked =
            true
        MainActivity.index = R.id.merch
        super.onResume()
    }

    fun setCartCountIcon() {
        val cartCount = Utility.calculateCartQuantity(context)
        if (cartCount != 0) {
            cartCountIcon!!.visibility = View.VISIBLE
            cartCountIcon!!.text = String.format("%d", cartCount)
        } else {
            cartCountIcon!!.visibility = View.GONE
        }
    }


    private fun populate_merch() {
        firestore!!.collection("Merch").get().addOnCompleteListener { task: Task<QuerySnapshot> ->
            for (documentSnapshot in task.result) {
                val obj =
                    documentSnapshot["Images"] as ArrayList<String>?
                merchModelList.add(
                    merchModel(
                        documentSnapshot.getString("Name"),
                        documentSnapshot.getString("Type"),
                        documentSnapshot.getString("Price"),
                        documentSnapshot.getString("Description"),
                        documentSnapshot.getString("Image"),
                        documentSnapshot.getBoolean("Available"),
                        documentSnapshot.getBoolean("Small"),
                        documentSnapshot.getBoolean("Medium"),
                        documentSnapshot.getBoolean("Large"),
                        documentSnapshot.getBoolean("ExtraLarge"),
                        documentSnapshot.getBoolean("XXLarge"),
                        obj,
                        documentSnapshot.getString("Video"),
                        documentSnapshot.getString("Small_Description"),
                        documentSnapshot.getString("background"),
                        documentSnapshot.getString("Merch_Default")
                    )
                )
            }
            //Toast.makeText(getContext(), merchModelList[2].name , Toast.LENGTH_SHORT).show();
            //TODO
            loaderView.visibility = View.GONE
        }
    }

    fun Onclick(position: Int) {
        //Toast.makeText(getContext(), "Item clicked: " + merch_modelList.get(position).getName(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Item clicked: " + merchModelList!![position].name)
        val intent = Intent(activity, MerchDescriptionActivity::class.java)
        intent.putExtra("item", merchModelList!![position])
        startActivity(intent)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MerchCards(
        eventDetails: SnapshotStateList<merchModel>,
        onClick: (Int) -> Unit
    ){
        val drbls = intArrayOf(
            R.drawable.merch_bg_1,
            R.drawable.merch_bg_2,
            R.drawable.merch_bg_3
        )
        val coroutineScope = rememberCoroutineScope()

        val textPaintStroke = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = 88f
            color = android.graphics.Color.BLACK
            strokeWidth = 18f
            typeface = resources.getFont(R.font.starguard)
            strokeMiter= 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
        }

        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = 88F
            typeface = resources.getFont(R.font.starguard)
            color = android.graphics.Color.WHITE
        }

        val textPaintStroke1 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = 44F
            color = android.graphics.Color.BLACK
            strokeWidth = 9f
            typeface = resources.getFont(R.font.starguard)
            strokeMiter= 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
        }

        val textPaint1 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = 44F
            typeface = resources.getFont(R.font.starguard)
            color = android.graphics.Color.WHITE
        }

        val textPaintStroke2 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = 66F
            color = android.graphics.Color.BLACK
            strokeWidth = 16f
            typeface = resources.getFont(R.font.starguard)
            strokeMiter= 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
        }

        val textPaint2 = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = 66F
            typeface = resources.getFont(R.font.starguard)
            color = ContextCompat.getColor(requireContext(), R.color.Green)
        }
        Alcheringa2022Theme {
            LazyColumn(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),

            ) {
                itemsIndexed(eventDetails){
                    index, dataEach -> context?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp,
                        border = BorderStroke(1.5.dp, MaterialTheme.colors.secondary),
                    ) {
                        Box(modifier = Modifier
                            .clickable {
//                                sheetIndex.value = index
//                                coroutineScope.launch {
//                                        bottomSheetScaffoldState.bottomSheetState.expand()
//
//                                }
                                onClick(index)
                            }
                            .height(200.dp)
                            .fillMaxWidth()

                        ){
                            Image(painter = painterResource(id = drbls[index]), contentDescription = null,
                                Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter), contentScale = ContentScale.Crop, alignment = Alignment.Center)

                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()

                            ){

                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .align(Alignment.BottomStart)
                                        .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {



                                    Column(
                                        Modifier
                                            .fillMaxWidth(0.5F)
                                            .fillMaxHeight()
                                            .padding(top = 60.dp)

                                    ) {
//                                Text(
//                                    text = merch[page].Name.uppercase(),
//                                    color = Color.White,
//                                    fontWeight = FontWeight.Normal,
//                                    fontSize = 32.sp,
//                                    fontFamily = star_guard,
//                                )
                                        Canvas(
                                            modifier = Modifier.fillMaxWidth(),
                                            onDraw = {
                                                drawIntoCanvas {
                                                    it.nativeCanvas.drawText(
                                                        dataEach.name.uppercase(),
                                                        0f,
                                                        0.dp.toPx(),
                                                        textPaintStroke
                                                    )
                                                    it.nativeCanvas.drawText(
                                                        dataEach.name.uppercase(),
                                                        0f,
                                                        0.dp.toPx(),
                                                        textPaint
                                                    )
                                                }
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = dataEach.material.uppercase(),
                                            color = black,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                            fontFamily = star_guard,
                                        )
                                        Spacer(modifier = Modifier.height(52.dp))
                                        Canvas(
                                            modifier = Modifier.fillMaxWidth(),
                                            onDraw = {
                                                drawIntoCanvas {
                                                    it.nativeCanvas.drawText(
                                                        "At just",
                                                        0f,
                                                        0.dp.toPx(),
                                                        textPaintStroke1
                                                    )
                                                    it.nativeCanvas.drawText(
                                                        "At just",
                                                        0f,
                                                        0.dp.toPx(),
                                                        textPaint1
                                                    )
                                                }
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(34.dp))

                                        Canvas(
                                            modifier = Modifier.fillMaxWidth(),
                                            onDraw = {
                                                drawIntoCanvas {
                                                    it.nativeCanvas.drawText(
                                                        "Rs. "+ dataEach.price,
                                                        0f,
                                                        0.dp.toPx(),
                                                        textPaintStroke2
                                                    )
                                                    it.nativeCanvas.drawText(
                                                       "Rs. "+ dataEach.price,
                                                        0f,
                                                        0.dp.toPx(),
                                                        textPaint2
                                                    )
                                                }
                                            }
                                        )

                                    }

                                    GlideImage(modifier = Modifier
                                        .fillMaxHeight()
                                        .align(Alignment.CenterVertically)
                                        .padding(vertical = 10.dp),
                                        imageModel = dataEach.image_url, contentDescription = "merch", contentScale = ContentScale.Fit,
                                        alignment = Alignment.Center,
                                        shimmerParams = ShimmerParams(
                                            baseColor = Color.Transparent,
                                            highlightColor = Color.LightGray,
                                            durationMillis = 350,
                                            dropOff = 0.65f,
                                            tilt = 20f
                                        ),failure = {
                                            Box(modifier= Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight(), contentAlignment = Alignment.Center) {
                                                val composition by rememberLottieComposition(
                                                    LottieCompositionSpec.RawRes(R.raw.failure))
                                                val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
                                                LottieAnimation(
                                                    composition,
                                                    progress,
                                                    modifier = Modifier.fillMaxHeight()
                                                )
//                            Column(
//                                Modifier
//                                    .fillMaxWidth()
//                                    .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
//                                Image(
//                                    modifier = Modifier
//                                        .width(60.dp)
//                                        .height(60.dp),
//                                    painter = painterResource(
//                                        id = R.drawable.ic_sad_svgrepo_com
//                                    ),
//                                    contentDescription = null
//                                )
//                                Spacer(modifier = Modifier.height(10.dp))
//                                Text(
//                                    text = "Image Request Failed",
//                                    style = TextStyle(
//                                        color = Color(0xFF747474),
//                                        fontFamily = hk_grotesk,
//                                        fontWeight = FontWeight.Normal,
//                                        fontSize = 12.sp
//                                    )
//                                )
//                            }
                                            }

                                        }
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


    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun MyContent() {
        var index by rememberSaveable {mutableStateOf(0)}

        val sizes = arrayListOf<String>("S", "M", "L", "XL", "XXL")
        var isAvailable = arrayListOf<Boolean>(
            homeViewModel.merchMerch[index].small,
            homeViewModel.merchMerch[index].medium,
            homeViewModel.merchMerch[index].large,
            homeViewModel.merchMerch[index].xlarge,
            homeViewModel.merchMerch[index].xxLarge,
        )
        var isInStock = homeViewModel.merchMerch[index].xxLarge ||
                        homeViewModel.merchMerch[index].xlarge ||
                        homeViewModel.merchMerch[index].medium ||
                        homeViewModel.merchMerch[index].large ||
                        homeViewModel.merchMerch[index].small
        var txtCol = black
        var merchSize by remember {
            mutableStateOf("")
        }


        // Declaring a Boolean value to
        // store bottom sheet collapsed state
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState =
            BottomSheetState(BottomSheetValue.Collapsed)
        )

        // Declaring Coroutine scope
        val coroutineScope = rememberCoroutineScope()

        // Creating a Bottom Sheet
        Alcheringa2022Theme {
            BottomSheetScaffold(

                scaffoldState = bottomSheetScaffoldState,
                sheetContent = {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.75f)
                            .border(
                                2.dp,
                                colors.secondary,
                                RoundedCornerShape(40.dp, 40.dp)
                            )
                    ) {
                        Box(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Icon(
                                painterResource(id = R.drawable.rectangle_expand), "",
                                Modifier
                                    .width(60.dp)
                                    .height(5.dp), tint = Color(0xffacacac)
                            )
                        }
                        if(bottomSheetScaffoldState.bottomSheetState.isExpanded){

                            Column(
                                Modifier
                                    .verticalScroll(rememberScrollState())
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(horizontal = 30.dp)) {
                                horizontalpager(
                                    merModel = homeViewModel.merchMerch[index],
                                    context = requireContext(),
                                    index
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Text(
                                    homeViewModel.merchMerch[index].name,
                                    style = TextStyle(
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                        color = colors.onBackground
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    homeViewModel.merchMerch[index].material,
                                    style = TextStyle(
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                        color = colors.secondaryVariant
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    "Rs. "+ homeViewModel.merchMerch[index].price,
                                    style = TextStyle(
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 32.sp,
                                        color = colors.onBackground
                                    )
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                                    Text(
                                        "Select Sizes",
                                        style = TextStyle(
                                            fontFamily = aileron,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = colors.onBackground
                                        )
                                    )
                                    Text(
                                        "Size chart",
                                        style = TextStyle(
                                            fontFamily = aileron,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = blu
                                        ),
                                        modifier = Modifier.clickable {
                                            val intent1 = Intent(requireContext(), SizeChartActivity::class.java)
                                            intent1.putExtra("type",homeViewModel.merchMerch[index].material)
                                            startActivity(intent1)
                                        }
                                    )


                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                Card(
                                    Modifier
                                        .height(42.dp)
                                        ,
                                    shape = RoundedCornerShape(9.dp),
                                    border = BorderStroke(1.dp, colors.secondary)
                                ) {
                                    Box(){
                                        LazyRow(){
                                            itemsIndexed(sizes){ i, dataeach -> context?.let{
                                                var boxColor: Color = if(!isAvailable[i]){
                                                    midWhite
                                                } else if(merchSize == dataeach){
                                                    blu
                                                } else{
                                                    colors.background
                                                }
                                                txtCol = if(boxColor == blu){
                                                    black
                                                } else{
                                                    colors.onBackground
                                                }

                                                Box(
                                                    Modifier
                                                        .fillMaxHeight()
                                                        .fillParentMaxWidth(0.2f)
                                                        .clickable {
                                                            if(isAvailable[i])
                                                            {
                                                                if(merchSize == dataeach){
                                                                    merchSize = ""
                                                                }
                                                                else{
                                                                    merchSize = dataeach
                                                                }

                                                            }
                                                        }
                                                        .background(boxColor)
                                                        .border(0.5.dp, colors.secondary)
                                                        .padding(horizontal= 8.dp),
                                                    Alignment.Center
                                                ){
                                                    Text(
                                                        dataeach,
                                                        style = TextStyle(
                                                            fontFamily = aileron,
                                                            fontWeight = FontWeight.Bold,
                                                            fontSize = 16.sp,
                                                            color = txtCol
                                                        ),

                                                    )
                                                }
                                            } }
                                        }
                                    }

                                }

                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    "Product Description",
                                    style = TextStyle(
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = colors.onBackground
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    homeViewModel.merchMerch[index].description,
                                    style = TextStyle(
                                        fontFamily = aileron,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                        color = colors.onBackground
                                    )
                                )
                                Spacer(modifier = Modifier.height(32.dp))

                            }
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(72.dp)
                                    .border(1.dp, colors.secondary)){
                                Row {
                                    Button(
                                        onClick = {
                                            if(isInStock && merchSize != ""){
                                                dbHandler.addNewitemIncart(
                                                    homeViewModel.merchMerch[index].name,
                                                    homeViewModel.merchMerch[index].price,
                                                    merchSize,
                                                    "1",
                                                    homeViewModel.merchMerch[index].image_url,
                                                    homeViewModel.merchMerch[index].material,
                                                    requireContext()
                                                )
                                                Toast.makeText(requireContext(), homeViewModel.merchMerch[index].name + " added to cart", Toast.LENGTH_SHORT).show()
                                                setCartCountIcon()
                                            }
                                            else if(!isInStock){
                                                Toast.makeText(requireContext(),"Out of Stock",Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(requireContext(),"Please select a Size first",Toast.LENGTH_SHORT).show()
                                            }

                                        },
                                        Modifier
                                            .fillMaxWidth()
                                            .border(1.dp, colors.secondary)
                                            .fillMaxHeight()
                                            .weight(1f),

                                        shape = RoundedCornerShape(0.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            colors.background
                                        )
                                    ) {
                                        Text(
                                            text = "Add to Cart",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = aileron,
                                            color = colors.onBackground
                                        )

                                    }
                                    Button(
                                        onClick = {
                                            if(merchSize == ""){
                                                Toast.makeText(requireContext(), "Please select a size first", Toast.LENGTH_SHORT ).show()
                                            }
                                            else{

                                                dbHandler.addNewitemIncart(
                                                    homeViewModel.merchMerch[index].name,
                                                    homeViewModel.merchMerch[index].price,
                                                    merchSize,
                                                    "1",
                                                    homeViewModel.merchMerch[index].image_url,
                                                    homeViewModel.merchMerch[index].material,
                                                    requireContext()
                                                )
                                                startActivity(Intent(requireContext(), CartActivity::class.java))
                                                setCartCountIcon()
                                            }

                                        },
                                        Modifier
                                            .fillMaxWidth(0.5f)
                                            .border(1.dp, colors.secondary)
                                            .fillMaxHeight()
                                            .weight(1f),

                                        shape = RoundedCornerShape(0.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            blu
                                        )
                                    ) {
                                        Text(
                                            text = "Buy Now",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = aileron,
                                            color = black
                                        )

                                    }
                                }
                            }


                        }

                    }


                },
                sheetPeekHeight = 0.dp, sheetShape = RoundedCornerShape(40.dp, 40.dp),
                sheetBackgroundColor = colors.background,


                ) {
                    MerchCards(eventDetails = homeViewModel.merchMerch){
                        index = it
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }


                    }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun horizontalpager(merModel: merchModel, context: Context, index: Int) {
        val images = merModel.images
        val videoUrl = merModel.video_url
        var isVideo = 0
        val drbls = intArrayOf(
            R.drawable.merch_bg_1,
            R.drawable.merch_bg_2,
            R.drawable.merch_bg_3
        )
        var bg = drbls[index];


        Column {
            val pagerState = rememberPagerState()

            HorizontalPager(
                count = images.size + isVideo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(344.dp),
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 28.dp)
            ){ page ->
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
                    if(page==images.size && isVideo == 1){
                        Box {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                //elevation = 5.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(blackbg)
                                        .padding(8.dp)
                                        .height(400.dp)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val mediaItem = MediaItem.fromUri(videoUrl)
                                    val exoPlayer = SimpleExoPlayer.Builder(context).build().apply {
                                        setMediaItem(mediaItem)
                                        repeatMode = Player.REPEAT_MODE_ONE
                                        playWhenReady = true
                                        prepare()
                                        play()
                                    }
                                    DisposableEffect(
                                        AndroidView(factory = { PlayerView(context).apply {
                                            player = exoPlayer
                                            setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)

                                        } })
                                    )
                                    { onDispose { exoPlayer.release() } }
                                }
                            }
                        }
                    }
                    else{
                        Box{
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = 5.dp
                            ) {
                                Image(
                                    painter = painterResource(id = bg),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize(),
                                    contentDescription = ""
                                )
                                Box(
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .height(400.dp)
                                        .fillMaxWidth(), contentAlignment = Alignment.Center
                                ) {
                                    GlideImage(
                                        imageModel = images[page],
                                        contentDescription = "artist",
                                        modifier = Modifier
                                            .width(348.dp)
                                            .height(360.dp),
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Crop,
                                        shimmerParams = ShimmerParams(
                                            baseColor = Color.Black,
                                            highlightColor = Color.LightGray,
                                            durationMillis = 350,
                                            dropOff = 0.65f,
                                            tilt = 20f
                                        ), failure = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(473.dp), contentAlignment = Alignment.Center
                                            ) {
                                                Column(
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .wrapContentHeight(),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
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


