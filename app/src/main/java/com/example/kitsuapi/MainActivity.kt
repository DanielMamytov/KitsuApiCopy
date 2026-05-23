package com.example.kitsuapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.kitsuapi.ui.AnimeViewModel
import com.example.kitsuapi.ui.AnimeViewModelFactory
import com.example.kitsuapi.ui.screen.AnimeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AnimeViewModel by viewModels {
        AnimeViewModelFactory(
            (application as KitsuApplication).appContainer.getTrendingAnimeUseCase,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnimeScreen(viewModel = viewModel)
        }
    }
}
