package com.pokebase.database.entities

import org.jetbrains.exposed.sql.Table

object MovePokemonTable : Table() {
    val move = reference("move", MoveTable)
    val pokemon = reference("pokemon", PokemonTable)
    override val primaryKey = PrimaryKey(move, pokemon, name = "PK_MovePokemon")
}