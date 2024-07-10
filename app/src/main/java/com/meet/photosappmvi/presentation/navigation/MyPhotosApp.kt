package com.meet.photosappmvi.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.meet.photosappmvi.ui.theme.PhotosAppMviTheme

@Composable
fun MyPhotosApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var bottomBarState by remember { mutableStateOf(BottomBarState(true)) }
    PhotosAppMviTheme {
        Scaffold(
            bottomBar = {
                MyPhotosBottomBar(navController, bottomBarState)
            },
            modifier = modifier.fillMaxSize()
        ) { pValues ->
            NavigationRoot(modifier= modifier.padding(pValues), navController) {
                bottomBarState = it
            }
        }
    }
}

@Composable
fun MyPhotosBottomBar(
    navController: NavHostController,
    bottomBarState: BottomBarState
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    if (bottomBarState.isVisible) {
        NavigationBar(
            modifier = Modifier.padding(8.dp),
            containerColor = Color.White,
            contentColor = Color.White
        ) {
            BottomItems.items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(item.routes)
                    },
                    icon = {
                        Icon(
                            imageVector = if (selectedItemIndex == index) {
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
}