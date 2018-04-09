package ru.touchin.mvvmsample.presentation.movieslist

import android.app.Application
import android.content.Context
import ru.touchin.mvvmsample.presentation.base.BaseViewModel
import javax.inject.Inject
import ru.touchin.kotlinsamples.data.database.AppDatabase
import ru.touchin.mvvmsample.R
import ru.touchin.mvvmsample.data.network.MoviesRepository
import ru.touchin.mvvmsample.data.network.MoviesRepository.Companion.POPULAR
import ru.touchin.mvvmsample.data.network.MoviesRepository.Companion.TOP_RATED

class MoviesListViewModel @Inject internal constructor(val app: Application,
                                                       var moviesRepository: MoviesRepository,
                                                       var db: AppDatabase) : BaseViewModel(app) {

    private val prefs by lazy { app.getSharedPreferences(app.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE) }
    private val modeKey by lazy { app.getString(R.string.preference_mode_key) }

    var pagedItems = moviesRepository.movies(10)

    fun loadPopularMovies() {
        prefs.edit().apply {
            putInt(modeKey, POPULAR)
            apply()
        }
        moviesRepository.loadFirstPage()
    }

    fun loadTopRatedMovies() {
        prefs.edit().apply {
            putInt(modeKey, TOP_RATED)
            apply()
        }
        moviesRepository.loadFirstPage()
    }

    fun refreshMovies() = moviesRepository.loadFirstPage()
}