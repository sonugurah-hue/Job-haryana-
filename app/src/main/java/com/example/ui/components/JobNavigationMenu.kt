package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NavCategory
import com.example.ui.theme.HaryanaNavyPrimary
import com.example.ui.theme.SaffronAccent

@Composable
fun JobNavigationMenu(
    selectedCategory: NavCategory,
    onCategorySelected: (NavCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val navItems = listOf(
        NavCategory.HOME,
        NavCategory.LATEST_JOBS,
        NavCategory.HARYANA_JOBS,
        NavCategory.CENTRAL_JOBS,
        NavCategory.ADMISSION,
        NavCategory.ADMIT_CARD,
        NavCategory.RESULT,
        NavCategory.ANSWER_KEY
    )

    val selectedIndex = navItems.indexOf(selectedCategory).let { if (it >= 0) it else 0 }

    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier
            .fillMaxWidth()
            .testTag("job_nav_menu"),
        containerColor = HaryanaNavyPrimary,
        contentColor = Color.White,
        edgePadding = 8.dp,
        indicator = { tabPositions ->
            if (selectedIndex < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                    height = 3.dp,
                    color = SaffronAccent
                )
            }
        },
        divider = {}
    ) {
        navItems.forEachIndexed { index, item ->
            val isSelected = selectedCategory == item
            Tab(
                selected = isSelected,
                onClick = { onCategorySelected(item) },
                modifier = Modifier.testTag("nav_tab_${item.id}"),
                text = {
                    Column(
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = item.titleEn,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                fontSize = 13.sp
                            ),
                            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.75f)
                        )
                        Text(
                            text = item.titleHi,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                            ),
                            color = if (isSelected) Color(0xFFFFD54F) else Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
            )
        }
    }
}
