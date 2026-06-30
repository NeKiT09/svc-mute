package org.zawarka.svcMuteCore.messages

import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.Permission
import org.zawarka.svcMuteCore.messages.config.MessageType
import org.zawarka.svcMuteCore.messages.config.MessagesData
import org.zawarka.svcMuteCore.utils.time.TimeConfigFormatter

abstract class MessageService() {

    protected lateinit var messagesData: MessagesData

    fun init(messagesData: MessagesData){
        this.messagesData = messagesData
    }

    abstract fun sendMessage(player: Any, values: MessageValue)

    abstract fun sendActionbar(player: Any, values: MessageValue)

    abstract fun sendGlobalMessage(value: MessageValue, permission: MutePermission = MutePermission.EMPTY)

    protected fun putValues(text: String, values: MessageValue): String {
        return text
            .replace("{sender}", values.sender ?: "{sender}")
            .replace("{target}", values.target ?: "{target}")
            .replace("{reason}", values.reason ?: "{reason}")
            .replace("{time}", values.time?.let(TimeConfigFormatter.instance::format) ?: "-")
            .replace("{prefix}", messagesData.getMessageString(
                MessageType.PLUGIN_PREFIX))
    }
}