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
object LessonNavigation: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object CalendarNavigation: Route {
    override val route = this::class.qualifiedName
}

@Serializable
data class TestSolvingNavigation(
    val testId: String
) {
    companion object: Route {
        override val route = this::class.qualifiedName
    }
}

@Serializable
data class TestResultNavigation(
    val testResultId: String
) {
    companion object: Route {
        override val route = this::class.qualifiedName
    }
}