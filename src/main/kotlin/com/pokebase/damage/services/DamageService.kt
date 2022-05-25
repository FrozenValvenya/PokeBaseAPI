package com.pokebase.damage.services

import com.pokebase.damage.dto.DamageDTO

interface DamageService {
    fun calculateDamage(userId: Int, damageDTO: DamageDTO): Int
}