package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.JavaTrackViewModel
import com.example.ui.theme.CodeBackground
import com.example.ui.theme.JavaBlueDark
import com.example.ui.theme.JavaEmerald
import com.example.ui.theme.JavaOrangeDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class CodeTemplate(
    val name: String,
    val nameBn: String,
    val code: String,
    val simulatedOutput: String
)

val PLAYGROUND_TEMPLATES = listOf(
    CodeTemplate(
        name = "Hello World",
        nameBn = "হ্যালো ওয়ার্ল্ড",
        code = """public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from Java Playground!");
        System.out.println("জাভায় কোডিং শিখছি প্রতিদিন 🚀");
    }
}""",
        simulatedOutput = "Hello from Java Playground!\nজাভায় কোডিং শিখছি প্রতিদিন 🚀"
    ),
    CodeTemplate(
        name = "Fibonacci",
        nameBn = "ফিবোনাচ্চি ধারা",
        code = """public class Main {
    public static void main(String[] args) {
        int n = 8;
        int first = 0, second = 1;
        System.out.print("ফিবোনাচ্চি ধারা: ");
        for (int i = 1; i <= n; i++) {
            System.out.print(first + " ");
            int next = first + second;
            first = second;
            second = next;
        }
        System.out.println();
    }
}""",
        simulatedOutput = "ফিবোনাচ্চি ধারা: 0 1 1 2 3 5 8 13"
    ),
    CodeTemplate(
        name = "OOP Bank",
        nameBn = "ব্যাংক একাউন্ট OOP",
        code = """class Account {
    String holder;
    double balance;

    public Account(String h, double b) {
        this.holder = h;
        this.balance = b;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println(holder + " এর একাউন্টে জমা: ৳" + amount);
    }
}

public class Main {
    public static void main(String[] args) {
        Account myAcc = new Account("নাঈম", 2000.0);
        myAcc.deposit(1500.0);
        System.out.println("মোট ব্যালেন্স: ৳" + myAcc.balance);
    }
}""",
        simulatedOutput = "নাঈম এর একাউন্টে জমা: ৳1500.0\nমোট ব্যালেন্স: ৳3500.0"
    ),
    CodeTemplate(
        name = "ArrayList",
        nameBn = "অ্যারে-লিস্ট কালেকশন",
        code = """import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("আম");
        fruits.add("কাঁঠাল");
        fruits.add("লিচু");

        for (int i = 0; i < fruits.size(); i++) {
            System.out.println((i + 1) + ". " + fruits.get(i));
        }
    }
}""",
        simulatedOutput = "1. আম\n2. কাঁঠাল\n3. লিচু"
    )
)

