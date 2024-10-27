package com.develofer.opositate.main.custom

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.develofer.opositate.R
import com.develofer.opositate.main.navigation.AppRoutes.Destination

@Composable
fun CustomBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Destination.PROFILE,
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
                        Destination.PROFILE -> Icon(painter = painterResource(id = R.drawable.ic_brain_6), contentDescription = "", modifier = Modifier.size(24.dp))
                        Destination.TEST -> Icon(painter = painterResource(id = R.drawable.ic_test), contentDescription = "", modifier = Modifier.size(24.dp))
                        Destination.LESSON -> Icon(painter = painterResource(id = R.drawable.ic_lesson), contentDescription = "", modifier = Modifier.size(24.dp))
                        Destination.CALENDAR -> Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = "", modifier = Modifier.size(24.dp))
                        Destination.LOGIN -> {}
                        Destination.REGISTER -> {}
                    }
                },
                label = { Text(destination.name) },
                selected = currentRoute == destination.route,
                onClick = {
                    if (currentRoute != destination.route) {
                        navController.navigate(destination.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
