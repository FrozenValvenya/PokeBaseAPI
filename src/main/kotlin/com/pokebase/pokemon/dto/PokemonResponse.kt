package com.pokebase.pokemon.dto

import com.pokebase.model.Stats
import com.pokebase.move.dto.MoveResponse
import com.pokebase.species.dto.SpeciesResponse

data class PokemonResponse(
    val pokemonId: Int,
    val nickname: String,
    val species: SpeciesResponse,
    val level: Int,
    val stats: Stats,
    val moves: List<MoveResponse>
)
