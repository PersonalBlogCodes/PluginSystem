package com.bluewhaleyt.pluginapi

import com.bluewhaleyt.openapi.action.ActionItem
import kotlinx.serialization.Serializable

@Serializable
data class PluginConfiguration(
    val name: String,
    val description: String? = null,
    val author: String,
    val actions: List<Action> = emptyList()
) {
    @Serializable
    data class Action(
        val className: String,
        val text: String,
        val description: String? = null
    ) {
        fun toActionItem(plugin: Plugin): ActionItem {
            val action = plugin.loadClass(className)
                .getConstructor()
                .newInstance() as com.bluewhaleyt.openapi.action.Action
            return ActionItem(
                text = text,
                description = description,
                icon = action.icon,
                onClick = { action.onClick() }
            )
        }
    }
}