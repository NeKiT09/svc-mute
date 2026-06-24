package org.zawarka.svcMute.messages

import net.kyori.adventure.audience.MessageType

class MessagesData {

    private val messages = mutableMapOf<MessageType, String>()

    fun getMessageString(key: MessageType) = messages.getOrDefault(key, "")
}