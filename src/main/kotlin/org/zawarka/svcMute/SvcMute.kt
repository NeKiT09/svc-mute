package org.zawarka.svcMute

import de.maxhenkel.voicechat.api.BukkitVoicechatService
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.java.JavaPlugin
import org.zawarka.svcMute.commands.MuteAllCommand
import org.zawarka.svcMute.commands.MuteCommand
import org.zawarka.svcMute.commands.SvcmuteCommand
import org.zawarka.svcMute.commands.UnmuteCommand
import org.zawarka.svcMute.mute.MuteStorage
import org.zawarka.svcMute.voice.VoiceChat
import org.zawarka.svcMute.voicemessages.VoiceMessagesHook


class SvcMute : JavaPlugin() {

    companion object {
        lateinit var instance: SvcMute private set
    }

    lateinit var voiceChat: VoiceChat private set

    val storage = MuteStorage(this)

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

        VoiceMessagesHook.init(this)

        regCommands()
        enableBStats()

        storage.init()
        storage.loadAll()
    }

    private fun enableBStats(){
        val pluginId = 32185
        val metrics = Metrics(this, pluginId)
    }

    private fun regCommands(){
        val vmute = MuteCommand()
        registerCommand("vmute", vmute, vmute)

        val vunmute = UnmuteCommand()
        registerCommand("vunmute", vunmute, vunmute)

        val muteall = MuteAllCommand()
        registerCommand("vmuteall", muteall, muteall)

        val svcmute = SvcmuteCommand()
        registerCommand("svcmute", svcmute, svcmute)
    }

    private fun registerCommand(label: String, executor: CommandExecutor, tab: TabCompleter) {
        val command = Bukkit.getPluginCommand(label) ?: return

        command.tabCompleter = tab
        command.setExecutor(executor)
    }

    override fun onDisable() {
        storage.saveAll()
    }
}
