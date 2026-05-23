package com.example.kitsuapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitsuapi.data.model.AnimeData
import com.example.kitsuapi.domain.usecase.GetTrendingAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val getTrendingAnimeUseCase: GetTrendingAnimeUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AnimeUiState())
    val uiState: StateFlow<AnimeUiState> = _uiState.asStateFlow()

    fun loadAnime() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            runCatching { getTrendingAnimeUseCase() }
                .onSuccess { _uiState.value = AnimeUiState(animeList = it, isLoading = false) }
                .onFailure { _uiState.value = AnimeUiState(isLoading = false, error = it.message) }
        }
    }
}

data class AnimeUiState(
    val animeList: List<AnimeData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
