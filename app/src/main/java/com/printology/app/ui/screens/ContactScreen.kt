@file:OptIn(ExperimentalMaterial3Api::class)

package com.printology.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.printology.app.services.EmailService
import com.printology.app.ui.components.BottomNavigationBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ContactScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val emailService = remember { EmailService(context) } // ← ENABLE INI

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var selectedService by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var submitMessage by remember { mutableStateOf<String?>(null) }

    // Real-time clock
    var currentTime by remember { mutableStateOf(Date()) }

    // Update time every second
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = Date()
        }
    }

    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))

    // Contact info data
    data class ContactInfo(
        val icon: @Composable () -> Unit,
        val title: String,
        val info: String
    )

    val contactInfoList = listOf(
        ContactInfo(
            icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Location") },
            title = "Alamat",
            info = "Jl. Raya Lenteng Agung No. 123, Jakarta LA"
        ),
        ContactInfo(
            icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone") },
            title = "Telepon",
            info = "+62 822-6009-8942"
        ),
        ContactInfo(
            icon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
            title = "Email",
            info = "rinarza8@gmail.com"
        ),
        ContactInfo(
            icon = { Icon(Icons.Filled.Schedule, contentDescription = "Schedule") },
            title = "Jam Operasional",
            info = "Senin - Sabtu: 08.00 - 20.00 WIB"
        )
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8FAFC), // slate-50 equivalent
                            Color(0xFFDBEAFE).copy(alpha = 0.3f) // blue-50/30 equivalent
                        )
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header Section
            Text(
                text = "Hubungi Kami",
                color = Color(0xFF2563EB),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Siap melayani kebutuhan cetak Anda dengan cepat dan profesional",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Real-time Clock
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3B82F6) // blue-500
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Waktu Indonesia Barat",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Text(
                        text = timeFormat.format(currentTime),
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = dateFormat.format(currentTime),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            // Contact Information Grid
            Text(
                text = "Informasi Kontak",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            contactInfoList.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    row.forEach { contact ->
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.8f)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .padding(bottom = 12.dp),
                                    shape = MaterialTheme.shapes.medium,
                                    color = Color(0xFF3B82F6).copy(alpha = 0.1f)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        contact.icon()
                                    }
                                }

                                Text(
                                    text = contact.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Text(
                                    text = contact.info,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }

            // Alternative Contact Options
            Text(
                text = "Atau hubungi kami langsung via:",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // WhatsApp Button
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    color = Color(0xFF25D366), // WhatsApp green
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("📱", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "WhatsApp",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Email Button
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    color = Color(0xFF2563EB), // Blue
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("✉️", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Email",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Contact Form
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama Lengkap *") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email *") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Nomor Telepon") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = selectedService,
                onValueChange = { selectedService = it },
                label = { Text("Layanan") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting,
                placeholder = { Text("Pilih layanan (opsional)") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Pesan *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                enabled = !isSubmitting
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (submitMessage != null) {
                Text(
                    text = submitMessage!!,
                    color = if (submitMessage!!.contains("berhasil"))
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank() && message.isNotBlank()) {
                        isSubmitting = true
                        submitMessage = null

                        scope.launch {
                            emailService.sendContactEmail(
                                EmailService.ContactForm(
                                    name = name,
                                    email = email,
                                    phone = phone,
                                    message = message,
                                    service = selectedService
                                )
                            ) { result ->
                                isSubmitting = false
                                when (result) {
                                    is EmailService.EmailResult.Success -> {
                                        submitMessage = result.message
                                        // Clear form setelah berhasil
                                        name = ""
                                        email = ""
                                        phone = ""
                                        message = ""
                                        selectedService = ""
                                    }
                                    is EmailService.EmailResult.Error -> {
                                        submitMessage = result.message
                                    }
                                }
                            }
                        }
                    } else {
                        submitMessage = "Harap isi nama, email, dan pesan"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting && name.isNotBlank() && email.isNotBlank() && message.isNotBlank()
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Mengirim...")
                } else {
                    Text("Kirim Pesan")
                }
            }
        }
    }
}