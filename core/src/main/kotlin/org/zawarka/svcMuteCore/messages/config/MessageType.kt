package org.zawarka.svcMuteCore.messages.config

enum class MessageType(val path: String) {
    PLUGIN_PREFIX("prefix"),

    ACTIONBAR_MUTE("actionbar_mute_msg"),
    ACTIONBAR_GROUP_MUTE("actionbar_group_mute_msg"),
    ACTIONBAR_MUTEALL("actionbar_muteall_msg"),
    ACTIONBAR_NOT_ALLOWED("actionbar_not_allowed_msg"),

    TIME_PERMANENT("time_permanent"),
    TIME_YEAR("time_year"),
    TIME_MONTH("time_month"),
    TIME_WEEK("time_week"),
    TIME_DAY("time_day"),
    TIME_HOUR("time_hour"),
    TIME_MINUTE("time_minute"),
    TIME_SECOND("time_second"),

    REASON_EMPTY("reason_empty"),

    CONFIG_RELOAD_MSG("config_reload_msg"),

    MUTE_GLOBAL_MESSAGE("mute_msg"),
    MUTE_GLOBAL_MESSAGE_PERMANENT("mute_msg_permanent"),
    MUTE_PLAYER_MESSAGE("mute_player_msg"),

    GROUP_NOT_EXIST_MESSAGE("group_not_exist_msg"),

    MUTE_GROUP_GLOBAL_MESSAGE("mute_group_msg"),
    MUTE_GROUP_GLOBAL_MESSAGE_PERMANENT("mute_group_msg_permanent"),

    MUTE_COMMAND_ALREADY_MUTED("mute_command_already_muted"),

    UNMUTE_GLOBAL_MESSAGE("unmute_global_msg"),
    UNMUTE_PLAYER_MESSAGE("unmute_player_msg"),

    UNMUTE_COMMAND_ALREADY_UNMUTED("unmute_command_already_unmuted"),

    MUTEALL_COMMAND_GLOBAL_MESSAGE_ON("muteall_command_global_msg_on"),
    MUTEALL_COMMAND_GLOBAL_MESSAGE_OFF("muteall_command_global_msg_off"),
    ;

}