package com.develofer.opositate.main.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.develofer.opositate.R
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
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
                        Destination.PROFILE -> GradientIcon(
                            painter = painterResource(id = R.drawable.ic_brain_6),
                            contentDescription = "",
                            isSelected = currentRoute == destination.route
                        )
                        Destination.TEST -> GradientIcon(
                            painter = painterResource(id = R.drawable.ic_test),
                            contentDescription = "",
                            isSelected = currentRoute == destination.route
                        )
                        Destination.LESSON -> GradientIcon(
                            painter = painterResource(id = R.drawable.ic_lesson),
                            contentDescription = "",
                            isSelected = currentRoute == destination.route
                        )
                        Destination.CALENDAR -> GradientIcon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "",
                            isSelected = currentRoute == destination.route
                        )
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

@Composable
fun GradientIcon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    isSelected: Boolean
) {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary
    )
    val iconTint = if (isSelected) {
        Modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = colors,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, 0f)
                        ),
                        blendMode = BlendMode.SrcAtop
                    )
                }
            }
    } else {
        Modifier
    }

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .then(iconTint)
            .size(24.dp),
        tint = if (isSelected) Color.Unspecified else LocalContentColor.current
    )
}