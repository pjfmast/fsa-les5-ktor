package com.example.routes

import com.example.dao.daoInMemoryImpl
import com.example.dtos.CreateReservationDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.reservationRoutes() {
    route("reservation") {
        get {
            val reservations = daoInMemoryImpl.allReservations()
            call.respond(reservations)
        }


        post {
            val reservation = call.receive<CreateReservationDto>()
            val newReservation = daoInMemoryImpl.addReservation(reservation.userId, reservation.itemId, reservation.date)
            if (newReservation != null) {
                call.respond(message = Json.encodeToString(newReservation), status = HttpStatusCode.Created)
            } else {
                // in this situation it is a server error
                call.respondText(text = "Reservation cannot be created", status = HttpStatusCode.NotImplemented)
            }
        }
    }
}