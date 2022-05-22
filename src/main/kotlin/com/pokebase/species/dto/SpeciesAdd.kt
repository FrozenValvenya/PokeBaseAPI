package com.pokebase.species.dto

import com.pokebase.model.Stats
import com.pokebase.model.Type
import com.pokebase.move.dto.MoveResponse

@kotlinx.serialization.Serializable
data class SpeciesAdd(
    val name: String,
    val primaryType: Type,
    val secondaryType: Type?,
    val evolutionIds: List<Int>,
    val weight: Float,
    val height: Float,
    val baseStats: Stats,
    val movePoolIds: List<Int>,
    val image: String
)
