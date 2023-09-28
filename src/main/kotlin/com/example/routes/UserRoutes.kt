package com.example.routes

import com.example.dtos.CreateUserDto
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.request.*
import com.example.dao.daoInMemoryImpl as dao

fun Route.userRoutes() {
    route("user") {
        get {
            val users = dao.allUsers()
            call.respond(users)
        }

        get("{id?}") {
            val id = call.parameters["id"]
                ?.toIntOrNull()
                ?: return@get call.respondText(
                    text = "Missing id",
                    status = HttpStatusCode.BadRequest
                )
            val user = dao.user(id) ?: return@get call.respondText(
                text = "No user with id $id",
                status = HttpStatusCode.NotFound
            )

            call.respond(user)
        }

        post {
            val user = call.receive<CreateUserDto>()
            val newUser = dao.addUser(user.name, user.email)
            if (newUser != null) {
//                call.respondText(Json.encodeToString(newUser), status = HttpStatusCode.Created)
                call.respond(message = newUser, status = HttpStatusCode.Created)
            } else {
                // in this situation it is a server error
                call.respondText("User cannot be created", status = HttpStatusCode.NotImplemented)
            }
        }

        delete("{id?}") {
            val id = call.parameters["id"]
                ?. toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (dao.deleteUser(id)) {
                call.respondText("User removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}