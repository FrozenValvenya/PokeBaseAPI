package com.pokebase.damage.dto

@kotlinx.serialization.Serializable
data class DamageDTO(
    val attackerId: Int,
    val defenderId: Int,
    val moveId: Int
)
