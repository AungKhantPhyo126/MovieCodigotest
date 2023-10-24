package com.akpdev.moviecodigotest.network.dto

import com.akpdev.moviecodigotest.roomDatabase.entity.MovieEntity
import com.squareup.moshi.Json

data class MoviesDto(
    val id:String?,
    val title:String?,

    @Json(name = "poster_path")
    val posterPath:String?,
    val overview:String?
)
data class MovieResponse(
    val page:Int,
    val results:List<MoviesDto>,
    @field:Json(name = "total_pages")
    val totalPages:Int?
)

fun MoviesDto.asEntity(type:String): MovieEntity {
    return MovieEntity(
        id=type+id,
        movieId = id.orEmpty(),
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        type = type,
        isFav = false
    )
}
