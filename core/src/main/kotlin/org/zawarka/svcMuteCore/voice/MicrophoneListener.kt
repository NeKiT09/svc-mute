package org.zawarka.svcMuteCore.voice

import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.IPlayerService
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcMuteCore.messages.config.MessageType
import org.zawarka.svcMuteCore.mute.MuteManager

class MicrophoneListener(val muteManager: MuteManager, val playerService: IPlayerService) {

    fun onMicrophonePacket(event: MicrophonePacketEvent) {
        try {
            val connection = event.senderConnection ?: return

            val uuid = connection.player.uuid

            val player = playerService.getPlayer(uuid) ?: return

            if(!player.hasPermission(MutePermission.ENABLE_VOICE)){
                event.cancel()

                player.sendActionbar(MessageValue(MessageType.ACTIONBAR_NOT_ALLOWED))
                return
            }

            val container = muteManager.mutes;

            if (container.isMuted(uuid)) {
                event.cancel()

                val mute = container.getMute(player.uniqueId)!!
                if(mute.isExpired()){
                    container.removeMute(uuid)
                }else {
                    player.sendActionbar(MessageValue(MessageType.ACTIONBAR_MUTE, time = mute.remainSeconds()))
                    return
                }
            }

//            val groupContainer = muteManager.groupMutes;
//
//            val group = connection.group
//
//            if(group != null) {
//                if (groupContainer.isMuted(uuid)) {
//                    event.cancel()
//
//                    val mute = groupContainer.getMute(group.id)!!
//                    if (mute.isExpired()) {
//                        groupContainer.removeMute(uuid)
//                    } else {
//                        player.sendActionbar(
//                            MessageValue(
//                                MessageType.ACTIONBAR_GROUP_MUTE,
//                                time = mute.remainSeconds()
//                            )
//                        )
//                        return
//                    }
//                }
//            }

            if (muteManager.isAllMuted) {
                if (!player.hasPermission(MutePermission.MUTEALL_ALLOW_VOICE)) {
                    event.cancel()
                    player.sendActionbar(MessageValue(MessageType.ACTIONBAR_MUTEALL))
                    return
                }
            }
        } catch (t: Throwable) { t.printStackTrace() }
    }
}