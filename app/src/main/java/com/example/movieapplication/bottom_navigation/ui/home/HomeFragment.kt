package com.example.movieapplication.bottom_navigation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.R
import com.example.movieapplication.bottom_navigation.ui.home.adapters.PopularAdapter
import com.example.movieapplication.bottom_navigation.ui.home.adapters.TopRatedAdapter
import com.example.movieapplication.bottom_navigation.ui.home.adapters.TopTodayAdapter
import com.example.movieapplication.bottom_navigation.ui.home.adapters.UpcomingAdapter
import com.example.movieapplication.detailed_view.DetailedMovieActivity
import com.example.movieapplication.detailed_view.DetailedMovieListener
import com.example.movieapplication.network_https.models.movie
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private var topTodayMoviesList = mutableListOf<movie>()
    private lateinit var topTodayAdapter: TopTodayAdapter

    private var popularMoviesList = mutableListOf<movie>()
    private lateinit var popularAdapter: PopularAdapter

    private var topRatedMoviesList = mutableListOf<movie>()
    private lateinit var topRatedAdapter: TopRatedAdapter

    private var upComingMoviesList = mutableListOf<movie>()
    private lateinit var upComingAdapter: UpcomingAdapter

    private lateinit var homeViewModel: HomeViewModel

    companion object {
        const val API_KEY = "22c64b1eb2954257036c84ed667c4109"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        init(root)


        homeViewModel.topTodayMoviesLiveData.observe(viewLifecycleOwner, Observer {
            topTodayMoviesList.addAll(it)
            topTodayAdapter.notifyDataSetChanged()
        })

        homeViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {

            popularMoviesList.addAll(it)
            popularAdapter.notifyDataSetChanged()
        })

        homeViewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner, Observer {

            topRatedMoviesList.addAll(it)
            topRatedAdapter.notifyDataSetChanged()
        })

        homeViewModel.upComingMoviesLiveData.observe(viewLifecycleOwner, Observer {

            upComingMoviesList.addAll(it)
            upComingAdapter.notifyDataSetChanged()

        })



        return root
    }

    private fun init(root: View) {
        /////////////////////////////////////////////////////////
        topTodayAdapter = TopTodayAdapter(topTodayMoviesList)
        root.topTodayRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.topTodayRecyclerView.isNestedScrollingEnabled = true
        root.topTodayRecyclerView.setHasFixedSize(false)
        root.topTodayRecyclerView.adapter = topTodayAdapter
/////////////////////////////////////////////////////////////////////////////////////////////////
        topRatedAdapter = TopRatedAdapter(topRatedMoviesList, object : DetailedMovieListener{
            override fun detailedViewClick(position: Int) {
                val topRatedMovie = topRatedMoviesList[position]
                val intent = Intent(context, DetailedMovieActivity::class.java)
                intent.putExtra("name", topRatedMovie.original_title)
                startActivity(intent)
            }

        })
        root.topRatedRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.topRatedRecyclerView.isNestedScrollingEnabled = true
        root.topRatedRecyclerView.setHasFixedSize(false)
        root.topRatedRecyclerView.adapter = topRatedAdapter
///////////////////////////////////////////////////////////////////////////////////////////
        popularAdapter = PopularAdapter(popularMoviesList, object : DetailedMovieListener{
            override fun detailedViewClick(position: Int) {
                val popularMovie = popularMoviesList[position]
                val intent = Intent(context, DetailedMovieActivity::class.java)
                intent.putExtra("name", popularMovie.original_title)
                startActivity(intent)
            }

        })
        root.popularRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.popularRecyclerView.isNestedScrollingEnabled = false
        root.popularRecyclerView.setHasFixedSize(false)
        root.popularRecyclerView.adapter = popularAdapter
////////////////////////////////////////////////////////////////////////////
        upComingAdapter = UpcomingAdapter(upComingMoviesList)
        root.upcomingRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.upcomingRecyclerView.isNestedScrollingEnabled = false
        root.upcomingRecyclerView.setHasFixedSize(false)
        root.upcomingRecyclerView.adapter = upComingAdapter


    }
}