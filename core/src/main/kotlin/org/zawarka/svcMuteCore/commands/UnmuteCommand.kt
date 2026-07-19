package org.zawarka.svcMuteCore.commands

import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.command.MuteCommandSender
import org.zawarka.svcMuteCore.data.command.ICommandExecutor
import org.zawarka.svcMuteCore.data.IPlayerService
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcMuteCore.messages.config.MessageType
import org.zawarka.svcMuteCore.mute.MuteContainer
import org.zawarka.svcMuteCore.mute.MuteManager

class UnmuteCommand(val container: MuteContainer, val messageService: MessageService, val playerService: IPlayerService) : ICommandExecutor,
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

        if (!container.isMuted(target.uniqueId)) {
            sender.sendMessage(
                MessageValue(
                    MessageType.UNMUTE_COMMAND_ALREADY_UNMUTED,
                    sender.name,
                    targetName
                )
            )
            return true
        }

        container.removeMute(target.uniqueId)

        messageService.sendGlobalMessage(
            MessageValue(
                MessageType.UNMUTE_GLOBAL_MESSAGE,
                sender.name,
                targetName
            ), MutePermission.UNMUTE_MSG_SEE)
        target.sendMessage(
            MessageValue(
                MessageType.UNMUTE_PLAYER_MESSAGE,
                sender.name,
                targetName
            )
        )

        return true
    }

    override fun tabComplete(): List<List<String>> {
        val list = playerService.getOnlinePlayers().map { if(container.isMuted(it.uniqueId)) it.name else "" }

        return listOf(list)
    }


}