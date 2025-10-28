package com.printology.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
    val gradientColors: List<Color> = listOf(Color(0xFF2563EB), Color(0xFF3B82F6))
) {
    object Home : BottomNavItem(
        route = "home",
        title = "Home",
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        gradientColors = listOf(Color(0xFF2563EB), Color(0xFF3B82F6))
    )

    object Services : BottomNavItem(
        route = "services",
        title = "Layanan",
        icon = Icons.Outlined.Build,
        selectedIcon = Icons.Filled.Build,
        gradientColors = listOf(Color(0xFF10B981), Color(0xFF059669))
    )

    object Promo : BottomNavItem(
        route = "promo",
        title = "Promo",
        icon = Icons.Outlined.LocalOffer,
        selectedIcon = Icons.Filled.LocalOffer,
        gradientColors = listOf(Color(0xFFEC4899), Color(0xFFDB2777))
    )

    object About : BottomNavItem(
        route = "about",
        title = "Tentang",
        icon = Icons.Outlined.Info,
        selectedIcon = Icons.Filled.Info,
        gradientColors = listOf(Color(0xFFF59E0B), Color(0xFFD97706))
    )

    object Contact : BottomNavItem(
        route = "contact",
        title = "Kontak",
        icon = Icons.Outlined.ContactMail,
        selectedIcon = Icons.Filled.ContactMail,
        gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFF7C3AED))
    )

    object AI : BottomNavItem(
        route = "ai",
        title = "AI",
        icon = Icons.Outlined.SmartToy,
        selectedIcon = Icons.Filled.SmartToy,
        gradientColors = listOf(Color(0xFF06B6D4), Color(0xFF0891B2))
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Services,
        BottomNavItem.Promo,
        BottomNavItem.About,
        BottomNavItem.Contact,
        BottomNavItem.AI
    )

    NavigationBar(
        modifier = Modifier
            .height(80.dp)
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                clip = true
            ),
        containerColor = Color.White.copy(alpha = 0.95f)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = currentRoute == item.route
            val scale by animateFloatAsState(
                targetValue = if (selected) 1.1f else 1f,
                animationSpec = tween(durationMillis = 300)
            )

            NavigationBarItem(
                icon = {
                    ModernNavIcon(
                        item = item,
                        selected = selected,
                        scale = scale
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                        color = if (selected) Color(0xFF2563EB) else Color(0xFF64748B)
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ModernNavIcon(
    item: BottomNavItem,
    selected: Boolean,
    scale: Float
) {
    Box(
        modifier = Modifier
            .size(if (selected) 50.dp else 40.dp)
            .shadow(
                elevation = if (selected) 8.dp else 2.dp,
                shape = CircleShape,
                clip = true
            ),
        contentAlignment = Alignment.Center
    ) {
        // Background with gradient when selected
        if (selected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = item.gradientColors
                        ),
                        shape = CircleShape
                    )
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xFFF1F5F9),
                        shape = CircleShape
                    )
            )
        }

        // Icon with modern styling
        Icon(
            imageVector = if (selected) item.selectedIcon else item.icon,
            contentDescription = item.title,
            tint = if (selected) Color.White else Color(0xFF64748B),
            modifier = Modifier.size(if (selected) 22.dp else 20.dp)
        )

        // Pulse effect for selected item
        if (selected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.3f),
                                        Color.Transparent
                                    ),
                                    radius = size.width / 2
                                ),
                                radius = size.width / 2
                            )
                        }
                    }
            )
        }
    }
}

// Simple version without custom clickable - using NavigationBarItem directly
@Composable
fun ModernBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Services,
        BottomNavItem.Promo,
        BottomNavItem.About,
        BottomNavItem.Contact,
        BottomNavItem.AI
    )

    NavigationBar(
        modifier = Modifier
            .height(80.dp)
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                clip = true
            ),
        containerColor = Color.White.copy(alpha = 0.95f)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = currentRoute == item.route
            val scale by animateFloatAsState(
                targetValue = if (selected) 1.15f else 1f,
                animationSpec = tween(durationMillis = 300)
            )

            NavigationBarItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Icon Container
                        Box(
                            modifier = Modifier
                                .size(if (selected) 50.dp else 40.dp)
                                .shadow(
                                    elevation = if (selected) 12.dp else 4.dp,
                                    shape = CircleShape,
                                    clip = true
                                )
                                .background(
                                    brush = if (selected) Brush.linearGradient(
                                        colors = item.gradientColors
                                    ) else Brush.linearGradient(
                                        colors = listOf(Color(0xFFF8FAFC), Color(0xFFF1F5F9))
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (selected) item.selectedIcon else item.icon,
                                contentDescription = item.title,
                                tint = if (selected) Color.White else Color(0xFF64748B),
                                modifier = Modifier.size(if (selected) 22.dp else 20.dp)
                            )

                            // Active indicator dot
                            if (selected) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .align(Alignment.BottomEnd)
                                        .offset(x = (-4).dp, y = (-4).dp)
                                )
                            }
                        }
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                        color = if (selected) Color(0xFF2563EB) else Color(0xFF64748B)
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}