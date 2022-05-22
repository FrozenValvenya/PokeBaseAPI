package com.pokebase

import com.pokebase.auth.services.AuthService
import com.pokebase.auth.services.UserRepository
import com.pokebase.auth.services.implementations.AuthServiceImpl
import com.pokebase.auth.services.implementations.UserRepositoryImpl
import com.pokebase.species.services.SpeciesRepository
import com.pokebase.species.services.implementations.SpeciesRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single { AppConfig() }
    single<AuthService> { AuthServiceImpl(get())}
    single<UserRepository> { UserRepositoryImpl() }
    single<SpeciesRepository> { SpeciesRepositoryImpl() }
}