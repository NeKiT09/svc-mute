package org.zawarka.svcMute.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import java.util.Locale
import java.util.Locale.getDefault
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @return Converted string to MiniMessage
 */
fun String.mm() : Component {
    return MiniMessageConverter.parse(this)
}

object MiniMessageConverter {
    val MINI_MESSAGE: MiniMessage = MiniMessage.miniMessage()

    fun parse(text: String): Component {
        // Replace & to MiniMessage
        val converted = LegacyFormatConverter.convertLegacyToMiniMessage(text)

        // Parse to MiniMessage
        return MINI_MESSAGE.deserialize(converted)
    }
}

private object LegacyFormatConverter{
    val LEGACY_PATTERN: Pattern = Pattern.compile("&([0-9a-fk-or])", Pattern.CASE_INSENSITIVE)

    fun convertLegacyToMiniMessage(text: String): String {
        val matcher: Matcher = LEGACY_PATTERN.matcher(text)
        val buffer = StringBuffer()

        while (matcher.find()) {
            val code: String = matcher.group(1).lowercase()
            val replacement = getMiniMessageTag(code)
            matcher.appendReplacement(buffer, replacement)
        }
        matcher.appendTail(buffer)

        return buffer.toString()
    }

    private fun getMiniMessageTag(code: String): String {
        // legacy format to MiniMessage tags
        return when (code) {
            "0" -> "<black>"
            "1" -> "<dark_blue>"
            "2" -> "<dark_green>"
            "3" -> "<dark_aqua>"
            "4" -> "<dark_red>"
            "5" -> "<dark_purple>"
            "6" -> "<gold>"
            "7" -> "<gray>"
            "8" -> "<dark_gray>"
            "9" -> "<blue>"
            "a" -> "<green>"
            "b" -> "<aqua>"
            "c" -> "<red>"
            "d" -> "<light_purple>"
            "e" -> "<yellow>"
            "f" -> "<white>"
            "k" -> "<obfuscated>"
            "l" -> "<bold>"
            "m" -> "<strikethrough>"
            "n" -> "<underlined>"
            "o" -> "<italic>"
            "r" -> "<reset>"
            else -> ""
        }
    }
}