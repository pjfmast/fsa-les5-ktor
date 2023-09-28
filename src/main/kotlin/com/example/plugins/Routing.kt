package com.example.plugins

import com.example.routes.itemRoutes
import com.example.routes.reservationRoutes
import com.example.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
//        openAPI(path="openapi", swaggerFile = "openapi/documentation.yaml")

        userRoutes()
        itemRoutes()
        reservationRoutes()
    }
}
