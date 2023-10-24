package com.akpdev.moviecodigotest.screen.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.akpdev.moviecodigotest.repositoryImpl.MovieRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingMovieListViewModel @Inject constructor(
    private val movieRepoImpl: MovieRepoImpl,
) : ViewModel(){
    var upcomingMoviesListLiveData=movieRepoImpl.fetchUpcomingMovies().cachedIn(viewModelScope)
}