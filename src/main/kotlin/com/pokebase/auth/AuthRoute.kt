package com.pokebase.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.pokebase.AppConfig
import com.pokebase.auth.dto.UserCredentials
import com.pokebase.auth.dto.UserJWT
import com.pokebase.auth.services.AuthService
import com.pokebase.model.RegisterUserModel
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneOffset
import java.util.*

fun UserCredentials.toRegisterUser() = RegisterUserModel (
    username = username,
    password = BCrypt.hashpw(password, BCrypt.gensalt()),
    isAdmin = false
)

fun Route.authRoute() {
    val authService by inject<AuthService>()
    val appConfig by inject<AppConfig>()

    route("/auth") {
        post("/register") {
            val user = call.receive<UserCredentials>()
            val userId = try {
                authService.registerUser(user.toRegisterUser())
            } catch (ex: ExposedSQLException) {
                return@post call.respond(
                    HttpStatusCode.BadRequest,
                    "Username already registered"
                )
            }
            call.respond(hashMapOf("userId" to userId))
        }

        post("/login") {
            val user = call.receive<UserCredentials>()
            val userId =  try {
                authService.checkUser(user)
            } catch (ex: IllegalArgumentException) {
                return@post call.respond(
                    HttpStatusCode.NotFound,
                    ex.message!!
                )
            }

            val expirationDate = LocalDateTime.now().plus(Period.ofMonths(1))
            val userJWT = UserJWT (
                userId = userId,
                JWT = JWT.create()
                    .withClaim("username", user.username)
                    .withExpiresAt(
                        Date.from(expirationDate.toInstant(ZoneOffset.UTC))
                    ).sign(Algorithm.HMAC256(appConfig.jwtConfig.secret))
            )
            call.respond(userJWT)
        }
    }
}