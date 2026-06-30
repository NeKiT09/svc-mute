package org.zawarka.svcMuteFabric.command.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.command.CommandSource
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.commands.SvcmuteCommand
import org.zawarka.svcMuteFabric.SvcMuteFabric.Companion.instance
import org.zawarka.svcMuteFabric.command.ICommand
import org.zawarka.svcMuteFabric.data.commands.toFabricCommandSender
import org.zawarka.svcMuteFabric.utils.check

class VSvcmuteCommand : ICommand {

    override fun literal(): LiteralArgumentBuilder<ServerCommandSource> {
        val svcMute = SvcmuteCommand(instance.core.muteManager, instance.core.messagesData)

        return literal("svcmute").requires { source -> MutePermission.ADMIN_COMMAND.check(source) }
            .then(
                CommandManager
                    .argument("arg", StringArgumentType.string())
                    .suggests { context, builder ->
                        CommandSource
                            .suggestMatching(
                                svcMute.tabComplete().getOrElse(0) { return@getOrElse emptyList() },
                                builder
                            )
                    }.executes { ctx ->
                        svcMute.onCommand(ctx.source.toFabricCommandSender(), listOf(StringArgumentType.getString(ctx, "arg")))
                            .compareTo(false)
                            .let { if(it == 0) throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect().create("") else 1 }
                    })

    }

}