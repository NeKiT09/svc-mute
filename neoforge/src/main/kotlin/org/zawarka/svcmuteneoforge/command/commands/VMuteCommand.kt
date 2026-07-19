package org.zawarka.svcmuteneoforge.command.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.commands.MuteCommand
import org.zawarka.svcmuteneoforge.utils.check
import org.zawarka.svcmuteneoforge.SvcMuteNeoforge.core
import org.zawarka.svcmuteneoforge.command.ICommand
import org.zawarka.svcmuteneoforge.data.NeoforgePlayerService
import org.zawarka.svcmuteneoforge.data.commands.toNeoforgeCommandSender

class VMuteCommand : ICommand {

    override fun literal(): LiteralArgumentBuilder<CommandSourceStack> {
        val svcMute = MuteCommand(
            core.muteManager.mutes,
            core.messageService,
            NeoforgePlayerService,
            core.messagesData
        )

        return Commands.literal("vmute")
            .requires { source ->
                MutePermission.MUTE_COMMAND.check(source)
            }
            .then(
                CommandsBuilder.targetPlayerBuilder(svcMute, svcMute)
                    .then(
                        Commands.argument("time/reason", StringArgumentType.greedyString())
                            .executes { context ->
                                svcMute.onCommand(
                                    context.source.toNeoforgeCommandSender(),
                                    listOf(
                                        EntityArgument.getPlayer(context, "player").name.string,
                                        StringArgumentType.getString(context, "time/reason")
                                    )
                                )
                                1
                            }
                    )
            )
    }
}