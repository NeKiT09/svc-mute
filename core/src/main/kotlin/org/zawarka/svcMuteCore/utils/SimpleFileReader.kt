package org.zawarka.svcMuteCore.utils

import java.io.File
import kotlin.sequences.forEach

class SimpleFileReader(private val file: File) {
    private val data = mutableMapOf<String, String>()

    fun load() {
        data.clear()

        if (!file.exists() || !file.isFile) {
            return
        }

        file.bufferedReader(Charsets.UTF_8).use { reader ->
            reader.lineSequence().forEach { line ->
                val trimmed = line.trim()

                if (trimmed.isEmpty() || trimmed.startsWith("#")) return@forEach

                val colonIndex = trimmed.indexOf(':')
                if (colonIndex > 0) {
                    val key = trimmed.substring(0, colonIndex).trim()
                    var value = trimmed.substring(colonIndex + 1).trim()

                    if (value.length >= 2) {
                        if ((value.startsWith("\"") && value.endsWith("\"")) ||
                            (value.startsWith("'") && value.endsWith("'"))) {
                            value = value.substring(1, value.length - 1)
                        }
                    }

                    data[key] = value
                }
            }
        }
    }

    fun getString(key: String, default: String = ""): String {
        return data[key] ?: default
    }

    fun getInt(key: String, default: Int = 0): Int {
        return data[key]?.toIntOrNull() ?: default
    }

    fun getLong(key: String, default: Long = 0L): Long {
        return data[key]?.toLongOrNull() ?: default
    }

    fun getDouble(key: String, default: Double = 0.0): Double {
        return data[key]?.toDoubleOrNull() ?: default
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        val value = data[key]?.lowercase() ?: return default
        return value == "true" || value == "yes" || value == "on" || value == "1"
    }

    fun get(key: String): String? = data[key]

    fun contains(key: String): Boolean = data.containsKey(key)

    fun getKeys(): Set<String> = data.keys
}