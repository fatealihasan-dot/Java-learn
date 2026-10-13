package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.JavaTrackDatabase
import com.example.data.repository.JavaTrackRepository
import com.example.ui.JavaTrackViewModel
import com.example.ui.JavaTrackViewModelFactory
import com.example.ui.screens.LessonDetailScreen
import com.example.ui.screens.PlaygroundScreen
import com.example.ui.screens.QuizChallengesScreen
import com.example.ui.screens.RoadmapScreen
import com.example.ui.screens.StudyLogScreen
import com.example.ui.theme.MyApplicationTheme

enum class NavTab(val titleBn: String, val icon: ImageVector, val tag: String) {
    ROADMAP("রোডম্যাপ", Icons.Default.School, "nav_roadmap"),
    PLAYGROUND("প্লেগ্রাউন্ড", Icons.Default.Code, "nav_playground"),
    QUIZ("কুইজ ও চ্যালেঞ্জ", Icons.Default.Quiz, "nav_quiz"),
    LOG("স্টাডি লগ", Icons.Default.EventNote, "nav_log")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val context = LocalContext.current
                val database = remember { JavaTrackDatabase.getDatabase(context) }
                val repository = remember {
                    JavaTrackRepository(
                        lessonDao = database.lessonDao(),
                        userStatsDao = database.userStatsDao(),
                        studyLogDao = database.studyLogDao(),
                        snippetDao = database.snippetDao()
                    )
                }
                val factory = remember { JavaTrackViewModelFactory(repository) }
                val viewModel: JavaTrackViewModel = viewModel(factory = factory)

                JavaTrackApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun JavaTrackApp(viewModel: JavaTrackViewModel) {
    val selectedLesson by viewModel.selectedLesson.collectAsStateWithLifecycle()
    var currentTab by remember { mutableIntStateOf(0) }

    if (selectedLesson != null) {
        LessonDetailScreen(
            lesson = selectedLesson!!,
            viewModel = viewModel,
            onBack = { viewModel.selectLesson(null) }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.testTag("main_navigation_bar")
                ) {
                    NavTab.entries.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = currentTab == index,
                            onClick = { currentTab = index },
                            icon = { Icon(imageVector = tab.icon, contentDescription = tab.titleBn) },
                            label = { Text(tab.titleBn) },
                            modifier = Modifier.testTag(tab.tag)
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 680.dp)
                ) {
                    when (currentTab) {
                        0 -> RoadmapScreen(
                            viewModel = viewModel,
                            onOpenLesson = { lesson -> viewModel.selectLesson(lesson) }
                        )
                        1 -> PlaygroundScreen(viewModel = viewModel)
                        2 -> QuizChallengesScreen(viewModel = viewModel)
                        3 -> StudyLogScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
