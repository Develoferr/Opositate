package com.develofer.opositate.test

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

data class TestItem(
    override val number: Int,
    override val title: String,
    override val isEnabled: Boolean
) : StudyItem