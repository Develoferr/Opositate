package com.develofer.opositate.feature.calendar.domain.repository

import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState.Date
import com.develofer.opositate.feature.calendar.utils.WeekConfiguration
import java.time.YearMonth

interface CalendarRepository {
    fun getDates(yearMonth: YearMonth, weekConfiguration: WeekConfiguration): List<Date>
}