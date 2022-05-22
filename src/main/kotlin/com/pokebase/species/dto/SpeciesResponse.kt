package com.pokebase.species.dto

import com.pokebase.model.Stats
import com.pokebase.model.Type
import com.pokebase.move.dto.MoveResponse

@kotlinx.serialization.Serializable
data class SpeciesResponse(
    val speciesId: Int,
    val name: String,
    val primaryType: Type,
    val secondaryType: Type?,
    val evolutions: List<SpeciesResponse>,
    val weight: Float,
    val height: Float,
    val baseStats: Stats,
    val movePool: List<MoveResponse>,
    val image: String
)
