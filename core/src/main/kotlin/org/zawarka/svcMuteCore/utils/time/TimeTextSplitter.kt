package org.zawarka.svcMuteCore.utils.time

object TimeTextSplitter {

    /**
     * Разделяет список строк на временные и остальные элементы
     * @param args список строк для анализа
     * @return пара списков: [временные_строки, остальные_строки]
     */
    fun splitTimeStrings(args: List<String>): TimeParseResult {
        val timeParts: MutableList<String> = mutableListOf()
        val otherParts: MutableList<String> = mutableListOf()

        if (args.isEmpty()) {
            return TimeParseResult(timeParts, otherParts)
        }


        // Флаг, указывающий что мы уже нашли не-временную строку
        var foundNonTime = false

        for (arg in args) {
            if (!foundNonTime && isTimeString(arg)) {
                timeParts.add(arg)
            } else {
                foundNonTime = true
                otherParts.add(arg)
            }
        }

        return TimeParseResult(timeParts, otherParts)
    }

    /**
     * Проверяет, является ли строка временным интервалом
     */
    private fun isTimeString(str: String?): Boolean {
        if (str == null || str.trim { it <= ' ' }.isEmpty()) {
            return false
        }

        try {
            val seconds = TimeParser.parseTimeToSeconds(str)
            // Считаем строку валидной, если она парсится в неотрицательное количество секунд
            return seconds >= 0
        } catch (e: Exception) {
            return false
        }
    }

    /**
     * Результат парсинга - содержит два списка строк
     */
    class TimeParseResult(timeStrings: List<String>?, otherStrings: List<String>?) {
        val timeStrings: MutableList<String> = if (timeStrings != null) ArrayList(timeStrings) else ArrayList()
        val otherStrings: MutableList<String> = if (otherStrings != null) ArrayList(otherStrings) else ArrayList()

        val timeString: String = java.lang.String.join(" ", this.timeStrings)

        val reasonString: String = java.lang.String.join(" ", this.otherStrings)
    }
}