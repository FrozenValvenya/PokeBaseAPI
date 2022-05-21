package com.pokebase

import com.pokebase.auth.authRoute
import com.pokebase.database.entities.UserTable
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
        SchemaUtils.createMissingTablesAndColumns(UserTable)
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
    }
}