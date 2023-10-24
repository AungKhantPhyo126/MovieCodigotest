package com.akpdev.moviecodigotest.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akpdev.moviecodigotest.domain.Movie

const val UPCOMING="upcoming"
const val POPULAR="popular"

const val UPCOMING_PAGE_KEY="upcomingPageKey"
const val POPULAR_PAGE_KEY="popularPageKey"


@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id:String,
    val movieId:String,
    val type:String,
    val title:String,
    val overview:String,
    val isFav:Boolean,
    val posterPath:String
)

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val keyId:String,
    val nextKey: Int?
    )

fun MovieEntity.asDomain():Movie{
    return Movie(
        id = id,
        movieId=movieId,
        title = title,
        posterPath = posterPath,
        type=type,
        overview = overview,
        isFav = isFav
    )
}

