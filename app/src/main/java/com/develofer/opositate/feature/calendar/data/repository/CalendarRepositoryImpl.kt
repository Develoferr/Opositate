package com.develofer.opositate.feature.calendar.data.repository

import com.develofer.opositate.feature.calendar.data.local.CalendarDataSource
import com.develofer.opositate.feature.calendar.domain.repository.CalendarRepository
import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState
import com.develofer.opositate.feature.calendar.utils.WeekConfiguration
import java.time.YearMonth

class CalendarRepositoryImpl(private val dataSource: CalendarDataSource): CalendarRepository {
    override fun getDates(yearMonth: YearMonth, weekConfiguration: WeekConfiguration): List<CalendarUiState.Date> {
        return dataSource.getDates(yearMonth, weekConfiguration)
    }
}