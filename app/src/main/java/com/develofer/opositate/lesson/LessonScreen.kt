package com.develofer.opositate.lesson

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.R


@Composable
fun LessonScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val screenTitle = stringResource(id = R.string.lesson_screen__app_bar_title__lesson)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }
    Spacer(modifier = Modifier.size(24.dp))
    LazyColumn {
        val lessonList = listOf(
            Lesson(1, "Introducción", false),
            Lesson(2, "Tema 1", true),
            Lesson(3, "Tema 2", false),
            Lesson(4, "Tema 3", false),
            Lesson(5, "Tema 4", false),
            Lesson(6, "Tema 5", false),
            Lesson(7, "Tema 6", false),
            Lesson(8, "Tema 7", false),
            Lesson(9, "Tema 8", false),
            Lesson(10, "Tema 9", false),
            Lesson(11, "Tema 10", false),
            Lesson(12, "Tema 11", false),
            Lesson(13, "Tema 12", false),
            Lesson(14, "Tema 13", false),
        )
        items(lessonList.size) {
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 8.dp),
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = lessonList[it].lessonNumber.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = lessonList[it].title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )

                    if (lessonList[it].isLocked) {
                        Icon(
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = "Locked",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.size(32.dp))
}

data class Lesson(
    val lessonNumber: Int,
    val title: String,
    val isLocked: Boolean
)

@Preview(showBackground = true)
@Composable
fun LessonScreenPreview() {
    LessonScreen(rememberNavController(), hiltViewModel())
}