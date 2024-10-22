package com.develofer.opositate.presentation.home


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.develofer.opositate.presentation.navigation.AppRoutes.Destination

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Destination.HOME,
        Destination.TEST,
        Destination.LESSON,
        Destination.CALENDAR
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { destination ->
            NavigationBarItem(
                icon = {
                    when (destination) {
                        Destination.HOME -> Icon(Icons.Default.Home, contentDescription = null)
                        Destination.TEST -> Icon(Icons.Default.Assessment, contentDescription = null)
                        Destination.LESSON -> Icon(Icons.Default.School, contentDescription = null)
                        Destination.CALENDAR -> Icon(Icons.Default.CalendarToday, contentDescription = null)
                        else -> null
                    }
                },
                label = { Text(destination.name) },
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
