package com.pkumar7.daggerhilt.retrofit

import com.pkumar7.daggerhilt.model.Blog
import java.lang.Exception
import javax.inject.Inject

class BlogServiceImpl
    @Inject constructor(var blogRetrofit: BlogRetrofit,
                        var networkMapper: NetworkMapper): WebService {

    override suspend fun get(): List<Blog> {
        try {
            val networkBlogs = blogRetrofit.get()
            return networkMapper.mapFromEntityList(networkBlogs)
        } catch (e: Exception) {
            return emptyList()
        }
    }
}