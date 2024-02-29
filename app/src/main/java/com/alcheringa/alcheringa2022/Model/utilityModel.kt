package com.alcheringa.alcheringa2022.Model

import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint
import com.google.type.LatLng
import io.grpc.internal.DnsNameResolver.SrvRecord
import kotlinx.parcelize.Parcelize


data class utilityModel(
    val name: String = "",
    val description: String = "",
    val location: GeoPoint? = null,
    val type: String = "",
    val link: String = "",
    val imgUrl: String = ""
    )
