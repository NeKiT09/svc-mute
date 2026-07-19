package org.zawarka.svcmuteneoforge.command.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.SharedSuggestionProvider
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.commands.MuteAllCommand
import org.zawarka.svcmuteneoforge.utils.check
import org.zawarka.svcmuteneoforge.SvcMuteNeoforge.core
import org.zawarka.svcmuteneoforge.command.ICommand
import org.zawarka.svcmuteneoforge.data.commands.toNeoforgeCommandSender

class VMuteallCommand : ICommand {

    override fun literal(): LiteralArgumentBuilder<CommandSourceStack> {
        val svcMuteall = MuteAllCommand(core.muteManager, core.messageService)

        return Commands.literal("vmuteall")
            .requires { source ->
                MutePermission.MUTEALL_COMMAND.check(source)
            }
            .then(
                Commands.argument("mode", StringArgumentType.string())
                    .suggests { _, builder ->
                        SharedSuggestionProvider.suggest(
                            svcMuteall.tabComplete().getOrElse(0) { emptyList() },
                            builder
                        )
                    }
                    .executes { ctx ->
                        val success = svcMuteall.onCommand(
                            ctx.source.toNeoforgeCommandSender(),
                            listOf(StringArgumentType.getString(ctx, "mode"))
                        )

                        if (!success) {
                            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS
                                .literalIncorrect()
                                .create("")
                        }

                        1
                    }
            )
    }

}