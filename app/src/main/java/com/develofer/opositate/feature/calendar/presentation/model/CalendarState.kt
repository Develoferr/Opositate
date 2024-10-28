package com.develofer.opositate.feature.calendar.presentation.model

import java.time.LocalDate
import java.time.YearMonth

data class CalendarUiState (
    val yearMonth: YearMonth,
    val dates: List<Date>
) {
    data class Date(
        val date: LocalDate,
        val isSelected: Boolean = false,
        val isInCurrentMonth: Boolean = true // Nuevo campo
    ) {
        val dayOfMonth: String get() = date.dayOfMonth.toString()
        companion object {
            val Empty = Date(LocalDate.MIN, isInCurrentMonth = false)
        }
    }
}