package org.zawarka.svcMute.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.zawarka.svcMute.messages.MessageValue
import org.zawarka.svcMute.messages.config.MessageType
import org.zawarka.svcMute.messages.config.MessagesData
import org.zawarka.svcMute.messages.sendGlobalMessage
import org.zawarka.svcMute.messages.sendMessage
import org.zawarka.svcMute.mute.MuteInfo
import org.zawarka.svcMute.mute.MuteManager
import org.zawarka.svcMute.utils.startWith
import org.zawarka.svcMute.utils.time.TimeParser
import org.zawarka.svcMute.utils.time.TimeTextSplitter
import java.util.*

class UnmuteCommand : CommandExecutor, TabCompleter {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        if (args.isNullOrEmpty()) {
            return false
        }

        val target = Bukkit.getOfflinePlayer(args[0])
        val targetName = target.name

        if (!MuteManager.isMuted(target.uniqueId)) {
            sender.sendMessage(MessageValue(MessageType.UNMUTE_COMMAND_PLAYER_ALREADY_UNMUTED, sender, targetName))
            return true
        }

        MuteManager.removeMute(target.uniqueId)

        sendGlobalMessage(MessageValue(MessageType.UNMUTE_GLOBAL_MESSAGE, sender, targetName), "svcmute.unmute.msg")
        target.player?.sendMessage(MessageValue(MessageType.UNMUTE_PLAYER_MESSAGE, sender, targetName))

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): List<String> {
        val l = args?.size ?: 0

        if (l < 2) {
            val list = Bukkit.getOnlinePlayers().map { if(MuteManager.isMuted(it.uniqueId)) it.name else "" }

            return if (l == 0) {
                list
            } else {
                list.startWith(args!![0])
            }
        }

        return emptyList()
    }


}