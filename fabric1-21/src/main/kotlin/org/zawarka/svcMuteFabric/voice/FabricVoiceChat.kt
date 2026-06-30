package org.zawarka.svcMuteFabric.voice

import org.zawarka.svcMuteCore.SvcMuteCore
import org.zawarka.svcMuteCore.data.IPlayerService
import org.zawarka.svcMuteCore.voice.VoiceChat
import org.zawarka.svcMuteFabric.SvcMuteFabric
import org.zawarka.svcMuteFabric.data.FabricPlayerService

class FabricVoiceChat : VoiceChat() {
    init {
        init(SvcMuteFabric.instance.core, FabricPlayerService)
    }

}