package com.droidcon.movieapp.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingComponent() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(40.dp),
        strokeWidth = 4.dp,
        color = Color.White,
    )
}
