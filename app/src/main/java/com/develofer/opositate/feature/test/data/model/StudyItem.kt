package com.develofer.opositate.feature.test.data.model

interface StudyItem {
    val number: Int
    val title: String
    val isEnabled: Boolean
}

data class LessonItem(
    override val number: Int,
    override val title: String,
    override val isEnabled: Boolean
) : StudyItem

data class TestByTaskItem(
    override val number: Int,
    override val title: String,
    override val isEnabled: Boolean,
    val testId: String,
    val abilityId: Int,
    val taskId: Int
) : StudyItem