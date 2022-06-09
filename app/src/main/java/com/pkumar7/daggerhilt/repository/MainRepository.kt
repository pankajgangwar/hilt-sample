package com.pkumar7.daggerhilt.repository

import com.pkumar7.daggerhilt.model.Blog
import com.pkumar7.daggerhilt.retrofit.BlogRetrofit
import com.pkumar7.daggerhilt.retrofit.NetworkMapper
import com.pkumar7.daggerhilt.retrofit.WebService
import com.pkumar7.daggerhilt.room.BlogDao
import com.pkumar7.daggerhilt.room.CacheMapper
import com.pkumar7.daggerhilt.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MainRepository
 @Inject constructor(
     var blogDao : BlogDao,
     var cacheMapper: CacheMapper,
     var webService : WebService) {

    suspend fun getBlogs() : Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val blogs = webService.get()
            for(blog in blogs){
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        }catch (e : Exception){
            emit(DataState.Error(e))
        }
    }
}