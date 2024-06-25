package com.bluewhaleyt.example_plugin

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Extension
import com.bluewhaleyt.openapi.Icon
import com.bluewhaleyt.openapi.action.Action
import com.bluewhaleyt.openapi.toIcon

class ExampleAction : Action {
    companion object {
        val LOG: String = ExampleAction::class.java.simpleName
    }

    override val icon = Icons.Outlined.Extension.toIcon()

    override fun onClick() {
        Log.d(LOG, "clicked event executed from Example plugin")
    }
}