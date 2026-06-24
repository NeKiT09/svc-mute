package org.zawarka.svcMute.utils.time

import org.zawarka.svcMute.messages.config.MessageType
import org.zawarka.svcMute.messages.config.MessagesData

class TimeConfigFormatter : TimeFormatter() {

    companion object {
        val instance = TimeConfigFormatter()
    }


    override fun getPermanentName() = MessagesData.getMessageString(MessageType.TIME_PERMANENT)
    override fun getYearsName(amount: Long) = MessagesData.getMessageString(MessageType.TIME_YEAR)
    override fun getMonthsName(amount: Long) = MessagesData.getMessageString(MessageType.TIME_MONTH)
    override fun getWeeksName(amount: Long) = MessagesData.getMessageString(MessageType.TIME_WEEK)
    override fun getDaysName(amount: Long) = MessagesData.getMessageString(MessageType.TIME_DAY)
    override fun getHoursName(amount: Long) = MessagesData.getMessageString(MessageType.TIME_HOUR)
    override fun getMinutesName(amount: Long) = MessagesData.getMessageString(MessageType.TIME_MINUTE)
    override fun getSecondsName(amount: Long) = MessagesData.getMessageString(MessageType.TIME_SECOND)

}