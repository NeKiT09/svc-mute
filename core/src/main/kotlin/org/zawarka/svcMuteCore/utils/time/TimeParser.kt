package org.zawarka.svcMuteCore.utils.time

import java.util.*
import java.util.regex.Pattern

object TimeParser {

    private val TIME_UNITS: MutableMap<String, Long> = mutableMapOf<String, Long>().apply {
        put("год", 365L * 24 * 60 * 60);
        put("лет", 365L * 24 * 60 * 60);
        put("г", 365L * 24 * 60 * 60);

        put("месяц", 30L * 24 * 60 * 60);
        put("месяцев", 30L * 24 * 60 * 60);
        put("мес", 30L * 24 * 60 * 60);

        put("неделя", 7L * 24 * 60 * 60);
        put("недель", 7L * 24 * 60 * 60);
        put("нед", 7L * 24 * 60 * 60);
        put("н", 7L * 24 * 60 * 60);

        put("день", 24L * 60 * 60);
        put("дней", 24L * 60 * 60);
        put("д", 24L * 60 * 60);

        put("час", 60L * 60);
        put("часов", 60L * 60);
        put("ч", 60L * 60);

        put("минута", 60L);
        put("минут", 60L);
        put("мин", 60L);
        put("м", 60L);

        put("секунда", 1L);
        put("секунд", 1L);
        put("сек", 1L);
        put("с", 1L);
    }
    private val TIME_UNITS_EN: MutableMap<String, Long> = mutableMapOf<String, Long>().apply {
        put("year", 365L * 24 * 60 * 60);
        put("yr", 365L * 24 * 60 * 60);
        put("y", 365L * 24 * 60 * 60);

        put("month", 30L * 24 * 60 * 60);
        put("mo", 30L * 24 * 60 * 60);

        put("week", 7L * 24 * 60 * 60);
        put("wk", 7L * 24 * 60 * 60);
        put("w", 7L * 24 * 60 * 60);

        put("day", 24L * 60 * 60);
        put("d", 24L * 60 * 60);

        put("hour", 60L * 60);
        put("hr", 60L * 60);
        put("h", 60L * 60);

        put("minute", 60L);
        put("min", 60L);
        put("m", 60L);

        put("second", 1L);
        put("sec", 1L);
        put("s", 1L);
    }

    /**
     * Парсит строку времени в секунды
     * @param timeString строка времени (например: "2н 1д 3с", "1y 2mo 3d", "1год 2мес 3дня")
     * @return количество секунд
     */
    fun parseTimeToSeconds(timeString: String?): Long {
        if (timeString == null || timeString.trim { it <= ' ' }.isEmpty()) {
            return 0
        }

        var totalSeconds: Long = 0
        val input = timeString.trim { it <= ' ' }.lowercase(Locale.getDefault())


        // Регулярное выражение для поиска пар "число-единица"
        val pattern = Pattern.compile("(\\d+)\\s*([а-яa-z]+)")
        val matcher = pattern.matcher(input)

        while (matcher.find()) {
            val value = matcher.group(1).toLong()
            val unit = matcher.group(2).lowercase(Locale.getDefault())


            // Проверяем русские единицы
            totalSeconds += if (TIME_UNITS.containsKey(unit)) {
                value * TIME_UNITS[unit]!!
            } else if (TIME_UNITS_EN.containsKey(unit)) {
                value * TIME_UNITS_EN[unit]!!
            } else {
                throw IllegalArgumentException("Неизвестная единица времени: $unit")
            }
        }


        // Если не найдено ни одного совпадения, пробуем парсить как просто число (секунды)
        if (totalSeconds == 0L) {
            try {
                totalSeconds = input.trim { it <= ' ' }.toLong()
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Неверный формат времени: $timeString")
            }
        }

        return totalSeconds
    }

    /**
     * Парсит строку времени в секунды, возвращает 0 при ошибке (без исключения)
     */
    fun parseTimeToSecondsSafe(timeString: String?): Long {
        return try {
            parseTimeToSeconds(timeString)
        } catch (e: Exception) {
            0
        }
    }
}