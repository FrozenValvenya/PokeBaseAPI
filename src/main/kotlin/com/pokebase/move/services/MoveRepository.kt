package com.pokebase.move.services

import com.pokebase.move.dto.MoveAdd

interface MoveRepository {
    fun create(move: MoveAdd): Int
}