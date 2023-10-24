package com.akpdev.moviecodigotest.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.akpdev.moviecodigotest.di.API_KEY
import com.akpdev.moviecodigotest.roomDatabase.AppDatabase
import com.akpdev.moviecodigotest.roomDatabase.dao.RemoteKeyDao
import com.akpdev.moviecodigotest.roomDatabase.dao.MovieDao
import com.akpdev.moviecodigotest.roomDatabase.entity.*
import com.akpdev.moviecodigotest.network.MovieApiService
import com.akpdev.moviecodigotest.network.dto.asEntity
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator (
    private val type:String,
    private val movieDao: MovieDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val movieApiService: MovieApiService,
    private val database: AppDatabase
) : RemoteMediator<Int, MovieEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try{

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND->{
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKey(if (type== UPCOMING) UPCOMING_PAGE_KEY else POPULAR_PAGE_KEY)
                    }

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.nextKey
                }
            }
            val keyId=if (type== UPCOMING) UPCOMING_PAGE_KEY else POPULAR_PAGE_KEY
            val response=if (type== UPCOMING){
                movieApiService.getUpcomingMovies(
                    apiKey = API_KEY, pageNumber = loadKey
                )
            }else{
                movieApiService.getPopularMovies(
                    apiKey = API_KEY,pageNumber = loadKey
                )
            }

            val isLastPage = loadKey == response.body()?.totalPages
            database.withTransaction {
                remoteKeyDao.insertOrReplace(
                    RemoteKey(keyId = keyId,nextKey = if(isLastPage) null else loadKey+1)
                )
                movieDao.saveUpcomingMovies(response.body()?.results?.map { it.asEntity(type) }.orEmpty())
            }

            MediatorResult.Success(
                endOfPaginationReached = loadKey == response.body()?.totalPages
            )
        }catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}