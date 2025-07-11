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
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.*
import com.alcheringa.alcheringa2022.Database.DBHandler
import com.alcheringa.alcheringa2022.Model.merchModel
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.ui.theme.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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
        if(homeViewModel.merchMerch.isEmpty()) { homeViewModel.getMerchMerch() }
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

        val search = view.findViewById<ImageView>(R.id.search)
        search.setOnClickListener { v: View? ->
            (activity as MainActivity).NavController.navigate(R.id.action_merch_to_searchFragment)
        }
        merchModelList = SnapshotStateList()
        //populate_merch()
        setCartCountIcon()
        var popUp = -1
        try {
            popUp = requireArguments().getInt("merchId")
        } catch (e: IllegalStateException){
            Log.d("MerchFragmentCompose", "No Argument")
        }




        compose = view.findViewById(R.id.compose1)
        compose.setContent {
            if(homeViewModel.merchMerch.isNotEmpty()){

                MyContent(popUp)

            }
        }

        return view
    }

    override fun onResume() {
        setCartCountIcon()
        (requireActivity().findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView).menu.findItem(
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
                verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(bottom = 20.dp,top=20.dp)

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

                                    GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(
                                        DiskCacheStrategy.AUTOMATIC)},modifier = Modifier
                                        .fillMaxHeight()
                                        .align(Alignment.CenterVertically)
                                        .padding(vertical = 10.dp),
                                        imageModel = dataEach.image_url, contentDescription = "merch", contentScale = ContentScale.Fit,
                                        alignment = Alignment.Center,
                                        shimmerParams = ShimmerParams(
                                    baseColor = if(isSystemInDarkTheme()) black else highWhite,
                                    highlightColor = if(isSystemInDarkTheme()) highBlack else white,
                                    durationMillis = 1500,
                                    dropOff = 1f,
                                    tilt = 20f
                                ),failure = {
                                            Box(modifier= Modifier
                                                .fillMaxWidth()
                                                .height(80.dp), contentAlignment = Alignment.Center) {
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


    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
        ExperimentalFoundationApi::class
    )
    @Composable
    fun MyContent(popUp: Int) {
        var index by rememberSaveable {mutableStateOf(if (popUp==-1) 0 else popUp)}





        // Declaring a Boolean value to
        // store bottom sheet collapsed state
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState =
            BottomSheetState(if(popUp!=-1) BottomSheetValue.Expanded else BottomSheetValue.Collapsed)
        )

        // Declaring Coroutine scope
        val coroutineScope = rememberCoroutineScope()

        


        

        // Creating a Bottom Sheet

        Alcheringa2022Theme {
            BottomSheetScaffold(

                scaffoldState = bottomSheetScaffoldState,
                sheetContent = {

                    MerchBottomSheet(bottomSheetState = bottomSheetScaffoldState, index = index)
                               },
//                sheetShape = RoundedCornerShape(40.dp, 40.dp),
                sheetBackgroundColor = Color.Transparent,
                sheetElevation = 0.dp,
                sheetPeekHeight = 0.dp


            ) {
                if(homeViewModel.merchMerch.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                        //   .padding(horizontal = 20.dp)
                            .paint(
                                painterResource(id = if (isSystemInDarkTheme()) R.drawable.merchbgg else R.drawable.merchbgg),
                                contentScale = ContentScale.Crop
                            )
                    ) {


                        Box(
                            modifier = Modifier
                                .height(73.dp)
                                .fillMaxWidth()

                        ) {
                            Image(
                                painter = painterResource(R.drawable.merchhead),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )



                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        LazyColumn(
                            //verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {



                            items(homeViewModel.merchMerch.size) { i ->

                                merchGridItem2024(item = homeViewModel.merchMerch[i], onClick = {
                                    index = i
                                    coroutineScope.launch {
                                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                            bottomSheetScaffoldState.bottomSheetState.expand()

                                        } else {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }/*TODO*/ }, index = i,homeViewModel.merchMerch.size)

//                                newMerchGridItem(
//                                    merch = homeViewModel.merchMerch[i],
//                                    Index = i,
//                                    onClick = {
//
//                                    }
//                                )
                            }
                        }
                    }
                }
                else{
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Merch coming soon",
                            fontSize = 20.sp,
                            fontFamily = futura,
                            color = colors.onBackground
                        )
                    }
                }

//                Column(Modifier.verticalScroll(rememberScrollState())) {
//                    Row() {
//                        Column(
//                            Modifier
//                                .weight(1f)
//                                .padding(start = 20.dp, end = 10.dp),
//                        ) {
//                            homeViewModel.merchMerch.subList(
//                                0,
//                                (homeViewModel.merchMerch.size + 1) / 2
//                            ).forEachIndexed { i, dataeach ->
//                                MerchGridItem(merch = dataeach) {
//                                    index = i
//                                    coroutineScope.launch {
//                                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
//                                            bottomSheetScaffoldState.bottomSheetState.expand()
//
//                                        } else {
//                                            bottomSheetScaffoldState.bottomSheetState.collapse()
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        Column(
//                            Modifier
//                                .weight(1f)
//                                .padding(start = 10.dp, end = 20.dp),
//                        ) {
//                            homeViewModel.merchMerch.subList(
//                                (homeViewModel.merchMerch.size + 1) / 2,
//                                homeViewModel.merchMerch.size
//                            ).forEachIndexed { i, dataeach ->
//                                MerchGridItem(merch = dataeach) {
//                                    index = i + (homeViewModel.merchMerch.size + 1) / 2
//                                    coroutineScope.launch {
//                                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
//                                            bottomSheetScaffoldState.bottomSheetState.expand()
//
//                                        } else {
//                                            bottomSheetScaffoldState.bottomSheetState.collapse()
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//
//                    }
//
//                }
                BackHandler(enabled = bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
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


        Column {
            val pagerState = rememberPagerState()

            HorizontalPager(
                count = images.size + isVideo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 28.dp)
            ){ page ->
                Box(
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
                        },

                    ) {
                    if(page==images.size && isVideo == 1){
                        Box {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                //elevation = 5.dp
                            ) {
                                Box(
                                    modifier = Modifier
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
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .fillMaxWidth(), contentAlignment = Alignment.Center
                                ) {
                                    GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)},
                                        imageModel = images[page],
                                        contentDescription = "artist",
                                        modifier = Modifier.fillMaxHeight(),
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Fit,
                                        shimmerParams = ShimmerParams(
                                            baseColor = if(isSystemInDarkTheme()) black else highWhite,
                                            highlightColor = if(isSystemInDarkTheme()) highBlack else white,
                                            durationMillis = 1500,
                                            dropOff = 1f,
                                            tilt = 20f
                                        ), failure = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight(), contentAlignment = Alignment.Center
                                            ) {
                                                val composition by rememberLottieComposition(
                                                    LottieCompositionSpec.RawRes(
                                                        if (isSystemInDarkTheme())R.raw.comingsoondark else R.raw.comingsoongray
                                                    )
                                                )
                                                val progress by animateLottieCompositionAsState(
                                                    composition,
                                                    iterations = LottieConstants.IterateForever
                                                )
                                                LottieAnimation(
                                                    composition,
                                                    progress,
                                                    modifier = Modifier.fillMaxHeight()
                                                )
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
                activeColor= colors.onBackground,
                inactiveColor = colors.secondaryVariant,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }

    }

    @Composable
    fun merchGridItem2024(item: merchModel, onClick: () -> Unit, index: Int,totalSize:Int){
        val context= LocalContext.current
        var fontCol:Color by remember{ mutableStateOf( if(context.getResources().getConfiguration().uiMode==33)
        {
            lightBar
        }
        else
        {
            darkBar
        }) }
        var tempBgCol:Color by remember{ mutableStateOf( if(context.getResources().getConfiguration().uiMode==33)
        {
            Color.Black
        }
        else
        {
            lightBar
        }) }
        var surfaceCol:Color by remember{ mutableStateOf( if(context.getResources().getConfiguration().uiMode==33)
        {
            darkBar
        }
        else
        {
            lightBar
        }) }
        val itembg = if (index%4 == 1 || index%4 == 2) R.drawable.merchbg_purple else R.drawable.merchbg_green
        val ribbonbg = if (index%4 == 1 || index%4 == 2) R.drawable.ribbon else R.drawable.ribbon2

                var bottomDp = 10.dp
                if (index == totalSize - 1) {
                    bottomDp = 90.dp
                }
                Card(

                    modifier = Modifier

                        .padding(15.dp, 10.dp, 15.dp, bottomDp)
                        .height(297.dp)
                        .clickable(
                            onClick = onClick,
                            enabled = true
                        ).paint(
                            painterResource(id = R.drawable.mercardbg)
                        ),

                    backgroundColor = Color.Transparent





                ) {










                        Row() {

                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(.80f)
                                    .fillMaxWidth(0.56f).padding(start = 46.dp,top=39.dp)


                            ) {
                                //   Box(modifier=Modifier.fillMaxHeight().background(color=Color.Blue,shape= RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))){
                                Image(
                                    painter = painterResource(id =R.drawable.merback),
                                    contentDescription = "cart_item_bg",
                                    modifier = Modifier
                                        .fillMaxHeight(),
//                                        .clip(
//                                            RoundedCornerShape(
//                                                topStart = 8.dp,
//                                                bottomStart = 8.dp
//                                            )
//                                        ),
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
                                            item.image_url,
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
//                                Image(
//                                    painter = painterResource(id = ribbonbg),
//                                    contentDescription = "cart_item_bg",
//                                    modifier = Modifier
//                                        .padding(top = 94.dp)
//                                        .fillMaxHeight(0.85f)
//                                        .clip(
//                                            RoundedCornerShape(
//                                                topEnd = 8.dp,
//                                                bottomEnd = 8.dp
//                                            )
//                                        ),
//                                    contentScale = ContentScale.Crop
//                                )
                                Column(
                                    modifier = Modifier
                                       // .padding(16.dp)
                                        .fillMaxWidth()
                                ) {

                                    Spacer(modifier = Modifier.height(50.dp))

                                    Text(
                                        text = item.material!!,

                                        style = TextStyle(
                                            fontSize = 24.sp,
                                         //   fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFF1E8),
                                            textAlign = TextAlign.Center,
                                            fontFamily  = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            ),
                                            shadow = Shadow(
                                                color = Color(0xFF000000),
                                                offset = Offset(6f, 4f)
                                            )
                                        ),

                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(end=40.dp)




                                    )

                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = item.name!!,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            //   fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFF77A8),
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
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                            .padding(end=40.dp),


                                    )




                                    Spacer(modifier = Modifier.height(20.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Rs. " + item.price!! + ".00/-",
                                        style = TextStyle(
                                            fontSize = 16.sp,
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
                                        modifier = Modifier.align(Alignment.CenterHorizontally),


                                        //maxLines = 1,
                                        //overflow = TextOverflow.Ellipsis,
                                    )
                                }
                            }


                        }


                    Image(
                        painter = painterResource(R.drawable.limitedoffer),
                        contentDescription = null,
                        modifier = Modifier.height(30.dp).padding( start=50.dp,bottom = 44.dp).fillMaxWidth(.85f),
                        Alignment.BottomEnd
                    )

//
//                    Image(
//                        painter = painterResource(R.drawable.atcbutton),
//                        contentDescription = null,
//                        modifier = Modifier.height(10.dp).padding( start=50.dp,bottom = 0.dp),
//                        Alignment.BottomEnd
//                    )
//



                }




    }

    @Composable
    fun newMerchGridItem(merch: merchModel, onClick: () -> Unit, Index: Int){
        val itembg = if (Index%4 == 1 || Index%4 == 2) R.drawable.merchbg_purple else R.drawable.merchbg_green
        Card (
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 10.dp)
                .clickable(
                    onClick = onClick,
                    enabled = true
                ),
            border = BorderStroke(1.dp, colors.onBackground),
            shape = RoundedCornerShape(4.dp),
            backgroundColor = colors.background
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(colors.background, RoundedCornerShape(4.dp))
                    .border(1.dp, colors.onBackground, RoundedCornerShape(4.dp)),
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = itembg),
                                contentDescription = null,
                                modifier = Modifier
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )
                            Image(
                                rememberImagePainter(data = merch.image_url),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(15.dp)
                                    .aspectRatio(1f)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = merch.material,

                        style = TextStyle(
                            fontSize = 25.sp,
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
                        )
                    )
                    Text(
                        text = merch.name,
                        fontFamily = futura,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Rs. ${merch.price}/-",
                        fontFamily = futura,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

    @Composable
    fun MerchGridItem(merch: merchModel, onClick: () -> Unit){
        Card(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 10.dp)
                .clickable(
                    onClick = onClick,
                    enabled = true
                ),
            border = BorderStroke(1.5.dp, colors.onSurface),
            shape = RoundedCornerShape(24.dp),
            backgroundColor = colors.background
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 30.dp, horizontal = 20.dp)
                ,
                Alignment.Center){
                Column(verticalArrangement = Arrangement.Center) {
                    GlideImage( requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)},modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 10.dp),
                        imageModel = merch.image_url, contentDescription = "merch", contentScale = ContentScale.Fit,
                        alignment = Alignment.Center,
                        shimmerParams = ShimmerParams(
                            baseColor = if(isSystemInDarkTheme()) black else highWhite,
                            highlightColor = if(isSystemInDarkTheme()) highBlack else white,
                            durationMillis = 1500,
                            dropOff = 1f,
                            tilt = 20f
                        ),failure = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp), contentAlignment = Alignment.Center
                            ) {
                                val composition by rememberLottieComposition(
                                    LottieCompositionSpec.RawRes(R.raw.failure)
                                )
                                val progress by animateLottieCompositionAsState(
                                    composition,
                                    iterations = LottieConstants.IterateForever
                                )
                                LottieAnimation(
                                    composition,
                                    progress,
                                    modifier = Modifier.fillMaxHeight()
                                )
                            }
                        })
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        merch.name,

                        style = TextStyle(
                            fontSize = 18.sp,
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
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        merch.material,
                        style = TextStyle(
                            fontFamily = aileron,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = colors.secondaryVariant
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Rs. "+ merch.price + ".00",

                        style = TextStyle(
                            fontSize = 20.sp,
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
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }

        }

    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MerchBottomSheet(bottomSheetState: BottomSheetScaffoldState, index: Int){

        val sizes = arrayListOf<String>("S", "M", "L", "XL", "XXL")
        val scrollState by  mutableStateOf(ScrollState(0))


        var txtCol = black
        var boxColor: Color by remember {
            mutableStateOf(Color.Transparent)
        }
        var merchSize by remember {
            mutableStateOf("L")
        }
        var isSizeChartExpanded by remember {
            mutableStateOf(false)
        }
        var currentMerch = homeViewModel.merchMerch[index]
        var isAvailable = arrayListOf<Boolean>(
            currentMerch.small,
            currentMerch.medium,
            currentMerch.large,
            currentMerch.xlarge,
            currentMerch.xxLarge,
        )
        var isInStock = currentMerch.xxLarge ||
                currentMerch.xlarge ||
                currentMerch.medium ||
                currentMerch.large ||
                currentMerch.small

        Box(
            contentAlignment = Alignment.BottomCenter
        ) {


            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

                    .verticalScroll(scrollState),
            ) {
                Box() {
                    Box(
                        Modifier
                            .fillMaxWidth()
//                                        .fillMaxSize()
//                                        .fillMaxHeight(0.8f)


                           // .padding(top = 80.dp)
//                                        .paint(
//                                            painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
//                                            contentScale = ContentScale.Crop
//                                        )


                    ) {

                        Box(
//                                        modifier = Modifier.paint(
//                                            painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
//                                            contentScale = ContentScale.FillBounds
//                                        ),

//                                        border = BorderStroke(2.dp, ),
//                                        backgroundColor = colors.background,
                            modifier = Modifier
//                                .border(
//                                    width = 2.dp,
//                                    color = colors.onBackground
//                                )
                                .paint(
                                    //for background
                                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.cart_bg else R.drawable.cart_bg),
                                    contentScale = ContentScale.Crop
                                )
                                .fillMaxHeight(),
//                                        contentAlignment = Alignment.TopCenter

                        ) {

                            Box(
                                modifier = Modifier.wrapContentHeight(),
                                contentAlignment = Alignment.TopCenter
                            ) {



                                Image(
                                    painter = painterResource(id = R.drawable.product_image_bg),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .fillMaxWidth()
                                        .height(330.dp)
                                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                                        ,
                                    contentScale = ContentScale.Crop

                                )

                                Spacer(modifier = Modifier.height(140.dp))

                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            bottom = 40.dp,
                                            top = 360.dp
                                        ),
                                )
                                {
                                    Text(
                                        currentMerch.material,
                                        style = TextStyle(
                                            fontSize = 35.sp,
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
                                        ) ,
                                                modifier = Modifier.padding(start = 30.dp,end= 30.dp)
                                    )
//                                    Text(
//                                        currentMerch.material,
//                                        style = TextStyle(
//                                            fontFamily = aileron,
//                                            fontWeight = FontWeight.Bold,
//                                            fontSize = 24.sp,
//                                            color = colors.onBackground
//                                        ),
//                                        modifier = Modifier.padding(start = 30.dp,end= 30.dp)
//                                    )

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        currentMerch.name,
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFA0C2),
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            ),
                                            shadow = Shadow(
                                                color = Color(0xFF7E2953),
                                                offset = Offset(6f, 5f)
                                            )
                                        ) ,
                                        modifier = Modifier.padding(start = 30.dp,end= 30.dp)
                                    )

//                                    Text(
//                                        currentMerch.name,
//                                        style = TextStyle(
//                                            fontFamily = aileron,
//                                            fontWeight = FontWeight.Normal,
//                                            fontSize = 14.sp,
//                                            color = colors.secondaryVariant
//                                        ),
//                                        modifier = Modifier.padding(start = 30.dp,end= 30.dp)
//                                    )

                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        "Rs. " + currentMerch.price,
                                        style = TextStyle(
                                            fontSize = 36.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFF1E8),
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            ),
                                            shadow = Shadow(
                                                color = Color(0xFF7E2953),
                                                offset = Offset(6f, 5f)
                                            )
                                        ) ,
                                        modifier = Modifier.padding(start = 30.dp,end= 30.dp)
                                    )
