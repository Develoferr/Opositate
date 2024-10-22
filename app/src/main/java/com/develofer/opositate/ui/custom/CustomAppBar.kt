package com.develofer.opositate.ui.custom

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: State<String>,
    actions: @Composable () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title.value) },
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