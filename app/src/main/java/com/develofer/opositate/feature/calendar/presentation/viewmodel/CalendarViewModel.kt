package com.develofer.opositate.feature.calendar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.calendar.domain.usecase.GetCalendarDatesUseCase
import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState
import com.develofer.opositate.feature.calendar.utils.WeekConfiguration
import com.develofer.opositate.feature.settings.domain.usecase.GetMondayStartWeekUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarDatesUseCase: GetCalendarDatesUseCase,
    private val getMondayStartWeekUseCase: GetMondayStartWeekUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState(YearMonth.now(), emptyList()))
    val uiState: StateFlow<CalendarUiState> = _uiState

    private val _mondayStartWeek = getMondayStartWeekUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val weekConfiguration: StateFlow<WeekConfiguration?> = _mondayStartWeek.map { isMondayStart ->
        when (isMondayStart) {
            true -> WeekConfiguration.MONDAY_START_WEEK
            false -> WeekConfiguration.SUNDAY_START_WEEK
            else -> null
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
    init {
        viewModelScope.launch {
            _mondayStartWeek.collect { isMondayStart ->
                updateCalendar(_uiState.value.yearMonth, isMondayStart)
            }
        }
    }

    fun toNextMonth() {
        updateCalendar(_uiState.value.yearMonth.plusMonths(1), _mondayStartWeek.value)
    }

    fun toPreviousMonth() {
        updateCalendar(_uiState.value.yearMonth.minusMonths(1), _mondayStartWeek.value)
    }

    private fun updateCalendar(newMonth: YearMonth, isMondayStart: Boolean?) {
        isMondayStart?.let {
            viewModelScope.launch {
                val weekConfig = if (isMondayStart) {
                    WeekConfiguration.MONDAY_START_WEEK
                } else {
                    WeekConfiguration.SUNDAY_START_WEEK
                }
                val dates = getCalendarDatesUseCase(newMonth, weekConfig)
                _uiState.value = CalendarUiState(newMonth, dates)
            }
        }
    }
}