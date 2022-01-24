package com.example.alcheringa2022

import android.os.Parcelable
import androidx.compose.ui.graphics.painter.Painter
import kotlinx.android.parcel.Parcelize
import java.sql.Time
import java.time.Duration
import java.time.LocalDateTime
@Parcelize
data class eventdetail(
    val artist:String="",
    val category:String="",
    val Starttime: OwnTime= OwnTime(0,0,0),
    val mode: String="",
    val imgurl:String="",
    var durationInMin:Int = 60,
    var peopleCustomized: Int=0
    ):Parcelable

@Parcelize
data class OwnTime(val date:Int=0, val hours:Int=0,val min:Int=0):Parcelable
