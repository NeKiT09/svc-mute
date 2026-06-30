package org.zawarka.svcMuteCore.data.command

import org.zawarka.svcMuteCore.data.IPlayer
import org.zawarka.svcMuteCore.messages.MessageValue
import java.util.UUID

abstract class MuteCommandSender(open val name: String) {
    abstract fun sendMessage(value: MessageValue)
}