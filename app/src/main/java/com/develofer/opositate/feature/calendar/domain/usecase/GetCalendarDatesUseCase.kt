package com.develofer.opositate.feature.calendar.domain.usecase

import com.develofer.opositate.feature.calendar.domain.repository.CalendarRepository
import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState
import com.develofer.opositate.feature.calendar.utils.WeekConfiguration
import java.time.YearMonth
import javax.inject.Inject

class GetCalendarDatesUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    operator fun invoke(yearMonth: YearMonth, weekConfiguration: WeekConfiguration): List<CalendarUiState.Date> =
        repository.getDates(yearMonth, weekConfiguration)
}