package com.develofer.opositate.main.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.develofer.opositate.feature.login.presentation.component.CustomTitleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: State<String>,
    isDarkTheme: Boolean,
    actions: @Composable () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier.height(136.dp),
        colors = TopAppBarColors(
            containerColor = if (isDarkTheme) {
                Color.Black
            } else {
                MaterialTheme.colorScheme.primary
            },
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        ),
        title = { CustomTitleText(text = title.value, isDarkTheme = isDarkTheme) },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Opción 1") },
                    onClick = {
                    expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Opción 2") },
                    onClick = {
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Opción 3") },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }
    )
}