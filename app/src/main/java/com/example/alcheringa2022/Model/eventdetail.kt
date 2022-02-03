package com.example.alcheringa2022

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import kotlinx.android.parcel.Parcelize
import java.sql.Time
import java.time.Duration
import java.time.LocalDateTime
data class eventWithLive(val eventdetail: eventdetail , var isLive:MutableState<Boolean> = mutableStateOf(false))

@Parcelize
data class eventdetail(
    val artist:String="",
    val category:String="",
    val starttime: OwnTime= OwnTime(0,0,0),
    val mode: String="",
    val imgurl:String="",
    var durationInMin:Int = 60,
    var genre:List<String> = emptyList(),
    val descriptionEvent:String="",
    val venue:String=""
//    var peopleCustomized: Int=0,
    ):Parcelable

@Parcelize
data class OwnTime(val date:Int=0, val hours:Int=0,val min:Int=0):Parcelable
