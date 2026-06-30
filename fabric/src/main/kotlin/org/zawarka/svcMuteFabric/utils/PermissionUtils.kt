package org.zawarka.svcMuteFabric.utils

import me.lucko.fabric.api.permissions.v0.Permissions
import net.minecraft.command.CommandSource
import net.minecraft.server.network.ServerPlayerEntity
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.Permission


fun CommandSource.hasPermission(permission: MutePermission): Boolean {
    return this.hasPermission(permission.permission)
}

fun CommandSource.hasPermission(permission: Permission): Boolean {
    return Permissions.check(this, permission.node, permission.lvl)
}

fun MutePermission.check(source: CommandSource): Boolean {
    return source.hasPermission(permission)
}

fun ServerPlayerEntity.hasPermission(permission: MutePermission): Boolean {
    return this.hasPermission(permission.permission)
}

fun ServerPlayerEntity.hasPermission(permission: Permission): Boolean {
    return Permissions.check(this, permission.node, permission.lvl)
}

fun MutePermission.check(source: ServerPlayerEntity): Boolean {
    return source.hasPermission(permission)
}