package com.meet.photosappmvi.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.meet.photosappmvi.data.model.Photo
import com.meet.photosappmvi.presentation.screens.FavScreen
import com.meet.photosappmvi.presentation.screens.HomeScreen
import com.meet.photosappmvi.presentation.screens.PhotoDetailsScreen
import com.meet.photosappmvi.presentation.screens.ProfileScreen
import com.meet.photosappmvi.presentation.screens.SearchScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationRoot(
    modifier: Modifier,
    navController: NavHostController,
    updateBottomBarState: (BottomBarState) -> Unit
) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = NavRoute.HomeScreenScreenRoute) {
            //bottom navigation screens
            composable<NavRoute.HomeScreenScreenRoute> {
                updateBottomBarState(BottomBarState(true))
                HomeScreen(modifier = modifier)
            }
            composable<NavRoute.SearchScreenRoute> {
                updateBottomBarState(BottomBarState(true))
                SearchScreen(navController = navController, modifier)
            }
            composable<NavRoute.FavScreenScreenRoute> {
                updateBottomBarState(BottomBarState(true))
                FavScreen(navController = navController, modifier, animatedVisibilityScope = this)
            }
            composable<NavRoute.ProfileScreenScreenRoute> {
                updateBottomBarState(BottomBarState(true))
                ProfileScreen(navController = navController, modifier)
            }

            composable<NavRoute.PhotoDetailScreenRoute> {
                val arg = it.toRoute<NavRoute.PhotoDetailScreenRoute>()
                updateBottomBarState(BottomBarState(false))
                PhotoDetailsScreen(
                    navController = navController,
                    url = arg.photo,
                    description = arg.description,
                    likes = arg.likes,
                    modifier = modifier,
                    animatedVisibilityScope = this
                )
            }
        }
    }
}


sealed interface NavRoute {
    val showBottomBar: Boolean

    //bottom navigation screens
    @Serializable
    data object HomeScreenScreenRoute : NavRoute {
        override val showBottomBar = true
    }

    @Serializable
    data object SearchScreenRoute : NavRoute {
        override val showBottomBar = true
    }

    @Serializable
    data object FavScreenScreenRoute : NavRoute {
        override val showBottomBar = true
    }

    @Serializable
    data object ProfileScreenScreenRoute : NavRoute {
        override val showBottomBar = true
    }

    @Serializable
    data class PhotoDetailScreenRoute(val photo: String, val description: String, val likes: Int) :
        NavRoute {
        override val showBottomBar = false
    }

}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val routes: NavRoute,
    val hasUpdates: Boolean = false
)

object BottomItems {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            routes = NavRoute.HomeScreenScreenRoute
        ),
        BottomNavigationItem(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            routes = NavRoute.SearchScreenRoute
        ),
        BottomNavigationItem(
            title = "Favourite",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            routes = NavRoute.FavScreenScreenRoute
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            routes = NavRoute.ProfileScreenScreenRoute,
            hasUpdates = true
        ),
    )
}

data class BottomBarState(var isVisible: Boolean)