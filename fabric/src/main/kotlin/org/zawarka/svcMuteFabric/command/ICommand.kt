package org.zawarka.svcMuteFabric.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.server.command.ServerCommandSource

interface ICommand {

    fun literal() : LiteralArgumentBuilder<ServerCommandSource>

}