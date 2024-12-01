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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.feature.test.domain.model.AbilityAsksItem
import com.develofer.opositate.feature.test.domain.model.TestAsksByGroup
import com.develofer.opositate.main.components.common.ExpandIcon
import com.develofer.opositate.main.data.provider.TestType
import com.develofer.opositate.ui.theme.Gray400
import com.develofer.opositate.ui.theme.Gray500
import com.develofer.opositate.ui.theme.Gray600
import com.develofer.opositate.ui.theme.Gray700
import com.develofer.opositate.ui.theme.Gray800
import com.develofer.opositate.ui.theme.Gray900
import com.develofer.opositate.ui.theme.Gray960

@Composable
fun TestContent(
    asksByGroup: List<TestAsksByGroup>,
    onClickItem: (difficultId: Int, groupId: Int, abilityId: Int, taskId: Int, newTestName: String, testType: TestType) -> Unit,
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
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.size(8.dp))

        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = if (isDarkTheme) CardDefaults.cardElevation(defaultElevation = 0.dp) else CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Gray800, RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkTheme) Gray960 else Color.White
            ),
            onClick = {  }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 2.dp, top = 10.dp, bottom = 10.dp),
                    text = "Test con todas las habilidades",
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                NewTestIcon(onClick = { onClickItem(0, 0, 0, 0, "General", TestType.GENERAL) })
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 1.dp, color = if (isDarkTheme) Gray900 else Gray600)

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Tests Por Habilidad",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.size(8.dp))

        asksByGroup.forEachIndexed { index, group ->
            if (index > 0 && index < asksByGroup.size) Spacer(modifier = Modifier.size(16.dp))

            AskGroupItem(
                askGroupIndex = index,
                askGroup = group,
                isDarkTheme = isDarkTheme,
                isLastItem = index == asksByGroup.size - 1,
                onClickItem = onClickItem
            )
        }

        Spacer(modifier = Modifier.size(12.dp))
    }
}

@Composable
private fun AskGroupItem(
    askGroup: TestAsksByGroup,
    isDarkTheme: Boolean,
    isLastItem: Boolean,
    onClickItem: (difficultId: Int, groupId: Int, abilityId: Int, taskId: Int, newTestName: String, testType: TestType) -> Unit,
    askGroupIndex: Int
) {
    val groupExpanded = remember { mutableStateOf(askGroup.expanded) }

    Column {
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
            Spacer(modifier = Modifier.size(8.dp))

            AskGroupHeader(
                askGroupIndex = askGroupIndex,
                askGroup = askGroup,
                isDarkTheme = isDarkTheme,
                isExpanded = groupExpanded.value,
                onExpandClick = { groupExpanded.value = !groupExpanded.value },
                onClickItem = onClickItem
            )

            if (groupExpanded.value) {
                AskGroupContent(
                    asks = askGroup.asksByGroup,
                    isDarkTheme = isDarkTheme,
                    onClickItem = onClickItem
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
    isDarkTheme: Boolean,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    onClickItem: (difficultId: Int, groupId: Int, abilityId: Int, taskId: Int, newTestName: String, testType: TestType) -> Unit,
    askGroupIndex: Int
) {
    val groupName = stringResource(id = askGroup.testAskGroupNameResId)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = askGroup.testAskGroupIconResId),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = if (isDarkTheme) Gray500 else Gray600
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = groupName,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        NewTestIcon(onClick = { onClickItem(0, askGroupIndex, 0, 0, groupName, TestType.GROUP) })
        Spacer(modifier = Modifier.size(16.dp))
        ExpandIcon(
            isExpanded = isExpanded,
            onClick = onExpandClick,
            tint = if (isDarkTheme) Gray400 else Gray600
        )
    }
}

@Composable
private fun AskGroupContent(
    asks: List<AbilityAsksItem>,
    isDarkTheme: Boolean,
    onClickItem: (difficultId: Int, groupId: Int, abilityId: Int, taskId: Int, newTestName: String, testType: TestType) -> Unit
) {
    asks.forEach { ask ->
        Spacer(modifier = Modifier.size(3.dp))
        HorizontalDivider(thickness = 1.dp, color = if (isDarkTheme) Gray700 else Gray600)
        Spacer(modifier = Modifier.size(3.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = ask.abilityName,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            NewTestIcon(onClick = { onClickItem(0, 0, ask.abilityId, 0, ask.abilityName, TestType.ABILITY) })
        }
    }
}