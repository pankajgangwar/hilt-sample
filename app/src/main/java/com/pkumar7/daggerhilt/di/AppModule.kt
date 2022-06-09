package com.pkumar7.daggerhilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class ApplicationTag

@Qualifier
annotation class DelimeterTag

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @ApplicationTag
    @Singleton
    @Provides
    fun provideApplicationTag(): String {
        return "DaggerHilt"
    }

    @DelimeterTag
    @Singleton
    @Provides
    fun provideDelimeterTag() : String {
        return "Hey this is awesome string !! "
    }
}
