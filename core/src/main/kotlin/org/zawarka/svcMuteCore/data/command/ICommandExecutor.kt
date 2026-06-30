package org.zawarka.svcMuteCore.data.command

interface ICommandExecutor {
    fun onCommand(sender: MuteCommandSender, args: List<String>) : Boolean
}