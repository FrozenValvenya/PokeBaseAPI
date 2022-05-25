package com.pokebase.move.services.implementations

import com.pokebase.database.entities.Move
import com.pokebase.move.dto.MoveAdd
import com.pokebase.move.services.MoveRepository
import org.jetbrains.exposed.sql.transactions.transaction

class MoveRepositoryImpl : MoveRepository {
    override fun create(move: MoveAdd): Int {
        return transaction {
            Move.new {
                name = move.name
                type = move.type
                category = move.category
                power = move.power
                accuracy = move.accuracy
                pp = move.pp
                description = move.description
            }.id.value
        }
    }
}