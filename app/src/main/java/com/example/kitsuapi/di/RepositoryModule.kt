package com.example.kitsuapi.di

import com.example.kitsuapi.domain.repository.AnimeRepository
import com.example.kitsuapi.repository.KitsuRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAnimeRepository(impl: KitsuRepository): AnimeRepository
}
