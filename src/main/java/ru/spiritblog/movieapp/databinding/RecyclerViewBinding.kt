package ru.spiritblog.movieapp.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.spiritblog.movieapp.adapters.MovieAdapter
import ru.spiritblog.movieapp.model.Movie

@BindingAdapter("list")
fun bindMovies(view: RecyclerView, movies: List<Movie>?) {
    val adapter = view.adapter as MovieAdapter
    adapter.addMovies(movies ?: emptyList())
}