package com.example.alcheringa2022

import androidx.compose.ui.graphics.painter.Painter
import java.sql.Time
import java.time.Duration
import java.time.LocalDateTime

data class eventdetail(
    val artist:String,
    val category:String,
    val Starttime: OwnTime,
    val mode: String,
    val imgurl:Int,
    var durationInHours:Int = 1
    )
data class OwnTime(val date:Int, val hours:Int)
