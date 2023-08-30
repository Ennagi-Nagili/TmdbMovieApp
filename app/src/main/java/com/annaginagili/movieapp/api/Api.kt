package com.annaginagili.movieapp.api

import com.annaginagili.movieapp.R
import com.annaginagili.movieapp.model.MovieModel
import com.annaginagili.movieapp.model.SearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/movie?api_key=" + Constants.api_key)
    fun searchMovie(@Query("query") query: String) : Call<SearchModel>

    @GET("movie/{id}?api_key=" + Constants.api_key)
    fun getMovieById(@Path("id") id: String): Call<MovieModel>

    @GET("discover/movie?api_key=" + Constants.api_key)
    fun getPopular(): Call<SearchModel>
}