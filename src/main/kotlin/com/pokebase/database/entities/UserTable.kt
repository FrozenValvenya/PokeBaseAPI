package com.pokebase.database.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object UserTable : IntIdTable("user", "user_id") {
    val username: Column<String> = varchar("username", 50).uniqueIndex()
    val password: Column<String> = varchar("password", 100)
    val isAdmin: Column<Boolean> = bool("is_admin")
}

class User (id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(UserTable)
    var username by UserTable.username
    var password by UserTable.password
    var isAdmin by UserTable.isAdmin
}