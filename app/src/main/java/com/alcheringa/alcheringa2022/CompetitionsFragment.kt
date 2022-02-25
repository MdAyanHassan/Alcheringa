package com.alcheringa.alcheringa2022

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.alcheringa.alcheringa2022.Model.viewModelHome
import com.alcheringa.alcheringa2022.databinding.FragmentCompetitionsBinding
import com.alcheringa.alcheringa2022.ui.theme.Alcheringa2022Theme
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompetitionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompetitionsFragment : Fragment() {

    private lateinit var binding: FragmentCompetitionsBinding
    val homeViewModel:viewModelHome by activityViewModels()
    lateinit var Fm:FragmentManager
//    val events=mutableListOf(
//
//        eventdetail(
//            "JUBIN NAUTIYAL",
//            "Pro Nights",
//            OwnTime(11,9,0),
//            "ONLINE", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fjubin.jpg?alt=media&token=90983a9f-bd0d-483d-b2a8-542c1f1c0acb"
//        ),
//
//        eventdetail(
//            "DJ SNAKE",
//            "Pro Nights",
//            OwnTime(12,12,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
//        ),
//        eventdetail(
//            "TAYLOR SWIFT",
//            "Pro Nights",
//            OwnTime(12,14,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
//        )
//        ,
//
//        eventdetail(
//            "DJ SNAKE2",
//            "Pro Nights",
//            OwnTime(12,10,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Fdjsnake.jpg?alt=media&token=8c7aa9c9-d27a-4393-870a-ddf1cd58f175"
//        ),
//        eventdetail(
//            "TAYLOR SWIFT2",
//            "Pro Nights",
//            OwnTime(12,15,0),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f", durationInMin = 120
//        )
//        ,
//        eventdetail(
//            "TAYLOR SWIFT3",
//            "Pro Nights",
//            OwnTime(12,14,30),
//            "ON GROUND", "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2Ftaylor.webp?alt=media&token=cb2a2ffb-009c-4361-b918-0fec2223228f"
//        )
//
//
//
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fm= parentFragmentManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_events, container, false)
        binding = FragmentCompetitionsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scheduleDatabase=ScheduleDatabase(context)
        val eventslist=scheduleDatabase.schedule;
//        binding.account.setOnClickListener {
//            startActivity(Intent(context,Account::class.java));
//
//        }
        binding.constraintLayout.setOnClickListener{requireActivity().onBackPressed()}

        binding.competitionsCompose.setContent {
            Full_view()
        }
    }

    @Composable
    @Preview
    fun Full_view() {
        Alcheringa2022Theme {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                /*.background(Color.Black)*/
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                Events_row(heading = "VOGUE NATION")
                Events_row(heading = "CLASS APART")
                Events_row(heading = "ANY BODY CAN DANCE")
                Events_row(heading = "MUSIC")
                Events_row(heading = "LITERARY")
                Events_row(heading = "ART TALKIES")
                Events_row(heading = "DIGITAL DEXTERITY")
                Events_row(heading = "LIGHTS CAMERA ACTION")
            }
        }
    }

    @Composable
    fun Events_row(heading: String) {
        val events_list=homeViewModel.allEventsWithLive.filter {
                data-> data.eventdetail.type.replace("\\s".toRegex(), "").uppercase()== heading.replace("\\s".toRegex(), "").uppercase()}
if (events_list.isNotEmpty()){
        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp) ){
            Text(text = heading.uppercase(
                Locale.getDefault()), style = MaterialTheme.typography.h2)
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 20.dp)
        ) { items(events_list) { dataEach -> context?.let { Event_card(eventdetail = dataEach,homeViewModel,it,Fm) } } }

        Spacer(modifier = Modifier.height(24.dp))}
    }
}