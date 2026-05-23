package com.example.kitsuapi.repository

import com.example.kitsuapi.data.model.AnimeData
import com.example.kitsuapi.data.network.KitsuApi
import com.example.kitsuapi.domain.repository.AnimeRepository
import javax.inject.Inject

class KitsuRepository @Inject constructor(
    private val kitsuApi: KitsuApi,
) : AnimeRepository {

    override suspend fun getTrendingAnime(): List<AnimeData> {
        return kitsuApi.getAnime().data
    }
}
