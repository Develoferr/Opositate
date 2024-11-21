package com.develofer.opositate.main.components.navbar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.develofer.opositate.R
import com.develofer.opositate.main.components.common.GradientIcon
import com.develofer.opositate.main.navigation.CalendarNavigation
import com.develofer.opositate.main.navigation.LessonNavigation
import com.develofer.opositate.main.navigation.ProfileNavigation
import com.develofer.opositate.main.navigation.Route
import com.develofer.opositate.main.navigation.SettingsNavigation
import com.develofer.opositate.main.navigation.TestNavigation
import com.develofer.opositate.ui.theme.Gray600
import com.develofer.opositate.ui.theme.Gray800
import com.develofer.opositate.ui.theme.Gray900

@Composable
fun CustomBottomNavigationBar(
    navController: NavHostController,
    isProgressVisible: Boolean,
    progress: Float,
    isDarkTheme: Boolean
) {
    val items = listOf(ProfileNavigation, TestNavigation, LessonNavigation, CalendarNavigation, SettingsNavigation)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)

    Column {
        SinusoidalProgressBar(
            isVisible = isProgressVisible,
            progress = progress,
        )
        if (!isDarkTheme) HorizontalDivider(thickness = 2.dp, color = Gray800)
        else {
            AnimatedSelectionIndicator(
                items = items,
                currentRoute = currentRoute,
                colors = colors
            )
        }

        NavigationBar(
            containerColor = if (isDarkTheme) Gray900 else Color.White,
        ) {
            items.forEach { destination ->
                val isSelected = currentRoute == destination.route
                val fontSize = animateFloatAsState(
                    targetValue = when {
                        isSelected && destination == CalendarNavigation -> 9f
                        isSelected -> 9.5f
                        destination == CalendarNavigation -> 8f
                        else -> 8.5f
                    },
                    animationSpec = spring(
                        dampingRatio = if (destination == CalendarNavigation)
                            Spring.DampingRatioNoBouncy
                        else
                            Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = "fontSize"
                )

                NavigationBarItem(
                    icon = { GetNavigationIcon(destination, isSelected) },
                    label = {
                        Text(
                            text = getNavigationLabel(destination).uppercase(),
                            fontSize = fontSize.value.sp,
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(destination) {
                                popUpTo(destination) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Unspecified,
                        selectedTextColor = MaterialTheme.colorScheme.onBackground,
                        unselectedIconColor = Gray600,
                        unselectedTextColor = Gray600,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
private fun GetNavigationIcon(destination: Route, isSelected: Boolean) {
    GradientIcon(
        painter = painterResource(
            when (destination) {
                ProfileNavigation -> R.drawable.ic_brain_profile_png
                TestNavigation -> R.drawable.ic_test_png
                LessonNavigation -> R.drawable.ic_lesson_png
                CalendarNavigation -> R.drawable.ic_calendar_png
                SettingsNavigation -> R.drawable.ic_gear
                else -> R.drawable.ic_brain_profile_png
            }
        ),
        contentDescription = stringResource(
            when (destination) {
                ProfileNavigation -> R.string.bottom_nav_bar__content_description__profile
                TestNavigation -> R.string.bottom_nav_bar__content_description__test
                LessonNavigation -> R.string.bottom_nav_bar__content_description__lesson
                CalendarNavigation -> R.string.bottom_nav_bar__content_description__calendar
                SettingsNavigation -> R.string.bottom_nav_bar__content_description__test
                else -> R.string.bottom_nav_bar__content_description__profile
            }
        ),
        isSelected = isSelected
    )
}

@Composable
private fun getNavigationLabel(destination: Route): String {
    return stringResource(
        when (destination) {
            ProfileNavigation -> R.string.profile_screen__app_bar_title__profile
            TestNavigation -> R.string.test_screen__app_bar_title__test
            LessonNavigation -> R.string.lesson_screen__app_bar_title__lesson
            CalendarNavigation -> R.string.calendar_screen__app_bar_title__calendar
            SettingsNavigation -> R.string.settings_screen__app_bar_title__settings
            else -> R.string.test_screen__app_bar_title__test
        }
    )
}