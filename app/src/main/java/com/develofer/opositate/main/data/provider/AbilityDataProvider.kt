package com.develofer.opositate.main.data.provider

import com.develofer.opositate.R

class AbilityDataProvider {

    fun getAbilityStringResId(abilityId: Int): Int =
        Abilities.entries.find { it.abilityId == abilityId }?.resId ?: 0

    fun getTaskStringResId(abilityId: Int, abilityTaskId: Int): Int =
        Abilities.entries.find { it.abilityId == abilityId }?.tasks?.find { it.taskId == abilityTaskId }?.resId ?: 0

    fun getAbilityIdList(): List<Map<String, Any>> =
        Abilities.entries.map {
            mapOf(
                "abilityId" to it.abilityId,
                "tasks" to it.tasks.map { task ->
                    task.taskId
                }
            )
        }

}

private enum class Abilities(val abilityId: Int, val resId: Int, val tasks: List<AbilityTask>) {
    LOGICAL_REASONING(0, R.string.ability_name__logical_reasoning,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__logical_reasoning__basic_inferences),
            AbilityTask(1, R.string.sub_ability_name__logical_reasoning__numerical_relationships),
            AbilityTask(2, R.string.sub_ability_name__logical_reasoning__element_relations_and_rules),
            AbilityTask(3, R.string.sub_ability_name__logical_reasoning__divisibility_and_distributions),
            AbilityTask(4, R.string.sub_ability_name__logical_reasoning__sequence_understanding),
            AbilityTask(5, R.string.sub_ability_name__logical_reasoning__logical_patterns),
            AbilityTask(6, R.string.sub_ability_name__logical_reasoning__analysis_and_deductions),
            AbilityTask(7, R.string.sub_ability_name__logical_reasoning__classification_and_grouping)
        )
    ),
    VERBAL_REASONING(1, R.string.ability_name__verbal_reasoning,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__verbal_reasoning__reading_comprehension),
            AbilityTask(1, R.string.sub_ability_name__verbal_reasoning__verbal_analogies),
            AbilityTask(2, R.string.sub_ability_name__verbal_reasoning__word_relationships),
            AbilityTask(3, R.string.sub_ability_name__verbal_reasoning__word_puzzles),
            AbilityTask(4, R.string.sub_ability_name__verbal_reasoning__text_coherence),
            AbilityTask(5, R.string.sub_ability_name__verbal_reasoning__implicit_explicit_inferences),
            AbilityTask(6, R.string.sub_ability_name__verbal_reasoning__antonyms_synonyms_contextual_meaning),
            AbilityTask(7, R.string.sub_ability_name__verbal_reasoning__semantic_relationships)
        )
    ),
    NUMERICAL_REASONING(2, R.string.ability_name__numerical_reasoning,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__numerical_reasoning__basic_math_operations),
            AbilityTask(1, R.string.sub_ability_name__numerical_reasoning__numerical_patterns),
            AbilityTask(2, R.string.sub_ability_name__numerical_reasoning__numerical_sequences_and_progressions),
            AbilityTask(3, R.string.sub_ability_name__numerical_reasoning__percentages_proportions_fractions),
            AbilityTask(4, R.string.sub_ability_name__numerical_reasoning__basic_algebra_equations),
            AbilityTask(5, R.string.sub_ability_name__numerical_reasoning__understanding_magnitudes_and_scales),
            AbilityTask(6, R.string.sub_ability_name__numerical_reasoning__numerical_sets_relationships),
            AbilityTask(7, R.string.sub_ability_name__numerical_reasoning__estimation_and_rounding)
        )
    ),
    SPATIAL_PERCEPTION(3, R.string.ability_name__spatial_perception,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__spatial_perception__visual_and_spatial_manipulation),
            AbilityTask(1, R.string.sub_ability_name__spatial_perception__geometric_figures),
            AbilityTask(2, R.string.sub_ability_name__spatial_perception__spatial_orientation_and_rotation),
            AbilityTask(3, R.string.sub_ability_name__spatial_perception__maps_and_diagrams_analysis),
            AbilityTask(4, R.string.sub_ability_name__spatial_perception__3d_structures_patterns_recognition),
            AbilityTask(5, R.string.sub_ability_name__spatial_perception__comparisons_of_figures_in_different_planes),
            AbilityTask(6, R.string.sub_ability_name__spatial_perception__3d_visualization_from_2d),
            AbilityTask(7, R.string.sub_ability_name__spatial_perception__spatial_puzzles_resolution)
        )
    ),
    MEMORY(4, R.string.ability_name__memory,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__memory__short_term_memory),
            AbilityTask(1, R.string.sub_ability_name__memory__visual_and_auditory_memory),
            AbilityTask(2, R.string.sub_ability_name__memory__sequences_remembering),
            AbilityTask(3, R.string.sub_ability_name__memory__working_memory),
            AbilityTask(4, R.string.sub_ability_name__memory__pattern_recognition_and_memorization),
            AbilityTask(5, R.string.sub_ability_name__memory__details_recall_from_texts),
            AbilityTask(6, R.string.sub_ability_name__memory__spatial_and_visual_memory),
            AbilityTask(7, R.string.sub_ability_name__memory__recognizing_previously_presented_information)
        )
    ),
    ATTENTION_AND_CONCENTRATION(5, R.string.ability_name__attention_and_concentration,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__attention_and_concentration__long_task_attention),
            AbilityTask(1, R.string.sub_ability_name__attention_and_concentration__concentration_under_distractions),
            AbilityTask(2, R.string.sub_ability_name__attention_and_concentration__detection_of_repetitive_task_details),
            AbilityTask(3, R.string.sub_ability_name__attention_and_concentration__divided_attention),
            AbilityTask(4, R.string.sub_ability_name__attention_and_concentration__time_pressure_concentration),
            AbilityTask(5, R.string.sub_ability_name__attention_and_concentration__visual_and_auditory_vigilance),
            AbilityTask(6, R.string.sub_ability_name__attention_and_concentration__multitasking_focus),
            AbilityTask(7, R.string.sub_ability_name__attention_and_concentration__resistance_to_cognitive_fatigue)
        )
    ),
    PROCESSING_SPEED(6, R.string.ability_name__processing_speed,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__processing_speed__quick_reaction),
            AbilityTask(1, R.string.sub_ability_name__processing_speed__basic_math_speed),
            AbilityTask(2, R.string.sub_ability_name__processing_speed__response_time_identification),
            AbilityTask(3, R.string.sub_ability_name__processing_speed__text_image_processing),
            AbilityTask(4, R.string.sub_ability_name__processing_speed__quick_decision_time_limit),
            AbilityTask(5, R.string.sub_ability_name__processing_speed__symbol_number_sorting),
            AbilityTask(6, R.string.sub_ability_name__processing_speed__sequence_word_letter_speed),
            AbilityTask(7, R.string.sub_ability_name__processing_speed__subtle_difference_detection)
        )
    ),
    ABSTRACT_REASONING(7, R.string.ability_name__abstract_reasoning,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__abstract_reasoning__complex_patterns),
            AbilityTask(1, R.string.sub_ability_name__abstract_reasoning__set_logic_abstract_relations),
            AbilityTask(2, R.string.sub_ability_name__abstract_reasoning__abstract_sequence_problems),
            AbilityTask(3, R.string.sub_ability_name__abstract_reasoning__non_numeric_rule_deduction),
            AbilityTask(4, R.string.sub_ability_name__abstract_reasoning__advanced_geometric_patterns),
            AbilityTask(5, R.string.sub_ability_name__abstract_reasoning__non_concrete_reasoning),
            AbilityTask(6, R.string.sub_ability_name__abstract_reasoning__abstract_diagram_comparison),
            AbilityTask(7, R.string.sub_ability_name__abstract_reasoning__geometric_pattern_identification)
        )
    ),
    STRESS_RESILIENCE(8, R.string.ability_name__stress_resilience,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__stress_resilience__performance_under_pressure),
            AbilityTask(1, R.string.sub_ability_name__stress_resilience__quick_decisions_high_pressure),
            AbilityTask(2, R.string.sub_ability_name__stress_resilience__adaptation_unexpected_changes),
            AbilityTask(3, R.string.sub_ability_name__stress_resilience__frustration_tolerance),
            AbilityTask(4, R.string.sub_ability_name__stress_resilience__perseverance_prolonged_tasks),
            AbilityTask(5, R.string.sub_ability_name__stress_resilience__cognitive_load_emotional_pressure),
            AbilityTask(6, R.string.sub_ability_name__stress_resilience__repetitive_tasks_increasing_difficulty),
            AbilityTask(7, R.string.sub_ability_name__stress_resilience__emotional_self_regulation)
        )
    ),
    VISUAL_MOTOR_SKILLS(9, R.string.ability_name__visual_motor_skills,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__visual_motor_skills__eye_hand_coordination),
            AbilityTask(1, R.string.sub_ability_name__visual_motor_skills__assembly_precision),
            AbilityTask(2, R.string.sub_ability_name__visual_motor_skills__fine_motor_skills),
            AbilityTask(3, R.string.sub_ability_name__visual_motor_skills__visual_tracking),
            AbilityTask(4, R.string.sub_ability_name__visual_motor_skills__speed_precision_touch_visual),
            AbilityTask(5, R.string.sub_ability_name__visual_motor_skills__pattern_reproduction),
            AbilityTask(6, R.string.sub_ability_name__visual_motor_skills__quick_response_visual_touch),
            AbilityTask(7, R.string.sub_ability_name__visual_motor_skills__complex_pattern_tracing)
        )
    ),
    PLANNING_AND_ORGANIZATION_SKILLS(10, R.string.ability_name__planning_and_organization_skills,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__planning_and_organization_skills__task_order_prioritization),
            AbilityTask(1, R.string.sub_ability_name__planning_and_organization_skills__goal_setting_sequential_steps),
            AbilityTask(2, R.string.sub_ability_name__planning_and_organization_skills__strategic_problem_solving),
            AbilityTask(3, R.string.sub_ability_name__planning_and_organization_skills__goal_oriented_sequencing),
            AbilityTask(4, R.string.sub_ability_name__planning_and_organization_skills__logical_ordering),
            AbilityTask(5, R.string.sub_ability_name__planning_and_organization_skills__plan_adaptation),
            AbilityTask(6, R.string.sub_ability_name__planning_and_organization_skills__sequential_puzzle_maze),
            AbilityTask(7, R.string.sub_ability_name__planning_and_organization_skills__resource_management_simulation)
        )
    ),
    DECISION_MAKING(11, R.string.ability_name__decision_making,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__decision_making__logical_decision_uncertainty),
            AbilityTask(1, R.string.sub_ability_name__decision_making__prioritization_selection_multiple_variables),
            AbilityTask(2, R.string.sub_ability_name__decision_making__ethical_practical_judgment),
            AbilityTask(3, R.string.sub_ability_name__decision_making__conflict_interest_context),
            AbilityTask(4, R.string.sub_ability_name__decision_making__rapid_decision_scenarios),
            AbilityTask(5, R.string.sub_ability_name__decision_making__long_term_consequence_analysis),
            AbilityTask(6, R.string.sub_ability_name__decision_making__incomplete_ambiguous_information),
            AbilityTask(7, R.string.sub_ability_name__decision_making__selection_under_time_resource_pressure)
        )
    ),
    CREATIVITY(12, R.string.ability_name__creativity,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__creativity__divergent_thinking),
            AbilityTask(1, R.string.sub_ability_name__creativity__fluency_originality_ideas),
            AbilityTask(2, R.string.sub_ability_name__creativity__unusual_connections),
            AbilityTask(3, R.string.sub_ability_name__creativity__creative_problem_solving),
            AbilityTask(4, R.string.sub_ability_name__creativity__inventiveness_product_ideas),
            AbilityTask(5, R.string.sub_ability_name__creativity__modifying_traditional_approaches),
            AbilityTask(6, R.string.sub_ability_name__creativity__creative_visualization),
            AbilityTask(7, R.string.sub_ability_name__creativity__creative_writing_hypotheticals)
        )
    ),
    MECHANICAL_COMPREHENSION(13, R.string.ability_name__mechanical_comprehension,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__mechanical_comprehension__mechanical_physical_principles),
            AbilityTask(1, R.string.sub_ability_name__mechanical_comprehension__spatial_reasoning_moving_objects),
            AbilityTask(2, R.string.sub_ability_name__mechanical_comprehension__laws_of_motion),
            AbilityTask(3, R.string.sub_ability_name__mechanical_comprehension__simple_mechanisms),
            AbilityTask(4, R.string.sub_ability_name__mechanical_comprehension__identifying_physical_principles),
            AbilityTask(5, R.string.sub_ability_name__mechanical_comprehension__predicting_mechanical_outcomes),
            AbilityTask(6, R.string.sub_ability_name__mechanical_comprehension__basic_electrical_circuits),
            AbilityTask(7, R.string.sub_ability_name__mechanical_comprehension__understanding_complex_systems)
        )
    ),
    PROBLEM_SOLVING(14, R.string.ability_name__problem_solving,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__problem_solving__analyzing_multi_variable_problems),
            AbilityTask(1, R.string.sub_ability_name__problem_solving__logical_critical_solution_steps),
            AbilityTask(2, R.string.sub_ability_name__problem_solving__resource_limitations),
            AbilityTask(3, R.string.sub_ability_name__problem_solving__alternative_solutions_complexity),
            AbilityTask(4, R.string.sub_ability_name__problem_solving__nonlinear_multi_step_problems),
            AbilityTask(5, R.string.sub_ability_name__problem_solving__reorganizing_information_patterns),
            AbilityTask(6, R.string.sub_ability_name__problem_solving__hypothetical_scenarios),
            AbilityTask(7, R.string.sub_ability_name__problem_solving__solving_changing_conditions)
        )
    ),
    EMOTIONAL_INTELLIGENCE(15, R.string.ability_name__emotional_intelligence,
        listOf(
            AbilityTask(0, R.string.sub_ability_name__emotional_intelligence__self_emotion_management),
            AbilityTask(1, R.string.sub_ability_name__emotional_intelligence__empathy_understanding_interactions),
            AbilityTask(2, R.string.sub_ability_name__emotional_intelligence__emotional_regulation_resilience),
            AbilityTask(3, R.string.sub_ability_name__emotional_intelligence__emotion_identification_faces),
            AbilityTask(4, R.string.sub_ability_name__emotional_intelligence__conflict_resolution_interpersonal),
            AbilityTask(5, R.string.sub_ability_name__emotional_intelligence__active_listening_emotional_response),
            AbilityTask(6, R.string.sub_ability_name__emotional_intelligence__emotional_regulation_social_simulations),
            AbilityTask(7, R.string.sub_ability_name__emotional_intelligence__self_control_under_stress)
        )
    )
}

data class AbilityTask(val taskId: Int, val resId: Int)