package com.pokebase.damage

import com.pokebase.auth.services.UserRepository
import com.pokebase.damage.dto.DamageDTO
import com.pokebase.damage.services.DamageService
import com.pokebase.database.entities.User
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.damageRoute() {
    val userRepo by inject<UserRepository>()
    val damageService by inject<DamageService>()

    fun getUsername(call: ApplicationCall) : User? {
        val username = call.principal<JWTPrincipal>()!!
            .payload.getClaim("username").asString()

        return userRepo.findUserByUsername(username)
    }

    authenticate("auth-jwt") {
        route("/damage") {
            post {
                val user = getUsername(call)
                    ?: return@post call.respond(
                        HttpStatusCode.Forbidden,
                        "Invalid authentication")

                val damageDTO = call.receive<DamageDTO>()

                val damage = try {
                    damageService.calculateDamage(user.id.value, damageDTO)
                } catch (ex: IllegalArgumentException) {
                    return@post call.respond(HttpStatusCode.BadRequest,
                        ex.message!!)
                }

                call.respond(hashMapOf("damage" to damage))
            }
        }
    }
}
