package com.bluewhaleyt.pluginapi

import android.content.Context
import com.bluewhaleyt.pluginapi.module.PluginModule

data class Plugin(
    override val context: Context,
    override val source: PluginSource
) : PluginModule(context, source) {

    fun load() {
        val dexClassLoader = getDexClassLoader()
        println("""
            APK file: ${source.getApkFileFromExternalCacheDir()}
            DEX file: ${getDexFile()}
        """.trimIndent())
    }
}