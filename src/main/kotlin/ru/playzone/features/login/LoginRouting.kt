package ru.playzone.features.login

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import ru.playzone.features.cache.InMemoryCache
import ru.playzone.features.cache.TokenCache
import java.util.UUID

fun Application.configureLoginRouting() {

    routing {
        post("/login") {
            val receive = call.receive<LoginReceive>()

            val first = InMemoryCache.userList.firstOrNull { it.login == receive.login }
            if (first == null) call.respond(HttpStatusCode.BadRequest, message = "") else {
                if (first.password == receive.password) {
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.token.add(TokenCache(receive.login, token))
                    call.respond(LoginResponse(token))
                }
                call.respond(HttpStatusCode.BadRequest, message = "Invalid Password")
            }
        }
    }
}
