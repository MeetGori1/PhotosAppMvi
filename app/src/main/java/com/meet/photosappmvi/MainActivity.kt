package com.meet.photosappmvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.meet.photosappmvi.presentation.navigation.MyPhotosApp
import com.meet.photosappmvi.ui.theme.PhotosAppMviTheme
import com.meet.photosappmvi.viewmodel.PhotosViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //need to use di to replace this way 
        val photosViewModel=PhotosViewModel()
        setContent {
            PhotosAppMviTheme {
                MyPhotosApp(photosViewModel)
            }
        }
    }
}
