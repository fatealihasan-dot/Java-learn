package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import com.example.ui.components.SegmentedTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.SavedSnippetEntity
import com.example.data.model.StudyLogEntity
import com.example.ui.JavaTrackViewModel
import com.example.ui.components.JavaCodeSnippetCard
import com.example.ui.theme.JavaBlueDark
import com.example.ui.theme.JavaEmerald
import com.example.ui.theme.JavaOrangeDark
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StudyLogScreen(
    viewModel: JavaTrackViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("স্টাডি লগ ট্র্যাকার", "সংরক্ষিত কোড স্নsnippet")

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("study_log_screen")
    ) {
        SegmentedTabRow(
            tabs = tabs,
            icons = listOf(Icons.Default.EventNote, Icons.Default.Bookmark),
            selectedIndex = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        when (selectedTab) {
            0 -> StudyLogsContent(viewModel = viewModel)
            1 -> SavedSnippetsContent(viewModel = viewModel)
        }
    }
}

@Composable
fun StudyLogsContent(
    viewModel: JavaTrackViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val logs by viewModel.studyLogs.collectAsStateWithLifecycle()
    val stats by viewModel.userStats.collectAsStateWithLifecycle()

    var showAddDialog by remember { mutableStateOf(false) }
    var topicInput by remember { mutableStateOf("") }
    var minutesInput by remember { mutableStateOf("30") }
    var notesInput by remember { mutableStateOf("") }

    val quickMinutes = listOf("15", "30", "45", "60")

    val dailyGoal = stats.currentGoalMinutesPerDay
    val todayMinutes = stats.totalMinutesStudied
    val progressRatio = (todayMinutes.toFloat() / dailyGoal).coerceIn(0f, 1f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Goal Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(JavaEmerald.copy(alpha = 0.15f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = JavaEmerald,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "দৈনিক পড়ার লক্ষ্যমাত্রা (Daily Goal)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "লক্ষ্য: $dailyGoal মিনিট / দিন",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Button(
                            onClick = { showAddDialog = true },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("open_add_study_log_dialog")
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("লগ করুন")
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    LinearProgressIndicator(
                        progress = { progressRatio },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = JavaEmerald,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        strokeCap = StrokeCap.Round
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "মোট অধ্যয়ন: $todayMinutes মিনিট (${stats.streakDays} দিনের স্ট্রিক 🔥)",
                        style = MaterialTheme.typography.bodySmall,
                        color = JavaEmerald,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Section Title
        item {
            Text(
                text = "পূর্ববর্তী পড়ার সেশনসমূহ (${logs.size} টি)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (logs.isEmpty()) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "কোনো স্টাডি লগ রেকর্ড করা হয়নি",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "আপনার প্রতিদিনের পড়ার সময় রেকর্ড করতে উপরের বাটনে চাপুন।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(logs, key = { it.id }) { log ->
                StudyLogItemCard(
                    log = log,
                    onDelete = {
                        viewModel.deleteStudyLog(log)
                        Toast.makeText(context, "লগ মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("নতুন স্টাডি সেশন রেকর্ড করুন") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = topicInput,
                        onValueChange = { topicInput = it },
                        label = { Text("কী বিষয় পড়েছেন? (টপিক)") },
                        placeholder = { Text("যেমন: OOP Polymorphism") },
                        modifier = Modifier.fillMaxWidth().testTag("log_topic_input"),
                        singleLine = true
                    )

                    Text(
                        text = "পড়ার সময় (মিনিট):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        quickMinutes.forEach { min ->
                            FilterChip(
                                selected = minutesInput == min,
                                onClick = { minutesInput = min },
                                label = { Text("$min মি.") }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = minutesInput,
                        onValueChange = { minutesInput = it },
                        label = { Text("কাস্টম মিনিট") },
                        modifier = Modifier.fillMaxWidth().testTag("log_minutes_input"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = notesInput,
                        onValueChange = { notesInput = it },
                        label = { Text("পড়ার নোট বা শিখন (ঐচ্ছিক)") },
                        placeholder = { Text("যেমন: মেথড ওভাররাইডিং স্পষ্ট বুঝতে পেরেছি।") },
                        modifier = Modifier.fillMaxWidth().testTag("log_notes_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val mins = minutesInput.toIntOrNull() ?: 30
                        val topic = topicInput.ifBlank { "জাভা প্রোগ্রামিং স্টাডি" }
                        viewModel.addStudyLog(topic, mins, notesInput)
                        showAddDialog = false
                        topicInput = ""
                        notesInput = ""
                        Toast.makeText(context, "সেশন রেকর্ড হয়েছে! +${mins * 2} XP", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.testTag("confirm_add_study_log")
                ) {
                    Text("সেভ করুন")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("বাতিল")
                }
            }
        )
    }
}

@Composable
fun StudyLogItemCard(
    log: StudyLogEntity,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateStr = remember(log.timestamp) {
        val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
        sdf.format(Date(log.timestamp))
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(JavaBlueDark.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${log.durationMinutes}m",
                    color = JavaBlueDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = log.topicName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dateStr,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )
                if (log.notes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = log.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun SavedSnippetsContent(
    viewModel: JavaTrackViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snippets by viewModel.savedSnippets.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "সংরক্ষিত জাভা কোড ও নোটস (${snippets.size} টি)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (snippets.isEmpty()) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "কোনো কোড স্নsnippet সংরক্ষিত নেই",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "'প্লেগ্রাউন্ড' থেকে যেকোনো কোড সহজেই সেভ করতে পারবেন।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(snippets, key = { it.id }) { snippet ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = snippet.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(
                                onClick = {
                                    viewModel.deleteSnippet(snippet)
                                    Toast.makeText(context, "স্নsnippet মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        if (snippet.notes.isNotBlank()) {
                            Text(
                                text = snippet.notes,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        JavaCodeSnippetCard(
                            code = snippet.code,
                            title = "${snippet.title}.java"
                        )
                    }
                }
            }
        }
    }
}
