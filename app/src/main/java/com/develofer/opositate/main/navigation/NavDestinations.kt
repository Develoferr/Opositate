package com.develofer.opositate.main.navigation

import com.develofer.opositate.feature.profile.PsTest
import kotlinx.serialization.Serializable

interface Route {
    val route: String?
}

@Serializable
object Login: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object Register: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object Profile: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object Test: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object Lesson: Route {
    override val route = this::class.qualifiedName
}

@Serializable
object Calendar: Route {
    override val route = this::class.qualifiedName
}

@Serializable
data class TestSolving(
    val psTest: PsTest
) {
    companion object: Route {
        override val route = this::class.qualifiedName
    }
}