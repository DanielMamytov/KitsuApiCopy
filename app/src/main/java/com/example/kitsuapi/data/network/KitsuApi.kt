package com.example.kitsuapi.data.network

import com.example.kitsuapi.data.model.AnimeData
import com.example.kitsuapi.data.model.ApiResponse
import retrofit2.http.GET

interface KitsuApi {
    @GET("trending/anime")
    suspend fun getAnime(): ApiResponse

}