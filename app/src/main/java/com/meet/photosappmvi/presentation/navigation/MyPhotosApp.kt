package com.meet.photosappmvi.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.meet.photosappmvi.ui.theme.PhotosAppMviTheme

@Composable
fun MyPhotosApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    PhotosAppMviTheme {
        Scaffold(
            bottomBar = {
                MyPhotosBottomBar(navController)
            },
            modifier = modifier.fillMaxSize()
        ) { pValues ->
            NavigationRoot(modifier = modifier.padding(pValues), navController)
        }
    }
}

@Composable
fun MyPhotosBottomBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: NavRoute.HomeScreenScreenRoute::class.qualifiedName.orEmpty()

    BottomAppBar(
        modifier = Modifier.padding(8.dp),
        containerColor = Color.White,
        contentColor = Color.White
    ) {
        BottomItems.items.forEachIndexed { _, item ->
            val isSelected by remember(currentRoute) {
                derivedStateOf { currentRoute == item.routes::class.qualifiedName }
            }
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.routes)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title,
                        modifier = Modifier.padding(1.dp)
                    )
                }
            )
        }
    }
}