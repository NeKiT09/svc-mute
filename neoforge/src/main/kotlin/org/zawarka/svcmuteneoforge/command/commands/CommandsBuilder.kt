package org.zawarka.svcmuteneoforge.command.commands

import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.commands.arguments.EntityArgument
import org.zawarka.svcMuteCore.data.command.ICommandExecutor
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcmuteneoforge.data.commands.toNeoforgeCommandSender

object CommandsBuilder {

    fun targetPlayerBuilder(
        executor: ICommandExecutor,
        tabComplete: ITabCompleter
    ): RequiredArgumentBuilder<CommandSourceStack, *> {

        return Commands.argument("player", EntityArgument.player())
            .suggests { _, builder ->
                SharedSuggestionProvider.suggest(
                    tabComplete.tabComplete().getOrElse(0) { emptyList() },
                    builder
                )
            }
            .executes { context ->
                executor.onCommand(
                    context.source.toNeoforgeCommandSender(),
                    listOf(EntityArgument.getPlayer(context, "player").name.string)
                )
                1
            }
    }

}