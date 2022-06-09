package com.pkumar7.daggerhilt.retrofit

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pkumar7.daggerhilt.R
import com.pkumar7.daggerhilt.model.Blog
import java.lang.Exception
import javax.inject.Inject

class FakeWebServiceImpl @Inject constructor(var context: Context,
                                             var networkMapper: NetworkMapper): WebService {

    override suspend fun get(): List<Blog> {
        try {
            val inputStream = context.resources.openRawResource(R.raw.blogs)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            val jsonString = String(buffer)
            val type = object : TypeToken<List<BlogNetworkEntity>>(){}.type
            val networkBlogs : List<BlogNetworkEntity> = Gson().fromJson(jsonString, type)
            return networkMapper.mapFromEntityList(networkBlogs)
        } catch (e: Exception) {
            return emptyList()
        }
    }
}