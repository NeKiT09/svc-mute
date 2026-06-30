package org.zawarka.svcMuteCore.messages

import org.zawarka.svcMuteCore.messages.config.MessageType

data class MessageValue(val type: org.zawarka.svcMuteCore.messages.config.MessageType, val sender: String? = null, val target: String? = null, val reason: String? = null, val time: Long? = null)
