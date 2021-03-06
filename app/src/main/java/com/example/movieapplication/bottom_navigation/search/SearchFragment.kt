package com.example.movieapplication.bottom_navigation.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapplication.R
import com.example.movieapplication.bottom_navigation.search.models.SearchResultModelByName
import com.example.movieapplication.bottom_navigation.search.models.SearchResultModelResultList
import com.example.movieapplication.constants.Constants.API_KEY
import com.example.movieapplication.detailed_movie_view.DetailedMovieActivity
import com.example.movieapplication.detailed_movie_view.DetailedMovieListener
import com.example.movieapplication.network_https.DataLoader
import com.example.movieapplication.network_https.futurecallbacks.FutureCallbackMoviesSearchByNameBridge
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {
    private var searchResultByNameMoviesList = mutableListOf<SearchResultModelResultList>()
    private lateinit var searchResultByNameAdapter: SearchResultRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_search, container, false)
        init(root)
        return root
    }


    private fun init(root: View) {
        searchResultByNameAdapter = SearchResultRecyclerViewAdapter(searchResultByNameMoviesList, object : DetailedMovieListener{
            override fun detailedViewClick(position: Int) {
                val searchedMovie = searchResultByNameMoviesList[position]
                val intent = Intent(context, DetailedMovieActivity::class.java)
                intent.putExtra("name", searchedMovie.original_title)
                intent.putExtra("movieID", searchedMovie.id.toString())
                startActivity(intent)
            }

        })
        root.searchResultRecyclerViewID.layoutManager = GridLayoutManager(context, 2)
        root.searchResultRecyclerViewID.adapter = searchResultByNameAdapter

        root.moviesSearchEditTextID.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                getPostsMoviesSearchByName(p0.toString())
            }

        })
    }


    private fun getPostsMoviesSearchByName(movieNameString: String) {


        DataLoader.getRequestSearchedMoviesByName(
            API_KEY, movieNameString,
            object :
                FutureCallbackMoviesSearchByNameBridge {
                override fun onResponseSearchedByName(response: SearchResultModelByName) {
                    d("sffdjsdsdfdfgfsdfs", response.toString())
                    searchResultByNameMoviesList.clear()
                    (0 until (response.results?.size ?: 1)-1).forEach{
                        searchResultByNameMoviesList.add(
                            SearchResultModelResultList(
                                response.results?.get(it)?.backdrop_path.toString(),
                                response.results?.get(it)?.id.toString().toInt(),
                                response.results?.get(it)?.original_language.toString(),
                                response.results?.get(it)?.original_title.toString(),
                                response.results?.get(it)?.overview.toString(),
                                response.results?.get(it)?.popularity.toString().toDouble(),
                                response.results?.get(it)?.poster_path.toString(),
                                response.results?.get(it)?.release_date.toString(),
                                response.results?.get(it)?.title.toString(),
                                response.results?.get(it)?.vote_average.toString().toDouble(),
                                response.results?.get(it)?.vote_count.toString().toInt()
                            ))}
                    d("eewffwee", searchResultByNameMoviesList.toString())
                    searchResultByNameAdapter.notifyDataSetChanged()
                }

                override fun onFailure(error: String) { d("dfgfgdfgdfg", error)
                }
            })
    }


}