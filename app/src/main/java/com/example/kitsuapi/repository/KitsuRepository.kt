package com.example.kitsuapi.repository

import com.example.kitsuapi.data.model.AnimeData
import com.example.kitsuapi.data.network.KitsuApi
import com.example.kitsuapi.data.network.RetrofitInstance


class KitsuRepository {

    suspend fun getAnime(): List<AnimeData> {
        return RetrofitInstance.api.getAnime().data
    }
}