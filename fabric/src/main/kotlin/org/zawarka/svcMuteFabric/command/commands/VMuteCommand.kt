package org.zawarka.svcMuteFabric.command.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandSource
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.commands.MuteCommand
import org.zawarka.svcMuteFabric.SvcMuteFabric.Companion.instance
import org.zawarka.svcMuteFabric.command.ICommand
import org.zawarka.svcMuteFabric.data.FabricPlayerService
import org.zawarka.svcMuteFabric.data.commands.toFabricCommandSender
import org.zawarka.svcMuteFabric.utils.check

class VMuteCommand : ICommand {

    override fun literal(): LiteralArgumentBuilder<ServerCommandSource> {
        val svcMute = MuteCommand(instance.core.muteManager.mutes, instance.core.messageService, FabricPlayerService, instance.core.messagesData)

        return literal("vmute").requires { source -> MutePermission.MUTE_COMMAND.check(source) }
            .then(CommandsBuilder.targetPlayerBuilder(svcMute, svcMute)
                    .then(
                        CommandManager
                            .argument("time/reason", StringArgumentType.greedyString())
                            .executes { context ->
                                svcMute.onCommand(
                                    context.source.toFabricCommandSender(),
                                    listOf(EntityArgumentType.getPlayer(context, "player").name.string, StringArgumentType.getString(context, "time/reason"))
                                )
                                1
                            })
            )

    }
}