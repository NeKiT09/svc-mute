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
        getFormatedMessage(values)?.let { player.sendMessage(it) }
    }

    fun sendActionbar(player: Player, values: MessageValue){
        getFormatedMessage(values)?.let { player.sendActionBar(it) }
    }

    fun getFormatedMessage(values: MessageValue): Component?{
        val message = MessagesData.getMessageString(values.type)

        if(message.isEmpty()) return null

        return putValues(message, values).mm()
    }

    private fun putValues(text: String, values: MessageValue): String {
        return text
            .replace("{sender}", values.sender?.name ?: "CONSOLE")
            .replace("{target}", values.target ?: "{target}")
            .replace("{reason}", values.reason ?: "{reason}")
            .replace("{time}", values.time?.let(TimeConfigFormatter.instance::format) ?: "-")
            .replace("{prefix}", MessagesData.getMessageString(MessageType.PLUGIN_PREFIX))
    }

//    private fun putValues(text: String, values: MessageValue) : String{
//        val builder = StringBuilder(text)
//
//        builder.replaceWith("sender", values.sender?.name ?: "CONSOLE")
//
//        values.target?.apply {
//            builder.replaceWith("target", this)
//        }
//
//        values.reason?.apply {
//            builder.replaceWith("reason", this)
//        }
//
//        values.time.apply {
//            builder.replaceWith("time", if(this != null) TimeConfigFormatter.instance.format(this) else "-")
//        }
//
//        builder.replaceWith("prefix", MessagesData.getMessageString(MessageType.PLUGIN_PREFIX))
//
//        return builder.toString()
//    }

    private fun StringBuilder.replaceWith(id: String, replacement: String){
        replace(Regex("\\{$id}"), replacement)

        //val regex = Regex("""\{$id\}""")

        //println(replacement)
        //println(regex.toString())

        //this.replace(regex, replacement)
    }


}