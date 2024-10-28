package com.develofer.opositate.feature.calendar.domain.usecase

import com.develofer.opositate.feature.calendar.domain.repository.CalendarRepository
import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState
import java.time.YearMonth
import javax.inject.Inject

class GetCalendarDatesUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    operator fun invoke(yearMonth: YearMonth): List<CalendarUiState.Date> {
        return repository.getDates(yearMonth)
    }
}