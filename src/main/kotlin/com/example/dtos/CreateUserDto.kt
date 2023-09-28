package com.example.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto (val name: String, val email: String)