package com.pokebase.auth.dto

@kotlinx.serialization.Serializable
data class UserCredentials(
    val username: String,
    val password: String
)
