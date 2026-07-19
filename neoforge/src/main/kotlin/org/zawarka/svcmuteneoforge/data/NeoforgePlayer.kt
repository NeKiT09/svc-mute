package org.zawarka.svcmuteneoforge.data

import net.minecraft.server.level.ServerPlayer
import org.zawarka.svcMuteCore.data.IPlayer
import org.zawarka.svcMuteCore.data.Permission
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcmuteneoforge.utils.hasPermission

fun ServerPlayer.toNeoforgePlayer() : NeoforgePlayer {
    return NeoforgePlayer(this)
}

class NeoforgePlayer(val serverPlayer: ServerPlayer) : IPlayer(serverPlayer.scoreboardName, serverPlayer.uuid) {
    override fun hasPermission(permission: Permission): Boolean {
        return serverPlayer.hasPermission(permission)
    }

    override fun sendActionbar(value: MessageValue) {
        NeoforgeMessageService.instance.sendActionbar(this, value)
    }

    override fun sendMessage(value: MessageValue) {
        NeoforgeMessageService.instance.sendMessage(this, value)
    }
}