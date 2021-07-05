package ru.spiritblog.movieapp

import androidx.arch.core.executor.DefaultTaskExecutor
import androidx.lifecycle.MutableLiveData
import ru.spiritblog.movieapp.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import ru.spiritblog.movieapp.repository.MovieRepository
import ru.spiritblog.movieapp.viewModels.MovieViewModel
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    @get:Rule
    val rule = DefaultTaskExecutor()

    @Mock
    lateinit var movieRepository: MovieRepository

    @Test
    fun getPopularMovies() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val releaseDate = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}"

        val movieLiveData = MutableLiveData<List<Movie>>()
        val popularMovies = listOf(
            Movie(
                title = "Title",
                release_date = releaseDate
            )
        )
        movieLiveData.postValue(popularMovies)

        Mockito.`when`(movieRepository.movies)
            .thenReturn(movieLiveData)
        val movieViewModel = MovieViewModel(movieRepository)

        assertEquals(
            movieLiveData.value,
            movieViewModel.popularMovies.getOrAwaitValue()
        )
    }

}