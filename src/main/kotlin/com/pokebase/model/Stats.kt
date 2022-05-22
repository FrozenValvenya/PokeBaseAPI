package com.pokebase.model

@kotlinx.serialization.Serializable
data class Stats(
    val hp : Int,
    val atk: Int,
    val def : Int,
    val spa : Int,
    val spd : Int,
    val spe : Int
)
