@file:OptIn(ExperimentalMaterial3Api::class)

package com.printology.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.printology.app.ui.components.BottomNavigationBar
import kotlinx.coroutines.delay

data class Service(
    val id: String,
    val name: String,
    val description: String,
    val price: String,
    val priceValue: Int,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val isCustom: Boolean = false
)

@Composable
fun HomeScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }

    val services = listOf(
        Service(
            id = "fotokopi",
            name = "Fotokopi",
            description = "Fotokopi hitam putih dan berwarna dengan kualitas terbaik",
            price = "Rp500/Lembar",
            priceValue = 500,
            icon = Icons.Default.ContentCopy
        ),
        Service(
            id = "print-dokumen",
            name = "Print Dokumen",
            description = "Cetak dokumen dengan hasil tajam dan rapi",
            price = "Rp1.000/Lembar",
            priceValue = 1000,
            icon = Icons.Default.Print
        ),
        Service(
            id = "sticker",
            name = "Sticker",
            description = "Sticker custom dengan berbagai ukuran dan bahan",
            price = "Rp4.000 - Rp10.000",
            priceValue = 4000,
            icon = Icons.Default.StickyNote2
        ),
        Service(
            id = "laminating",
            name = "Laminating Kartu",
            description = "Laminating kartu nama, ID card, dan lainnya",
            price = "Rp2.000/Kartu",
            priceValue = 2000,
            icon = Icons.Default.CreditCard
        ),
        Service(
            id = "cetak-foto",
            name = "Cetak Foto",
            description = "Cetak foto berbagai ukuran dengan kualitas premium",
            price = "Rp2.000 - Rp8.000",
            priceValue = 2000,
            icon = Icons.Default.Photo
        ),
        Service(
            id = "produk-kustom",
            name = "Produk Kustom",
            description = "Sticker, poster, photocard, kartu ucapan custom",
            price = "Harga Bervariasi",
            priceValue = 0,
            icon = Icons.Default.AutoAwesome,
            isCustom = true
        )
    )

    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    val logoAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    val titleAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 300)
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 600)
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8FAFC),
                            Color(0xFFF1F5F9)
                        )
                    )
                ),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Hero Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .alpha(logoAlpha),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Modern Badge
                    Surface(
                        modifier = Modifier
                            .shadow(8.dp, RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFF2563EB).copy(alpha = 0.1f),
                        border = CardDefaults.outlinedCardBorder()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = "Fast",
                                tint = Color(0xFF2563EB),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Layanan Cetak Tercepat di Kota",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF2563EB),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Logo
                    Surface(
                        modifier = Modifier
                            .size(100.dp)
                            .shadow(12.dp, RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Print,
                                contentDescription = "Print Icon",
                                modifier = Modifier.size(50.dp),
                                tint = Color(0xFF2563EB)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Title
                    Text(
                        text = "Printology",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.alpha(titleAlpha)
                    )

                    Text(
                        text = "Go Print, Go Fast!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.alpha(titleAlpha)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Description Card
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Layanan cetak dan fotokopi cepat dengan harga terjangkau untuk",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF64748B),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "pelajar, mahasiswa, profesional, dan UMKM",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color(0xFF2563EB),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Stats Section
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .alpha(contentAlpha),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(number = "500+", label = "Pelanggan Puas")
                    StatItem(number = "10 Menit", label = "Rata-rata Waktu")
                    StatItem(number = "Rp500", label = "Mulai Dari")
                }
            }

            // Services Section Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .alpha(contentAlpha)
                ) {
                    Text(
                        text = "Layanan Kami",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Berbagai pilihan layanan cetak dengan harga terjangkau dan kualitas terjamin",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }

            // Services Grid
            items(services) { service ->
                ServiceCard(
                    service = service,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .alpha(contentAlpha),
                    onAddToCart = {
                        if (service.isCustom) {
                            navController.navigate("contact")
                        } else {
                            // Handle add to cart for regular services
                        }
                    }
                )
            }

            // Pre-Order Banner
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .alpha(contentAlpha),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF0F9FF),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Pre-Order",
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Sistem Pre-Order tersedia untuk semua produk kustom",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF2563EB),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Quick Actions
            item {
                Text(
                    text = "Akses Cepat",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .alpha(contentAlpha)
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .alpha(contentAlpha),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    QuickActionButton(
                        icon = Icons.Default.Build,
                        text = "Layanan",
                        color = Color(0xFF2563EB),
                        onClick = { navController.navigate("services") }
                    )

                    QuickActionButton(
                        icon = Icons.Default.LocalOffer,
                        text = "Promo",
                        color = Color(0xFFEC4899),
                        onClick = { navController.navigate("promo") }
                    )

                    QuickActionButton(
                        icon = Icons.Default.Info,
                        text = "Tentang",
                        color = Color(0xFF10B981),
                        onClick = { navController.navigate("about") }
                    )

                    QuickActionButton(
                        icon = Icons.Default.SmartToy,
                        text = "AI Chat",
                        color = Color(0xFF9333EA),
                        onClick = { navController.navigate("ai") }
                    )
                }
            }

            // Price List CTA
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .alpha(contentAlpha),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF2563EB),
                    shadowElevation = 8.dp,
                    onClick = {
                        navController.navigate("services")
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "DAFTAR HARGA TERBARU",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Lihat harga terupdate semua layanan kami",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceCard(
    service: Service,
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Service Icon
                Surface(
                    modifier = Modifier.size(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF2563EB).copy(alpha = 0.1f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = service.icon,
                            contentDescription = service.name,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Service Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = service.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = service.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = service.price,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563EB)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add to Cart Button
            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (service.isCustom) Color(0xFF9333EA) else Color(0xFF2563EB)
                )
            ) {
                Icon(
                    imageVector = if (service.isCustom) Icons.Default.AutoAwesome else Icons.Default.Add,
                    contentDescription = if (service.isCustom) "Pesan Kustom" else "Tambah ke Keranjang",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (service.isCustom) "Pesan Kustom" else "Tambah ke Keranjang",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun StatItem(number: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = number,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFF2563EB)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(60.dp)
                .shadow(4.dp, CircleShape),
            shape = CircleShape,
            color = color.copy(alpha = 0.1f),
            onClick = onClick
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = color,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF475569),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}