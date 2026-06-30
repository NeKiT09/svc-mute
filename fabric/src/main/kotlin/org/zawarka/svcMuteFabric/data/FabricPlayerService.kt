package org.zawarka.svcMuteFabric.data

import net.minecraft.server.network.ServerPlayerEntity
import org.apache.logging.log4j.core.jmx.Server
import org.zawarka.svcMuteCore.data.IPlayer
import org.zawarka.svcMuteCore.data.IPlayerService
import org.zawarka.svcMuteFabric.SvcMuteFabric
import java.util.UUID

object FabricPlayerService : IPlayerService {
    override fun getPlayer(name: String): IPlayer? {
        return SvcMuteFabric.instance.server.playerManager.getPlayer(name)?.toFabricPlayer()
    }

    override fun getPlayer(uuid: UUID): IPlayer? {
        return SvcMuteFabric.instance.server.playerManager.getPlayer(uuid)?.toFabricPlayer()
    }

    override fun getOnlinePlayers(): List<IPlayer> {
        return SvcMuteFabric.instance.server.playerManager.playerList.map { FabricPlayer(it) }
    }
}