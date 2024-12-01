package com.develofer.opositate.feature.profile.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.R
import com.develofer.opositate.ui.theme.Gray600

@Composable
fun ClickableBox(content: @Composable () -> Unit, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        content()
    }
}

@Composable
fun SettingRow(text: String) {
    Row {
        Text(
            modifier = Modifier.padding(start = 18.dp, top = 10.dp, bottom = 10.dp),
            text = text,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_keyboard_arrow_right),
            contentDescription = "Editar",
            tint = Gray600,
            modifier = Modifier
                .padding(end = 18.dp)
                .align(Alignment.CenterVertically)
        )
    }
}