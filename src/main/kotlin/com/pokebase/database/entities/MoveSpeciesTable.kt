package com.pokebase.database.entities

import org.jetbrains.exposed.sql.Table

object MoveSpeciesTable : Table() {
    val move = reference("move", MoveTable)
    val species = reference("pokemon", SpeciesTable)
    override val primaryKey = PrimaryKey(move, species, name = "PK_MoveSpecies")
}