package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MainContainer
import com.example.ui.theme.CineFlixTheme
import com.example.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineFlixTheme {
                val movieViewModel: MovieViewModel = viewModel()
                MainContainer(viewModel = movieViewModel)
            }
        }
    }
}
