package com.pokebase.auth.services.implementations

import com.pokebase.auth.services.UserRepository
import com.pokebase.database.entities.User
import com.pokebase.database.entities.UserTable
import com.pokebase.model.RegisterUserModel
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {

    @kotlin.jvm.Throws(ExposedSQLException::class)
    override fun create(newUser: RegisterUserModel): Int {
       /* return transaction {
            UserTable.insert {
                it[username] = newUser.username
                it[password] = newUser.password
                it[isAdmin] = newUser.isAdmin
            } get UserTable.id
        }.value
        */
        return transaction {
            User.new {
                username = newUser.username
                password = newUser.password
                isAdmin = newUser.isAdmin
            }.id
        }.value
    }

    override fun read(userId: Int):  User? {
        return transaction {
            User.findById(userId)
        }
    }

    override fun findUserByUsername(username: String): User? {
        return transaction {
            User.find { UserTable.username eq username }
                .firstOrNull()
        }
    }

    override fun update() {
        TODO("Not yet implemented")
    }

}