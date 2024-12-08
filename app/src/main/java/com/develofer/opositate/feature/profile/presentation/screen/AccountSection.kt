package com.develofer.opositate.feature.profile.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.feature.profile.presentation.components.ClickableBox
import com.develofer.opositate.feature.profile.presentation.components.OpositateCardWithoutClick
import com.develofer.opositate.feature.profile.presentation.components.SettingRow
import com.develofer.opositate.feature.profile.presentation.model.ProfileDialogType
import com.develofer.opositate.ui.theme.Gray800

@Composable
fun AccountSection(
    isDarkTheme: Boolean,
    userName: String,
    userEmail: String,
    showDialog: (ProfileDialogType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AccountInfo(isDarkTheme, userName, userEmail)
        AccountSettings(isDarkTheme, showDialog)
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
                modifier = Modifier.padding(start = 18.dp, top = 10.dp, bottom = 10.dp),
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
fun AccountSettings(
    isDarkTheme: Boolean,
    showDialog: (ProfileDialogType) -> Unit
) {
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
                },
                onClick = { showDialog(ProfileDialogType.UPDATE_USERNAME) }
            )
            ClickableBox(
                content = {
                    HorizontalDivider(thickness = 1.dp, color = Gray800)
                    SettingRow(text = "Actualizar dirección de correo")
                },
                onClick = { showDialog(ProfileDialogType.UPDATE_EMAIL) }
            )
            ClickableBox(
                content = {
                    HorizontalDivider(thickness = 1.dp, color = Gray800)
                    SettingRow(text = "Reestablecer contraseña")
                },
                onClick = { showDialog(ProfileDialogType.UPDATE_PASSWORD) }
            )
            ClickableBox(
                content = {
                    HorizontalDivider(thickness = 1.dp, color = Gray800)
                    SettingRow(text = "Cerrar sesión")
                },
                onClick = { showDialog(ProfileDialogType.SIGN_OUT) }
            )

        }
    )
}
