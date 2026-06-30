package org.zawarka.svcMute.data.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcMuteCore.utils.startWith

class TabCompleterPaper(val completer: ITabCompleter) : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): List<String?> {
        val id = (args?.size ?: 1) - 1

        return completer.tabComplete().getOrNull(id)?.let {
            it.startWith(args?.getOrNull(id) ?: return@let it)
        } ?: emptyList()
    }

}