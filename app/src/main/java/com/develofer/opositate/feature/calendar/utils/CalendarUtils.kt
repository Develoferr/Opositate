package com.develofer.opositate.feature.calendar.utils

import com.develofer.opositate.utils.StringConstants.EMPTY_STRING
import java.time.DayOfWeek
import java.time.LocalDate
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

enum class WeekConfiguration {
    MONDAY_START_WEEKEND_SATURDAY_SUNDAY,
    SUNDAY_START_WEEKEND_FRIDAY_SATURDAY
}

fun getDaysOfWeek(weekConfiguration: WeekConfiguration): List<String> {
    val allDays = daysOfWeek.toList()
    return when (weekConfiguration) {
        WeekConfiguration.MONDAY_START_WEEKEND_SATURDAY_SUNDAY -> allDays
        WeekConfiguration.SUNDAY_START_WEEKEND_FRIDAY_SATURDAY -> {
            val sundayIndex = allDays.indexOf(DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()))
            if (sundayIndex != -1) {
                allDays.drop(sundayIndex) + allDays.take(sundayIndex)
            } else {
                allDays
            }
        }
    }
}

fun isWeekend(date: LocalDate, weekConfiguration: WeekConfiguration): Boolean {
    return when (weekConfiguration) {
        WeekConfiguration.MONDAY_START_WEEKEND_SATURDAY_SUNDAY ->
            date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY
        WeekConfiguration.SUNDAY_START_WEEKEND_FRIDAY_SATURDAY ->
            date.dayOfWeek == DayOfWeek.FRIDAY || date.dayOfWeek == DayOfWeek.SATURDAY
    }
}