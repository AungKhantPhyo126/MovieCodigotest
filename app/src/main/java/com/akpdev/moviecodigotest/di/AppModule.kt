package com.akpdev.moviecodigotest.di

import android.content.Context
import com.akpdev.moviecodigotest.roomDatabase.AppDatabase
import com.akpdev.moviecodigotest.roomDatabase.dao.RemoteKeyDao
import com.akpdev.moviecodigotest.roomDatabase.dao.MovieDao
import com.akpdev.moviecodigotest.network.MovieApiService
import com.akpdev.moviecodigotest.repository.MovieRepo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

const val BASE_URL="https://api.themoviedb.org/3/movie/"
const val API_KEY="012a0a929d214b25f6dc147d762bd6e4"
const val BASE_IMG_URL="https://image.tmdb.org/t/p/w400"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideRetrofit(moshi: Moshi,okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideMoshi() : Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit) = retrofit.create<MovieApiService>()

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context)  : AppDatabase {
        return AppDatabase.create(context)
    }
    @Provides
    fun provideRemoteKeyDao(appDatabase: AppDatabase) : RemoteKeyDao {
        return appDatabase.remoteKeyDao()
    }
    @Provides
    @Singleton
    fun provideOkHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BASIC)
    }
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase) : MovieDao {
        return appDatabase.moviesDao()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        oKHttpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(oKHttpLoggingInterceptor)
        }.build()
    }

}