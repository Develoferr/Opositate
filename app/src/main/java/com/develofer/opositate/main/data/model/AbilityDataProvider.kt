package com.develofer.opositate.main.data.model

import com.develofer.opositate.R

class AbilityDataProvider {
    fun getAbilityName(abilityId: Int): Int {
        return when (abilityId) {
            Abilities.LOGICAL_REASONING.id -> R.string.ability_name__logical_reasoning
            Abilities.VERBAL_REASONING.id -> R.string.ability_name__verbal_reasoning
            Abilities.NUMERICAL_REASONING.id -> R.string.ability_name__numerical_reasoning
            Abilities.SPATIAL_PERCEPTION.id -> R.string.ability_name__spatial_perception
            Abilities.MEMORY.id -> R.string.ability_name__memory
            Abilities.ATTENTION_AND_CONCENTRATION.id -> R.string.ability_name__attention_and_concentration
            Abilities.PROCESSING_SPEED.id -> R.string.ability_name__processing_speed
            Abilities.ABSTRACT_REASONING.id -> R.string.ability_name__abstract_reasoning
            Abilities.STRESS_RESILIENCE.id -> R.string.ability_name__stress_resilience
            Abilities.VISUAL_MOTOR_SKILLS.id -> R.string.ability_name__visual_motor_skills
            Abilities.PLANNING_AND_ORGANIZATION_SKILLS.id -> R.string.ability_name__planning_and_organization_skills
            else -> 0
        }
    }

    fun getAbilityIdList(): List<Int> {
        return Abilities.entries.map { it.id }
    }

    private enum class Abilities(val id: Int) {
        LOGICAL_REASONING(0),
        VERBAL_REASONING(1),
        NUMERICAL_REASONING(2),
        SPATIAL_PERCEPTION(3),
        MEMORY(4),
        ATTENTION_AND_CONCENTRATION(5),
        PROCESSING_SPEED(6),
        ABSTRACT_REASONING(7),
        STRESS_RESILIENCE(8),
        VISUAL_MOTOR_SKILLS(9),
        PLANNING_AND_ORGANIZATION_SKILLS(10)
    }
}