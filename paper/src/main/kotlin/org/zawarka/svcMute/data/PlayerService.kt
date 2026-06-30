package org.zawarka.svcMute.data

import org.bukkit.Bukkit
import org.zawarka.svcMuteCore.data.IPlayer
import org.zawarka.svcMuteCore.data.IPlayerService
import java.util.UUID

object PlayerService : IPlayerService {
    override fun getPlayer(name: String): IPlayer {
        return Bukkit.getOfflinePlayer(name).toPaperPlayer()
    }

    override fun getPlayer(uuid: UUID): IPlayer {
        return Bukkit.getOfflinePlayer(uuid).toPaperPlayer()
    }

    override fun getOnlinePlayers(): List<IPlayer> {
        return Bukkit.getOnlinePlayers().map { it.toPaperPlayer() }
    }
}