package com.develofer.opositate.main.data.model

import android.content.Context
import androidx.annotation.StringRes
import com.develofer.opositate.R
import com.develofer.opositate.utils.StringConstants.EMPTY_STRING
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AbilityDataProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getAbilityName(abilityId: Int): String {
        return when (abilityId) {
            Abilities.LOGICAL_REASONING.id -> context.getString(Abilities.LOGICAL_REASONING.abilityNameResId)
            Abilities.VERBAL_REASONING.id -> context.getString(Abilities.VERBAL_REASONING.abilityNameResId)
            Abilities.NUMERICAL_REASONING.id -> context.getString(Abilities.NUMERICAL_REASONING.abilityNameResId)
            Abilities.SPATIAL_PERCEPTION.id -> context.getString(Abilities.SPATIAL_PERCEPTION.abilityNameResId)
            Abilities.MEMORY.id -> context.getString(Abilities.MEMORY.abilityNameResId)
            Abilities.ATTENTION_AND_CONCENTRATION.id -> context.getString(Abilities.ATTENTION_AND_CONCENTRATION.abilityNameResId)
            Abilities.PROCESSING_SPEED.id -> context.getString(Abilities.PROCESSING_SPEED.abilityNameResId)
            Abilities.ABSTRACT_REASONING.id -> context.getString(Abilities.ABSTRACT_REASONING.abilityNameResId)
            Abilities.STRESS_RESILIENCE.id -> context.getString(Abilities.STRESS_RESILIENCE.abilityNameResId)
            Abilities.VISUAL_MOTOR_SKILLS.id -> context.getString(Abilities.VISUAL_MOTOR_SKILLS.abilityNameResId)
            Abilities.PLANNING_AND_ORGANIZATION_SKILLS.id -> context.getString(Abilities.PLANNING_AND_ORGANIZATION_SKILLS.abilityNameResId)
            else -> EMPTY_STRING
        }
    }

    fun getAbilityIdList(): List<Int> {
        return Abilities.entries.map { it.id }
    }

    private enum class Abilities(@StringRes val abilityNameResId: Int, val id: Int) {
        LOGICAL_REASONING(abilityNameResId = R.string.ability_name__logical_reasoning, 0),
        VERBAL_REASONING(abilityNameResId = R.string.ability_name__verbal_reasoning, 1),
        NUMERICAL_REASONING(abilityNameResId = R.string.ability_name__numerical_reasoning, 2),
        SPATIAL_PERCEPTION(abilityNameResId = R.string.ability_name__spatial_perception, 3),
        MEMORY(abilityNameResId = R.string.ability_name__memory, 4),
        ATTENTION_AND_CONCENTRATION(abilityNameResId = R.string.ability_name__attention_and_concentration, 5),
        PROCESSING_SPEED(abilityNameResId = R.string.ability_name__processing_speed, 6),
        ABSTRACT_REASONING(abilityNameResId = R.string.ability_name__abstract_reasoning, 7),
        STRESS_RESILIENCE(abilityNameResId = R.string.ability_name__stress_resilience, 8),
        VISUAL_MOTOR_SKILLS(abilityNameResId = R.string.ability_name__visual_motor_skills, 9),
        PLANNING_AND_ORGANIZATION_SKILLS(abilityNameResId = R.string.ability_name__planning_and_organization_skills, 10)
    }
}