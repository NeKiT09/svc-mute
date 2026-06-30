package org.zawarka.svcMute.data.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.zawarka.svcMuteCore.data.command.ICommandExecutor

class CommandExecutorPaper(val executor: ICommandExecutor) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        return executor.onCommand(sender.toPaperSender(), args?.toList() ?: emptyList())
    }
}