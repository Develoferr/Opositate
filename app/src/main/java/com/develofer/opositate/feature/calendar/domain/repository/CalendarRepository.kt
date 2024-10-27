package com.develofer.opositate.feature.calendar.domain.repository

import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState.Date
import java.time.YearMonth

interface CalendarRepository {
    fun getDates(yearMonth: YearMonth): List<Date>
}