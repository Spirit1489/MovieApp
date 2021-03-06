package ru.spiritblog.movieapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import ru.spiritblog.movieapp.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spiritblog.movieapp.repository.MovieRepository
import java.util.*

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    init {
        fetchPopularMovies()
    }

    val popularMovies: LiveData<List<Movie>>
        get() = movieRepository.movies.map { list ->
            list.filter {
                it.release_date.startsWith(
                        Calendar.getInstance().get(Calendar.YEAR).toString()
                )
            }.sortedBy { it.title }
        }

    fun getError(): LiveData<String> = movieRepository.error

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
        }
    }
}