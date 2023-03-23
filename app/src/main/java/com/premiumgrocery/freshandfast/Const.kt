package com.premiumgrocery.freshandfast

import com.premiumgrocery.freshandfast.remote.model.ShippingAddress

object Const {
    const val imageBaseUrl = "https://rjtmobile.com/grocery/images/"
    const val apiBaseUrl = "https://orca-app-jhg4l.ondigitalocean.app/api/"
    const val sharedPrefName = "freshAndFast"

    const val dbName = "fastAndFresh"
    const val dbTableCategory = "shopCategory"
    const val dbTableSubcategory = "shopSubcategory"
    const val dbTableCurrentOrder = "currentOrder"

    const val processLabelStart = "start"
    const val processLabelSearchProduct = "product_search"
    const val processLabelGetCurrentProducts = "current_products"
    const val processLabelGetTotalOrderCost = "total_order_cost"
    const val processLabelDeleteCurrentProducts = "delete_products"
    const val processLabelPlaceOrder = "place_order"
    const val processLabelGetOrders = "get_orders"

    const val placeholderUserId = "6346077ca0227900171cdba0"
    const val placeholderUserEmail = "abc@gmail.com"
    const val placeholderToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImZpcnN0TmFtZSI6ImFiYyIsIl9pZCI6IjYzNDYwNzdjYTAyMjc5MDAxNzFjZGJhMCIsImVtYWlsIjoiYWJjQGdtYWlsLmNvbSIsIm1vYmlsZSI6Ijk5OTk5IiwicGFzc3dvcmQiOiIkMmEkMTAkMmVDd3FiWWtzTnJIMHdtTmp6eWxZLmIzZEg3S1lzQU0xbG1jUUN1ODdJa3Z0RzZwUzBCRWkiLCJjcmVhdGVkQXQiOiIyMDIyLTEwLTEyVDAwOjE3OjAwLjg0MVoiLCJfX3YiOjB9LCJpYXQiOjE2Nzc5NTYzNjV9.94rAcLDg7-8XIeD8L6NtnCKg_JMcjuhRbLvD9K6yeIw"
    val placeholderShippingAddress = ShippingAddress(
        city = "bolts",
        houseNo = "69",
        pincode = 69420,
        streetName = "ligma",
        type = "fedex toss"
    )
}

enum class OrderStatus {
    SUCCESS,
    FAIL
}