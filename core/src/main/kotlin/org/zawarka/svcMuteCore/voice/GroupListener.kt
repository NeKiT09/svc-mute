package org.zawarka.svcMuteCore.voice

import de.maxhenkel.voicechat.api.events.CreateGroupEvent
import de.maxhenkel.voicechat.api.events.RemoveGroupEvent

class GroupListener {


    fun onGroupCreated(e: CreateGroupEvent){
        if(e.isCancelled) return

        val group = e.group ?: return

        VoiceChat.groupStorage.addGroup(group)
    }

    fun onGroupRemoved(e: RemoveGroupEvent){
        if(e.isCancelled) return

        val group = e.group ?: return

        VoiceChat.groupStorage.removeGroup(group)
    }

}