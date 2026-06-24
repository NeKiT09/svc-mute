package org.zawarka.svcMute.mute

import org.zawarka.svcMute.utils.time.TimeConfigFormatter
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.max

class MuteInfo(val uuid: UUID, val seconds : Long, val reason: String = "", val startTime: LocalDateTime = LocalDateTime.now(), val permanent: Boolean = seconds <= 0) {

    /**
     * @return Time of mute's end, null - if permanent
     */
    fun getEndTime(): LocalDateTime? {
        if (permanent) {
            return null
        }
        return startTime.plusSeconds(seconds)
    }

    /**
     * Checks if mute expired
     */
    fun isExpired(): Boolean {
        return !permanent && !LocalDateTime.now().isBefore(getEndTime())
    }

    fun remainSeconds(): Long {
        val endTime = getEndTime() ?: return -1
        val now = LocalDateTime.now()
        val remaining = ChronoUnit.SECONDS.between(now, endTime)

        return max(0L, remaining)
    }

    fun timeString() : String {
        return TimeConfigFormatter.instance.format(seconds)
    }

    fun timeRemainsString() : String {
        return TimeConfigFormatter.instance.format(remainSeconds())
    }
}