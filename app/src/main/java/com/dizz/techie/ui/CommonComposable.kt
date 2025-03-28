package com.dizz.techie.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
            contentColor = Color.Black,
            disabledContentColor = Color.White
        )
    ) {
        Text(buttonText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ScalableYellowButton(modifier: Modifier = Modifier, enabled: Boolean) {

    val (containerColor, textColor) = when (enabled) {
        true -> Pair(ActiveComponentColor, Color.Black)
        false -> Pair(Color(0xFF34312F), Color.White)
    }
    Box(
        modifier
            .background(containerColor, ButtonDefaults.elevatedShape)
            .padding(ButtonDefaults.ContentPadding)
            .semantics { role = Role.Button }, Alignment.Center
    ) {
        Text(
            "Continue",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            style = MaterialTheme.typography.labelLarge
        )
    }
}