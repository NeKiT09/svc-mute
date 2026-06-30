package org.zawarka.svcMuteFabric.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.ServerCommandSource
import org.zawarka.svcMuteFabric.command.commands.VMuteCommand
import org.zawarka.svcMuteFabric.command.commands.VMuteallCommand
import org.zawarka.svcMuteFabric.command.commands.VSvcmuteCommand
import org.zawarka.svcMuteFabric.command.commands.VUnmuteCommand

object CommandsRegister {

    val commands = listOf(VMuteCommand(), VUnmuteCommand(), VMuteallCommand(), VSvcmuteCommand())

    fun register() {
        commands.forEach {registerCommand(it.literal())}
    }

    private fun registerCommand(literal: LiteralArgumentBuilder<ServerCommandSource>) {
        CommandRegistrationCallback
            .EVENT
            .register { dispatcher, _, _ -> dispatcher.register(literal) }
    }
}