package com.akpdev.moviecodigotest.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.akpdev.moviecodigotest.databinding.FragmentMovieDetailBinding
import com.akpdev.moviecodigotest.di.BASE_IMG_URL
import com.akpdev.moviecodigotest.util.loadImageWithGlide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment:Fragment() {
    private var _binding:FragmentMovieDetailBinding? = null
    val binding:FragmentMovieDetailBinding
        get() = _binding!!
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args by navArgs<MovieDetailFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentMovieDetailBinding.inflate(inflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieDetail(args.movieId)
        viewModel.movieDetail.observe(viewLifecycleOwner) {
            binding.ivMoviePoster.loadImageWithGlide(BASE_IMG_URL+it.posterPath)
            binding.movieTitle.text = it.title
            binding.tvReview.text = it.overview
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}