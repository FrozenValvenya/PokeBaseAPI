package com.pokebase.model

data class Species(
    val speciesId : Int,
    val name : String,
    val primaryType : Type,
    val secondaryType : Type?,
    val weight : Float,
    val height : Float,
    val baseStats : Stats,
    val movePool : ArrayList<Move>
)