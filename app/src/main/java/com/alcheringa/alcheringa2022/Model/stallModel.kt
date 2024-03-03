package com.alcheringa.alcheringa2022.Model

import com.google.android.gms.maps.model.LatLng

data class stallModel (
    val name: String = "",
    val description: String = "",
    val imgurl: String = "",
    val menu: List<stallMenuItem> = emptyList(),
    val LatLng: LatLng = LatLng(26.190750, 91.696418)
)

data class stallMenuItem(
    val name: String = "",
    val price: Double = 0.0
)