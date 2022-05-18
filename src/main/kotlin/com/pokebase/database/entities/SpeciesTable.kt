package com.pokebase.database.entities

import com.pokebase.model.Type
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.net.URL

object SpeciesTable : IntIdTable("species", "species_id") {
    val name : Column<String> = varchar("name", 15)
    val primaryType : Column<Type> = enumerationByName("primary_type", 10, Type::class)
    val secondaryType : Column<Type?> = enumerationByName("secondary_type", 10, Type::class).nullable()
    val weight : Column<Float> = float("weight")
    val height : Column<Float> = float("height")
    val hp: Column<Int> = integer("hp")
    val atk: Column<Int> = integer("atk")
    val def: Column<Int> = integer("def")
    val spa: Column<Int> = integer("spa")
    val spd: Column<Int> = integer("spd")
    val spe: Column<Int> = integer("spe")
    val image: Column<String> = varchar("image", 100)

}

object SpeciesToSpeciesTable : Table() {
    val evolution = reference("evolution_id", SpeciesTable)
    val preEvolution = reference("pre_evolution_id", SpeciesTable)
}

class Species(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Species>(SpeciesTable)
    var name by SpeciesTable.name
    var primaryType by SpeciesTable.primaryType
    var secondaryType by SpeciesTable.secondaryType
    var weight by SpeciesTable.weight
    var height by SpeciesTable.height
    var hp by SpeciesTable.hp
    var atk by SpeciesTable.atk
    var def by SpeciesTable.def
    var spa by SpeciesTable.spa
    var spd by SpeciesTable.spd
    var spe by SpeciesTable.spe
    var image by SpeciesTable.image
    var evolutions by Species.via(SpeciesToSpeciesTable.preEvolution, SpeciesToSpeciesTable.evolution)
    var movePool by Move via MoveSpeciesTable
}