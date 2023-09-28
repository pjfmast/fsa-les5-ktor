package com.example.dtos

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
class CreateReservationDto (
    val userId: Int,
    val itemId: Int,
    val date: LocalDate
)