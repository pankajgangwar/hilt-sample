package com.pkumar7.daggerhilt.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pkumar7.daggerhilt.retrofit.*
import com.pkumar7.daggerhilt.room.BlogDao
import com.pkumar7.daggerhilt.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson : Gson) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://open-api.xyz/placeholder/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder) : BlogRetrofit {
        return retrofit.build().create(BlogRetrofit::class.java)
    }

    @Singleton
    @Provides
    fun provideBlogWebService(@ApplicationContext context: Context,
                              blogRetrofit : BlogRetrofit,
                              networkMapper: NetworkMapper) : WebService {
        /*
        * To build app with clean architecture
        * 1) More abstraction
        * 2) Add components to fake data
        * 3) Code should be testable either with DI or with some framework
        * */
       // return BlogServiceImpl(blogRetrofit, networkMapper)
        return FakeWebServiceImpl(context, networkMapper)
    }
}