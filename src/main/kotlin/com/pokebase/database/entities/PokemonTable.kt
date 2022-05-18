package com.pokebase.database.entities

import com.pokebase.model.Nature
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object PokemonTable : IntIdTable("pokemon", "pokemon_id") {
    val nickname : Column<String> = varchar("nickname", 20)
    val species = reference("species", SpeciesTable)
    val level: Column<Int> = integer("level")
    val nature: Column<Nature> = enumerationByName("nature", 15, Nature::class)
    val ivHp: Column<Int> = integer("hp")
    val ivAtk: Column<Int> = integer("atk")
    val ivDef: Column<Int> = integer("def")
    val ivSpa: Column<Int> = integer("spa")
    val ivSpd: Column<Int> = integer("spd")
    val ivSpe: Column<Int> = integer("spe")
    val evHp: Column<Int> = integer("hp")
    val evAtk: Column<Int> = integer("atk")
    val evDef: Column<Int> = integer("def")
    val evSpa: Column<Int> = integer("spa")
    val evSpd: Column<Int> = integer("spd")
    val evSpe: Column<Int> = integer("spe")
    val user = reference("user", UserTable)
}

class Pokemon(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Pokemon>(PokemonTable)
    var nickname by PokemonTable.nickname
    var species by Species referencedOn PokemonTable.species
    var level by  PokemonTable.level
    var nature by PokemonTable.nature
    var ivHp by PokemonTable.ivHp
    var ivAtk by PokemonTable.ivAtk
    var ivDef by PokemonTable.ivDef
    var ivSpa by PokemonTable.ivSpa
    var ivSpd by PokemonTable.ivSpd
    var ivSpe by PokemonTable.ivSpe
    var evHp by PokemonTable.evHp
    var evAtk by PokemonTable.evAtk
    var evDef by PokemonTable.evDef
    var evSpa by PokemonTable.evSpa
    var evSpd by PokemonTable.evSpd
    var evSpe by PokemonTable.evSpe
    var user by User referencedOn PokemonTable.user
    var moves by Move via MovePokemonTable
}
