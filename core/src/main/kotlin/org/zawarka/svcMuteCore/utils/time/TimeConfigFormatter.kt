package org.zawarka.svcMuteCore.utils.time

import org.zawarka.svcMuteCore.messages.config.MessageType
import org.zawarka.svcMuteCore.messages.config.MessagesData

class TimeConfigFormatter(private val messagesData: MessagesData) : TimeFormatter() {

    companion object {
        lateinit var instance: TimeConfigFormatter
    }

    init {
        instance = this
    }


    override fun getPermanentName() = messagesData.getMessageString(
        MessageType.TIME_PERMANENT)
    override fun getYearsName(amount: Long) = messagesData.getMessageString(
        MessageType.TIME_YEAR)
    override fun getMonthsName(amount: Long) = messagesData.getMessageString(
        MessageType.TIME_MONTH)
    override fun getWeeksName(amount: Long) = messagesData.getMessageString(
        MessageType.TIME_WEEK)
    override fun getDaysName(amount: Long) = messagesData.getMessageString(
        MessageType.TIME_DAY)
    override fun getHoursName(amount: Long) = messagesData.getMessageString(
        MessageType.TIME_HOUR)
    override fun getMinutesName(amount: Long) = messagesData.getMessageString(
        MessageType.TIME_MINUTE)
    override fun getSecondsName(amount: Long) = messagesData.getMessageString(
        MessageType.TIME_SECOND)

}