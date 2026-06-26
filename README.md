# SVC-Mute
Addon for Simple Voice Chat plugin, that allows you to mute players' voice.

Requires [Simple Voice Chat](https://modrinth.com/plugin/simple-voice-chat) installed.

# Commands
|Command|Definition|Permission
|-|-|-
|/vmute </player> </time> </reason>|Mute player's voice chat|svcmute.mute.command
|/vunmute </player>|Unmute muted player|svcmute.unmute.command
|/vmuteall <on/off>|Enable/disable "muteall" mode, when nobody can use voice chat|svcmute.muteall.command
|/svcmute </reload>|Reload plugin's config|svcmute.admin.command

# Permissions
### Mute
|Permission|Definition|Default
|-|-|-
|svcmute.mute.command|/vmute|OP
|svcmute.mute.msg.see|Allows to see mute global notifications|true|
### Unmute
|Permission|Definition|Default
|-|-|-
|svcmute.unmute.command|/vunmute|OP
|svcmute.unmute.msg.see|Allows to see unmute global notifications|true
### Muteall
|Permission|Definition|Default
|-|-|-
|svcmute.muteall.command|/vmuteall| OP
|svcmute.muteall.allow.voice|Access to voice chat while "muteall" is enabled|OP
|svcmute.muteall.msg.see|Allows to see muteall global notifications|true
### Other
|Permission|Definition|Default
|-|-|-
|svcmute.admin.command|/svcmute reload|OP
|svcmute.disable.voice|Disables player's voice chat at all|false
# Config

```
# You can leave any field blank if you don't want that message to appear
# Formatting support:
# - Legacy (&)
# - MiniMessage; Docs: https://docs.papermc.io/adventure/minimessage/; Online Viewer: https://webui.advntr.dev

# ==============================================================================
# ========================== MUTE PLUGIN CONFIGURATION =========================
# ==============================================================================

# General prefix added to the beginning of most plugin messages.
# You can use {prefix} placeholder anywhere
prefix: "&8[&6SVC-Mute&8]&r "

# Message displayed in the player's actionbar when they try to chat while muted.
# Placeholders: {time}
actionbar_mute_msg: "&cYou are currently muted and cannot speak."

# Message displayed in the player's actionbar when they try to chat while "muteall" is enabled.
actionbar_muteall_msg: "&cYou are currently muted and cannot speak."

# Message displayed in the player's actionbar when they try to chat with permission "svcmute.disable.voice".
actionbar_not_allowed_msg: "&cYou are not allowed to speak."

# ==============================================================================
# ================================ TIME FORMATS ================================
# ==============================================================================

# Text used when a mute has no expiration date.
time_permanent: "&4Permanent"

# Base words for time units.
time_year: "y"
time_month: "mon"
time_week: "w"
time_day: "d"
time_hour: "h"
time_minute: "m"
time_second: "s"

# ==============================================================================
# ================================ REASONS & MISC ==============================
# ==============================================================================

# Message shown when a reason is blank.
reason_empty: "&cViolation of server rules."

# Message shown when the plugin configuration is reloaded.
config_reload_msg: "{prefix}&aConfiguration successfully reloaded."

# ==============================================================================
# ============================= MUTE MESSAGES ==============================
# ==============================================================================

# Broadcasted to the server when a player is muted.
# Placeholders: {target}, {time}, {reason}, {sender}
mute_msg: "{prefix}&c{target} &7has been muted for &e{time}&7. Reason: &f{reason}"

# Broadcasted to the server when a player is permanently muted.
# Placeholders: {target}, {reason}, {sender}
mute_msg_permanent: "{prefix}&c{target} &7has been &4permanently muted&7. Reason: &f{reason}"

# Message sent directly to the player in chat when they get muted.
# Placeholders: {target}, {time}, {reason}, {sender}
mute_player_msg: "{prefix}&cYou have been muted for &e{time}&c. Reason: &f{reason}"

# Error message when trying to mute a player who is already muted.
# Placeholders: {target}
mute_command_already_muted: "{prefix}&c{target} is already muted."

# ==============================================================================
# =============================== UNMUTE MESSAGES ==============================
# ==============================================================================

# Broadcasted to the server when a player is unmuted.
# Placeholders: {target}, {sender}
unmute_global_msg: "{prefix}&a{target} &7has been unmuted by &e{sender}&7."

# Message sent to the player when they are unmuted.
# Placeholders: {target}, {sender}
unmute_player_msg: "{prefix}&aYou have been unmuted by &e{sender}&a."

# Error message when trying to unmute a player who is not currently muted.
# Placeholders: {target}
unmute_command_already_unmuted: "{prefix}&c{target} is not currently muted."

# ==============================================================================
# ================================ MUTE ALL ====================================
# ==============================================================================

# Broadcasted when the global voice chat is muted (muteall on).
# Placeholders: {sender}
muteall_command_global_msg_on: "{prefix}&4[!] &cGlobal chat has been muted by &e{sender}&c."

# Broadcasted when the global voice chat is unmuted (muteall off).
# Placeholders: {sender}
muteall_command_global_msg_off: "{prefix}&a[!] &aGlobal chat has been unmuted by &e{sender}&a."

```
