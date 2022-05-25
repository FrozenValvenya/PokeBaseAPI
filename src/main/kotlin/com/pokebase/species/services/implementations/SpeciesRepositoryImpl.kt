package com.pokebase.species.services.implementations

import com.pokebase.database.entities.Move
import com.pokebase.database.entities.MoveSpeciesTable
import com.pokebase.database.entities.Species
import com.pokebase.database.entities.SpeciesToSpeciesTable
import com.pokebase.species.dto.SpeciesAdd
import com.pokebase.species.dto.SpeciesResponse
import com.pokebase.species.services.SpeciesRepository
import com.pokebase.species.toSpeciesResponse
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class SpeciesRepositoryImpl : SpeciesRepository {
    override fun create(species: SpeciesAdd): Int {
        val newSpecies = transaction {
            Species.new {
                name = species.name
                primaryType = species.primaryType
                secondaryType = species.secondaryType
                weight = species.weight
                height = species.height
                hp = species.baseStats.hp
                atk = species.baseStats.atk
                def = species.baseStats.def
                spa = species.baseStats.spa
                spd = species.baseStats.spd
                spe = species.baseStats.spe
                image = species.image
            }
        }

        transaction {
            newSpecies.evolutions = Species.forIds(species.evolutionIds)
            newSpecies.movePool = Move.forIds(species.movePoolIds)
        }

        return newSpecies.id.value
    }

    override fun read(speciesId: Int): SpeciesResponse? {
        return transaction {
            val species = Species.findById(speciesId)
            /*
            val evolutions = SpeciesToSpeciesTable.select {
                SpeciesToSpeciesTable.preEvolution eq speciesId
            }.map {
                it[SpeciesToSpeciesTable.evolution]
            }

            species?.evolutions = Species.forEntityIds(evolutions)

            val moves = MoveSpeciesTable.select {
                MoveSpeciesTable.species eq  speciesId
            }.map {
                it[MoveSpeciesTable.move]
            }

            species?.movePool = Move.forEntityIds(moves)
            */
            return@transaction species?.toSpeciesResponse()
        }
    }

    override fun readAll(): List<Species> {
        return transaction {
            Species.all().toList()
        }
    }

    override fun getMoves(speciesId: Int): List<Move> {
        return transaction {
            val species = Species.findById(speciesId)
                ?: throw IllegalArgumentException("Species id doesn't exist")

            species.movePool.toList()
        }
    }
}