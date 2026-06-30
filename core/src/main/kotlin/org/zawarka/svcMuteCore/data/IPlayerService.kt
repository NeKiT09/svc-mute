package org.zawarka.svcMuteCore.data

import java.util.UUID

interface IPlayerService {
    fun getPlayer(name: String): IPlayer?
    fun getPlayer(uuid: UUID): IPlayer?
    fun getOnlinePlayers(): List<IPlayer>

}