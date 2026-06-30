package org.zawarka.svcMuteFabric.data.commands

import net.minecraft.server.command.ServerCommandSource
import org.zawarka.svcMuteCore.data.command.MuteCommandSender
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcMuteFabric.data.sendMessage

fun ServerCommandSource.toFabricCommandSender() : MuteCommandSender {
    return FabricCommandSender(this)
}

class FabricCommandSender(val sender: ServerCommandSource) : MuteCommandSender(sender.name) {
    override fun sendMessage(value: MessageValue) {
        sender.sendMessage(value)
    }
}