//                                    Text(
//                                        "Rs. " + currentMerch.price,
//                                        style = TextStyle(
//                                            fontFamily = aileron,
//                                            fontWeight = FontWeight.Bold,
//                                            fontSize = 32.sp,
//                                            color = colors.onBackground
//                                        ),
//                                        modifier = Modifier.padding(start = 30.dp,end= 30.dp)
//                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 30.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "Pick Your Size",
                                            style = TextStyle(
                                                fontSize = 19.sp,
                                                //fontWeight = FontWeight.Bold,
                                                color = Color(0xFFFFF1E8),
                                                textAlign = TextAlign.Center,
                                                fontFamily = FontFamily(
                                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                ),
                                                shadow = Shadow(
                                                    color = Color(0xFF7E2953),
                                                    offset = Offset(6f, 5f)
                                                )
                                            ) ,
                                            modifier = Modifier.padding(start = 10.dp,end= 30.dp)
                                        )
//                                        Text(
//                                            "Pick Your Size",
//                                            style = TextStyle(
//                                                fontFamily = futura,
//                                                fontWeight = FontWeight.Bold,
//                                                fontSize = 14.sp,
//                                                color = colors.onBackground
//                                            )
//                                            ,
////                                                            modifier = Modifier.padding(start = 30.dp,end= 30.dp)
//                                        )

                                        Text(
                                            "Size chart",
                                            style = TextStyle(
                                                fontSize = 19.sp,
                                                //fontWeight = FontWeight.Bold,
                                                color = Color(0xFFFF77A8),
                                                textAlign = TextAlign.Center,
                                                fontFamily = FontFamily(
                                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                ),
                                                shadow = Shadow(
                                                    color = Color(0xFF7E2953),
                                                    offset = Offset(6f, 5f)
                                                )
                                            ),
                                            modifier = Modifier.clickable {
                                                //                                            val intent1 = Intent(requireContext(), SizeChartActivity::class.java)
                                                //                                            intent1.putExtra("type",homeViewModel.merchMerch[index].material)
                                                //                                            startActivity(intent1)
                                                isSizeChartExpanded =
                                                    !isSizeChartExpanded
                                            }
                                        )


                                    }
                                  //  Spacer(modifier = Modifier.height(20.dp))


                                    Card(
                                        Modifier
                                            .wrapContentHeight()
                                            .animateContentSize(),
                                       // shape = RoundedCornerShape(9.dp),
                                        backgroundColor = Color.Transparent,
                                        elevation = 0.dp
                                        /*border = BorderStroke(1.dp, colors.secondary)*/
                                    ) {
                                        Box() {
                                            Column() {

                                                Row {


                                                    Box(
                                                        modifier = Modifier
                                                            .height(45.dp)
                                                            .width(45.dp)
                                                            .align(alignment = Alignment.Bottom)
                                                            .padding(bottom = 9.dp)


                                                    ) {
                                                        Image(
                                                            painter = painterResource(R.drawable.larrow),
                                                            contentDescription = null,
                                                            modifier = Modifier.fillMaxSize()
                                                        )


                                                    }

                                                    Spacer(modifier = Modifier.width(5.dp))



                                                    Column {


                                                        LazyRow(
                                                            modifier = Modifier
                                                                .height(80.dp)
                                                                .width(340.dp),
                                                            // .padding(start = 30.dp, end = 30.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {


                                                            itemsIndexed(sizes) { i, dataeach ->
                                                                context?.let {
                                                                    var boxColor: Color =
                                                                        if (!isAvailable[i]) {
                                                                            midWhite
                                                                        } /*else if (merchSize == dataeach) {
                                                                                blu
                                                                            }*/ else {
                                                                            Color.Transparent
                                                                        }
                                                                    txtCol =
                                                                        if (merchSize == dataeach) {
                                                                            darkTealGreen
                                                                        } else {
                                                                            colors.onBackground
                                                                        }

                                                                    Box(
                                                                        Modifier
                                                                            .fillMaxHeight()
                                                                            .fillParentMaxWidth(
                                                                                0.2f
                                                                            )
                                                                            .clickable {
                                                                                if (isAvailable[i]) {
                                                                                    if (merchSize == dataeach) {
                                                                                        merchSize =
                                                                                            ""
                                                                                    } else {
                                                                                        merchSize =
                                                                                            dataeach
                                                                                    }

                                                                                }
                                                                            }
                                                                            .background(
                                                                                boxColor
                                                                            )
                                                                            /*.border(
                                                                        0.5.dp,
                                                                        colors.secondary
                                                                    )*/
                                                                           ,
                                                                        Alignment.Center
                                                                    ) {


                                                                        Column(
                                                                            modifier = Modifier
                                                                                .height(39.dp)
                                                                                .width(68.dp)
                                                                                .align(alignment = Alignment.BottomCenter)
                                                                        ) {
                                                                            Text(
                                                                                dataeach,
                                                                                color = if (txtCol == darkTealGreen) Color(0xFFFFF1E8) else Color(0xFF83769C),

                                                                                style = TextStyle(
                                                                                    fontSize = if (txtCol == darkTealGreen) 40.sp else 25.sp,

                                                                                    //fontWeight = FontWeight.Bold,
                                                                                    color = Color.White,
                                                                                    textAlign = TextAlign.End,
                                                                                    fontFamily = FontFamily(
                                                                                        Font(
                                                                                            R.font.alcher_pixel,
                                                                                            FontWeight.Normal
                                                                                        ),
                                                                                        Font(
                                                                                            R.font.alcher_pixel_bold,
                                                                                            FontWeight.Bold
                                                                                        ),
                                                                                    ),
                                                                                    shadow = Shadow(
                                                                                        color = Color.Gray,
                                                                                        offset = Offset(
                                                                                            6f,
                                                                                            5f
                                                                                        )
                                                                                    )
                                                                                )




                                                                            )


                                                                        }

                                                                    }
                                                                }

                                                            }
                                                        }
                                                        Canvas(modifier = Modifier
                                                            .width(335.dp)
                                                            .padding(bottom = 30.dp)) {
                                                            drawLine(
                                                                color = Color(
                                                                    0xFFFF77A8
                                                                ),
                                                                start = Offset(
                                                                    0f,
                                                                    size.height / 2
                                                                ),
                                                                end = Offset(
                                                                    size.width,
                                                                    size.height / 2
                                                                ),
                                                                strokeWidth = 11f
                                                            )
                                                        }
                                                    }
                                                    Box(
                                                        modifier = Modifier
                                                            .height(45.dp)
                                                            .width(45.dp)
                                                            .align(alignment = Alignment.Bottom)
                                                            .padding(bottom = 9.dp)
                                                    ) {
                                                        Image(
                                                            painter = painterResource(R.drawable.rarrow),
                                                            contentDescription = null,
                                                            modifier = Modifier.fillMaxSize()
                                                        )



                                                     }
                                                }

                                                if (isSizeChartExpanded) {
                                                    val lengths = if(currentMerch.material.contains("oversized", ignoreCase = true))arrayListOf<String>("28", "30", "30.5", "31", "--")
                                                    else arrayListOf<String>("26", "27", "28", "29", "30")


                                                    val widths = if(currentMerch.material.contains("sweatshirt", ignoreCase = true)) arrayListOf<String>("40", "42", "44", "46", "48")
                                                    else if(currentMerch.material.contains("oversized", ignoreCase = true)) arrayListOf<String>("42", "44", "46", "48", "--")
                                                    else arrayListOf<String>("38", "40", "42", "44", "46")
                                                    //val shoulders = arrayListOf<String>("17.5", "18.5", "19.5", "20.5", "21.5")
                                                    Text(
                                                        "Length (+/- 0.5in)",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            //fontWeight = FontWeight.Bold,
                                                            color = Color(0xFFFFF1E8),
                                                            textAlign = TextAlign.Center,
                                                            fontFamily = FontFamily(
                                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                            ),
                                                            shadow = Shadow(
                                                                color = Color(0xFF7E2953),
                                                                offset = Offset(6f, 5f)
                                                            )
                                                        ) ,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(8.dp),
                                                        textAlign = TextAlign.Center
                                                    )
                                                    LazyRow(
                                                        modifier = Modifier.height(42.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        itemsIndexed(lengths) { i, dataeach ->
                                                            context?.let {
                                                                boxColor =
                                                                    if (!isAvailable[i]) {
                                                                        midWhite
//                                                                                    } else if (merchSize == sizes[i]) {
//                                                                                        blu
                                                                    } else {
                                                                        Color.Transparent
                                                                    }
                                                                txtCol =
                                                                    if (merchSize==sizes[i]) {
                                                                        darkTealGreen
                                                                    } else {
                                                                        colors.onBackground
                                                                    }

                                                                Box(
                                                                    Modifier
                                                                        .fillMaxHeight()
                                                                        .fillParentMaxWidth(
                                                                            0.2f
                                                                        )
                                                                        .clickable {
                                                                            if (isAvailable[i]) {
                                                                                if (merchSize == sizes[i]) {
                                                                                    merchSize =
                                                                                        ""
                                                                                } else {
                                                                                    merchSize =
                                                                                        sizes[i]
                                                                                }

                                                                            }
                                                                        }
                                                                        .background(
                                                                            boxColor
                                                                        )
                                                                        /*.border(
                                                                            0.5.dp,
                                                                            colors.secondary
                                                                        )*/
                                                                        .padding(
                                                                            horizontal = 8.dp
                                                                        ),
                                                                    Alignment.Center
                                                                ) {
                                                                    Column(modifier = Modifier
                                                                        .height(30.dp)
                                                                        .width(40.dp)) {
                                                                        Text(
                                                                            dataeach,
                                                                            style = TextStyle(
                                                                                fontSize = 32.sp,
                                                                                //fontWeight = FontWeight.Bold,
                                                                                color = Color(0xFFFFF1E8),
                                                                                textAlign = TextAlign.Center,
                                                                                fontFamily = FontFamily(
                                                                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                                                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                                                ),
                                                                                shadow = Shadow(
                                                                                    color = Color(0xFF7E2953),
                                                                                    offset = Offset(6f, 5f)
                                                                                )
                                                                            ) ,
                                                                            modifier = Modifier.align(Alignment.CenterHorizontally)


                                                                        )

                                                                        if(txtCol== darkTealGreen) {
                                                                            Image(
                                                                                painter = painterResource(R.drawable.squiggle),
                                                                                contentDescription = null,
                                                                                alignment = Alignment.BottomCenter,
                                                                                modifier = Modifier
                                                                                    .size(
                                                                                        30.dp
                                                                                    )
                                                                                    .align(
                                                                                        Alignment.CenterHorizontally
                                                                                    )
                                                                            )
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    Text(
                                                        "Chest (+/- 0.5in)",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            //fontWeight = FontWeight.Bold,
                                                            color = Color(0xFFFFF1E8),
                                                            textAlign = TextAlign.Center,
                                                            fontFamily = FontFamily(
                                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                            ),
                                                            shadow = Shadow(
                                                                color = Color(0xFF7E2953),
                                                                offset = Offset(6f, 5f)
                                                            )
                                                        ) ,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(8.dp),
                                                        textAlign = TextAlign.Center
                                                    )
                                                    LazyRow(
                                                        modifier = Modifier.height(42.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {



                                                        itemsIndexed(widths) { i, dataeach ->
                                                            context?.let {
                                                                boxColor =
                                                                    if (!isAvailable[i]) {
                                                                        midWhite
//                                                                                    } else if (merchSize == sizes[i]) {
//                                                                                        blu
                                                                    } else {
                                                                        Color.Transparent
                                                                    }
                                                                txtCol =
                                                                    if (merchSize==sizes[i]) {
                                                                        darkTealGreen
                                                                    } else {
                                                                        colors.onBackground
                                                                    }

                                                                Box(
                                                                    Modifier
                                                                        .fillMaxHeight()
                                                                        .fillParentMaxWidth(
                                                                            0.2f
                                                                        )
                                                                        .clickable {
                                                                            if (isAvailable[i]) {
                                                                                if (merchSize == sizes[i]) {
                                                                                    merchSize =
                                                                                        ""
                                                                                } else {
                                                                                    merchSize =
                                                                                        sizes[i]
                                                                                }

                                                                            }
                                                                        }
                                                                        .background(
                                                                            boxColor
                                                                        )
                                                                        /*.border(
                                                                            0.5.dp,
                                                                            colors.secondary
                                                                        )*/
                                                                        .padding(
                                                                            horizontal = 8.dp
                                                                        ),
                                                                    Alignment.Center
                                                                ) {
                                                                    Column(modifier = Modifier
                                                                        .height(30.dp)
                                                                        .width(40.dp)) {
                                                                        Text(
                                                                            dataeach,
                                                                            style = TextStyle(
                                                                                fontSize = 32.sp,
                                                                                //fontWeight = FontWeight.Bold,
                                                                                color = Color(0xFFFFF1E8),
                                                                                textAlign = TextAlign.Center,
                                                                                fontFamily = FontFamily(
                                                                                    Font(R.font.alcher_pixel, FontWeight.Normal),
                                                                                    Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                                                                ),
                                                                                shadow = Shadow(
                                                                                    color = Color(0xFF7E2953),
                                                                                    offset = Offset(6f, 5f)
                                                                                )
                                                                            ),
                                                                            modifier = Modifier.align(Alignment.CenterHorizontally)


                                                                        )

                                                                        if(txtCol== darkTealGreen) {
//                                                                            Image(
//                                                                                painter = painterResource(R.drawable.squiggle),
//                                                                                contentDescription = null,
//                                                                                alignment = Alignment.BottomCenter,
//                                                                                modifier = Modifier
//                                                                                    .size(
//                                                                                        30.dp
//                                                                                    )
//                                                                                    .align(
//                                                                                        Alignment.CenterHorizontally
//                                                                                    )
//                                                                            )
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

                                    Spacer(modifier = Modifier.height(24.dp))

                                    Text(
                                        currentMerch.description,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            //fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFF1E8),
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(
                                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                                            ),
                                            shadow = Shadow(
                                                color = Color(0xFF7E2953),
                                                offset = Offset(6f, 5f)
                                            )
                                        ) ,
                                        modifier = Modifier.padding(start = 30.dp,end= 30.dp)
                                    )

//                                    Text(
//                                        currentMerch.description,
//                                        style = TextStyle(
//                                            fontFamily = aileron,
//                                            fontWeight = FontWeight.Normal,
//                                            fontSize = 14.sp,
//                                            color = colors.onBackground
//                                        ),
//                                        modifier = Modifier.padding(start = 30.dp,end= 30.dp)
//                                    )
                                    Spacer(modifier = Modifier.height(32.dp))


                                    Row(horizontalArrangement = Arrangement.Center,modifier = Modifier
                                        .padding(start = 16.dp, end = 16.dp)
                                        .fillMaxWidth()) {





                                        //


                                        //
                                        Box(
                                            modifier = Modifier
                                                .height(50.dp)
                                                .weight(0.8f)
                                                .border(
                                                    1.dp,
                                                    colors.onBackground,
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
                                                    if (merchSize == "") {
                                                        Toast
                                                            .makeText(
                                                                requireContext(),
                                                                "Please select a size first",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else {

                                                        dbHandler.addNewitemIncart(
                                                            currentMerch.name,
                                                            currentMerch.price,
                                                            merchSize,
                                                            "1",
                                                            currentMerch.image_url,
                                                            currentMerch.material,
                                                            requireContext()
                                                        )
                                                        startActivity(
                                                            Intent(
                                                                requireContext(),
                                                                CartActivity::class.java
                                                            )
                                                        )
                                                        setCartCountIcon()
                                                    }
                                                }


                                        ) {



                                                Image(
                                                    painter = painterResource(R.drawable.buynow),
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize()
                                                )

                                        }

                                        Spacer(modifier = Modifier.width(30.dp))

                                        Box(
                                            modifier = Modifier
                                                .height(50.dp)
                                                .weight(1f)


                                                .clickable {
                                                    if (isInStock && merchSize != "") {
                                                        dbHandler.addNewitemIncart(
                                                            currentMerch.name,
                                                            currentMerch.price,
                                                            merchSize,
                                                            "1",
                                                            currentMerch.image_url,
                                                            currentMerch.material,
                                                            requireContext()
                                                        )
                                                        Toast
                                                            .makeText(
                                                                requireContext(),
                                                                currentMerch.name + " added to cart",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                        setCartCountIcon()
                                                    } else if (!isInStock) {
                                                        Toast
                                                            .makeText(
                                                                requireContext(),
                                                                "Out of Stock",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show();
                                                    } else {
                                                        Toast
                                                            .makeText(
                                                                requireContext(),
                                                                "Please select a Size first",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    }
                                                }


                                        ) {

                                                Image(
                                                    painter = painterResource(R.drawable.atc),
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize()
                                                )

                                        }

                                    }


                                }

                            }
                        }
                    }



                    Box(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(vertical = 20.dp),
                        Alignment.TopCenter
                    ) {
                        horizontalpager(
                            merModel = currentMerch,
                            context = requireContext(),
                            index
                        )
                    }


                }

            }



        }
    }

    @Preview
    @Composable
    fun MyContentPreview() {
        MyContent(popUp = 0)
    }
}


