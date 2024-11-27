package com.develofer.opositate.feature.test.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.feature.test.domain.model.AbilityAsksItem
import com.develofer.opositate.feature.test.domain.model.TestAsksByGroup
import com.develofer.opositate.main.components.common.ExpandIcon
import com.develofer.opositate.ui.theme.Gray300
import com.develofer.opositate.ui.theme.Gray400
import com.develofer.opositate.ui.theme.Gray600
import com.develofer.opositate.ui.theme.Gray700
import com.develofer.opositate.ui.theme.Gray900
import com.develofer.opositate.ui.theme.Gray960

@Composable
fun TestContent(
    asksByGroup: List<TestAsksByGroup>,
    onClickItem: (abilityId: Int, taskId: Int, testName: String) -> Unit,
    isDarkTheme: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Tests Completos",
            fontSize = 14.sp,
            color = Gray400
        )

        Spacer(modifier = Modifier.size(8.dp))

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Gray700, RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = when { isDarkTheme -> Gray960 else -> Gray300 }
            )
        ) {
            Text(
                modifier = Modifier.padding(start = 18.dp, top = 10.dp, bottom = 10.dp),
                text = "Test con todas las habilidades",
                fontSize = 12.sp,
                color = Gray400
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 1.dp, color = if (isDarkTheme) Gray900 else Gray600)

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Tests Por Habilidad",
            fontSize = 14.sp,
            color = Gray400
        )

        Spacer(modifier = Modifier.size(8.dp))

        asksByGroup.forEachIndexed { index, group ->
            if (index > 0 && index < asksByGroup.size) Spacer(modifier = Modifier.size(16.dp))

            AskGroupItem(
                askGroup = group,
                isDarkTheme = isDarkTheme,
                isLastItem = index == asksByGroup.size - 1
            )
        }

        Spacer(modifier = Modifier.size(12.dp))
    }
}

@Composable
private fun AskGroupItem(
    askGroup: TestAsksByGroup,
    isDarkTheme: Boolean,
    isLastItem: Boolean
) {
    val groupExpanded = remember { mutableStateOf(askGroup.expanded) }

    Column {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Gray700, RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = when { isDarkTheme -> Gray960 else -> Gray300 }
            )
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            AskGroupHeader(
                askGroup = askGroup,
                isExpanded = groupExpanded.value,
                onExpandClick = { groupExpanded.value = !groupExpanded.value }
            )

            if (groupExpanded.value) {
                AskGroupContent(
                    asks = askGroup.asksByGroup,
                    isDarkTheme = isDarkTheme
                )
            }

            Spacer(modifier = Modifier.size(8.dp))
            if (!isLastItem) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (isDarkTheme) Gray900 else Gray600,
                    modifier = Modifier.padding(horizontal = 12.dp))
            }
        }
    }
}

@Composable
private fun AskGroupHeader(
    askGroup: TestAsksByGroup,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = askGroup.testAskGroupIconResId),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = Gray400
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = askGroup.testAskGroupNameResId),
            fontSize = 12.sp,
            color = Gray400
        )
        Spacer(modifier = Modifier.weight(1f))
        ExpandIcon(
            isExpanded = isExpanded,
            onClick = onExpandClick,
            tint = Gray400
        )
    }
}

@Composable
private fun AskGroupContent(
    asks: List<AbilityAsksItem>,
    isDarkTheme: Boolean
) {
    asks.forEach { ask ->
        Spacer(modifier = Modifier.size(3.dp))
        HorizontalDivider(thickness = 1.dp, color = if (isDarkTheme) Gray700 else Gray600)
        Spacer(modifier = Modifier.size(3.dp))

        Text(
            color = Gray400,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            text = ask.abilityName,
            fontSize = 12.sp
        )
    }
}