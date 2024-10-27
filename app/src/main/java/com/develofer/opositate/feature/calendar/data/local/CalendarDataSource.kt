package com.develofer.opositate.feature.calendar.data.local

import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

class CalendarDataSource {
    fun getDates(yearMonth: YearMonth): List<CalendarUiState.Date> {
        val firstDayOfMonth = LocalDate.of(yearMonth.year, yearMonth.monthValue, 1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()
        val startDay = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endDay = lastDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

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
        if (datesInCalendar.size <= 35) {
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