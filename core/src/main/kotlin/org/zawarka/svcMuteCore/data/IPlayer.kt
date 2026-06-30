package org.zawarka.svcMuteCore.data

import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.command.MuteCommandSender
import org.zawarka.svcMuteCore.messages.MessageValue
import java.util.UUID

abstract class IPlayer(override val name: String, val uniqueId: UUID) : MuteCommandSender(name) {
    abstract fun hasPermission(permission: Permission): Boolean

    fun hasPermission(permission: MutePermission): Boolean{
        return hasPermission(permission.permission)
    }

    abstract fun sendActionbar(value: MessageValue)
}