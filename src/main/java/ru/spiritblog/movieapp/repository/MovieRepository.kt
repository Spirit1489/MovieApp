package ru.spiritblog.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.spiritblog.movieapp.api.MovieService
import ru.spiritblog.movieapp.database.MovieDao
import ru.spiritblog.movieapp.database.MovieDatabase
import ru.spiritblog.movieapp.model.Movie

class MovieRepository(private val movieService: MovieService, private val movieDatabase: MovieDatabase) {
    private val apiKey = "a268989c3e381c769048746555e23e93"

    private val movieLiveData = MutableLiveData<List<Movie>>()
    private val errorLiveData = MutableLiveData<String>()

    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    val error: LiveData<String>
        get() = errorLiveData

    suspend fun fetchMovies() {
        val movieDao: MovieDao = movieDatabase.movieDao()
        var moviesFetched = movieDao.getMovies()
        if (moviesFetched.isEmpty()) {
            try {
                val popularMovies = movieService.getPopularMovies(apiKey)
                moviesFetched = popularMovies.results
                movieDao.addMovies(moviesFetched)
            } catch (exception: Exception) {
                errorLiveData.postValue("An error occurred: ${exception.message}")
            }
        }

        movieLiveData.postValue(moviesFetched)
    }

    suspend fun fetchMoviesFromNetwork() {
        val movieDao: MovieDao = movieDatabase.movieDao()
        try {
            val popularMovies = movieService.getPopularMovies(apiKey)
            val moviesFetched = popularMovies.results
            movieDao.addMovies(moviesFetched)
        } catch (exception: Exception) {
            errorLiveData.postValue("An error occurred: ${exception.message}")
        }
    }
}