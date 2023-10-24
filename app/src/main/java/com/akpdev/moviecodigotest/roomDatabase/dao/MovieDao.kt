package com.akpdev.moviecodigotest.roomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.akpdev.moviecodigotest.roomDatabase.entity.MovieEntity
import com.akpdev.moviecodigotest.roomDatabase.entity.RemoteKey


@Dao
interface MovieDao {

    //For Upcoming
    @Transaction
    @Query("SELECT * FROM movies where type=:type")
    fun getUpcomingMovies(type:String):PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUpcomingMovies( upcomingMovies: List<MovieEntity>)

    //For Popular
    @Transaction
    @Query("SELECT * FROM movies where type=:type")
    fun getPopularMovies(type:String):PagingSource<Int,MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePopularMovies(popularMovies: List<MovieEntity>)

    @Transaction
    @Query("select * from movies where id=:id")
    suspend fun getMovie(id:String):MovieEntity

}

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys where keyId=:id")
    suspend fun remoteKey(id:String): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun delete()
}