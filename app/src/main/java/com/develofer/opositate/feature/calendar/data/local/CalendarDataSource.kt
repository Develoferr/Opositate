package com.develofer.opositate.feature.calendar.data.local

import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState
import com.develofer.opositate.feature.calendar.utils.WeekConfiguration
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

class CalendarDataSource {
    fun getDates(yearMonth: YearMonth, weekConfiguration: WeekConfiguration): List<CalendarUiState.Date> {
        val firstDayOfWeek = when (weekConfiguration) {
            WeekConfiguration.MONDAY_START_WEEK -> DayOfWeek.MONDAY
            WeekConfiguration.SUNDAY_START_WEEK -> DayOfWeek.SUNDAY
        }

        val firstDayOfMonth = LocalDate.of(yearMonth.year, yearMonth.monthValue, 1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()

        val startDay = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(firstDayOfWeek))
        val endDay = lastDayOfMonth.with(TemporalAdjusters.nextOrSame(
            when (weekConfiguration) {
                WeekConfiguration.MONDAY_START_WEEK -> DayOfWeek.SUNDAY
                WeekConfiguration.SUNDAY_START_WEEK -> DayOfWeek.SATURDAY
            }
        ))

        val datesInCalendar = generateSequence(startDay) { it.plusDays(1) }
            .takeWhile { it.isBefore(endDay.plusDays(1)) }
            .map { date ->
                CalendarUiState.Date(
                    date = date,
                    isSelected = date.isEqual(LocalDate.now()),
                    isInCurrentMonth = date.month == yearMonth.month
                )
            }
            .toList().toMutableList()

        if (datesInCalendar.size < 42) {
            val daysToAdd = 42 - datesInCalendar.size

            for (i in 1..daysToAdd) {
                val nextDate = datesInCalendar.last().date.plusDays(1)
                datesInCalendar += CalendarUiState.Date(
                    date = nextDate,
                    isSelected = false,
                    isInCurrentMonth = false
                )
            }
        }

        return datesInCalendar
    }
}