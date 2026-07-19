package org.zawarka.svcmuteneoforge.command.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.commands.UnmuteCommand
import org.zawarka.svcmuteneoforge.utils.check
import org.zawarka.svcmuteneoforge.SvcMuteNeoforge.core
import org.zawarka.svcmuteneoforge.command.ICommand
import org.zawarka.svcmuteneoforge.data.NeoforgePlayerService


class VUnmuteCommand : ICommand {

    override fun literal(): LiteralArgumentBuilder<CommandSourceStack> {
        val svcUnmute = UnmuteCommand(
            core.muteManager.mutes,
            core.messageService,
            NeoforgePlayerService
        )

        return Commands.literal("vunmute")
            .requires { source ->
                MutePermission.UNMUTE_COMMAND.check(source)
            }
            .then(
                CommandsBuilder.targetPlayerBuilder(
                    svcUnmute,
                    svcUnmute
                )
            )
    }

}