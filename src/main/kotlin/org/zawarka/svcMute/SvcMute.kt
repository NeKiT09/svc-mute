package org.zawarka.svcMute

import de.maxhenkel.voicechat.api.BukkitVoicechatService
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.zawarka.svcMute.voice.VoiceChat

class SvcMute : JavaPlugin() {

    companion object {
        lateinit var instance: SvcMute private set
    }

    lateinit var voiceChat: VoiceChat private set

    override fun onEnable() {
        instance = this

        val service = server.servicesManager.load(BukkitVoicechatService::class.java)
        if (service != null) {
            voiceChat = VoiceChat()
            service.registerPlugin(voiceChat)
        } else {
            logger.info("Error occurred while loading voice chat")
            Bukkit.getPluginManager().disablePlugin(this)

            return
        }
    }

    override fun onDisable() {

    }
}
