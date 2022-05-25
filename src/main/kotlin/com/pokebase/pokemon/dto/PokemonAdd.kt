package com.pokebase.pokemon.dto

import com.pokebase.model.Nature
import com.pokebase.model.Stats

@kotlinx.serialization.Serializable
data class PokemonAdd(
    val nickname: String,
    val speciesId: Int,
    val level: Int
)
