package com.pkumar7.daggerhilt.retrofit
import com.pkumar7.daggerhilt.model.Blog

interface WebService {
    suspend fun get() : List<Blog>
}