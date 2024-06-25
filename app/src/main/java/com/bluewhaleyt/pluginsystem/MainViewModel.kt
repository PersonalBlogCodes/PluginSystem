package com.bluewhaleyt.pluginsystem

import android.app.Application
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bluewhaleyt.openapi.action.Action
import com.bluewhaleyt.openapi.action.ActionItem
import com.bluewhaleyt.openapi.toIcon
import com.bluewhaleyt.pluginapi.Plugin
import com.bluewhaleyt.pluginapi.PluginSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Collections.addAll
import kotlin.system.exitProcess

data class MainState(
    val plugins: SnapshotStateList<Plugin> = mutableStateListOf(),
    val actions: SnapshotStateList<ActionItem> = mutableStateListOf(),
    val loading: Boolean = false,
    val success: Boolean = false
)

sealed interface MainEvent {
    data object LoadPlugins : MainEvent
}

class MainViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    var state by mutableStateOf(
        MainState(
            plugins = mutableStateListOf<Plugin>().apply {
                addAll(listOf(
                    Plugin(
                        context = app,
                        source = PluginSource.AssetsPluginSource(app, "example-plugin-debug.apk")
                    )
                ))
            },
            actions = mutableStateListOf<ActionItem>().apply {
                addAll(listOf(
                    ActionItem(
                        text = "Exit Application",
                        icon = Icons.AutoMirrored.Outlined.ExitToApp.toIcon(),
                        onClick = {
                            exitProcess(0)
                        }
                    )
                ))
            }
        )
    )
        private set

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.LoadPlugins -> loadPlugins()
        }
    }

    private fun loadPlugins() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(loading = true)
            if (!state.success) {
                state.plugins.forEach { plugin ->
                    plugin.load()
                    delay(2000L)
                    plugin.configuration.actions.forEach { action ->
                        state.actions += action.toActionItem(plugin)
                    }
                }
                state = state.copy(success = true)
            }
            state = state.copy(loading = false)
        }
    }
}