@Composable
fun PlaygroundScreen(
    viewModel: JavaTrackViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedTemplateIndex by remember { mutableStateOf(0) }
    var currentCode by remember { mutableStateOf(PLAYGROUND_TEMPLATES[0].code) }
    var terminalOutput by remember { mutableStateOf<String?>(null) }
    var isCompiling by remember { mutableStateOf(false) }
    var executionStats by remember { mutableStateOf<String?>(null) }

    var showSaveDialog by remember { mutableStateOf(false) }
    var snippetTitle by remember { mutableStateOf("") }
    var snippetNotes by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("playground_screen")
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Title Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "জাভা প্লেগ্রাউন্ড ও কোড রানার",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ইন্টারেক্টিভ জাভা কোড এডিটর ও সিমুলেটর",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(
                onClick = {
                    snippetTitle = PLAYGROUND_TEMPLATES[selectedTemplateIndex].nameBn
                    showSaveDialog = true
                },
                modifier = Modifier.testTag("save_snippet_button")
            ) {
                Icon(
                    imageVector = Icons.Default.BookmarkAdd,
                    contentDescription = "Save Snippet",
                    tint = JavaBlueDark
                )
            }
        }

        // Template chips row
        Text(
            text = "কোড টেমপ্লেট নির্বাচন করুন:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PLAYGROUND_TEMPLATES.forEachIndexed { index, template ->
                val isSelected = selectedTemplateIndex == index
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedTemplateIndex = index
                        currentCode = template.code
                        terminalOutput = null
                        executionStats = null
                    },
                    label = { Text(template.nameBn, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        // Code Editor Box
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = CodeBackground,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                // Editor Top bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFEF4444)))
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFF59E0B)))
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF10B981)))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Main.java",
                            color = Color(0xFF94A3B8),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                    }

                    Row {
                        IconButton(
                            onClick = {
                                currentCode = PLAYGROUND_TEMPLATES[selectedTemplateIndex].code
                                Toast.makeText(context, "কোড রিসেট করা হয়েছে", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.RestartAlt,
                                contentDescription = "Reset",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = { currentCode = "" },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Editable Text Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    BasicTextField(
                        value = currentCode,
                        onValueChange = { currentCode = it },
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("code_editor_input"),
                        textStyle = TextStyle(
                            color = Color(0xFFE2E8F0),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        ),
                        cursorBrush = SolidColor(JavaBlueDark)
                    )
                }
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        isCompiling = true
                        terminalOutput = null
                        executionStats = null
                        delay(600) // compilation simulation
                        isCompiling = false
                        // Check if current code matches template or custom
                        val output = if (currentCode.contains("System.out.println")) {
                            PLAYGROUND_TEMPLATES[selectedTemplateIndex].simulatedOutput
                        } else if (currentCode.isBlank()) {
                            "Error: কোন জাভা কোড পাওয়া যায়নি।"
                        } else {
                            "Build Successful!\nProgram executed without errors."
                        }
                        terminalOutput = output
                        executionStats = "Compilation: 0.28s | Memory: 14MB | Status: Success"
                        viewModel.recordCodeRun()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("run_playground_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = JavaEmerald
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isCompiling) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("কম্পাইল হচ্ছে...", fontWeight = FontWeight.Bold)
                } else {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Run")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("কোড রান করুন", fontWeight = FontWeight.Bold)
                }
            }

            OutlinedButton(
                onClick = {
                    snippetTitle = PLAYGROUND_TEMPLATES[selectedTemplateIndex].nameBn
                    showSaveDialog = true
                },
                modifier = Modifier
                    .height(48.dp)
                    .testTag("save_to_snippets_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.BookmarkAdd, contentDescription = "Save")
                Spacer(modifier = Modifier.width(4.dp))
                Text("সেভ করুন")
            }
        }

        // Output Console Terminal
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("terminal_output_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0F172A)
            )
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = "Console",
                            tint = JavaEmerald,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "আউটপুট টার্মিনাল (Java Console)",
                            color = JavaEmerald,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    if (executionStats != null) {
                        Text(
                            text = "✓ OK",
                            color = JavaEmerald,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF070B14), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    if (terminalOutput != null) {
                        Column {
                            Text(
                                text = terminalOutput ?: "",
                                color = Color(0xFFF8FAFC),
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace,
                                lineHeight = 18.sp
                            )
                            if (executionStats != null) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = executionStats ?: "",
                                    color = Color(0xFF64748B),
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "কোড রান করার জন্য ওপরের 'কোড রান করুন' বাটনে চাপুন...",
                            color = Color(0xFF64748B),
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

    // Save Snippet Dialog
    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("কোড স্নsnippet সেভ করুন") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = snippetTitle,
                        onValueChange = { snippetTitle = it },
                        label = { Text("স্নsnippet শিরোনাম") },
                        modifier = Modifier.fillMaxWidth().testTag("snippet_title_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = snippetNotes,
                        onValueChange = { snippetNotes = it },
                        label = { Text("সংক্ষিপ্ত নোট (ঐচ্ছিক)") },
                        modifier = Modifier.fillMaxWidth().testTag("snippet_notes_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (snippetTitle.isNotBlank()) {
                            viewModel.saveSnippet(snippetTitle, currentCode, snippetNotes)
                            showSaveDialog = false
                            Toast.makeText(context, "স্নsnippet সংরক্ষিত হয়েছে!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.testTag("confirm_save_snippet")
                ) {
                    Text("সংরক্ষণ করুন")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("বাতিল")
                }
            }
        )
    }
}
