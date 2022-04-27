package com.pokebase.model

import java.util.UUID

data class User(
    val userId : UUID,
    val name : String,
    val isAdmin : Boolean,
    val pokemonList : List<Pokemon>
)
