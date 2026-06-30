package org.zawarka.svcMuteFabric.data

import me.lucko.fabric.api.permissions.v0.Permissions
import net.minecraft.server.network.ServerPlayerEntity
import org.zawarka.svcMuteCore.data.IPlayer
import org.zawarka.svcMuteCore.data.Permission
import org.zawarka.svcMuteCore.messages.MessageValue

fun ServerPlayerEntity.toFabricPlayer(): IPlayer {
    return FabricPlayer(this)
}

class FabricPlayer(val player: ServerPlayerEntity) : IPlayer(player.name.string, player.uuid) {
    override fun sendMessage(value: MessageValue) {
        player.sendMessage(value)
    }

    override fun sendActionbar(value: MessageValue) {
        player.sendActionbar(value)
    }

    override fun hasPermission(permission: Permission): Boolean {
        return Permissions.check(player, permission.node, permission.lvl)
    }
}