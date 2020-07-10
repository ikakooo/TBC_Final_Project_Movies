package com.example.movieapplication.network_https.futurecallbacks

import com.example.movieapplication.bottom_navigation.search.models.ByNameSearchResultModel


interface FutureCallbackMoviesSearchByNameBridge {
    fun onResponseSearchedByName(response: ByNameSearchResultModel)
    fun onFailure(error: String)
}