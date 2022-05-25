package com.pokebase.damage.services.implementations

import com.pokebase.damage.dto.DamageDTO
import com.pokebase.damage.services.DamageService
import com.pokebase.database.entities.MoveTable
import com.pokebase.database.entities.Pokemon
import com.pokebase.database.entities.PokemonTable
import com.pokebase.model.Category
import com.pokebase.model.Stats
import com.pokebase.pokemon.getBaseStats
import com.pokebase.pokemon.getEV
import com.pokebase.pokemon.getIV
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

fun calculateStat(base: Int, iv: Int, ev: Int, level: Int) =
    (base * 2 + iv + ev/4)*level/100 + 5

fun calculateStats(baseStats: Stats, iv: Stats, ev: Stats, level: Int) = Stats(
    hp = (baseStats.hp * 2 + iv.hp + ev.hp/4)*level/100 + level + 10,
    atk = com.pokebase.pokemon.calculateStat(baseStats.atk, iv.atk, ev.atk, level),
    def = com.pokebase.pokemon.calculateStat(baseStats.def, iv.def, ev.def, level),
    spa = com.pokebase.pokemon.calculateStat(baseStats.spa, iv.spa, ev.spa, level),
    spd = com.pokebase.pokemon.calculateStat(baseStats.spd, iv.spd, ev.spd, level),
    spe = com.pokebase.pokemon.calculateStat(baseStats.spe, iv.spe, ev.spe, level)
)

class DamageServiceImpl: DamageService {
    override fun calculateDamage(userId: Int, damageDTO: DamageDTO): Int {
        val attacker = transaction {
            Pokemon.find {
                (PokemonTable.user eq userId) and
                        (PokemonTable.id eq damageDTO.attackerId)
            }.firstOrNull()
                ?: throw IllegalArgumentException("Pokemon id doesn't exist")

        }

        val defender = transaction {
            Pokemon.find {
                (PokemonTable.user eq userId) and
                        (PokemonTable.id eq damageDTO.defenderId)
            }.firstOrNull()
                ?: throw IllegalArgumentException("Pokemon id doesn't exist")

        }

        val move = transaction {
            attacker.moves.find {
                m -> m.id.value == damageDTO.moveId
            } ?: throw IllegalArgumentException("Illegal move id")
        }

        if (move.category == Category.Status) {
            throw IllegalArgumentException("Move can't be status")
        }

        val attackerStats = calculateStats(
            attacker.species.getBaseStats(),
            attacker.getIV(),
            attacker.getEV(),
            attacker.level
        )

        val defenderStats = calculateStats(
            defender.species.getBaseStats(),
            defender.getIV(),
            defender.getEV(),
            defender.level
        )

        val stab = if (move.type == attacker.species.primaryType
            || move.type == attacker.species.secondaryType)
            1.5 else 1

        val result = (((2 * attacker.level)/5 + 2)
                * move.power!!
                * attackerStats.atk/defenderStats.def/50
                + 2)

        return result
    }
}