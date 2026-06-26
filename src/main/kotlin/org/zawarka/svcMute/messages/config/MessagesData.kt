package org.zawarka.svcMute.messages.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.zawarka.svcMute.SvcMute
import java.io.File

object MessagesData {

    private val messages = mutableMapOf<MessageType, String>()

    fun isMessageEmpty(key: MessageType) = !messages.containsKey(key)
    fun getMessageString(key: MessageType) = messages.getOrDefault(key, "")

    fun loadFromConfig() {
        val plugin = SvcMute.instance;

        val config = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "config.yml"))

        for (type in MessageType.entries) {
            loadType(config, type)
        }
    }

    private fun loadType(config: FileConfiguration, type: MessageType){
        messages[type] = config.getString(type.path) ?: ""
    }
}