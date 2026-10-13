package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CodeBackground
import com.example.ui.theme.CodeComment
import com.example.ui.theme.CodeKeyword
import com.example.ui.theme.CodeNumber
import com.example.ui.theme.CodeString
import com.example.ui.theme.CodeType
import com.example.ui.theme.JavaEmerald
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val JAVA_KEYWORDS = setOf(
    "abstract", "boolean", "break", "byte", "case", "catch", "char", "class",
    "continue", "default", "do", "double", "else", "enum", "extends", "final",
    "finally", "float", "for", "if", "implements", "import", "instanceof", "int",
    "interface", "long", "native", "new", "package", "private", "protected",
    "public", "return", "short", "static", "strictfp", "super", "switch",
    "synchronized", "this", "throw", "throws", "transient", "try", "void",
    "volatile", "while", "true", "false", "null", "var"
)

val JAVA_BUILTIN_TYPES = setOf(
    "String", "System", "out", "println", "print", "Scanner", "ArrayList",
    "HashMap", "List", "Map", "Set", "HashSet", "Integer", "Double",
    "Boolean", "Character", "StringBuilder", "Thread", "Runnable", "Override",
    "Exception", "ArithmeticException"
)

fun highlightJavaCode(code: String): AnnotatedString {
    return buildAnnotatedString {
        val lines = code.split("\n")
        lines.forEachIndexed { lineIdx, line ->
            var i = 0
            val len = line.length

            while (i < len) {
                // Check single-line comment
                if (i < len - 1 && line[i] == '/' && line[i + 1] == '/') {
                    val commentText = line.substring(i)
                    append(AnnotatedString(commentText, SpanStyle(color = CodeComment, fontWeight = FontWeight.Normal)))
                    i = len
                    break
                }

                // Check string literals
                if (line[i] == '"') {
                    val start = i
                    i++
                    while (i < len && line[i] != '"') {
                        if (line[i] == '\\' && i + 1 < len) i++ // escape
                        i++
                    }
                    if (i < len && line[i] == '"') i++
                    val str = line.substring(start, i)
                    append(AnnotatedString(str, SpanStyle(color = CodeString, fontWeight = FontWeight.Normal)))
                    continue
                }

                // Check character literals
                if (line[i] == '\'') {
                    val start = i
                    i++
                    while (i < len && line[i] != '\'') {
                        if (line[i] == '\\' && i + 1 < len) i++
                        i++
                    }
                    if (i < len && line[i] == '\'') i++
                    val ch = line.substring(start, i)
                    append(AnnotatedString(ch, SpanStyle(color = CodeNumber, fontWeight = FontWeight.Normal)))
                    continue
                }

                // Check word tokens (identifiers & keywords)
                if (line[i].isLetter() || line[i] == '_' || line[i] == '$' || line[i] == '@') {
                    val start = i
                    while (i < len && (line[i].isLetterOrDigit() || line[i] == '_' || line[i] == '$' || line[i] == '@')) {
                        i++
                    }
                    val word = line.substring(start, i)
                    when {
                        JAVA_KEYWORDS.contains(word) -> {
                            append(AnnotatedString(word, SpanStyle(color = CodeKeyword, fontWeight = FontWeight.Bold)))
                        }
                        JAVA_BUILTIN_TYPES.contains(word) -> {
                            append(AnnotatedString(word, SpanStyle(color = CodeType, fontWeight = FontWeight.SemiBold)))
                        }
                        else -> {
                            append(AnnotatedString(word, SpanStyle(color = Color(0xFFE2E8F0))))
                        }
                    }
                    continue
                }

                // Numbers
                if (line[i].isDigit()) {
                    val start = i
                    while (i < len && (line[i].isDigit() || line[i] == '.' || line[i] == 'L' || line[i] == 'f')) {
                        i++
                    }
                    val num = line.substring(start, i)
                    append(AnnotatedString(num, SpanStyle(color = CodeNumber, fontWeight = FontWeight.Normal)))
                    continue
                }

                // Symbols
                val charStr = line[i].toString()
                append(AnnotatedString(charStr, SpanStyle(color = Color(0xFF94A3B8))))
                i++
            }

            if (lineIdx < lines.size - 1) {
                append("\n")
            }
        }
    }
}

@Composable
fun JavaCodeSnippetCard(
    code: String,
    expectedOutput: String? = null,
    onCodeRun: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    title: String = "Main.java"
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isRunning by remember { mutableStateOf(false) }
    var outputText by remember { mutableStateOf<String?>(null) }
    val horizontalScroll = rememberScrollState()

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = CodeBackground,
        shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Terminal fake dots
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFEF4444)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFF59E0B)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF10B981)))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = title,
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Java Code", code)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "কোড কপি করা হয়েছে!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(36.dp).testTag("copy_code_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Code",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    if (expectedOutput != null) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    isRunning = true
                                    delay(400) // Realistic compilation simulation
                                    isRunning = false
                                    outputText = expectedOutput
                                    onCodeRun?.invoke()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = JavaEmerald,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(34.dp).testTag("run_code_button")
                        ) {
                            if (isRunning) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Run",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("রান করুন", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Code Body
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                    .padding(12.dp)
                    .horizontalScroll(horizontalScroll)
            ) {
                Text(
                    text = highlightJavaCode(code),
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    lineHeight = 20.sp
                )
            }

            // Output Terminal
            if (outputText != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = "Terminal",
                            tint = JavaEmerald,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "আউটপুট (Console)",
                            color = JavaEmerald,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = outputText ?: "",
                        color = Color(0xFFF8FAFC),
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
