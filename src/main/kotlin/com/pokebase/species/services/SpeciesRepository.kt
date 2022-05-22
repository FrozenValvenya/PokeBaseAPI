package com.pokebase.species.services

import com.pokebase.database.entities.Species
import com.pokebase.species.dto.SpeciesAdd

interface SpeciesRepository {
    fun create(species: SpeciesAdd): Int
    fun read(speciesId: Int): Species
    fun readAll(): List<Species>
}