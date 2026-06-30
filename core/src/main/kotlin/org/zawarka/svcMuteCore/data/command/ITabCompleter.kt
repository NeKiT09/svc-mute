package org.zawarka.svcMuteCore.data.command

interface ITabCompleter {
    fun tabComplete(
    ): List<List<String>>
}