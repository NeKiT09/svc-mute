package org.zawarka.svcMuteFabric.command.commands

import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.command.CommandSource
import net.minecraft.command.EntitySelector
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import org.zawarka.svcMuteCore.data.command.ICommandExecutor
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcMuteFabric.data.commands.toFabricCommandSender

object CommandsBuilder {

    fun targetPlayerBuilder(executor: ICommandExecutor, tabComplete: ITabCompleter) : RequiredArgumentBuilder<ServerCommandSource?, EntitySelector> {
        return CommandManager.argument("player", EntityArgumentType.player())
            .suggests { context, builder ->
                CommandSource.suggestMatching(
                    tabComplete.tabComplete().getOrElse(0, { return@getOrElse emptyList() }),
                    builder
                )
            }
            .executes { context ->
                executor.onCommand(
                    context.source.toFabricCommandSender(),
                    listOf(EntityArgumentType.getPlayer(context, "player").name.string)
                )
                1
            }

    }

}