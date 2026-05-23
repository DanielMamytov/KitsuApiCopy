package com.example.kitsuapi.domain.repository

import com.example.kitsuapi.data.model.AnimeData

interface AnimeRepository {
    suspend fun getTrendingAnime(): List<AnimeData>
}
