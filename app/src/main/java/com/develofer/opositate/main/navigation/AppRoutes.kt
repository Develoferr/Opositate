package com.develofer.opositate.main.navigation

object AppRoutes {
    enum class Destination (val route: String) {
        LOGIN("login"),
        REGISTER("register"),
        PROFILE("profile"),
        TEST("test"),
        LESSON("lesson"),
        CALENDAR("calendar")
    }
}
