package com.akpdev.moviecodigotest.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.akpdev.moviecodigotest.R
import com.akpdev.moviecodigotest.databinding.FragmentMoviePagerBinding
import com.akpdev.moviecodigotest.screen.popular.PopularMovieListFragment
import com.akpdev.moviecodigotest.screen.upcoming.UpcomingMovieListFragment
import com.google.android.material.tabs.TabLayoutMediator


class MoviePagerFragment: Fragment() {
    private lateinit var binding:FragmentMoviePagerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentMoviePagerBinding.inflate(inflater,container,false).also{
            binding=it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter=MoviePagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text=getString(R.string.upcoming)
                }
                1 -> {
                    tab.text=getString(R.string.popular)
                }

            }
        }.attach()

        binding.pager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val childFragment = childFragmentManager.fragments[position]
                when(childFragment){
                    is UpcomingMovieListFragment->childFragment.onPageSelected()
                    is PopularMovieListFragment->childFragment.onPageSelected()
                }
                Log.e("Selected_Page", position.toString())
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

    }

}

class MoviePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return when(position){
            0->  UpcomingMovieListFragment()
            1-> PopularMovieListFragment()
            else -> UpcomingMovieListFragment()
        }
    }
}

