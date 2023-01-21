package com.alcheringa.alcheringa2022.Model

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.android.parcel.Parcelize

data class eventWithLive(val eventdetail: eventdetail , var isLive:MutableState<Boolean> = mutableStateOf(false))

@Parcelize
data class eventdetail(
    val artist:String="",
    val category:String="",
    val starttime: OwnTime= OwnTime(0,0,0),
    val mode: String="Offline",
    val imgurl:String="",
    var durationInMin:Int = 60,
    var genre:List<String> = emptyList(),
    val descriptionEvent:String="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
    var venue:String="",
    val type:String="",
    val joinlink:String="https://alcheringa.in",
    val reglink:String="https://alcheringa.in",
    var stream:Boolean = false,
//    var peopleCustomized: Int=0,
    ):Parcelable

@Parcelize
data class OwnTime(val date:Int=0, val hours:Int=0,val min:Int=0):Parcelable
