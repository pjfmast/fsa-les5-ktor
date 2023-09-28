package com.example.dao

import com.example.models.Item
import com.example.models.LatLong
import com.example.models.Reservation
import com.example.models.User
import kotlinx.datetime.LocalDate

// Following: https://ktor.io/docs/interactive-website-add-persistence.html#persistence_logic
interface DAOFacade {
    suspend fun allItems(): List<Item>
    suspend fun item(id: Int): Item?
    suspend fun addItem(name: String, description: String, latLong: LatLong): Item?
    suspend fun deleteItem(id: Int): Boolean

    suspend fun allUsers(): List<User>
    suspend fun user(id: Int): User?
    suspend fun addUser(name: String, email: String): User?
    suspend fun deleteUser(id: Int): Boolean

    suspend fun allReservations(): List<Reservation>
    suspend fun addReservation(userId: Int, itemId: Int, date: LocalDate): Reservation?
}