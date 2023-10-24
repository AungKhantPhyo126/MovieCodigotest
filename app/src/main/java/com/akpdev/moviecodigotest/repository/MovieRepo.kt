package com.akpdev.moviecodigotest.repository

import androidx.paging.PagingData
import com.akpdev.moviecodigotest.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepo {
    fun fetchUpcomingMovies(): Flow<PagingData<Movie>>

    fun fetchPopularMovies():Flow<PagingData<Movie>>
}