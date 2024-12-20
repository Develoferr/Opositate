package com.develofer.opositate.main.navigation

import kotlinx.serialization.Serializable

interface Route {
    val route: String?
}

@Serializable
object LoginNavigation: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object RegisterNavigation: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object ProfileNavigation: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object TestNavigation: Route {
    override val route = this::class.qualifiedName
}

@Serializable
data class TestSolvingNavigation(
    val testTypeId: Int,
    val difficultId: Int?,
    val groupId: Int?,
    val abilityId: Int?,
    val taskId: Int?
) {
    companion object: Route {
        override val route = this::class.qualifiedName
    }
}

@Serializable
data class TestResultNavigation(
    val testTypeId: Int,
    val difficultId: Int?,
    val groupId: Int?,
    val abilityId: Int?,
    val taskId: Int?,
    val testResultId: Int
) {
    companion object: Route {
        override val route = this::class.qualifiedName
    }

}

@Serializable
object LessonNavigation: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object CalendarNavigation: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object SettingsNavigation: Route {
    override val route = this::class.qualifiedName
}