package com.develofer.opositate.feature.profile.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.develofer.opositate.feature.profile.domain.model.ScoreAbility
import com.develofer.opositate.feature.profile.domain.model.ScoreTask
import com.develofer.opositate.feature.profile.domain.model.UserScoresByGroup
import com.develofer.opositate.feature.profile.presentation.components.CustomDualProgressBar
import com.develofer.opositate.main.components.common.ExpandIcon
import com.develofer.opositate.ui.theme.Gray300
import com.develofer.opositate.ui.theme.Gray400
import com.develofer.opositate.ui.theme.Gray600
import com.develofer.opositate.ui.theme.Gray900
import com.develofer.opositate.ui.theme.Gray960

@Composable
fun ScoresContent(
    scoresByGroup: List<UserScoresByGroup>,
    isDarkTheme: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.padding(top = 8.dp))

        scoresByGroup.forEachIndexed { index, group ->
            if (index > 0) Spacer(modifier = Modifier.size(8.dp))

            ScoreGroupItem(
                scoreGroup = group,
                isDarkTheme = isDarkTheme,
                isLastItem = index == scoresByGroup.size - 1
            )
        }
    }
}

@Composable
private fun ScoreGroupItem(
    scoreGroup: UserScoresByGroup,
    isDarkTheme: Boolean,
    isLastItem: Boolean
) {
    val groupExpanded = remember { mutableStateOf(scoreGroup.expanded) }

    Column {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Gray900, RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = when { isDarkTheme -> Gray960 else -> Gray300 }
            )
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            ScoreGroupHeader(
                scoreGroup = scoreGroup,
                isExpanded = groupExpanded.value,
                onExpandClick = { groupExpanded.value = !groupExpanded.value }
            )

            if (groupExpanded.value) {
                ScoreGroupContent(
                    scores = scoreGroup.scoresByGroup,
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
private fun ScoreGroupHeader(
    scoreGroup: UserScoresByGroup,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = scoreGroup.userScoresGroupIconResId),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = Gray400
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = scoreGroup.userScoresGroupNameResId),
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
private fun ScoreGroupContent(
    scores: List<ScoreAbility>,
    isDarkTheme: Boolean
) {
    scores.forEachIndexed { index, score ->
        Spacer(modifier = Modifier.size(3.dp))
        HorizontalDivider(thickness = 1.dp, color = if (isDarkTheme) Gray900 else Gray600)
//        Spacer(modifier = Modifier.size(3.dp))

        val abilityExpanded = remember { mutableStateOf(score.expanded) }

        ScoreAbilityHeader(
            score = score,
            isExpanded = abilityExpanded.value,
            onExpandClick = { abilityExpanded.value = !abilityExpanded.value }
        )

        CustomDualProgressBar(
            primaryProgress = score.startScore.toFloat() / 10,
            secondaryProgress = score.presentScore.toFloat() / 10,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 6.dp, top = 2.dp)
                .height(8.dp)
        )
//        Card(
//            shape = RoundedCornerShape(8.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = when { isDarkTheme -> Gray900 else -> Gray300 }
//            )
//        ) {

//            if (abilityExpanded.value) {
//                ScoreAbilityTasks(tasks = score.taskScores)
//            }
//        }
    }
}

@Composable
private fun ScoreAbilityHeader(
    score: ScoreAbility,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            color = Gray300,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            text = stringResource(id = score.abilityNameResId),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.weight(1f))
//        ExpandIcon(
//            isExpanded = isExpanded,
//            onClick = onExpandClick
//        )
    }
}

@Composable
private fun ScoreAbilityTasks(tasks: List<ScoreTask>) {
    tasks.forEach { taskScore ->
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = taskScore.taskNameResId),
                fontSize = 12.sp
            )
            CustomDualProgressBar(
                modifier = Modifier.height(8.dp),
                primaryProgress = taskScore.startScore.toFloat() / 10,
                secondaryProgress = taskScore.presentScore.toFloat() / 10
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}