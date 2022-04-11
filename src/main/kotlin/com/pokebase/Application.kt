package com.pokebase

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    install(Koin) {
        SLF4JLogger()
    }

    routing {
        get("/test") {
            call.respondText("Hello, world!")
        }
    }
}