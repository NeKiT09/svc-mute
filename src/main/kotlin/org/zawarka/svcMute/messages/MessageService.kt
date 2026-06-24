package org.zawarka.svcMute.messages

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.zawarka.svcMute.messages.config.MessageType
import org.zawarka.svcMute.messages.config.MessagesData
import org.zawarka.svcMute.utils.mm
import org.zawarka.svcMute.utils.time.TimeConfigFormatter

fun CommandSender.sendMessage(value: MessageValue){
    MessageService.sendMessage(this, value)
}

fun Player.sendActionbar(value: MessageValue){
    MessageService.sendActionbar(this, value)
}

fun sendGlobalMessage(message: MessageValue, permission: String = ""){
    Bukkit.getOnlinePlayers().forEach {
        if(it.hasPermission(permission)) {
            it.sendMessage(message)
        }
    }
}

object MessageService {

    fun sendMessage(player: CommandSender, values: MessageValue){
        player.sendMessage { getFormatedMessage(values) }
    }

    fun sendActionbar(player: Player, values: MessageValue){
        player.sendActionBar { getFormatedMessage(values) }
    }

    fun getFormatedMessage(values: MessageValue): Component{
        val message = MessagesData.getMessageString(MessageType.PLUGIN_PREFIX) + MessagesData.getMessageString(values.type)

        if(message.isEmpty()) return Component.empty()

        return putValues(message, values).mm()
    }

    private fun putValues(text: String, values: MessageValue) : String{
        val builder = StringBuilder(text)

        builder.replaceWith("sender", values.sender?.name ?: "CONSOLE")

        values.target?.apply {
            builder.replaceWith("target", this)
        }

        values.reason?.apply {
            builder.replaceWith("reason", this)
        }

        values.time.apply {
            builder.replaceWith("time", if(this != null) TimeConfigFormatter.instance.format(this) else "-")
        }

        return builder.toString()
    }

    private fun StringBuilder.replaceWith(id: String, replacement: String){
        this.replace(("{$id}").toRegex(), replacement)
    }


}