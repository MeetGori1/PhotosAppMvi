package com.meet.photosappmvi.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.meet.photosappmvi.data.model.User
import com.meet.photosappmvi.presentation.components.ErrorComponent
import com.meet.photosappmvi.presentation.components.LoadingComponent
import com.meet.photosappmvi.viewmodel.PhotoIntent
import com.meet.photosappmvi.viewmodel.PhotosState
import com.meet.photosappmvi.viewmodel.PhotosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    photosViewModel: PhotosViewModel = viewModel()
) {
    LaunchedEffect(key1 = true) {
        photosViewModel.processIntent(PhotoIntent.GetUserProfile)
    }

    val state = photosViewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile") }
            )
        }
    ) { contentPadding ->
        when (state) {
            is PhotosState.Loading -> LoadingComponent(modifier.padding(contentPadding))
            is PhotosState.Error -> {
                ErrorComponent(
                    message = state.message,
                    modifier = modifier.padding(contentPadding),
                    onRetry = { photosViewModel.processIntent(PhotoIntent.GetUserProfile) }
                )
            }

            is PhotosState.OnUserProfileResult -> {
                ProfileItem(state.user, modifier = modifier.padding(contentPadding))
            }

            else -> {}
        }
    }
}

@Composable
fun ProfileItem(item: User, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Header
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = item.profileImage?.medium,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = rememberVectorPainter(image = Icons.Filled.Person),
                        error = rememberVectorPainter(image = Icons.Filled.Person)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "${item.name ?: ""} ${item.lastName ?: ""}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    item.username?.let {
                        Text(
                            text = "@$it",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    item.bio?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    item.social?.let { social ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SocialMediaItem(
                                social.instagram_username,
                                social.instagram_username ?: "",
                                Icons.Filled.PhotoCamera,
                                "Instagram", modifier = Modifier.weight(1f)
                            )
                            VerticalDivider(
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(1.dp)
                            )
                            SocialMediaItem(
                                social.twitter_username,
                                social.twitter_username ?: "",
                                Icons.Filled.ChatBubble,
                                "Twitter", modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Followers and Following
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                FollowerItem(count = item.followersCount ?: 0, label = "Followers")
                FollowerItem(count = item.followingCount ?: 0, label = "Following")
            }
        }
    }
}

@Composable
fun FollowerItem(count: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SocialMediaItem(
    link: String?,
    username: String,
    icon: ImageVector,
    platform: String,
    modifier: Modifier = Modifier
) {
    if (link != null) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$platform Link",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = username,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = modifier
                    .padding(16.dp),
            )
        }
    }
}