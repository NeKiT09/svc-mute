package org.zawarka.svcmuteneoforge.data

import org.zawarka.svcMuteCore.data.IPlayer
import org.zawarka.svcMuteCore.data.IPlayerService
import org.zawarka.svcmuteneoforge.SvcMuteNeoforge
import java.util.UUID

object NeoforgePlayerService : IPlayerService {
    override fun getPlayer(name: String): IPlayer? {
        return SvcMuteNeoforge.server?.playerList?.getPlayerByName(name)?.toNeoforgePlayer()
    }

    override fun getPlayer(uuid: UUID): IPlayer? {
        return SvcMuteNeoforge.server?.playerList?.getPlayer(uuid)?.toNeoforgePlayer()
    }

    override fun getOnlinePlayers(): List<IPlayer> {
        return SvcMuteNeoforge.server?.playerList?.players?.map { it.toNeoforgePlayer() } ?: emptyList()
    }
}