package com.develofer.opositate.feature.calendar.utils

import com.develofer.opositate.utils.StringConstants.EMPTY_STRING
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

val daysOfWeek: Array<String>
    get() {
        val daysOfWeek = Array(7) { EMPTY_STRING }
        for (dayOfWeek in DayOfWeek.entries) {
            val localizedDayName = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            daysOfWeek[dayOfWeek.ordinal] = localizedDayName
        }
        return daysOfWeek
    }

fun getLocalizedMonthName(yearMonth: YearMonth): String {
    val month = yearMonth.month
    return month.getDisplayName(TextStyle.FULL, Locale.getDefault())
}