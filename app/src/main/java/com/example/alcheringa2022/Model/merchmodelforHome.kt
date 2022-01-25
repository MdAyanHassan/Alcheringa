package com.example.alcheringa2022.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class merchmodelforHome(
    var Name: String="",
    var Type: String ="",
    var Description: String="",
    var Image: String="",
    var Available: Boolean=false,
):Parcelable
