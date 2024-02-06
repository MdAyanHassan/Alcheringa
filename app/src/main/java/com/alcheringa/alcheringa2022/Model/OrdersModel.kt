package com.alcheringa.alcheringa2022.Model

data class OrdersModel(
    val status: String,
    var merch_name: String,
    var merch_type: String,
    var merch_quantity: String,
    var merch_size: String,
    var price: String,
    var delivery_date: String,
    var Image: String
)
