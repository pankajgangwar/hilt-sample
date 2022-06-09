package com.pkumar7.daggerhilt.di

import com.pkumar7.daggerhilt.repository.MainRepository
import com.pkumar7.daggerhilt.retrofit.WebService
import com.pkumar7.daggerhilt.room.BlogDao
import com.pkumar7.daggerhilt.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(blogDao: BlogDao,
                              cacheMapper: CacheMapper,
                              webService: WebService) : MainRepository {
        return MainRepository(blogDao = blogDao,
                              cacheMapper = cacheMapper,
                              webService = webService)
    }
}