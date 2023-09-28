package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val name: String,
    val description: String,
    val location: LatLong
)

@Serializable
data class LatLong(
    val latitude: Double,
    val longitude: Double
)
