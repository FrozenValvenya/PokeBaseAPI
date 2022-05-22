package com.pokebase.move

import com.pokebase.database.entities.Move
import com.pokebase.move.dto.MoveResponse

fun Move.toMoveResponse() = MoveResponse(
    moveId = id.value,
    name = name,
    type = type,
    category = category,
    power = power,
    accuracy = accuracy,
    pp = pp,
    description = description
)