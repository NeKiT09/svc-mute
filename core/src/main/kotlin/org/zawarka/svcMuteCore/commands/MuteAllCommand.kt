package org.zawarka.svcMuteCore.commands

import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.command.MuteCommandSender
import org.zawarka.svcMuteCore.data.command.ICommandExecutor
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcMuteCore.messages.config.MessageType
import org.zawarka.svcMuteCore.mute.MuteManager

class MuteAllCommand(val muteManager: MuteManager, val messageService: MessageService) : ICommandExecutor,
    ITabCompleter {
    override fun onCommand(
        sender: MuteCommandSender,
        args: List<String>
    ): Boolean {
        if(args.isNullOrEmpty()) {
            return false
        }

        if(args[0] == "on"){
            muteManager.muteAll()
            messageService.sendGlobalMessage(
                MessageValue(
                    MessageType.MUTEALL_COMMAND_GLOBAL_MESSAGE_ON,
                    sender.name
                ), MutePermission.MUTEALL_MSG_SEE)
            return true
        }else if(args[0] == "off"){
            muteManager.unMuteAll()
            messageService.sendGlobalMessage(
                MessageValue(
                    MessageType.MUTEALL_COMMAND_GLOBAL_MESSAGE_OFF,
                    sender.name
                ), MutePermission.MUTEALL_MSG_SEE)
            return true
        }

        return false
    }

    override fun tabComplete(): List<List<String>> {
        val list = listOf("on", "off")

        return listOf(list)
    }
}