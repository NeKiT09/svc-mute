package org.zawarka.svcMuteCore.commands

import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.command.MuteCommandSender
import org.zawarka.svcMuteCore.data.command.ICommandExecutor
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcMuteCore.messages.config.MessageType
import org.zawarka.svcMuteCore.messages.config.MessagesData
import org.zawarka.svcMuteCore.mute.MuteContainer
import org.zawarka.svcMuteCore.mute.MuteInfo
import org.zawarka.svcMuteCore.utils.time.TimeParser
import org.zawarka.svcMuteCore.utils.time.TimeTextSplitter
import org.zawarka.svcMuteCore.voice.GroupStorage

class MuteGroupCommand(val container: MuteContainer, val messageService: MessageService, val groupStorage: GroupStorage, val messagesData: MessagesData) : ICommandExecutor,
    ITabCompleter {
    override fun onCommand(
        sender: MuteCommandSender,
        args: List<String>
    ): Boolean {
        if (args.isNullOrEmpty()) {
            return false
        }

        val group = groupStorage.get(args[0])

        if(group == null) {
            sender.sendMessage(
                MessageValue(
                    MessageType.MUTE_COMMAND_ALREADY_MUTED,
                    sender.name,
                    args[0]
                )
            )
            return false
        }

        val groupName = group.name

        if (container.isMuted(group.id)) {
            sender.sendMessage(
                MessageValue(
                    MessageType.MUTE_COMMAND_ALREADY_MUTED,
                    sender.name,
                    groupName
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

        container.addMute(MuteInfo(group.id, length, reason))

        if (length == 0L) {
            messageService.sendGlobalMessage(
                MessageValue(
                    MessageType.MUTE_GROUP_GLOBAL_MESSAGE_PERMANENT,
                    sender.name,
                    groupName,
                    reason
                ), MutePermission.MUTE_MSG_SEE)

            return true
        }
        messageService.sendGlobalMessage(
            MessageValue(
                MessageType.MUTE_GROUP_GLOBAL_MESSAGE,
                sender.name,
                groupName,
                reason,
                length
            ), MutePermission.MUTE_MSG_SEE)

        return true
    }

    override fun tabComplete(): List<List<String>> {
        val playerList = groupStorage.groups().map { if(!container.isMuted(it.id)) it.name else "" }

        return listOf(playerList, listOf("<time>", "<reason>"))
    }


}