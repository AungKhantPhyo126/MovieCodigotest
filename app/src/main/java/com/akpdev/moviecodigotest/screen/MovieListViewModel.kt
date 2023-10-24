package com.akpdev.moviecodigotest.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.akpdev.moviecodigotest.domain.Movie
import com.akpdev.moviecodigotest.repository.MovieRepo
import com.akpdev.moviecodigotest.repositoryImpl.MovieRepoImpl
import com.akpdev.moviecodigotest.roomDatabase.entity.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviewListViewModel @Inject constructor(
    private val movieRepoImpl: MovieRepoImpl,
) :ViewModel(){
    var upcomingMoviesListLiveData=movieRepoImpl.fetchUpcomingMovies().cachedIn(viewModelScope)

    var popularMoviesListLiveData=movieRepoImpl.fetchPopularMovies().cachedIn(viewModelScope)

}