package com.bluewhaleyt.openapi.action

import com.bluewhaleyt.openapi.Icon

data class ActionItem(
    val text: String,
    val description: String? = null,
    val icon: Icon?,
    val isEnabled: Boolean = true,
    val isVisible: Boolean = true,
    val onClick: () -> Unit
)