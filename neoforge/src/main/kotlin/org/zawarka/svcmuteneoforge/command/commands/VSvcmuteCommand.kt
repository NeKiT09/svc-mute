package org.zawarka.svcmuteneoforge.command.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.SharedSuggestionProvider
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.commands.SvcmuteCommand
import org.zawarka.svcmuteneoforge.utils.check
import org.zawarka.svcmuteneoforge.SvcMuteNeoforge.core
import org.zawarka.svcmuteneoforge.command.ICommand
import org.zawarka.svcmuteneoforge.data.commands.toNeoforgeCommandSender

class VSvcmuteCommand : ICommand {

    override fun literal(): LiteralArgumentBuilder<CommandSourceStack> {
        val svcMute = SvcmuteCommand(
            core.muteManager,
            core.messagesData
        )

        return Commands.literal("svcmute")
            .requires { source ->
                MutePermission.ADMIN_COMMAND.check(source)
            }
            .then(
                Commands.argument("arg", StringArgumentType.string())
                    .suggests { _, builder ->
                        SharedSuggestionProvider.suggest(
                            svcMute.tabComplete().getOrElse(0) { emptyList() },
                            builder
                        )
                    }
                    .executes { ctx ->
                        val success = svcMute.onCommand(
                            ctx.source.toNeoforgeCommandSender(),
                            listOf(StringArgumentType.getString(ctx, "arg"))
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