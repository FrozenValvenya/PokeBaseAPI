package com.pokebase.pokemon.services

import com.pokebase.database.entities.Pokemon
import com.pokebase.pokemon.dto.PokemonAdd
import com.pokebase.pokemon.dto.PokemonResponse
import com.pokebase.pokemon.dto.PokemonShort

interface PokemonRepository {
    fun readAll(userId: Int): List<PokemonShort>
    fun read(userId: Int, pokemonId: Int): PokemonResponse?
    fun create(userId: Int, pokemon: PokemonAdd): Int
    fun delete(userId: Int, pokemonId: Int)
    fun addMove(userId: Int, pokemonId: Int, moveId: Int)
    fun deleteMove(userId: Int, pokemonId: Int, moveId: Int)
}