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
    val nature: Column<Nature> = enumerationByName("nature", 15, Nature::class).clientDefault { Nature.Hardy }
    val ivHp: Column<Int> = integer("iv_hp").default(30)
    val ivAtk: Column<Int> = integer("iv_atk").default(30)
    val ivDef: Column<Int> = integer("iv_def").default(30)
    val ivSpa: Column<Int> = integer("iv_spa").default(30)
    val ivSpd: Column<Int> = integer("iv_spd").default(30)
    val ivSpe: Column<Int> = integer("iv_spe").default(30)
    val evHp: Column<Int> = integer("ev_hp").default(0)
    val evAtk: Column<Int> = integer("ev_atk").default(0)
    val evDef: Column<Int> = integer("ev_def").default(0)
    val evSpa: Column<Int> = integer("ev_spa").default(0)
    val evSpd: Column<Int> = integer("ev_spd").default(0)
    val evSpe: Column<Int> = integer("ev_spe").default(0)
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
