package org.zawarka.svcMute.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.zawarka.svcMute.messages.MessageValue
import org.zawarka.svcMute.messages.config.MessageType
import org.zawarka.svcMute.messages.config.MessagesData
import org.zawarka.svcMute.messages.sendMessage
import org.zawarka.svcMute.utils.startWith

class SvcmuteCommand : CommandExecutor, TabCompleter {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): Boolean {

        if(args.isNullOrEmpty()) {
            return false
        }

        if(args[0] == "reload"){
            MessagesData.loadFromConfig()
            sender.sendMessage(MessageValue(MessageType.CONFIG_RELOAD_MSG))
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): List<String> {
        val l = args?.size ?: 0

        val list = listOf("reload")

        if(l == 0){
            return list
        }else if(l == 1){
            return list.startWith(args!![0])
        }

        return emptyList()
    }
}