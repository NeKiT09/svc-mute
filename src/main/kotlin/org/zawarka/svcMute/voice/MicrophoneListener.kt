package org.zawarka.svcMute.voice

import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.zawarka.svcMute.messages.MessageValue
import org.zawarka.svcMute.messages.config.MessageType
import org.zawarka.svcMute.messages.sendActionbar
import org.zawarka.svcMute.messages.sendMessage
import org.zawarka.svcMute.mute.MuteManager

class MicrophoneListener {

    fun onMicrophonePacket(event: MicrophonePacketEvent) {
        try {
            val connection = event.senderConnection ?: return

            val uuid = connection.player.uuid

            val player = Bukkit.getPlayer(uuid) ?: return

            if(player.hasPermission("svcmute.disable.voice")){
                event.cancel()

                player.sendActionbar(MessageValue(MessageType.ACTIONBAR_NOT_ALLOWED))
                return
            }

            if (MuteManager.isMuted(uuid)) {
                event.cancel()

                onCancel(player)
                return
            }

            if (MuteManager.isAllMuted) {
                if (!player.hasPermission("svcmute.muteall.allow.voice")) {
                    event.cancel()
                    player.sendActionbar(MessageValue(MessageType.ACTIONBAR_MUTEALL))
                    return
                }
            }
        } catch (t: Throwable) { t.printStackTrace() }
    }

    private fun onCancel(player: Player){
        val mute = MuteManager.getMute(player.uniqueId)
        player.sendActionbar(MessageValue(MessageType.ACTIONBAR_MUTE, time = mute?.remainSeconds()))
    }

}