package com.develofer.opositate

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Property
import android.view.View
import android.view.WindowInsets
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import com.develofer.opositate.ui.LoginScreen
import com.develofer.opositate.ui.theme.OpositateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
//            setKeepOnScreenCondition {
//                !viewModel.isReady.value
//            }
            if (Build.VERSION.SDK_INT >= 34) {
                setOnExitAnimationListener { screen ->
                    animateSplashScreenExit(screen)
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            OpositateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LoginScreen()
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OpositateTheme {
        LoginScreen()
    }
}