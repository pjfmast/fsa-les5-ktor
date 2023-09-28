package com.example.routes

import com.example.module
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class UserRoutesKtTest {

    @Test
    fun testPostUser() = testApplication {
        application {
            module()
        }
        client.post("/user").apply {
            TODO("Please write your test here")
        }
    }
}