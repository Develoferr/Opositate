package com.develofer.opositate.main

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Property
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.main.navigation.AppNavigation
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var auth: FirebaseAuth
    private lateinit var navHostController: NavHostController
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplashScreen()
        setupEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            SetupAppContent()
        }
    }

    private fun setupSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition { mainViewModel.isUserNotRetrieved.value }

            if (Build.VERSION.SDK_INT >= 34) {
                setOnExitAnimationListener { screen ->
                    animateSplashScreenExit(screen)
                }
            }
        }
    }

    private fun setupEdgeToEdge() {
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    @Composable
    private fun SetupAppContent() {
        val appBarTitle = mainViewModel.appBarTitle.collectAsState()
        val isSystemUIVisible by mainViewModel.isSystemUIVisible.collectAsState()
        val themePreferences by mainViewModel.themePreferences.collectAsState()

        val isDarkTheme = if (themePreferences.isAutoThemeEnabled) {
            isSystemInDarkTheme()
        } else {
            themePreferences.isDarkThemeManual
        }

        LaunchedEffect(isSystemUIVisible) {
            if (isSystemUIVisible) showSystemUI() else hideSystemUI()
        }

        val startDestination = mainViewModel.getStartDestination()
        AppNavigation(
            navHostController = navHostController,
            startDestination,
            mainViewModel,
            appBarTitle,
            isDarkTheme
        )
    }

    private fun hideSystemUI() {
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI() {
        WindowCompat.getInsetsController(window, window.decorView)
            .show(WindowInsetsCompat.Type.systemBars())
    }

    private fun animateSplashScreenExit(screen: SplashScreenViewProvider) {
        fun createZoomAnimator(property: Property<View, Float>): ObjectAnimator {
            return ObjectAnimator.ofFloat(screen.iconView, property, 1.0f, 0.0f).apply {
                interpolator = OvershootInterpolator()
                duration = 500L
                doOnEnd { screen.remove() }
            }
        }
        createZoomAnimator(View.SCALE_X).start()
        createZoomAnimator(View.SCALE_Y).start()
    }
}