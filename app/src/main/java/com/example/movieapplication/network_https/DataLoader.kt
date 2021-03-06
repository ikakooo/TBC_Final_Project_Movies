package com.example.movieapplication.network_https


import android.util.Log.d
import com.example.movieapplication.bottom_navigation.actors.model.ActorsResponseModel
import com.example.movieapplication.bottom_navigation.search.models.SearchResultModelByName
import com.example.movieapplication.detailed_movie_view.model.MovieCastResponse
import com.example.movieapplication.bottom_navigation.home.models.MainMovieModel
import com.example.movieapplication.constants.Constants.BASE_URL_MOVIES
import com.example.movieapplication.detailed_movie_view.model.MovieSearchResultModelByID
import com.example.movieapplication.detailed_movie_view.model.MovieTrailerModeByID
import com.example.movieapplication.network_https.futurecallbacks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DataLoader {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_MOVIES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(APIService::class.java)


    fun getRequestTopToday(
        key: String,
        page: String,
        callback: FutureCallbackMoviesBridge
    ) {
        val call = service.getTopToday(key, page)
        call.enqueue(object : Callback<MainMovieModel> {
            override fun onFailure(call: Call<MainMovieModel>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<MainMovieModel>,
                response: Response<MainMovieModel>
            ) {
                response.body()?.let { callback.onResponse(it) }
                d("topToday", response.body().toString())
            }

        })
    }


    fun getRequestPopular(
        key: String,
        page: String,
        callback: FutureCallbackMoviesBridge
    ) {
        val call = service.getPopular(key, page)
        call.enqueue(object : Callback<MainMovieModel> {
            override fun onFailure(call: Call<MainMovieModel>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<MainMovieModel>,
                response: Response<MainMovieModel>

            ) {
                response.body()?.let { callback.onResponse(it) }
                d("Popular", response.body().toString())
            }

        })
    }


    fun getRequestTopRated(
        key: String,
        page: String,
        callback: FutureCallbackMoviesBridge
    ) {
        val call = service.getTopRated(key, page)
        call.enqueue(object : Callback<MainMovieModel> {
            override fun onFailure(call: Call<MainMovieModel>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<MainMovieModel>,
                response: Response<MainMovieModel>
            ) {
                response.body()?.let { callback.onResponse(it) }
                d("topRated", response.body().toString())
            }

        })
    }

    fun getRequestUpComing(
        key: String,
        page: String,
        callback: FutureCallbackMoviesBridge
    ) {
        val call = service.getUpcoming(key, page)
        call.enqueue(object : Callback<MainMovieModel> {
            override fun onFailure(call: Call<MainMovieModel>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<MainMovieModel>,
                response: Response<MainMovieModel>
            ) {
                response.body()?.let { callback.onResponse(it) }
                d("topRated", response.body().toString())
            }
        })
    }

    fun getRequestedMovieByID(
        id: Int,
        key: String,

        callbackByID: FutureCallbackMoviesSearchByIDBridge
    ) {
        val call = service.getMoviesByID(id, key)
        call.enqueue(object : Callback<MovieSearchResultModelByID> {
            override fun onFailure(call: Call<MovieSearchResultModelByID>, t: Throwable) {
                callbackByID.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<MovieSearchResultModelByID>,
                response: Response<MovieSearchResultModelByID>
            ) {
                // //response.body()?.let { callback.onResponseSearchedByID(it) }

                response.body()?.let { callbackByID.onResponseSearchedByID(it) }
                d("topRatedfbdfdava", response.body().toString())
            }
        })
    }

    fun getRequestedCastByID(
        id: Int,
        key: String,
        callback: FutureCallbackCastBridge
    ) {
        val call = service.getCastByID(id, key)
        call.enqueue(object : Callback<MovieCastResponse> {
            override fun onFailure(call: Call<MovieCastResponse>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<MovieCastResponse>,
                response: Response<MovieCastResponse>
            ) {
                response.body()?.let { callback.onResponseCastByID(it) }
                d("topRsffeatedfbdfdava", response.body().toString())
            }
        })
    }


    fun getRequestSearchedMoviesByName(
        key: String,
        movieNameString: String,
        callback: FutureCallbackMoviesSearchByNameBridge
    ) {
        val call = service.getSearchedMoviesByName(key, movieNameString)
        call.enqueue(object : Callback<SearchResultModelByName> {
            override fun onFailure(call: Call<SearchResultModelByName>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<SearchResultModelByName>,
                response: Response<SearchResultModelByName>
            ) {
                response.body()?.let { callback.onResponseSearchedByName(it) }
                d("topRated", response.body().toString())
            }
        })
    }

    fun getPopularActors(
        page: String,
        key: String,
        callbackFutureCallbackActors: FutureCallbackActorsBridge
    ) {
        val call = service.getPopularActors(key, page)
        call.enqueue(object : Callback<ActorsResponseModel> {

            override fun onFailure(call: Call<ActorsResponseModel>, t: Throwable) {
                callbackFutureCallbackActors.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<ActorsResponseModel>,
                responseModel: Response<ActorsResponseModel>
            ) {
                responseModel.body()?.let { callbackFutureCallbackActors.onResponseActor(it) }
            }

        })
    }

    fun getActorDetails(
        id: Int,
        key: String,
        callbackActorDetailsByIDBridge: FutureCallbackActorDetailsByIDBridge
    ) {
        val call = service.getActorDetails(id, key)
        call.enqueue(object : Callback<ActorsResponseModel.Actor> {
            override fun onFailure(call: Call<ActorsResponseModel.Actor>, t: Throwable) {
                callbackActorDetailsByIDBridge.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<ActorsResponseModel.Actor>,
                responseModel: Response<ActorsResponseModel.Actor>
            ) {
                responseModel.body()
                    ?.let { callbackActorDetailsByIDBridge.onResponseActorDetail(it) }
                d("reslsklkslks", responseModel.toString())
            }

        })
    }


    fun getRequestedMovieTrailerByID(
        id: Int,
        key: String,
        callback: FutureCallbackMovieTrailerByIDBridge
    ) {
        val call = service.getMovieTrailerByID(id, key)
        call.enqueue(object : Callback<MovieTrailerModeByID> {
            override fun onFailure(call: Call<MovieTrailerModeByID>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<MovieTrailerModeByID>,
                response: Response<MovieTrailerModeByID>
            ) {
                response.body()?.let { callback.onResponseMovieTrailerByID(it) }
                d("ResponseBody", response.body().toString())
            }
        })
    }

}