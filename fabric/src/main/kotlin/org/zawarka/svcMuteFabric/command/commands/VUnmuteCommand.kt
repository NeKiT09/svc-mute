package org.zawarka.svcMuteFabric.command.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.command.CommandSource
import net.minecraft.command.EntitySelector
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.commands.UnmuteCommand
import org.zawarka.svcMuteCore.data.command.ICommandExecutor
import org.zawarka.svcMuteCore.data.command.ITabCompleter
import org.zawarka.svcMuteFabric.SvcMuteFabric.Companion.instance
import org.zawarka.svcMuteFabric.command.ICommand
import org.zawarka.svcMuteFabric.data.FabricPlayerService
import org.zawarka.svcMuteFabric.data.commands.toFabricCommandSender
import org.zawarka.svcMuteFabric.utils.check

class VUnmuteCommand : ICommand {

    override fun literal(): LiteralArgumentBuilder<ServerCommandSource> {
        val svcUnmute = UnmuteCommand(instance.core.muteManager, instance.core.messageService, FabricPlayerService)

        return literal("vunmute").requires { source -> MutePermission.UNMUTE_COMMAND.check(source) }
            .then(CommandsBuilder.targetPlayerBuilder(svcUnmute, svcUnmute))

    }

}