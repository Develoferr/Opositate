package com.develofer.opositate.main.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.develofer.opositate.ui.theme.Gray600
import com.develofer.opositate.ui.theme.Gray800
import com.develofer.opositate.ui.theme.Gray960

@Composable
fun StatisticCard(
    title: String = "",
    content: @Composable () -> Unit,
    isDarkTheme: Boolean,
    showPagerIndicator: Boolean = false,
    pagerState: PagerState? = null,
    titleIcon: Painter? = null,
    showChart: Boolean = false,
    itemsSize: Int = 0,
    onTitleButtonClick: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = if (isDarkTheme) CardDefaults.cardElevation(defaultElevation = 0.dp) else CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, Gray800, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Gray960 else Color.White
        )
    ) {
        Column(Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            if (title.isNotBlank()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = title)
                    Spacer(Modifier.weight(1f))

                    when {
                        showPagerIndicator && pagerState != null && itemsSize > 1 -> {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                repeat(itemsSize) { iteration ->
                                    val color = if (pagerState.currentPage == iteration) {
                                        if (isDarkTheme) Color.White else Color.Black
                                    } else {
                                        if (isDarkTheme) Color.DarkGray else Color.LightGray
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                    )
                                }
                            }
                        }
                        titleIcon != null && onTitleButtonClick != null -> {
                            IconButton(
                                onClick = onTitleButtonClick,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = titleIcon,
                                    contentDescription = if (showChart) "Ocultar" else "Mostrar",
                                    tint = if (isDarkTheme) Color.White else Color.Black
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(4.dp))
                HorizontalDivider(thickness = 1.dp, color = if (isDarkTheme) Gray800 else Gray600)
                Spacer(Modifier.height(4.dp))
            }
            content()
        }
    }
}

@Composable
fun <T> PagerCard(
    items: List<T>,
    isDarkTheme: Boolean,
    title: String = "",
    showPagerIndicator: Boolean = false,
    itemContent: @Composable (T) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { items.size }
    )
    StatisticCard(
        title = title,
        isDarkTheme = isDarkTheme,
        showPagerIndicator = showPagerIndicator,
        pagerState = pagerState,
        itemsSize = items.size,
        content = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        itemContent(items[page])
                    }
                }
            }
        }
    )
}