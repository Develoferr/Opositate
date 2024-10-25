package com.develofer.opositate.main.navigation

object AppRoutes {
    enum class Destination (val route: String) {
        LOGIN("login"),
        REGISTER("register"),
        HOME("home"),
        TEST("test"),
        LESSON("lesson"),
        CALENDAR("calendar")
    }
}
