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
import com.nursing.admin.mobile.data.models.OrderDTO
import java.math.BigDecimal

@Composable
fun OrdersScreen(
    onBackClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showAssignDialog by remember { mutableStateOf(false) }
    
    // Mock data
    val orders = remember {
        listOf(
            OrderDTO(
                id = 1,
                patientId = 1,
                patientName = "أحمد محمد",
                nurseId = 1,
                nurseName = "سارة أحمد",
                serviceId = 1,
                serviceName = "عناية عامة",
                status = "COMPLETED",
                scheduledDate = "2024-01-20T10:00:00",
                completedDate = "2024-01-20T12:30:00",
                totalPrice = BigDecimal("150.00"),
                notes = "تم بنجاح",
                createdAt = "2024-01-20T09:00:00"
            ),
            OrderDTO(
                id = 2,
                patientId = 2,
                patientName = "فاطمة علي",
                nurseId = 2,
                nurseName = "نور محمد",
                serviceId = 2,
                serviceName = "رعاية مسنين",
                status = "IN_PROGRESS",
                scheduledDate = "2024-01-21T14:00:00",
                completedDate = null,
                totalPrice = BigDecimal("200.00"),
                notes = "جاري التنفيذ",
                createdAt = "2024-01-21T13:00:00"
            ),
            OrderDTO(
                id = 3,
                patientId = 3,
                patientName = "محمود حسن",
                nurseId = null,
                nurseName = null,
                serviceId = 1,
                serviceName = "عناية عامة",
                status = "PENDING",
                scheduledDate = "2024-01-22T09:00:00",
                completedDate = null,
                totalPrice = BigDecimal("150.00"),
                notes = "بانتظار تعيين ممرضة",
                createdAt = "2024-01-21T15:30:00"
            )
        )
    }

    val filteredOrders = orders.filter { order ->
        (searchQuery.isEmpty() || 
         order.patientName.contains(searchQuery, ignoreCase = true) ||
         order.serviceName.contains(searchQuery, ignoreCase = true)) &&
        (selectedStatus == null || order.status == selectedStatus)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        TopAppBar(
            title = { Text("Orders Management") },
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
                label = { Text("Search orders...") },
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
                    selected = selectedStatus == "PENDING",
                    onClick = { selectedStatus = "PENDING" },
                    label = { Text("Pending") }
                )
                FilterChip(
                    selected = selectedStatus == "IN_PROGRESS",
                    onClick = { selectedStatus = "IN_PROGRESS" },
                    label = { Text("In Progress") }
                )
                FilterChip(
                    selected = selectedStatus == "COMPLETED",
                    onClick = { selectedStatus = "COMPLETED" },
                    label = { Text("Completed") }
                )
                FilterChip(
                    selected = selectedStatus == "CANCELLED",
                    onClick = { selectedStatus = "CANCELLED" },
                    label = { Text("Cancelled") }
                )
            }
        }

        // Orders List
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (filteredOrders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No orders found",
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
                items(filteredOrders) { order ->
                    OrderCard(
                        order = order,
                        onAssignClick = { showAssignDialog = true }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    order: OrderDTO,
    onAssignClick: () -> Unit = {}
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Order #${order.id}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = order.serviceName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                StatusBadge(status = order.status)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoItem(label = "Patient", value = order.patientName)
                InfoItem(label = "Price", value = "$${order.totalPrice}")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoItem(label = "Scheduled", value = order.scheduledDate.take(10))
                if (order.nurseName != null) {
                    InfoItem(label = "Nurse", value = order.nurseName)
                }
            }

            if (!order.notes.isNullOrEmpty()) {
                Text(
                    text = "Notes: ${order.notes}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (order.status == "PENDING") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onAssignClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Assign Nurse")
                    }
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
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
                    Text("View Details")
                }
            }
        }
    }
}

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
