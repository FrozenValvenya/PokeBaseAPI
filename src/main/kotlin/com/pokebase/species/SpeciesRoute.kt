package com.pokebase.species

import com.pokebase.database.entities.Species
import com.pokebase.model.Stats
import com.pokebase.move.toMoveResponse
import com.pokebase.species.dto.SpeciesAdd
import com.pokebase.species.dto.SpeciesResponse
import com.pokebase.species.dto.SpeciesShort
import com.pokebase.species.services.SpeciesRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Species.toSpeciesResponse() : SpeciesResponse = SpeciesResponse(
    speciesId = id.value,
    name = name,
    primaryType = primaryType,
    secondaryType = secondaryType,
    evolutions = evolutions.map { s -> s.toSpeciesResponse() },
    weight = weight,
    height = height,
    baseStats = Stats(
        hp, atk, def, spa, spd, spe
    ),
    movePool = movePool.map {m -> m.toMoveResponse()},
    image = image
)

fun Species.toSpeciesShort() : SpeciesShort = SpeciesShort(
    speciesId = id.value,
    name = name,
    primaryType = primaryType,
    secondaryType = secondaryType,
    image = image
)

fun Route.speciesRoute() {
    val speciesRepo by inject<SpeciesRepository>()

    authenticate("auth-jwt") {
        route("/species") {
            get {
                val speciesList = speciesRepo.readAll()

                call.respond(speciesList.map { s -> s.toSpeciesShort() })
            }

            put {
                val speciesAdd = call.receive<SpeciesAdd>()
                val speciesId = speciesRepo.create(speciesAdd)
                call.respond(hashMapOf("speciesId" to speciesId))
            }

            get("/{speciesId}/moves") {
                val speciesId = try {
                    call.parameters["speciesId"]!!.toInt()
                } catch (ex: NumberFormatException) {
                    return@get call.respond(
                        HttpStatusCode.BadRequest,
                        "Id must be a number")
                }
                val moveList = speciesRepo.getMoves(speciesId)

                call.respond(moveList.map { m -> m.toMoveResponse() })
            }

            get("/{speciesId}") {
                val speciesId = try {
                    call.parameters["speciesId"]!!.toInt()
                } catch (ex: NumberFormatException) {
                    return@get call.respond(
                        HttpStatusCode.BadRequest,
                        "Id must be a number")
                }

                val species = speciesRepo.read(speciesId)
                    ?: return@get call.respond(HttpStatusCode.BadRequest,
                        "Id doesn't exist")

                call.respond(species.toSpeciesResponse())
            }
        }
    }
}