package org.zawarka.svcMute.messages

enum class ConfigMessagePath(val path: String) {
    PLUGIN_PREFIX("prefix"),

    ACTIONBAR_MUTE("actionbar_mute_msg"),

    PLAYER_MUTED("player_muted_msg"),
    PLAYER_UNMUTED("player_unmuted_msg"),
    
    ;
}