package com.develofer.opositate.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.develofer.opositate.feature.login.presentation.component.CustomTitleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: State<String>,
    isDarkTheme: Boolean,
    actions: @Composable () -> Unit = {},
    logout: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            windowInsets = WindowInsets.safeContent.only(WindowInsetsSides.Top),
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
            navigationIcon = {
                IconButton(
                    onClick = { /* Handle navigation icon click */ },
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Menu", tint = MaterialTheme.colorScheme.onBackground)
                }
            },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomTitleText(text = title.value, isDarkTheme = isDarkTheme)
                }
            },
            actions = {
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More options", tint = MaterialTheme.colorScheme.onBackground)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Cerrar sesión") },
                        onClick = {
                            logout()
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
}