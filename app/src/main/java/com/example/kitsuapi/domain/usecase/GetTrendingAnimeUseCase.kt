package com.example.kitsuapi.domain.usecase

import com.example.kitsuapi.data.model.AnimeData
import com.example.kitsuapi.domain.repository.AnimeRepository
import javax.inject.Inject

class GetTrendingAnimeUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
) {
    suspend operator fun invoke(): List<AnimeData> {
        return animeRepository.getTrendingAnime()
    }
}
