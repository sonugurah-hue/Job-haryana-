package com.example.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.HaryanaNavyPrimary
import com.example.ui.theme.SaffronAccent

@Composable
fun JobSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    selectedFilter: String,
    onFilterSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf(
        "All" to "सभी",
        "10th" to "10वीं पास",
        "12th" to "12वीं पास",
        "Graduate" to "स्नातक (Graduate)",
        "Police" to "पुलिस भर्ती",
        "CET" to "हरियाणा CET",
        "ITI" to "ITI / डिप्लोमा"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        // Search Input Bar
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("job_search_input"),
            placeholder = {
                Text(
                    text = "Search Jobs, Exams, Results... (नौकरियां, रिजल्ट्स खोजें)",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        color = Color(0xFF64748B)
                    )
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = HaryanaNavyPrimary
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = { onQueryChange("") },
                        modifier = Modifier.testTag("clear_search_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Search",
                            tint = Color.Gray
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = HaryanaNavyPrimary,
                unfocusedBorderColor = Color(0xFFCBD5E1),
                focusedContainerColor = Color(0xFFF8FAFC),
                unfocusedContainerColor = Color(0xFFF8FAFC)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Horizontal filter chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter by qualification",
                tint = Color(0xFF64748B),
                modifier = Modifier.size(16.dp)
            )

            filters.forEach { (tag, label) ->
                val isSelected = selectedFilter == tag
                FilterChip(
                    selected = isSelected,
                    onClick = { onFilterSelect(tag) },
                    label = {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = HaryanaNavyPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF1F5F9),
                        labelColor = Color(0xFF334155)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = null,
                    modifier = Modifier.testTag("filter_chip_$tag")
                )
            }
        }
    }
}
