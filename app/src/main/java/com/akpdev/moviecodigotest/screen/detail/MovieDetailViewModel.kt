package com.akpdev.moviecodigotest.screen.detail

import androidx.lifecycle.*
import com.akpdev.moviecodigotest.domain.Movie
import com.akpdev.moviecodigotest.repositoryImpl.MovieRepoImpl
import com.akpdev.moviecodigotest.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepoImpl: MovieRepoImpl
) : ViewModel(){
    private val _movieDetail = MutableLiveData<Movie>()
    val movieDetail:LiveData<Movie>
    get() = _movieDetail

    fun getMovieDetail(movieId:String){
        viewModelScope.launch {
            val result=movieRepoImpl.getMovieDetail(movieId)
            _movieDetail.value=result
        }
    }
}