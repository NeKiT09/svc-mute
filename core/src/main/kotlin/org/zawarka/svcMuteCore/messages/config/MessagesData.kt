package org.zawarka.svcMuteCore.messages.config

import org.zawarka.svcMuteCore.utils.SimpleFileReader
import java.io.File

class MessagesData(private val file: File) {

    private val messages = mutableMapOf<MessageType, String>()

    private val reader = SimpleFileReader(file)

    fun isMessageEmpty(key: MessageType) = !messages.containsKey(key)
    fun getMessageString(key: MessageType) = messages.getOrDefault(key, "")

    fun loadFromConfig() {
        createIfNotExists()
        reader.load()
        for (type in MessageType.entries) {
            loadType(type)
        }
    }

    private fun loadType(type: MessageType){
        messages[type] = reader.getString(type.path)
    }

    fun createIfNotExists() {
        if (file.exists()) return

        file.parentFile?.mkdirs()

        val resource = MessagesData::class.java.getResourceAsStream("/config.yml")
            ?: error("config.yml not found in resources")

        resource.use { it.copyTo(file.outputStream()) }
    }
}