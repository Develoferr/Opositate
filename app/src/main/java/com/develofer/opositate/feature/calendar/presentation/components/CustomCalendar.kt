package com.develofer.opositate.feature.calendar.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.R
import com.develofer.opositate.feature.calendar.presentation.model.CalendarUiState
import com.develofer.opositate.feature.calendar.utils.daysOfWeek
import com.develofer.opositate.feature.calendar.utils.getLocalizedMonthName
import com.develofer.opositate.ui.theme.Gray500
import com.develofer.opositate.ui.theme.Gray700
import java.time.YearMonth

@Composable
fun CalendarContent(
    yearMonth: YearMonth,
    dates: List<CalendarUiState.Date>,
    onDateClick: (CalendarUiState.Date) -> Unit,
    onPreviousMonthClicked: () -> Unit,
    onNextMonthClicked: () -> Unit,
    isDarkTheme: Boolean
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        MonthYearSelector(yearMonth, onPreviousMonthClicked, onNextMonthClicked)
        WeekDays()
        DaysOfMonth(dates, onDateClick, isDarkTheme)
    }
}

@Composable
fun WeekDays() {
    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { day ->
            DayOfWeekItem(day, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun DayOfWeekItem(day: String, modifier: Modifier) {
    Box (modifier = modifier) {
        Text(
            text = day.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            fontWeight = FontWeight.W500,
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}

@Composable
fun MonthYearSelector(
    yearMonth: YearMonth,
    onPreviousMonthClicked: () -> Unit,
    onNextMonthClicked: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onPreviousMonthClicked) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = stringResource(id = R.string.lesson_screen__content_description__previous_month))
        }
        Text(
            text = stringResource(
                id = R.string.month_year_format,
                getLocalizedMonthName(yearMonth).lowercase().replaceFirstChar { it.uppercase() },
                yearMonth.year
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.W500,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onNextMonthClicked) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = stringResource(id = R.string.lesson_screen__content_description__next_month))
        }
    }
}

@Composable
private fun DaysOfMonth(
    dates: List<CalendarUiState.Date>,
    onDateClick: (CalendarUiState.Date) -> Unit,
    isDarkTheme: Boolean
) {
    var index = 0
    repeat(6) {
        Row {
            repeat(7) {
                val date = if (index < dates.size) dates[index] else CalendarUiState.Date.Empty
                DayOfMonthItem(
                    date = date,
                    onClickListener = onDateClick,
                    modifier = Modifier.weight(1f),
                    isDarkTheme = isDarkTheme
                )
                index++
            }
        }
    }
}

@Composable
fun DayOfMonthItem(
    date: CalendarUiState.Date,
    onClickListener: (CalendarUiState.Date) -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean
) {
    val textColor = if (date.isInCurrentMonth) MaterialTheme.colorScheme.onBackground
        else { if (isDarkTheme) Gray700 else Gray500 }
    Box(
        modifier = modifier
            .background(
                color =
                if (date.isSelected) MaterialTheme.colorScheme.secondaryContainer
                else Color.Transparent
            )
            .clickable { onClickListener(date) }
    ) {
        Text(
            text = date.dayOfMonth,
            style = MaterialTheme.typography.bodyMedium.copy(color = textColor),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}