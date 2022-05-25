package com.pokebase.pokemon.services.implementations

import com.pokebase.database.entities.Move
import com.pokebase.database.entities.Pokemon
import com.pokebase.database.entities.PokemonTable
import com.pokebase.database.entities.Species
import com.pokebase.pokemon.dto.PokemonAdd
import com.pokebase.pokemon.services.PokemonRepository
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class PokemonRepositoryImpl: PokemonRepository {
    override fun readAll(userId: Int): List<Pokemon> {
        return transaction {
            Pokemon.find{ PokemonTable.user eq userId }
                .toList()
        }
    }

    override fun read(userId: Int, pokemonId: Int): Pokemon? {
        return transaction {
            Pokemon.find {
                (PokemonTable.user eq userId) and
                (PokemonTable.id eq pokemonId)
            }.firstOrNull()
        }
    }

    override fun create(userId: Int, pokemon: PokemonAdd): Int {
        return transaction {
            Pokemon.new {
                nickname = pokemon.nickname
                level = pokemon.level
                species = Species.findById(pokemon.speciesId)
                    ?: throw IllegalArgumentException("Species id doesn't exist")
            }.id.value
        }
    }

    override fun delete(userId: Int, pokemonId: Int) {
        transaction {
            val pokemon = Pokemon.find {
                (PokemonTable.user eq userId) and
                        (PokemonTable.id eq pokemonId)
            }.firstOrNull()
                ?: throw IllegalArgumentException("Pokemon id doesn't exist")

            pokemon.delete()
        }
    }

    override fun addMove(userId: Int, pokemonId: Int, moveId: Int) {
        transaction {
            val pokemon = Pokemon.find {
                (PokemonTable.user eq userId) and
                        (PokemonTable.id eq pokemonId)
            }.firstOrNull()
                ?: throw IllegalArgumentException("Pokemon id doesn't exist")

            val newMove = Move.findById(moveId)
                ?: throw  IllegalArgumentException("Move id doesn't exist")

            pokemon.moves = SizedCollection(pokemon.moves.plusElement(newMove))
        }
    }

    override fun deleteMove(userId: Int, pokemonId: Int, moveId: Int) {
        transaction {
            val pokemon = Pokemon.find {
                (PokemonTable.user eq userId) and
                        (PokemonTable.id eq pokemonId)
            }.firstOrNull()
                ?: throw IllegalArgumentException("Pokemon id doesn't exist")

            val oldMove = Move.findById(moveId)
                ?: throw  IllegalArgumentException("Move id doesn't exist")

            pokemon.moves = SizedCollection(pokemon.moves.minusElement(oldMove))
        }
    }

}