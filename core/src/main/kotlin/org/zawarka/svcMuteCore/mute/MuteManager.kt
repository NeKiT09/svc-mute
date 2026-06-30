package org.zawarka.svcMuteCore.mute

import java.util.UUID

class MuteManager () {
    lateinit var storage: MuteStorage
    fun init(storage: MuteStorage) {
        this.storage = storage
    }

    var isAllMuted = false
    private set

    fun muteAll(){
        isAllMuted = true
    }

    fun unMuteAll(){
        isAllMuted = false
    }

    private val mutes = mutableMapOf<UUID, MuteInfo>()

    fun replaceAll(mutes: Collection<MuteInfo>, allMuted: Boolean) {
        this.mutes.clear()
        for (info in mutes) {
            this.mutes[info.uuid] = info
        }
        isAllMuted = allMuted
    }

    fun clear() {
        mutes.clear()
        isAllMuted = false
    }

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
        //storage.saveMute(info)
    }

    fun removeMute(uuid: UUID) {
        mutes.remove(uuid)
        storage.removeMute(uuid)
    }

    fun getMute(uuid: UUID) = mutes[uuid]

    fun allMutes() = mutes.keys

}