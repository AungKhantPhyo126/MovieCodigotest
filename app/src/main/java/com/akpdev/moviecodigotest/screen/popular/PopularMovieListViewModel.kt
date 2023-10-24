package com.akpdev.moviecodigotest.screen.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.akpdev.moviecodigotest.repositoryImpl.MovieRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PopularMovieListViewModel @Inject constructor(
    private val movieRepoImpl: MovieRepoImpl,
) : ViewModel(){

    var popularMoviesListLiveData=movieRepoImpl.fetchPopularMovies().cachedIn(viewModelScope)

}