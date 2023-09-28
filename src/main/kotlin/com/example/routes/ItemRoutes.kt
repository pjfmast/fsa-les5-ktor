package com.example.routes

import com.example.dao.daoInMemoryImpl
import com.example.dtos.CreateItemDto
import com.example.models.Item
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.itemRoutes() {
    route("item") {
        get {
            val items = daoInMemoryImpl.allItems()
            call.respond(message = items)
        }

        get("{id?}") {
            val id = call.parameters["id"]
                ?.toIntOrNull()
                ?: return@get call.respondText(
                    text = "Missing id",
                    status = HttpStatusCode.BadRequest
                )
            val item = daoInMemoryImpl.item(id) ?: return@get call.respondText(
                text = "No item with id $id",
                status = HttpStatusCode.NotFound
            )

            call.respond(item)
        }

        post {
            val item = call.receive<CreateItemDto>()
            val newItem = daoInMemoryImpl.addItem(item.name, item.description, item.location)
            if (newItem != null) {
// call below is the same as:                call.respondText(Json.encodeToString(newItem), status = HttpStatusCode.Created)
                call.respond(message = newItem, status = HttpStatusCode.Created)
            } else {
                // in this situation it is a server error
                call.respondText("Item cannot be created", status = HttpStatusCode.NotImplemented)
            }
        }

        delete("{id?}") {
            val id = call.parameters["id"]
                ?. toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (daoInMemoryImpl.deleteItem(id)) {
                call.respondText("Item removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}