package com.develofer.opositate.presentation

import android.animation.ObjectAnimator
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.os.Bundle
import android.util.Property
import android.view.View
import android.view.WindowInsets
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.presentation.navigation.AppNavigation
import com.develofer.opositate.presentation.navigation.AppRoutes
import com.develofer.opositate.presentation.viewmodel.MainViewModel
import com.develofer.opositate.ui.theme.OpositateTheme
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
        installSplashScreen().apply {

            val isUserNotRetrieved = !mainViewModel.isUserRetrieved.value

            setKeepOnScreenCondition {
                isUserNotRetrieved
            }

            if (Build.VERSION.SDK_INT >= 34) {
                setOnExitAnimationListener { screen ->
                    animateSplashScreenExit(screen)
                }
            }
        }
        enableEdgeToEdge()

        setContent {

            val isSystemUIVisible by mainViewModel.isSystemUIVisible.collectAsState()

            LaunchedEffect(isSystemUIVisible) {
                if (isSystemUIVisible) showSystemUI() else hideSystemUI()
            }

            navHostController = rememberNavController()
            OpositateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val startDestination =
                        if (mainViewModel.currentUser != null) AppRoutes.Destination.HOME.route
                        else AppRoutes.Destination.LOGIN.route
                    AppNavigation(navHostController = navHostController, startDestination, mainViewModel)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        hideSystemUI()
    }

    private fun hideSystemUI() {
        val windowInsetsController = window.insetsController
        windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
    }

    private fun showSystemUI() {
        val windowInsetsController = window.insetsController
        windowInsetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
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

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainPreview() {
    val loginRoute = AppRoutes.Destination.LOGIN.route
    val homeRoute = AppRoutes.Destination.HOME.route
    OpositateTheme {
        AppNavigation(rememberNavController(), loginRoute)
    }
}