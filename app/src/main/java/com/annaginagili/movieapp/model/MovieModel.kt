package com.annaginagili.movieapp.model

import java.io.Serializable

data class MovieModel (val poster_path: String, val vote_average: Double, val title: String,
                       val release_date: String, val runtime: Int, val genres: List<GenreModel>,
                       val overview: String): Serializable