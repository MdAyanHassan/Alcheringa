package com.alcheringa.alcheringa2022.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class sponsersnew(val imageurl:String="",val title:String="", val heading:Boolean=false):Parcelable
