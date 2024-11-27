package com.develofer.opositate.feature.test.presentation.component

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.R
import com.develofer.opositate.feature.test.data.model.StudyItem
import com.develofer.opositate.ui.theme.Gray200
import com.develofer.opositate.ui.theme.Gray300
import com.develofer.opositate.ui.theme.Gray500
import com.develofer.opositate.ui.theme.Gray700
import com.develofer.opositate.ui.theme.Gray900
import com.develofer.opositate.ui.theme.Gray960

@Composable
fun StudyItemList(
    studyItemList: List<StudyItem>,
    isDarkTheme: Boolean,
    onClickItem: (testId: String) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isDarkTheme) MaterialTheme.colorScheme.background
                    else Color.White
            )
    ) {
        items(studyItemList.size) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        isDarkTheme && studyItemList[it].isEnabled -> Gray900
                        isDarkTheme && !studyItemList[it].isEnabled -> Gray960
                        !isDarkTheme && studyItemList[it].isEnabled -> Gray300
                        !isDarkTheme && !studyItemList[it].isEnabled -> Gray500
                        else -> Color.White
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .padding(end = 16.dp)
                    .padding(top = if (it == 0) 16.dp else 6.dp)
                    .padding(bottom = if (it == studyItemList.size - 1) 16.dp else 6.dp)
                    .clickable(
                        enabled = studyItemList[it].isEnabled,
                        onClick = { onClickItem((studyItemList[it].number - 1).toString()) }
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = studyItemList[it].number.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            isDarkTheme && studyItemList[it].isEnabled -> Gray200
                            isDarkTheme && !studyItemList[it].isEnabled -> Gray700
                            !isDarkTheme -> Color.Gray
                            else -> Color.Black
                        }
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    Text(
                        text = studyItemList[it].title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f),
                        color = when {
                            isDarkTheme && studyItemList[it].isEnabled -> MaterialTheme.colorScheme.primary
                            isDarkTheme && !studyItemList[it].isEnabled -> Gray700
                            !isDarkTheme && studyItemList[it].isEnabled -> Color.Black
                            !isDarkTheme && !studyItemList[it].isEnabled -> Color.Gray
                            else -> Color.Black
                        }
                    )

                    if (!studyItemList[it].isEnabled) {
                        Icon(
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = stringResource(R.string.study_item_list__content_description__locked),
                            tint = if (isDarkTheme) Gray700 else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}