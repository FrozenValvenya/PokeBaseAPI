package com.pokebase.pokemon.services

import com.pokebase.database.entities.Pokemon
import com.pokebase.pokemon.dto.PokemonAdd

interface PokemonRepository {
    fun readAll(userId: Int): List<Pokemon>
    fun read(userId: Int, pokemonId: Int): Pokemon?
    fun create(userId: Int, pokemon: PokemonAdd): Int
    fun delete(userId: Int, pokemonId: Int)
    fun addMove(userId: Int, pokemonId: Int, moveId: Int)
    fun deleteMove(userId: Int, pokemonId: Int, moveId: Int)
}