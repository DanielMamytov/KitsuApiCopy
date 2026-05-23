package com.example.kitsuapi.ui

import androidx.lifecycle.ViewModel
import com.example.kitsuapi.repository.KitsuRepository
import androidx.lifecycle.viewModelScope
import com.example.kitsuapi.data.model.AnimeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {

    private val repository = KitsuRepository()

    private val _animeList = MutableStateFlow<List<AnimeData>>(emptyList())
    val animeList: StateFlow<List<AnimeData>> = _animeList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getAnime() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _animeList.value = repository.getAnime()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}