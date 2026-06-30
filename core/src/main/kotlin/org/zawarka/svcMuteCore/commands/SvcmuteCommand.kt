package org.zawarka.svcMuteCore.commands

import org.zawarka.svcMuteCore.data.command.ICommandExecutor
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcMuteCore.data.command.MuteCommandSender
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcMuteCore.messages.config.MessageType
import org.zawarka.svcMuteCore.messages.config.MessagesData
import org.zawarka.svcMuteCore.mute.MuteManager

class SvcmuteCommand(val muteManager : MuteManager, val messagesData: MessagesData) : ICommandExecutor,
    ITabCompleter {
    override fun onCommand(
        sender: MuteCommandSender,
        args: List<String>
    ): Boolean {

        if(args.isNullOrEmpty()) {
            return false
        }

        if(args[0] == "reload"){
            messagesData.loadFromConfig()
            sender.sendMessage(MessageValue(MessageType.CONFIG_RELOAD_MSG))
        }

        return true
    }

    override fun tabComplete(): List<List<String>> {
        val list = listOf("reload")

        return listOf(list)
    }
}