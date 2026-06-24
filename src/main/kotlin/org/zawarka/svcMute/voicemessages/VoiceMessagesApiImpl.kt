package org.zawarka.svcMute.voicemessages

import org.bukkit.Bukkit
import org.zawarka.svcMute.messages.MessageValue
import org.zawarka.svcMute.messages.config.MessageType
import org.zawarka.svcMute.messages.sendMessage
import org.zawarka.svcMute.mute.MuteManager
import ru.dimaskama.voicemessages.api.VoiceMessagesApi
import java.util.*


class VoiceMessagesApiImpl(val api : VoiceMessagesApi = VoiceMessagesApiImpl()) : VoiceMessagesApi {
    override fun isPlayerHasCompatibleModVersion(playerUuid: UUID?): Boolean {
        return api.isPlayerHasCompatibleModVersion(playerUuid)
    }

    override fun updateAvailableTargets(playerUuid: UUID): Boolean {
        return api.updateAvailableTargets(playerUuid)
    }

    override fun sendVoiceMessage(
        senderUuid: UUID?,
        playerUuids: Iterable<UUID>,
        message: MutableList<ByteArray?>?,
        displayTarget: String?
    ) {
        if(senderUuid != null && MuteManager.isMuted(senderUuid)){
            val player = Bukkit.getPlayer(senderUuid) ?: return
            val mute = MuteManager.getMute(senderUuid)

            player.sendMessage(MessageValue(MessageType.ACTIONBAR_MUTE, time = mute?.remainSeconds()))

            return
        }
        api.sendVoiceMessage(senderUuid, playerUuids, message, displayTarget)
    }
}