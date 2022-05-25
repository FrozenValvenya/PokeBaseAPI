package com.pokebase.pokemon.dto

import com.pokebase.model.Nature
import com.pokebase.species.dto.SpeciesShort

@kotlinx.serialization.Serializable
data class PokemonShort(
    val pokemonId: Int,
    val nickname: String,
    val species: SpeciesShort,
    val level: Int
)
