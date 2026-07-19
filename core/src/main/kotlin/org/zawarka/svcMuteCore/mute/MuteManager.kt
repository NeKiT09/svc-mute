package org.zawarka.svcMuteCore.mute

import java.util.UUID

class MuteManager () {
    lateinit var storage: MuteStorage
    lateinit var groupStorage: MuteStorage

    val mutes: MuteContainer = MuteContainer()

    fun init(storage: MuteStorage, groupStorage: MuteStorage) {
        this.storage = storage
        this.groupStorage = groupStorage

        mutes.init(storage)
    }

    var isAllMuted = false
    private set

    fun muteAll(mute: Boolean = true){
        isAllMuted = mute
    }

    fun unMuteAll(){
        isAllMuted = false
    }


    //val groupMutes = MuteContainer(groupStorage)
}