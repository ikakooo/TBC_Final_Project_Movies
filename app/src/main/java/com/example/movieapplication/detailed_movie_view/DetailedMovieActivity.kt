package com.example.movieapplication.detailed_movie_view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapplication.R
import com.example.movieapplication.bottom_navigation.home.HomeFragment
import com.example.movieapplication.constants.Constants.API_KEY
import com.example.movieapplication.detailed_actors_view.DetailedActorsActivity
import com.example.movieapplication.detailed_movie_view.model.MovieCastResponse
import com.example.movieapplication.detailed_movie_view.model.MovieSearchResultModelByID
import com.example.movieapplication.detailed_movie_view.model.MovieTrailerModeByID
import com.example.movieapplication.local_data_base.DatabaseBuilder.roomDB
import com.example.movieapplication.local_data_base.RoomFavouriteMovieModel
import com.example.movieapplication.network_https.DataLoader
import com.example.movieapplication.network_https.futurecallbacks.FutureCallbackCastBridge
import com.example.movieapplication.network_https.futurecallbacks.FutureCallbackMovieTrailerByIDBridge
import com.example.movieapplication.network_https.futurecallbacks.FutureCallbackMoviesSearchByIDBridge
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_detailed_movie.*

class DetailedMovieActivity : AppCompatActivity() {
    private var castList = mutableListOf<MovieCastResponse.MovieCast>()
    lateinit var castAdapter: CastAdapter
    var youtubeVideoID: String? = null
    private var isFavourite = false
    val imgBaseURL = "https://image.tmdb.org/t/p/w780/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_movie)
        supportActionBar?.hide()
        init()
        d("erfrerferf", "vrereerer")


    }


    private fun init() {

        var movieID = intent.getStringExtra("movieID")
        d("sdfdfdsf", movieID.toString())
        val originalTitle = intent.getStringExtra("name")
        titleTV.text = originalTitle
        if (movieID == null) {
            movieID = "531454"
        }
        getPostsDetailedMovie(movieID.toInt())
        getPostsDetailedCast(movieID.toInt())
        getPostsDetailedTrailer(movieID.toInt())
        castAdapter = CastAdapter(castList, object : DetailedMovieListener {
            override fun detailedViewClick(position: Int) {
                val castMember = castList[position]
                val intent = Intent(applicationContext, DetailedActorsActivity::class.java)
                intent.putExtra("id", castMember.id)
                startActivity(intent)
            }

        })
        castRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castRecyclerView.adapter = castAdapter

        //Player

        lifecycle.addObserver(youtube_player_view)
        youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {


                val loader = object : Thread() {
                    override fun run() {
                        try {
                            sleep(200)
                            d("fjsdfksdf", youtubeVideoID.toString())
                            youTubePlayer.loadVideo(youtubeVideoID.toString(), 0f)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                loader.start()
            }
        })
    }

    private fun getPostsDetailedMovie(id: Int) {
        DataLoader.getRequestedMovieByID(
            id, API_KEY,
            object :
                FutureCallbackMoviesSearchByIDBridge {
                @SuppressLint("SetTextI18n")
                override fun onResponseSearchedByID(response: MovieSearchResultModelByID) {
                    d("fsfesefesfsf", response.toString())
                    titleDetailedTextViewID.text = response.overview
                    titleTV.text = response.original_title
                    ratingTV.text = response.vote_average
                    (0 until response.genres.size).forEach {
                        val text = genreTVID.text.toString()
                        genreTVID.text = text + " " + response.genres[it].name
                    }
                    Glide.with(applicationContext).load(imgBaseURL + response.poster_path)
                        .into(moviesDetailedImageViewID)
                    Glide.with(applicationContext).load(imgBaseURL + response.backdrop_path)
                        .into(detailedBackground)
                    progressBarVisibilityID.isVisible = false
                    /// არის თუ არა დამატებული ფავორიტებში ///////////////////////////////////
                    val favorite = roomDB.favoriteDaoConnection().isFavourite(response.id)
                    isFavourite = if(favorite == null) {
                        addToFavourites.setImageResource(R.drawable.ic_heart_unchecked)
                        false
                    } else {
                        addToFavourites.setImageResource(R.drawable.ic_heart_checked)
                        true
                    }
                    /////////////////////////////////////////////////////////////////////////////
                    // ფავორიტებში დამატება / წაშლა
                    addToFavourites.setOnClickListener {
                        isFavourite = if (isFavourite) { roomDB.favoriteDaoConnection().deleteFavouriteMovie(response.id)
                            addToFavourites.setImageResource(R.drawable.ic_heart_unchecked)

                            false
                        } else {
                            roomDB.favoriteDaoConnection().insertRoomFavouriteMovieModel(RoomFavouriteMovieModel(movie_id = response.id, path = response.poster_path, title = response.original_title))
                            addToFavourites.setImageResource(R.drawable.ic_heart_checked)
                            true
                        }
                    }
                }
                override fun onFailure(error: String) {
                    d("detailedResponse", error)
                }
            }
        )
    }



    private fun getPostsDetailedCast(id: Int) {
        DataLoader.getRequestedCastByID(id, API_KEY, object :
            FutureCallbackCastBridge {
            override fun onResponseCastByID(response: MovieCastResponse) {
                d("dfsdfsdf", response.toString())
                val size = response.cast?.size.toString().toInt()
                (0 until size).forEach {
                    castList.add(
                        MovieCastResponse.MovieCast(
                            response.cast?.get(it)?.cast_id.toString().toInt(),
                            response.cast?.get(it)?.name.toString(),
                            response.cast?.get(it)?.profile_path.toString(),
                            response.cast?.get(it)?.id.toString()
                        )
                    )

                }

                castAdapter.notifyDataSetChanged()
                d("jakja", castList.size.toString())

            }

            override fun onFailure(error: String) {
            }
        })
    }

    private fun getPostsDetailedTrailer(id: Int) {
        DataLoader.getRequestedMovieTrailerByID(id, API_KEY, object :
            FutureCallbackMovieTrailerByIDBridge {
            override fun onResponseMovieTrailerByID(response: MovieTrailerModeByID) {
                d("dfsdfhghffsdf", response.toString())

                (0 until response.results.size).forEach {
                    if (response.results[it].size == 1080) {
                        youtubeVideoID = response.results[it].key
                        return@onResponseMovieTrailerByID
                    } else youtubeVideoID = response.results[it].key

                }


            }

            override fun onFailure(error: String) {
            }
        })
    }
}