package com.example.dtos

import com.example.models.LatLong
import kotlinx.serialization.Serializable

@Serializable
data class CreateItemDto (
    val name: String,
    val description: String,
    val location: LatLong
)