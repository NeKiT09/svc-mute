package org.zawarka.svcMute.mute

import java.time.LocalDateTime
import java.util.*

class MuteInfo(val uuid: UUID, val startTime: LocalDateTime, val seconds : Long, val reason: String = "", val permanent: Boolean = seconds <= 0) {

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
}