package com.akpdev.moviecodigotest.screen.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.akpdev.moviecodigotest.R
import com.akpdev.moviecodigotest.databinding.FragmentUpcomingMovieListBinding
import com.akpdev.moviecodigotest.roomDatabase.entity.POPULAR
import com.akpdev.moviecodigotest.roomDatabase.entity.UPCOMING
import com.akpdev.moviecodigotest.screen.MoviePagerFragmentDirections
import com.akpdev.moviecodigotest.screen.MoviesListRecyclerAdapter
import com.akpdev.moviecodigotest.screen.MoviewListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingMovieListFragment:Fragment() {
    private var _binding: FragmentUpcomingMovieListBinding? = null
    val binding: FragmentUpcomingMovieListBinding
        get() = _binding!!

    private var _adapter : MoviesListRecyclerAdapter? = null
    val adapter:MoviesListRecyclerAdapter
        get() = _adapter!!
    private val viewModel by viewModels<UpcomingMovieListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentUpcomingMovieListBinding.inflate(inflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _adapter = MoviesListRecyclerAdapter { data ->
            findNavController().navigate(
                MoviePagerFragmentDirections.actionMoviePagerFragmentToMovieDetailFragment(
                    data.id, data.type
                )
            )
        }
        binding.rvUpcomingMovies.adapter = adapter
        adapter.addLoadStateListener { loadState ->

            binding.btnReload.isVisible = loadState.source.refresh is LoadState.Error
            binding.rvUpcomingMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
            if (loadState.source.refresh is LoadState.Loading) {
                binding.lavLoadState.setAnimation(R.raw.cube_loader)
                binding.lavLoadState.visibility = View.VISIBLE
                binding.lavLoadState.playAnimation()
            } else {
                binding.lavLoadState.cancelAnimation()
                binding.lavLoadState.visibility = View.INVISIBLE
            }
        }
        binding.btnReload.setOnClickListener {
            adapter.refresh()
        }
    }
    fun onPageSelected() {
        // Handle page selection in this child fragment
        observeData()
    }

    fun observeData(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.upcomingMoviesListLiveData.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}