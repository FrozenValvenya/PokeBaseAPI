package com.pokebase.species.dto

import com.pokebase.model.Stats
import com.pokebase.model.Type
import com.pokebase.move.dto.MoveResponse

@kotlinx.serialization.Serializable
data class SpeciesShort(
    val speciesId: Int,
    val name: String,
    val primaryType: Type,
    val secondaryType: Type?,
    val image: String
)
