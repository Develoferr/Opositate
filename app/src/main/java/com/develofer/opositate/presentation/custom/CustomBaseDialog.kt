package com.develofer.opositate.presentation.custom

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun SuccessDialog(
    modifier: Modifier = Modifier,
    isDialogVisible: Boolean = true,
    onDismiss: () -> Unit = {},
    delayTime: Long = 0,
    confirmButton: @Composable () -> Unit,
    title: @Composable (() -> Unit)?,
    text: @Composable (() -> Unit)? = {
        Text(text = "Operación exitosa.")
    }
) {
    CustomBaseAlertDialog(
        modifier = modifier,
        isDialogVisible = isDialogVisible,
        onDismiss = onDismiss,
        title = title,
        text = text ,
        confirmButton = confirmButton,
        delayTime = delayTime
    )
}

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    isDialogVisible: Boolean = true,
    onDismiss: () -> Unit = {},
    delayTime: Long = 0,
    confirmButton: @Composable () -> Unit,
    title: @Composable (() -> Unit)? = {
        Text(text = "Error")
    },
    text: @Composable (() -> Unit)? = {
        Text(text = "Ha ocurrido un error. Inténtalo de nuevo.")
    }
) {
    CustomBaseAlertDialog(
        modifier = modifier,
        isDialogVisible = isDialogVisible,
        onDismiss = onDismiss,
        title = title,
        text = text ,
        confirmButton = confirmButton,
        delayTime = delayTime
    )
}

@Composable
fun CustomBaseAlertDialog(
    modifier: Modifier = Modifier,
    isDialogVisible: Boolean = true,
    onDismiss: () -> Unit = {},
    delayTime: Long = 0,
    title: @Composable (() -> Unit)?,
    text: @Composable (() -> Unit)?,
    dismissButton: @Composable (() -> Unit)? = null,
    confirmButton: @Composable (() -> Unit)
) {
    if (isDialogVisible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            title = title,
            text = text,
            confirmButton = confirmButton,
            dismissButton = dismissButton
        )

    }
    if (delayTime > 0) {
        LaunchedEffect(isDialogVisible) {
            delay(delayTime)
            onDismiss()
        }
    }
}

@Composable
fun CustomBaseDialog(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
    properties: DialogProperties,
    delayTime: Long = 0,
    isDialogVisible: Boolean
) {
    Dialog(
        onDismissRequest = onDismiss,
        content = content,
        properties = properties
    )
    if (delayTime > 0) {
        LaunchedEffect(isDialogVisible) {
            delay(delayTime)
            onDismiss()
        }
    }
}
