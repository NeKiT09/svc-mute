package org.zawarka.svcMute.messages

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.zawarka.svcMute.utils.mm
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.messages.MessageService
import org.zawarka.svcMuteCore.messages.MessageValue
import org.zawarka.svcMuteCore.messages.config.MessagesData
import org.zawarka.svcMuteCore.utils.time.TimeConfigFormatter

fun CommandSender.sendMessage(value: MessageValue){
    PaperMessageService.instance.sendMessage(this, value)
}

fun Player.sendActionbar(value: MessageValue){
    PaperMessageService.instance.sendActionbar(this, value)
}

class PaperMessageService() : MessageService() {
    companion object {
        lateinit var instance: MessageService
    }

    init {
        instance = this
    }

    override fun sendMessage(player: Any, values: MessageValue){
        getFormatedMessage(values)?.let { (player as CommandSender).sendMessage(it) }
    }

    override fun sendActionbar(player: Any, values: MessageValue){
        getFormatedMessage(values)?.let { (player as Player).sendActionBar(it) }
    }

    override fun sendGlobalMessage(
        value: MessageValue,
        permission: MutePermission
    ) {
        Bukkit.getOnlinePlayers().forEach {
            if(it.hasPermission(permission.node)) {
                it.sendMessage(value)
            }
        }
    }

    fun getFormatedMessage(values: MessageValue): Component?{
        val message = messagesData.getMessageString(values.type)

        if(message.isEmpty()) return null

        return putValues(message, values).mm()
    }

}