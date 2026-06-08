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
import com.nursing.admin.mobile.data.models.NurseDTO
import java.math.BigDecimal

@Composable
fun NursesScreen(
    onBackClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    // Mock data
    val nurses = remember {
        listOf(
            NurseDTO(
                id = 1,
                name = "سارة أحمد",
                email = "sarah@example.com",
                phone = "+966501234567",
                specialization = "عناية عامة",
                yearsOfExperience = 5,
                licenseNumber = "LIC001",
                licenseExpiry = "2025-12-31",
                status = "APPROVED",
                rating = BigDecimal("4.8"),
                totalOrders = 45,
                createdAt = "2024-01-10T10:30:00"
            ),
            NurseDTO(
                id = 2,
                name = "نور محمد",
                email = "noor@example.com",
                phone = "+966502345678",
                specialization = "رعاية مسنين",
                yearsOfExperience = 8,
                licenseNumber = "LIC002",
                licenseExpiry = "2026-06-30",
                status = "APPROVED",
                rating = BigDecimal("4.9"),
                totalOrders = 78,
                createdAt = "2024-01-05T14:20:00"
            ),
            NurseDTO(
                id = 3,
                name = "ليلى علي",
                email = "layla@example.com",
                phone = "+966503456789",
                specialization = "عناية عامة",
                yearsOfExperience = 3,
                licenseNumber = "LIC003",
                licenseExpiry = "2024-12-31",
                status = "PENDING",
                rating = BigDecimal("0.0"),
                totalOrders = 0,
                createdAt = "2024-01-20T09:15:00"
            )
        )
    }

    val filteredNurses = nurses.filter { nurse ->
        (searchQuery.isEmpty() || nurse.name.contains(searchQuery, ignoreCase = true)) &&
        (selectedStatus == null || nurse.status == selectedStatus)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        TopAppBar(
            title = { Text("Nurses Management") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        // Search and Filter
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search nurses...") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                },
                singleLine = true
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedStatus == null,
                    onClick = { selectedStatus = null },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = selectedStatus == "APPROVED",
                    onClick = { selectedStatus = "APPROVED" },
                    label = { Text("Approved") }
                )
                FilterChip(
                    selected = selectedStatus == "PENDING",
                    onClick = { selectedStatus = "PENDING" },
                    label = { Text("Pending") }
                )
                FilterChip(
                    selected = selectedStatus == "REJECTED",
                    onClick = { selectedStatus = "REJECTED" },
                    label = { Text("Rejected") }
                )
            }
        }

        // Nurses List
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (filteredNurses.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No nurses found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredNurses) { nurse ->
                    NurseCard(nurse = nurse)
                }
            }
        }
    }
}

@Composable
fun NurseCard(nurse: NurseDTO) {
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = nurse.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = nurse.email,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                StatusBadge(status = nurse.status)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoItem(label = "Specialization", value = nurse.specialization)
                InfoItem(label = "Experience", value = "${nurse.yearsOfExperience} years")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoItem(label = "License", value = nurse.licenseNumber)
                InfoItem(label = "Expires", value = nurse.licenseExpiry)
            }

            if (nurse.totalOrders > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoItem(label = "Rating", value = "⭐ ${nurse.rating}")
                    InfoItem(label = "Orders", value = nurse.totalOrders.toString())
                }
            }

            if (nurse.status == "PENDING") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green
                        )
                    ) {
                        Text("Approve")
                    }
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Reject")
                    }
                }
            } else {
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("View Profile")
                }
            }
        }
    }
}

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
