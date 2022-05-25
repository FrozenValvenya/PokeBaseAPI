package com.pokebase.pokemon

import com.pokebase.auth.services.UserRepository
import com.pokebase.database.entities.Pokemon
import com.pokebase.database.entities.Species
import com.pokebase.database.entities.User
import com.pokebase.model.Stats
import com.pokebase.move.toMoveResponse
import com.pokebase.pokemon.dto.PokemonAdd
import com.pokebase.pokemon.dto.PokemonMove
import com.pokebase.pokemon.dto.PokemonResponse
import com.pokebase.pokemon.dto.PokemonShort
import com.pokebase.pokemon.services.PokemonRepository
import com.pokebase.species.toSpeciesResponse
import com.pokebase.species.toSpeciesShort
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import kotlin.math.floor

fun Pokemon.toPokemonShort() : PokemonShort = PokemonShort(
    pokemonId = id.value,
    nickname = nickname,
    species = species.toSpeciesShort(),
    level = level
)

fun Species.getBaseStats() = Stats(
    hp, atk, def, spa, spd, spe
)

fun Pokemon.getIV() = Stats(
    ivHp, ivAtk, ivDef, ivSpa, ivSpd, ivSpe
)

fun Pokemon.getEV() = Stats(
    evHp, evAtk, evDef, evSpa, evSpd, evSpe
)

fun Pokemon.toPokemonResponse() : PokemonResponse = PokemonResponse(
    pokemonId = id.value,
    nickname = nickname,
    species = species.toSpeciesResponse(),
    level = level,
    stats = calculateStats(species.getBaseStats(),
                        getIV(), getEV(), level),
    moves = moves.map { m -> m.toMoveResponse() }
)

fun calculateStat(base: Int, iv: Int, ev: Int, level: Int) =
    (base * 2 + iv + ev/4)*level/100 + 5

fun calculateStats(baseStats: Stats, iv: Stats, ev: Stats, level: Int) = Stats(
    hp = (baseStats.hp * 2 + iv.hp + ev.hp/4)*level/100 + level + 10,
    atk = calculateStat(baseStats.atk, iv.atk, ev.atk, level),
    def = calculateStat(baseStats.def, iv.def, ev.def, level),
    spa = calculateStat(baseStats.spa, iv.spa, ev.spa, level),
    spd = calculateStat(baseStats.spd, iv.spd, ev.spd, level),
    spe = calculateStat(baseStats.spe, iv.spe, ev.spe, level)
)

fun Route.pokemonRoute() {
    val userRepo by inject<UserRepository>()
    val pokemonRepo by inject<PokemonRepository>()

    fun getUsername(call: ApplicationCall) : User? {
        val username = call.principal<JWTPrincipal>()!!
            .payload.getClaim("username").asString()

        return userRepo.findUserByUsername(username)
    }

    authenticate("auth-jwt") {
        route("/pokemon") {

            get {
                //val username = call.principal<JWTPrincipal>()!!
                //    .payload.getClaim("username").asString()

                val user = getUsername(call)
                    ?: return@get call.respond(HttpStatusCode.Forbidden,
                                "Invalid authentication")

                val pokemonList = pokemonRepo.readAll(user.id.value)

                call.respond(pokemonList)
            }

            get("/{pokemonId}") {
                //val username = call.principal<JWTPrincipal>()!!
                //    .payload.getClaim("username").asString()

                val user = getUsername(call)
                    ?: return@get call.respond(HttpStatusCode.Forbidden,
                        "Invalid authentication")

                val pokemonId = try {
                    call.parameters["pokemonId"]!!.toInt()
                } catch (ex: NumberFormatException) {
                    return@get call.respond(HttpStatusCode.BadRequest,
                        "Id must be a number")
                }

                val pokemon = pokemonRepo.read(user.id.value, pokemonId)
                    ?: return@get call.respond(HttpStatusCode.BadRequest,
                        "Id doesn't exist")

                call.respond(pokemon)
            }



            delete("/{pokemonId}") {
                //val username = call.principal<JWTPrincipal>()!!
                //    .payload.getClaim("username").asString()

                val user = getUsername(call)
                    ?: return@delete call.respond(HttpStatusCode.Forbidden,
                        "Invalid authentication")

                val pokemonId = try {
                    call.parameters["pokemonId"]!!.toInt()
                } catch (ex: NumberFormatException) {
                    return@delete call.respond(HttpStatusCode.BadRequest,
                        "Id must be a number")
                }

                pokemonRepo.delete(user.id.value, pokemonId)

                call.respond("OK")
            }

            put("/move") {
                val user = getUsername(call)
                    ?: return@put call.respond(HttpStatusCode.Forbidden,
                        "Invalid authentication")

                val pokemonMove = call.receive<PokemonMove>()

                try {
                    pokemonRepo.addMove(
                        user.id.value,
                        pokemonMove.pokemonId,
                        pokemonMove.moveId
                    )
                } catch (ex: IllegalArgumentException) {
                    return@put call.respond(HttpStatusCode.BadRequest,
                        ex.message!!)
                }

                call.respond("OK")
            }

            put {
                val user = getUsername(call)
                    ?: return@put call.respond(HttpStatusCode.Forbidden,
                        "Invalid authentication")

                val pokemonAdd = call.receive<PokemonAdd>()
                val pokemonId = try {
                    pokemonRepo.create(user.id.value, pokemonAdd)
                } catch (ex: IllegalArgumentException) {
                    return@put call.respond(HttpStatusCode.BadRequest,
                    ex.message!!)
                }

                call.respond(hashMapOf("pokemonId" to pokemonId))
            }

            delete("/move") {
                val user = getUsername(call)
                    ?: return@delete call.respond(HttpStatusCode.Forbidden,
                        "Invalid authentication")

                val pokemonMove = call.receive<PokemonMove>()

                try {
                    pokemonRepo.deleteMove(
                        user.id.value,
                        pokemonMove.pokemonId,
                        pokemonMove.moveId
                    )
                } catch (ex: IllegalArgumentException) {
                    return@delete call.respond(HttpStatusCode.BadRequest,
                        ex.message!!)
                }

                call.respond("OK")
            }
        }
    }
}