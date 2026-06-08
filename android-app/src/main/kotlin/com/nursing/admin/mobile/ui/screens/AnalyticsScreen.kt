package com.nursing.admin.mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AnalyticsScreen(
    onBackClick: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        TopAppBar(
            title = { Text("Analytics & Reports") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Key Metrics
                item {
                    Text(
                        text = "Key Metrics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AnalyticsCard(
                            title = "Total Revenue",
                            value = "\$45,230",
                            icon = Icons.Filled.AttachMoney,
                            color = Color(0xFF4CAF50),
                            modifier = Modifier.weight(1f)
                        )
                        AnalyticsCard(
                            title = "Total Orders",
                            value = "523",
                            icon = Icons.Filled.Assignment,
                            color = Color(0xFF2196F3),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AnalyticsCard(
                            title = "Active Users",
                            value = "298",
                            icon = Icons.Filled.Person,
                            color = Color(0xFFFF9800),
                            modifier = Modifier.weight(1f)
                        )
                        AnalyticsCard(
                            title = "Avg Rating",
                            value = "4.7⭐",
                            icon = Icons.Filled.Star,
                            color = Color(0xFFF44336),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Performance Metrics
                item {
                    Text(
                        text = "Performance Metrics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.paddingFromBaseline(top = 24.dp)
                    )
                }

                item {
                    MetricCard(
                        title = "Completion Rate",
                        value = "94.2%",
                        subtitle = "Orders completed on time",
                        progress = 0.942f
                    )
                }

                item {
                    MetricCard(
                        title = "Customer Satisfaction",
                        value = "92.8%",
                        subtitle = "Based on 523 reviews",
                        progress = 0.928f
                    )
                }

                item {
                    MetricCard(
                        title = "Nurse Utilization",
                        value = "87.5%",
                        subtitle = "Average nurse utilization rate",
                        progress = 0.875f
                    )
                }

                // Top Performers
                item {
                    Text(
                        text = "Top Performing Nurses",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.paddingFromBaseline(top = 24.dp)
                    )
                }

                items(3) { index ->
                    TopPerformerCard(
                        rank = index + 1,
                        name = when (index) {
                            0 -> "نور محمد"
                            1 -> "سارة أحمد"
                            else -> "ليلى علي"
                        },
                        orders = when (index) {
                            0 -> 78
                            1 -> 65
                            else -> 52
                        },
                        rating = when (index) {
                            0 -> "4.9"
                            1 -> "4.8"
                            else -> "4.7"
                        }
                    )
                }

                // Revenue Breakdown
                item {
                    Text(
                        text = "Revenue Breakdown",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.paddingFromBaseline(top = 24.dp)
                    )
                }

                item {
                    RevenueBreakdownCard(
                        title = "By Service Type",
                        items = listOf(
                            "General Care" to "$18,500" to 40.9f,
                            "Elderly Care" to "$15,200" to 33.6f,
                            "Specialized Care" to "$11,530" to 25.5f
                        )
                    )
                }

                // Monthly Trends
                item {
                    Text(
                        text = "Monthly Trends",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.paddingFromBaseline(top = 24.dp)
                    )
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Orders Trend (Last 6 Months)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            
                            listOf(
                                "January" to 78,
                                "February" to 92,
                                "March" to 85,
                                "April" to 110,
                                "May" to 128,
                                "June" to 130
                            ).forEach { (month, orders) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = month,
                                        modifier = Modifier.width(80.dp),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(20.dp)
                                            .background(
                                                Color(0xFF2196F3),
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                            .fillMaxWidth(orders / 130f)
                                    )
                                    Text(
                                        text = orders.toString(),
                                        style = MaterialTheme.typography.bodySmall,
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
fun AnalyticsCard(
    title: String,
    value: String,
    icon: androidx.compose.material.icons.materialIcon,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    progress: Float
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF4CAF50),
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
fun TopPerformerCard(
    rank: Int,
    name: String,
    orders: Int,
    rating: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = when (rank) {
                    1 -> Color(0xFFFFD700)
                    2 -> Color(0xFFC0C0C0)
                    else -> Color(0xFFCD7F32)
                },
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "#$rank",
                    modifier = Modifier
                        .padding(8.dp)
                        .width(32.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$orders orders • ⭐ $rating",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RevenueBreakdownCard(
    title: String,
    items: List<Triple<String, String, Float>>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            items.forEach { (label, amount, percentage) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.width(100.dp)) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = amount,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(24.dp)
                            .background(
                                Color(0xFF2196F3),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .fillMaxWidth(percentage / 100f)
                    )

                    Text(
                        text = "${"%.1f".format(percentage)}%",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(40.dp)
                    )
                }
            }
        }
    }
}

import androidx.compose.material3.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
