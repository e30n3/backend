package ru.playzone.features.registration

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import ru.playzone.features.cache.InMemoryCache
import ru.playzone.features.cache.TokenCache
import ru.playzone.features.login.LoginReceive
import ru.playzone.util.isValidEmail
import java.util.UUID

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val receive = call.receive<RegisterReceive>()
            if (!receive.email.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, message = "Некорректный email")
            }
            if (receive.login in InMemoryCache.userList.map { it.login }){
                call.respond(HttpStatusCode.Conflict, message = "Такой логин уже существует")
            }

            val token = UUID.randomUUID().toString()
            InMemoryCache.userList.add(receive)
            InMemoryCache.token.add(TokenCache(receive.login, token))


            call.respond(RegisterResponse(token))
        }
    }
}
