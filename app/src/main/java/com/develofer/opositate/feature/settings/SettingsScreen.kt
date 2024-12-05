package com.develofer.opositate.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.presentation.components.OpositateCard
import com.develofer.opositate.main.MainViewModel

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    mainViewModel.showSystemUI()
    val screenTitle = stringResource(id = R.string.settings_screen__app_bar_title__settings)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }

    AccountDangerZone(isDarkTheme)
}

@Composable
fun AccountDangerZone(isDarkTheme: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize().background(if (isDarkTheme) Color.Unspecified else Color.White)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Zona de Peligro",
            color = Color.Red.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OpositateCard(
            isDarkTheme = isDarkTheme,
            content = {
                Row {
                    Text(
                        modifier = Modifier.padding(start = 18.dp, top = 10.dp, bottom = 10.dp),
                        text = "Eliminar cuenta",
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier
                            .padding(end = 18.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.ic_close),
                        tint = Color.Red.copy(alpha = 0.8f),
                        contentDescription = "Botón"
                    )
                }

            },
            onClick = {  }
        )
    }
}