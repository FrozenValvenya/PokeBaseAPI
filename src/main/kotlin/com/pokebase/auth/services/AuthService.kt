package com.pokebase.auth.services

import com.pokebase.auth.dto.UserCredentials
import com.pokebase.auth.dto.UserJWT
import com.pokebase.model.RegisterUserModel

interface AuthService {
    fun registerUser(newUser: RegisterUserModel) : Int
    fun checkUser(user: UserCredentials) : Int
}