package com.pkumar7.daggerhilt.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pkumar7.daggerhilt.model.Blog

@Database(entities = [BlogCacheEntity::class], version = 1)
abstract class BlogDatabase: RoomDatabase(){
    abstract fun blogDao() : BlogDao

    companion object{
        val DATABASE_NAME : String = "blog_db"
    }
}