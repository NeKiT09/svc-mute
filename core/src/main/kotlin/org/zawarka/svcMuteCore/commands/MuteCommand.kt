package org.zawarka.svcMuteCore.commands

import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.command.MuteCommandSender
import org.zawarka.svcMuteCore.data.command.ICommandExecutor
import org.zawarka.svcMuteCore.data.IPlayerService
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcMuteCore.messages.config.MessageType
import org.zawarka.svcMuteCore.messages.config.MessagesData
import org.zawarka.svcMuteCore.mute.MuteInfo
import org.zawarka.svcMuteCore.mute.MuteManager
import org.zawarka.svcMuteCore.utils.time.TimeParser
import org.zawarka.svcMuteCore.utils.time.TimeTextSplitter

class MuteCommand(val muteManager: MuteManager, val messageService: MessageService, val playerService: IPlayerService, val messagesData: MessagesData) : ICommandExecutor,
    ITabCompleter {
    override fun onCommand(
        sender: MuteCommandSender,
        args: List<String>
    ): Boolean {
        if (args.isNullOrEmpty()) {
            return false
        }

        val target = playerService.getPlayer(args[0])!!
        val targetName = target.name

        if (muteManager.isMuted(target.uniqueId)) {
            sender.sendMessage(
                MessageValue(
                    MessageType.MUTE_COMMAND_PLAYER_ALREADY_MUTED,
                    sender.name,
                    targetName
                )
            )
            return true
        }

        val parts = TimeTextSplitter.splitTimeStrings(args.stream().skip(1).toList())

        val length: Long = try {
            TimeParser.parseTimeToSeconds(parts.timeString)
        } catch (_: IllegalArgumentException) {
            return false
        }

        val reason: String = with(parts.reasonString) {
                ifBlank {
                    messagesData.getMessageString(
                        MessageType.REASON_EMPTY)
                }
            }

        muteManager.addMute(MuteInfo(target.uniqueId, length, reason))


        target.sendMessage(
            MessageValue(
                MessageType.MUTE_PLAYER_MESSAGE,
                sender.name,
                targetName,
                reason,
                length
            )
        )

        if (length == 0L) {
            messageService.sendGlobalMessage(
                MessageValue(
                    MessageType.MUTE_GLOBAL_MESSAGE_PERMANENT,
                    sender.name,
                    targetName,
                    reason
                ), MutePermission.MUTE_MSG_SEE)

            return true
        }
        messageService.sendGlobalMessage(
            MessageValue(
                MessageType.MUTE_GLOBAL_MESSAGE,
                sender.name,
                targetName,
                reason,
                length
            ), MutePermission.MUTE_MSG_SEE)

        return true
    }

    override fun tabComplete(): List<List<String>> {
        val playerList = playerService.getOnlinePlayers().map { if(!muteManager.isMuted(it.uniqueId)) it.name else "" }

        return listOf(playerList, listOf("<time>", "<reason>"))
    }


}