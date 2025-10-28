@file:OptIn(ExperimentalMaterial3Api::class)

package com.printology.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.printology.app.R
import com.printology.app.services.GeminiAIService
import com.printology.app.ui.components.BottomNavigationBar
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AIChatScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val aiService = remember { GeminiAIService(context) }
    val listState = rememberLazyListState()

    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }
    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Auto scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // Welcome message
    LaunchedEffect(Unit) {
        if (messages.isEmpty()) {
            messages = listOf(ChatMessage(
                "Halo! 👋 Saya AI Assistant Printology. Saya siap membantu Anda dengan semua kebutuhan cetak dan informasi layanan kami. Ada yang bisa saya bantu?",
                false
            ))
        }
    }

    // Function to handle sending messages
    val sendMessage: (String) -> Unit = { userInput ->
        if (userInput.isNotBlank() && !isLoading) {
            val userMessage = userInput.trim()
            // Add user message immediately
            messages = messages + ChatMessage(userMessage, true)
            inputText = ""
            isLoading = true
            keyboardController?.hide()

            scope.launch {
                try {
                    val result = aiService.generateResponse(userMessage)
                    isLoading = false

                    if (result.success && result.response != null) {
                        messages = messages + ChatMessage(result.response, false)
                    } else {
                        messages = messages + ChatMessage(
                            "Maaf, terjadi kesalahan. ${result.error ?: "Silakan coba lagi."}",
                            false
                        )
                    }
                } catch (e: Exception) {
                    isLoading = false
                    messages = messages + ChatMessage(
                        "Maaf, tidak dapat menghubungi AI. Periksa koneksi internet Anda.",
                        false
                    )
                }

                // Auto scroll to bottom after AI response
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = Color(0xFFF8FAFC)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Modern Header dengan Glass Morphism
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                        clip = true
                    ),
                color = Color.White.copy(alpha = 0.95f),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // AI Avatar dengan gradient border
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .drawWithCache {
                                onDrawWithContent {
                                    drawCircle(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFF2563EB),
                                                Color(0xFF9333EA),
                                                Color(0xFFEC4899)
                                            )
                                        ),
                                        radius = size.width / 2 + 2.dp.toPx(),
                                        style = Stroke(width = 2.dp.toPx())
                                    )
                                    drawCircle(
                                        color = Color(0xFFF8FAFC),
                                        radius = size.width / 2
                                    )
                                    drawContent()
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF2563EB),
                                            Color(0xFF9333EA)
                                        )
                                    )
                                )
                                .align(Alignment.Center),
                            contentAlignment = Alignment.Center
                        ) {
                            // Gunakan icon AI Printology dari drawable
                            Icon(
                                painter = painterResource(id = R.drawable.ai_printology),
                                contentDescription = "AI Assistant",
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Printology AI",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "Assistant Cetak Digital",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Status indicator dengan animasi
                    AnimatedVisibility(
                        visible = !isLoading,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981))
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Online",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF10B981),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = isLoading,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(14.dp),
                                strokeWidth = 2.dp,
                                color = Color(0xFF2563EB)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Typing",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF2563EB),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Chat Messages dengan background gradient
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFF8FAFC),
                                Color(0xFFF1F5F9)
                            )
                        )
                    ),
                reverseLayout = false,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages, key = { it.timestamp }) { message ->
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        ) + fadeIn(),
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        ChatBubble(message)
                    }
                }

                if (isLoading) {
                    item {
                        ThinkingIndicator()
                    }
                }
            }

            // Quick Suggestions
            if (messages.size <= 2 && !isLoading) {
                QuickSuggestions(
                    onSuggestionClick = { suggestion ->
                        inputText = suggestion
                        keyboardController?.show()
                    }
                )
            }

            // Modern Input Area dengan glass effect
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                        clip = true
                    ),
                color = Color.White.copy(alpha = 0.9f),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            ) {
                Column {
                    // Typing indicator when loading
                    AnimatedVisibility(
                        visible = isLoading,
                        enter = fadeIn(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(300))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = Color(0xFF2563EB)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "AI Assistant sedang mengetik...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF475569),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Input Field dengan desain modern
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = {
                                Text(
                                    "Tanya tentang layanan cetak...",
                                    color = Color(0xFF94A3B8),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    clip = true
                                ),
                            enabled = !isLoading,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = Color(0xFF2563EB),
                                textColor = Color(0xFF1E293B)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            singleLine = false,
                            maxLines = 3,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                    sendMessage(inputText)
                                }
                            ),
                            trailingIcon = {
                                if (inputText.isNotEmpty() && !isLoading) {
                                    IconButton(
                                        onClick = { inputText = "" }
                                    ) {
                                        Icon(
                                            Icons.Filled.Close,
                                            contentDescription = "Clear",
                                            tint = Color(0xFF64748B)
                                        )
                                    }
                                }
                            }
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // Enhanced Send Button yang diperbaiki
                        SendButton(
                            inputText = inputText,
                            isLoading = isLoading,
                            onClick = { sendMessage(inputText) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SendButton(
    inputText: String,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    val isEnabled = inputText.isNotBlank() && !isLoading

    Box(
        modifier = Modifier
            .size(56.dp)
            .shadow(
                elevation = if (isEnabled) 8.dp else 2.dp,
                shape = CircleShape,
                clip = true
            )
            .clickable(
                enabled = isEnabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = if (isEnabled) listOf(
                            Color(0xFF2563EB),
                            Color(0xFF3B82F6),
                            Color(0xFF60A5FA)
                        ) else listOf(
                            Color(0xFFCBD5E1),
                            Color(0xFF94A3B8)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Send Message",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Pulse animation for enabled state
        if (isEnabled && !isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.3f),
                                Color.Transparent
                            ),
                            radius = 0.8f
                        ),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        // AI Avatar for AI messages
        if (!message.isUser) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF475569),
                                Color(0xFF64748B)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ai_printology),
                    contentDescription = "AI Assistant",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            // Sender name
            Text(
                text = if (message.isUser) "Anda" else "Printology AI",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF64748B),
                modifier = Modifier.padding(bottom = 2.dp)
            )

            // Message bubble
            Surface(
                shape = when {
                    message.isUser -> RoundedCornerShape(20.dp, 8.dp, 20.dp, 20.dp)
                    else -> RoundedCornerShape(8.dp, 20.dp, 20.dp, 20.dp)
                },
                shadowElevation = if (message.isUser) 6.dp else 4.dp,
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            if (message.isUser) {
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF2563EB),
                                        Color(0xFF3B82F6),
                                        Color(0xFF60A5FA)
                                    )
                                )
                            } else {
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF475569),
                                        Color(0xFF64748B),
                                        Color(0xFF94A3B8)
                                    )
                                )
                            }
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = message.text,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp
                    )
                }
            }

            // Timestamp
            Text(
                text = formatTime(message.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF94A3B8),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // User avatar for user messages
        if (message.isUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF2563EB),
                                Color(0xFF3B82F6)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "You",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun ThinkingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF475569),
                            Color(0xFF64748B)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ai_printology),
                contentDescription = "AI Assistant",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            shape = RoundedCornerShape(8.dp, 20.dp, 20.dp, 20.dp),
            shadowElevation = 2.dp,
            modifier = Modifier.widthIn(max = 120.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF475569))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Dot()
                    Spacer(modifier = Modifier.width(6.dp))
                    Dot(delay = 150)
                    Spacer(modifier = Modifier.width(6.dp))
                    Dot(delay = 300)
                }
            }
        }
    }
}

@Composable
fun Dot(delay: Int = 0) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(delay) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 600, delayMillis = delay)),
        exit = fadeOut(animationSpec = tween(durationMillis = 600))
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

@Composable
fun QuickSuggestions(onSuggestionClick: (String) -> Unit) {
    val suggestions = listOf(
        "Apa saja layanan cetak yang tersedia?",
        "Berapa harga cetak banner?",
        "Bagaimana cara order online?",
        "Ada promo bulan ini?",
        "Berapa lama proses cetak?",
        "Bisa cetak stiker custom?"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            "💡 Pertanyaan cepat:",
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF475569),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Using Row with wrap for simple flow layout
        Column {
            suggestions.chunked(2).forEach { rowSuggestions ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    rowSuggestions.forEach { suggestion ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            shadowElevation = 4.dp,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onSuggestionClick(suggestion) }
                        ) {
                            Text(
                                text = suggestion,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF475569),
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                lineHeight = 16.sp
                            )
                        }
                    }
                    // Fill empty space if row has less than 2 items
                    if (rowSuggestions.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

private fun formatTime(timestamp: Long): String {
    return android.text.format.DateFormat.format("HH:mm", timestamp).toString()
}