package ru.spiritblog.movieapp

import androidx.arch.core.executor.DefaultTaskExecutor
import ru.spiritblog.movieapp.api.MovieService
import ru.spiritblog.movieapp.model.Movie
import ru.spiritblog.movieapp.model.PopularMoviesResponse

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import ru.spiritblog.movieapp.repository.MovieRepository

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {
    @get:Rule
    val rule = DefaultTaskExecutor()

    @InjectMocks
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var movieService: MovieService

    @Test
    fun fetchMovies() {
        val movies = listOf(Movie(id = 3), Movie(id = 4))
        val response = PopularMoviesResponse(1, movies)

        runBlocking {
            Mockito.`when`(movieService.getPopularMovies(anyString()))
                .thenReturn(response)

            movieRepository.fetchMovies()
            val movieLiveData = movieRepository.movies
            assertEquals(movieLiveData.value, movies)
        }
    }
}