package com.pokebase.species.services.implementations

import com.pokebase.database.entities.Move
import com.pokebase.database.entities.Species
import com.pokebase.species.dto.SpeciesAdd
import com.pokebase.species.services.SpeciesRepository
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction

class SpeciesRepositoryImpl : SpeciesRepository {
    override fun create(species: SpeciesAdd): Int {
        val speciesId = transaction {
            val newSpecies = Species.new {
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

            newSpecies.evolutions = Species.forIds(species.evolutionIds)
            newSpecies.movePool = Move.forIds(species.movePoolIds)
            return@transaction newSpecies.id
        }.value

        return speciesId
    }

    override fun read(speciesId: Int): Species {
        TODO("Not yet implemented")
    }

    override fun readAll(): List<Species> {
        return transaction {
            Species.all().toList()
        }
    }
}