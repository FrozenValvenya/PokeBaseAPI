package com.pokebase.model

import java.net.URL

data class Species(
    val speciesId : Int,
    val name : String,
    val primaryType : Type,
    val secondaryType : Type?,
    val evolution : Species?,
    val preEvolution : Species?,
    val weight : Float,
    val height : Float,
    val baseStats : Stats,
    val movePool : ArrayList<Move>,
    val abilities : ArrayList<String>,
    val image : URL
)