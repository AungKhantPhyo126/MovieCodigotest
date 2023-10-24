package com.akpdev.moviecodigotest.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akpdev.moviecodigotest.R
import com.akpdev.moviecodigotest.databinding.ItemRecyclerUpcomingMovieBinding
import com.akpdev.moviecodigotest.di.BASE_IMG_URL
import com.akpdev.moviecodigotest.di.BASE_URL
import com.akpdev.moviecodigotest.domain.Movie
import com.akpdev.moviecodigotest.util.loadImageWithGlide

class MoviesListRecyclerAdapter(
    private val navigateToDetail: (data: Movie) -> Unit,
) :
    PagingDataAdapter<Movie, UpcomingMoviesViewHolder>(MoviesDiffCallback) {
    override fun onBindViewHolder(holder: UpcomingMoviesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerUpcomingMovieBinding.inflate(layoutInflater,parent,false)
        return UpcomingMoviesViewHolder(binding, navigateToDetail)
    }

}

object MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

class UpcomingMoviesViewHolder(
    private val binding: ItemRecyclerUpcomingMovieBinding,
    private val navigateToDetail: (data: Movie) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {

        binding.ivMoviePoster.loadImageWithGlide(BASE_IMG_URL +movie.posterPath)
        binding.movieTitle.text = movie.title
        binding.mcvMovie.setOnClickListener {
            navigateToDetail(movie)
        }
    }
}