package com.pokebase.move

import com.pokebase.database.entities.Move
import com.pokebase.move.dto.MoveAdd
import com.pokebase.move.dto.MoveResponse
import com.pokebase.move.services.MoveRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Move.toMoveResponse() = MoveResponse(
    moveId = id.value,
    name = name,
    type = type,
    category = category,
    power = power,
    accuracy = accuracy,
    pp = pp,
    description = description
)

fun Route.moveRoute() {

    val moveRepo by inject<MoveRepository>()

    authenticate("auth-jwt") {
        route("/move") {
            put {
                val moveAdd = call.receive<MoveAdd>()

                val moveId = moveRepo.create(moveAdd)

                call.respond(hashMapOf("moveId" to moveId))
            }
        }
    }
}