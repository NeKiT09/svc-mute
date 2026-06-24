package org.zawarka.svcMute.messages

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.zawarka.svcMute.messages.config.MessageType

data class MessageValue(val type: MessageType, val sender: CommandSender? = null, val target: String? = null, val reason: String? = null, val time: Long? = null)
