package com.pokebase.model

data class Move(
    val moveId : Int,
    val name : String,
    val type : Type,
    val power : Int,
    val accuracy : Int,
    val description : String
)
