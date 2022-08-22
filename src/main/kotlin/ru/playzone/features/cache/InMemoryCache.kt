package ru.playzone.features.cache

import ru.playzone.features.registration.RegisterReceive

data class TokenCache(
    val login: String,
    val token: String
)


object InMemoryCache {
    val userList: MutableList<RegisterReceive> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}