package org.zawarka.svcmuteneoforge.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack

interface ICommand {

    fun literal() : LiteralArgumentBuilder<CommandSourceStack>

}