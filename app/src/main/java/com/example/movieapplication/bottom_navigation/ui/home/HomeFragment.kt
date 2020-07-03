package com.example.movieapplication.bottom_navigation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.R
import com.example.movieapplication.adapters.PopularAdapter
import com.example.movieapplication.adapters.TopTodayAdapter
import com.example.movieapplication.network_https.movie
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private var popularMoviesList = mutableListOf<movie>()
    private var topTodayMoviesList = mutableListOf<movie>()
    private lateinit var topTodayAdapter: TopTodayAdapter
    private lateinit var popularAdapter: PopularAdapter
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



        return root
    }

    private fun init(root: View) {
        topTodayAdapter = TopTodayAdapter(topTodayMoviesList)
        root.topTodayRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.topTodayRecyclerView.adapter = topTodayAdapter

        popularAdapter = PopularAdapter(popularMoviesList)
        root.popularRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.popularRecyclerView.adapter = popularAdapter


    }

//    private fun httpsRequest(page: String,root:View) {
//        DateLoader.getRequest(
//            API_KEY,page,
//
//            object : FutureCallbackCountryBridge {
//                override fun onResponse(response: MainMovieModel) {
//                    val moviesOfCountries = response.toString()
//                    Log.d("erferffref", response.toString())
//                    // texViewID.text = response.toString()
//                    Log.d("dsfdfsdf", response.results[0].original_title.toString())
//                    (0 until response.results.size).forEach{ it ->
//                       // ItemsList.add(ModelItem(response.results[it].original_title.toString()))
//                    }
//                    topTodayAdapter.notifyDataSetChanged()
//
//                }
//
//
//                override fun onFailure(error: String) {
//                    Toast.makeText(context,"this is toast message", Toast.LENGTH_SHORT).show()
//                    val toast = Toast.makeText(context, "Hello Javatpoint", Toast.LENGTH_LONG)
//                    toast.show()
//                    val myToast = Toast.makeText(context,"No Internet", Toast.LENGTH_SHORT)
//                    myToast.setGravity(Gravity.TOP,200,200)
//                    myToast.show()
//                }
//
//
//            }
//        )
//    }


}