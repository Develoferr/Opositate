package com.develofer.opositate.feature.test.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.R
import com.develofer.opositate.feature.test.domain.model.AbilityAsksItem
import com.develofer.opositate.ui.theme.Gray200
import com.develofer.opositate.ui.theme.Gray300
import com.develofer.opositate.ui.theme.Gray500
import com.develofer.opositate.ui.theme.Gray900

@Composable
fun TestItemList(
    testItemList: List<AbilityAsksItem>,
    onClickItem: (abilityId: Int, taskId: Int, testName: String) -> Unit,
    isDarkTheme: Boolean,
) {
    LazyColumn {
        items(testItemList.size) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        isDarkTheme -> Gray900
                        !isDarkTheme -> Gray300
                        else -> Gray500
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .padding(end = 16.dp)
                    .padding(top = if (it == 0) 16.dp else 6.dp)
                    .padding(bottom = if (it == testItemList.size - 1) 16.dp else 6.dp)
            ) {
                val expanded = remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = testItemList[it].abilityName,
                        color = when {
                            isDarkTheme -> Gray200
                            !isDarkTheme -> Color.Gray
                            else -> Color.Black
                        }
                    )
                    Spacer(modifier = Modifier.size(0.dp).weight(1f))
                    Icon(
                        modifier = Modifier.clickable { expanded.value = !expanded.value },
                        painter = if (expanded.value) painterResource(R.drawable.ic_keyboard_arrow_up) else painterResource(
                            R.drawable.ic_keyboard_arrow_down),
                        contentDescription = stringResource(id = R.string.lesson_screen__content_description__next_month)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                if (expanded.value) {
                    if (it != 0) Spacer(modifier = Modifier.size(4.dp))
                    testItemList[it].tasksAsks.forEach { task ->
                        Text(
                            text = task.taskName,
                            fontSize = 12.sp,
                            modifier = Modifier.clickable {
                                onClickItem(testItemList[it].abilityId, task.taskId, task.taskName)
                            }.padding(start = 16.dp),
                            color = when {
                                isDarkTheme -> Gray200
                                !isDarkTheme -> Color.Gray
                                else -> Color.Black
                            }
                        )
                        if (task.taskId == testItemList[it].tasksAsks.last().taskId) Spacer(modifier = Modifier.size(8.dp))
                        else Spacer(modifier = Modifier.size(4.dp))
                    }
                }
            }
        }
    }
}