package com.pokebase.auth.services

import com.pokebase.database.entities.User
import com.pokebase.model.RegisterUserModel


interface UserRepository {
    fun create(newUser : RegisterUserModel): Int
    fun read(userId : Int): User?
    fun findUserByUsername(username: String): User?
    fun update()
}