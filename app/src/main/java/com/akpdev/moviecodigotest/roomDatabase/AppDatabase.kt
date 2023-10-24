package com.akpdev.moviecodigotest.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akpdev.moviecodigotest.roomDatabase.dao.MovieDao
import com.akpdev.moviecodigotest.roomDatabase.dao.RemoteKeyDao
import com.akpdev.moviecodigotest.roomDatabase.entity.RemoteKey
import com.akpdev.moviecodigotest.roomDatabase.entity.MovieEntity

@Database(
    entities = [
        MovieEntity::class,
        RemoteKey::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun remoteKeyDao():RemoteKeyDao
    abstract fun moviesDao(): MovieDao
    companion object {
        fun create(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "AppDatabase"
            ).allowMainThreadQueries()
                .build()
        }
    }
}