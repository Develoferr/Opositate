package com.develofer.opositate.main.navigation

import androidx.compose.animation.core.Spring
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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource

@Composable
fun CustomBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        ProfileNavigation,
        TestNavigation,
        LessonNavigation,
        CalendarNavigation
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { destination ->
            NavigationBarItem(
                icon = {
                    when (destination) {
                        ProfileNavigation -> GradientIcon(
                            painter = painterResource(id = R.drawable.ic_brain_profile_png),
                            contentDescription = "",
                            isSelected = currentRoute == destination.route
                        )
                        TestNavigation -> GradientIcon(
                            painter = painterResource(id = R.drawable.ic_test_png),
                            contentDescription = "",
                            isSelected = currentRoute == destination.route
                        )
                        LessonNavigation -> GradientIcon(
                            painter = painterResource(id = R.drawable.ic_lesson_png),
                            contentDescription = "",
                            isSelected = currentRoute == destination.route
                        )
                        CalendarNavigation -> GradientIcon(
                            painter = painterResource(id = R.drawable.ic_calendar_png),
                            contentDescription = "",
                            isSelected = currentRoute == destination.route
                        )
                        else -> GradientIcon(
                            painter = painterResource(id = R.drawable.ic_test_png),
                            contentDescription = "",
                            isSelected = currentRoute == destination.route
                        )
                    }
                },
                label = {
                    Text(text = when (destination) {
                        ProfileNavigation -> stringResource(id = R.string.profile_screen__app_bar_title__profile).uppercase()
                        TestNavigation -> stringResource(id = R.string.test_screen__app_bar_title__test).uppercase()
                        LessonNavigation -> stringResource(id = R.string.lesson_screen__app_bar_title__lesson).uppercase()
                        CalendarNavigation -> stringResource(id = R.string.calendar_screen__app_bar_title__calendar).uppercase()
                        else -> stringResource(id = R.string.test_screen__app_bar_title__test).uppercase()})
                },
                selected = currentRoute == destination.route,
                onClick = {
                    if (currentRoute != destination.route) {
                        navController.navigate(destination) {
                            popUpTo(destination) { inclusive = false }
                            launchSingleTop = true
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
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    val colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    val iconTint = if (isSelected) {
        Modifier
            .graphicsLayer(alpha = 0.99f, scaleX = scale, scaleY = scale)
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
        Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
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
