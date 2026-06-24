package org.zawarka.svcMute.utils.time

open class TimeFormatter {

    companion object {
        private const val SECONDS_IN_MINUTE = 60L
        private const val SECONDS_IN_HOUR = 60 * SECONDS_IN_MINUTE
        private const val SECONDS_IN_DAY = 24 * SECONDS_IN_HOUR
        private const val SECONDS_IN_WEEK = 7 * SECONDS_IN_DAY
        private const val SECONDS_IN_MONTH = 30 * SECONDS_IN_DAY
        private const val SECONDS_IN_YEAR = 365 * SECONDS_IN_DAY
    }

    open fun format(totalSeconds: Long): String {
        if(totalSeconds < 0) return getPermanentName()
        var remaining = totalSeconds

        // buildString - это идиоматичный и эффективный способ сборки строк в Kotlin
        return buildString {
            val years = remaining / SECONDS_IN_YEAR
            remaining %= SECONDS_IN_YEAR
            appendIfNotEmpty(years, getYearsName(years))

            val months = remaining / SECONDS_IN_MONTH
            remaining %= SECONDS_IN_MONTH
            appendIfNotEmpty(months, getMonthsName(months))

            val weeks = remaining / SECONDS_IN_WEEK
            remaining %= SECONDS_IN_WEEK
            appendIfNotEmpty(weeks, getWeeksName(weeks))

            val days = remaining / SECONDS_IN_DAY
            remaining %= SECONDS_IN_DAY
            appendIfNotEmpty(days, getDaysName(days))

            val hours = remaining / SECONDS_IN_HOUR
            remaining %= SECONDS_IN_HOUR
            appendIfNotEmpty(hours, getHoursName(hours))

            val minutes = remaining / SECONDS_IN_MINUTE
            remaining %= SECONDS_IN_MINUTE
            appendIfNotEmpty(minutes, getMinutesName(minutes))

            appendIfNotEmpty(remaining, getSecondsName(remaining))

            // Если время было 0, возвращаем "0 секунд"
            if (isEmpty()) {
                append("0 ").append(getSecondsName(0))
            }
        }
    }

    private fun StringBuilder.appendIfNotEmpty(amount: Long, unitName: String) {
        if (amount > 0) {
            if (isNotEmpty()) append(" ")
            append(amount).append(" ").append(unitName)
        }
    }

    protected open fun getPermanentName(): String = "permanent"
    protected open fun getYearsName(amount: Long): String = "year"
    protected open fun getMonthsName(amount: Long): String = "month"
    protected open fun getWeeksName(amount: Long): String = "week"
    protected open fun getDaysName(amount: Long): String = "day"
    protected open fun getHoursName(amount: Long): String = "hour"
    protected open fun getMinutesName(amount: Long): String = "minute"
    protected open fun getSecondsName(amount: Long): String = "second"
}