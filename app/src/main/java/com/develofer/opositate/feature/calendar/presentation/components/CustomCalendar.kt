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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    Column {
        Spacer(modifier = Modifier.size(32.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach { day ->
                ContentItem(day, modifier = Modifier.weight(1f))
            }
        }
        Header(
            yearMonth = yearMonth,
            onPreviousMonthClicked = onPreviousMonthClicked,
            onNextMonthClicked = onNextMonthClicked
        )

        var index = 0
        repeat(6) {
            Row {
                repeat(7) {
                    val element = if (index < dates.size) dates[index] else CalendarUiState.Date.Empty
                    ContentDateItem(
                        date = element,
                        onClickListener = onDateClick,
                        modifier = Modifier.weight(1f),
                        isDarkTheme = isDarkTheme
                    )
                    index++
                }
            }
        }
    }
}

@Composable
fun Header(
    yearMonth: YearMonth,
    onPreviousMonthClicked: () -> Unit,
    onNextMonthClicked: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onPreviousMonthClicked) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Previous month")
        }
        Text(
            text = "${getLocalizedMonthName(yearMonth).lowercase().replaceFirstChar { it.uppercase() }} ${yearMonth.year}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.W500,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onNextMonthClicked) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next month")
        }
    }
}

@Composable
fun ContentItem(day: String, modifier: Modifier) {
    Box (modifier = modifier) {
        Text(
            text = day.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}

@Composable
fun ContentDateItem(
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
                color = if (date.isSelected) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    Color.Transparent
                }
            )
            .clickable {
                onClickListener(date)
            }
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