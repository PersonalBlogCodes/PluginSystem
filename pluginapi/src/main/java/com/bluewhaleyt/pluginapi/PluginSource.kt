package com.bluewhaleyt.pluginapi

import android.content.Context
import java.io.File

sealed interface PluginSource {
    val context: Context
    fun getApkFileFromExternalCacheDir(): File

    data class AssetsPluginSource(
        override val context: Context,
        val apkFileName: String
    ) : PluginSource {
        override fun getApkFileFromExternalCacheDir(): File {
            val inputStream = context.assets.open(apkFileName)
            val apkFileFromExternalCacheDir = File(context.externalCacheDir, apkFileName)
            apkFileFromExternalCacheDir.writeBytes(inputStream.readBytes())
            return apkFileFromExternalCacheDir
        }
    }

    data class FilePluginSource(
        override val context: Context,
        val apkFile: File
    ) : PluginSource {
        override fun getApkFileFromExternalCacheDir(): File {
            val apkFileFromExternalCacheDir = File(context.externalCacheDir, apkFile.name)
            apkFile.copyTo(apkFileFromExternalCacheDir, true)
            return apkFileFromExternalCacheDir
        }
    }
}