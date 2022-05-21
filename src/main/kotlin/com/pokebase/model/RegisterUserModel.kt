package com.pokebase.model

data class RegisterUserModel(
    val username: String,
    val password: String,
    val isAdmin: Boolean
)
