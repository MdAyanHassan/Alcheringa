package com.alcheringa.alcheringa2022.Model

import com.google.firebase.firestore.GeoPoint

data class InformalModel(
    val name: String = "",
    val location: GeoPoint = GeoPoint(26.18938295597227, 91.69602130270817),
    val imgUrl: String = ""
)
