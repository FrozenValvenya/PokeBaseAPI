package com.pokebase.auth.dto

@kotlinx.serialization.Serializable
data class UserJWT(
    val userId : Int,
    val JWT : String
)
