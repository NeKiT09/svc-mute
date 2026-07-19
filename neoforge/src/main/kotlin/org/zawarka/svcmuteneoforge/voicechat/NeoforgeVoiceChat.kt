package org.zawarka.svcmuteneoforge.voicechat

import de.maxhenkel.voicechat.api.ForgeVoicechatPlugin
import org.zawarka.svcMuteCore.voice.VoiceChat
import org.zawarka.svcmuteneoforge.SvcMuteNeoforge
import org.zawarka.svcmuteneoforge.data.NeoforgePlayerService

@ForgeVoicechatPlugin
class NeoforgeVoiceChat : VoiceChat() {
    fun init(){
        init(SvcMuteNeoforge.core, NeoforgePlayerService)
    }
}