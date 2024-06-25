package com.bluewhaleyt.pluginapi.module

import android.content.Context
import com.bluewhaleyt.openapi.module.Module
import com.bluewhaleyt.pluginapi.PluginConfiguration
import com.bluewhaleyt.pluginapi.PluginContextWrapper
import com.bluewhaleyt.pluginapi.PluginSource
import dalvik.system.DexClassLoader
import dalvik.system.DexFile
import kotlinx.serialization.json.Json
import java.io.File

open class PluginModule(
    override val context: Context,
    override val source: PluginSource
) : Module, PluginContextWrapper(context, source) {

    companion object {
        const val PLUGIN_CONFIGURATION_FILE_NAME = "plugin-configuration.json"
    }

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        classDiscriminator = "className"
    }

    private val configurationJsonString
        get() = assets.open(PLUGIN_CONFIGURATION_FILE_NAME)
            .bufferedReader()
            .use { it.readText() }

    val configuration
        get() = json.decodeFromString<PluginConfiguration>(configurationJsonString)

    fun getDexFile(): File {
        val dexFile = File(context.externalCacheDir, "dex")
        if (!dexFile.exists()) dexFile.mkdirs()
        return dexFile
    }

    fun getDexClassLoader(): DexClassLoader {
        val apkFile = source.getApkFileFromExternalCacheDir()
        val dexFile = getDexFile()
        return DexClassLoader(
            apkFile.absolutePath,
            dexFile.absolutePath,
            null,
            context.classLoader
        )
    }

    fun loadClass(className: String) =
        getDexClassLoader().loadClass(className)

    fun loadClasses(): MutableList<Class<*>> {
        val apkFile = source.getApkFileFromExternalCacheDir()
        val dexFile = getDexFile()
        val dexClassLoader = getDexClassLoader()
        val classes = mutableListOf<Class<*>>()

        @Suppress("DEPRECATION")
        val classNames = DexFile.loadDex(
            apkFile.absolutePath, dexFile.absolutePath, 0
        ).entries()
        while (classNames.hasMoreElements()) {
            val className = classNames.nextElement()
            try {
                val clazz = dexClassLoader.loadClass(className)
                classes.add(clazz)
            } catch (_: ClassNotFoundException) {
            }
        }
        return classes
    }

}