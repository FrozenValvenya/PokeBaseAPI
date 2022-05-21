package com.pokebase.auth.services.implementations

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.pokebase.auth.dto.UserCredentials
import com.pokebase.auth.dto.UserJWT
import com.pokebase.auth.services.AuthService
import com.pokebase.auth.services.UserRepository
import com.pokebase.model.RegisterUserModel
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneOffset
import java.util.*


class AuthServiceImpl (private val userRepo: UserRepository) : AuthService {
    override fun registerUser(newUser: RegisterUserModel) = userRepo.create(newUser)
    override fun checkUser(user: UserCredentials) : Int {
        val userEntity =
            userRepo.findUserByUsername(user.username)
                ?: throw IllegalArgumentException("Username or password error")
        if (!BCrypt.checkpw(user.password, userEntity.password)) {
            throw IllegalArgumentException("Username or password error")
        }
        return userEntity.id.value
    }
}