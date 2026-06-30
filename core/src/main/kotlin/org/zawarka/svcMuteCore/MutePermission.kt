package org.zawarka.svcMuteCore

import org.zawarka.svcMuteCore.data.Permission

enum class MutePermission(val permission: Permission) {
    ENABLE_VOICE(Permission("svcmute.enable.voice", 0)),

    MUTE_COMMAND(Permission("svcmute.mute.command", 2)),
    MUTE_MSG_SEE(Permission("svcmute.mute.msg.see", 0)),

    UNMUTE_COMMAND(Permission("svcmute.unmute.command", 2)),
    UNMUTE_MSG_SEE(Permission("svcmute.unmute.msg.see", 0)),

    MUTEALL_COMMAND(Permission("svcmute.muteall.command", 2)),
    MUTEALL_ALLOW_VOICE(Permission("svcmute.muteall.allow.voice", 2)),
    MUTEALL_MSG_SEE(Permission("svcmute.muteall.msg.see", 0)),

    ADMIN_COMMAND(Permission("svcmute.admin.command", 2)),

    EMPTY(Permission("", 0));

    val node = permission.node
    val lvl = permission.lvl
}