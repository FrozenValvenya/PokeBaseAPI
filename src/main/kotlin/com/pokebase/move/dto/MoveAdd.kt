package com.pokebase.move.dto

import com.pokebase.model.Category
import com.pokebase.model.Type

@kotlinx.serialization.Serializable
data class MoveAdd(
    val name: String,
    val type: Type,
    val category: Category,
    val power: Int?,
    val accuracy: Int?,
    val pp: Int,
    val description: String
)
