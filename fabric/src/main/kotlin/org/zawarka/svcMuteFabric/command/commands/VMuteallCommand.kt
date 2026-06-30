package org.zawarka.svcMuteFabric.command.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.command.CommandSource
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.commands.MuteAllCommand
import org.zawarka.svcMuteFabric.SvcMuteFabric.Companion.instance
import org.zawarka.svcMuteFabric.command.ICommand
import org.zawarka.svcMuteFabric.data.commands.toFabricCommandSender
import org.zawarka.svcMuteFabric.utils.check

class VMuteallCommand : ICommand {

    override fun literal(): LiteralArgumentBuilder<ServerCommandSource> {
        val svcMuteall = MuteAllCommand(instance.core.muteManager, instance.core.messageService)

        return literal("vmuteall").requires { source -> MutePermission.MUTEALL_COMMAND.check(source) }
            .then(
                CommandManager
                    .argument("mode", StringArgumentType.string())
                    .suggests { context, builder ->
                        CommandSource
                            .suggestMatching(
                                svcMuteall.tabComplete().getOrElse(0) { return@getOrElse emptyList() },
                                builder
                            )
                    }.executes { ctx ->
                        svcMuteall.onCommand(ctx.source.toFabricCommandSender(), listOf(StringArgumentType.getString(ctx, "mode")))
                            .compareTo(false)
                            .let { if(it == 0) throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect().create("") else 1 }
                    })

    }

}