package com.akpdev.moviecodigotest.repositoryImpl

import androidx.paging.*
import com.akpdev.moviecodigotest.domain.Movie
import com.akpdev.moviecodigotest.roomDatabase.AppDatabase
import com.akpdev.moviecodigotest.roomDatabase.dao.RemoteKeyDao
import com.akpdev.moviecodigotest.roomDatabase.dao.MovieDao
import com.akpdev.moviecodigotest.roomDatabase.entity.POPULAR
import com.akpdev.moviecodigotest.roomDatabase.entity.UPCOMING
import com.akpdev.moviecodigotest.roomDatabase.entity.asDomain
import com.akpdev.moviecodigotest.network.MovieApiService
import com.akpdev.moviecodigotest.remoteMediator.MovieRemoteMediator
import com.akpdev.moviecodigotest.repository.MovieRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val appDatabase: AppDatabase,
    ) : MovieRepo{

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchUpcomingMovies() :Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(10),
            remoteMediator = MovieRemoteMediator(UPCOMING,movieDao,remoteKeyDao,movieApiService,appDatabase)
        ) {
            movieDao.getUpcomingMovies(UPCOMING)
        }.flow.map { pagingData->
            pagingData.map { it.asDomain() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchPopularMovies() :Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(10),
            remoteMediator = MovieRemoteMediator(POPULAR,movieDao,remoteKeyDao,movieApiService,appDatabase)
        ) {
            movieDao.getPopularMovies(POPULAR)
        }.flow.map { pagingData->
            pagingData.map { it.asDomain() }
        }
    }

    suspend fun getMovieDetail(movieId:String):Movie{
        return movieDao.getMovie(movieId).asDomain()
    }

}