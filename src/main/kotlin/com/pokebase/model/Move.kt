package com.pokebase.model

data class Move(
    val moveId : Int,
    val name : String,
    val type : Type,
    val category : Category,
    val power : Int?,
    val accuracy : Int?,
    val pp : Int,
    val description : String
)
