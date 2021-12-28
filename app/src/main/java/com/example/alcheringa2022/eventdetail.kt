package com.example.alcheringa2022

import androidx.compose.ui.graphics.painter.Painter
import java.sql.Time
import java.time.LocalDateTime

data class eventdetail(
    val artist:String, val category:String,
    val time: String,
    val mode: String, val imgurl:Int)
