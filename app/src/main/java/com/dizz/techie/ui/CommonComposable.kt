package com.dizz.techie.ui

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dizz.techie.ui.theme.ActiveComponentColor

@Composable
fun YellowButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick, enabled = enabled, modifier = modifier,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = ActiveComponentColor,
            disabledContainerColor = Color(0xFF34312F),
            contentColor = Color.Black
        )
    ) {
        Text(buttonText)
    }
}