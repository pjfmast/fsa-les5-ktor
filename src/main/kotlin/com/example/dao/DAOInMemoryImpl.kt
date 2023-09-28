package com.example.dao

import com.example.models.Item
import com.example.models.LatLong
import com.example.models.Reservation
import com.example.models.User
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.*
import kotlin.random.Random


class DAOInMemoryImpl : DAOFacade {
    private val items: MutableList<Item> = mutableListOf()
    private val users: MutableList<User> = mutableListOf()
    private val reservations: MutableList<Reservation> = mutableListOf()

    override suspend fun allItems(): List<Item> = items.toList()

    override suspend fun item(id: Int): Item? = items.firstOrNull { it.id == id }

    override suspend fun addItem(name: String, description: String, latLong: LatLong): Item? {
        val newId = (items.maxOfOrNull { it.id } ?: 0) + 1
        val newItem = Item(newId, name, description, latLong)
        items.add(newItem)
        return newItem
    }

    override suspend fun deleteItem(id: Int): Boolean = items.removeIf { it.id == id }

    override suspend fun allUsers(): List<User> = users.toList()
    override suspend fun user(id: Int): User? = users.firstOrNull { it.id == id }

    override suspend fun addUser(name: String, email: String): User? {
        val newId: Int = users.maxOfOrNull { it.id }?.plus((1)) ?: 1
        val newUser = User(newId, name, email)
        users.add(newUser)
        return newUser
    }

    override suspend fun deleteUser(id: Int): Boolean = users.removeIf { it.id == id }

    override suspend fun allReservations(): List<Reservation> = reservations.toList()

    override suspend fun addReservation(userId: Int, itemId: Int, date: LocalDate): Reservation? {
        val isAvailable = reservations.none { it.itemId == itemId && it.date == date }

        return if (isAvailable) {
            val newReservation = Reservation(userId, itemId, date)
            reservations.add(newReservation)
            newReservation
        } else {
            null
        }
    }
}

val daoInMemoryImpl = DAOInMemoryImpl().apply {
    runBlocking {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        fun randomDay(): LocalDate = today.plus(DatePeriod(0, 0, Random.nextInt(29)))

        fun randomLatLong(): LatLong = LatLong(
            latitude = 51.58513744454799 + Random.nextDouble(-0.01, 0.01),
            longitude = 4.797598620308406 + Random.nextDouble(-0.01, 0.01)
        )

        addUser("Henk", "Henk@avans.nl")
        addUser("Nicole", "Nicole@avans.nl")
        addUser("Ilse", "Ilse@avans.nl")
        addUser("Ge", "Ge@avans.nl")

        addItem("ladder", "veilige ladder tot 7 meter", randomLatLong())
        addItem("kruiwagen", "band kan niet lek", randomLatLong())
        addItem("hamerboor", "met betonboortjes", randomLatLong())
        addItem("kettingzaag", "voor boomstammen tot 80cm doorsnede", randomLatLong())
        addItem("steiger", "veilig in de dakgoot werken", randomLatLong())

        val userIds = allUsers().map { it.id }
        val itemIds = allItems().map { it.id }

        repeat(20) {
            addReservation(userIds.random(), itemIds.random(), randomDay())
        }
    }
}

