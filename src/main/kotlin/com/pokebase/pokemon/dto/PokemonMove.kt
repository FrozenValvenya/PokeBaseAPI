package com.pokebase.pokemon.dto

@kotlinx.serialization.Serializable
data class PokemonMove(
    val pokemonId: Int,
    val moveId: Int
)
