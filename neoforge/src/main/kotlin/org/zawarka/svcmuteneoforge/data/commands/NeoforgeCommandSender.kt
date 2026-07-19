package org.zawarka.svcmuteneoforge.data.commands

import net.minecraft.commands.CommandSourceStack
import org.zawarka.svcMuteCore.data.command.MuteCommandSender
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcmuteneoforge.data.NeoforgeMessageService

fun CommandSourceStack.toNeoforgeCommandSender() : MuteCommandSender {
    return NeoforgeCommandSender(this)
}

class NeoforgeCommandSender(val sourceStack: CommandSourceStack) : MuteCommandSender(sourceStack.textName) {
    override fun sendMessage(value: MessageValue) {
        NeoforgeMessageService.instance.sendMessage(sourceStack, value)
    }
}