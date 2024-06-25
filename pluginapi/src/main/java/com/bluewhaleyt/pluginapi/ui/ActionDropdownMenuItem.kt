package com.bluewhaleyt.pluginapi.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.openapi.Icon
import com.bluewhaleyt.openapi.action.ActionItem

@Composable
fun ActionDropdownMenuItem(
    modifier: Modifier = Modifier,
    action: ActionItem
) {
    AnimatedVisibility(visible = action.isVisible) {
        DropdownMenuItem(
            modifier = modifier,
            enabled = action.isEnabled,
            text = {
                Text(text = action.text)
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    icon = action.icon,
                    contentDescription = null,
                    keepSpaceIfNull = true
                )
            },
            onClick = action.onClick
        )
    }
}