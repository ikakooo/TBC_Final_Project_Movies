package com.example.movieapplication.bottom_navigation.home

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.AppRoot
import com.example.movieapplication.network_https.DataLoader
import com.example.movieapplication.network_https.futurecallbacks.FutureCallbackMoviesBridge
import com.example.movieapplication.bottom_navigation.home.models.MainMovieModel
import com.example.movieapplication.bottom_navigation.home.models.Movies
import com.example.movieapplication.constants.Constants.API_KEY
import com.example.movieapplication.tools.CustomTools.isConnectedToNetwork

class HomeViewModel : ViewModel() {


    private val _topTodayMoviesLiveData = MutableLiveData<MutableList<Movies>>().apply {
        if (AppRoot.instance.getContext().isConnectedToNetwork()){
            getPostsTopToday()
        }else{
            d("cfvfdvvfvd","noooooooooooooooo")
        }


    }
    private val _popularMoviesLiveData = MutableLiveData<MutableList<Movies>>().apply {

        if (AppRoot.instance.getContext().isConnectedToNetwork()){
            getPostsPopular()
        }else{
            d("cfvfdvvfvd","noooooooooooooooo")
        }
    }
    private val _topRatedMoviesLiveData = MutableLiveData<MutableList<Movies>>().apply {

        if (AppRoot.instance.getContext().isConnectedToNetwork()){
            getPostsTopRated()
        }else{
            d("cfvfdvvfvd","noooooooooooooooo")
        }
    }
    private val _upComingMoviesLiveData = MutableLiveData<MutableList<Movies>>().apply {

        if (AppRoot.instance.getContext().isConnectedToNetwork()){
            getPostsUpComing()
        }
    }

    val topTodayMoviesLiveData: LiveData<MutableList<Movies>> = _topTodayMoviesLiveData
    val topRatedMoviesLiveData: LiveData<MutableList<Movies>> = _topRatedMoviesLiveData
    val popularMoviesLiveData: LiveData<MutableList<Movies>> = _popularMoviesLiveData
    val upComingMoviesLiveData: LiveData<MutableList<Movies>> = _upComingMoviesLiveData


    private fun getPostsTopToday() {

        DataLoader.getRequestTopToday(
            API_KEY, "1",
            object : FutureCallbackMoviesBridge {
                override fun onResponse(response: MainMovieModel) {
                    _topTodayMoviesLiveData.value = response.results.toMutableList()
                    d("cfdvdfvfdv",response.results.toMutableList().toString())
                }
                override fun onFailure(error: String) {
                } })
    }


    private fun getPostsPopular() {

        DataLoader.getRequestPopular(
            API_KEY, "1",

            object :
                FutureCallbackMoviesBridge {

                override fun onResponse(response: MainMovieModel) {
                    _popularMoviesLiveData.value = response.results.toMutableList()

                }
                override fun onFailure(error: String) {
                }
            }
        )
    }

    private fun getPostsTopRated() {
        DataLoader.getRequestTopRated(
            API_KEY, "1",
            object :
                FutureCallbackMoviesBridge {
                override fun onResponse(response: MainMovieModel) {
                    _topRatedMoviesLiveData.value = response.results.toMutableList()
                }
                override fun onFailure(error: String) {
                }
            }
        )
    }


    private fun getPostsUpComing() {
        DataLoader.getRequestUpComing(
            API_KEY, "1",
            object :
                FutureCallbackMoviesBridge {
                override fun onResponse(response: MainMovieModel) {
                    _upComingMoviesLiveData.value = response.results.toMutableList()
                }

                override fun onFailure(error: String) {}
            })
    }


}