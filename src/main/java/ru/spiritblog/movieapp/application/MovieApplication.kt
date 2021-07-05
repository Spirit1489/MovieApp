package ru.spiritblog.movieapp.application

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import ru.spiritblog.movieapp.api.MovieService
import ru.spiritblog.movieapp.database.MovieDatabase
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.spiritblog.movieapp.repository.MovieRepository
import ru.spiritblog.movieapp.worker.MovieWorker
import java.util.concurrent.TimeUnit

class MovieApplication : Application() {

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val movieService = retrofit.create(MovieService::class.java)

        val movieDatabase = MovieDatabase.getInstance(applicationContext)

        movieRepository = MovieRepository(movieService, movieDatabase)

        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = PeriodicWorkRequest
            .Builder(MovieWorker::class.java, 1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag("movie-work")
            .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}