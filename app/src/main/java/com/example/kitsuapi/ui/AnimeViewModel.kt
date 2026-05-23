package com.example.kitsuapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kitsuapi.data.model.AnimeData
import com.example.kitsuapi.domain.usecase.GetTrendingAnimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeViewModel(
    private val getTrendingAnimeUseCase: GetTrendingAnimeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnimeUiState())
    val uiState: StateFlow<AnimeUiState> = _uiState.asStateFlow()

    fun loadAnime() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            runCatching { getTrendingAnimeUseCase() }
                .onSuccess { anime ->
                    _uiState.value = AnimeUiState(animeList = anime, isLoading = false)
                }
                .onFailure { throwable ->
                    _uiState.value = AnimeUiState(isLoading = false, error = throwable.message)
                }
        }
    }
}

class AnimeViewModelFactory(
    private val getTrendingAnimeUseCase: GetTrendingAnimeUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeViewModel(getTrendingAnimeUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class AnimeUiState(
    val animeList: List<AnimeData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
