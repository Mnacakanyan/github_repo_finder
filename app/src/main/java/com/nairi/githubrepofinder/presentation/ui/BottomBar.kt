package com.nairi.githubrepofinder.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.nairi.githubrepofinder.presentation.routes.AppRoutes

typealias OnNavigate = (String) -> Unit


private val tabItems = listOf(
    BottomBarItem(
        text = "Search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        route = AppRoutes.SearchScreen.route
    ),
    BottomBarItem(
        text = "Downloads",
        selectedIcon = Icons.Filled.Folder,
        unselectedIcon = Icons.Outlined.Folder,
        route = AppRoutes.DownloadsScreen.route
    ),

)

@Composable
fun BottomBar(
    onClick: OnNavigate
) {
    var selectedTab by remember {
        mutableIntStateOf(0)
    }
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        tabItems.forEachIndexed { index, item->
            val isSelected = index == selectedTab
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedTab = index
                    onClick.invoke(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.text
                    )
                },
                label = {
                    Text(
                        text = item.text,
                        fontWeight = if (isSelected) FontWeight.Bold else null
                    )
                }
            )
        }
    }
}

data class BottomBarItem(
    val text: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)