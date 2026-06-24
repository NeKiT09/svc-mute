package org.zawarka.svcMute.mute

import java.util.UUID

class MuteListManager {

    private val mutes = mutableMapOf<UUID, MuteInfo>()

    fun isMuted(uuid: UUID) = mutes.containsKey(uuid)

    /**
     * Checks if mute is expired, and removes it if true
     */
    fun isMutedWithExpireCheck(uuid: UUID): Boolean {
        val info = getMute(uuid) ?: return false

        if(info.isExpired()){
            removeMute(uuid)
            return false
        }

        return true
    }

    fun addMute(info: MuteInfo) {
        mutes[info.uuid] = info
    }

    fun removeMute(uuid: UUID) {
        mutes.remove(uuid)
    }

    fun getMute(uuid: UUID) = mutes[uuid]

    fun allMutes() = mutes.keys

}