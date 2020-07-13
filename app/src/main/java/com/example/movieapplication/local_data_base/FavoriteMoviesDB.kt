package com.example.movieapplication.local_data_base

import androidx.room.*
import com.example.movieapplication.AppRoot

@Database(entities = [RoomFavouriteMovieModel::class], version = 2)
abstract class FavoriteMoviesDB: RoomDatabase(){
    abstract fun favoriteDaoConnection() : FavoriteMoviesDao
}