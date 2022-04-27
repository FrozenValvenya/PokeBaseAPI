package com.pokebase.model

data class Pokemon(
    val pokemonId : Int,
    val nickname : String,
    val species: Species,
    val level: Int,
    val nature: Nature,
    val moves : ArrayList<Move>,
    val iv : Stats,
    val ev : Stats,
    val user : User
)
