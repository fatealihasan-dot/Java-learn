package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import com.example.ui.components.SegmentedTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CodingChallenge
import com.example.data.model.QuizQuestion
import com.example.ui.JavaTrackViewModel
import com.example.ui.components.JavaCodeSnippetCard
import com.example.ui.theme.JavaBlueDark
import com.example.ui.theme.JavaEmerald
import com.example.ui.theme.JavaOrangeDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizChallengesScreen(
    viewModel: JavaTrackViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("কুইজ অনুশীলন", "কোডিং চ্যালেঞ্জ")

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("quiz_challenges_screen")
    ) {
        SegmentedTabRow(
            tabs = tabs,
            icons = listOf(Icons.Default.Quiz, Icons.Default.Code),
            selectedIndex = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        when (selectedTab) {
            0 -> QuizSection(viewModel = viewModel)
            1 -> ChallengesSection(viewModel = viewModel)
        }
    }
}

@Composable
fun QuizSection(
    viewModel: JavaTrackViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val quizzes = viewModel.quizzes

    var currentQuestionIdx by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var totalCorrect by remember { mutableIntStateOf(0) }

    val currentQuiz = quizzes.getOrNull(currentQuestionIdx)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header Stats
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "প্রশ্ন ${currentQuestionIdx + 1} / ${quizzes.size}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "সঠিক উত্তর: $totalCorrect টি",
                            style = MaterialTheme.typography.bodySmall,
                            color = JavaEmerald,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Surface(
                        color = JavaOrangeDark.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = JavaOrangeDark,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "+20 XP প্রতিটি",
                                color = JavaOrangeDark,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        if (currentQuiz != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = currentQuiz.questionBn,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentQuiz.question,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        currentQuiz.options.forEachIndexed { index, option ->
                            val isSelected = selectedOption == index
                            val isCorrect = index == currentQuiz.correctIndex

                            val borderCol = when {
                                isSubmitted && isCorrect -> JavaEmerald
                                isSubmitted && isSelected && !isCorrect -> Color(0xFFEF4444)
                                isSelected -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                            }

                            val bgCol = when {
                                isSubmitted && isCorrect -> JavaEmerald.copy(alpha = 0.12f)
                                isSubmitted && isSelected && !isCorrect -> Color(0xFFEF4444).copy(alpha = 0.12f)
                                isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                else -> Color.Transparent
                            }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .border(1.5.dp, borderCol, RoundedCornerShape(12.dp))
                                    .clickable(enabled = !isSubmitted) {
                                        selectedOption = index
                                    },
                                shape = RoundedCornerShape(12.dp),
                                color = bgCol
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
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

                        Spacer(modifier = Modifier.height(16.dp))

                        if (!isSubmitted) {
                            Button(
                                onClick = {
                                    if (selectedOption != null) {
                                        isSubmitted = true
                                        if (selectedOption == currentQuiz.correctIndex) {
                                            totalCorrect++
                                            viewModel.recordQuizCompletion(20)
                                            Toast.makeText(context, "সঠিক উত্তর! +20 XP", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(context, "একটি উত্তর নির্বাচন করুন", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().testTag("submit_quiz_answer_button"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("উত্তর যাচাই করুন", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Column {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (selectedOption == currentQuiz.correctIndex)
                                        JavaEmerald.copy(alpha = 0.12f)
                                    else
                                        Color(0xFFEF4444).copy(alpha = 0.12f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = if (selectedOption == currentQuiz.correctIndex)
                                                "🎉 সঠিক উত্তর!"
                                            else
                                                "❌ উত্তরটি ভুল হয়েছে",
                                            fontWeight = FontWeight.Bold,
                                            color = if (selectedOption == currentQuiz.correctIndex) JavaEmerald else Color(0xFFEF4444)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = currentQuiz.explanationBn,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = {
                                        if (currentQuestionIdx < quizzes.size - 1) {
                                            currentQuestionIdx++
                                            selectedOption = null
                                            isSubmitted = false
                                        } else {
                                            Toast.makeText(context, "সবগুলো কুইজ সম্পন্ন হয়েছে!", Toast.LENGTH_LONG).show()
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth().testTag("next_quiz_button"),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = if (currentQuestionIdx < quizzes.size - 1) "পরবর্তী প্রশ্ন ➔" else "কুইজ সম্পন্ন হয়েছে",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChallengesSection(
    viewModel: JavaTrackViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val challenges = viewModel.challenges

    val solvedMap = remember { mutableStateMapOf<String, Boolean>() }
    val runningMap = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(challenges, key = { it.id }) { challenge ->
            val isSolved = solvedMap[challenge.id] == true
            val isRunning = runningMap[challenge.id] == true

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("challenge_card_${challenge.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = JavaBlueDark.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = challenge.difficulty,
                                color = JavaBlueDark,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            color = JavaOrangeDark.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "+${challenge.xpReward} XP",
                                color = JavaOrangeDark,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = challenge.titleBn,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = challenge.title,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = challenge.descriptionBn,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Hint
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Hint",
                            tint = JavaOrangeDark,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ইঙ্গিত: ${challenge.hintBn}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    JavaCodeSnippetCard(
                        code = challenge.starterCode,
                        title = "Solution.java"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                runningMap[challenge.id] = true
                                delay(600)
                                runningMap[challenge.id] = false
                                solvedMap[challenge.id] = true
                                viewModel.recordQuizCompletion(challenge.xpReward)
                                Toast.makeText(context, "টেস্ট কেস পাস করেছে! +${challenge.xpReward} XP", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSolved) JavaEmerald else MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("solve_challenge_button_${challenge.id}"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isRunning) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("টেস্ট কেস যাচাই হচ্ছে...")
                        } else if (isSolved) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("সমাধান সফল (Passed ✓)", fontWeight = FontWeight.Bold)
                        } else {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("কোড যাচাই করুন (Run Test)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
