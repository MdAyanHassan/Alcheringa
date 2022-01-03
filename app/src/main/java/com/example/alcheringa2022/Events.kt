package com.example.alcheringa2022

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.alcheringa2022.databinding.FragmentEventsBinding
import com.example.alcheringa2022.ui.theme.Alcheringa2022Theme
import java.util.*


class Events : Fragment() {

    private lateinit var binding: FragmentEventsBinding
    private val events = mutableListOf(

        eventdetail(
            "JUBIN NAUTIYAL",
            "Pro Nights",
            "12 Feb, 4 PM",
            "Online", R.drawable.jubin
        ),

        eventdetail(
            "DJ SNAKE",
            "Pro Nights",
            "11 Feb, 4 PM",
            "Online", R.drawable.djsnake
        ),
        eventdetail(
            "TAYLOR SWIFT",
            "Pro Nights",
            "13 Feb, 4 PM",
            "Online", R.drawable.taylor
        )


    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_events, container, false)
        binding = FragmentEventsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.eventsCompose.setContent {
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
                    .verticalScroll(rememberScrollState()).padding(horizontal = 12.dp)
                    /*.background(Color.Black)*/
            ) {
                Events_row(heading = "Pronites", events_list = events)
                Events_row(heading = "ProShows", events_list = events)
                Events_row(heading = "Creator's Camp", events_list = events)
                Events_row(heading = "Humor Fest", events_list = events)
                Events_row(heading = "Competitions", events_list = events)
            }
        }
    }

    @Composable
    fun Events_row(heading: String, events_list: MutableList<eventdetail>) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(text = heading.uppercase(Locale.getDefault()), style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(events_list) { dataEach -> Event_card(eventdetail = dataEach) }
        }
    }
}

