package com.example.movieapplication.bottom_navigation.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication.R
import com.example.movieapplication.detailed_movie_view.DetailedMovieListener
import com.example.movieapplication.bottom_navigation.home.models.Movies
import com.example.movieapplication.constants.Constants.BASE_IMG_URL
import kotlinx.android.synthetic.main.items_layout.view.*

class MoviesRecyclerviewAdapter(
    val MoviesList: MutableList<Movies>,
    val detailedMovieListener: DetailedMovieListener
) :
    RecyclerView.Adapter<MoviesRecyclerviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = MoviesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var model: Movies

        fun onBind() {
            model = MoviesList[adapterPosition]
            itemView.title.text = model.original_title
            Glide.with(itemView.context).load(BASE_IMG_URL + model.poster_path)
                .into(itemView.moviesImageViewID)
            itemView.setOnClickListener {
                detailedMovieListener.detailedViewClick(adapterPosition)
            }
        }

    }

}