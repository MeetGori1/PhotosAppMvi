package com.meet.photosappmvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.meet.photosappmvi.presentation.navigation.MyPhotosApp
import com.meet.photosappmvi.ui.theme.PhotosAppMviTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotosAppMviTheme {
                MyPhotosApp()
            }
        }
    }
}
