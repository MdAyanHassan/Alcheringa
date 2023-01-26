
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.alcheringa.alcheringa2022.SliderTransformer

// ---------------- class ViewPagerScope ----------------
/**
 * ViewPagerScope class will provide a Scope from which we will get the access to
 * two important scope members [androidViewPager2] and [ViewPagerChild].
 *
 * @param viewPagerChildren a `@NonNull` List of [ViewPagerChild] object/Composable.
 */
class ViewPagerScope(private val viewPagerChildren: MutableList<ViewPagerScope.ViewPagerChild>) {
    // ---------------- attribute androidViewPager2 ----------------
    /**
     * androidViewPager2 Provides the current object of Android ViewPager2 which we are using in
     * Jetpack Compose ViewPager library.
     *
     * With the help of this object user will to able to receive callbacks by adding some listeners
     * or can attach it with some TabLayout like things.
     *
     * for very own custom ViewPager in Composable scope user can override its adapter and other
     * properties as well.
     */
    lateinit var androidViewPager2: ViewPager2

    // ---------------- class ViewPagerChild ----------------
    /**
     * ViewPagerChild class will allow us to add [Composable] stuff in Android ViewPager screen,
     * Instead of inflating new View components we will inflate a single ComposableView in which
     * we will attach/load the content of ViewPagerChild.
     *
     * @param content [Composable] stuff that will be shown on the screen of [ViewPager].
     */
    inner class ViewPagerChild(
        private val content: @Composable () -> Unit = {}
    ): @Composable () -> () -> Unit {
        init { viewPagerChildren.add(this) }
        override fun invoke() = content
    }
}

// ---------------- composable fun ViewPager ----------------
/**
 * ViewPager is a [Composable] function that is able to inflate a Android [ViewPager2] view within
 * the scope of [modifier].
 *
 * @param modifier Modifier to be applied to the layout corresponding to the ViewPager.
 * @param verticalOrientation Boolean will define the scroll Orientation between [Children][ViewPagerScope.ViewPagerChild]
 * @param content [ViewPagerScope] from which we will be able to add some [Children][ViewPagerScope.ViewPagerChild]
 * in our [ViewPager].
 *
 * @return [Unit]
 */

@Composable
fun ViewPagernew(
    modifier: Modifier = Modifier,
    verticalOrientation: Boolean = false,


    content: ViewPagerScope.() -> Unit = {}
) {
   val paddingend= LocalDensity.current.run { 20.dp.toPx().toInt() }
    val paddingstart= LocalDensity.current.run { 20.dp.toPx().toInt() }
    val viewPagerChildren = mutableListOf<ViewPagerScope.ViewPagerChild>()
    val viewPagerScope = ViewPagerScope(viewPagerChildren)

    class ComposeViewHolder(val composeView: ComposeView): RecyclerView.ViewHolder(composeView)
    val adapter = object: RecyclerView.Adapter<ComposeViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ComposeViewHolder(
            ComposeView(parent.context).apply { layoutParams = parent.layoutParams }
        )
        override fun onBindViewHolder(holder: ComposeViewHolder, position: Int) {
            holder.composeView.setContent { viewPagerChildren[position]()() }
        }
        override fun getItemCount() = viewPagerChildren.size
    }

    //This Library yet uses a Traditional ViewPager2 which is needed to replace
    //will fully Composable Element in future releases.
    AndroidView(
        modifier = modifier,
        factory = { ViewPager2(it).apply { this.adapter = adapter
            this.clipChildren=false
            this.clipToPadding=false
            this.offscreenPageLimit=3
            this.layoutParams.apply { setPadding(paddingstart,0, paddingend,0) }
            this.setPageTransformer(SliderTransformer(4))
        } }
    ) {
        if(verticalOrientation) it.orientation = ViewPager2.ORIENTATION_VERTICAL
        content(viewPagerScope.apply { androidViewPager2 = it })
    }
}