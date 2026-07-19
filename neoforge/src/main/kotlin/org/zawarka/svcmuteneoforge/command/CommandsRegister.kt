package org.zawarka.svcmuteneoforge.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.RegisterCommandsEvent
import org.zawarka.svcmuteneoforge.command.commands.VMuteCommand
import org.zawarka.svcmuteneoforge.command.commands.VMuteallCommand
import org.zawarka.svcmuteneoforge.command.commands.VSvcmuteCommand
import org.zawarka.svcmuteneoforge.command.commands.VUnmuteCommand

object CommandsRegister {

    val commands = listOf(VMuteCommand(), VUnmuteCommand(), VMuteallCommand(), VSvcmuteCommand())

    fun register() {
        NeoForge.EVENT_BUS.addListener<RegisterCommandsEvent> { event ->
            run {
                commands.forEach { registerCommand(it.literal(), event.dispatcher) }
            }
        }
    }

    private fun registerCommand(literal: LiteralArgumentBuilder<CommandSourceStack>, dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(literal)
    }
}