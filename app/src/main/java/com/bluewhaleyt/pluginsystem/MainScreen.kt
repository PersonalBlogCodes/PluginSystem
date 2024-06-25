package com.bluewhaleyt.pluginsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.bluewhaleyt.pluginapi.ui.ActionDropdownMenuItem

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit,
    innerPadding: PaddingValues,
) {
    Column(Modifier.padding(innerPadding)) {
        LaunchedEffect(key1 = Unit) {
            onEvent(MainEvent.LoadPlugins)
        }
        AnimatedVisibility(visible = state.loading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }
        state.actions.forEach { action ->
            ActionDropdownMenuItem(
                action = action
            )
        }
    }
}