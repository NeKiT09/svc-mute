package org.zawarka.svcMute.data

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.zawarka.svcMute.messages.sendActionbar
import org.zawarka.svcMute.messages.sendMessage
import org.zawarka.svcMuteCore.data.IPlayer
import org.zawarka.svcMuteCore.data.Permission
import org.zawarka.svcMuteCore.messages.MessageValue
import java.util.UUID

fun OfflinePlayer.toPaperPlayer() : PaperPlayer{
    return PaperPlayer(this)
}

class PaperPlayer(val player: OfflinePlayer) : IPlayer(player.name ?: "null", player.uniqueId) {
    override fun sendMessage(value: MessageValue) {
        Bukkit.getPlayer(player.uniqueId)?.sendMessage(value)
    }

    override fun sendActionbar(value: MessageValue) {
        Bukkit.getPlayer(player.uniqueId)?.sendActionbar(value)
    }

    override fun hasPermission(permission: Permission): Boolean {
        return player.player?.hasPermission(permission.node) ?: return false
    }
}