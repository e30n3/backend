package ru.playzone.features.registration

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceive(
    val login: String,
    val email: String,
    val password: String,
)

@Serializable
data class RegisterResponse(
    val token: String,
)