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

class MuteCommand : CommandExecutor, TabCompleter {
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

        if (MuteManager.isMuted(target.uniqueId)) {
            sender.sendMessage(MessageValue(MessageType.MUTE_COMMAND_PLAYER_ALREADY_MUTED, sender, targetName))
            return true
        }

        val parts = TimeTextSplitter.splitTimeStrings(Arrays.stream(args).skip(1).toList())

        val length: Long = try {
            TimeParser.parseTimeToSeconds(parts.timeString)
        } catch (_: IllegalArgumentException) {
            return false
        }

        val reason: String = parts.reasonString
            .apply {
                if(isEmpty()) MessagesData.getMessageString(MessageType.REASON_EMPTY)
            }

        MuteManager.addMute(MuteInfo(target.uniqueId, length, reason))

        if (length == 0L) {
            sendGlobalMessage(MessageValue(MessageType.MUTE_GLOBAL_MESSAGE_PERMANENT, sender, targetName, reason), "svcmute.mute.msg")
            target.player?.sendMessage(MessageValue(MessageType.MUTE_PLAYER_MESSAGE, sender, targetName, reason))

            return true
        }
        sendGlobalMessage(MessageValue(MessageType.MUTE_GLOBAL_MESSAGE, sender, targetName, reason, length), "svcmute.mute.msg")
        target.player?.sendMessage(MessageValue(MessageType.MUTE_PLAYER_MESSAGE, sender, targetName, reason, length))

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
            val list = Bukkit.getOnlinePlayers().map { if(!MuteManager.isMuted(it.uniqueId)) it.name else "" }

            return if (l == 0) {
                list
            } else {
                list.startWith(args!![0])
            }
        }

        return listOf("*length>>>reason*")
    }


}