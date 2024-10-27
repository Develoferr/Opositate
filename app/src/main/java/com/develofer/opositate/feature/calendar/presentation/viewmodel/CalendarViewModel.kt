package com.develofer.opositate.feature.calendar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.calendar.domain.usecase.GetCalendarDatesUseCase
import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarDatesUseCase: GetCalendarDatesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState(YearMonth.now(), emptyList()))
    val uiState: StateFlow<CalendarUiState> = _uiState

    init {
        updateCalendar(YearMonth.now())
    }

    fun toNextMonth() {
        updateCalendar(_uiState.value.yearMonth.plusMonths(1))
    }

    fun toPreviousMonth() {
        updateCalendar(_uiState.value.yearMonth.minusMonths(1))
    }

    private fun updateCalendar(newMonth: YearMonth) {
        viewModelScope.launch {
            val dates = getCalendarDatesUseCase(newMonth)
            _uiState.value = CalendarUiState(newMonth, dates)
        }
    }
}