package com.bluewhaleyt.pluginapi

import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetManager
import android.content.res.Resources

abstract class PluginContextWrapper(
    open val context: Context,
    open val source: PluginSource
) : ContextWrapper(context) {

    override fun getAssets(): AssetManager {
        with(AssetManager::class.java) {
            val assetManager = getConstructor().newInstance()
            val addAssetPath = getMethod("addAssetPath", String::class.java)
            addAssetPath(assetManager, source.getApkFileFromExternalCacheDir().absolutePath)
            return assetManager
        }
    }

    override fun getResources(): Resources {
        @Suppress("DEPRECATION")
        val resources = Resources(assets, context.resources.displayMetrics, context.resources.configuration)
        return resources
    }
}