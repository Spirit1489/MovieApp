package ru.spiritblog.movieapp.model

data class PopularMoviesResponse(
    val page: Int,
    val results: List<Movie>
)