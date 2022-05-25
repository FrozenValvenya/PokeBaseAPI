package com.pokebase

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.pokebase.auth.authRoute
import com.pokebase.damage.damageRoute
import com.pokebase.database.entities.*
import com.pokebase.model.Category
import com.pokebase.model.Type
import com.pokebase.pokemon.pokemonRoute
import com.pokebase.species.speciesRoute
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.ktor.ext.modules
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.initDB() {
    val appConfig by inject<AppConfig>()

    val config = HikariConfig()
    config.jdbcUrl = appConfig.databaseConfig.jdbcUrl
    config.username = appConfig.databaseConfig.username
    config.password = appConfig.databaseConfig.password
    config.driverClassName = appConfig.databaseConfig.driver

    Database.connect(HikariDataSource(config))
    transaction {
        SchemaUtils.createMissingTablesAndColumns(UserTable,
            MoveTable,
            SpeciesTable,
            PokemonTable,
            MovePokemonTable,
            MoveSpeciesTable,
            SpeciesToSpeciesTable
        )
    }
}
fun Application.module() {

    install(Koin) {
        SLF4JLogger()
        modules(appModule)
    }

    initConfig()
    initDB()

    val appConfig by inject<AppConfig>()
    install(Authentication) {
        jwt("auth-jwt") {
            realm = appConfig.jwtConfig.realm
            verifier(JWT.require(Algorithm.HMAC256(appConfig.jwtConfig.secret))
                .build())
            validate {
                credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                }
                else {
                    null
                }
            }
        }
    }


    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/test") {
            call.respondText("Hello, world!")
        }
        authRoute()
        speciesRoute()
        pokemonRoute()
        damageRoute()
    }
}