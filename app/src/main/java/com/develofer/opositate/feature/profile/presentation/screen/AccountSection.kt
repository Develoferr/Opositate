package com.develofer.opositate.feature.profile.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.presentation.components.ClickableBox
import com.develofer.opositate.feature.profile.presentation.components.OpositateCard
import com.develofer.opositate.feature.profile.presentation.components.OpositateCardWithoutClick
import com.develofer.opositate.feature.profile.presentation.components.SettingRow
import com.develofer.opositate.ui.theme.Gray800

@Composable
fun AccountSection(isDarkTheme: Boolean, userName: String, userEmail: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AccountInfo(isDarkTheme, userName, userEmail)
        AccountSettings(isDarkTheme)
        AccountDangerZone(isDarkTheme)
    }
}

@Composable
fun AccountInfo(isDarkTheme: Boolean, userName: String, userEmail: String) {
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = "Cuenta"
    )

    OpositateCardWithoutClick(
        isDarkTheme = isDarkTheme,
        content = {
            Text(
                modifier = Modifier.padding(start = 18.dp, top = 10.dp),
                text = userName,
                fontSize = 12.sp
            )
            HorizontalDivider(thickness = 1.dp, color = Gray800)
            Text(
                modifier = Modifier.padding(start = 18.dp, top = 10.dp, bottom = 10.dp),
                text = userEmail,
                fontSize = 12.sp
            )
        }
    )

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun AccountSettings(isDarkTheme: Boolean) {
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = "Ajustes de la Cuenta"
    )

    OpositateCardWithoutClick(
        isDarkTheme = isDarkTheme,
        content = {
            ClickableBox(
                content = {
                    SettingRow(text = "Actualizar nombre de usuario")
                    HorizontalDivider(thickness = 1.dp, color = Gray800)
                }
            )
            ClickableBox(
                content = {
                    SettingRow(text = "Actualizar dirección de correo")
                    HorizontalDivider(thickness = 1.dp, color = Gray800)
                }
            )
            ClickableBox(
                content = {
                    SettingRow(text = "Actualizar contraseña")
                    HorizontalDivider(thickness = 1.dp, color = Gray800)
                }
            )
            ClickableBox(
                content = {
                    SettingRow(text = "Cerras sesión")
                }
            )

        }
    )
}

@Composable
fun AccountDangerZone(isDarkTheme: Boolean) {
    Spacer(modifier = Modifier.height(32.dp))

    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = "Zona de Peligro",
        color = Color.Red.copy(alpha = 0.8f)
    )

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

        }
    )
}
