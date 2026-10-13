package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LessonEntity
import com.example.ui.JavaTrackViewModel
import com.example.ui.components.JavaCodeSnippetCard
import com.example.ui.theme.JavaBlueDark
import com.example.ui.theme.JavaEmerald
import com.example.ui.theme.JavaOrangeDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonDetailScreen(
    lesson: LessonEntity,
    viewModel: JavaTrackViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    BackHandler { onBack() }

    val associatedQuiz = remember(lesson.id) {
        viewModel.quizzes.find { it.lessonId == lesson.id }
    }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var quizSubmitted by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.testTag("lesson_detail_screen"),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = lesson.titleBn,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            viewModel.toggleLessonCompletion(lesson)
                            val msg = if (!lesson.isCompleted) "পাঠ সম্পন্ন হয়েছে! +${lesson.xpReward} XP" else "চিহ্নিত অপসারিত"
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (lesson.isCompleted) JavaEmerald else MaterialTheme.colorScheme.primaryContainer,
                            contentColor = if (lesson.isCompleted) Color.White else MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(end = 8.dp).testTag("detail_complete_button")
                    ) {
                        Icon(
                            imageVector = if (lesson.isCompleted) Icons.Default.CheckCircle else Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (lesson.isCompleted) "সম্পন্ন ✓" else "সম্পন্ন করুন",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Module Title & Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = lesson.moduleTitleBn,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        color = JavaOrangeDark.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "+${lesson.xpReward} XP",
                            color = JavaOrangeDark,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        color = JavaBlueDark.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${lesson.estimatedMinutes} মিনিট",
                            color = JavaBlueDark,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Titles
            Column {
                Text(
                    text = lesson.titleBn,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Key Takeaways Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(JavaOrangeDark.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Key Concept",
                            tint = JavaOrangeDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "সংক্ষিপ্ত সারমর্ম (Key Takeaway)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = JavaOrangeDark
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = lesson.keyPointsJson,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Detailed Theory Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "তাত্ত্বিক ব্যাখ্যা ও নিয়মাবলী",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = lesson.theoryContentBn,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    )
                }
            }

            // Java Code Snippet with Run in Console
            Column {
                Text(
                    text = "কোড উদাহরণ ও কনসোল রানার",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                JavaCodeSnippetCard(
                    code = lesson.codeExample,
                    expectedOutput = lesson.expectedOutput,
                    onCodeRun = {
                        viewModel.recordCodeRun()
                        Toast.makeText(context, "কোড সফলভাবে নির্বাহ হয়েছে! +5 XP", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // Quick MCQ Quiz if available for this lesson
            if (associatedQuiz != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("lesson_quiz_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = "Quiz",
                                tint = JavaBlueDark,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ধারণা যাচাই কুইজ (Quick Check)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = associatedQuiz.questionBn,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        associatedQuiz.options.forEachIndexed { index, option ->
                            val isSelected = selectedOptionIndex == index
                            val isCorrect = index == associatedQuiz.correctIndex

                            val borderCol = when {
                                quizSubmitted && isCorrect -> JavaEmerald
                                quizSubmitted && isSelected && !isCorrect -> Color(0xFFEF4444)
                                isSelected -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                            }

                            val bgCol = when {
                                quizSubmitted && isCorrect -> JavaEmerald.copy(alpha = 0.1f)
                                quizSubmitted && isSelected && !isCorrect -> Color(0xFFEF4444).copy(alpha = 0.1f)
                                isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                else -> Color.Transparent
                            }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .border(1.dp, borderCol, RoundedCornerShape(10.dp))
                                    .clickable(enabled = !quizSubmitted) {
                                        selectedOptionIndex = index
                                    },
                                shape = RoundedCornerShape(10.dp),
                                color = bgCol
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${('A' + index)}. $option",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (!quizSubmitted) {
                            Button(
                                onClick = {
                                    if (selectedOptionIndex != null) {
                                        quizSubmitted = true
                                        if (selectedOptionIndex == associatedQuiz.correctIndex) {
                                            viewModel.recordQuizCompletion(25)
                                            Toast.makeText(context, "সঠিক উত্তর! +25 XP", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(context, "একটি উত্তর নির্বাচন করুন", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().testTag("submit_quiz_button"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("উত্তর যাচাই করুন", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                color = if (selectedOptionIndex == associatedQuiz.correctIndex)
                                    JavaEmerald.copy(alpha = 0.12f)
                                else
                                    Color(0xFFEF4444).copy(alpha = 0.12f)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = if (selectedOptionIndex == associatedQuiz.correctIndex)
                                            "🎉 চমৎকার! সঠিক হয়েছে।"
                                        else
                                            "❌ উত্তরটি ভুল ছিল।",
                                        fontWeight = FontWeight.Bold,
                                        color = if (selectedOptionIndex == associatedQuiz.correctIndex) JavaEmerald else Color(0xFFEF4444)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = associatedQuiz.explanationBn,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
