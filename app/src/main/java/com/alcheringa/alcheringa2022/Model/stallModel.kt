package com.alcheringa.alcheringa2022.Model

data class stallModel (
    val name: String = "",
    val description: String = "",
    val imgurl: String = "",
    val menu: List<stallMenuItem> = emptyList()
)

data class stallMenuItem(
    val name: String = "",
    val price: Double = 0.0
)