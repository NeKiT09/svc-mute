package org.zawarka.svcMute

import de.maxhenkel.voicechat.api.BukkitVoicechatService
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.zawarka.svcMuteCore.commands.MuteAllCommand
import org.zawarka.svcMuteCore.commands.MuteCommand
import org.zawarka.svcMuteCore.commands.SvcmuteCommand
import org.zawarka.svcMuteCore.commands.UnmuteCommand
import org.zawarka.svcMute.data.PlayerService
import org.zawarka.svcMuteCore.data.command.ICommandExecutor
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcMute.data.commands.CommandExecutorPaper
import org.zawarka.svcMute.data.commands.TabCompleterPaper
import org.zawarka.svcMute.messages.PaperMessageService
import org.zawarka.svcMute.voice.PaperVoiceChat
import org.zawarka.svcMuteCore.messages.config.MessagesData
import org.zawarka.svcMuteCore.mute.MuteManager
import org.zawarka.svcMuteCore.mute.MuteStorage
import org.zawarka.svcMuteCore.SvcMuteCore
import org.zawarka.svcMuteCore.commands.MuteGroupCommand
import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.utils.time.TimeConfigFormatter
import org.zawarka.svcMuteCore.voice.VoiceChat
import java.io.File

class SvcMute() : JavaPlugin() {

    companion object {
        lateinit var instance: SvcMute private set
    }

    lateinit var voiceChat: PaperVoiceChat private set

    val core = SvcMuteCore()

    override fun onEnable() {
        instance = this

        core.init(this.dataFolder, PaperMessageService())

        val service = server.servicesManager.load(BukkitVoicechatService::class.java)
        if (service != null) {
            voiceChat = PaperVoiceChat()
            voiceChat.init(core, PlayerService)
            service.registerPlugin(voiceChat)
        } else {
            logger.info("Error occurred while loading voice chat")
            Bukkit.getPluginManager().disablePlugin(this)

            return
        }

        regCommands()
        enableBStats()
    }

    private fun enableBStats(){
        val pluginId = 32185
        val metrics = Metrics(this, pluginId)
    }

    private fun regCommands(){
        val vmute = MuteCommand(core.muteManager.mutes, core.messageService, PlayerService, core.messagesData)
        registerCommand("vmute", vmute, vmute)

        val vunmute = UnmuteCommand(core.muteManager.mutes, core.messageService, PlayerService)
        registerCommand("vunmute", vunmute, vunmute)

        val vgroupmute = MuteGroupCommand(core.muteManager.mutes, core.messageService, VoiceChat.groupStorage, core.messagesData)
        registerCommand("vgroupmute", vgroupmute, vgroupmute)

        val muteall = MuteAllCommand(core.muteManager, core.messageService)
        registerCommand("vmuteall", muteall, muteall)

        val svcmute = SvcmuteCommand(core.muteManager, core.messagesData)
        registerCommand("svcmute", svcmute, svcmute)
    }

    private fun registerCommand(label: String, executor: ICommandExecutor, tab: ITabCompleter) {
        val command = Bukkit.getPluginCommand(label) ?: return

        command.tabCompleter = TabCompleterPaper(tab)
        command.setExecutor(CommandExecutorPaper(executor))
    }

    override fun onDisable() {
        core.storage.saveAll()
    }
}
