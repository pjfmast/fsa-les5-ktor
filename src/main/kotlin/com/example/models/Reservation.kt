package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Reservation(
    val userId: Int,
    val itemId: Int,
    val date: LocalDate
)