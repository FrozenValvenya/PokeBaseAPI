package com.pokebase.database.entities

import com.pokebase.model.Category
import com.pokebase.model.Type
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object MoveTable : IntIdTable("move", "move_id") {
    val name: Column<String> = varchar("nickname", 20)
    val type: Column<Type> = enumerationByName("type", 10, Type::class)
    val category : Column<Category> = enumerationByName("Category", 10, Category::class)
    val power : Column<Int?> = integer("power").nullable()
    val accuracy : Column<Int?> = integer("accuracy").nullable()
    val pp : Column<Int> = integer("pp")
    val description : Column<String> = text("description")
}

class Move (id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Move>(MoveTable)
    var name by MoveTable.name
    var type by MoveTable.type
    var category by MoveTable.category
    var power by MoveTable.power
    var accuracy by MoveTable.accuracy
    var pp by MoveTable.pp
    var description by MoveTable.description
}