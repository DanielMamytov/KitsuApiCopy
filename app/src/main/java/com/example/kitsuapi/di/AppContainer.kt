package com.example.kitsuapi.di

import com.example.kitsuapi.data.network.KitsuApi
import com.example.kitsuapi.domain.repository.AnimeRepository
import com.example.kitsuapi.domain.usecase.GetTrendingAnimeUseCase
import com.example.kitsuapi.repository.KitsuRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://kitsu.io/api/edge/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val kitsuApi: KitsuApi = retrofit.create(KitsuApi::class.java)

    private val animeRepository: AnimeRepository = KitsuRepository(kitsuApi)

    val getTrendingAnimeUseCase: GetTrendingAnimeUseCase =
        GetTrendingAnimeUseCase(animeRepository)
}
