package org.zawarka.svcmuteneoforge.utils

import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.server.permission.PermissionAPI
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.Permission
import org.zawarka.svcmuteneoforge.data.NeoForgePermissionRegistry

fun CommandSourceStack.hasPermission(permission: MutePermission): Boolean {
    return hasPermission(permission.permission)
}

fun CommandSourceStack.hasPermission(permission: Permission): Boolean {
    val player = player ?: return hasPermission(permission.lvl)
    return PermissionAPI.getPermission(player, NeoForgePermissionRegistry.node(permission))
}

fun MutePermission.check(source: CommandSourceStack): Boolean {
    return source.hasPermission(permission)
}

fun ServerPlayer.hasPermission(permission: MutePermission): Boolean {
    return hasPermission(permission.permission)
}

fun ServerPlayer.hasPermission(permission: Permission): Boolean {
    return PermissionAPI.getPermission(this, NeoForgePermissionRegistry.node(permission))
}

fun MutePermission.check(source: ServerPlayer): Boolean {
    return source.hasPermission(permission)
}