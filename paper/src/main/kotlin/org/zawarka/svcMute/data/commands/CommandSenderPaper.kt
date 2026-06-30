package org.zawarka.svcMute.data.commands

import org.bukkit.command.CommandSender
import org.zawarka.svcMute.messages.sendMessage
import org.zawarka.svcMuteCore.data.command.MuteCommandSender
import org.zawarka.svcMuteCore.messages.MessageValue
import java.util.UUID

fun CommandSender.toPaperSender() : CommandSenderPaper{
    return CommandSenderPaper(this)
}

class CommandSenderPaper(val sender: CommandSender) : MuteCommandSender(sender.name) {
    override fun sendMessage(value: MessageValue) {
        sender.sendMessage(value)
    }
}